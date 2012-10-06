package net.foxycorndog.p1xeland.actors;

import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.util.LightBuffer;
import net.foxycorndog.p1xeland.P1xeland;
import net.foxycorndog.p1xeland.actors.Actor.Direction;
import net.foxycorndog.p1xeland.map.Map;

import static net.foxycorndog.p1xeland.actors.Actor.Direction.*;

public class Player extends Actor
{
	private boolean            legRotPos;
	
	private int                legRot;
	
	private static boolean     initialized;
	
	private static LightBuffer      verticesBuffer, texturesBuffer;
	
	private static SpriteSheet sprites;
	
//	private static int         renderPoints[];
	
	private static final int   RECT_SIZE = 4 * 2;
	
	public Player(Map map)
	{
		super(0, 0, 16, 32, 26, 1, map);
		
		legRot = 0;
	}
	
	public void init()
	{
		if (!initialized)
		{
			sprites        = new SpriteSheet("res/images/character/skins/default.png", "PNG", 16, 8, true);
			
			verticesBuffer = new LightBuffer(RECT_SIZE * 6 * 4);
			texturesBuffer = new LightBuffer(RECT_SIZE * 6 * 4);
			
//			renderPoints = new int[]
//			{
//				0,  6,
//				6,  6,
//				12, 6,
//				18, 6
//			};
			
			initialized    = true;
		}
	}
	
	public float[] getVertices()
	{
		int index = 0;
		
		float array[] = new float[RECT_SIZE * 6 * 4];
		
		// Front/Back
		
		// Left leg
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Right leg
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Left arm
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Torso
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 8; array[index ++] = 12;
		array[index ++] = 8; array[index ++] = 0;
		
		// Right arm
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Head
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 8;
		array[index ++] = 8; array[index ++] = 8;
		array[index ++] = 8; array[index ++] = 0;
		
		// Front/Back
	
		// Left leg
		array[index ++] = 4;  array[index ++] = 0;
		array[index ++] = 4;  array[index ++] = 12;
		array[index ++] = 8;  array[index ++] = 12;
		array[index ++] = 8;  array[index ++] = 0;
		
		// Right leg
		array[index ++] = 8;  array[index ++] = 0;
		array[index ++] = 8;  array[index ++] = 12;
		array[index ++] = 12; array[index ++] = 12;
		array[index ++] = 12; array[index ++] = 0;
		
		// Left arm
		array[index ++] = 12; array[index ++] = 12;
		array[index ++] = 12; array[index ++] = 24;
		array[index ++] = 16; array[index ++] = 24;
		array[index ++] = 16; array[index ++] = 12;
		
		// Torso
		array[index ++] = 4;  array[index ++] = 12;
		array[index ++] = 4;  array[index ++] = 24;
		array[index ++] = 12; array[index ++] = 24;
		array[index ++] = 12; array[index ++] = 12;
		
		// Right arm
		array[index ++] = 0;  array[index ++] = 12;
		array[index ++] = 0;  array[index ++] = 24;
		array[index ++] = 4;  array[index ++] = 24;
		array[index ++] = 4;  array[index ++] = 12;
		
		// Head
		array[index ++] = 4;  array[index ++] = 24;
		array[index ++] = 4;  array[index ++] = 32;
		array[index ++] = 12; array[index ++] = 32;
		array[index ++] = 12; array[index ++] = 24;

		// Left/Right
		
		// Left leg
		array[index ++] = 0;  array[index ++] = 0;
		array[index ++] = 0;  array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Right leg
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Left arm
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Torso
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Right arm
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Head
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 8;
		array[index ++] = 8; array[index ++] = 8;
		array[index ++] = 8; array[index ++] = 0;
		
		// Left/Right
		
		// Left leg
		array[index ++] = 0;  array[index ++] = 0;
		array[index ++] = 0;  array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Right leg
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Left arm
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Torso
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Right arm
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 12;
		array[index ++] = 4; array[index ++] = 0;
		
		// Head
		array[index ++] = 0; array[index ++] = 0;
		array[index ++] = 0; array[index ++] = 8;
		array[index ++] = 8; array[index ++] = 8;
		array[index ++] = 8; array[index ++] = 0;

		return array;
	}
	
