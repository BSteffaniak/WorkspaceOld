public interface Stack
{
	public boolean isEmpty();
	
	public Object push(Object o);
	
	public Object pop();
	
	public Object peek();
	
	/**
	 * @return position of element on stack, top element = 1 ...return -1 if not found
	 */
	public int search(Object o);
}