
package com.jeremyP.diseasedefense;

import java.util.ArrayList;

import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Pixmap;
import com.jeremyP.diseasedefense.tools.Vector2i;

public class Character 
{
	private Vector2i coords;
	private Pixmap characterSprite;
	private ArrayList<Pixmap> animation;
	private Pixmap currentFrame;
	private Projectile weapon;
	private Graphics g;
	private boolean projectileFlying;
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	private int health;
	private int frameCount;
	
	public Character(Graphics g)
	{
		this.coords = new Vector2i();
		this.g = g;
		this.animation = new ArrayList<Pixmap>();
		this.loadAnimation();
		this.height = animation.get(0).getHeight(); 
		this.width = animation.get(0).getWidth();
		this.centerX = (getWidth() / 4);
		this.centerY = (getHeight() / 4);
		this.health = 5;
		this.projectileFlying = false;
		setCoords(g.getWidth() / 2, g.getHeight() / 2);
	}
	
	public Vector2i getCoords()
	{
		return coords;
	}
	
	public int getHeight()
	{
		return height;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public void loadAnimation()
	{
		animation.add(Assets.pc1);
		animation.add(Assets.pc2);
		animation.add(Assets.pc3);
		animation.add(Assets.pc4);
		animation.add(Assets.pc5);
		animation.add(Assets.pc6);
		animation.add(Assets.pc7);
		animation.add(Assets.pc8);
		animation.add(Assets.pc9);
		animation.add(Assets.pc10);
		animation.add(Assets.pc11);
		animation.add(Assets.pc12);
		animation.add(Assets.pc13);
	}
	
	public void setCoords(int x, int y)
	{
		coords.setX(x);
		coords.setY(y);
	}
	
	public Pixmap getCharSprite()
	{
		return characterSprite;
	}
	
	public Projectile getWeapon()
	{
		return weapon;
	}
	
	public void shoot(Graphics g, int x, int y)
	{
		if (weapon == null || weapon.getOriginX() > g.getWidth())
		{
			weapon = new Projectile(coords.getX(), coords.getY(), x, y);
		}
	}
	
	public boolean checkProjectileState(Graphics g)
	{
		if (weapon.isStopped())
		{
			this.weapon = null;
			return false;
		} else {
			return true;
		}
	}
	public boolean changePos(int x, int y)
	{
		int currentX = coords.getX();
		int currentY = coords.getY();
		
		int distX = Math.abs((x - currentX));
		int distY = Math.abs((y - currentY));
		
		if (distX > centerX || distY > centerY)
		{
			projectileFlying = true;
    		shoot(g, x, y);
			return false;
		} else {
			setCoords(x, y);
			return true;
		}
	}
	
	public boolean hasCollided(Vector2i enemyCoords)
	{
		int currentX = coords.getX();
		int currentY = coords.getY();
		
		int distX = Math.abs((enemyCoords.getX() - currentX));
		int distY = Math.abs((enemyCoords.getY() - currentY));
		
		if (distX > centerX || distY > centerY)
		{
			return false;
		}
		
		else
		{
			return true;
		}
	}
		
	private void draw(Graphics g)
	{
		if (frameCount % 30 == 0)
		{
			currentFrame = animation.get(0);
		}
    	g.drawPixmap(currentFrame, (coords.getX() - (getWidth() / 2)), (coords.getY() - (getHeight() / 2)));
    	
    	if (frameCount % 30 == 0)
		{
    		animation.remove(0);
		}
    	
    	if (animation.size() == 0)
    	{
    		loadAnimation();
    	}
    	
    	frameCount += 1;
	}
	
	public void getHit()
	{
		this.health -= 1;
	}
	
	
	public int getHealth()
	{
		return health;
	}
	
	public void present()
	{
		draw(g);
		
		if (projectileFlying && weapon != null)
    	{
          weapon.update(g);
    	  if (!checkProjectileState(g))
          {
        	projectileFlying = false;
          }
    	}
	}
	
	public void update(int x, int y)
	{
		changePos(x, y);
	}
	
	public boolean getFlyingState()
	{
		return projectileFlying;
	}
	
	public void stopProjectile()
	{
		projectileFlying = false;
		weapon = null;
	}
	
	
}
