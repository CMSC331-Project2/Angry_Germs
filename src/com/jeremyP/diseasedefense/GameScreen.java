package com.jeremyP.diseasedefense;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Input.TouchEvent;
import com.jeremyP.diseasedefense.framework.Music;
import com.jeremyP.diseasedefense.framework.Pixmap;
import com.jeremyP.diseasedefense.framework.Screen;

public class GameScreen extends Screen {
	private Character character;
	private ArrayList<Enemy> enemy;
	private int enemyindex;
	private EnemyPool enemies;
	private GameState state;
	private Graphics g;
	//private int timer;
	private int min;
	private int xOffset;
	private int xEnemyOffset;
	private int xCoord;
	private int yCoord;
	//private int enemiesKilled;
	//private int enemySpeed = 1;
	private int numEnemies = 1;
	//private int enemyHealth = 1;
	private Level level;
	private Music runningMusic;
	private int humanPhase;

	enum GameState {
		LevelUp, Upgrade, Running, Paused, GameOver, Beaten, HumanPhase
	}

	@SuppressLint("UseValueOf")
	public GameScreen(Game game) {
		super(game);
		state = GameState.Running;
		level = new Level();
		runningMusic = Assets.levelMusic[level.whatLevel()-1];
		runningMusic.setLooping(true);
		humanPhase = 1;
		g = game.getGraphics();
		character = new Character(g);
		enemy = new ArrayList<Enemy>();
		enemy.add(new Enemy(g, Assets.badGuys[level.whatLevel()-1], 1, 1, 1));	//Add one L1 enemy
		enemyindex = 0;
		enemies = new EnemyPool(g, character, numEnemies, 1);
		min = 0;
		xCoord = character.getCoords().getX();
		yCoord = character.getCoords().getY();
		//enemiesKilled = 0;
	}

