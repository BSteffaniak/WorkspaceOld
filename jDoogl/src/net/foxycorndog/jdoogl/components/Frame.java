package net.foxycorndog.jdoogl.components;

import static net.foxycorndog.jdoogl.GL.beginTextureDraw;
import static net.foxycorndog.jdoogl.GL.beginVertexDraw;
import static net.foxycorndog.jdoogl.GL.endTextureDraw;
import static net.foxycorndog.jdoogl.GL.endVertexDraw;
import static net.foxycorndog.jdoogl.components.Frame.Alignment.BOTTOM;
import static net.foxycorndog.jdoogl.components.Frame.Alignment.CENTER;
import static net.foxycorndog.jdoogl.components.Frame.Alignment.LEFT;
import static net.foxycorndog.jdoogl.components.Frame.Alignment.RIGHT;
import static net.foxycorndog.jdoogl.components.Frame.Alignment.TOP;

import static org.lwjgl.opengl.Display.wasResized;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDrawArrays;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glScalef;
import static org.lwjgl.opengl.GL11.glTranslatef;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.DataBufferInt;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.ArrayList;

import javax.imageio.ImageIO;

import net.foxycorndog.jdobase.Base;
import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.fonts.TTFont;
import net.foxycorndog.jdoogl.image.imagemap.SpriteSheet;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.input.KeyboardInput;
import net.foxycorndog.jdoogl.input.MouseInput;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.jdoogl.listeners.KeyListener;
import net.foxycorndog.jdoogl.listeners.MouseListener;
import net.foxycorndog.jdoutil.FrameLoop;
import net.foxycorndog.jdoutil.FrameTask;
import net.foxycorndog.jdoutil.Intersects;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.Util;
import net.foxycorndog.jdoutil.VerticesBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.UnicodeFont.DisplayList;
import org.newdawn.slick.font.GlyphPage;

import de.matthiasmann.twl.utils.PNGDecoder;
import de.matthiasmann.twl.utils.PNGDecoder.Format;

public abstract class Frame
{
	private static boolean     resized;
	private static boolean     stream;
	private static boolean     fullscreen;
	private static boolean     startedFps;
	
	private static short       dfps, fps, predictedFps;
	
	private static int         oldX, oldY;
	private static int         width, height;
	
	private static float       scaleX, scaleY;
	
	private static long        newTime, oldTime;
	
	private static String      fileSeparator = System.getProperty("file.separator");
	
	private static Dimension   originalDimensions, resizedDimensions;
	
	private static TTFont      font;
	
	private static Cursor      cursor;
	
	private static FrameLoop   loop;
	
	private static DisplayMode oldDisplay;
	
	private static boolean     keysPressed[];
	private static boolean     buttonsPressed[];
	
	private static float       normalFontHeight;
	
	private static ArrayList<MouseListener>  mouseListeners;
//	private static ArrayList<ActionListener> actionListeners;
//	private static ArrayList<KeyListener>    keyListeners;
	
	private static ArrayList<Component>      components;
	
	private static ButtonInfo mouseButtons[];
	
//	private ArrayList<Font>  fonts;
	
	public static enum Alignment
	{
		LEFT, RIGHT, CENTER, TOP, BOTTOM
	}
	
	private static class ButtonInfo
	{
		private boolean released;
		
		private int     buttonId;
		
		public ButtonInfo(int buttonId)
		{
			this.buttonId = buttonId;
			
			this.released = true;
		}
		
		public boolean isReleased()
		{
			return released;
		}
		
		public void setReleased(boolean released)
		{
			this.released = released;
		}
		
		public int getButtonId()
		{
			return buttonId;
		}
		
		public boolean isButtonDown()
		{
			return Mouse.isButtonDown(buttonId);
		}
	}
	
	public Frame(int width, int height, String title, boolean stream)
	{
		init(width, height, title, null, stream);
	}
	
	public Frame(int width, int height, String title, Canvas drawCanvas)
	{
		init(width, height, title, drawCanvas, false);
	}
	
	/**
	* Called when a new Display is created. Calls the initialization
	* method to start it up.
	* 
	* @param width The width of the frame to create.
	* @param height The height of the frame to create.
	*/
	public Frame(int width, int height, String title, Canvas drawCanvas, boolean stream)
	{
		init(width, height, title, drawCanvas, stream);
	}
	
