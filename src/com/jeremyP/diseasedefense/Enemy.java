package com.jeremyP.diseasedefense;

import java.util.ArrayList;

import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Pixmap;
import com.jeremyP.diseasedefense.tools.Vector2i;

public class Enemy {
	private Vector2i coords;
	private Pixmap characterSprite;
	private Pixmap currentFrame;
	private Projectile weapon;
	private Graphics g;
	private ArrayList<Pixmap> animation;
	private boolean projectileFlying;
	private Pixmap enemy[];
	private int width;
	private int height;
	private int centerX;
	private int centerY;
	private int totalHealth;
	private int currentHealth;
	private int frameCount;
	private int speed;
	private int points;

	//TODO: ADD POINTS PARAMETER
	public Enemy(Graphics g, Pixmap enemy[], int speed, int health, int points) {
		this.coords = new Vector2i();
		this.g = g;
		this.animation = new ArrayList<Pixmap>();
		this.enemy = enemy;
		loadAnimation();
		this.points = points;
		this.height = animation.get(0).getHeight();
		this.width = animation.get(0).getWidth();
		this.centerX = (getWidth() / 4);
		this.centerY = (getHeight() / 4);
		this.totalHealth = currentHealth = health;
		this.projectileFlying = false;
		this.speed = speed;
		setCoords(0, 0);
	}

	public Vector2i getCoords() {
		return coords;
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public void setX(int x) {
		coords.setX(x);
	}

	public void setY(int y) {
		coords.setY(y);
	}

	public void setCoords(int x, int y) {
		coords.setX(x);
		coords.setY(y);
	}

	public Pixmap getCharSprite() {
		return characterSprite;
	}

	public Projectile getWeapon() {
		return weapon;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	public int getSpeed() {
		return speed;
	}

	public int getHealth() {
		return totalHealth;
	}

	public int getScore(){
		return points;
	}
	
	public boolean isDead(){
		return currentHealth < 1;
	}
	
	public void loadAnimation() {
		
		for(int i=0; i < enemy.length; i++){
			animation.add(enemy[i]);
		}
	}

	public void shoot(Graphics g, int x, int y) {
		if (weapon == null || weapon.getOriginX() > g.getWidth()) {
			weapon = new Projectile(coords.getX(), coords.getY(), x, y);
		}
	}

	public boolean checkProjectileState(Graphics g) {
		if (weapon.isStopped()) {
			this.weapon = null;
			return false;
		}

		else {
			return true;
		}
	}

	public void getHit() {
		this.currentHealth -= 1;
	}

	public boolean hasCollided(Vector2i enemyCoords) {
		int currentX = coords.getX();
		int currentY = coords.getY();

		int distX = Math.abs((enemyCoords.getX() - currentX));
		int distY = Math.abs((enemyCoords.getY() - currentY));

		if (distX > centerX || distY > centerY) {
			return false;
		}

		else {
			return true;
		}
	}

	private void enemyPos(Vector2i playerCoords) {
		int originX = coords.getX();
		int originY = coords.getY();
		int destX = playerCoords.getX();
		int destY = playerCoords.getY();
		float xDiff = (originX - destX);
		float yDiff = (originY - destY);

		if ((xDiff > speed || xDiff < -speed)
				|| (yDiff > speed || yDiff < -speed)) {
			if ((originX < destX)) {
				setX(originX + speed);
			}

			else {
				setX(originX - speed);
			}

			if ((originY < destY)) {
				setY(originY + speed);
			}

			else {
				setY(originY - speed);
			}
		}

		else {

		}
	}

	/*public void newAnimation() {
		animation.clear();
		loadAnimation2();
		newAnimation = true;
	}*/

	private void draw(Graphics g) {
		if (frameCount % 30 == 0) {
			currentFrame = animation.get(0);
		}
		g.drawPixmap(currentFrame, (coords.getX() - (getWidth() / 2)),
				(coords.getY() - (getHeight() / 2)));

		if (frameCount % 30 == 0) {
			animation.remove(0);
		}

		loadAnimation();

		frameCount += 1;
	}

	public void present() {
		draw(g);
	}

	public void update(Vector2i playerCoords) {
		enemyPos(playerCoords);
	}

}
