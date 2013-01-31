package net.foxycorndog.arrowide.formatter;

import net.foxycorndog.arrowide.components.CodeField;
import net.foxycorndog.arrowide.language.CommentProperties;

import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Point;

public class Formatter
{
	public static void indent(StyledText text)
	{
		if (text.getSelectionCount() == 0)
		{
			return;
		}
		
		Point sel = text.getSelection();
		int start = sel.x;
		
		String txt = text.getText();
		
		start--;
		
		while (start >= 0 && (txt.charAt(start) != '\n' && txt.charAt(start) != '\r'))
		{
			start--;
		}
		
		start++;
		
		int offset  = start;
		int offsetY = 0;
		
		while (offset < sel.y + offsetY)
		{
			text.setSelection(offset);
			text.insert("\t");
			offsetY++;
			
			int lineNum = text.getLineAtOffset(offset);
			
			offset += text.getLine(lineNum).length() + 2;
		}
		
		text.setSelection(sel.x + 1, sel.y + offsetY);
	}
	
	public static void unIndent(StyledText text)
	{
		if (text.getSelectionCount() == 0)
		{
			return;
		}
		
		Point sel = text.getSelection();
		int start = sel.x;
		
		StringBuilder builder = new StringBuilder(text.getText());
		
		start--;
		
		while (start >= 0 && (builder.charAt(start) != '\n' && builder.charAt(start) != '\r'))
		{
			start--;
		}
		
		start++;
		
		int offset  = start;
		int offsetY = 0;
		
		StringBuilder txt = new StringBuilder(builder.subSequence(start, sel.y));
		
		builder.delete(start, sel.y);
		
		int locStart    = 0;
		int amount      = 0;
		int otherAmount = 0;
		
		int startOffset = txt.charAt(locStart) == '\t' ? 1 : 0;
		
		while (locStart < txt.length())
		{
			if (txt.charAt(locStart) == '\t')
			{
				txt.deleteCharAt(locStart);
				amount = 1;
				otherAmount += 1;
			}
			else
			{
				amount = 2;
				otherAmount += 0;
			}
			
			offsetY++;
			
			int lineNum = text.getLineAtOffset(offset);
			
			locStart += text.getLine(lineNum).length() + amount;
			
			offset = start + locStart + otherAmount;
		}
		
		builder.insert(start, txt);
		
		text.setText(builder.toString());
		
		text.setSelection(sel.x - startOffset, sel.y - otherAmount);//sel.x - 1, sel.y + offsetY);
	}
	
	public static void format(CodeField text)
	{
		CommentProperties comProp = text.getCommentProperties();
		
		if (comProp == null)
		{
			return;
		}
		
		StringBuilder builder = new StringBuilder(text.getText());
		
		int tabs   = 0;
		int start  = 0;
		int offset = 0;
		
		while (start < builder.length())
		{
			int lineNum    = text.getLineAtOffset(offset);
			
			int lineLength = text.getLine(lineNum).length();
			
			int index      = start;
			
			int numRemoved = 0;
			
			while (((builder.charAt(index) == '\t') || (builder.charAt(index) == '\n') || (builder.charAt(index) == '\r')) && index < start + lineLength)
			{
				if (builder.charAt(index) == '\t')
				{
					builder.deleteCharAt(index);
					numRemoved++;
				}
				else
				{
					index++;
				}
			}
			
			start += lineLength + 2 - numRemoved;
			offset += lineLength + 2;
		}
		
		
		
		text.setText(builder.toString());
	}
}