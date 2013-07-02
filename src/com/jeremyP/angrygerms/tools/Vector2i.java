package com.jeremyP.angrygerms.tools;

public class Vector2i 
{
	private int x;
	private int y;
	
	public Vector2i(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2i()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public int getX()
	{
		return this.x;
	}
	
	public int getY()
	{
		return this.y;
	}
	
	public void setX(int x)
	{
		this.x = x;
	}
	
	public void setY(int y)
	{
		this.y = y;
	}
	
	public int magnitude()
    {
       return (int) Math.sqrt((x * x) + (y * y));
    }
    
    public Vector2i normalize()
    {
    	int magnitude = Math.abs(this.magnitude());
    	return new Vector2i ((this.x / magnitude), (this.y / magnitude));
    }
    
    public float dotProduct(Vector2i v)
    {
       return this.x * v.getX() + this.y * v.getY();
    }
    
    public Vector2i vectorAdd(Vector2i v)
    {
       return new Vector2i (this.x + v.getX(), this.y + v.getY());
    }
    
    public Vector2i vectorSub(Vector2i v)
    {
       return new Vector2i (this.x - v.getX(), this.y - v.getY());
    }
    
    public Vector2i scalarMultiply(int scalar)
    {
       return new Vector2i (x * scalar, y *  scalar);
    }


}