import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.MouseListener;
import java.awt.event.MouseEvent;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;

import net.foxycorndog.presto2d.graphics.PixelGraphics;
import net.foxycorndog.presto2d.graphics.PrestoColor;

// Fg = G * ((m1 * m2) / (d^2))

public class Main extends JPanel
{
	private int   width, height;
	private int   dfps, fps;
	
	private long  oldTime;
	
	JFrame        frame;
	
	Image         image;
	
	BufferedImage display, buffer;
	
	Graphics      backg;
	
	PixelGraphics p;
	
	Planet        currentPlanet;
	
	int           pixels[];
	
	ArrayList<Planet> planets;
	
	public static void main(String args[])
	{
		(new Main()).start();
	}
	
	public Main()
	{
		buffer  = new BufferedImage(800, 600, BufferedImage.BITMASK);
		backg   = buffer.createGraphics();
		
		frame = new JFrame("Physics Simulation");
		
		frame.setSize(800, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setMinimumSize(new Dimension(frame.getWidth(), frame.getHeight()));
		
		frame.add(this);
		
		frame.setVisible(true);
		
		frame.addComponentListener(new ComponentListener()
		{
			@Override
			public void componentResized(ComponentEvent e)
			{
				buffer  = new BufferedImage(frame.getWidth(), frame.getHeight(), BufferedImage.BITMASK);
				backg   = buffer.createGraphics();
			}

			@Override
			public void componentMoved(ComponentEvent e)
			{
				
			}

			@Override
			public void componentShown(ComponentEvent e)
			{
				
			}

			@Override
			public void componentHidden(ComponentEvent e)
			{
				
			}
		});
		
		frame.addMouseListener(new MouseListener()
		{
			@Override
			public void mousePressed(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1)
				{
					currentPlanet = new Planet(e.getX(), e.getY(), 10, 10, PrestoColor.getColor(200, 200, 200));
				    currentPlanet.frozen = true;
				    
				    planets.add(currentPlanet);
				}
			}

			@Override
			public void mouseClicked(MouseEvent e)
			{
				
			}

			@Override
			public void mouseReleased(MouseEvent e)
			{
				if (e.getButton() == MouseEvent.BUTTON1 && currentPlanet != null)
				{
					currentPlanet.dx = (currentPlanet.x - e.getX()) / 300.0;
					currentPlanet.dy = (currentPlanet.y - e.getY()) / 300.0;
					
					currentPlanet.frozen = false;
					
					currentPlanet = null;
				}
			}

			@Override
			public void mouseEntered(MouseEvent e)
			{
				
			}

			@Override
			public void mouseExited(MouseEvent e)
			{
				
			}
		});
		
		planets     = new ArrayList<Planet>();
		
		display     = new BufferedImage(800, 600, BufferedImage.BITMASK);
		
		pixels      = ((DataBufferInt)display.getRaster().getDataBuffer()).getData();
		
		this.width  = frame.getWidth();
		this.height = frame.getHeight();
	}
	
	private void start()
	{
		p = new PixelGraphics(pixels, width);
		
		planets.add(new Planet(350,   250,   50, 1000, PrestoColor.getColor(195, 195, 0)));
		
		int r = 55 + (int)(Math.random() * 200);
		int g = 55 + (int)(Math.random() * 200);
		int b = 55 + (int)(Math.random() * 200);
		
//		planets.add(new Planet(300,   200,   10, 20, PrestoColor.getColor(r, g, b)));
		
		r = 55 + (int)(Math.random() * 200);
		g = 55 + (int)(Math.random() * 200);
		b = 55 + (int)(Math.random() * 200);
		
//		planets.add(new Planet(100, 50,   10, 40, PrestoColor.getColor(r, g, b)));
		
		
		
		planets.add(new Planet(100, 90,   10, 10, -15/300d, 7/300d, PrestoColor.getColor(20, 100, 200)));
		
		planets.add(new Planet(400, 490,   10, 20, 16.25/300d, -6.5/300d, PrestoColor.getColor(140, 30, 30)));
		
		planets.add(new Planet(100, 390,   10, 10, -0/300d, -12/300d, PrestoColor.getColor(r, g, b)));
		
		planets.add(new Planet(100, 320,   10, 10, 0, 0, PrestoColor.getColor(r, g, b)));
		
//		planets.add(new Planet(400, 490,   10, 20, 6.25, -2.5, PrestoColor.getColor(140, 30, 30)));
		
		oldTime = System.currentTimeMillis();
		
		Timer timer = new Timer();
		
		timer.schedule(new TimerTask()
		{
			@Override
			public void run()
			{
				if (oldTime < System.currentTimeMillis() - 1000)
				{
					fps = dfps;
					
					System.out.println(fps + ", " + planets.size());
					
					dfps = 0;
					
					oldTime = System.currentTimeMillis();
				}
				
				dfps ++;
				
				tick();
				tick();
				tick();
				tick();
				tick();
				tick();
				
				render();
			}
		}, 0, 1000 / 60);
	}
	