	private static void init(int width, int height, String title, Canvas drawCanvas, boolean stream)
	{
		keysPressed    = new boolean[1024];
		buttonsPressed = new boolean[3];
		
		Frame.stream = stream;
		
//		File f = new File("");
//		System.out.println("Path: " + f.getAbsolutePath());
//		GL.initGL();
		
		mouseListeners     = new ArrayList<MouseListener>();
//		actionListeners    = new ArrayList<ActionListener>();
//		keyListeners       = new ArrayList<KeyListener>();
		
		components         = new ArrayList<Component>();
		
		originalDimensions = new Dimension(width, height);
		resizedDimensions  = new Dimension(width, height);
		
		scaleX = 1;
		scaleY = 1;
		
		mouseButtons       = new ButtonInfo[3];
		
		for (int i = 0; i < mouseButtons.length; i ++)
		{
			mouseButtons[i] = new ButtonInfo(i);
		}
		
		oldX = Mouse.getX();
		oldY = Mouse.getY();
		
		Frame.init(width, height, title, drawCanvas);
		
//		setFont("res/images/font/pixel.ttf", 16);
	}
	
	public static class Cursor
	{
		private int         width, height;
		
		private VerticesBuffer verticesBuffer;
		
		private LightBuffer    texturesBuffer;
		
		private Texture     cursorSprites;
		
		public Cursor(int width, int height, String location, boolean flipped)
		{
			this.width  = width;
			this.height = height;
			
			cursorSprites = new Texture(location);
			
			verticesBuffer = new VerticesBuffer(4 * 2, 2);
			texturesBuffer = new LightBuffer(4 * 2);
			
			verticesBuffer.addData(GL.addRectVertexArrayf(0, 0, width, height, 0, null));
			texturesBuffer.addData(GL.addRectTextureArrayf(cursorSprites, 0, null));
		}
		
		public void render()
		{
			GL.beginManipulation();
			{
				GL.beginInvertingBackground();
				
				float offset = 10;
				
				glTranslatef(Mouse.getX(), Mouse.getY() - height + 1, offset);
				
				GL.renderQuads(verticesBuffer, texturesBuffer, cursorSprites, 0, 1);
				
				GL.endInverting();
			}
			GL.endManipulation();
		}
		
		public int getWidth()
		{
			return width;
		}
		
		public int getHeight()
		{
			return height;
		}
	}
	
	/**
	* The initialization method for the Display class. Creates a
	* frame and assigns buffer variables.
	* 
	* @param width
	* @param height
	*/
	private static void init(int width, int height, String title, Canvas drawCanvas)
	{
		Mouse.setClipMouseCoordinatesToWindow(false);
		
		Frame.width  = width;
		Frame.height = height;
		
		GL.createFrame(width, height, title, drawCanvas);
		
//		GL.initBasicView();
		
		Display.setSwapInterval(1);
		
//		try {
//			System.out.println(this.getClass().getResource("res"));
//		}catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
//		Image wht = null;
//		
//		try
//		{
//			System.out.println(new File("res/images").getAbsolutePath());
//			
//			Class clazz = Frame.class;
//			
//			System.out.println(Frame.class.getCanonicalName());
//			
//			URL url = clazz.getClassLoader().getResource("res/images/White.png");
//			
//			InputStream is = clazz.getResourceAsStream("res/images/White.png");
//			
//			System.out.println(is);
//			
//			URI uri = url.toURI();
//			
//			File fle = new File(uri);
//			
//			wht = ImageIO.read(fle);
//		} catch (IOException e) {
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			e.printStackTrace();
//		}
		
		GL.white = new Texture("res/images/White.png");//, Frame.class);
		
		newTime = System.currentTimeMillis();
		oldTime = newTime;
		
		dfps    =  0;
		fps     = -1;
		
		boolean antiAlias   = true;
		java.awt.Graphics g = GlyphPage.getScratchGraphics();
		
		if (g != null && g instanceof Graphics2D)
		{
			Graphics2D g2d = (Graphics2D)g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, antiAlias ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
			g2d.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, antiAlias ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
		}
		
		//ResourceLoader.addResourceLocation(new ClasspathLocation());
//		ResourceLoader.addResourceLocation(new FileSystemLocation(new File(".")));
	}
	