	/**
	 * This method is the main logical loop of the game. It determines which state the game is
	 * in by checking which enum state is equal to. GameState.Running is where the main action takes
	 * place. GameState.GameOver is when the player loses all of their health and is sent to a game over
	 * screen. GameState.Beaten is the screen shown when the player wins the game. GameState.Paused is
	 * when the player pauses the game.
	 * 
	 * No drawing happens within any of the update loops.
	 */
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		System.out.println("Updating...");
		if (state == GameState.Running) {
			System.out.println("Running");
			updateRunning(touchEvents, deltaTime);
		}else if (state == GameState.LevelUp) {
			System.out.println("LevelUp");
			updateLevel(touchEvents);
		}else if(state == GameState.Upgrade){
			System.out.println("Upgrade");
			updateUpgrade(touchEvents);
		}else if (state == GameState.GameOver) {
			System.out.println("Gameover");
			updateGameOver(touchEvents);
		}else if (state == GameState.Beaten){
			System.out.println("Beaten");
			updateBeaten(touchEvents);
		}else if (state == GameState.Paused) {
			updatePaused(touchEvents);
		}else if (state == GameState.HumanPhase) {
			System.out.println("HumanPhase");
			updateHumanPhase(touchEvents);
		}
	}

	private void updateHumanPhase(List<TouchEvent> touchEvents) {

		try {
			
			//Update the first phase
			if(humanPhase == 1){
				Thread.sleep(700);
				state = GameState.HumanPhase;
				humanPhase++;

			//Finished update?
			}else if(humanPhase == 6){
				Thread.sleep(1000);
				
				//Is the game beaten?
				if(level.isEnd()){
					state = GameState.Beaten;
				}else{
					state = GameState.Running;
				}
				humanPhase = 1;
			
			//Update periodically through the phases
			}else{

				Thread.sleep(250);
				Assets.humanphase.play(1);
				state = GameState.HumanPhase;
				humanPhase++;

			}

		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void updateUpgrade(List<TouchEvent> touchEvents) {
		
	}

	private void updateLevel(List<TouchEvent> touchEvents) {
		
		game.getInput().getKeyEvents();
		
		//Change the music for new level
		//TODO: Make the music fade out as the level changes
		if(runningMusic.isPlaying()){
			runningMusic.stop();
			runningMusic = Assets.levelMusic[level.whatLevel()-1];
		}

		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				int contin_x = g.getWidth()/2 - Assets.contin.getWidth()/2;
				int contin_y = (int) (g.getHeight() - (g.getHeight()*.05)) - Assets.contin.getHeight();
				
				int upgrade_x = g.getWidth()/2 - Assets.upgrade.getWidth()/2;
				int upgrade_y = (int) (g.getHeight() - (g.getHeight()*.22)) - Assets.upgrade.getHeight();
				
				//Did you click the Continue button?
				if(event.x > contin_x && event.x < contin_x + Assets.contin.getWidth() && event.y > contin_y && event.y < contin_y + Assets.contin.getHeight()){
					Assets.click.play(1);
					level.resetScore();
					state = GameState.HumanPhase;
					return;
				}
				
				//Did you click the Upgrade button?
				if(event.x > upgrade_x && event.x < upgrade_x + Assets.upgrade.getWidth() && event.y > upgrade_y && event.y < upgrade_y + Assets.upgrade.getHeight()){
					Assets.click.play(1);
					if(level.isEnd()){
						state = GameState.Beaten;
					}else{
						level.resetScore();
						state = GameState.Running;
					}
				}
			}
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		
		//Play the music if it isn't already playing
		//NOTE: This usually will only be called when the level is just beginning
		if(!runningMusic.isPlaying())
			runningMusic.play();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			int x = event.x;
			int y = event.y;

			//Moves your character to where user dragged character
			if (event.type == TouchEvent.TOUCH_DRAGGED) {
				if (character != null) {
					character.update(x, y);
				}
			}

			//Paused game
			if (event.type == TouchEvent.TOUCH_UP) {
				if (event.x < 64 && event.y < 64) {
					//if (Settings.soundEnabled)
						Assets.click.play(1);
					state = GameState.Paused;
					return;
				}
			}
		}

		//Move enemy towards the character
		if (enemyindex != -1 && character != null) {
			enemy.get(enemyindex).update(character.getCoords());
		}

		//The enemy has been hit
		if (enemyindex != -1 && character != null && character.getFlyingState() && enemy.get(enemyindex).hasCollided(character.getWeapon().getOrigin())) 
		{
			//System.out.println(enemy.get(enemyindex).getCurrentHealth());
			enemy.get(enemyindex).getHit();
			//System.out.println("Hit: " + enemy.get(enemyindex).getCurrentHealth());
			//System.out.println("Hit: " + enemy.get(enemyindex).getCurrentHealth());
			character.stopProjectile();
			
			if (enemy.get(enemyindex).isDead()) 
			{
				Assets.kill.play(1);
				enemy.get(enemyindex).revive();
				character.stopProjectile();
				level.addScore(enemy.get(enemyindex).getScore());
				enemyindex = -1;

				//Have you finished the level?
				if(level.isLevelEnd(false)){
					
					state = GameState.LevelUp;
					
					Assets.level_up.play(1);
					
					int enemySum = enemy.size();
					
					//Create new level enemy
					//TODO: Add speed variation to stronger/different enemies
					int speed = 1;
					int health = enemy.get(enemySum-1).getTotalHealth() + 1;
					int points = enemy.get(enemySum-1).getSpeed() + 1;
					
					//This if statement will stay here until more enemy variations are created
					//TODO: Change the 4 as more enemy pictures are made
					if(level.whatLevel() < 5){
						Enemy newenemy = new Enemy(g, Assets.badGuys[level.whatLevel()-1] ,speed, health, points);
						
						//Add new level enemy
						int moreEnemies = enemySum * 2;
						System.out.println(moreEnemies);
						for(int i=0; i < moreEnemies; i++){
							enemy.add(newenemy);
							System.out.println("Enemy added " + enemy.size());
						}
					}
					
				}
			}else{
				Assets.hit.play(1);
			}
		}

		//Enemy has hit character
		if (enemyindex != -1 && character != null && character.hasCollided(enemy.get(enemyindex).getCoords())) {
			enemy.get(enemyindex).revive();
			enemyindex = -1;
			character.getHit();
			Assets.hit.play(1);
			if (character.getHealth() <= 0) {
				character = null;
				state = GameState.GameOver;
			}
		}

		//Create new enemy after he's dead
		if(enemyindex == -1 && character != null){	
			createEnemy();
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		
		//Stop the music
		if(runningMusic.isPlaying())
			runningMusic.stop();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				int contin_x = g.getWidth()/2 - Assets.contin.getWidth()/2;
				int contin_y = (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight();
				
				//Did you click the Continue button?
				if(event.x > contin_x && event.x < contin_x + Assets.contin.getWidth() && event.y > contin_y && event.y < contin_y + Assets.contin.getHeight()){
					g.clear(0);
					Assets.click.play(1);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}
	
	private void updateBeaten(List<TouchEvent> touchEvents){
		
		//Stop the music
		if(runningMusic.isPlaying())
			runningMusic.stop();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				int contin_x = g.getWidth()/2 - Assets.contin.getWidth()/2;
				int contin_y = (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight();
				
				//Did you click the Continue button?
				if(event.x > contin_x && event.x < contin_x + Assets.contin.getWidth() && event.y > contin_y && event.y < contin_y + Assets.contin.getHeight()){
					g.clear(0);
					Assets.click.play(1);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}

	private void updatePaused(List<TouchEvent> touchEvents) {
		
		//Stop the music
		if(runningMusic.isPlaying())
			runningMusic.pause();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if (event.x > 80 && event.x <= 240) {
					
					if (event.y > 100 && event.y <= 148) {
						//if (Settings.soundEnabled)
							Assets.click.play(1);
						state = GameState.Running;
						return;
					}
					
					if (event.y > 148 && event.y < 196) {
						//if (Settings.soundEnabled)
							Assets.click.play(1);
						g.clear(0);
						game.setScreen(new MainMenuScreen(game));
						return;
					}
				}
			}
		}
	}

	public void createEnemy() {
		//enemy = new Enemy(g, enemySpeed, enemyHealth, newAnimation);
		
		enemyindex = (new Random()).nextInt(enemy.size());
		System.out.println(enemyindex);
		xCoord = character.getCoords().getX();
		yCoord = character.getCoords().getY();

		while (xCoord == character.getCoords().getX() && yCoord == character.getCoords().getY()) {
			xCoord = min + (int) (Math.random() * ((g.getWidth() - min) + 1));
			yCoord = min + (int) (Math.random() * ((g.getHeight() - min) + 1));
		}

		enemy.get(enemyindex).setCoords(xCoord, yCoord);
	}

	@Override
	public void present(float deltaTime) {
		System.out.print("Drawing...");
		
		if (state == GameState.GameOver) {
			drawGameOver();
		}else if (state == GameState.LevelUp) {
			System.out.println("LevelUp");
			drawLevel();
		}else if(state == GameState.Upgrade){
			System.out.println("Upgrade");
			drawUpgrade();
		}else if(state == GameState.Beaten){
			System.out.println("Beaten");
			drawBeaten();
		}else if (state == GameState.Running) {
			System.out.println("Running");
			drawRunning();
		}else if (state == GameState.Paused) {
			drawPaused();
		}else if(state == GameState.HumanPhase){
			System.out.println("HumanPhase");
			drawHumanPhase();
		}

	}

	private void drawHumanPhase() {
		g.clear(0);
		int x;
		Pixmap hp;
		
		// Draw the first phase
		if (humanPhase == 1) {
			hp = Assets.human[level.whatLevel() - 2];
			x = g.getWidth() / 2 - hp.getWidth() / 2;
			g.drawPixmap(hp, x, 0);
			
		// Periodically switch between phases to create an animation
		} else {
			hp = Assets.human[level.whatLevel() - (humanPhase % 2) - 1];
			x = g.getWidth() / 2 - hp.getWidth() / 2;
			g.drawPixmap(hp, x, 0);
		}
	}

	private void drawUpgrade() {
		
	}

	public void drawGameOver() {
		g.clear(0);
		g.drawPixmap(Assets.gameover, -15, 100);
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight());
	}
	
	public void drawBeaten(){
		g.clear(0);
		g.drawPixmap(Assets.youWin, 0, 75);
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight());
	}

	public void drawLevel(){
		g.clear(0);
		g.drawPixmap(Assets.levelUp, 0, 0);
		
		drawText(g, "" + (level.whatLevel()-1), 215, 60);
		drawText(g, "" + (level.getLevelScore()), 250, 190);
		drawText(g, "" + (level.getCumScore()), 265, 225);
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.05)) - Assets.contin.getHeight());
		g.drawPixmap(Assets.upgrade, g.getWidth()/2 - Assets.upgrade.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.22)) - Assets.upgrade.getHeight());
	}
	
	public void drawRunning() {
		drawBackground();
		drawPauseButton();

		//Draw character health
		for (int i = 0; i < character.getHealth(); i++) {
			g.drawRect(xOffset + 10, g.getHeight() - 20, 25, 25, Color.BLUE);
			xOffset += 50;
		}
       
		//Draw enemy health
		if (enemy != null) 
		{
			for (int i = 0; i < enemy.get(enemyindex).getCurrentHealth(); i++) {
				g.drawRect(xEnemyOffset + 275, 20, 25, 25, Color.GREEN);
				xEnemyOffset -= 50;
			}
		}

		if(enemyindex != -1){
			enemy.get(enemyindex).present();
		}

		if (character != null) {
			character.present();
		}
		xOffset = 0;
		xEnemyOffset = 0;

		drawText(g, Integer.toString((int)level.timeLeft()), g.getWidth() - 60, g.getHeight() - 35);

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
		g.drawPixmap(Assets.levelBackground[level.whatLevel()-1], 0, 0);
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
