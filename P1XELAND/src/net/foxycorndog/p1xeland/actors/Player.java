package net.foxycorndog.p1xeland.actors;

import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.Task;
import net.foxycorndog.jdoutil.VerticesBuffer;
import net.foxycorndog.p1xeland.P1xeland;
import net.foxycorndog.p1xeland.actors.Actor.Direction;
import net.foxycorndog.p1xeland.items.Item;
import net.foxycorndog.p1xeland.items.tiles.Tile;
import net.foxycorndog.p1xeland.map.Map;

import static net.foxycorndog.p1xeland.actors.Actor.Direction.*;

public class Player extends Actor
{
	private boolean            legRotPos;
	private boolean            beganScaling;
	
	private float              oldWid, oldHei;
	private float              legRot, headRot, armRot, crouchRot;
	
	private QuickBar           quickBar;
	
	private LightBuffer        itemHeldTexturesBuffer;
	
	private static boolean     initialized;
	
	private static VerticesBuffer verticesBuffer, itemHeldVerticesBuffer;
	
	private static LightBuffer    texturesBuffer;
	
	private static SpriteSheet sprites;
	
//	private static int         renderPoints[];
	
	private static final int   RECT_SIZE = 4 * 2;
	
	public class QuickBar
	{
		private int     width, height, selectedIndexCounter, selectedIndex, oldSelectedIndex;
		
		private Texture texture;
		
		private VerticesBuffer verticesBuffer, verticesBuffer2;
		
		private LightBuffer    texturesBuffer, texturesBuffer2;
		
		public QuickBar()
		{
			texture         = new Texture("res/images/InventorySpot.png");
			
			verticesBuffer  = new VerticesBuffer(4 * 2 * 10, 2);
			texturesBuffer  = new LightBuffer(4 * 2 * 10);
			
			verticesBuffer2 = new VerticesBuffer(4 * 2 * 10, 2);
			texturesBuffer2 = new LightBuffer(4 * 2 * 10);
			
			for (int i = 0; i < 10; i ++)
			{
				verticesBuffer.addData(GL.addRectVertexArrayf(i * (texture.getWidth() + 2), 0, texture.getWidth(), texture.getHeight(), 0, null));
				texturesBuffer.addData(GL.addRectTextureArrayf(texture, 0, null));
				
				verticesBuffer2.addData(GL.addRectVertexArrayf(0, 0, 0, 0, 0, null));
				texturesBuffer2.addData(GL.addRectVertexArrayf(0, 0, 0, 0, 0, null));
			}
			
//			verticesBuffer.refreshData();
//			texturesBuffer.refreshData();
//			
//			verticesBuffer2.refreshData();
//			texturesBuffer2.refreshData();
			
			width  = 10 * (texture.getWidth() + 2);
			height = texture.getHeight();
			
			refreshInventory();
		}
		
		public Object getSelectedObject()
		{
			return getInventory().getItem(selectedIndex);
		}
		
		public void refreshInventory()
		{
			getInventory().refreshInventory();
			
			for (int i = 0; i < 10; i ++)
			{
				Tile tile = null;
				Item item = null;
				
				if (getInventory().getItem(i) instanceof Tile)
				{
					tile = (Tile)(getInventory().getItem(i));
				}
				else if (getInventory().getItem(i) instanceof Item)
				{
					item = (Item)(getInventory().getItem(i));
				}
				
				if (tile != null)
				{
					verticesBuffer2.setData(i * 4 * 2, GL.addRectVertexArrayf(i * (texture.getWidth() + 2) * 2 + 3 * 2, 3 * 2, P1xeland.textureSize, P1xeland.textureSize, 0, null));
					texturesBuffer2.setData(i * 4 * 2, GL.addRectTextureArrayf(tile.getOffsets(), 0, null));
				}
				else if (item != null)
				{
					verticesBuffer2.setData(i * 4 * 2, GL.addRectVertexArrayf(i * (texture.getWidth() + 2) * 2 + 3 * 2, 3 * 2, P1xeland.textureSize, P1xeland.textureSize, 0, null));
					texturesBuffer2.setData(i * 4 * 2, GL.addRectTextureArrayf(item.getOffsets(), 0, null));
				}
				else
				{
					verticesBuffer2.setData(i * 4 * 2, GL.addRectVertexArrayf(0, 0, 0, 0, 0, null));
					texturesBuffer2.setData(i * 4 * 2, GL.addRectVertexArrayf(0, 0, 0, 0, 0, null));
				}
			}
			
			Object obj = getSelectedObject();
			
			if (obj instanceof Tile)
			{
				Tile tile = (Tile)obj;
				
				itemHeldTexturesBuffer.setData(0, GL.addRectTextureArrayf(tile.getOffsets(), 0, null));
			}
			else if (obj instanceof Item)
			{
				Item item = (Item)obj;
				
				itemHeldTexturesBuffer.setData(0, GL.addRectTextureArrayf(item.getOffsets(), 0, null));
			}
		}
		
