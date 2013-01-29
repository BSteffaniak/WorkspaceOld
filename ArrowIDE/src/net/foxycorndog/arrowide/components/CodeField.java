package net.foxycorndog.arrowide.components;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.foxycorndog.arrowide.ArrowIDE;
import net.foxycorndog.arrowide.file.FileUtils;
import net.foxycorndog.arrowide.language.CommentProperties;
import net.foxycorndog.arrowide.language.IdentifierProperties;
import net.foxycorndog.arrowide.language.Keyword;
import net.foxycorndog.arrowide.language.Language;
import net.foxycorndog.arrowide.language.MethodProperties;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.Bullet;
import org.eclipse.swt.custom.ExtendedModifyEvent;
import org.eclipse.swt.custom.ExtendedModifyListener;
import org.eclipse.swt.custom.LineStyleEvent;
import org.eclipse.swt.custom.LineStyleListener;
import org.eclipse.swt.custom.ST;
import org.eclipse.swt.custom.StyleRange;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.dnd.Clipboard;
import org.eclipse.swt.dnd.TextTransfer;
import org.eclipse.swt.dnd.Transfer;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.events.PaintEvent;
import org.eclipse.swt.events.PaintListener;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.FontMetrics;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.GlyphMetrics;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.SelectionListener;
import org.eclipse.swt.widgets.Text;

import static net.foxycorndog.arrowide.ArrowIDE.PROPERTIES;

public class CodeField extends StyledText
{
	private boolean										commentStarted,
			textStarted;
	private boolean										redrawReady;

	private char										textBeginning;

	private int											commentType,
			commentStartLocation;
	private int											textBeginningLocation;
	// private int oldLength, oldLineCount;
	// private int selectionLength, selectionLines;
	private int											lineNumberOffset;
	private int											language;
	private int											charWidth;

	private String										text;

	private StringBuilder								commentTransText;

	private CommentProperties							commentProperties;
	private MethodProperties							methodProperties;
	private IdentifierProperties						identifierProperties;

	private LineStyleListener							lineNumbers,
			lineSpaces, syntaxHighlighting;

	private StyledText									lineNumberText;

	private StyleRange									styleRange;

	private Composite									composite;

	private CodeField									thisField;

	private StyleRange									styles[];

	// private ArrayList<ArrayList<Boolean>> tabs;

	private ArrayList<ContentListener>					contentListeners;
	private ArrayList<CodeFieldListener>				codeFieldListeners;

	private HashMap<String, WordList>					identifierLists;
	private HashMap<WordList, String>					identifierWords;

	private static final String							whitespaceRegex;

	private static final char							whitespaceArray[];
	
	static
	{
		whitespaceRegex = "[.,[ ]/*=()\r\n\t\\[\\]{};[-][+]['][\"]:[-][+]><!]";
		
		whitespaceArray = new char[] { ' ', '.', ',', '/', '*', '=', '(', ')', '[', ']', '{', '}', ';', '\n', '\t', '\r', '-', '+', '\'', '"', ':', '-', '+', '>', '<', '!' };
	}
	
	private class WordStyle
	{
		private StyleRange style;
	}
	
	private class WordList
	{
		private ArrayList<WordStyle> locs;
		
		public WordList()
		{
			locs = new ArrayList<WordStyle>();
		}
		
		public void add(WordStyle loc)
		{
			locs.add(loc);
		}
		
		public WordStyle get(int index)
		{
			return locs.get(index);
		}
		
		public int size()
		{
			return locs.size();
		}
	}
	
	private class SpaceBetweenResult
	{
		private int		count;
		private char	firstChar, firstCharOtherThanSpace, onlyChar, onlyCharOtherThanSpace;
	}
	
	public CodeField(Composite comp)
	{
		super(comp, SWT.MULTI | SWT.V_SCROLL | SWT.H_SCROLL | (Integer)PROPERTIES.get("composite.modifiers"));
		
		thisField = this;
		
		this.composite = comp;
		
		contentListeners      = new ArrayList<ContentListener>();
		codeFieldListeners    = new ArrayList<CodeFieldListener>();
		
		identifierLists           = new HashMap<String, WordList>();
		identifierWords           = new HashMap<WordList, String>();
		
		setText("");
		setBounds(new Rectangle(0, 0, 100, 100));
	    setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, true, true, 1, 1));
	    
	    Font f = FileUtils.loadMonospacedFont(Display.getDefault(), "courier new", "res/fonts/CECOUR.ttf", 10, SWT.NORMAL);
	    setFont(f);
	    
	    GC g = new GC(this);
	    FontMetrics fm = g.getFontMetrics();
	    this.charWidth = fm.getAverageCharWidth();
	    g.dispose();
	    
