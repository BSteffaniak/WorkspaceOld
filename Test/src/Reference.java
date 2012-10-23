/**
 * File:          Reference.java
 * Author:        Braden Steffaniak
 * Programming:   APCS
 * Last Modified: 6Sep2012
 * Description:   Class that holds the value of a generic.
 */
public class Reference<T>
{  
    private T obj;
    
    /**
     * Create a Reference class that holds the value of the
     * passed generic.
     * 
     * @param obj The generic.
     */
    public Reference(T obj)
    {
    	this.obj = obj;
    }
    
    /**
     * Get the generic type.
     * 
     * @return The generic type.
     */
    public T getObj()
    {
    	return obj;
    }
    
    /**
     * Set the generic type.
     * 
     * @param obj The generic object.
     */
    public void setObj(T obj)
    {
    	this.obj = obj;
    }
}