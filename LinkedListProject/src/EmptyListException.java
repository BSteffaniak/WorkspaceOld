public class EmptyListException extends RuntimeException
{
	public EmptyListException()
	{
		super("The List you are trying to access is empty.");
	}
}