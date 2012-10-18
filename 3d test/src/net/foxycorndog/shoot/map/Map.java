package net.foxycorndog.shoot.map;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Map
{
//	private Texture        texture;
	
	private SpriteSheet    sprites;
	
	private LightBuffer    texturesBuffer, colorsBuffer;
	
	private VerticesBuffer verticesBuffer;
	
	private float          vertices[];
	
	public Map()
	{
		sprites = new SpriteSheet("res/images/sprites.png", 36, 18);
		
		vertices = new float[4 * 3 * 6 * 2];
		
		verticesBuffer = new VerticesBuffer(4 * 3 * 6 * 2);
		texturesBuffer = new LightBuffer(2 * 4 * 6 * 2);
		colorsBuffer   = new LightBuffer(4 * 4 * 6 * 2);
		
		GL.addCubeVertexArrayf(0, 2.5f, -10, 2, 2, 2, 0, vertices);
		GL.addCubeVertexArrayf(-100, -2, -100, 200, 2, 200, 4 * 3 * 6, vertices);
		
		verticesBuffer.addData(vertices);
		texturesBuffer.addData(GL.addCubeTextureArrayf(new float[][] { sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(1, 0, 1, 1), sprites.getImageOffsetsf(7, 2, 1, 1), sprites.getImageOffsetsf(2, 0, 1, 1),  }, 0, null));

		int r = 255;
		int g = 255;
		int b = 255;
		int a = 255;
		colorsBuffer.addData(GL.addCubeColorArrayif(
				new int[][]
				{
					new int[] { 255, 255, 255, a },
					new int[] { 255, 255, 255, a },
					new int[] { 255, 255, 255, a }, 
					new int[] { 255, 255, 255, a },
					new int[] { 151, 255, 100, a },
					new int[] { 255, 255, 255, a }
				} , 0, null));
		
		texturesBuffer.addData(GL.addCubeTextureArrayf(GL.white, 0, null));
		
		r = 200;
		g = 200;
		b = 200;
		a = 255;
		colorsBuffer.addData(GL.addCubeColorArrayif(
				new int[][]
				{
					new int[] { r, g, b, a },
					new int[] { r, g, b, a },
					new int[] { r, g, b, a }, 
					new int[] { r, g, b, a },
					new int[] { r, g, b, a },
					new int[] { r, g, b, a }
				} , 0, null));
	}
	
	public void render()
	{
		GL.beginColorDraw(colorsBuffer);
		
		GL.renderCubes(verticesBuffer, texturesBuffer, sprites, 0, 1);
		
		GL.renderCubes(verticesBuffer, texturesBuffer, GL.white, 1, 1);

		GL.endColorDraw();
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
}