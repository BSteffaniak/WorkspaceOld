package net.foxycorndog.jdoogl.model;

import java.io.File;
import java.net.URISyntaxException;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.geometry.Point;
import net.foxycorndog.jdoogl.geometry.Vector;
import net.foxycorndog.jdoutil.ArrayUtil;
import net.foxycorndog.jdoutil.Intersects;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.VerticesBuffer;

public class Model
{
	private float          x, y, z;
	private float          width, height, depth;
	
	private LightBuffer    texturesBuffer, normalsBuffer, colorsBuffer;
	
	private VerticesBuffer verticesBuffer;//, allVerticesBuffer;
	
	private short          vertexIndices[], normalIndices[];
	
	private float          /*allVertices[], */textures[], normals[], faceNormals[], colors[], vertices[];
	
	public Model(String location)
	{
		this(location, 1);
	}
	
	public Model(String location, float scale)
	{
		File file = new File(location);
		
		setValues(file, scale);
	}
	
	public Model(String location, Class clazz)
	{
		this(location, clazz, 1);
	}
	
	public Model(String location, Class clazz, float scale)
	{
		File file = null;
		
		try
		{
			file = new File(clazz.getClassLoader().getResource(location).toURI());
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}

		setValues(file, scale);
	}
	
	public Model(File file)
	{
		this(file, 1);
	}
	
	public Model(File file, float scale)
	{
		setValues(file, scale);
	}
	
	public Model(float vertices[], float textures[], float normals[], float colors[], short vertexIndices[], short normalIndices[])
	{
		verticesBuffer = new VerticesBuffer(vertices.length, 3);
		verticesBuffer.addData(vertices);
		
		if (vertexIndices == null)
		{
			verticesBuffer.genIndices(GL.QUADS, null);
			
			vertexIndices = new short[verticesBuffer.getIndices().capacity()];
			
			verticesBuffer.getIndices().get(vertexIndices);
		}
		
		if (normals == null)
		{
//			for (int i = 0; i < vertexIndices.length; i ++)
//			{
//				Vector v1 = new Vector(vertices[vertexIndices[i + 0] + 0] - vertices[vertexIndices[i + 1] + 0], vertices[vertexIndices[i + 0] + 1] - vertices[vertexIndices[i + 1] + 1], vertices[vertexIndices[i + 0] + 2] - vertices[vertexIndices[i + 1] + 2]);
//				Vector v2 = new Vector(vertices[vertexIndices[i + 2] + 0] - vertices[vertexIndices[i + 0] + 0], vertices[vertexIndices[i + 2] + 1] - vertices[vertexIndices[i + 0] + 1], vertices[vertexIndices[i + 2] + 2] - vertices[vertexIndices[i + 0] + 2]);
//				
//				Vector cross = Vector.crossProduct(v1, v2);
//				
//				cross.normalize();
//				
//				faceNormals[(i / 3) / 3 + 0] = cross.getX();
//				faceNormals[(i / 3) / 3 + 1] = cross.getY();
//				faceNormals[(i / 3) / 3 + 2] = cross.getZ();
//			}
		}
		
		faceNormals = new float[vertices.length / 3];
		
		for (int i = 0; i < vertexIndices.length; i += 3)
		{
			Vector v1 = new Vector(vertices[vertexIndices[i + 0] + 0] - vertices[vertexIndices[i + 1] + 0], vertices[vertexIndices[i + 0] + 1] - vertices[vertexIndices[i + 1] + 1], vertices[vertexIndices[i + 0] + 2] - vertices[vertexIndices[i + 1] + 2]);
			Vector v2 = new Vector(vertices[vertexIndices[i + 2] + 0] - vertices[vertexIndices[i + 0] + 0], vertices[vertexIndices[i + 2] + 1] - vertices[vertexIndices[i + 0] + 1], vertices[vertexIndices[i + 2] + 2] - vertices[vertexIndices[i + 0] + 2]);
			
			Vector cross = Vector.crossProduct(v1, v2);
			
			cross.normalize();
			
			faceNormals[(i / 3) / 3 + 0] = cross.getX();
			faceNormals[(i / 3) / 3 + 1] = cross.getY();
			faceNormals[(i / 3) / 3 + 2] = cross.getZ();
		}
		
		this.vertices      = vertices;
		this.textures      = textures;
		this.normals       = normals;
		this.colors        = colors;
		
		this.vertexIndices = vertexIndices;
		this.normalIndices = normalIndices;
	}
	
