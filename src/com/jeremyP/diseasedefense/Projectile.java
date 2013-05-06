package com.jeremyP.diseasedefense;

import android.graphics.Color;

import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.tools.Vector2i;

public class Projectile 
{
	private Vector2i origin;
	private Vector2i destination;
	private int speed;
	private float damage;
	private boolean stopped;
	
	public Projectile(int x, int y, int dx, int dy)
	{
		this.origin = new Vector2i(x, y);
		this.destination = new Vector2i(dx,dy);
		this.speed = 5;
		this.damage = 100;
		this.stopped = true;
	}
	
	public void drawProjectile(Graphics g)
	{
		g.drawRect(origin.getX(), origin.getY(), 5, 10, Color.WHITE);
	}
	
	public void setOrigin(int x, int y)
	{
		this.origin.setX(x);
		this.origin.setX(y);
	}
	
	public int getOriginX()
	{
		return origin.getX();
	}
	
	public int getOriginY()
	{
		return origin.getY();
	}
	
	public int getDestinationX()
	{
		return destination.getX();
	}
	
	public int getDestinationY()
	{
		return destination.getY();
	}
	
	public boolean hasCollided(Vector2i enemyCoords)
	{
		int currentX = origin.getX();
		int currentY = origin.getY();
		
		int distX = Math.abs((enemyCoords.getX() - currentX));
		int distY = Math.abs((enemyCoords.getY() - currentY));
		
		if (distX > currentX || distY > currentY)
		{
			return false;
		}  else {
			return true;
		}
	}
	
	public Vector2i getOrigin()
	{
		return origin;
	}
	
	private void projectilePos()
	{
		int originX = getOriginX();
		int originY = getOriginY();
		int destX = getDestinationX();
		int destY = getDestinationY();
		float xDiff = (originX - destX);
		float yDiff = (originY - destY);
		
		if ((xDiff > speed || xDiff < -speed) || (yDiff > speed || yDiff < -speed))
		{
			this.stopped = false;
			if ((originX < destX)) {
				origin.setX(originX + speed);
			} else {
				origin.setX(originX - speed);
			}
		
			if ((originY < destY)) {
				origin.setY(originY + speed);
			} else {
				origin.setY(originY - speed);
			}
		} else {
			this.stopped = true;
		}
	}
	
	public boolean isStopped()
	{
		return stopped;
	}
	
	public void update(Graphics g)
	{
		projectilePos();
		drawProjectile(g);
	}
	
}
