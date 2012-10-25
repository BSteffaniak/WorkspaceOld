package net.foxycorndog.jdoogl.model;

public class Model
{
	public Model(String location)
	{
		File file = getFile(location);
		
		setValues(file);
	}
	
	public Model(String location, Class clazz)
	{
		File file = getFile(location);

		setValues(file);
	}
	
	public Model(File file)
	{
		setValues(file);
	}
	
	private void setValues(File file)
	{
		
	}
	
	private static File getFile(String location)
	{
		File file = null;

		try
		{
			file = new File(location);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
			System.exit(1);
		}
		
		return file;
	}
}