	public float[] getTextures()
	{
		float array[] = new float[RECT_SIZE * 6 * 4];

		// Front
		
		array = GL.addRectTextureArrayf(getSprites(),  1, 5, 1, 3, RECT_SIZE * 0,  array);
		array = GL.addRectTextureArrayf(getSprites(),  1, 5, 1, 3, RECT_SIZE * 1,  array);
		array = GL.addRectTextureArrayf(getSprites(), 11, 5, 1, 3, RECT_SIZE * 2,  array);
		array = GL.addRectTextureArrayf(getSprites(),  5, 5, 2, 3, RECT_SIZE * 3,  array);
		array = GL.addRectTextureArrayf(getSprites(), 11, 5, 1, 3, RECT_SIZE * 4,  array);
		array = GL.addRectTextureArrayf(getSprites(),  2, 2, 2, 2, RECT_SIZE * 5,  array);
		
		// Back
		
		array = GL.addRectTextureArrayf(getSprites(),  3, 5, 1, 3, RECT_SIZE * 6,  array);
		array = GL.addRectTextureArrayf(getSprites(),  3, 5, 1, 3, RECT_SIZE * 7,  array);
		array = GL.addRectTextureArrayf(getSprites(), 13, 5, 1, 3, RECT_SIZE * 8,  array);
		array = GL.addRectTextureArrayf(getSprites(),  8, 5, 2, 3, RECT_SIZE * 9,  array);
		array = GL.addRectTextureArrayf(getSprites(), 13, 5, 1, 3, RECT_SIZE * 10, array);
		array = GL.addRectTextureArrayf(getSprites(),  6, 2, 2, 2, RECT_SIZE * 11, array);

		// Left
		
		array = GL.addRectTextureArrayf(getSprites(),  2, 5, 1, 3, RECT_SIZE * 12, array);
		array = GL.addRectTextureArrayf(getSprites(),  2, 5, 1, 3, RECT_SIZE * 13, array);
		array = GL.addRectTextureArrayf(getSprites(), 12, 5, 1, 3, RECT_SIZE * 14, array);
		array = GL.addRectTextureArrayf(getSprites(),  7, 5, 1, 3, RECT_SIZE * 15, array);
		array = GL.addRectTextureArrayf(getSprites(), 12, 5, 1, 3, RECT_SIZE * 16, array);
		array = GL.addRectTextureArrayf(getSprites(),  4, 2, 2, 2, RECT_SIZE * 17, array);

		// Right
		
		array = GL.addRectTextureArrayf(getSprites(),  0, 5, 1, 3, RECT_SIZE * 18, array);
		array = GL.addRectTextureArrayf(getSprites(),  0, 5, 1, 3, RECT_SIZE * 19, array);
		array = GL.addRectTextureArrayf(getSprites(), 10, 5, 1, 3, RECT_SIZE * 20, array);
		array = GL.addRectTextureArrayf(getSprites(),  4, 5, 1, 3, RECT_SIZE * 21, array);
		array = GL.addRectTextureArrayf(getSprites(), 10, 5, 1, 3, RECT_SIZE * 22, array);
		array = GL.addRectTextureArrayf(getSprites(),  0, 2, 2, 2, RECT_SIZE * 23, array);
		
		return array;
	}
	
	public boolean move(float dx, float dy)
	{
		boolean moved = super.move(dx, dy);
		
		if (moved && getFacing() == LEFT || getFacing() == RIGHT)
		{
			if (dx != 0)
			{
				float delta = Math.abs(dx);
				
				if (legRotPos)
				{
					legRot += delta * 6 * (isSprinting() ? 1.2f : 1);
					
					if (legRot >= 54 * (isSprinting() ? 1.2f : 1))
					{
						legRotPos = false;
					}
				}
				else
				{
					legRot -= delta * 6 * (isSprinting() ? 1.2f : 1);
					
					if (legRot <= -54 * (isSprinting() ? 1.2f : 1))
					{
						legRotPos = true;
					}
				}
			}
			else
			{
				legRot = 0;
			}
		}
		
		if (moved)
		{
			if (dx < 0 && getScreenX() * P1xeland.scale < Frame.getWidth() / 4)
			{
				setScreenX(getScreenX() - dx);
				
				getMap().move(-dx, 0);
			}
			else if (dx > 0 && getScreenX() * P1xeland.scale >= (Frame.getWidth()) - ((Frame.getWidth()) / 4))
			{
				setScreenX(getScreenX() - dx);
				
				getMap().move(-dx, 0);
			}
			if (dy < 0 && getScreenY() * P1xeland.scale < Frame.getHeight() / 4)
			{
				setScreenY(getScreenY() - dy);
				
				getMap().move(0, -dy);
			}
			else if (dy > 0 && getScreenY() * P1xeland.scale >= Frame.getHeight() - Frame.getHeight() / 4)
			{
				setScreenY(getScreenY() - dy);
				
				getMap().move(0, -dy);
			}
		}
		
		return moved;
	}
	
	@Override
	public void render()
	{
		GL.beginManipulation();
		{
			GL.translatef(getScreenX(), getScreenY() + Math.abs(legRot / 25f), 0);
			
			renderAppendages();
		}
		GL.endManipulation();
	}
	
	private void renderAppendages()
	{
		renderLeftArm(-legRot / 1.5f);
		renderRightArm(legRot / 1.5f);
		renderLeftLeg(-legRot);
		renderRightLeg(legRot);
		renderHead(0);
		renderTorso(0);
	}
	
