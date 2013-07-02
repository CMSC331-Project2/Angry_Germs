package com.jeremyP.angrygerms.tools;

public class Vector2f 
{
	private float x;
	private float y;
	
	public Vector2f(float x, float y)
	{
		this.x = x;
		this.y = y;
	}
	
	public Vector2f()
	{
		this.x = 0;
		this.y = 0;
	}
	
	public float getX()
	{
		return this.x;
	}
	
	public float getY()
	{
		return this.y;
	}
	
	public float magnitude()
    {
       return (float) Math.sqrt((x * x) + (y * y));
    }
    
    public Vector2f normalize()
    {
    	float magnitude = this.magnitude();
    	return new Vector2f ((this.x / magnitude), (this.y / magnitude));
    }
    
    public float dotProduct(Vector2f v)
    {
       return this.x * v.getX() + this.y * v.getY();
    }
    
    public Vector2f vectorAdd(Vector2f v)
    {
       return new Vector2f (this.x + v.getX(), this.y + v.getY());
    }
    
    public Vector2f vectorSub(Vector2f v)
    {
       return new Vector2f (this.x - v.getX(), this.y - v.getY());
    }
    
    Vector2f vectorMultiply(float scalar)
    {
       return new Vector2f (x * scalar, y *  scalar);
    }


}
