import java.awt.List;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.ArrayList;

import java.awt.image.DataBufferByte;

import javax.imageio.ImageIO;

import net.foxycorndog.presto3d.graphics.PrestoGL3D;
import net.foxycorndog.presto3d.util.FrameLoop;
import net.foxycorndog.presto3d.util.FrameTask;

import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.util.glu.Sphere;
import org.lwjgl.util.vector.Vector3f;

import org.lwjgl.BufferUtils;

import static org.lwjgl.util.glu.GLU.gluPerspective;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;

public class Main
{
	Vector3f rotation = new Vector3f(0, 0, 0);
	Vector3f offset   = new Vector3f(0, 0, 0);
	Vector3f position = new Vector3f(0, 0, 0);
	
	FloatBuffer matSpecular;
	FloatBuffer lightPosition;
	FloatBuffer whiteLight;
	FloatBuffer lModelAmbient;
	
	FloatBuffer vertexData;
	ByteBuffer  mapBuffer;
	
	int vboVertexHandle;
	
	final int amountOfVertices = 6 * 4;
	final int vertexSize       = 3;
	final int cubeSize         = amountOfVertices * vertexSize;
	
	final int chunkSize = cubeSize * 110000;
	
	private ArrayList<float[]> cubes = new ArrayList<float[]>();
	
	private int fps;
	
	private long oldTime, newTime;
	
	private int cubeWidth = 10;
	
	private float walkingSpeed = 10;
	
	private float speed = 0;
	
	public static void main(String[] argv)
	{
		Main displayExample = new Main();
		displayExample.start();
	}
	
