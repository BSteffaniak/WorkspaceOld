package net.foxycorndog.arrowide;

import java.awt.BorderLayout;
import java.awt.TextField;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import net.foxycorndog.arrowide.components.CodeField;
import net.foxycorndog.arrowide.components.CodeFieldEvent;
import net.foxycorndog.arrowide.components.CodeFieldListener;
import net.foxycorndog.arrowide.components.ConsoleField;
import net.foxycorndog.arrowide.components.ContentEvent;
import net.foxycorndog.arrowide.components.ContentListener;
import net.foxycorndog.arrowide.components.SplashScreen;
import net.foxycorndog.arrowide.console.ConsoleListener;
import net.foxycorndog.arrowide.console.ConsoleStream;
import net.foxycorndog.arrowide.dialog.Dialog;
import net.foxycorndog.arrowide.dialog.DialogFilter;
import net.foxycorndog.arrowide.dialog.FileBrowseDialog;
import net.foxycorndog.arrowide.dialog.FileInputDialog;
import net.foxycorndog.arrowide.dialog.OptionDialog;
import net.foxycorndog.arrowide.dialog.PreferencesDialog;
import net.foxycorndog.arrowide.dialog.PreferencesDialogPanel;
import net.foxycorndog.arrowide.dialog.TextInputDialog;
import net.foxycorndog.arrowide.dialog.preferencesdialogpanel.CppPanel;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CompilerListener;
import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.Language;
import net.foxycorndog.arrowide.language.LanguageCompiler;
import net.foxycorndog.arrowide.language.java.JavaLanguage;
import net.foxycorndog.arrowide.menubar.Menubar;
import net.foxycorndog.arrowide.menubar.MenubarListener;
import net.foxycorndog.arrowide.printer.TextPrinter;
import net.foxycorndog.arrowide.tabmenu.TabMenu;
import net.foxycorndog.arrowide.tabmenu.TabMenuListener;
import net.foxycorndog.arrowide.toolbar.Toolbar;
import net.foxycorndog.arrowide.toolbar.ToolbarListener;
import net.foxycorndog.arrowide.treemenu.TreeMenu;
import net.foxycorndog.arrowide.treemenu.TreeMenuListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.events.ControlListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.opengl.GLCanvas;
import org.eclipse.swt.opengl.GLData;
import org.eclipse.swt.printing.PrintDialog;
import org.eclipse.swt.printing.Printer;
import org.eclipse.swt.printing.PrinterData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.FileDialog;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.Widget;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.GLContext;


public class ArrowIDE implements ContentListener, CodeFieldListener, TabMenuListener
{
	private boolean      filesNeedRefresh;
	
	private CodeField    codeField;
	
	private ConsoleField console;
	
	private String       fileLocation;
	
	private Image        folderImage, fileImage, javaFileImage, classFileImage,
						glslFileImage, txtFileImage, rtfFileImage, exeFileImage,
						asmFileImage, cppFileImage, hFileImage;
	
	private PreferencesDialog preferences;
	
	private Menubar   menubar;
	
	private Toolbar   toolbar;
	
	private TreeMenu  treeMenu;
	
	private TabMenu   tabs;
	
	private ConsoleStream   consoleStream;
	
	private Dialog          newFolderDialog, newFileDialog, newProjectDialog;
	private TextInputDialog renameFileDialog;
	
	private HashMap<Integer, String>  treeItemLocations;
	private HashMap<Integer, String>  treeItemOrigLocations;
	private HashMap<String, Integer>  treeItemIds;
	private HashMap<Integer, String>  treeItemDirectories;
	private HashMap<Integer, String>  menuItemLocations;
	private HashMap<String, Integer>  menuItems;
	private HashMap<String, String>   fileCache;
	private HashMap<String, Boolean>  fileCacheSaved;
	private HashMap<Integer, String>  tabFileLocations;
	private HashMap<String, Integer>  tabFileIds;
	
	private static boolean      restarting;
	
	private static Display      display;
	private static Shell        shell;
	private static SplashScreen splash;
	
	private static String                   configLocation;
	
	public  static final HashMap<String, String>  CONFIG_DATA;
	public  static final HashMap<Integer, String> CONFIG_LINE_NUMBER_DATA;
	public  static final HashMap<String, Integer> CONFIG_LINE_NUMBERS;
	
	public  static final HashMap<String, Object>  PROPERTIES;
	
	/**
	 * Initialize the CONFIG_DATA HashMaps and set the os properties
	 * in the PROPERTIES HashMap.
	 */
	static
	{
		CONFIG_DATA             = new HashMap<String, String>();
		CONFIG_LINE_NUMBER_DATA = new HashMap<Integer, String>();
		CONFIG_LINE_NUMBERS     = new HashMap<String, Integer>();
		
		PROPERTIES              = new HashMap<String, Object>();
		
		String osName = System.getProperty("os.name");
		
		if (osName.toLowerCase().contains("mac"))
		{
			PROPERTIES.put("os.name", "macosx");
			PROPERTIES.put("composite.modifiers", SWT.BORDER);
			PROPERTIES.put("key.control", SWT.COMMAND);
			PROPERTIES.put("os.executable.extension", "");
		}
		else if (osName.toLowerCase().contains("win"))
		{
			PROPERTIES.put("os.name", "windows");
			PROPERTIES.put("composite.modifiers", SWT.NONE);
			PROPERTIES.put("key.control", SWT.CTRL);
			PROPERTIES.put("os.executable.extension", ".exe");
		}
		else if (osName.toLowerCase().contains("lin"))
		{
			PROPERTIES.put("os.name", "linux");
			PROPERTIES.put("composite.modifiers", SWT.NONE);
			PROPERTIES.put("key.control", SWT.CTRL);
			PROPERTIES.put("os.executable.extension", "");
		}
		
		setArchitecture();
	}
	