	public static void setIcon(String image16Location, String image32Location) throws IOException
	{
		InputStream in;
		
		in = new FileInputStream(image16Location);
	
		PNGDecoder decoder = new PNGDecoder(in);
		
		ByteBuffer buf16 = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(buf16, decoder.getWidth() * 4, Format.RGBA);
		buf16.flip();
		
		in.close();
		
		
		in = new FileInputStream(image32Location);
		
		decoder = new PNGDecoder(in);
		
		ByteBuffer buf32 = BufferUtils.createByteBuffer(4 * decoder.getWidth() * decoder.getHeight());
		decoder.decode(buf32, decoder.getWidth() * 4, Format.RGBA);
		buf32.flip();
		
		in.close();
		
		
		Display.setIcon(new ByteBuffer[] { buf16, buf32 });
		
		
	}
	
	public static void setFont(String location, int size)
	{
		font = new TTFont(location, size, true);
		
		normalFontHeight = font.getLegitHeight("WA10|)!1");
	}
	
	public static void setCursor(Cursor cursor)
	{
		Frame.cursor = cursor;
	}
	
	/**
	* Start the main game loop that runs at all times.
	*/
	public void startLoop(int targetFps)
	{
		init();
		
		loop = new FrameLoop();
		
		loop.start(targetFps, new FrameTask()
		{
			public void run()
			{
//				onDrawFrame();Frame.init(P1xeland.GAME_TITLE, getGameRenderer(), activity);
			}
		});
	}
	
	private static void listenKeys()
	{
		boolean keys[] = KeyboardInput.getKeys();
		
		synchronized (components)
		{
			for (int i = components.size() - 1; i >= 0; i --)
			{
				synchronized (components)
				{
					try
					{
						Component component = components.get(i);
						
						ArrayList<KeyListener> keyListeners = component.getKeyListeners();
						
						if (keyListeners == null || keyListeners.size() <= 0)
						{
							continue;
						}
						
						for (int d = 0; d < keys.length; d ++)
						{
							if (keys[d])
							{
								synchronized (keyListeners)
								{
									for (int j = keyListeners.size() - 1; j >= 0; j --)
									{
										synchronized (keyListeners)
										{
											keyListeners.get(j).onKeyPressed(d);
										}
									}
								}
							}
							else if (keysPressed[d])
							{
								synchronized (keyListeners)
								{
									for (int j = keyListeners.size() - 1; j >= 0; j --)
									{
										synchronized (keyListeners)
										{
											keyListeners.get(j).onKeyReleased(d);
											keyListeners.get(j).onKeyTyped(d);
										}
									}
								}
							}
						}
					}
					catch (IndexOutOfBoundsException ex)
					{
						ex.printStackTrace();
					}
				}
			}
			
			keysPressed = keys.clone();
		}
		
//		synchronized (Frame.keyListeners)
//		{
//			for (int i = 0; i < Frame.keyListeners.size(); i ++)
//			{
//				synchronized (Frame.keyListeners)
//				{
//					for (int d = 0; d < keys.length; d ++)
//					{
//						if (keys[d])
//						{
//							Frame.keyListeners.get(i).onKeyPressed(d);
//						}
//					}
//				}
//			}
//		}
	}
	
