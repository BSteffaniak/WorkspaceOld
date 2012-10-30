package net.foxycorndog.nostalgia.actor;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoutil.Buffer;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.nostalgia.map.Map;

import java.nio.FloatBuffer;

/**
 * We use keyboard and mouse input values to build a view matrix which gets
 * loaded instead of the existing modelview matrix. We also attach a simple
 * white square which moves with the camera as if it were a gun.
 */
public class Player extends Actor
{
	public Player(float width, float height, float depth, float centerX, float centerY, float centerZ, Map map)
	{
		super(width, height, depth, centerX, centerY, centerZ, map);
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.setColori(25, 25, 25, 255);
			
			GL.translatef(getX(), getY(), getZ());
			
			if (getCamera() != null)
			{
				GL.translatef(getCenterX(), getCenterY(), getCenterZ());
				
				GL.rotatef(0, -getCamera().getYaw(), 0);
				
				GL.translatef(-getCenterX(), -getCenterY(), -getCenterZ());
			}
			
			getModel().render();
		}
		GL.endManipulation();
	}
	
	public void update()
	{
		super.update();
	}
}