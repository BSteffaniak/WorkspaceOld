package net.foxycorndog.minecraftcopy;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.util.ArrayList;

import net.foxycorndog.minecraftcopy.entities.Chunk;
import net.foxycorndog.presto2d.util.SpriteSheet;
import net.foxycorndog.presto3d.components.Camera;
import net.foxycorndog.presto3d.graphics.PrestoGL3D;
import net.foxycorndog.presto3d.util.FrameLoop;
import net.foxycorndog.presto3d.util.FrameTask;
import net.foxycorndog.presto3d.util.volume.Volume3f;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.Sys;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL15;
import org.lwjgl.util.glu.Sphere;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;
import org.newdawn.slick.util.ResourceLoader;

import static net.foxycorndog.presto3d.graphics.PrestoGL3D.*;

import static org.lwjgl.util.glu.GLU.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class MinecraftCopy
{
	private ArrayList<Chunk> cubes = new ArrayList<Chunk>();
	
	private int         verticesId, normalsId, texturesId;
	private int         verticesIndex;
	private int         fps;
	
	private float       gravity;
	private float       cameraSpeed;
	
	private SpriteSheet sprites;
	
	private long        newTime, oldTime;
	
	private ByteBuffer  mappedBuffer;
	private FloatBuffer verticesBuffer, normalsBuffer, texturesBuffer;
	
	private Player	    player;
	
	private final int cu = 1000000;
	
	private final int amountOfVertices = 6 * 4;
	private final int vertexSize       = 3;
	private final int cubeSize         = amountOfVertices * vertexSize;
	private final int chunkSize	       = cubeSize * 16 * 16 * 16;
	
	private static final String GAME_TITLE = "Minecraft Copy";
	
	
	float dx       = 0.0f;
	float dy       = 0.0f;
	float dt       = 0.0f; //length of frame
	float lastTime = 0.0f; // when the last frame was
	float time     = 0.0f;
 
	float mouseSensitivity = 0.05f;
	float movementSpeed = 3.0f; //move 10 units per second
	
	
	
	
	public static void main(String args[])
	{
		new MinecraftCopy().init();
	}
	
	public MinecraftCopy()
	{
		try
		{
			Display.create();
			Display.setDisplayMode(new DisplayMode(640, 512));
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
		}
		
		Display.setTitle(GAME_TITLE);

		initBasicView(0.01f, -1.0f);
		
		
		// Lights...
		initBasicLights();
		
		
		gravity = 2.5f;
		
		fps = 0;
		
		oldTime = System.currentTimeMillis();
		newTime = System.currentTimeMillis();
	}
	
	public void init()
	{
		sprites = new SpriteSheet("res/images/texturepacks/8bit/8bit/test.png", "PNG", 18, 18, true);
		
		cubes.add(new Chunk(0, -2, 0, this));
		cubes.add(new Chunk(-3, -2, -6, this));
		
		//init();
		
		for (int i = 0; i < chunkSize / cubeSize; i ++)
		{
			cubes.get(0).addCube(i % 16, i / 16 % 16, i / 16 / 16);//, grass);
		}
		
		verticesIndex = cubes.get(0).getAmountOfCubes() * 72;
		
		for (int i = 0; i < chunkSize / cubeSize; i ++)
		{
			cubes.get(1).addCube(i % 16, i / 16 % 16, i / 16 / 16);//, grass);
		}
		//cubes.get(0).addCube(15, 15, 15);
		
		//cubes.set(0, PrestoGL.addCubeArray(0, 0, -130, 10, 0, cubes.get(0)));
		
		verticesBuffer = BufferUtils.createFloatBuffer(cubeSize * cu);
		verticesBuffer.put(cubes.get(0).getVertices());
		verticesBuffer.put(cubes.get(1).getVertices());
		verticesBuffer.flip();
		
		normalsBuffer = BufferUtils.createFloatBuffer(cubeSize * cu);
		normalsBuffer.put(cubes.get(0).getNormals());
		normalsBuffer.put(cubes.get(1).getNormals());
		normalsBuffer.flip();
		
		texturesBuffer = BufferUtils.createFloatBuffer(((cubeSize / vertexSize) * 2) * cu);
		texturesBuffer.put(cubes.get(0).getTextures());
		texturesBuffer.put(cubes.get(1).getTextures());
		texturesBuffer.flip();
		
		
		verticesId = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, verticesId);
		glBufferData(GL_ARRAY_BUFFER, verticesBuffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		normalsId  = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, normalsId);
		glBufferData(GL_ARRAY_BUFFER, normalsBuffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		texturesId  = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, texturesId);
		glBufferData(GL_ARRAY_BUFFER, texturesBuffer, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
		
		
		player = new Player(0, 0, 0, this);
		player.setSpeed(1);
		player.setVolume(new Volume3f(10, 10, 10));
		
		player.setCameraViewLocations(5, 35, 45);
		
		new FrameLoop().start(60, new FrameTask()
				{
					public void run()
					{
						updateFps();
						
						glLoadIdentity();
						
						//look through the camera before you draw anything
						player.lookThrough();
						
						drawCubes();
						
//						Sphere p = new Sphere();
//						p.draw(5, 5, 5);
						
						checkMouse();
						
						checkKeys();
						
						//playerLoop();
					}
				}
		);
	}
	
	public void drawCubes()
	{
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);
		
		beginNormalDraw(normalsId);
		beginVertexDraw(verticesId, vertexSize);
		beginTextureDraw(texturesId);

		glDrawArrays(GL_QUADS, 0, cubes.get(0).getVertices().length * 2);
		
		endTextureDraw();
		endVertexDraw();
		endNormalDraw();
	}
	
	public void playerLoop()
	{
		player.move(Player.DOWN, gravity);
	}
	
	public void checkKeys()
	{
		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			player.move(Player.FORWARD);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			player.move(Player.BACKWARD);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			player.move(Player.LEFT);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			player.move(Player.RIGHT);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_SPACE))
		{
			player.move(Player.UP);
		}
		if (Keyboard.isKeyDown(Keyboard.KEY_LSHIFT))
		{
			player.move(Player.DOWN);
		}
	
		if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
		{
			Display.destroy();
			System.exit(0);
		}
	}

	
	
	public void checkMouse()
	{
		
		if (Mouse.isGrabbed())
		{
			//distance in mouse movement from the last getDX() call.
			dx = Mouse.getDX();
			//distance in mouse movement from the last getDY() call.
			dy = -Mouse.getDY();
	 
			//control camera yaw from x movement from the mouse
			player.yaw(dx * mouseSensitivity);
			//control camera pitch from y movement from the mouse
			player.pitch(dy * mouseSensitivity);
			
			if (Mouse.isButtonDown(1))
			{
				Mouse.setGrabbed(false);
			}
		}
		else
		{
			if (Mouse.isButtonDown(0))
			{
				Mouse.setGrabbed(true);
			}
		}
		
		if (Mouse.next())
		{
			if (Mouse.isButtonDown(0))
			{
				editData();
			}
		}
	}
	
	public void editData()
	{
//		glBindBuffer(GL_ARRAY_BUFFER, verticesId);
		
		mappedBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_READ_WRITE, mappedBuffer);
		
		verticesBuffer = mappedBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		//verticesBuffer.put(PrestoGL.getCubeArray(-2, -3, -10, 10), 0, 72);
		
		verticesBuffer.position(verticesIndex += 72);
		
		verticesBuffer.put(PrestoGL3D.getCubeArray((int)(Math.random() * 1000) - 500, (int)(Math.random() * 1000) - 500, (int)(Math.random() * 1000) - 500, 10));
		
		verticesBuffer.rewind();
		
		glUnmapBuffer(GL_ARRAY_BUFFER);
		
//		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
	}
	
	private void updateFps()
	{
		fps ++;
		
		newTime = System.currentTimeMillis();
		
		if (newTime >= oldTime + 1000)
		{
			Display.setTitle(GAME_TITLE + " FPS: " + fps);
			
			oldTime = newTime;
			
			fps = 0;
		}
	}
	
	public int getChunkSize()
	{
		return chunkSize;
	}
	
	public Chunk getChunk(int index)
	{
		return cubes.get(index);
	}
	
	public SpriteSheet getSprites()
	{
		return sprites;
	}
}