	private static void listenMouse()
	{
		boolean buttons[] = MouseInput.getButtons();
		
		int dx = Mouse.getX();
		int dy = Mouse.getY();
		
		dx -= oldX;
		dy -= oldY;
		
		synchronized (components)
		{
			for (int i = components.size() - 1; i >= 0; i --)
			{
				synchronized (components)
				{
					try
					{
						Component component = components.get(i);
//						System.out.println(Mouse.getX() + ", " + Mouse.getY() + " : " + component.getScreenWidth() + ", " + component.getScreenHeight());
						if (Intersects.rectangles(Mouse.getX(), Mouse.getY(), 1, 1, component.getMouseX(), component.getMouseY(), component.getScreenWidth(), component.getScreenHeight()))
						{
							ArrayList<ActionListener> actionListeners = component.getActionListeners();
							
							synchronized (actionListeners)
							{
								for (int j = actionListeners.size() - 1; j >= 0; j --)
								{
									synchronized (actionListeners)
									{
										try
										{
											if (buttons[0] && !buttonsPressed[0])
											{
												synchronized (components)
												{
													for (int f = components.size() - 1; f >= 0; f --)
													{
														synchronized (components)
														{
															components.get(f).setFocused(false);
														}
													}
												}
												
												component.setFocused(true);
												
												actionListeners.get(j).onActionPerformed(component);
											}
											
											actionListeners.get(j).onHover(component);
											
											component.setHovering(true);
										}
										catch (IndexOutOfBoundsException ex)
										{
											
										}
									}
								}
							}
						}
						else
						{
							component.setHovering(false);
						}
						
						ArrayList<MouseListener> mouseListeners = component.getMouseListeners();
						
						if (mouseListeners != null)
						{
							for (int b = 0; b < buttons.length; b ++)
							{
								synchronized (mouseListeners)
								{
									for (int f = mouseListeners.size() - 1; f >= 0; f --)
									{
										synchronized (mouseListeners)
										{
											if (buttons[b])
											{
												mouseListeners.get(f).onMousePressed(b);
											}
											else if (!buttons[b] && buttonsPressed[b])
											{
												mouseListeners.get(f).onMouseClicked(b);
											}
											if (!buttons[b])
											{
												mouseListeners.get(f).onMouseReleased(0);
											}
											
											if (dx != 0 || dy != 0)
											{
												mouseListeners.get(f).onMouseMoved();
												
												if (buttons[b])
												{
													mouseListeners.get(f).onMouseDragged(b);
												}
											}
										}
									}
								}
							}
						}
					}
					catch (IndexOutOfBoundsException ex)
					{
						if (Base.debug)
						{
							ex.printStackTrace();
						}
					}
				}
			}
		}
		
		synchronized (mouseListeners)
		{
			for (int i = mouseListeners.size() - 1; i >= 0; i --)
			{
				synchronized (mouseListeners)
				{
					for (int b = 0; b < 2; b ++)
					{
						if (buttons[b])
						{
							mouseListeners.get(i).onMousePressed(b);
						}
						else if (!buttons[b])
						{
							mouseListeners.get(i).onMouseClicked(b);
						}
						if (!buttons[b])
						{
							mouseListeners.get(i).onMouseReleased(b);
						}
						
						if (dx != 0 || dy != 0)
						{
							mouseListeners.get(i).onMouseMoved();
							
							if (buttons[b])
							{
								mouseListeners.get(i).onMouseDragged(b);
							}
						}
					}
				}
			}
		}
		
		buttonsPressed = buttons.clone();
		
		oldX = Mouse.getX();
		oldY = Mouse.getY();
	}
	
	public static void addMouseListener(MouseListener mouseListener)
	{
		mouseListeners.add(mouseListener);
	}
	
	public static void removeMouseListener(MouseListener mouseListener)
	{
		mouseListeners.remove(mouseListener);
	}
	
//	public static void addActionListener(ActionListener actionListener)
//	{
//		actionListeners.add(actionListener);
//	}
//	
//	public static void addKeyListener(KeyListener keyListener)
//	{
//		keyListeners.add(keyListener);
//	}
	
	public static void setFocused(Component component, boolean focused)
	{
		if (focused)
		{
			synchronized (components)
			{
				for (int f = components.size() - 1; f >= 0; f --)
				{
					synchronized (components)
					{
						if (component != components.get(f))
						{
							components.get(f).setFocused(false);
						}
					}
				}
			}
		}
	}
	
	/**
	* Abstract void that is used to initialize stuff.
	*/
	public abstract void init();
	
	/**
	* Abstract void that is used for the loop.
	*/
	public abstract void loop();
	
	/**
	* Abstract void that is used to render objects.
	*/
	public abstract void render();
	
	public static void setResizable(boolean resizable)
	{
		Display.setResizable(resizable);
	}
	
	/**
	* Updates the FPS of the screen.
	*/
	public static void updateFps()
	{
		dfps ++;
		
		newTime = System.currentTimeMillis();
		
		if (fps == -1)
		{
			oldTime = newTime - 20;
		}
		
		if (newTime > oldTime + 1000)
		{
			fps = dfps;
			fps --;
			
			dfps       = 0;
			
			oldTime    = newTime;
			
			startedFps = true;
		}
		else if (newTime != oldTime)
		{
			predictedFps = (short)((dfps) * (1000 / (float)(newTime - oldTime)));
			
			if (!startedFps)
			{
				fps = predictedFps;
			}
		}
	}
	
	public static float getCenterX()
	{
		return (float)Display.getWidth() / 2;
	}
	
	public static float getCenterY()
	{
		return (float)Display.getHeight() / 2;
	}
	
	public static int getFps()
	{
		return fps;
	}
	
	public static int getPredictedFps()
	{
		return predictedFps;
	}
	
	public static int getDFps()
	{
		return dfps;
	}
	
