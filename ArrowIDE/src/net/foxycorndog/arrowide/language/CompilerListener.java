package net.foxycorndog.arrowide.language;

import java.io.PrintStream;

public interface CompilerListener
{
	public void compiled(String files[], CompileOutput outputs[], PrintStream stream, String fileName);
}