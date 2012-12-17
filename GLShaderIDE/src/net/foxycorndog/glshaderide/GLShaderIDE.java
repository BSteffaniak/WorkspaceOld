package net.foxycorndog.glshaderide;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import net.foxycorndog.glshaderide.components.CodeField;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GLContext;

import net.foxycorndog.glshaderide.compiler.GLSLCompiler;
import net.foxycorndog.glshaderide.compiler.JavaCompiler;
import net.foxycorndog.glshaderide.compiler.Compiler;
import net.foxycorndog.glshaderide.menubar.Menubar;
import net.foxycorndog.glshaderide.shaderlanguage.Keyword;
import net.foxycorndog.glshaderide.toolbar.Toolbar;

public class GLShaderIDE
{
	private Button    compileButton;
	
	private CodeField codeField, console;
	
	private String    fileLocation, fileName;
	
	private Menubar   menubar;
	
	private Toolbar   toolbar;
	
	private static boolean restarting;
	
	public static Display display;
	public static Shell   shell;
	
	public static void main(String args[])
	{
		start();
	}
	
	public static void restart()
	{
		restarting = true;
		
		shell.dispose();
		
		start();
		
		restarting = false;
	}
	
	public void exit()
	{
		System.exit(0);
	}
	
	public static void start()
	{
		if (!restarting)
		{
			display        = new Display();
		}
		
		Monitor monitor        = display.getPrimaryMonitor();
		final Rectangle screenBounds = monitor.getBounds();
		
		final Shell shell            = new Shell(display);//, SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setSize(800, 600);
		final Rectangle shellBounds = shell.getBounds();
		
		shell.setLocation(screenBounds.width / 2 - shellBounds.width / 2, screenBounds.height / 2 - shellBounds.height / 2);
		shell.setText("GLShader IDE");
		
		/**
		 * Set up the OpenGL (lwjgl) capabilities for the program.
		 */
		{
			Composite comp = new Composite(shell, SWT.NONE);
			comp.setLayout(new FillLayout());
			
			GLData data = new GLData ();
			data.doubleBuffer = true;
			final GLCanvas canvas = new GLCanvas(comp, SWT.NONE, data);
			
			canvas.setCurrent();
			
			try
			{
				GLContext.useContext(canvas);
			}
			catch(LWJGLException e)
			{
				e.printStackTrace();
			}
		}
		
		GLShaderIDE ide = new GLShaderIDE(display, shell);
		
		shell.open();
		
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				display.sleep();
			}
		}
		
		if (!restarting)
		{
			display.dispose();
		}
	}
	
	public GLShaderIDE(final Display display, final Shell shell)
	{
		this.shell = shell;
		
//		GridLayout b = new GridLayout();
//		b.makeColumnsEqualWidth = false;
//		
//		shell.setLayout(b);
		
		Listener buttonListener = new Listener()
		{
			public void handleEvent(Event e)
			{
				if (e.widget == compileButton)
				{
					console.setText(Compiler.compile(fileLocation, codeField.getRawText()));//GLSLCompiler.loadVertexShader("name.vs", codeField.getRawText()));
				}
			}
		};
		
		codeField     = new CodeField(display, shell);
		console       = new CodeField(display, shell);
		
//		compileButton = new Button(shell, SWT.PUSH);
//		compileButton.setSize(100, 20);
//		compileButton.setLocation(0, 0);
//		compileButton.setText("Compile");
//		compileButton.addListener(SWT.Selection, buttonListener);
		
		menubar       = new Menubar(shell);
		menubar.addMenuHeader("File");
		menubar.addMenuSubItem("Open", "File");
		menubar.addSeparator("File");
		menubar.addMenuSubItem("Save", "File");
		menubar.addMenuSubItem("Save as...", "File");
		menubar.addSeparator("File");
		menubar.addMenuSubItem("Restart", "File");
		menubar.addMenuSubItem("Exit", "File");
		
		toolbar       = new Toolbar(shell);
		try
		{
			toolbar.addToolItem("Compile", new Image(display, new FileInputStream("res/images/compilebutton.png")));
			toolbar.addSeparator();
			toolbar.addToolItem("Run", new Image(display, new FileInputStream("res/images/runbutton.png")));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		menubar.addSelectionListener("File", "Save", new SelectionListener()
		{
			public void widgetSelected(SelectionEvent e)
			{
				saveFile(fileLocation);
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				widgetSelected(e);
			}
		});
		
		menubar.addSelectionListener("File", "Save as...", new SelectionListener()
		{
			public void widgetSelected(SelectionEvent e)
			{
				saveFile(null);
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				widgetSelected(e);
			}
		});
		
		menubar.addSelectionListener("File", "Restart", new SelectionListener()
		{
			public void widgetSelected(SelectionEvent e)
			{
				restart();
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				widgetSelected(e);
			}
		});
		
		menubar.addSelectionListener("File", "Exit", new SelectionListener()
		{
			public void widgetSelected(SelectionEvent e)
			{
				exit();
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				widgetSelected(e);
			}
		});
		
		menubar.addSelectionListener("File", "Open", new SelectionListener()
		{
			public void widgetSelected(SelectionEvent e)
			{
				openFile();
			}

			public void widgetDefaultSelected(SelectionEvent e)
			{
				widgetSelected(e);
			}
		});
		
		toolbar.addSelectionListener("Compile", new SelectionListener()
		{
			public void widgetDefaultSelected(SelectionEvent e)
			{
				if (fileLocation == null)
				{
					openFile();
				}
				else
				{
					saveFile(fileLocation);
				}
				
				console.setText(Compiler.compile(fileLocation, codeField.getRawText()));//GLSLCompiler.loadVertexShader("name.vs", codeField.getRawText()));
			}

			public void widgetSelected(SelectionEvent e)
			{
				widgetDefaultSelected(e);
			}
		});
		
	    shell.addControlListener(new ControlListener()
		{
			@Override
			public void controlMoved(ControlEvent e)
			{
				
			}

			@Override
			public void controlResized(ControlEvent e)
			{
				int width         = (int)(shell.getClientArea().width / 100f * 80);
				int conHeight     = (int)(shell.getClientArea().height / 100f * 20);
				int toolbarHeight = (int)(25);
				
				codeField.setSize(width, shell.getClientArea().height - conHeight - toolbarHeight);
				codeField.setLocation(shell.getClientArea().width - codeField.getWidth(), toolbarHeight);//shell.getClientArea().height - codeField.getHeight());
				
				console.setSize(width, conHeight);
				console.setLocation(codeField.getBounds().x, codeField.getHeight() + codeField.getBounds().y);
				
				toolbar.setSize(toolbar.getWidth(), toolbarHeight);
				toolbar.setLocation(codeField.getX(), 0);
			}
		});
	}
	
	public FileDialog openFileBrowseDialog()
	{
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setFilterNames(new String[] { "All Files (*)" });
		dialog.setFilterExtensions(new String[] { "*" });
		dialog.setFilterPath("/");
		
		return dialog;
	}
	
	public void openFile()
	{
		FileDialog dialog = openFileBrowseDialog();
		
		String location = dialog.open();
		
		if (location == null)
		{
			return;
		}
		
		File file = new File(location);
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
			
			StringBuilder builder = new StringBuilder();
			
			String line = "";
			
			int lineNum = 0;
			
			int offset = 0;
			
			for (int i = 0; (line = reader.readLine()) != null; i ++)
			{
				codeField.parseString(line, builder, 0, 0, lineNum, offset + lineNum, true, false);
				codeField.parseChar('\n', builder, 0, 0, lineNum, offset + lineNum, true, false);
				
				lineNum ++;
				
				offset += line.length();
			}
			
			reader.close();
			
			if (builder.length() > 0)
			{
				codeField.setText(builder.substring(1).toString());
			}
			
			codeField.highlightSyntax();
			
			fileLocation = location.replace('\\', '/');
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			
		}
	}
	
	public FileDialog openSaveDialog()
	{
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterNames(new String[] { "All Files (*)" });
		dialog.setFilterExtensions(new String[] { "*" });
		dialog.setFilterPath("/");
		
		return dialog;
	}
	
	public void saveFile(String location)
	{
		if (location == null)
		{
			FileDialog dialog = openSaveDialog();
			location = dialog.open();
		}
		
		if (location == null)
		{
			return;
		}
		
		File file = new File(location);
		
		try
		{
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			
			writer.print(codeField.getWritableText());
			
			writer.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		fileLocation = location.replace('\\', '/');
	}
}