		public void render()
		{
			GL.beginManipulation();
			{
//				GL.scalef(1f / scale, 1f / scale, 1);
				
				GL.translatef((Frame.getWidth() / (2) - (width * 2 - 2 * 2)) / (2 * (P1xeland.scale / 2)), 1, 0);
				
				GL.beginManipulation();
				{
//					GL.scalef(scale, scale, 1);
					GL.scalef(4f / P1xeland.scale, 4f / P1xeland.scale, 1);
					
					GL.beginManipulation();
					{
						GL.renderQuads(verticesBuffer, texturesBuffer, texture, 0, 10, new Task()
						{
							public boolean run(int index)
							{
								Object obj = getSelectedObject();
								
								if (index == selectedIndex)
								{
									GL.setColori(255, 255, 255, 255);
									
									if (oldSelectedIndex != selectedIndex)
									{
										if (obj instanceof Tile)
										{
											Tile tile = (Tile)obj;
											
											itemHeldTexturesBuffer.setData(0, GL.addRectTextureArrayf(tile.getOffsets(), 0, null));
										}
										else if (obj instanceof Item)
										{
											Item item = (Item)obj;
											
											itemHeldTexturesBuffer.setData(0, GL.addRectTextureArrayf(item.getOffsets(), 0, null));
										}
									}
								}
								else
								{
									GL.setColori(100, 100, 100, 255);
								}
								
	//							selectedIndexCounter ++;
								
								return true;
							}
						});
					}
					GL.endManipulation();
					
					oldSelectedIndex = selectedIndex;
					
//					selectedIndexCounter = 0;
					GL.setColori(255, 255, 255, 255);
				}
				GL.endManipulation();
				
				GL.beginManipulation();
				{
					GL.scalef(2f / P1xeland.scale, 2f / P1xeland.scale, 1);
					
					GL.translatef(0, 0, 1);
					
					for (int i = 0; i < 10; i ++)
					{
						int quantity = getInventory().getQuantity(i);
//						i == selectedIndex ? Color.RED : Color.WHITE
						Frame.getFont().render(quantity > 0 ? "" + quantity : "", i * (32), 16, 1f);
					}
					
					beganScaling = false;
					
					GL.renderQuads(verticesBuffer2, texturesBuffer2, Tile.getTerrain(), 0, 10, new Task()
					{
						public boolean run(int index)
						{
							if (beganScaling)
							{
								GL.endManipulation();
								
								beganScaling = false;
							}
							
							Object obj = getInventory().getItem(index);
							
							if (obj == null)
							{
								return false;
							}
							
							float wid = 1, hei = 1;
							
							if (obj instanceof Tile)
							{
								Tile tile = (Tile)obj;
								
								wid = tile.getWidth();
								hei = tile.getHeight();
							}
							else if (obj instanceof Item)
							{
								Item item = (Item)obj;
								
								wid = item.getWidth();
								hei = item.getHeight();
							}
							
							GL.beginManipulation();
							beganScaling = true;
							
							GL.scalef(wid, hei, 1);
							
							float max = Math.max((wid / hei), (hei / wid));
							
							GL.scalef(1 / max, 1 / max, 1);
							
							if (max != 1)
							{
								GL.translatef(index * (texture.getWidth() + 2) * 2 + 3 * 2 * max, 0, 0);
							}
							
							oldWid = wid;
							oldHei = hei;
							
							return true;
						}
					});
					
					if (beganScaling)
					{
						GL.endManipulation();
						
						beganScaling = false;
					}
				}
				GL.endManipulation();
			}
			GL.endManipulation();
		}
		