//	    tabs = new ArrayList<ArrayList<Boolean>>();
//	    tabs.add(new ArrayList<Boolean>());
	    setTabs(4);
//		setAlwaysShowScrollBars(false);
	    
//	    highlightSyntax();
	    
	    Listener identifierSelectorListener = new Listener()
	    {
			public void handleEvent(Event e)
			{
				int offset = thisField.getCaretOffset();
				
				String word = null;
				
				WordList locs[] = identifierLists.values().toArray(new WordList[0]);
				
				for (int i = 0; i < locs.length; i++)
				{
					WordList list = locs[i];
					
					for (int j = 0; j < list.size(); j++)
					{
						WordStyle loc = list.get(j);
						
						if (offset >= loc.style.start && offset <= loc.style.start + loc.style.length + 1)
						{
							word = identifierWords.get(list);
						}
						else
						{
							loc.style.borderStyle = SWT.NONE;
						}
					}
				}
				
				if (word != null)
				{
					setIdentifierSelected(word);
				}
				
				redraw();
			}
	    };
	    
	    addListener(SWT.MouseDown, identifierSelectorListener);
	    addListener(SWT.KeyDown, identifierSelectorListener);
	    
	    addKeyListener(new KeyListener()
	    {
			public void keyPressed(KeyEvent e)
			{
//				if (getSelectionCount() > 0)
//				{
//					selectionLines  = getLineAtOffset(getSelectionCount() + getSelection().x) - getLineAtOffset(getSelection().x);
//					selectionLength = getSelectionCount();
//				}
//				else
//				{
//					selectionLines  = 0;
//					selectionLength = 0;
//				}
//				
////				int xPosition     = getCaretXPosition();
				int caretPosition = getCaretPosition();
				int lineNum       = getCaretLineNumber();
				
				if (isPrintable(e.character) || e.keyCode == 13 || e.keyCode == SWT.BS || e.keyCode == SWT.CR || e.character == '\t')
				{
					contentChanged();
				}
				
				/*
				 * Carry tabs if new line.
				 */
				if (e.character == '\r' || e.character == '\n')
				{
					String tabsStr = "";
					
					int tabsCount  = lineTabCount(lineNum, 1);
					
					char lastChar  = lastChar(lineNum, 1);
					tabsCount     += tabIncrease(lastChar);
					
					for (int i = 0; i < tabsCount; i ++)
					{
						tabsStr += "\t";
					}
		
					String text   = getText();
					int    length = text.length();
					
					insert(tabsStr);
					
					setCaretOffset(caretPosition + tabsStr.length());
					
					if (lastChar == '{')
					{
						String endingBrace = "\r\n" + tabsStr.substring(0, tabsStr.length() - 1) + "}";
						
						insert(endingBrace);
					}
				}
				
				CodeFieldEvent event = new CodeFieldEvent(e.character, e.stateMask, e.keyCode, thisField);
				
				for (int i = codeFieldListeners.size() - 1; i >= 0; i --)
				{
					codeFieldListeners.get(i).keyPressed(event);
				}
			}

			public void keyReleased(KeyEvent e)
			{
				
			}
	    });
	}
	
	public void setIdentifierSelected(String word)
	{
		WordList list = identifierLists.get(word);
		
		for (int i = 0; i < list.size(); i++)
		{
			WordStyle loc = list.get(i);
			
			loc.style.borderStyle = SWT.BORDER_DASH;
		}
	}
	
	public void highlightSyntax()
	{
		redrawReady = false;
		
		Display.getDefault().syncExec(new Runnable()
		{
			public void run()
			{
				text = getText();
			}
		});
		
		StyleRange styleRange = new StyleRange();
		styleRange.start      = 0;
		styleRange.length     = text.length();
		styleRange.foreground = new Color(Display.getDefault(), 0, 0, 0);
		
//		setStyleRange(styleRange);
//		setStyleRanges(new StyleRange[] { styleRange });
		
		ArrayList<StyleRange> styles = new ArrayList<StyleRange>();
		
		styles.add(styleRange);
		
		String strings[] = text.split("\\s*" + whitespaceRegex + "+\\s*");//whitespaceRegex);
		int    offsets[] = new int[strings.length];
		
		int charCount    = 0;
		
		commentTransText = new StringBuilder();
		
		commentStarted   = false;
		
		commentType          = 0;
		commentStartLocation = 0;
		
		SpaceBetweenResult newResult, oldResult;
		
		newResult = calculateSpaceBetween(text, 0, whitespaceArray, styles);
		oldResult = newResult;
		
		for (int i = 0; i < strings.length; i++)
		{
			String prevWord = i > 0 ? strings[i - 1] : "";
			String word     = strings[i];
			
			offsets[i]      = charCount;
			
			boolean isKeyword = Keyword.isKeyword(language, word);
			
//			System.out.println("i: " + i + ",\tword: " + word + ",\toffset: " + offsets[i] + ",\tlength: " + word.length() + ",\tsize: " + text.length());
			
			if (commentStarted || textStarted)
			{
				
			}
			else if (isKeyword)
			{
				Keyword keyword       = Keyword.getKeyword(language, word);
				
				int offset            = charCount;
				int length            = word.length() - 1;
				
				StyleRange range = new StyleRange(offset, length, keyword.getColor(), null);
				
				styles.add(range);
				
//					setStyleRange(styleRange);
//					setStyleRanges(new StyleRange[] { styleRange });
			}
			else if (identifierLists.containsKey(word))
			{
				int offset            = charCount;
				int length            = word.length() - 1;
				
				StyleRange range = new StyleRange(offset, length, new Color(Display.getDefault(), 4, 150, 120), null);
				
				styles.add(range);
				
				WordStyle loc = new WordStyle();
				loc.style = range;
				
				WordList locs = identifierLists.get(word);
				
				locs.add(loc);
			}
			
			boolean textWasStarted    = textStarted;
			boolean commentWasStarted = commentStarted;
			
			newResult = calculateSpaceBetween(text, charCount + word.length(), whitespaceArray, styles);
			
			charCount += word.length() + newResult.count;
			
			if ((commentWasStarted) || textWasStarted)//commentStartLocation < offsets[i] || textStartLocation < offsets[i])
			{
				
			}
			else if (newResult.firstCharOtherThanSpace == '(')
			{
				int offset = offsets[i];
				int length = word.length() - 1;
				
				StyleRange range = new StyleRange(offset, length, methodProperties.COLOR, null);
				
				styles.add(range);
			}
			else
			{
				if (!isKeyword && identifierProperties.isQualified(oldResult.onlyChar, newResult.firstCharOtherThanSpace, word, prevWord))
				{
					if (!identifierLists.containsKey(word))
					{
						int offset = offsets[i];
						int length = word.length() - 1;
						
						StyleRange range = new StyleRange(offset, length, identifierProperties.COLOR, null);
						
						styles.add(range);
						
						WordStyle loc = new WordStyle();
						loc.style = range;
						
						WordList locs = new WordList();
						locs.add(loc);
						
						identifierLists.put(word, locs);
						identifierWords.put(locs, word);
					}
				}
			}
			
			oldResult = newResult;
		}
		
		StyleRange range = null;
		
		if ((range = endComment(text.length() - commentStartLocation + 1)) != null)
		{
			styles.add(range);
		}
		
		else if ((range = endText(text.length() - commentStartLocation)) != null)
		{
			styles.add(range);
		}
		
		thisField.setStyles((StyleRange[])styles.toArray(new StyleRange[0]));
		
		redrawReady = true;
		
		Display.getDefault().syncExec(new Runnable()
		{
			public void run()
			{
				thisField.redraw();
			}
		});
	}
	
	private StyleRange endText(int length)
	{
		StyleRange style = null;
		
		if (textStarted)
		{
			textStarted = false;
			
			{
				int offset = textBeginningLocation;
				
				style = new StyleRange(offset, length, new Color(Display.getDefault(), 180, 100, 30), null);
			}
			
			textBeginning = 0;
		}
		
		return style;
	}
	
	private StyleRange endComment(int length)
	{
		StyleRange style = null;
		
		if (commentStarted)
		{
			commentStarted = false;
			
			{
				int offset = commentStartLocation;
				
				style = new StyleRange(offset, length, commentProperties.COLOR, null);
			}
			
			commentType = 0;
			
			commentTransText = new StringBuilder();
		}
		
		return style;
	}
	
	private SpaceBetweenResult calculateSpaceBetween(String text, int start, char chars[], ArrayList<StyleRange> styles)
	{
		boolean noOtherCharOtherThanSpace = false;
		
		SpaceBetweenResult result = new SpaceBetweenResult();
		
		result.count = 0;
		
		for (int i = start; i < text.length(); i ++)
		{
			char c = text.charAt(i);
			
			if (commentProperties != null && !textStarted)
			{
				// TODO: maybe try only using commentTransText w/o trans local var.
				String trans = commentTransText.toString() + c;
				
				boolean isTrans = (!commentStarted && commentProperties.startsToStartComment(trans)) || (commentStarted && commentProperties.startsToEndComment(trans));
				
				if (isTrans)
				{
					commentTransText.append(c);
				}
				else// if (commentTransText.length() > 0)
				{
					commentTransText = new StringBuilder();
//					while (commentTransText.length() > 0 && !isTrans)
//					{
//						commentTransText.deleteCharAt(0);
//						
//						isTrans = (!commentStarted && commentProperties.startsToStartComment(commentTransText.toString())) || (commentStarted && commentProperties.startsToEndComment(commentTransText.toString()));
//					}
				}
				
				int type = 0;
				
				if (!commentStarted && (type = commentProperties.startsComment(commentTransText.toString())) != 0)
				{
					commentStarted = true;
					
					commentType    = type;
					
					commentStartLocation = start + result.count - commentTransText.length() + 1;
					
					commentTransText = new StringBuilder();
				}
				else if (commentStarted && (type = commentProperties.endsComment(commentTransText.toString())) != 0)
				{
					styles.add(endComment(start + result.count - commentStartLocation));
				}
				
				if (c == '\n')
				{
					if (commentStarted && commentType == CommentProperties.SINGLE_LINE)
					{
						styles.add(endComment(start + result.count - commentStartLocation + 1));
					}
				}
			}
			
			if (!commentStarted)
			{
				if (c == '"' || c == '\'')
				{
					if (!textStarted)
					{
						textStarted = true;
						textBeginning = c;
						textBeginningLocation = start + result.count;
					}
					else
					{
						if (c == textBeginning)
						{
							textStarted = false;
							
							{
								int offset = textBeginningLocation;
								int length = start + result.count - offset;
								
								styles.add(new StyleRange(offset, length, new Color(Display.getDefault(), 180, 100, 30), null));
							}
							
							textBeginning = 0;
						}
					}
				}
			}
			
			if (containsChar(chars, c))
			{
				result.count++;
				
				if (c != ' ')
				{
					if (result.onlyCharOtherThanSpace == 0 && !noOtherCharOtherThanSpace)
					{
						result.onlyCharOtherThanSpace = c;
					}
					else if (!noOtherCharOtherThanSpace && c != result.onlyCharOtherThanSpace)
					{
						noOtherCharOtherThanSpace = true;
						
						result.onlyCharOtherThanSpace = 0;
					}
					
					if (result.firstCharOtherThanSpace == 0)
					{
						result.firstCharOtherThanSpace = c;
					}
				}
				
				if (result.firstChar == 0)
				{
					result.firstChar = c;
					result.onlyChar  = c;
				}
				else
				{
					if (result.onlyChar != c)
					{
						result.onlyChar = 0;
					}
				}
			}
			else
			{
				return result;
			}
		}
		
		return result;
	}
	
	private boolean containsChar(char chars[], char key)
	{
		for (int i = 0; i < chars.length; i ++)
		{
			if (key == chars[i])
			{
				return true;
			}
		}
		
		return false;
	}
	
	private int getCaretLineNumber()
	{
		return getLineAtOffset(getCaretPosition());
	}
	
	private int getCaretPosition()
	{
		return getCaretOffset();
	}
	
	private char lastCharacter(int charactersBack)
	{
		int textLength = getText().length();
		
		if (textLength >= charactersBack)
		{
			return getText().charAt(textLength - charactersBack);
		}
		
		return 0;
	}
	
	private boolean lastCharacterIsTab(int lineNum, int charactersBack)
	{
		String line = getLine(lineNum);
		
		int size    = line.length();
		
		if (size >= charactersBack)
		{
			return line.charAt(size - charactersBack - 1) == '\t';
		}
		else if (size == 0)
		{
			return true;
		}
		
		return false;
	}
	
	private char lastChar(int lineNumber, int linesBack)
	{
		String line = getLine(lineNumber - linesBack).toLowerCase();
		
		if (line.length() > 0)
		{
			return line.charAt(line.length() - 1);
		}
		
		return 0;
	}
	
	private int tabIncrease(char c)
	{
		if (c == '{')
		{
			return 1;
		}
		
		return 0;
	}
	
	private int lineTabCount(int lineNumber, int linesBack)
	{
		String line = getLine(lineNumber - linesBack);
		
		int size    = line.length();
		
		for (int i = 0; i < size; i ++)
		{
			if (line.charAt(i) != '\t')
			{
				return i;
			}
		}
		
		return size;
	}
	
	public boolean isPrintableChar(char c)
	{
	    Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
	    
	    return ((!Character.isISOControl(c)) &&
	            c != java.awt.event.KeyEvent.CHAR_UNDEFINED &&
	            block != null &&
	            block != Character.UnicodeBlock.SPECIALS) ||
	            (c == '\n' || c == '\t' || c == '\r');
	}
	
	private boolean isPrintable(char c)
	{
//		Character.UnicodeBlock block = Character.UnicodeBlock.of(c);
//		
//		return (!Character.isISOControl(c) && block != null && block != Character.UnicodeBlock.SPECIALS);
		
		return (((int)c >= 32 && (int)c < 127));
	}
	
	private void contentChanged()
	{
		for (int i = contentListeners.size() - 1; i >= 0; i--)
		{
			ContentEvent event = new ContentEvent();
			
			event.setSource(this);
			
			contentListeners.get(i).contentChanged(event);
		}
		
		new Thread()
		{
			public void run()
			{
				highlightSyntax();
			}
		}.start();
	}
	
	public void setText(String text)
	{
		setText(text, false);
	}
	
	public void setText(String text, boolean loaded)
	{
		setText(text, loaded, true);
	}
	
	public void setText(String text, boolean loaded, boolean parse)
	{
		super.setText(text);
		
		redraw();
	}
	
	public String getRawText()
	{
		return getText().replace(new Character((char)13), ' ');
	}
	
	public String getWritableText()
	{
		return getText();//.replace("\n", "\r\n");
	}
	
	public int getX()
	{
		return getBounds().x;
	}
	
	public int getY()
	{
		return getBounds().y;
	}
	
	public int getWidth()
	{
		int width = getBounds().width;
		
		return width;
	}
	
	public int getHeight()
	{
		return getBounds().height;
	}
	
	public int getLanguage()
	{
		return language;
	}
	
	public void setLanguage(int language)
	{
		this.language     = language;
		
		commentProperties    = Language.getCommentProperties(language);
		methodProperties     = Language.getMethodProperties(language);
		identifierProperties = Language.getIdentifierProperties(language);
		
		if (language > 0)
		{
			highlightSyntax();
		}
	}
	
	public void addContentListener(ContentListener listener)
	{
		contentListeners.add(listener);
	}
	
	public void addCodeFieldListener(CodeFieldListener listener)
	{
		codeFieldListeners.add(listener);
	}
	