	private static void setArchitecture()
	{
		int bitness = 32;
		
		ProcessBuilder b = new ProcessBuilder(new String[] { "res/bitness" });
		
		try
		{
			Process p = b.start();
			
			InputStream in = p.getInputStream();
			
			BufferedReader reader = new BufferedReader(new InputStreamReader(in));
			
			String line = null;
			
			while ((line = reader.readLine()) != null)
			{
				bitness = Integer.valueOf(line);
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		PROPERTIES.put("os.arch", bitness);
	}
	
	/**
	 * The initial starting point of the program. It is only called
	 * whenever the program is started from scratch without a restart.
	 * 
	 * @param args The command line arguments. (Unused)
	 */
	public static void main(String args[])
	{
		start();
	}
	
	/**
	 * The constructor for this class. Initializes the window that is
	 * used for programming.
	 * 
	 * @param display The display to use.
	 * @param shell The window to use.
	 */
	public ArrowIDE(final Display display, final Shell shell)
	{
		this.shell = shell;
		
		shell.setBackground(new Color(display, 170, 170, 170));
		
//		GridLayout b = new GridLayout();
//		b.makeColumnsEqualWidth = false;
//		
//		shell.setLayout(b);
		
		fileCache     = new HashMap<String, String>();
		
		codeField     = new CodeField(shell);
		console       = new ConsoleField(shell);
		
		codeField.addContentListener(this);
		codeField.addCodeFieldListener(this);
		
		int width         = (int)(shell.getClientArea().width / 100f * 80);
		int conHeight     = (int)(shell.getClientArea().height / 100f * 20);
		int toolbarHeight = (int)(25);
		
		codeField.setSize(width, shell.getClientArea().height - conHeight - toolbarHeight - 16);
		codeField.setLocation(shell.getClientArea().width - codeField.getWidth(), toolbarHeight);//shell.getClientArea().height - codeField.getHeight());
		codeField.setShowLineNumbers(true);
		
		console.setSize(width, conHeight - 5);
		console.setLocation(codeField.getBounds().x, codeField.getHeight() + codeField.getBounds().y + 5);
		
		try
		{
			consoleStream = new ConsoleStream("log.txt");
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		try
		{
			folderImage       = new Image(display, new FileInputStream("res/images/folderimage.png"));
			fileImage         = new Image(display, new FileInputStream("res/images/fileimage.png"));
			javaFileImage     = new Image(display, new FileInputStream("res/images/javafileimage.png"));
			classFileImage    = new Image(display, new FileInputStream("res/images/classfileimage.png"));
			glslFileImage     = new Image(display, new FileInputStream("res/images/glslfileimage.png"));
			txtFileImage      = new Image(display, new FileInputStream("res/images/txtfileimage.png"));
			rtfFileImage      = new Image(display, new FileInputStream("res/images/rtffileimage.png"));
			exeFileImage      = new Image(display, new FileInputStream("res/images/exefileimage.png"));
			asmFileImage      = new Image(display, new FileInputStream("res/images/asmfileimage.png"));
			cppFileImage      = new Image(display, new FileInputStream("res/images/cppfileimage.png"));
			hFileImage        = new Image(display, new FileInputStream("res/images/hfileimage.png"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		tabFileLocations  = new HashMap<Integer, String>();
		tabFileIds        = new HashMap<String, Integer>();
		
		tabs = new TabMenu(shell);
		tabs.setBackground(new Color(Display.getCurrent(), 199, 238, 255));
		tabs.addListener(this);
//		shell.setBackground(tabs.getBackground());
		
	    tabs.setMaxWidth(codeField.getWidth() + 2);
		codeField.setLocation(codeField.getX(), codeField.getY() + tabs.getHeight());
		tabs.setLocation(codeField.getX(), 2);
		
		preferences = new PreferencesDialog(shell);
		preferences.addPreferencesDialogPanel(new CppPanel(preferences.getWindow()));
		
		menuItemLocations = new HashMap<Integer, String>();
		menuItems         = new HashMap<String, Integer>();
		
		menubar           = new Menubar(shell);
		addMenuItem(menubar.addMenuHeader("File"), "File");
		addMenuItem(menubar.addMenuSubItem("New", menuItems.get("File")), "File>New");
		menubar.addSeparator(menuItems.get("File"));
		addMenuItem(menubar.addMenuSubItem("Open", menuItems.get("File")), "File>Open");
		menubar.addSeparator(menuItems.get("File"));
		addMenuItem(menubar.addMenuSubItem("Save", menuItems.get("File")), "File>Save");
		addMenuItem(menubar.addMenuSubItem("Save as...", menuItems.get("File")), "File>Save as...");
		menubar.addSeparator(menuItems.get("File"));
		addMenuItem(menubar.addMenuSubItem("Print", menuItems.get("File")), "File>Print");
		menubar.addSeparator(menuItems.get("File"));
		addMenuItem(menubar.addMenuSubItem("Restart", menuItems.get("File")), "File>Restart");
		addMenuItem(menubar.addMenuSubItem("Exit", menuItems.get("File")), "File>Exit");

		addMenuItem(menubar.addMenuSubItem("Project", menuItems.get("File>New")), "File>New>Project");
		addMenuItem(menubar.addMenuSubItem("Empty File", menuItems.get("File>New")), "File>New>Empty File");
		
		addMenuItem(menubar.addMenuHeader("Edit"), "Edit");
		addMenuItem(menubar.addMenuSubItem("Preferences", menuItems.get("Edit")), "Edit>Preferences");
		
		menubar.addListener(new MenubarListener()
		{
			public void subItemPressed(int subItemId)
			{
				if (!menuItemLocations.containsKey(subItemId))
				{
					return;
				}
				
				if (menuItemLocations.get(subItemId).equals("File>New>Empty File"))
				{
					newFile();
				}
				else if (menuItemLocations.get(subItemId).equals("File>New>Project"))
				{
					newProject();
				}
				else if (menuItemLocations.get(subItemId).equals("File>Open"))
				{
					openFile();
				}
				else if (menuItemLocations.get(subItemId).equals("File>Save"))
				{
					saveFile(fileLocation);
				}
				else if (menuItemLocations.get(subItemId).equals("File>Save as..."))
				{
					saveFile(null);
				}
				else if (menuItemLocations.get(subItemId).equals("File>Print"))
				{
					PrintDialog dialog = new PrintDialog(shell, SWT.NONE);
					dialog.setScope(PrinterData.SELECTION);
					
					PrinterData data = dialog.open();
					
					FontData fd[] = codeField.getFont().getFontData().clone();
					fd[0].setHeight(12);
					
					TextPrinter printer = new TextPrinter(data, codeField.getText(), new Font(display, fd[0]), codeField.getStyles());
					printer.setMargins(1, 1, 1, 1);
					
					if (!printer.print())
					{
						System.err.println("Was not able to print!");
					}
				}
				else if (menuItemLocations.get(subItemId).equals("File>Restart"))
				{
					restart();
				}
				else if (menuItemLocations.get(subItemId).equals("File>Exit"))
				{
					exit(shell);
				}
				else if (menuItemLocations.get(subItemId).equals("Edit>Preferences"))
				{
					preferences.open();
				}
			}
		});
		
		try
		{
			toolbar       = new Toolbar(shell);
			
			toolbar.setBackground(shell.getBackground());

			toolbar.addToolItem("Save", new Image(display, new FileInputStream("res/images/savebutton.png")));
			toolbar.addSeparator();
			toolbar.addToolItem("Compile", new Image(display, new FileInputStream("res/images/compilebutton.png")));
			toolbar.addSeparator();
			toolbar.addToolItem("Run", new Image(display, new FileInputStream("res/images/runbutton.png")));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
		toolbar.addListener(new ToolbarListener()
		{
			public void toolItemPressed(String toolItemName)
			{
				if (toolItemName.equals("Save"))
				{
					saveFile(fileLocation);
				}
				else if (toolItemName.equals("Compile"))
				{
					if (fileLocation == null)
					{
						openFile();
					}
					else
					{
						if (Language.canCompile(FileUtils.getFileType(fileLocation)))
						{
							saveFile(fileLocation);
					
							console.setText("");
							
							String outputLocation = FileUtils.getParentFolder(fileLocation) + "/";
							
							new File(outputLocation).mkdirs();
							
							try
							{
								LanguageCompiler.compile(fileLocation, codeField.getRawText(), outputLocation, consoleStream);
							}
							catch (UnsupportedOperationException e)
							{
								consoleStream.println(e.getMessage());
							}
						}
					}
				}
				else if (toolItemName.equals("Run"))
				{
					if (fileLocation == null)
					{
						saveFile(fileLocation);
					}
					else
					{
						console.setText("");
						
						try
						{
							Language.run(codeField.getLanguage(), fileLocation, consoleStream);
						}
						catch (UnsupportedOperationException e)
						{
							consoleStream.println(e.getMessage());
						}
					}
				}
			}
		});
		
		LanguageCompiler.addCompilerListener(new CompilerListener()
		{
			public void compiled(int result)
			{
				if (result == 0)
				{
					filesNeedRefresh = true;
					
					consoleStream.println("Compiled successfully.");
				}
			}
		});
		
		fileCacheSaved        = new HashMap<String, Boolean>();
		treeItemLocations     = new HashMap<Integer, String>();
		treeItemOrigLocations = new HashMap<Integer, String>();
		treeItemIds           = new HashMap<String, Integer>();
		treeItemDirectories   = new HashMap<Integer, String>();
		
		treeMenu              = new TreeMenu(shell);
		treeMenu.setSize(shell.getClientArea().width - codeField.getWidth() - 10, codeField.getHeight() + console.getHeight());
		treeMenu.setLocation(5, codeField.getY());
		treeMenu.setBackground(new Color(display, 255, 255, 255));
		
		Menu m = new Menu(treeMenu);
		treeMenu.setMenu(m);
		
		final MenuItem newFolder = new MenuItem(m, SWT.CASCADE);
		newFolder.setText("New Folder");
		
		final MenuItem newFile = new MenuItem(m, SWT.CASCADE);
		newFile.setText("New File");
		
		final MenuItem rename = new MenuItem(m, SWT.CASCADE);
		rename.setText("Rename");
		
		final MenuItem delete = new MenuItem(m, SWT.CASCADE);
		delete.setText("Delete");
		
		final ArrowIDE thisIDE = this;
		
		SelectionListener menuListener = new SelectionListener()
		{
			public void widgetDefaultSelected(SelectionEvent e)
			{
				if (e.widget == delete)
				{
					int id = treeMenu.getSelection();
//					
//					if (treeItems.containsKey(id))
//					{
//						System.out.println("is file");
//					}
					
					String location = treeItemOrigLocations.get(id);
					
					deleteFile(location);
					
					refreshFileViewer();
				}
				else if (e.widget == newFolder)
				{
					String preLoc = treeItemOrigLocations.get(treeMenu.getSelection());
					
					if (preLoc == null)
					{
						preLoc = FileUtils.removeEndingSlashes(CONFIG_DATA.get("workspace.location")) + "/";
					}
					
					String preLocation = FileUtils.removeEndingSlashes(preLoc);
					
					if (FileUtils.isFileName(preLocation))
					{
						preLocation = FileUtils.getParentFolder(preLocation);
					}
					
					newFolderDialog = new FileInputDialog("Enter the folder name:", "Folder name:", true, preLocation + "/", false);
					
					String location = newFolderDialog.open();
					
					if (location != null)
					{
						File f = new File(location);
						f.mkdirs();
						
						refreshFileViewer();
					}
				}
				else if (e.widget == newFile)
				{
					String preLoc = treeItemOrigLocations.get(treeMenu.getSelection());
					
					if (preLoc == null)
					{
						preLoc = FileUtils.removeEndingSlashes(CONFIG_DATA.get("workspace.location")) + "/";
					}
					
					String preLocation = FileUtils.removeEndingSlashes(preLoc);
					
					if (FileUtils.isFileName(preLocation))
					{
						preLocation = FileUtils.getParentFolder(preLocation);
					}
					
					newFileDialog = new FileInputDialog("Enter the file name:", "File name:", false, preLocation + "/", false);
					
					String location = newFileDialog.open();
					
					if (location != null)
					{
						File f = new File(location);
						
						try
						{
							f.createNewFile();
						
							openFile(location);
							
							refreshFileViewer();
						}
						catch (IOException e2)
						{
							e2.printStackTrace();
						}
					}
				}
				else if (e.widget == rename)
				{
					final int selection		= treeMenu.getSelection();
					
					final String loc		= treeItemOrigLocations.get(selection);
					final String locLower	= loc.toLowerCase();
					
					boolean willContinue	= false;
					
					if (fileCache.containsKey(locLower))
					{
						if (!fileCacheSaved.get(locLower))
						{
							String result = null;
							
							OptionDialog optDialog = new OptionDialog("Save?", "Would you like to save before renaming?");
							
							result = optDialog.open();
							
							if (result != null)
							{
								if (result.equals("yes"))
								{
									saveFile(loc);
									
									willContinue	= true;
								}
								else if (result.equals("no"))
								{
									willContinue = true;
								}
							}
						}
						else
						{
							willContinue = true;
						}
					}
					else
					{
						willContinue = true;
					}
					
					if (willContinue)
					{
						renameFileDialog = new TextInputDialog("Enter the new name:", "New name:", FileUtils.getFileName(treeItemOrigLocations.get(selection)));
						
						renameFileDialog.addDialogFilter(new DialogFilter()
						{
							public String filter(String text)
							{
								text = FileUtils.removeEndingSlashes(text.replace('\\', '/'));
								
								for (int i = 0; i < text.length(); i ++)
								{
									if (text.charAt(i) == '/')
									{
										return "The name must be in the same location.";
									}
								}
								
								
								String newLoc		= FileUtils.getParentFolder(loc) + "/" + text;
								String newLocLower	= newLoc.toLowerCase();
								
								boolean currentFile	= text.equals(FileUtils.getFileName(loc));
								
								if (currentFile)
								{
									return "The name must be different than the current name.";
								}
								
								File f = new File(loc);
								
								boolean successful = f.renameTo(new File(newLoc));
								
								if (successful)
								{
									int id = treeItemIds.get(locLower);
									
									if (treeItemDirectories.containsKey(id))
									{
										treeItemDirectories.remove(id);
									}
									
									treeItemIds.remove(locLower);
									treeItemLocations.remove(id);
									treeItemOrigLocations.remove(id);
									treeMenu.removeItem(id);
									
									if (fileCache.containsKey(locLower))
									{
										fileCache.put(newLocLower, fileCache.remove(locLower));
										fileCacheSaved.put(newLocLower, fileCacheSaved.remove(locLower));
										System.out.println("Added: " + newLocLower + ", " + fileCacheSaved.get(newLocLower));
									}
									
									if (tabFileLocations.containsValue(locLower))
									{
										int tabId = tabFileIds.get(locLower);
										
										tabs.setTabText(tabId, FileUtils.getFileName(newLoc));
										tabFileLocations.put(tabId, newLocLower);
										
										tabFileIds.put(newLocLower, tabFileIds.remove(locLower));
									}
								}
								
								if (currentFile)
								{
									fileLocation = newLoc;
									
									codeField.setLanguage(Language.getLanguage(fileLocation));
									
									codeField.highlightSyntax();
								}
								
								boolean before = true;
								
								if (fileCacheSaved.containsKey(newLocLower))
								{
									before = fileCacheSaved.get(newLocLower);
								}
								
								refreshFileViewer();
								
								fileCacheSaved.put(newLocLower, before);
								
								return null;
							}
						});
						
						String result = renameFileDialog.open();
						
						if (result != null)
						{
							FileUtils.removeEndingSlashes(result.replace('\\', '/'));
						}
					}
				}
			}

			public void widgetSelected(SelectionEvent e)
			{
				widgetDefaultSelected(e);
			}
		};
		
		newFolder.addSelectionListener(menuListener);
		newFile.addSelectionListener(menuListener);
		rename.addSelectionListener(menuListener);
		delete.addSelectionListener(menuListener);
		
		treeMenu.addListener(new TreeMenuListener()
		{
			public void treeItemDoubleClicked(int id)
			{
				if (!treeItemDirectories.containsKey(id))
				{
					String location = treeItemOrigLocations.get(id);
					
					openFile(location);
				}
			}

			public void treeItemRightClicked(int id)
			{
				
			}
		});
		
		
		consoleStream.addConsoleListener(new ConsoleListener()
		{
			public void onPrintln(Object o)
			{
				if (o instanceof String)
				{
					console.append((String)o);
				}
			}
			
			public void onPrint(Object o)
			{
				if (o instanceof String)
				{
					console.append((String)o);
				}
			}
		});
		
	    ControlListener shellListener = new ControlListener()
		{
			public void controlMoved(ControlEvent e)
			{
				
			}

			public void controlResized(ControlEvent e)
			{
				int width         = (int)(shell.getClientArea().width / 100f * 80);
				int conHeight     = (int)(shell.getClientArea().height / 100f * 20);
				int toolbarHeight = (int)(25);
				
				codeField.setSize(width, shell.getClientArea().height - conHeight - toolbarHeight - tabs.getY());
				codeField.setLocation(shell.getClientArea().width - codeField.getWidth(), tabs.getHeight() + tabs.getY());
				
				toolbar.setSize(toolbar.getWidth(), toolbarHeight);
				toolbar.setLocation(codeField.getX(), 0);
				
				console.setSize(width, conHeight - 5);
				console.setLocation(codeField.getBounds().x, codeField.getHeight() + codeField.getBounds().y + 5);
				
//				tabs.setWidth(codeField.getWidth() + 2);
				tabs.setLocation(codeField.getX(), toolbarHeight + 2);
				
				treeMenu.setLocation(5, codeField.getY());
				treeMenu.setSize(shell.getClientArea().width - codeField.getWidth() - 10, console.getLocation().y + console.getHeight() - codeField.getY());
			}
		};
		
		shell.addControlListener(shellListener);
		
		shellListener.controlResized(null);
	}
	
	/**
	 * The start method that is used to start up the whole ArrowIDE
	 * program. Creates the window and puts the stuff in it.
	 */
	public static void start()
	{
		if (!restarting)
		{
			display = new Display();
		}
		
		Monitor monitor = display.getPrimaryMonitor();
		final Rectangle screenBounds = monitor.getBounds();
		
//		splash = new Shell(display, SWT.ON_TOP);
//		splash.setSize(largeIcon.getBounds().width, largeIcon.getBounds().height);
//		splash.setLocation(screenBounds.width / 2 - splash.getSize().x / 2, screenBounds.height / 2 - splash.getSize().y / 2);
//		
//		Label splashImage = new Label(splash, SWT.NONE);
//		splashImage.setSize(splash.getSize());
//		splashImage.setImage(largeIcon);
		
//		splash = new SplashScreen("res/images/iconlarge.png", 3000);
		
		Image largeIcon = null;

		try
		{
			largeIcon = new Image(display, new FileInputStream("res/images/iconlarge.png"));
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		
//		splash.open(3000);
		
		final Shell shell = new Shell(display);//, SWT.SHELL_TRIM & (~SWT.RESIZE));
		shell.setSize((int)(monitor.getBounds().width / 1.5f), (int)(monitor.getBounds().height / 1.5f));
		shell.setImage(largeIcon);
		ArrowIDE.shell = shell;
		
		final Rectangle shellBounds = shell.getBounds();
		
		shell.setLocation(screenBounds.width / 2 - shellBounds.width / 2, screenBounds.height / 2 - shellBounds.height / 2);
		shell.setText("Arrow IDE");
		
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
		
		configLocation       = new File("arrow.config").getAbsolutePath().replace('\\', '/');

		createConfigData();
		
		PROPERTIES.put("arrowide.location", FileUtils.getParentFolder(configLocation));
		
		ArrowIDE ide = null;
		
		if (workspaceCreated())
		{
			ide = openIDE();
		}
		else
		{
			FileBrowseDialog chooseWorkspace = new FileBrowseDialog("Choose your project workspace folder:", "Workspace:", FileBrowseDialog.DIRECTORY);
			
			String location = chooseWorkspace.open();
			
			if (location == null)
			{
				exit(shell);
			}
			
			setConfigDataValue("workspace.location", location);
			
			ide = openIDE();
		}
		
//		System.out.println(OS.SendMessage(shell.handle, OS.EM_SETSEL, 5, 9));//new TCHAR(0, "2dasdf", true)));
		
		shell.setFocus();
		shell.forceActive();
		shell.forceFocus();
		shell.setActive();
		
		while (!shell.isDisposed())
		{
			if (!display.readAndDispatch())
			{
				ide.update();
//				splash.update();
				
				display.sleep();
			}
		}
		
		if (!restarting)
		{
			exit(shell);
		}
	}
	
	/**
	 * Add a menu item with the specified id and name.
	 * 
	 * @param id The id to assign the item to.
	 * @param name The name that the item will show up as.
	 */
	private void addMenuItem(int id, String name)
	{
		menuItems.put(name, id);
		menuItemLocations.put(id, name);
	}
	
	/**
	 * Create a new ArrowIDE and then open it.
	 * 
	 * @return The created ArrowIDE object.
	 */
	public static ArrowIDE openIDE()
	{
		ArrowIDE ide = new ArrowIDE(display, shell);
		
		shell.open();
		
		ide.refreshFileViewer();
		
		return ide;
	}
	
	/**
	 * Restarts the program to a fresh state.
	 */
	public static void restart()
	{
		restarting = true;
		
		shell.dispose();
		
		start();
		
		restarting = false;
	}
	
	/**
	 * The method that is called right before the exiting of the program.
	 * 
	 * @param shell The window to close (The main window).
	 */
	public static void exit(Shell shell)
	{
		shell.dispose();
		
		Display.getCurrent().close();
		
		System.exit(0);
	}
	
	/**
	 * Returns whether a workspace has been located or created.
	 * 
	 * @return Whether the workspace has been located or created.
	 */
	public static boolean workspaceCreated()
	{
		File workspaceDirectory = new File(CONFIG_DATA.get("workspace.location"));
		
		return workspaceDirectory.exists();
	}
	
	/**
	 * Set a CONFIG_DATA value in the HashMap and the arrow.config file.
	 * If the key is not already in the file, it will add it to the end.
	 * 
	 * @param key The key of the property to set.
	 * @param value The value of the property to set.
	 */
	public static void setConfigDataValue(String key, String value)
	{
		boolean added = false;
		
		try
		{
			PrintWriter p = new PrintWriter(new FileWriter(configLocation));
			
			for (int i = 0; i < CONFIG_DATA.size(); i ++)
			{
				String lineKey   = null;
				String lineValue = null;
				
				lineKey = CONFIG_LINE_NUMBER_DATA.get(i);
				
				if (lineKey.equals(key))
				{
					added = true;
					
					lineValue = value;
				}
				else
				{
					lineValue = CONFIG_DATA.get(lineKey);
				}
				
				p.print(lineKey + "=" + lineValue + (i == CONFIG_DATA.size() - 1 ? "" : "\r\n"));
			}
		
			if (!added)
			{
				p.print("\r\n" + key + "=" + value);
			}
			
			p.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		
		CONFIG_DATA.put(key, value);
	}
	
	/**
	 * Creates and initializes the CONFIG_DATA. Puts all of the correct
	 * values into the HashMap.
	 */
	private static void createConfigData()
	{
		File file = new File(configLocation);
		
		try
		{
			BufferedReader reader = new BufferedReader(new FileReader(file));
		
			String line = null;
			
			for (int lineNum = 0; (line = reader.readLine()) != null; lineNum ++)
			{
				String lineData[] = line.split("=");
				
				String key        = lineData[0];
				String value      = "";
				
				for (int i = 1; i < lineData.length; i ++)
				{
					if (i > 1)
					{
						value += "=";
					}
					
					value += lineData[i];
				}
				
				CONFIG_DATA.put(key, value);
				CONFIG_LINE_NUMBERS.put(key, lineNum);
				CONFIG_LINE_NUMBER_DATA.put(lineNum, key);
			}
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	/**
	 * Creates a Dialog to ask for the project name. Next it creates a
	 * directory/folder for the project and refreshes the file viewer.
	 */
	public void newProject()
	{
		newProjectDialog = new FileInputDialog("Enter the name of your project:", "Project name:", "", true, CONFIG_DATA.get("workspace.location"), false);
		
		String location  = newProjectDialog.open();
		
		if (location != null)
		{
			File f = new File(location);
			f.mkdirs();
			
			refreshFileViewer();
		}
	}
	
	/**
	 * Creates new file and saves the old one. Switches to the new file
	 * for editing automatically.
	 */
	public void newFile()
	{
		if (fileLocation != null)
		{
			saveFile(fileLocation);
		}
		
		fileLocation = null;
		
		codeField.setText("");
	}
	
	/**
	 * Creates a new FileDialog at the absolute path of the workspace
	 * location.
	 * 
	 * @return The created, unopened FileDialog at the workspace path
	 * 		used for opening files.
	 */
	public FileDialog openFileBrowseDialog()
	{
		String relativeLocation = "";
		
		if (fileLocation != null)
		{
			if (fileLocation.lastIndexOf('/') - (CONFIG_DATA.get("workspace.location").length() + 1) > -1)
			{
				fileLocation.substring(CONFIG_DATA.get("workspace.location").length() + 1, fileLocation.lastIndexOf('/'));
			}
		}
		
		return openFileBrowseDialog(relativeLocation);
	}
	
	/**
	 * Instantiates a new FileDialog set for all types of files to be
	 * set at the specified location.
	 * 
	 * @param relativeLocation The location for the FileDialog to start.
	 * @return The created, unopened FileDialog used for opening files.
	 */
	public FileDialog openFileBrowseDialog(String relativeLocation)
	{
		FileDialog dialog = new FileDialog(shell, SWT.OPEN);
		dialog.setFilterNames(new String[] { "All Files (*)" });
		dialog.setFilterExtensions(new String[] { "*" });
		dialog.setFilterPath(CONFIG_DATA.get("workspace.location") + "/" + relativeLocation);
		
		return dialog;
	}
	
	/**
	 * Open a file located at the specified location.
	 * 
	 * @param location The location of the file to open.
	 */
	public void openFile(String location)
	{
		location = location.replace('\\', '/');
		
		String locationKey  = location.toLowerCase();
		
		boolean alreadyOpen = fileCache.containsKey(locationKey);
		
		if (alreadyOpen)
		{
			codeField.setText(fileCache.get(locationKey), true, true);
			
			codeField.setLanguage(Language.getLanguage(location));
			
			codeField.redraw();
			
			int tabId = tabFileIds.get(locationKey);
			
			tabs.setSelection(tabId);
		}
		else
		{
			File file = new File(location);
			
			try
			{
				BufferedReader reader = new BufferedReader(new FileReader(file));
				
				StringBuilder builder = new StringBuilder();
				
				String line = "";
				
				while ((line = reader.readLine()) != null)
				{
					builder.append(line + "\r\n");
				}
				
				reader.close();
				
				if (builder.length() > 0)
				{
					builder.deleteCharAt(builder.length() - 1);
					builder.deleteCharAt(builder.length() - 1);
				}
				
				String fileContents = builder.toString();
				
				fileCache.put(locationKey, fileContents);
				fileCacheSaved.put(locationKey, true);
				
				codeField.setText(fileContents, true);
				
				codeField.setLanguage(Language.getLanguage(location));
				
				codeField.redraw();
				
				addTab(location);
			}
			catch (FileNotFoundException e)
			{
				e.printStackTrace();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
				
		fileLocation = location;
	}
	
	/**
	 * Opens a FileDialog to search for a file to open, then opens
	 * the result.
	 */
	public void openFile()
	{
		FileDialog dialog = openFileBrowseDialog();
		
		String location   = dialog.open();
		
		if (location == null)
		{
			return;
		}
		
		openFile(location);
	}
	
	/**
	 * Creates a new FileDialog at the absolute path of the workspace
	 * location.
	 * 
	 * @return The created, unopened FileDialog at the workspace path
	 * 		used for saving.
	 */
	public FileDialog openSaveDialog()
	{
		String relativeLocation = "";
		
		if (fileLocation != null)
		{
			if (fileLocation.lastIndexOf('/') - (CONFIG_DATA.get("workspace.location").length() + 1) > -1)
			{
				fileLocation.substring(CONFIG_DATA.get("workspace.location").length() + 1, fileLocation.lastIndexOf('/'));
			}
		}
		
		return openSaveDialog(relativeLocation);
	}
	
	/**
	 * Instantiates a new FileDialog set for all types of files to be
	 * set at the specified location.
	 * 
	 * @param relativeLocation The location for the FileDialog to start.
	 * @return The created, unopened FileDialog used for saving.
	 */
	public FileDialog openSaveDialog(String relativeLocation)
	{
		FileDialog dialog = new FileDialog(shell, SWT.SAVE);
		dialog.setFilterNames(new String[] { "All Files (*)" });
		dialog.setFilterExtensions(new String[] { "*" });
		dialog.setFilterPath(CONFIG_DATA.get("workspace.location") + "/" + relativeLocation);
		
		return dialog;
	}
	
	/**
	 * Saves a file located at the specified location.
	 * 
	 * @param location The location of the file to open.
	 */
	public void saveFile(String location)
	{
		location = location.replace('\\', '/');
		
		if (location == null)
		{
			FileDialog dialog = openSaveDialog();
			location = dialog.open();
		}
		
		if (location == null)
		{
			return;
		}
		
		if (fileLocation == null)
		{
			fileLocation = "";
		}
		
		String locKey			= location.toLowerCase();
		String currentLocKey	= fileLocation.toLowerCase();
		
		boolean saved			= false;
		
		boolean currentFile  = locKey.equals(currentLocKey);
	
		if (fileCacheSaved.containsKey(currentLocKey))
		{
			saved = fileCacheSaved.get(currentLocKey);
		}
		
		FileUtils.writeFile(location, codeField.getWritableText());
		
		fileLocation	= location;
		
		boolean highlight = codeField.getLanguage() == 0;
		
		codeField.setLanguage(Language.getLanguage(fileLocation));
		
		if (highlight)
		{
			codeField.highlightSyntax();
		}
		
		if (currentFile)
		{
			setFileSaved(location, true);
		}
		
		refreshFileViewer();
	}
	
	/**
	 * Refresh the file viewer to all of the updated file names.
	 * If a file is no longer existent, then remove it, or if a file
	 * has been added, add it to the TreeMenu.
	 * 
	 * TODO: Make more efficient!!!
	 */
	private void refreshFileViewer()
	{
		File workspace = new File(CONFIG_DATA.get("workspace.location"));
		
		findSubFiles(workspace, 0);
		
		String locations[] = treeItemLocations.values().toArray(new String[0]);
		
		for (int i = 0; i < locations.length; i ++)
		{
			File file = new File(locations[i]);
			
			if (!file.exists())
			{
				int id = treeItemIds.get(locations[i]);
				
				treeItemIds.remove(locations[i]);
				treeItemLocations.remove(id);
				treeItemOrigLocations.remove(id);
				
				if (treeMenu.containsItem(id))
				{
					treeMenu.removeItem(id);
				}
			}
		}
		
		treeMenu.alphabetize();
	}
	
	/**
	 * Finds the sub-files of a directory and if they have not been
	 * added, add them to the TreeMenu.
	 * 
	 * @param file The directory to search sub-files for.
	 * @param parent The id of the directory TreeMenu item.
	 */
	private void findSubFiles(File file, int parent)
	{
		File subFiles[] = file.listFiles();
		
		if (subFiles != null)
		{
			int subFilesIds[] = new int[subFiles.length];
			
			for (int i = 0; i < subFiles.length; i ++)
			{
				boolean isDirectory = subFiles[i].isDirectory();
				
				String orig         = subFiles[i].getAbsolutePath().replace('\\', '/');
				String name         = FileUtils.getFileName(orig);
				String location     = orig.toLowerCase();
				
				int id              = 0;
				
				Image img           = isDirectory ? folderImage : getFileImage(location);
				
				if (treeItemOrigLocations.containsValue(orig))
				{
					id = treeItemIds.get(location);
					
					if (isDirectory)
					{
						findSubFiles(subFiles[i], id);
					}
				}
				// Set text correctly of renamed files.
				else if (treeItemLocations.containsValue(location))
				{
					id = treeItemIds.get(location);
					
					treeMenu.setTreeItemText(id, name);
					treeItemOrigLocations.put(id, orig);
				}
				else
				{
					if (parent > 0)
					{
						id = treeMenu.addSubItem(parent, name, img);
					}
					else
					{
						id = treeMenu.addItem(name, img);
					}
					
					if (isDirectory)
					{
						findSubFiles(subFiles[i], id);
						
						treeItemDirectories.put(id, location);
					}
					
					fileCacheSaved.put(location, true);
					treeItemLocations.put(id, location);
					treeItemOrigLocations.put(id, orig);
					treeItemIds.put(location, id);
				}
			}
		}
	}
	
	/**
	 * Implemented method that occurs whenever the content
	 * of a TextField is changed. In this case it tells you that
	 * the current file has been changed and needs to be saved.
	 */
	public void contentChanged(ContentEvent event)
	{
		Object source = event.getSource();
		
		if (source == codeField)
		{
			if (fileLocation != null)
			{
				setFileSaved(fileLocation, false);
			}
		}
	}
	
	/**
	 * Return the Image associated with the type of file given through
	 * the location parameter.
	 * 
	 * @param location The location of the file.
	 * @return The Image associated with the file.
	 */
	private Image getFileImage(String location)
	{
		Image img = null;
		
		int fileType = FileUtils.getFileType(location);
		
		if (fileType == FileUtils.JAVA)
		{
			img = javaFileImage;
		}
		else if (fileType == FileUtils.CLASS)
		{
			img = classFileImage;
		}
		else if (fileType == FileUtils.GLSL)
		{
			img = glslFileImage;
		}
		else if (fileType == FileUtils.TXT)
		{
			img = txtFileImage;
		}
		else if (fileType == FileUtils.RTF)
		{
			img = rtfFileImage;
		}
		else if (fileType == FileUtils.EXE)
		{
			img = exeFileImage;
		}
		else if (fileType == FileUtils.ASSEMBLY)
		{
			img = asmFileImage;
		}
		else if (fileType == FileUtils.CPP)
		{
			img = cppFileImage;
		}
		else if (fileType == FileUtils.H)
		{
			img = hFileImage;
		}
		else
		{
			img = fileImage;
		}
		
		return img;
	}
	
	/**
	 * Checks whether the text returned from the codeField is null, or
	 * if it is an empty String.
	 * 
	 * @return Whether the codeField is empty.
	 */
	public boolean isCodeFieldEmpty()
	{
		String text = codeField.getText();
		
		return text == null || text.equals("");
	}
	
	/**
	 * Set the tab and TreeMenu item associated with the file
	 * location to start with a '*' depending if the file is
	 * saved or not.
	 * 
	 * @param location The location of the file to set as saved or not.
	 * @param saved Whether the file is saved or not.
	 */
	public void setFileSaved(String location, boolean saved)
	{
		String locKey	= location.toLowerCase();
		
		String text		= null;
		
		int id			= 0;
		
		if (saved)
		{
			if (treeItemIds.containsKey(locKey))
			{
				id = treeItemIds.get(locKey);
			}
			
			if (tabFileIds.containsKey(locKey))
			{
				int tabId	= tabFileIds.get(locKey);
				
				text		= tabs.getTabText(tabId);
			}
			else if (treeItemLocations.containsValue(locKey))
			{
				text = treeMenu.getTreeItemText(id);
			}
			
			if (text != null && text.startsWith("*"))
			{
				text = text.substring(1);
			
				if (treeItemLocations.containsValue(locKey))
				{
					treeMenu.setTreeItemText(id, text);
				}
				if (tabFileIds.containsKey(locKey))
				{
					tabs.setTabText(tabFileIds.get(locKey), text);
				}
			}
			
			fileCacheSaved.put(locKey, true);
		}
		else
		{
			int tabId = tabFileIds.get(locKey);
			
			text = tabs.getTabText(tabId);
			
			if (!text.startsWith("*"))
			{
				text = "*" + text;
			}
			
			if (treeItemIds.containsKey(locKey))
			{
				id = treeItemIds.get(locKey);
				treeMenu.setTreeItemText(id, text);
			}
			
			tabs.setTabText(tabId, text);
			
			fileCacheSaved.put(locKey, false);
			
			String fileContents = codeField.getText();
			
			fileCache.put(locKey, fileContents);
		}
	}
	
	/**
	 * Add a tab of the file at fileLocation to the TabMenu.
	 * 
	 * @param fileLocation The location of the file to represent.
	 */
	public void addTab(String fileLocation)
	{
		String fileName = FileUtils.getFileName(fileLocation);
		
		int id = tabs.addTab(fileName);
		
		String lowerLoc = fileLocation.toLowerCase();
		
		tabFileLocations.put(id, lowerLoc);
		tabFileIds.put(lowerLoc, id);
	}
	
	/**
	 * Implemented method that is called whenever a tab's close icon
	 * is pressed. If the file in the tab is not saved, ask whether to
	 * save it or not.
	 * 
	 * @param tabId The id of the tab that is closing.
	 * @return Whether to close the tab or not.
	 */
	public boolean tabClosing(int tabId)
	{
		int newId		= tabs.getSelected();
		
		String location = tabFileLocations.get(tabId);
		String result	= null;

		boolean askSave	= false;
		boolean cancel	= false;
		
		if (fileCacheSaved.containsKey(location))
		{
			if (!fileCacheSaved.get(location))
			{
				askSave = true;
			}
		}
		else
		{
			if (!isCodeFieldEmpty())
			{
				askSave = true;
			}
		}
		
		if (askSave)
		{
			OptionDialog saveDialog = new OptionDialog("Save?", "\"" + FileUtils.getFileName(location) + "\" has not been saved, would you like to save it?");
			
			result = saveDialog.open();
		}
		
		if (result == null || result.equals("yes") || result.equals("no"))
		{
			tabFileLocations.remove(tabId);
			tabFileIds.remove(location);
		}
		
		if (result != null)
		{
			if (result.equals("yes"))
			{
				saveFile(location);
			}
			else if (result.equals("no"))
			{
				setFileSaved(location, true);
			}
			else
			{
				cancel = true;
			}
		}
		
		if (cancel)
		{
			
		}
		else
		{
			fileCache.remove(location);
			fileCacheSaved.remove(location);
			
			if (tabId != newId)
			{
				String loc = tabFileLocations.get(newId);
				
				openFile(loc);
			}
			else
			{
				fileLocation = null;
				codeField.setText("");
			}
		}
		
		return !cancel;
	}
	
	/**
	 * Implemented method that is called whenever a tab is selected
	 * in a TabMenu. It then opens the file.
	 * 
	 * @param tabId the id of the tab that was selected.
	 */
	public void tabSelected(int tabId)
	{
		String location = tabFileLocations.get(tabId);
		
		openFile(location);
	}
	
	/**
	 * Implemented method that is called whenever a key is pressed
	 * in a TextField.
	 * 
	 * @param e The CodeFieldEvent that was passed.
	 */
	public void keyPressed(CodeFieldEvent e)
	{
		if (e.getSource() == codeField)
		{
			if (e.getStateMask() == (Integer)PROPERTIES.get("key.control") && e.getKeyCode() == 's')
			{
				saveFile(fileLocation);
			}
		}
	}
	
	/**
	 * Method that deletes the file at the specified location.
	 * 
	 * @param location The location of the file to be deleted.
	 * @return Whether the file was successfully deleted or not.
	 */
	public boolean deleteFile(String location)
	{
//		private HashMap<Integer, String>  treeItemLocations;
//		private HashMap<Integer, String>  treeItemOrigLocations;
//		private HashMap<String, Integer>  treeItemIds;
//		private HashMap<Integer, String>  treeItemDirectories;
//		private HashMap<Integer, String>  menuItemLocations;
//		private HashMap<String, Integer>  menuItems;
//		private HashMap<String, String>   fileCache;
//		private HashMap<String, Boolean>  fileCacheSaved;
//		private HashMap<Integer, String>  tabFileLocations;
//		private HashMap<String, Integer>  tabFileIds;
		
		String locKey = location.toLowerCase();
		
		int treeId    = treeItemIds.get(locKey);
		
		treeItemOrigLocations.remove(treeId);
		treeItemLocations.remove(treeId);
		treeItemDirectories.remove(treeId);
		fileCache.remove(locKey);
		fileCacheSaved.remove(locKey);
		
		treeMenu.removeItem(treeId);
		
		if (tabFileIds.containsKey(locKey))
		{
			int tabId = tabFileIds.get(locKey);
			
			tabFileLocations.remove(tabId);
			tabFileIds.remove(locKey);
		}
		
		boolean deleted = FileUtils.delete(new File(location));
		
		return deleted;
	}
	
	/**
	 * Method to synchronistically update the components of the main
	 * window.
	 */
	public void update()
	{
		console.updateText();
		
		if (filesNeedRefresh)
		{
			refreshFileViewer();
		}
	}
}