	private void setValues(File file, float scale)
	{
		Object data[] = ModelLoader.loadModel(file, true, true, true, true, scale);
		
		vertices      = (float[])data[0];
		textures      = (float[])data[1];
		normals       = (float[])data[2];
		colors        = (float[])data[3];
		vertexIndices = (short[])data[4];
		normalIndices = (short[])data[5];
		
//		allVertices   = ArrayUtil.removeIndices(vertices, vertexIndices);
		
		verticesBuffer = new VerticesBuffer(vertices.length, 3);
		verticesBuffer.addData(vertices);
		verticesBuffer.setIndices(vertexIndices);
		
		normalsBuffer = new LightBuffer(normals.length);
		normalsBuffer.addData(normals);
		
//		allVerticesBuffer = new VerticesBuffer(allVertices.length, 3);
//		allVerticesBuffer.addData(allVertices);
		
		faceNormals = new float[normalIndices.length / 3];
		
		for (int i = 0; i < vertexIndices.length; i += 3)
		{
			Vector v1 = new Vector(vertices[vertexIndices[i + 0] + 0] - vertices[vertexIndices[i + 1] + 0], vertices[vertexIndices[i + 0] + 1] - vertices[vertexIndices[i + 1] + 1], vertices[vertexIndices[i + 0] + 2] - vertices[vertexIndices[i + 1] + 2]);
			Vector v2 = new Vector(vertices[vertexIndices[i + 2] + 0] - vertices[vertexIndices[i + 0] + 0], vertices[vertexIndices[i + 2] + 1] - vertices[vertexIndices[i + 0] + 1], vertices[vertexIndices[i + 2] + 2] - vertices[vertexIndices[i + 0] + 2]);
			
			Vector cross = Vector.crossProduct(v1, v2);
			
			cross.normalize();
			
			faceNormals[(i / 3) / 3 + 0] = cross.getX();
			faceNormals[(i / 3) / 3 + 1] = cross.getY();
			faceNormals[(i / 3) / 3 + 2] = cross.getZ();
		}
		
		float x, y, z, maxX, maxY, maxZ;
		
		x = vertices[0];
		y = vertices[1];
		z = vertices[2];
		
		maxX = x;
		maxY = y;
		maxZ = z;
		
		for (int i = 3; i < vertices.length; i += 3)
		{
			if (vertices[i + 0] < x)
			{
				x = vertices[i + 0];
			}
			else if (vertices[i + 0] > maxX)
			{
				maxX = vertices[i + 0];
			}
			
			if (vertices[i + 1] < y)
			{
				y = vertices[i + 1];
			}
			else if (vertices[i + 1] > maxY)
			{
				maxY = vertices[i + 1];
			}
			
			if (vertices[i + 2] < z)
			{
				z = vertices[i + 2];
			}
			else if (vertices[i + 2] > maxZ)
			{
				maxZ = vertices[i + 2];
			}
		}
		
		this.x      = x;
		this.y      = y;
		this.z      = z;
		
		this.width  = maxX - x;
		this.height = maxY - y;
		this.depth  = maxZ - z;
	}
	
	public boolean inside(float x, float y, float z)
	{
		Vector vec = new Vector(this.x - x, this.y - y, this.z - z);
		
		if (Intersects.cubes(x, y, z, 0, 0, 0, this.x, this.y, this.z, width, height, depth))
		{
			if (true){return true;};
			int counter = 0;
			
			for (int i = 0; i < faceNormals.length; i += 3)
			{
				if (vec.dotProduct(faceNormals[i + 0], faceNormals[i + 1], faceNormals[i + 2]) < 0)
				{
					float p1x = vertexIndices[i * 3 + 0 + 0];
					float p1y = vertexIndices[i * 3 + 1 + 0];
					float p1z = vertexIndices[i * 3 + 2 + 0];
					
					float p2x = vertexIndices[i * 3 + 0 + 3];
					float p2y = vertexIndices[i * 3 + 1 + 3];
					float p2z = vertexIndices[i * 3 + 2 + 3];
					
					float p3x = vertexIndices[i * 3 + 0 + 6];
					float p3y = vertexIndices[i * 3 + 1 + 6];
					float p3z = vertexIndices[i * 3 + 2 + 6];
					
					// Check to see if intersection is out of bounds.
					if (false)
					{
						continue;
					}
					
					counter ++;
				}
			}
			
			if (counter % 2 != 0)
			{
				return true;
			}
		}
		
		return false;
	}
	
	public boolean collision(float vertices[], Point offset)
	{
		for (int i = 0; i < vertices.length; i += 3)
		{
			if (inside(vertices[i + 0] + offset.getX(), vertices[i + 1] + offset.getY(), vertices[i + 2] + offset.getZ()))
			{
				return true;
			}
		}
		
//		for (int i = 0; i < this.vertices.length; i += 3)
//		{
//			if (inside(vertices[i + 0] + offset.getX(), vertices[i + 1] + offset.getY(), vertices[i + 2] + offset.getZ()))
//			{
//				return true;
//			}
//		}
		
		return false;
	}
	
	public void render()
	{
		GL.white.bind();
		
		GL.beginManipulation();
		{
			GL.translatef(x, y, z);
			
//			if (GL.DRAW_MODE_ELEMENTS)
//			{
				GL.renderTriangles(verticesBuffer, null, normalsBuffer, null, null, 0, vertexIndices.length / 3, null);
//			}
//			else
//			{
//				GL.renderTriangles(allVerticesBuffer, null, null, null, null, 0, allVertices.length / 3 / 3, null);
//			}
		}
		GL.endManipulation();
	}
	
	public boolean move(float dx, float dy, float dz)
	{
		x += dx;
		y += dy;
		z += dz;
		
		return true;
	}
	
	public float[] getVertices()
	{
		return vertices;
	}
	
	public float[] getTextures()
	{
		return textures;
	}
	
	public float[] getNormals()
	{
		return normals;
	}
	
	public float[] getColors()
	{
		return colors;
	}
	
	public short[] getVertexIndices()
	{
		return vertexIndices;
	}
	
	public short[] getNormalIndices()
	{
		return normalIndices;
	}
}
