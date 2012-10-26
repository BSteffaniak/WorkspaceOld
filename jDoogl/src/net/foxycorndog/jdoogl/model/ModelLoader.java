package net.foxycorndog.jdoogl.model;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class ModelLoader
{
	public static Object[] loadModel(File file, boolean vertices, boolean textures, boolean normals, boolean colors, boolean vertexIndices, boolean normalIndices, float scale)
	{
		BufferedReader br = null;
		
		float verts[] = null;
		ArrayList<Float> v = null;
		if (vertices)
		{
			v = new ArrayList<Float>();
		}

		float texts[] = null;
		ArrayList<Float> t = null;
		if (textures)
		{
			t = new ArrayList<Float>();
		}

		float norms[] = null;
		ArrayList<Float> n = null;
		if (normals)
		{
			n = new ArrayList<Float>();
		}

		float cols[] = null;
		ArrayList<Float> c = null;
		if (colors)
		{
			c = new ArrayList<Float>();
		}

		short vertsInds[] = null;
		ArrayList<Short> vi = null;
		if (vertexIndices)
		{
			vi = new ArrayList<Short>();
		}

		short normsInds[] = null;
		ArrayList<Short> ni = null;
		if (normalIndices)
		{
			ni = new ArrayList<Short>();
		}
		
		try
		{
			br = new BufferedReader(new FileReader(file));
		
			String line = "";
			
			while ((line = br.readLine()) != null)
			{
				if (vertices && line.startsWith("v "))
				{
					v.add(Float.valueOf(line.split(" ")[1]));
					v.add(Float.valueOf(line.split(" ")[2]));
					v.add(Float.valueOf(line.split(" ")[3]));
				}
				else if (normals && line.startsWith("vn "))
				{
					n.add(Float.valueOf(line.split(" ")[1]));
					n.add(Float.valueOf(line.split(" ")[2]));
					n.add(Float.valueOf(line.split(" ")[3]));
				}
				else if (line.startsWith("f "))
				{
					if (vertexIndices)
					{
						vi.add(Short.valueOf(line.split(" ")[1].split("/")[0]));
						vi.add(Short.valueOf(line.split(" ")[2].split("/")[0]));
						vi.add(Short.valueOf(line.split(" ")[3].split("/")[0]));
					}
					if (normalIndices)
					{
						ni.add(Short.valueOf(line.split(" ")[1].split("/")[2]));
						ni.add(Short.valueOf(line.split(" ")[2].split("/")[2]));
						ni.add(Short.valueOf(line.split(" ")[3].split("/")[2]));
					}
				}
			}
		
			br.close();
			
			if (vertices)
			{
				verts = new float[v.size()];
				
				for (int i = 0; i < v.size(); i ++)
				{
					verts[i] = v.get(i) * scale;
				}
			}
			if (textures)
			{
				texts = new float[t.size()];
				
				for (int i = 0; i < t.size(); i ++)
				{
					texts[i] = t.get(i);
				}
			}
			if (normals)
			{
				norms = new float[n.size()];
				
				for (int i = 0; i < n.size(); i ++)
				{
					norms[i] = n.get(i);
				}
			}
			if (colors)
			{
				cols = new float[c.size()];
				
				for (int i = 0; i < c.size(); i ++)
				{
					cols[i] = c.get(i);
				}
			}
			if (vertexIndices)
			{
				vertsInds = new short[vi.size()];
				
				for (int i = 0; i < vi.size(); i ++)
				{
					vertsInds[i] = (short)(vi.get(i) - 1);
				}
			}
			if (normalIndices)
			{
				normsInds = new short[ni.size()];
				
				for (int i = 0; i < ni.size(); i ++)
				{
					normsInds[i] = (short)(ni.get(i) - 1);
				}
			}
			
//			if (vertices)
//			{
//				verts2 = new float[vertsInds.length * 3];
//				
//				for (int i = 0; i < vertsInds.length; i ++)
//				{
//					verts2[i * 3 + 0] = verts[((int)vertsInds[i] - 1) * 3];
//					verts2[i * 3 + 1] = verts[((int)vertsInds[i] - 1) * 3 + 1];
//					verts2[i * 3 + 2] = verts[((int)vertsInds[i] - 1) * 3 + 2];
//				}
//			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		Object g[] = new Object[] {verts};
		
		return new Object[] { verts, texts, norms, cols, vertsInds, normsInds };
	}
	
	public static Object[] loadModel(String location, boolean vertices, boolean textures, boolean normals, boolean colors, boolean vertexIndices, boolean normalIndices)
	{
		return loadModel(location, vertices, textures, normals, colors, vertexIndices, normalIndices, 1);
	}
	
	public static Object[] loadModel(String location, boolean vertices, boolean textures, boolean normals, boolean colors, boolean vertexIndices, boolean normalIndices, float scale)
	{
		File file = new File(location);
		
		return loadModel(file, vertices, textures, normals, colors, vertexIndices, normalIndices, scale);
	}
}