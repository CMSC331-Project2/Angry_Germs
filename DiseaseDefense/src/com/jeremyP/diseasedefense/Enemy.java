package com.jeremyP.diseasedefense;

import java.util.ArrayList;

import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Pixmap;
import com.jeremyP.diseasedefense.tools.Vector2i;

public class Enemy
{
	private Vector2i coords;
	private Pixmap characterSprite;
	private Pixmap currentFrame;
	private Projectile weapon;
	private Graphics g;
	private ArrayList<Pixmap> animation;
	private boolean projectileFlying;
	private boolean newAnimation;
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	private int health;
	private int frameCount;
	private int speed;
	
	public Enemy(Graphics g, int speed, int health, boolean newAnimation)
	{
		this.coords = new Vector2i();
		this.g = g;
		this.animation = new ArrayList<Pixmap>();
		this.newAnimation = newAnimation;
		if (!newAnimation)
		{
		 this.loadAnimation1();
		}
		
		else
		{
	     this.loadAnimation1();
		}
		this.height = animation.get(0).getHeight(); 
		this.width = animation.get(0).getWidth();
		this.centerX = (getWidth() / 4);
		this.centerY = (getHeight() / 4);
		this.health = health;
		this.projectileFlying = false;
		this.speed = speed;
		setCoords(0, 0);
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
	
	public void setX(int x)
	{
		coords.setX(x);
	}
	
	public void setY(int y)
	{
		coords.setY(y);
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
	
	public void setSpeed(int speed)
	{
		this.speed = speed;
	}
	
	public int getSpeed()
	{
		return speed;
	}
	
	public int getHealth()
	{
		return health;
	}
	
	public void loadAnimation1()
	{
		animation.add(Assets.badGuy1);
		animation.add(Assets.badGuy2);
		animation.add(Assets.badGuy3);
		animation.add(Assets.badGuy4);
		animation.add(Assets.badGuy5);
		animation.add(Assets.badGuy6);
		animation.add(Assets.badGuy7);
	}

	public void loadAnimation2()
	{
		animation.add(Assets.badGuy2_1);
		animation.add(Assets.badGuy2_2);
		animation.add(Assets.badGuy2_3);
		animation.add(Assets.badGuy2_4);
		animation.add(Assets.badGuy2_5);
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
		}
		
		else
		{
			return true;
		}
	}
	
	public void getHit()
	{
		this.health -= 1;
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
	
	private void enemyPos(Vector2i playerCoords)
	{
		int originX = coords.getX();
		int originY = coords.getY();
		int destX = playerCoords.getX();
		int destY = playerCoords.getY();
		float xDiff = (originX - destX);
		float yDiff = (originY - destY);
		
		if ((xDiff > speed || xDiff < -speed) || (yDiff > speed || yDiff < -speed))
		{
			if ((originX < destX))
			{
				setX(originX + speed);
			}
		
			else
			{
				setX(originX - speed);
			}
		
			if ((originY < destY))
			{
				setY(originY + speed);
			}
		
			else
			{
				setY(originY - speed);
			}
		}
		
		else
		{
			
		}
	}
	
	public void newAnimation()
	{
		animation.clear();
		loadAnimation2();
		newAnimation = true;
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
    		if (!newAnimation)
    		{
    		 loadAnimation1();
    		}
    		
    		else
    		{
    			loadAnimation2();
    		}
    	}
    	
    	frameCount += 1;
	}
	
	public void present()
	{
		draw(g);
	}
	
	public void update(Vector2i playerCoords)
	{	
		enemyPos(playerCoords);
	}
	
	
}
