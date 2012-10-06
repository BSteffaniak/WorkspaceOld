package net.foxycorndog.p1xeland.installer;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.util.ArrayList;

import javax.swing.ImageIcon;

import net.foxycorndog.jdobase.Base;
import net.foxycorndog.jdoogl.Color;
import net.foxycorndog.jdoogl.GL;
import net.foxycorndog.jdoogl.components.Component;
import net.foxycorndog.jdoogl.components.Frame;
import net.foxycorndog.jdoogl.components.ImageButton;
import net.foxycorndog.jdoogl.components.Frame.Alignment;
import net.foxycorndog.jdoogl.image.imagemap.Texture;
import net.foxycorndog.jdoogl.listeners.ActionListener;
import net.foxycorndog.jdoutil.FileUtil;
import net.foxycorndog.jdoutil.LightBuffer;
import net.foxycorndog.jdoutil.web.Downloader;
import net.foxycorndog.jdoutil.zip.Unzipper;

public class Installer implements ActionListener
{
	private boolean     installed, installing, alreadyInstalled;
	
	private int         progress, oldProgress;
	
	private String      status;
	
	private ImageButton install;
	
	private LightBuffer loadingBarVertices, loadingBarTextures;
	private LightBuffer progressVertices, progressTextures;
	
	private Texture     loadingBarTexture;
	
	private Installer   thisInstaller;
	
	public static void main(String args[])
	{
		new Installer();
	}
	
	public Installer()
	{
		thisInstaller = this;
		
		Base.includeNatives = false;
		
		new Frame(512, 256, "P1XELAND Installer", true)
		{
			@Override
			public void init()
			{
				
				int darkness = 228;
				GL.setClearColori(darkness, darkness, darkness, 255);
				
				Frame.setResizable(false);
				
				alreadyInstalled = checkInstalled();
				
				if (!installed)
				{
					loadingBarTexture  = new Texture("res/images/GUI/LoadingBar.png", "PNG", true, true);
					
					loadingBarVertices = new LightBuffer(4 * 2);
					loadingBarTextures = new LightBuffer(4 * 2);
					
					progressVertices = new LightBuffer(4 * 2);
					progressTextures = new LightBuffer(4 * 2);
					
					loadingBarVertices.setData(0, GL.addRectVertexArrayf(0, 0, loadingBarTexture.getWidth(), loadingBarTexture.getHeight(), 0, null));
					loadingBarTextures.setData(0, GL.addRectTextureArrayf(loadingBarTexture, 0, null));
					
					progressVertices.setData(0, GL.addRectVertexArrayf(0, 0, (loadingBarTexture.getWidth() - 2 * 2) * (progress / 100f), loadingBarTexture.getHeight() - 2 * 2, 0, null));
					progressTextures.setData(0, GL.addRectTextureArrayf(GL.white, 0, null));
					
					
					install = new ImageButton(new Texture("res/images/GUI/Button.png", "PNG", true, true));
					install.setHoverImageMap(new Texture("res/images/GUI/ButtonHover.png", "PNG", true, true));
					install.setText("Install");
					install.setLocation(0, -20);
					install.setAlignment(Alignment.CENTER, Alignment.CENTER);
					install.addActionListener(thisInstaller);
					Frame.add(install);
				}
			}
			
			@Override
			public void render()
			{
				GL.loadIdentity();
				
				GL.beginManipulation();
				{
					if (alreadyInstalled)
					{
						Frame.renderText(0, 0, "You already have P1XELAND installed.", Color.BLACK, 2, Alignment.CENTER, Alignment.CENTER);
					}
					else
					{
						GL.scalef(3, 3, 1);
						
						install.render();
						
						renderLoadingBar();
						
						renderStatus();
					}
				}
				GL.endManipulation();
			}
			
			@Override
			public void loop()
			{
				progress = progress > 100 ? 100 : progress;
				
				if (progress != oldProgress)
				{
					progressVertices.setData(0, GL.addRectVertexArrayf(0, 0, (loadingBarTexture.getWidth() - 2 * 2) * (progress / 100f), loadingBarTexture.getHeight() - 2 * 2, 0, null));
				}
				
				oldProgress = progress;
			}
		}.startLoop(60);
	}
	
	public void renderLoadingBar()
	{
		GL.beginManipulation();
		{
			GL.translatef(((Frame.getWidth() / 2) / 3) - loadingBarTexture.getWidth() / 2, 50, 0);
			
			GL.renderQuad(loadingBarVertices, loadingBarTextures, loadingBarTexture);
			
			GL.translatef(2, 2, 1);
			
			GL.setColori(0, 180, 0, 180);
			
			GL.renderQuad(progressVertices, progressTextures, GL.white);
			
			GL.setColori(255, 255, 255, 255);
		}
		GL.endManipulation();
	}
	
	public void renderStatus()
	{
		GL.beginManipulation();
		{
			GL.translatef(((Frame.getWidth() / 2) / 3) - loadingBarTexture.getWidth() / 2, 21, 0);
			
			Frame.renderText(0, 0, status, Color.BLACK, 1 / 3f);
		}
		GL.endManipulation();
	}
	
	public boolean checkInstalled()
	{
		File launcherJar = new File(Base.APPLICATION_DIR + "P1XELAND/bin/P1XELANDLauncher.jar");
		
		if (!launcherJar.isFile())
		{
			return false;
		}
		
		return true;
	}
	
	@Override
	public void onActionPerformed(Component source)
	{
		if (source == install)
		{
			if (!alreadyInstalled && !installed && !installing)
			{
				new Thread()
				{
					public void run()
					{
						installing = true;
						
						progress = 0;
						status = "Making directory \"" + Base.APPLICATION_DIR + "P1XELAND/bin\"";
						
						String dir = Base.APPLICATION_DIR + "P1XELAND/bin";
							
						File f = new File(dir);
						
						if (!f.exists())
						{
							System.out.println(f.mkdirs());
						}
						
						progress = 20;
						status = "Downloading \"P1XELANDLauncher.jar\"...";
						Downloader.downloadFile("http://crapbuntu/braden/P1XELAND/Latest/P1XELANDLauncher.jar", Base.APPLICATION_DIR + "P1XELAND/bin", "P1XELANDLauncher.jar");
						
						progress = 40;
						status = "Downloading \"natives.zip\"...";
						Downloader.downloadFile("http://crapbuntu/braden/P1XELAND/Latest/natives.zip", Base.APPLICATION_DIR + "P1XELAND/bin", "natives.zip");
						
						progress = 50;
						status = "Unzipping \"natives.zip\"";
						Unzipper.unzip(Base.APPLICATION_DIR + "P1XELAND/bin/natives.zip", Base.APPLICATION_DIR + "P1XELAND/bin");
						
						progress = 60;
						status = "Deleting \"natives.zip\"";
						FileUtil.delete(Base.APPLICATION_DIR + "P1XELAND/bin/natives.zip");
						
						progress = 100;
						
						installed  = true;
						installing = false;
					}
				}.start();
			}
		}
	}

	@Override
	public void onHover(Component source)
	{
		
	}
}