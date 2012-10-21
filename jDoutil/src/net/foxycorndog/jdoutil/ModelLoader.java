package net.foxycorndog.jdoutil;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

public class ModelLoader
{
	public static float[][] loadModel(File file, boolean vertices, boolean textures, boolean normals, boolean colors, boolean vertexIndices, boolean normalIndices)
	{
		BufferedReader br = null;
		
		float verts[] = null;
		ArrayList<Float> v = null;
		if (vertices)
		{
			v = new ArrayList<Float>();
		}
		
		ArrayList<Float> t = null;
		if (textures)
		{
			t = new ArrayList<Float>();
		}
		
		ArrayList<Float> n = null;
		if (normals)
		{
			n = new ArrayList<Float>();
		}
		
		ArrayList<Float> c = null;
		if (colors)
		{
			c = new ArrayList<Float>();
		}
		
		ArrayList<Float> vi = null;
		if (vertexIndices)
		{
			vi = new ArrayList<Float>();
		}
		
		ArrayList<Float> ni = null;
		if (normalIndices)
		{
			new ArrayList<Float>();
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
						vi.add(Float.valueOf(line.split(" ")[1].split("/")[0]));
						vi.add(Float.valueOf(line.split(" ")[2].split("/")[0]));
						vi.add(Float.valueOf(line.split(" ")[3].split("/")[0]));
					}
					if (normalIndices)
					{
						ni.add(Float.valueOf(line.split(" ")[1].split("/")[2]));
						ni.add(Float.valueOf(line.split(" ")[2].split("/")[2]));
						ni.add(Float.valueOf(line.split(" ")[3].split("/")[2]));
					}
				}
			}
		
			br.close();
			
			if (vertices)
			{
				verts = new float[v.size()];
				
				for (int i = 0; i < v.size(); i ++)
				{
					verts[i] = v.get(i);
				}
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		return new float[][] { verts };
	}
	
	public static float[][] loadModel(String location)
	{
		File file = new File(location);
		
		return loadModel(file, true, false, false, false, false, false);
	}
}