	public void start()
	{
		try
		{
			Display.setDisplayMode(new DisplayMode(640,512));
			Display.setVSyncEnabled(true);
			Display.create();
		}
		catch (LWJGLException e)
		{
			e.printStackTrace();
			System.exit(0);
		}
		
//		glMatrixMode(GL_PROJECTION);
//		glLoadIdentity();
//		//glOrtho(0, 640, 512, 0, -1, 40);
//		gluPerspective((float) 30, 640f / 512f, 0.001f, -1);
//		glMatrixMode(GL_MODELVIEW);
		
		
		
		
//		glEnable(GL_TEXTURE_2D); // Enable Texture Mapping
//		glShadeModel(GL_SMOOTH); // Enable Smooth Shading
//		glClearColor(0.0f, 0.0f, 0.0f, 0.0f); // Black Background
//		glClearDepth(1.0); // Depth Buffer Setup
//		glEnable(GL_DEPTH_TEST); // Enables Depth Testing
//		glDepthFunc(GL_LEQUAL); // The Type Of Depth Testing To Do
//	
//		glMatrixMode(GL_PROJECTION); // Select The Projection Matrix
//		glLoadIdentity(); // Reset The Projection Matrix
//	
//		// Calculate The Aspect Ratio Of The Window
//		gluPerspective(45.0f, (float)Display.getWidth() / (float)Display.getHeight(), 0.01f, -1.0f);
//	//		glOrtho(1, 1, 1, 1, 1, -1);
//		glMatrixMode(GL_MODELVIEW); // Select The Modelview Matrix
//	
//		// Really Nice Perspective Calculations
//		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);
//		glPointSize(8.0f);
//		glLineWidth(2.0f);
		
		PrestoGL3D.initBasicView(0.01f, -1.0f);
		
		//glEnable(GL_CULL_FACE);
		
		
		
		//----------- Variables & method calls added for Lighting Test -----------//
//		initLightArrays();
//		glShadeModel(GL_SMOOTH);
//		glMaterial(GL_FRONT, GL_SPECULAR, matSpecular);				// sets specular material color
//		glMaterialf(GL_FRONT, GL_SHININESS, 50.0f);					// sets shininess
//		
//		glLight(GL_LIGHT0, GL_POSITION, lightPosition);				// sets light position
//		glLight(GL_LIGHT0, GL_SPECULAR, whiteLight);				// sets specular light to white
//		glLight(GL_LIGHT0, GL_DIFFUSE, whiteLight);					// sets diffuse light to white
//		glLightModel(GL_LIGHT_MODEL_AMBIENT, lModelAmbient);		// global ambient light 
//		
//		glEnable(GL_LIGHTING);										// enables lighting
//		glEnable(GL_LIGHT0);										// enables light0
//		
//		glEnable(GL_COLOR_MATERIAL);								// enables opengl to use glColor3f to define material color
//		glColorMaterial(GL_FRONT, GL_AMBIENT_AND_DIFFUSE);			// tell opengl glColor3f effects the ambient and diffuse properties of material
		//----------- END: Variables & method calls added for Lighting Test -----------//
		
		PrestoGL3D.initBasicLights();
		
		cubes.add(new float[chunkSize]);
		
		for (int i = 0; i < chunkSize / cubeSize; i ++)
		{
			cubes.set(0, PrestoGL3D.addCubeVertexArray((int)((Math.random() * 2000) - 1000) * 10, (int)((Math.random() * 200) - 100) * 10, (int)((Math.random() * 2000) - 1000) * 10, 10, i * cubeSize, cubes.get(0)));
		}
		
		vertexData = BufferUtils.createFloatBuffer(cubes.get(0).length);
		vertexData.put(cubes.get(0));
		vertexData.flip();
		
//		FloatBuffer colorData = BufferUtils.createFloatBuffer(amountOfVertices * colorSize);
//		colorData.put(new float[]{1, 0, 0, 0, 1, 0, 0, 0, 1});
//		colorData.flip();
		
		vboVertexHandle = glGenBuffers();
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
//		int vboColorHandle = glGenBuffers();
//		glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
//		glBufferData(GL_ARRAY_BUFFER, colorData, GL_STATIC_DRAW);
//		glBindBuffer(GL_ARRAY_BUFFER, 0);
		
		
		newTime = System.currentTimeMillis();
		oldTime = System.currentTimeMillis();
		 
		new FrameLoop().start(60, new FrameTask()
		{
			public void run()
			{
				updateFps();
				
	//			glBegin(GL_QUADS);
	//			
	//				glColor3f(1, 0, 0);
	//				
	//				glVertex3i(0, 0, -90);
	//				
	//				glColor3f(0, 0, 0);
	//				
	//				glVertex3i(10, 0, -90);
	//				
	//				glColor3f(0, 1, 0);
	//				
	//				glVertex3i(10, 10, -50);
	//				
	//				glColor3f(0, 0, 8);
	//				
	//				glVertex3i(0, 10, -50);
	//			
	//			glEnd();
				
	//			int cube = glGenLists(1);
	//			glNewList(cube, GL_COMPILE);
	//			
	//			PrestoGL.drawCube(10, 10, -100, 10);
	//			
	//			glEndList();
				
	//			for (Vector3f vec : cubes)
	//			{
	//				cube = glGenLists(1);
	//				glNewList(cube, GL_COMPILE);
	//				
	//				PrestoGL.drawCube(vec.x, vec.y, vec.z, 10);
	//				
	//				glEndList();
	//			}
				
				
	//			int maxLookDown = 80;
	//			int maxLookUp = -80;
				
				float mouseDX = Mouse.getDX() * 1 * 0.016f;
				float mouseDY = Mouse.getDY() * 1 * 0.016f;
				
	//////////////////////////////////////////////////////////////////////////
				if (Mouse.isGrabbed())
				{
					if (rotation.y + mouseDX >= 360)
					{
						rotation.y = rotation.y + mouseDX - 360;
					}
					else if (rotation.y + mouseDX < 0)
					{
						rotation.y = 360 - rotation.y + mouseDX;
					}
					else
					{
						rotation.y += mouseDX;
					}
					
	//////////////////////////////////////////////////////////////////////////
					
					if (rotation.x + -mouseDY >= 360)
					{
						rotation.x = rotation.x + -mouseDY - 360;
					}
					else if (rotation.x + -mouseDY < 0)
					{
						rotation.x = 360 - rotation.x + -mouseDY;
					}
					else
					{
						rotation.x += -mouseDY;
					}
				}
			  
	//////////////////////////////////////////////////////////////////////////
				
	//			if (rotation.x - mouseDY >= maxLookDown && rotation.x - mouseDY <= maxLookUp)
	//			{
	//				rotation.x += -mouseDY;
	//			}
	//			else if (rotation.x - mouseDY < maxLookDown)
	//			{
	//				rotation.x = maxLookDown;
	//			}
	//			else if (rotation.x - mouseDY > maxLookUp)
	//			{
	//				rotation.x = maxLookUp;
	//			}
				
				
				
				
				boolean keyUp = Keyboard.isKeyDown(Keyboard.KEY_UP) || Keyboard.isKeyDown(Keyboard.KEY_W);
				boolean keyDown = Keyboard.isKeyDown(Keyboard.KEY_DOWN) || Keyboard.isKeyDown(Keyboard.KEY_S);
				boolean keyLeft = Keyboard.isKeyDown(Keyboard.KEY_LEFT) || Keyboard.isKeyDown(Keyboard.KEY_A);
				boolean keyRight = Keyboard.isKeyDown(Keyboard.KEY_RIGHT) || Keyboard.isKeyDown(Keyboard.KEY_D);
				boolean flyUp = Keyboard.isKeyDown(Keyboard.KEY_SPACE);
				boolean flyDown = Keyboard.isKeyDown(Keyboard.KEY_LSHIFT);
				boolean moveFaster = Keyboard.isKeyDown(Keyboard.KEY_LCONTROL);
				boolean moveSlower = Keyboard.isKeyDown(Keyboard.KEY_TAB);
	
				if (!Mouse.isGrabbed())
				{
					keyUp      = false;
					keyDown    = false;
					keyLeft    = false;
					keyRight   = false;
					flyUp      = false;
					flyDown    = false;
					moveFaster = false;
					moveSlower = false;
					
					offset.x = 0;
					offset.y = 0;
					offset.z = 0;
					
					rotation.x = 0;
					rotation.y = 0;
					rotation.z = 0;
				}
				
	//			if (moveFaster && !moveSlower) {
	//				walkingSpeed *= 4f;
	//			}
	//			if (moveSlower && !moveFaster) {
	//				walkingSpeed /= 10f;
	//			}
	
				if (keyUp && keyRight && !keyLeft && !keyDown) {
					float angle = rotation.y + 45;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = (walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (keyUp && keyLeft && !keyRight && !keyDown) {
					float angle = rotation.y - 45;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = (walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (keyUp && !keyLeft && !keyRight && !keyDown) {
					float angle = rotation.y;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = (walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (keyDown && keyLeft && !keyRight && !keyUp) {
					float angle = rotation.y - 135;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = (walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (keyDown && keyRight && !keyLeft && !keyUp) {
					float angle = rotation.y + 135;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = (walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (keyDown && !keyUp && !keyLeft && !keyRight) {
					float angle = rotation.y;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = -(walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (keyLeft && !keyRight && !keyUp && !keyDown) {
					float angle = rotation.y - 90;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = (walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (keyRight && !keyLeft && !keyUp && !keyDown) {
					float angle = rotation.y + 90;
					Vector3f newPosition = new Vector3f(offset);
					float schuine = (walkingSpeed * 0.0002f) * 1;//delta;
					float aanliggende = schuine * (float) Math.cos(Math.toRadians(angle));
					float overstaande = (float) (Math.sin(Math.toRadians(angle)) * schuine);
					newPosition.z += aanliggende;
					newPosition.x -= overstaande;
					offset.z = newPosition.z;
					offset.x = newPosition.x;
				}
				if (flyUp && !flyDown) {
					double newPositionY = (walkingSpeed * 0.0002) * 1;//delta;
					offset.y -= newPositionY;
				}
				if (flyDown && !flyUp) {
					double newPositionY = (walkingSpeed * 0.0002) * 1;//delta;
					offset.y += newPositionY;
				}
				if (moveFaster && !moveSlower) {
					walkingSpeed /= 4f;
				}
				if (moveSlower && !moveFaster) {
					walkingSpeed *= 10f;
				}
				
				while (Mouse.next()) {
					if (Mouse.isButtonDown(0)) {
						Mouse.setGrabbed(true);
					}
					if (Mouse.isButtonDown(1)) {
						Mouse.setGrabbed(false);
					}
	
				}
				
				
	//			glBegin(GL_POINTS);
	//			
	//			glVertex3f(-21f, 0f, -81f);
	//			
	//			glEnd();
				
				if (Keyboard.isKeyDown(Keyboard.KEY_UP))
				{
					speed += 0.01f;
				}
				else if (Keyboard.isKeyDown(Keyboard.KEY_DOWN))
				{
					speed -= 0.01f;
				}
				
				while (Keyboard.next())
				{
					
				}
				
				if (Keyboard.isKeyDown(Keyboard.KEY_ESCAPE))
				{
					Display.destroy();
					System.exit(0);
				}
				
				float testx = (float)Math.cos(rotation.x) + offset.x;
	//			float testy = (float)Math.cos(rotation.y) + offset.y;
				float testz = (float)Math.cos(rotation.z) + offset.z;
				
	//			float testx = offset.x;
	//			float testy = offset.y;
	//			float testz = offset.z;
				
				glLoadIdentity();
				
				glRotatef(rotation.x, 1f, 0f, 0f);
				glRotatef(rotation.y, 0f, 1f, 0f);
				glRotatef(rotation.z, 0f, 0f, 1f);
				
				move(position.x, position.y, position.z);
				
				glTranslatef(offset.x, offset.y, offset.z);
				
				glEnableClientState(GL_VERTEX_ARRAY);
				
				glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
	            glVertexPointer(vertexSize, GL_FLOAT, 0, 0);
	            
	//            glBindBuffer(GL_ARRAY_BUFFER, vboColorHandle);
	//            glColorPointer(colorSize, GL_FLOAT, 0, 0L);
	            
	            
	//            glEnableClientState(GL_COLOR_ARRAY);
	            glDrawArrays(GL_QUADS, 0, cubes.get(0).length);
	//            glDisableClientState(GL_COLOR_ARRAY);
	            glDisableClientState(GL_VERTEX_ARRAY);
				
	//			moveCubes();
	//			
	//			rerenderCubes();
				
				if (mapBuffer == null)
				{
					rerenderCubes();
				}
			}
		}
		);
	}
	
	int loadTexture(String pathname)
	{
		BufferedImage img = null;
		try
		{
			img = ImageIO.read(new File(pathname));
		}
		catch(IOException e)
		{
			throw new RuntimeException(e);
		}
		
		byte[] src = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
		byte temp;
		
		for(int i = 0x00000000; i < src.length; i += 0x00000003) {
			temp = src[i];
			src[i] = src[i+0x00000002];
			src[i+0x00000002] = temp;
		}
		
		ByteBuffer pixels = (ByteBuffer)BufferUtils.createByteBuffer(src.length).put(src, 0x00000000, src.length).flip();
		IntBuffer textures = BufferUtils.createIntBuffer(0x00000001);
		
		glGenTextures(textures);
		glBindTexture(GL_TEXTURE_2D, textures.get(0x00000000));
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
		glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);
		glTexImage2D(GL_TEXTURE_2D, 0x00000000, GL_RGB, img.getWidth(), img.getHeight(), 0x00000000, GL_RGB, GL_UNSIGNED_BYTE, pixels);
		
		return textures.get(0x00000000);
	}
	
	public void move(float x, float y, float z)
	{
		position.x += x;
		position.y += y;
		position.z += z;
	}
	

	//------- Added for Lighting Test----------//
	private void initLightArrays()
	{
		matSpecular = BufferUtils.createFloatBuffer(4);
		matSpecular.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		
		lightPosition = BufferUtils.createFloatBuffer(4);
		lightPosition.put(1.0f).put(1.0f).put(1.0f).put(0.0f).flip();
		
		whiteLight = BufferUtils.createFloatBuffer(4);
		whiteLight.put(1.0f).put(1.0f).put(1.0f).put(1.0f).flip();
		
		lModelAmbient = BufferUtils.createFloatBuffer(4);
		lModelAmbient.put(0.5f).put(0.5f).put(0.5f).put(1.0f).flip();
	}
	
	public void moveCube(float x, float y, float z)
	{
		cubes.set(0, PrestoGL3D.moveCubeArray(0, 0, -10, 10, 0, cubes.get(0)));
		
		vertexData = BufferUtils.createFloatBuffer(cubes.size());
		vertexData.put(cubes.get(0));
		vertexData.flip();
		
		glBindBuffer(GL_ARRAY_BUFFER, vboVertexHandle);
		glBufferData(GL_ARRAY_BUFFER, vertexData, GL_DYNAMIC_DRAW);
		glBindBuffer(GL_ARRAY_BUFFER, 0);
	}
	
	public void rerenderCubes()
	{
		mapBuffer = glMapBuffer(GL_ARRAY_BUFFER, GL_WRITE_ONLY, mapBuffer);
		
		vertexData = mapBuffer.order(ByteOrder.nativeOrder()).asFloatBuffer();
		
		vertexData.put(cubes.get(0));
		
		glUnmapBuffer(GL_ARRAY_BUFFER);
	}
	
	public void moveCubes()
	{
		for (int i = 0; i < cubes.get(0).length / cubeSize; i ++)
		{
			float d[] = PrestoGL3D.getCubeArray((int)((Math.random() * 2000) - 1000) * 10, (int)((Math.random() * 200) - 100) * 10, (int)((Math.random() * 2000) - 1000) * 10, 10);
			
			for (int j = 0; j < 72; j ++)
			{
				cubes.get(0)[(i * 72) + j] = d[j];
			}
		}
	}
	
	private void updateFps()
	{
		fps ++;
		
		newTime = System.currentTimeMillis();
		
		if (newTime >= oldTime + 1000)
		{
			Display.setTitle(" FPS: " + fps);
			
			oldTime = newTime;
			
			fps = 0;
		}
		
		if (mapBuffer != null)// && fps % 60 == 0)
		{
//			moveCubes();
//			
//			rerenderCubes();
		}
	}
	
//	public void addCube(float x, float y, float z, int index )
//	{
//		
//		
//		cubes.set(0, PrestoGL.addCubeVertexArray((int)((Math.random() * 400) - 200) * cubeWidth, (int)((Math.random() * 20) - 10) * cubeWidth, (int)((Math.random() * 400) - 200) * cubeWidth, cubeWidth, cubeSize, cubes.get(0)));
//	}
	
//	private float[] toFloatArray(ArrayList<Float> floats)
//	{
//	    float[] floats2 = new float[floats.size()];
//	    int i = 0;
//	    for (Float n : floats) {
//	        floats2[i++] = n;
//	    }
//	    return floats2;
//	}
}