		public int getSelectedIndex()
		{
			return selectedIndex;
		}
		
		public void setSelectedIndex(int index)
		{
			this.selectedIndex = index;
		}
	}
	
	public Player(Map map)
	{
		super(0, 0, 16, 32, 26, 1, 4, map);
		
		quickBar               = new QuickBar();
		
		itemHeldTexturesBuffer = new LightBuffer(RECT_SIZE);
		
		itemHeldTexturesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, 0, 0, 0, null));
		
		legRot                 = 0;
	}
	
	public void init()
	{
		if (!initialized)
		{
			sprites                = new SpriteSheet("res/images/character/skins/default.png", 16, 8);
			
			verticesBuffer         = new VerticesBuffer(RECT_SIZE * 6 * 4, 2);
			texturesBuffer         = new LightBuffer(RECT_SIZE * 6 * 4);
			
			itemHeldVerticesBuffer = new VerticesBuffer(RECT_SIZE, 2);
			
			itemHeldVerticesBuffer.setData(0, GL.addRectVertexArrayf(0, 0, P1xeland.textureSize, P1xeland.textureSize, 0, null));
			
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
		int index = -4 * 2;
		
		float array[] = new float[RECT_SIZE * 6 * 4];
		
		// Front/Back
		
		// Left leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Right leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Left arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Torso
		array = GL.addRectVertexArrayf(0, 0, 8, 12, index += (4 * 2), array);
		
		// Right arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Head
		array = GL.addRectVertexArrayf(0, 0, 8, 8, index += (4 * 2), array);
		
		// Front/Back
	
		// Left leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Right leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Left arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Torso
		array = GL.addRectVertexArrayf(0, 0, 8, 12, index += (4 * 2), array);
		
		// Right arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Head
		array = GL.addRectVertexArrayf(0, 0, 8, 8, index += (4 * 2), array);

		// Left/Right
		
		// Left leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Right leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Left arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Torso
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Right arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Head
		array = GL.addRectVertexArrayf(0, 0, 8, 8, index += (4 * 2), array);
		
		// Left/Right
		
		// Left leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Right leg
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Left arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Torso
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Right arm
		array = GL.addRectVertexArrayf(0, 0, 4, 12, index += (4 * 2), array);
		
		// Head
		array = GL.addRectVertexArrayf(0, 0, 8, 8, index += (4 * 2), array);

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
	
	public void setLocation(float x, float y)
	{
		super.setLocation(x, y);
		
		setScreenX(Frame.getCenterX() / P1xeland.scale);
		setScreenY(Frame.getCenterY() / P1xeland.scale);
		
		getMap().move(-getX() + getScreenX(), -getY() + getScreenY());
	}
	
	public boolean move(float dx, float dy)
	{
		boolean moved = super.move(dx, dy);
		
		dx = getX() - getOldX();
		dy = getY() - getOldY();
		
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
//			else
//			{
//				legRot = 0;
//			}
		}
		
		if (moved)
		{
			boolean movedX = false;
			boolean movedY = false;
			
//			if (centered)
//			{
//				setLocation(getX(), getY());
//			}
//			else
//			{
				if (dx < 0 && getScreenX() * P1xeland.scale < Frame.getWidth() / 4)
				{
					setScreenX(getScreenX() - dx);
					
					getMap().move(-dx, 0);
					
					movedX = true;
				}
				else if (dx > 0 && getScreenX() * P1xeland.scale >= (Frame.getWidth()) - ((Frame.getWidth()) / 4))
				{
					setScreenX(getScreenX() - dx);
					
					getMap().move(-dx, 0);
					
					movedX = true;
				}
				if (dy < 0 && getScreenY() * P1xeland.scale < Frame.getHeight() / 4)
				{
					setScreenY(getScreenY() - dy);
					
					getMap().move(0, -dy);
					
					movedY = true;
				}
				else if (dy > 0 && getScreenY() * P1xeland.scale >= Frame.getHeight() - Frame.getHeight() / 4)
				{
					setScreenY(getScreenY() - dy);
					
					getMap().move(0, -dy);
					
					movedY = true;
				}
//			}
			
//			if (movedX || movedY)
//			{
//				getMap().pollChunks();
//			}
		}
		
		return moved;
	}
	
	public void setArmAngle()
	{
		boolean applicable = getFacing() == LEFT || getFacing() == RIGHT;
		
		if (applicable)
		{
			armRot = 180 * (getFacing() == Direction.LEFT ? 1 : 0) - (headRot + 90) * (getFacing() == Direction.LEFT ? 1 : -1);
		}
		else
		{
			armRot = 0;
		}
	}
	
	public void setFacingAngle()
	{
		boolean applicable = getFacing() == LEFT || getFacing() == RIGHT;
		
		if (!applicable || getOldFacing() != getFacing())
		{
			if (!applicable)
			{
				headRot = 0;
			}
			else
			{
				if (getFacing() == LEFT)
				{
					headRot = -90;
				}
				else
				{
					headRot = 90;
				}
			}
		}
		
		if (applicable && (MouseInput.getDX() != 0 || MouseInput.getDY() != 0))
		{
			float mx = MouseInput.getX();
			float my = MouseInput.getY();
			
			mx /= (float)P1xeland.scale;
			my /= (float)P1xeland.scale;
			
			float px = getScreenX();
			float py = getScreenY();
			
			px += 8;
			py += 28;
			
			float adjacent   = mx - px;
			float opposite   = my - py;
			float hypotenuse = (float)Math.sqrt(Math.pow(adjacent, 2) + Math.pow(opposite, 2));
			
			headRot = (float)Math.toDegrees(Math.acos(adjacent / hypotenuse));
			
			headRot *= (opposite < 0 ? -1 : 1);
			
			if (getFacing() == Direction.RIGHT)
			{
				headRot = headRot >  90 ?  90 : headRot;
				headRot = headRot < -90 ? -90 : headRot;
			}
			else if (getFacing() == Direction.LEFT)
			{
				headRot = headRot > -90 && headRot < 90 ? 90 * (opposite >= 0 ? 1 : -1) : headRot;
				headRot += 180;
			}
		}
	}
	
	@Override
	public void render()
	{
		GL.setColori(255 - getDarkness(), 255 - getDarkness(), 255 - getDarkness(), 255);
		
		if (MouseInput.getDX() != 0 || MouseInput.getDY() != 0)
		{
			if (MouseInput.getX() >= (getScreenX() + getWidth() / 2) * P1xeland.scale)
			{
				setFacing(RIGHT);
			}
			else
			{
				setFacing(LEFT);
			}
		}
		
		GL.beginManipulation();
		{
			GL.translatef(getScreenX(), getScreenY() + Math.abs(legRot / 25f), -9);
			
			renderItemHeld();
			
			renderAppendages();
		}
		GL.endManipulation();
		
		setOldFacing(getFacing());
		
		GL.setColori(255, 255, 255, 255);
	}
	
	private void renderItemHeld()
	{
		if (isHoldingObject() && (getFacing() == LEFT || getFacing() == RIGHT))
		{
			Object obj = getObjectHeld();
			
			float offsetX  = 0, offsetY = 0;
			float scale    = 1;
			float rotation = 0;
			
			if (obj instanceof Item)
			{
				Item item = (Item)obj;
				
//				scale     = item.getScale();
//				rotation  = item.getRotation();
				
//				offsetY   = getFacing() == LEFT ? P1xeland.textureSize / 2 : (P1xeland.textureSize / 2) * -1;
			}
			
			GL.beginManipulation();
			{
				GL.translatef(0, 0, getFacing() == RIGHT ? 0.1f : 0);
				
				if (obj instanceof Tile && obj == Tile.TORCH)
				{
					offsetY = 2;
				}
				
				GL.translatef(4 + (getFacing() == LEFT ? -10 : 10) + offsetX * (getFacing() == LEFT ? 2.3f : -1), 12 + 4 + 2 + offsetY, 0);
				
				GL.translatef((6 + offsetX) * (getFacing() == LEFT ? 2.3f : -1), 4 - offsetY, 0);
				
				GL.rotatef(0, 0, headRot + rotation * (getFacing() == LEFT ? -1f : 1));
				
				GL.translatef(-((6 + offsetX) * (getFacing() == LEFT ? 2.3f : -1)), -(4 - offsetY), 0);
				
				GL.scalef(.5f * 1, .5f * 1 , 1);
//				GL.scalef(-1, 1, 1);
				
				GL.renderQuads(itemHeldVerticesBuffer, itemHeldTexturesBuffer, Tile.getTerrain(), 0, 1);
				
			}
			GL.endManipulation();
		}
	}
	
	private void renderAppendages()
	{
		setFacingAngle();
		setArmAngle();
		
		crouchRot = isCrouching() ? 20f : 0f;
		
		crouchRot = getFacing() == LEFT ? crouchRot : crouchRot * -1;
		
		crouchRot = getFacing() == LEFT || getFacing() == RIGHT ? crouchRot : 0;
		
		renderLeftArm(-legRot / 1.5f + crouchRot);
		renderRightArm(isHoldingObject() ? armRot : legRot / 1.5f + crouchRot);
		renderLeftLeg(-legRot);
		renderRightLeg(legRot);
		renderHead(headRot);
		renderTorso(0 + crouchRot);
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
				if (crouchRot != 0)
				{
					GL.translatef(3 * (crouchRot / Math.abs(crouchRot)) * -1, -2, 0);
				}
				
				GL.translatef(6f, 12f, (getFacing() == RIGHT ? 0.2f : -0.09f));
				
				GL.translatef(2f, 10f, 0f);
				
				GL.rotatef(0f, 0f, rotation * (getFacing() == RIGHT ? 1 : -1));
				
				GL.translatef(-2f, -10f, 0f);
				
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
				if (crouchRot != 0)
				{
					GL.translatef(3 * (crouchRot / Math.abs(crouchRot)) * -1, -2, 0);
				}
				
				GL.translatef(6f, 12f, (getFacing() == LEFT ? 0.1f : -0.21f));
				
				GL.translatef(2f, 10f, 0f);
				
				GL.rotatef(0f, 0f, rotation * (getFacing() == RIGHT ? 1 : -1));
				
				GL.translatef(-2f, -10f, 0f);
				
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
				if (crouchRot != 0)
				{
					GL.translatef(1 * (crouchRot / Math.abs(crouchRot)), -2, 0);
				}
				
				GL.translatef(6f, 12f, 0f);
				
				GL.translatef(2f, 0f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-2f, -0f, 0f);
				
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
				if (crouchRot != 0)
				{
					GL.translatef(3 * (crouchRot / Math.abs(crouchRot)) * -1, -2, 0);
				}
				
				GL.translatef(4f, 24f, 0);
				
				GL.translatef(4f, 0f, 0f);
				
				GL.rotatef(0f, 0f, rotation);
				
				GL.translatef(-4f, -0f, 0f);
				
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
	
	public Item getItemHeld()
	{
		Object obj = getObjectHeld();
		
		if (obj instanceof Item)
		{
			return (Item)obj;
		}
		
		return null;
	}
	
	public Object getObjectHeld()
	{
		return quickBar.getSelectedObject();
	}
	
	public boolean isHoldingObject()
	{
		return quickBar.getSelectedObject() != null;
	}
	
	public QuickBar getQuickBar()
	{
		return quickBar;
	}
	
	@Override
	public VerticesBuffer getVerticesBuffer()
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