	public void render()
	{
		p.fillRect(0, 0, width, height, 0);
		
		for (int i = planets.size() - 1; i >= 0; i --)
		{
			planets.get(i).render(pixels);
		}
		
		repaint();
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		
		backg.clearRect(0, 0, frame.getWidth(), frame.getHeight());
		
		backg.drawImage(display, 0, 0, frame.getWidth(), frame.getHeight(), null);
		
		g.drawImage(buffer, 0, 0, null);
	}
	
	private void tick()
	{
		Planet planet1, planet2;
		
		for (int i = planets.size() - 1; i >= 0; i --)
		{
			planet1 = planets.get(i);
			
			if (planet1.frozen)
			{
				continue;
			}
			
			for (int j = planets.size() - 1; j >= 0; j --)
			{
				planet2 = planets.get(j);
				
				if (planet2.frozen || planet1.equals(planet2))
				{
					continue;
				}
				
				double distance = Math.sqrt(Math.pow((planet1.x) - (planet2.x), 2) + Math.pow((planet1.y) - (planet2.y), 2));
				
				if (distance < 2)
				{
					continue;
				}
				
				double fg = 1/1000d * ((planet1.mass * planet2.mass) / (distance * distance));
				
				double xVal = (fg * (planet2.x - planet1.x)) / (planet1.mass * 1000);
				double yVal = (fg * (planet2.y - planet1.y)) / (planet1.mass * 1000);
				
				if (xVal < -1000 || xVal > 1000 || Double.isNaN(xVal))
				{
					continue;
				}
				else if (yVal < -1000 || yVal > 1000 || Double.isNaN(yVal))
				{
					continue;
				}
				
				planet1.dx += xVal;
				planet1.dy += yVal;
				
				planet1.x += planet1.dx;
				planet1.y += planet1.dy;
			}
		}
	}
	
	private class Planet
	{
		private boolean frozen;
		
		private int     radius;
		private int     color;
		
		private double  x, y;
		private double  mass;
		private double  dx, dy;
		
		public Planet(double x, double y, int radius, double mass, int color)
		{
			this.x      = x;
			this.y      = y;
			
			this.radius = radius;
			this.mass   = mass;
			
			this.color  = color;
		}
		
		public Planet(double x, double y, int radius, double mass, double dx, double dy, int color)
		{
			this.x        = x;
			this.y        = y;

			this.radius   = radius;
			this.mass     = mass;
			
			this.dx       = dx;
			this.dy       = dy;

			this.color    = color;
		}
		
		public void render(int pixels[])
		{
			p.fillCircle((int)(x - radius + 0),                         (int)(y - radius + 0),                         (int)(radius),       color);
			p.fillCircle((int)(x - radius + (radius - (radius * .8f))), (int)(y - radius + (radius - (radius * .8f))), (int)(radius * .8f), PrestoColor.darkenColor(color, 0x00222222));
			p.fillCircle((int)(x - radius + (radius - (radius * .4f))), (int)(y - radius + (radius - (radius * .4f))), (int)(radius * .4f), PrestoColor.darkenColor(color, 0x00333333));
		}
	}
}
