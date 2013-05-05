package com.jeremyP.diseasedefense;

import java.util.List;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Input.TouchEvent;
import com.jeremyP.diseasedefense.framework.Screen;

public class GameScreen extends Screen {
	private Character character;
	private Enemy enemy;
	private EnemyPool enemies;
	private GameState state;
	private Graphics g;
	private int timer;
	private int min;
	private int xOffset;
	private int xEnemyOffset;
	private int xCoord;
	private int yCoord;
	private int enemiesKilled;
	private int enemySpeed = 1;
	private int numEnemies = 1;
	private int enemyHealth = 1;
	private boolean newAnimation;

	enum GameState {
		Ready, Running, Paused, GameOver
	}

	@SuppressLint("UseValueOf")
	public GameScreen(Game game) {
		super(game);
		state = GameState.Running;
		g = game.getGraphics();
		character = new Character(g);
		enemy = new Enemy(g, enemySpeed, enemyHealth, false);
		enemies = new EnemyPool(g, character, numEnemies, enemySpeed);
		timer = 0;
		min = 0;
		xCoord = character.getCoords().getX();
		yCoord = character.getCoords().getY();
		enemiesKilled = 0;
		this.newAnimation = false;
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if (state == GameState.Running) {
			updateRunning(touchEvents, deltaTime);
		}

		if (state == GameState.GameOver) {
			updateGameOver(touchEvents);
		}

		if (state == GameState.Paused) {
			updatePaused(touchEvents);
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			int x = event.x;
			int y = event.y;

			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				if (character != null) {
					character.update(x, y);
				}
			}

			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x < 64 && event.y < 64) {
					if (Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.Paused;
					return;
				}
			}
		}

		if (enemy != null && character != null) {
			enemy.update(character.getCoords());
		}

		if (enemy != null && character != null && character.getFlyingState()
				&& enemy.hasCollided(character.getWeapon().getOrigin())) {
			enemy.getHit();
			character.stopFlying();
			if (enemy.getHealth() <= 0) {
				enemy = null;
				character.stopFlying();
				enemiesKilled += 1;
			}
		}

		if (enemy != null && character != null
				&& character.hasCollided(enemy.getCoords())) {
			enemy = null;
			character.getHit();
			if (character.getHealth() <= 0) {
				character = null;
				state = GameState.GameOver;
			}
		}

		if (enemy == null && timer % 100 == 0) {
			if (enemiesKilled % 30 == 0 && enemiesKilled != 0) {
				enemyHealth += 1;
				numEnemies += 1;
				newAnimation = true;
				createEnemy();
			}

			else {
				createEnemy();
			}
		}

		timer += 1;
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x >= 128 && event.x <= 192 && event.y >= 200
						&& event.y <= 264) {
					g.clear(0);
					if (Settings.soundEnabled) {
						Assets.click.play(1);
					}
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x > 80 && event.x <= 240) {
					if (event.y > 100 && event.y <= 148) {
						if (Settings.soundEnabled)
							Assets.click.play(1);
						state = GameState.Running;
						return;
					}
					if (event.y > 148 && event.y < 196) {
						if (Settings.soundEnabled)
							Assets.click.play(1);
						g.clear(0);
						game.setScreen(new MainMenuScreen(game));
						return;
					}
				}
			}
		}
	}

	public void drawGameOver() {
		g.clear(0);
		g.drawPixmap(Assets.gameover, -15, 100);
	}

	public void drawRunning() {
		drawBackground();
		drawPauseButton();

		for (int i = 0; i < character.getHealth(); i++) {
			g.drawRect(xOffset + 10, g.getHeight() - 20, 25, 25, Color.BLUE);
			xOffset += 50;
		}

		if (enemy != null) {
			for (int i = 0; i < enemy.getHealth(); i++) {
				g.drawRect(xEnemyOffset + 275, 20, 25, 25, Color.GREEN);
				xEnemyOffset -= 50;
			}
		}

		if (enemy != null) {
			enemy.present();
		}

		if (character != null) {
			character.present();
		}
		xOffset = 0;
		xEnemyOffset = 0;

		drawText(g, Integer.toString(enemiesKilled), g.getWidth() - 60,
				g.getHeight() - 35);
	}

	public void enemyCheck(Enemy enemy) {
		if (enemy != null && character != null) {
			enemy.update(character.getCoords());
		}

		if (enemy != null && character != null && character.getFlyingState()
				&& enemy.hasCollided(character.getWeapon().getOrigin())) {
			enemy.getHit();
			character.stopFlying();
			if (enemy.getHealth() <= 0) {
				enemy = null;
				character.stopFlying();
				enemiesKilled += 1;
			}
		}

		if (enemy != null && character != null
				&& character.hasCollided(enemy.getCoords())) {
			enemy = null;
			character.getHit();
			if (character.getHealth() <= 0) {
				character = null;
				state = GameState.GameOver;
			}
		}

		if (enemy == null && timer % 100 == 0) {
			if (enemiesKilled % 30 == 0 && enemiesKilled != 0) {
				enemyHealth += 1;
				numEnemies += 1;
				newAnimation = true;
				createEnemy();
			}

			else {
				createEnemy();
			}
		}

		timer += 1;
	}

	public void createEnemy() {
		enemy = new Enemy(g, enemySpeed, enemyHealth, newAnimation);
		xCoord = character.getCoords().getX();
		yCoord = character.getCoords().getY();

		while (xCoord == character.getCoords().getX()
				&& yCoord == character.getCoords().getY()) {
			xCoord = min + (int) (Math.random() * ((g.getWidth() - min) + 1));
			yCoord = min + (int) (Math.random() * ((g.getHeight() - min) + 1));
		}

		if (newAnimation) {
			enemy.newAnimation();
		}

		enemy.setCoords(xCoord, yCoord);
	}

	@Override
	public void present(float deltaTime) {
		if (state == GameState.GameOver) {
			drawGameOver();
		}

		if (state == GameState.Running) {
			drawRunning();
		}

		if (state == GameState.Paused) {
			drawPaused();
		}

	}

	private void drawPaused() {
		Graphics g = game.getGraphics();

		g.drawPixmap(Assets.pauseMenu, 80, 100);
	}

	private void drawPauseButton() {
		g.drawPixmap(Assets.pauseButton, 1, 1);
	}

	/**
	 * draw the Background image of the current level
	 */
	private void drawBackground() {
		if (enemiesKilled < 15) {
			g.drawPixmap(Assets.background1, 0, 0);
		}else if(enemiesKilled < 30){
			g.drawPixmap(Assets.background2, 0, 0);
		}else if(enemiesKilled < 45){
			g.drawPixmap(Assets.background3, 0, 0);
		}else if(enemiesKilled < 60){
			g.drawPixmap(Assets.background4, 0, 0);
		}else if(enemiesKilled < 75){
			g.drawPixmap(Assets.background5, 0, 0);
		}else if(enemiesKilled < 90){
			g.drawPixmap(Assets.background6, 0, 0);
		}
	}

	public void drawText(Graphics g, String line, int x, int y) {
		int len = line.length();
		for (int i = 0; i < len; i++) {
			char character = line.charAt(i);

			if (character == ' ') {
				x += 20;
				continue;
			}

			int srcX = 0;
			int srcWidth = 0;
			if (character == '.') {
				srcX = 200;
				srcWidth = 10;
			} else {
				srcX = (character - '0') * 20;
				srcWidth = 20;
			}

			g.drawPixmap(Assets.numbers, x, y, srcX, 0, srcWidth, 32);
			x += srcWidth;
		}
	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {

	}
}
