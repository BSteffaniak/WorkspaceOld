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
	private int     perspective;
	
	public  static final int   THIRD = 3, FIRST = 1;
	
	public Player(float width, float height, float depth, float centerX, float centerY, float centerZ, Map map)
	{
		super(width, height, depth, centerX, centerY, centerZ, map);
		
		setPerspective(THIRD);
	}
	
	public void render()
	{
		GL.beginManipulation();
		{
			GL.setColori(25, 25, 25, 255);
			
			GL.translatef(-getOffsetX() + getX(), -getOffsetY() + getY(), -getOffsetZ() + getZ());
			
			if (getCamera() != null)
			{
				GL.translatef(getCenterX(), getCenterY(), getCenterZ());
				
				GL.rotatef(0, -getCamera().getYaw(), 0);
				
				GL.translatef(-getCenterX(), -getCenterY(), -getCenterZ());
			}
			
			GL.renderCubes(getVerticesBuffer(), 0, 1);
		}
		GL.endManipulation();
	}
	
	public void update()
	{
		super.update();
	}
	
	public int getPerspective()
	{
		return perspective;
	}
	
	public void setPerspective(int perspective)
	{
		if (this.perspective == perspective)
		{
			return;
		}
		
		if (perspective == THIRD)
		{
//			super.moveOffest(0, 2, 6);
//			
//			move(0, 2, 6);
			
			if (getCamera() != null)
			{
				getCamera().move(0, 2, 6);
			}
		}
		else if (perspective == FIRST)
		{
//			super.moveOffest(0, -2, -6);
//			
//			move(0, -2, -6);

			if (getCamera() != null)
			{
				getCamera().move(0, -2, -6);
			}
		}
		
		this.perspective = perspective;
	}
}