	public static void renderTextBox(float x, float y, String text, Color color, float scale)
	{
		int beginIndex = 0;
		int yoff = 0;
		
		int len = text.length();
		
		int height = font.getHeight(text);
		
//		for (int i = 0; i < len + 1; i ++)
//		{
//			if (font.getWidth(text.substring(beginIndex, i)) * scale2 > 300 || i == len)
//			{
//				String substr = text.substring(beginIndex, i);
//				
//				renderText(x, y + yoff * height * scale2, substr, color, scale);
//				
//				yoff ++;
//				beginIndex = i;
//			}
//		}
		
		for (int i = 0; i < len; i ++)
		{
			
		}
	}
	
	/**
	* Render text to the screen at the specified place with the
	* specified Color.
	* 
	* @param x The x position.
	* @param y The y position.
	* @param text The text to be rendered.
	* @param color The color to render the text in.
	* @param size The size of the text to be rendered.
	* @param beginIndex The index to start drawing the string at.
	* @param endIndex The index of the String to stop drawing it at.
	*/
	public static void renderText(float x, float y, String text, Color color, float scale, int beginIndex, int endIndex)
	{
		if (text == null || text.equals(""))
		{
			return;// null;
		}
		
		float scaleX = (float)GL.getAmountScaled()[0];
		float scaleY = (float)GL.getAmountScaled()[1];
		
//		DisplayList d = null;
		
		GL.flipView();
		
//		y = Frame.getHeight() - y - 1;
		
		glPushMatrix();
		{
			glScalef(scale, scale, 1);
			
			//font.drawString(x / scale2, y / scale2 + Idk.offsetY / scale2, text, color, beginIndex, endIndex);
//			d = font.drawDisplayList(0, 0, text, color, beginIndex, endIndex);
//			font.drawString(x / scale, y / scale, text, color, beginIndex, endIndex);
			font.drawString(x / scale, y / scale, text, color, beginIndex, endIndex);
		}
		glPopMatrix();
		
		GL.flipView();
		
		glColor4f(1, 1, 1, 1);
		
		//return new short[] { d.width, d.height };
	}
	
	/**
	* Render text to the screen at the specified place with the
	* specified Color.
	* 
	* @param x The x position.
	* @param y The y position.
	* @param text The text to be rendered.
	* @param color The color to render the text in.
	* @param size The size of the text to be rendered.
	*/
	public static float[] renderText(float x, float y, String text, Color color, float scale)
	{
		if (text == null || text.equals(""))
		{
			return null;
		}
		
		double scales[] = GL.getAmountScaled();
		double renderLoc[] = GL.getRenderLocation();
		
		y += renderLoc[1] * 2;
		
		y += normalFontHeight * scale;
		y = (float)(Frame.getHeight() / scales[1] - y - 1 / scales[1]);
		
		renderText(x, y, text, color, scale, 0, text.length());
		
		return new float[] { x + (float)renderLoc[0], y + (float)renderLoc[1] };
	}
	
	/**
	* Render text to the screen at the specified place with the
	* specified Color.
	* 
	* @param xo The x offset position.
	* @param yo The y offset position.
	* @param text The text to be rendered.
	* @param color The color to render the text in.
	* @param size The size of the text to be rendered.
	* @param halign The horizontal alignment of the text to be
	* 		rendered in the Display.
	*/
	public static float[] renderText(float xo, float yo, String text, Color color, float scale, Alignment halign)
	{
		if (halign == LEFT)
		{
			
		}
		else if (halign == CENTER)
		{
			xo += (int)getCenterX() - ((font.getLegitWidth(text) * scale) / 2);
		}
		else if (halign == RIGHT)
		{
			xo += Display.getWidth() - (font.getLegitWidth(text) * scale);
		}
		
		return renderText(xo, yo, text, color, scale);
	}
	
	/**
	* Render text to the screen at the specified place with the
	* specified Color.
	* 
	* @param xo The x offset position.
	* @param yo The y offset position.
	* @param text The text to be rendered.
	* @param color The color to render the text in.
	* @param size The size of the text to be rendered.
	* @param halign The horizontal alignment of the text to be
	* 		rendered in the Display.
	*/
	public static float[] renderText(float xo, float yo, String text, Color color, float scale, Alignment halign, Alignment valign)
	{
		if (valign == TOP)
		{
			yo += Display.getHeight() - normalFontHeight * scale;
		}
		else if (valign == CENTER)
		{
			yo += (int)getCenterY() + ((normalFontHeight * scale) / 2);
		}
		else if (valign == BOTTOM)
		{
			
		}
		
		return renderText(xo, yo, text, color, scale, halign);
	}
	
