package net.foxycorndog.minecraftcopy.entities;

import org.lwjgl.util.vector.Vector3f;

import net.foxycorndog.minecraftcopy.MinecraftCopy;
import net.foxycorndog.minecraftcopy.Player;
import net.foxycorndog.presto2d.PrestoGL2D;
import net.foxycorndog.presto3d.graphics.PrestoGL3D;
import net.foxycorndog.presto3d.util.volume.Volume3f;

import org.newdawn.slick.opengl.Texture;

public class Chunk
{
	private float     vertices[], normals[], textures[];
	
	private int       rx, ry, rz;
	private int       amountOfCubes;
	
	private MinecraftCopy mc;
	
	private final int amountOfVertices = 6 * 4;
	private final int vertexSize       = 3;
	private final int cubeSize         = amountOfVertices * vertexSize;
	private final int chunkSize        = cubeSize * 16 * 16 * 16;
	
	public Chunk(int rx, int ry, int rz, MinecraftCopy mc)
	{
		this.rx = rx;
		this.ry = ry;
		this.rz = rz;
		
		this.mc = mc;
		
		vertices = new float[chunkSize];
		normals  = new float[chunkSize];
		textures = new float[chunkSize];
	}
	
	public void addCube(int x, int y, int z)//, Texture texture)
	{
		int offset = x + (y * 16) + (z * 16 * 16);
		
		vertices = PrestoGL3D.addCubeVertexArray(getOriginRelativeX() + (x * 10), getOriginRelativeY() + (y * 10), getOriginRelativeZ() + (z * 10), 10, offset * cubeSize, vertices);
		normals  = PrestoGL3D.addCubeNormalArray(getOriginRelativeX() + (x * 10), getOriginRelativeY() + (y * 10), getOriginRelativeZ() + (z * 10), 10, offset * cubeSize, normals);
		
		for (int i = 0; i < 6; i ++)
		{
			textures = PrestoGL2D.addRectTextureArray(mc.getSprites(), 1, 2, 1, 1, offset * amountOfVertices * 2 + (i * 8) , textures);
		}
		
		amountOfCubes ++;
	}
	
	public boolean collided(Object o)
	{
		if (o instanceof Player)
		{
			Player p = (Player)o;
			
			for (int i = 0; i < vertices.length / cubeSize; i ++)
			{
				if (collided(vertices[i * cubeSize + 0], vertices[i * cubeSize + 1], vertices[i * cubeSize + 2], p.getX(), p.getY(), p.getZ(), 10, 10, 10, p.getWidth(), p.getHeight(), p.getLength()))
				{
					return true;
				}
			}
		}
		
		return false;
	}
	
	private boolean collided(float x1, float y1, float z1, float x2, float y2, float z2, float width1, float height1, float length1, float width2, float height2, float length2)
	{
		if (((x1 > x2 && x1 < x2 + width2) || x2 > x1 && x2 < x1 + width1) &&
				((y1 > y2 && y1 < y2 + height2) || y2 > y1 && y2 < y1 + height1) &&
				((z1 > z2 && z1 < z2 + length2) || z2 > z1 && z2 < z1 + length1))
		{
			return true;
		}
		
		return false;
	}
	
	public float getOriginRelativeX()
	{
		return rx * 16 * 10;
	}
	
	public float getOriginRelativeY()
	{
		return ry * 16 * 10;
	}
	
	public float getOriginRelativeZ()
	{
		return rz * 16 * 10;
	}
	
	public int getAmountOfCubes()
	{
		return amountOfCubes;
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
	
	public float[] getNormals()
	{
		return normals;
	}
	
	public float[] getTextures()
	{
		return textures;
	}
}