//	public void setSize(int width, int height)
//	{
//		if (lineNumberText != null)
//		{
//			lineNumberText.setSize((new String(getLineCount() + ".").length()) * charWidth, height - getHorizontalBar().getSize().y + 1);
//		}
//		
//		super.setSize(width, height);
//	}
	
	public void setLocation(int x, int y)
	{
		if (lineNumberText != null)
		{
			lineNumberText.setLocation(x, y);
		}
		
		super.setLocation(x, y);
	}
	
	public void paste()
	{
		String before = getText();
		
		super.paste();
		
		if (!getText().equals(before))
		{
			contentChanged();
		}
	}
	
	public StyleRange[] getStyles()
	{
		return styles;
	}
	
	private void setStyles(StyleRange styles[])
	{
		this.styles = styles;
	}
	
	public void setShowLineNumbers(boolean show)
	{
		if (show)
		{
//			lineNumberText = new StyledText(composite, SWT.MULTI);
//			lineNumberText.setFont(getFont());
//			lineNumberText.setSize(new String(getLineCount() + ".").length() * charWidth, getHeight() - getHorizontalBar().getSize().y + 1);
//			lineNumberText.setBackground(new Color(Display.getCurrent(), 200, 200, 200));
//			lineNumberText.moveAbove(this);
//			
////			composite.
//			
//			setFocus();
			
//			lineNumbers = new LineStyleListener()
//		    {
//				public void lineGetStyle(LineStyleEvent e)
//				{
//					e.bulletIndex = lineNumberText.getLineAtOffset(e.lineOffset);
//					e.alignment   = SWT.RIGHT;
//					
//					String count  = e.bulletIndex + 1 + "";
//					
//					String prefix = "";
//					
//					int l = count.length();
//					
//					int biggestLength = new String((getLineCount()) + "").length();
//					
//					while (l++ < biggestLength)
//					{
//						prefix += " ";
//					}
//					
//					String text = prefix + count + ".";
//					
//					StyleRange style = new StyleRange();
//					style.metrics = new GlyphMetrics(0, 0, (text.length() + 1) * charWidth);
////					style.background = new Color(Display.getCurrent(), 200, 200, 200);
//					
//					Bullet bullet = new Bullet(ST.BULLET_TEXT, style);
//					
//					bullet.text = text;
//					
//					e.bullet = bullet;
//				}
//		    };
//			
//			lineNumberText.addLineStyleListener(lineNumbers);
			
			lineNumbers = new LineStyleListener()
		    {
				public void lineGetStyle(LineStyleEvent e)
				{
//					e.bulletIndex = getLineAtOffset(e.lineOffset);
//					e.alignment = SWT.RIGHT;
//					
//					String count = e.bulletIndex + 1 + "";
//					
//					String prefix = "";
//					
//					int l = 0;
//					
//					int biggestLength = new String((getLineCount()) + ".").length();
//					
//					while (l++ < biggestLength)
//					{
//						prefix += " ";
//					}
//					
//					int wid = (prefix.length());
//					
//					StyleRange style = new StyleRange();
//					style.metrics = new GlyphMetrics(0, 0, wid * charWidth);
//					
//					Bullet bullet = new Bullet(ST.BULLET_TEXT, style);
//					
//					bullet.text = prefix;
//					
//					e.bullet = bullet;
					
					e.bulletIndex = getLineAtOffset(e.lineOffset);
					e.alignment   = SWT.RIGHT;
					
					String count  = e.bulletIndex + 1 + "";
					
					String prefix = "";
					
					String text = count + ".";
					
					int offset = new String((getLineCount()) + ".").length();
					
					int wid = (((offset % 4) + 4 - (offset % 4)) + (4 * (offset / 5)));
					
					lineNumberOffset = wid - offset;
					
					for (int i = 0; i < wid - text.length(); i ++)
					{
						prefix += " ";
					}
					
					text = prefix + text;
					
					StyleRange style = new StyleRange();
					style.metrics = new GlyphMetrics(0, 0, wid * charWidth);
					style.background = new Color(Display.getCurrent(), 200, 200, 200);
					
					Bullet bullet = new Bullet(ST.BULLET_TEXT, style);
					
					bullet.text = text;
					
					e.bullet = bullet;
				}
		    };
		    
		    syntaxHighlighting = new LineStyleListener()
		    {
				public void lineGetStyle(LineStyleEvent event)
				{
//					e.bulletIndex = getLineAtOffset(e.lineOffset);
//					e.alignment = SWT.RIGHT;
//					
//					String count = e.bulletIndex + 1 + "";
//					
//					String prefix = "";
//					
//					int l = 0;
//					
//					int biggestLength = new String((getLineCount()) + ".").length();
//					
//					while (l++ < biggestLength)
//					{
//						prefix += " ";
//					}
//					
//					int wid = (prefix.length());
//					
//					StyleRange style = new StyleRange();
//					style.metrics = new GlyphMetrics(0, 0, wid * charWidth);
//					
//					Bullet bullet = new Bullet(ST.BULLET_TEXT, style);
//					
//					bullet.text = prefix;
//					
//					e.bullet = bullet;
					
					if (redrawReady)
					{
						StyleRange styles[] = thisField.getStyles();
						
						event.styles = styles;
					}
				}
		    };
		    
		    addLineStyleListener(lineNumbers);
		    addLineStyleListener(syntaxHighlighting);
		}
		else
		{
			lineNumberText.removeLineStyleListener(lineNumbers);
			removeLineStyleListener(lineSpaces);
			lineNumberText.dispose();
			lineNumberText = null;
		}
	}
}