	public static void setFullscreen(boolean fullscreen)
	{
		Frame.fullscreen = fullscreen;
		
		if (fullscreen)
		{
			DisplayMode oldM = Display.getDisplayMode();
			
			DisplayMode modes[] = null;
			
			try
			{
				modes            = Display.getAvailableDisplayModes();
				
				DisplayMode mode = modes[0];
				
				Toolkit toolkit  = Toolkit.getDefaultToolkit();
				
				Dimension dim    = toolkit.getScreenSize();
				
				if (modes.length > 1)
				{
					for (int i = 1; i < modes.length; i ++)
					{
						if (modes[i].getWidth() == dim.width &&
								modes[i].getHeight() == dim.height)
						{
							mode = modes[i];
							
							break;
						}
					}
				}
				
				Display.setDisplayMode(mode);
				
				Display.setFullscreen(true);
				
				oldDisplay = oldM;
			}
			catch (LWJGLException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			try
			{
//				Display.setDisplayMode(new DisplayMode(width, height));
//				
//				Display.setResizable(true);
//				
//				Display.setFullscreen(false);
				
				Display.setDisplayMode(oldDisplay);
			}
			catch (LWJGLException e)
			{
				e.printStackTrace();
			}
		}
		
		resized = true;
		
		GL.resetBasicView();
	}
	
	public static boolean isFullscreen()
	{
		return fullscreen;
	}
	
//	public static void setFullscreen(boolean fullscreen, Dimension dimension)
//	{
//		if (fullscreen)
//		{
//			DisplayMode modes[] = null;
//			
//			try
//			{
//				modes            = Display.getAvailableDisplayModes();
//				
//				DisplayMode mode = modes[0];
//				
//				if (modes.length > 1)
//				{
//					for (int i = 1; i < modes.length; i ++)
//					{
//						if (modes[i].getWidth() == dimension.width &&
//								modes[i].getHeight() == dimension.height)
//						{
//							mode = modes[i];
//							
//							break;
//						}
//					}
//				}
//				
//				Display.setDisplayMode(mode);
//				
//				Display.setFullscreen(true);
//			}
//			catch (LWJGLException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		else
//		{
//			try
//			{
//				Display.setDisplayMode(new DisplayMode(width, height));
//				
//				Display.setResizable(true);
//				
//				Display.setFullscreen(false);
//			}
//			catch (LWJGLException e)
//			{
//				e.printStackTrace();
//			}
//		}
//		
//		resized = true;
//	}
	
	public static boolean wasResized()
	{
		return resized;
	}
	
	public static int getWidth()
	{
		return Display.getWidth();
	}
	
	public static int getHeight()
	{
		return Display.getHeight();
	}
	
	public static TTFont getFont()
	{
		return font;
	}
	
	public static void add(Component component)
	{
		components.add(component);
	}
	
	public static void remove(Component component)
	{
		components.remove(component);
	}
	
	public static float getScaleX()
	{
		return scaleX;
	}
	
	public static float getScaleY()
	{
		return scaleY;
	}
	
	public static Cursor getCursor()
	{
		return cursor;
	}
	
	public static void setTitle(String title)
	{
		Display.setTitle(title);
	}
	
	public static boolean usingStream()
	{
		return stream;
	}
	
	public static float getNormalFontHeight()
	{
		return normalFontHeight;
	}
	
	public static abstract class GameRenderer
	{
		private int width, height;
		
		public GameRenderer(String title, int width, int height)
		{
			Frame.init(width, height, title, null, false);
		}

		public void onDrawFrame()
		{
			GL11.glClear(GL11.GL_COLOR_BUFFER_BIT | GL11.GL_DEPTH_BUFFER_BIT | GL11.GL_STENCIL_BUFFER_BIT);
			
			Util.nanoTime = System.nanoTime();
			
			GL.beginManipulation();
			{
				Frame.updateFps();
				
				if (Display.wasResized())
				{
					resized = true;
					
					GL.resetBasicView();
					
					resizedDimensions = new Dimension(Display.getWidth(), Display.getHeight());
					
					scaleX = (float)resizedDimensions.width  / (float)originalDimensions.width;
					scaleY = (float)resizedDimensions.height / (float)originalDimensions.height;
				}
				
				Frame.listenMouse();
				Frame.listenKeys();
				
				loop(getDFps());
				render(getDFps());
				
				if (cursor != null)
				{
					cursor.render();
				}
			}
			GL.endManipulation();

			resized = false;
			
			MouseInput.next();
			KeyboardInput.next();
		}

		public abstract void render(int dfps);
		
		public abstract void loop(int dfps);
	}
}