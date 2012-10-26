package net.foxycorndog.nostalgia.map;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.model.Model;
import net.foxycorndog.jdoogl.model.ModelLoader;
import net.foxycorndog.jdoutil.ArrayUtil;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.Task;
import net.foxycorndog.jdoutil.Vector;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.nostalgia.actor.Actor;

public class Map
{
	private int            numCubes;
	
	private SpriteSheet    sprites;
	
	private Model          bunny;
	
	private LightBuffer    texturesBuffer, colorsBuffer;
	
	private VerticesBuffer verticesBuffer;
	public  int            render = GL.POINTS;
	private float          cubes[], allVertices[], vertices[], textures[], normals[], colors[], normalIndices[];
	private short          vertexIndices[];
	
	public Map()
	{
		numCubes = 2;
		
		sprites  = new SpriteSheet("res/images/sprites.png", 36, 18);
		
		cubes    = new float[6 * numCubes];
		
		verticesBuffer = new VerticesBuffer(4 * 3 * 6 * numCubes, 3);
		texturesBuffer = new LightBuffer(2 * 4 * 6 * numCubes);
		colorsBuffer   = new LightBuffer(4 * 4 * 6 * numCubes);
		
		int index = 0;
		
		addCube(0, 2.5f, -10, 2, 2, 2,
				new float[][]
				{
					sprites.getImageOffsetsf(1, 0, 1, 1),
					sprites.getImageOffsetsf(1, 0, 1, 1),
					sprites.getImageOffsetsf(1, 0, 1, 1),
					sprites.getImageOffsetsf(1, 0, 1, 1),
					sprites.getImageOffsetsf(7, 2, 1, 1),
					sprites.getImageOffsetsf(2, 0, 1, 1),
				},
				new int[][]
				{
					new int[] { 255, 255, 255, 255 },
					new int[] { 255, 255, 255, 255 },
					new int[] { 255, 255, 255, 255 }, 
					new int[] { 255, 255, 255, 255 },
					new int[] { 151, 255, 100, 255 },
					new int[] { 255, 255, 255, 255 }
				}, index ++);
		
		addCube(-100, -2, -100, 200, 2, 200, GL.white, 200, 200, 200, 255, index ++);
		
		bunny = new Model("res/bunny.obj", 20);
	}
	
	public void addCube(float x, float y, float z, float width, float height, float depth, float textures[][], int colors[][], int index)
	{
		verticesBuffer.addData(GL.addCubeVertexArrayf(x, y, z, width, height, depth, 0, null));
		texturesBuffer.addData(GL.addCubeTextureArrayf(textures, 0, null));
		colorsBuffer.addData(GL.addCubeColorArrayif(colors , 0, null));
		
		cubes[0 + index * 6] = x;
		cubes[1 + index * 6] = y;
		cubes[2 + index * 6] = z;
		
		cubes[3 + index * 6] = x + width;
		cubes[4 + index * 6] = y + height;
		cubes[5 + index * 6] = z + depth;
	}
	
	public void addCube(float x, float y, float z, float width, float height, float depth, float textures[][], int r, int g, int b, int a, int index)
	{
		int colors[][] = new int[][]
		{
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a }
		};
		
		addCube(x, y, z, width, height, depth, textures, colors, index);
	}
	
	public void addCube(float x, float y, float z, float width, float height, float depth, Texture texture, int r, int g, int b, int a, int index)
	{
		int colors[][] = new int[][]
		{
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a },
			{ r, g, b, a }
		};
		
		float textures[][] = new float[][]
		{
			texture.getImageOffsetsf(),
			texture.getImageOffsetsf(),
			texture.getImageOffsetsf(),
			texture.getImageOffsetsf(),
			texture.getImageOffsetsf(),
			texture.getImageOffsetsf(),
		};
		
		addCube(x, y, z, width, height, depth, textures, colors, index);
	}
	
	public boolean collided(Actor actor)
	{
		float vertices[] = actor.getVertices();
		
		if (bunny.collision(vertices, new Vector(actor.getX(), actor.getY(), actor.getZ())))
		{
			return true;
		}
		
		return false;
	}
	
	public void render()
	{
		bunny.render();
		
		GL.renderCubes(verticesBuffer, texturesBuffer, null, colorsBuffer, sprites, 0, 1, null);
		
		GL.renderCubes(verticesBuffer, texturesBuffer, colorsBuffer, GL.white, 1, 1);
	}
	
	public float[] getCubes()
	{
		return cubes;
	}
	
	public void genIndices()
	{
		verticesBuffer.genIndices(GL.QUADS);
	}
	
	public void destroyIndices()
	{
		verticesBuffer.destroyIndices();
	}
}