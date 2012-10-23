public class Reference<T>
{  
    private T obj;
    
    public Reference(T obj)
    {
    	this.obj = obj;
    }
    
    public T getObj()
    {
    	return obj;
    }
    
    public void setObj(T obj)
    {
    	this.obj = obj;
    }
}