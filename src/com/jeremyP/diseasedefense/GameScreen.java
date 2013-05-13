package com.jeremyP.diseasedefense;

import java.util.ArrayList;
import java.util.Date;
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
	private long timePaused;
	private boolean wait; /* Used so pause to screen so the player has time to choose an option */

	enum GameState {
		LevelUp, Running, Paused, GameOver, Win, HumanPhase
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
		wait = true;
		//enemiesKilled = 0;
	}

	/**
	 * This method is the main logical loop of the game. It determines which state the game is
	 * in by checking which enum state is equal to. GameState.Running is where the main action takes
	 * place. GameState.GameOver is when the player loses all of their health and is sent to a game over
	 * screen. GameState.Win is the screen shown when the player wins the game. GameState.Paused is
	 * when the player pauses the game.
	 * 
	 * No drawing happens within any of the update loops.
	 */
	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		//System.out.println("Updating...");
		if (state == GameState.Running) {
			//System.out.println("Running");
			updateRunning(touchEvents, deltaTime);
		}else if (state == GameState.LevelUp) {
			//System.out.println("LevelUp");
			updateLevel(touchEvents);
		}else if (state == GameState.GameOver) {
			//System.out.println("Gameover");
			updateGameOver(touchEvents);
		}else if (state == GameState.Win){
			////System.out.println("Win");
			updateWin(touchEvents);
		}else if (state == GameState.Paused) {
			updatePaused(touchEvents);
		}else if (state == GameState.HumanPhase) {
			////System.out.println("HumanPhase");
			updateHumanPhase(touchEvents);
		}
	}

	private void updateHumanPhase(List<TouchEvent> touchEvents) {

		try {
			
			//Update the first phase
			if(humanPhase == 1){
				Thread.sleep(700);
				state = GameState.HumanPhase;
				Assets.humanphase.play(1);
				humanPhase++;

			//Finished update?
			}else if(humanPhase == 6){
				Thread.sleep(1000);
				
				//Is the game won?
				if(level.isEnd()){
					state = GameState.Win;
				}else{
					state = GameState.Running;
				}
				humanPhase = 1;
				System.out.println("Enemy Size: " + enemy.size());
			
			//Update periodically through the phases
			}else{

				Thread.sleep(250);
				Assets.humanphase.play(1);
				state = GameState.HumanPhase;
				humanPhase++;
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	private void updateLevel(List<TouchEvent> touchEvents) {
		
		game.getInput().getKeyEvents();
		
		//Change the music for new level
		//TODO: Make the music fade out as the level changes
		if(runningMusic.isPlaying() && level.isEnd()){
			runningMusic.stop();
		}else if(runningMusic.isPlaying() && !level.isEnd()){
			runningMusic.stop();
			runningMusic = Assets.levelMusic[level.whatLevel()-1];
			runningMusic.setLooping(true);
		}

		if(wait){
			wait = false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				int contin_x = g.getWidth()/2 - Assets.contin.getWidth()/2;
				int contin_y = (int) (g.getHeight() - (g.getHeight()*.05)) - Assets.contin.getHeight();
				
				//Did you click the Continue button?
				if(event.x > contin_x && event.x < contin_x + Assets.contin.getWidth() && event.y > contin_y && event.y < contin_y + Assets.contin.getHeight()){
					Assets.click.play(1);
					level.resetScore();
					state = GameState.HumanPhase;
					return;
				}
			}
		}
	}

	private void updateRunning(List<TouchEvent> touchEvents, float deltaTime) {
		
		//Play the music if it isn't already playing
		//NOTE: This usually will only be called when the level is just beginning
		if(!runningMusic.isPlaying())
			runningMusic.play();
		
		wait = true;
		//runningMusic.setVolume(1.0f);
		
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
					timePaused = new Date().getTime();
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
			////System.out.println(enemy.get(enemyindex).getCurrentHealth());
			enemy.get(enemyindex).getHit();
			////System.out.println("Hit: " + enemy.get(enemyindex).getCurrentHealth());
			////System.out.println("Hit: " + enemy.get(enemyindex).getCurrentHealth());
			character.stopProjectile();
			
			if (enemy.get(enemyindex).isDead()) 
			{
				Assets.kill.play(1);
				enemy.get(enemyindex).revive();
				character.stopProjectile();
				level.addScore(enemy.get(enemyindex).getScore());
				enemyindex = -1;
			
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
				Assets.game_over.play(1);
				state = GameState.GameOver;
			}
		}

		//Create new enemy after he's dead
		if(enemyindex == -1 && character != null){	
			createEnemy();
		}
		
		//Have time ran out?
		if(level.isLevelEnd(false)){
			
			state = GameState.LevelUp;
			
			Assets.level_up.play(1);
			
			//Clear if there was an enemy running on the board at close
			enemyindex = -1;
			
			int enemySum = enemy.size();
			
			//Create new level enemy
			//TODO: Add speed variation to stronger/different enemies
			int speed = 1;
			int health = enemy.get(enemySum-1).getTotalHealth() + 1;
			int points = enemy.get(enemySum-1).getSpeed() + 1;
			
			//This if statement will stay here until more enemy variations are created
			if(level.whatLevel() < 6){
				Enemy newenemy = new Enemy(g, Assets.badGuys[level.whatLevel()-1] ,speed, health, points);
				
				//Add new level enemy
				int moreEnemies = enemySum * 2;
				////System.out.println(moreEnemies);
				for(int i=0; i < moreEnemies; i++){
					enemy.add(newenemy);
					////System.out.println("Enemy added " + enemy.size());
				}
			}
		}
	}

	private void updateGameOver(List<TouchEvent> touchEvents) {
		
		//Stop the music
		if(runningMusic.isPlaying())
			runningMusic.stop();
		
		if(wait){
			wait = false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		Assets.gameOverMusic.play();
		Assets.gameOverMusic.setVolume(1.0f);
		Assets.gameOverMusic.setLooping(true);
		
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
					Assets.gameOverMusic.stop();
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}
	}
	
	private void updateWin(List<TouchEvent> touchEvents){
		
		//Stop the music
		if(runningMusic.isPlaying())
			runningMusic.stop();
		
		if(wait){
			wait = false;
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
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
		if(runningMusic.isPlaying()){
			runningMusic.setVolume(0.5f);
		}
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				
				if (event.x > 80 && event.x <= 240) {
					
					//Did you press Continue?
					if (event.y > 100 && event.y <= 148) {
						//if (Settings.soundEnabled)
							Assets.click.play(1);
						runningMusic.setVolume(1.0f);
						long endtimePaused = new Date().getTime();
						//System.out.println("Paused time: " + new Date(timePaused - endtimePaused));
						level.addPausedTime(timePaused - endtimePaused);
						timePaused = 0;
						state = GameState.Running;
						return;
					}
					
					//Did you press Exit?
					if (event.y > 148 && event.y < 196) {
						//if (Settings.soundEnabled)
							Assets.click.play(1);
						runningMusic.stop();
						g.clear(0);
						game.setScreen(new MainMenuScreen(game));
						return;
					}
				}
			}
		}
	}

	private void createEnemy() {
		//enemy = new Enemy(g, enemySpeed, enemyHealth, newAnimation);
		
		enemyindex = (new Random()).nextInt(enemy.size());
		//System.out.println(enemyindex);
		xCoord = character.getCoords().getX();
		yCoord = character.getCoords().getY();

		while (xCoord == character.getCoords().getX() && yCoord == character.getCoords().getY()) {
			//xCoord = min + (int) (Math.random() * ((g.getWidth() - min) + 1));
			//yCoord = min + (int) (Math.random() * ((g.getHeight() - min) + 1));
			
			int rando = (int) (Math.random() * ((10 - 0) + 1 ));
			rando = rando - 5;
			
			if(rando > 0){
				xCoord = min + (int) (Math.random() * ((  (g.getWidth()/5) - min  ) + 1));
				xCoord = xCoord - (g.getWidth()/10);
				if (xCoord > 0){
					xCoord = xCoord + g.getWidth();
				}
				yCoord = min + (int) (Math.random() * ((g.getHeight() - min) + 1));
			}
			else{
				yCoord = min + (int) (Math.random() * ((  (g.getWidth()/5) - min  ) + 1));		
				yCoord = yCoord - (g.getWidth()/10);
				if (yCoord > 0){
					yCoord = yCoord + g.getHeight();
				}
				xCoord = min + (int) (Math.random() * ((g.getWidth() - min) + 1));
			}
			
		}

		enemy.get(enemyindex).setCoords(xCoord, yCoord);
		//enemy.get(enemyindex).setCoords(0, 0);
	}

	@Override
	public void present(float deltaTime) {
		System.out.print("Drawing...");
		
		if (state == GameState.GameOver) {
			drawGameOver();
		}else if (state == GameState.LevelUp) {
			//System.out.println("LevelUp");
			drawLevel();
		}else if(state == GameState.Win){
			//System.out.println("Beaten");
			drawWin();
		}else if (state == GameState.Running) {
			//System.out.println("Running");
			drawRunning();
		}else if (state == GameState.Paused) {
			drawPaused();
		}else if(state == GameState.HumanPhase){
			//System.out.println("HumanPhase");
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

	private void drawGameOver() {
		g.clear(0);
		g.drawPixmap(Assets.gameover, g.getWidth()/2 - Assets.gameover.getWidth()/2, 60);
		
		//Display scores
		g.drawPixmap(Assets.scores, 0, Assets.gameover.getHeight() + 5 + 60);
		drawText(g, "" + (level.getLevelScore()), Assets.scores.getWidth() - 35, Assets.levelUp.getHeight() + 15 + 60);
		drawText(g, "" + (level.getCumScore()), Assets.scores.getWidth() - 20, Assets.levelUp.getHeight() + 50 + 60);
		
		//Display buttons
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight());
	}
	
	private void drawWin(){
		g.clear(0);
		g.drawPixmap(Assets.youWin, g.getWidth()/2 - Assets.youWin.getWidth()/2, 30);

		//Display scores
		g.drawPixmap(Assets.scores, 0, Assets.youWin.getHeight() + 60);
		drawText(g, "" + (level.getLevelScore()), Assets.scores.getWidth() - 35, Assets.levelUp.getHeight() + 15 + 30);
		drawText(g, "" + (level.getCumScore()), Assets.scores.getWidth() - 20, Assets.levelUp.getHeight() + 50 + 30);
		
		//Display buttons
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight());
	}

	private void drawLevel(){
		g.clear(0);
		g.drawPixmap(Assets.levelUp, g.getWidth()/2 - Assets.levelUp.getWidth()/2, 30);
		drawText(g, "" + (level.whatLevel()-1), Assets.levelUp.getWidth() - 15, (int) (Assets.levelUp.getHeight()*.25 + 30));

		//Display scores
		g.drawPixmap(Assets.scores, 0, Assets.levelUp.getHeight() + 5 + 30);
		drawText(g, "" + (level.getLevelScore()), Assets.scores.getWidth() - 35, Assets.levelUp.getHeight() + 50);
		drawText(g, "" + (level.getCumScore()), Assets.scores.getWidth() - 20, Assets.levelUp.getHeight() + 35 + 50);
		
		//Display buttons
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.05)) - Assets.contin.getHeight());
	}
	
	private void drawRunning() {
		drawBackground();
		drawPauseButton();

		//Draw character health
		for (int i = 0; i < character.getHealth(); i++) {
			g.drawRect(xOffset + 10, g.getHeight() - 20, 25, 25, Color.BLUE);
			xOffset += 50;
		}
       
		//Draw enemy health
		if (enemyindex != -1) 
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

	private void drawText(Graphics g, String line, int x, int y) {
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
