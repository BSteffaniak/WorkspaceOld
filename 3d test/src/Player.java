import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.camera.Camera;
import net.foxycorndog.jdoutil.Buffer;

import java.nio.FloatBuffer;

/**
 * We use keyboard and mouse input values to build a view matrix which gets
 * loaded instead of the existing modelview matrix. We also attach a simple
 * white square which moves with the camera as if it were a gun.
 */
public class Player extends Camera
{
	private int width, height, depth;
	
	public Player(int width, int height, int depth)
	{
		this.width  = width;
		this.height = height;
		this.depth  = depth;
	}
	
	public boolean collided(float vertices[])
	{
		for (int i = 0; i < vertices.length; i += 3 * 4 * 6)
		{
			float minX1 = super.getLocation()[0];
			float minY1 = super.getLocation()[1];
			float minZ1 = super.getLocation()[2];
			
			float maxX1 = super.getLocation()[0] + width;
			float maxY1 = super.getLocation()[1] + height;
			float maxZ1 = super.getLocation()[2] + depth;
			

			float minX2 = vertices[i];
			float minY2 = vertices[i + 1];
			float minZ2 = vertices[i + 2];
			
			float maxX2 = vertices[i + 12];
			float maxY2 = vertices[i + 4];
			float maxZ2 = vertices[i + 20];
			
			if ((maxX1 >= minX2 && minX1 <= maxX2) && (maxY1 >= minY2 && minY1 <= maxY2) && (maxZ1 >= minZ2 && minZ1 <= maxZ2))
			{
				return true;
			}
		}
		
		return false;
	}
}