	private void renderRightArm(float rotation)
	{
		GL.beginManipulation();
		{
			if (getFacing() == FRONT || getFacing() == BACK)
			{
				GL.translatef(0f, 12f, 0);
				
				GL.translatef(4f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-4f, -12f, 0f);
				
				GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 2, 1);
			}
			else if (getFacing() == LEFT || getFacing() == RIGHT)
			{
				GL.translatef(6f, 12f, (getFacing() == RIGHT ? 0.1f : -0.1f));
				
				GL.translatef(2f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation * (getFacing() == RIGHT ? 1 : -1));
				
				GL.translatef(-2f, -12f, 0f);
				
				if (getFacing() == RIGHT)
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 2 + 6 + 6 + 6, 1);
				}
				else
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 2 + 6 + 6, 1);
				}
			}
		}
		GL.endManipulation();
	}
	
	private void renderLeftArm(float rotation)
	{
		GL.beginManipulation();
		{
			if (getFacing() == FRONT || getFacing() == BACK)
			{
				GL.translatef(12f, 12f, 0);
				
				GL.translatef(0f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(0f, -12f, 0f);
				
				GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 4, 1);
			}
			else if (getFacing() == LEFT || getFacing() == RIGHT)
			{
				GL.translatef(6f, 12f, (getFacing() == LEFT ? 0.1f : -0.1f));
				
				GL.translatef(2f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation * (getFacing() == RIGHT ? 1 : -1));
				
				GL.translatef(-2f, -12f, 0f);
				
				if (getFacing() == RIGHT)
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 4 + 6 + 6 + 6, 1);
				}
				else
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 4 + 6 + 6, 1);
				}
			}
		}
		GL.endManipulation();
	}
	
	private void renderRightLeg(float rotation)
	{
		GL.beginManipulation();
		{
			if (getFacing() == FRONT || getFacing() == BACK)
			{
				GL.translatef(4f, 0f, 0);
				
				GL.translatef(2f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-2f, -12f, 0f);
				
				GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 0, 1);
			}
			else if (getFacing() == LEFT || getFacing() == RIGHT)
			{
				GL.translatef(6f, 0f, (getFacing() == RIGHT ? 0.1f : -0.1f));
				
				GL.translatef(2f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation * (getFacing() == RIGHT ? 1 : -1));
				
				GL.translatef(-2f, -12f, 0f);
				
				if (getFacing() == RIGHT)
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 0 + 6 + 6 + 6, 1);
				}
				else
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 0 + 6 + 6, 1);
				}
			}
		}
		GL.endManipulation();
	}
	
	private void renderLeftLeg(float rotation)
	{
		GL.beginManipulation();
		{
			if (getFacing() == FRONT || getFacing() == BACK)
			{
				GL.translatef(8f, 0f, 0);
				
				GL.translatef(2f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-2f, -12f, 0f);
				
				GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 1, 1);
			}
			else if (getFacing() == LEFT || getFacing() == RIGHT)
			{
				GL.translatef(6f, 0f, (getFacing() == LEFT ? 0.1f : -0.1f));
				
				GL.translatef(2f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation * (getFacing() == RIGHT ? 1 : -1));
				
				GL.translatef(-2f, -12f, 0f);
				
				if (getFacing() == RIGHT)
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 1 + 6 + 6 + 6, 1);
				}
				else
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 1 + 6 + 6, 1);
				}
			}
		}
		GL.endManipulation();
	}
	
	private void renderTorso(float rotation)
	{
		GL.beginManipulation();
		{
			if (getFacing() == FRONT || getFacing() == BACK)
			{
				GL.translatef(4f, 12f, 0);
				
				GL.translatef(4f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-4f, -12f, 0f);
				
				GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 3, 1);
			}
			else if (getFacing() == LEFT || getFacing() == RIGHT)
			{
				GL.translatef(6f, 12f, 0f);
				
				GL.translatef(4f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-4f, -12f, 0f);
				
				if (getFacing() == RIGHT)
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 3 + 6 + 6 + 6, 1);
				}
				else
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 3 + 6 + 6, 1);
				}
			}
		}
		GL.endManipulation();
	}
	
	private void renderHead(float rotation)
	{
		GL.beginManipulation();
		{
			if (getFacing() == FRONT || getFacing() == BACK)
			{
				GL.translatef(4f, 24f, 0);
				
				GL.translatef(4f, 0f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-4f, -0f, 0f);
				
				GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 5, 1);
			}
			else if (getFacing() == LEFT || getFacing() == RIGHT)
			{
				GL.translatef(4f, 24f, 0);
				
				GL.translatef(4f, 12f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-4f, -12f, 0f);
				
				if (getFacing() == RIGHT)
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 5 + 6 + 6 + 6, 1);
				}
				else
				{
					GL.renderQuads(getVerticesBuffer(), getTexturesBuffer(), getSprites(), 5 + 6 + 6, 1);
				}
			}
		}
		GL.endManipulation();
	}
	
	public void onStoppedMoving()
	{
		legRot = 0;
	}

	@Override
	public LightBuffer getVerticesBuffer()
	{
		return verticesBuffer;
	}

	@Override
	public LightBuffer getTexturesBuffer()
	{
		return texturesBuffer;
	}

	@Override
	public SpriteSheet getSprites()
	{
		return sprites;
	}
}