package com.jeremyP.angrygerms;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Timer;

import android.annotation.SuppressLint;
import android.graphics.Color;

import com.jeremyP.angrygerms.framework.Game;
import com.jeremyP.angrygerms.framework.Graphics;
import com.jeremyP.angrygerms.framework.Music;
import com.jeremyP.angrygerms.framework.Pixmap;
import com.jeremyP.angrygerms.framework.Screen;
import com.jeremyP.angrygerms.framework.Input.TouchEvent;

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
	private char hsName[];

	enum GameState {
		LevelUp, Running, Paused, GameOver, Win, HumanPhase, HighScore
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
		hsName = new char[]{'A', 'A', 'A'};
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

		if (state == GameState.Running) {
			updateRunning(touchEvents, deltaTime);
		}else if (state == GameState.LevelUp) {
			updateLevel(touchEvents);
		}else if (state == GameState.GameOver) {
			updateGameOver(touchEvents);
		}else if (state == GameState.Win){
			updateWin(touchEvents);
		}else if (state == GameState.Paused) {
			updatePaused(touchEvents);
		}else if (state == GameState.HumanPhase) {
			updateHumanPhase(touchEvents);
		}else if(state == GameState.HighScore) {
			updateHighScore(touchEvents);
		}
	}

	private void updateHighScore(List<TouchEvent> touchEvents) {
		
		for (int i = 0; i < touchEvents.size(); i++) {
			
			TouchEvent event = touchEvents.get(i);
			
			if(event.type == TouchEvent.TOUCH_UP){
				
				int arrow_x[] = new int[3];
				
				arrow_x[0] = 73;
				arrow_x[1] = 133;
				arrow_x[2] = 193;
						
				// Did you touch the up arrows?
				int up_y = 160;
				int u = -1;
				
				if(event.x > arrow_x[0] && event.x < arrow_x[0] + Assets.up.getWidth() && event.y > up_y && event.y < up_y + Assets.up.getHeight()){
					u = 0;
				}else if(event.x > arrow_x[1] && event.x < arrow_x[1] + Assets.up.getWidth() && event.y > up_y && event.y < up_y + Assets.up.getHeight()){
					u = 1;
				}else if(event.x > arrow_x[2] && event.x < arrow_x[2] + Assets.up.getWidth() && event.y > up_y && event.y < up_y + Assets.up.getHeight()){
					u = 2;
				}
				
				System.out.println("Up " + u + " has been pressed!");
				
				if(u != -1){
					Assets.click.play(1);
					if(hsName[u] == 'A'){
						hsName[u] = 'Z';
					}else{
						hsName[u]--;  
					}
				}
				
				// Did you touch the down arrows?
				arrow_x[0] = 76;
				arrow_x[1] = 136;
				arrow_x[2] = 196;
				
				int down_y = 270;
				int d = -1;
				
				if(event.x > arrow_x[0] && event.x < arrow_x[0] + Assets.down.getWidth() && event.y > down_y && event.y < down_y + Assets.down.getHeight()){
					d = 0;
				}else if(event.x > arrow_x[1] && event.x < arrow_x[1] + Assets.down.getWidth() && event.y > down_y && event.y < down_y + Assets.down.getHeight()){
					d = 1;
				}else if(event.x > arrow_x[2] && event.x < arrow_x[2] + Assets.down.getWidth() && event.y > down_y && event.y < down_y + Assets.down.getHeight()){
					d = 2;
				}
				
				System.out.println("Down " + d + " has been pressed!");

				if(d != -1){
					Assets.click.play(1);
					if(hsName[d] == 'Z'){
						hsName[d] = 'A';
					}else{
						hsName[d]++;
					}
				}
				
				// Did you touch the continue button?
				int contin_x = g.getWidth()/2 - Assets.contin.getWidth()/2;
				int contin_y = 330;
				
				char[] cname = {hsName[0], hsName[1], hsName[2]};
				String name = new String(cname);
				
				String score = String.valueOf(level.getCumScore());
				
				if(event.x > contin_x && event.x < contin_x + Assets.contin.getWidth() && event.y > contin_y && event.y < contin_y + Assets.contin.getHeight()){
					Assets.click.play(1);
					HighscoreScreen.addHighScore(game, name, score);
					game.setScreen(new MainMenuScreen(game));
				}
				
			}
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
					Assets.youwinMusic.play();
					Assets.youwinMusic.setLooping(false);
					state = GameState.Win;
				}else{
					state = GameState.Running;
				}
				humanPhase = 1;
				//System.out.println("Enemy Size: " + enemy.size());
				level.resetScore();
			
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
				touchEvents.clear();
				Thread.sleep(2000);
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
			
			//This prepares the enemy array
			//Of course, we don't need to do this if you have beaten the game
			if(!level.isEnd()){
				enemy.get(enemyindex).revive();
				
				//Clear if there was an enemy running on the board at close
				enemyindex = -1;
				
				int enemySum = enemy.size();
				
				//Create new level enemy
				//TODO: Add speed variation to stronger/different enemies
				int speed = 1;
				int health = enemy.get(enemySum-1).getTotalHealth() + 1;
				int points = enemy.get(enemySum-1).getScore() + 1;
				
				Enemy newenemy = new Enemy(g, Assets.badGuys[level.whatLevel()-1] ,speed, health, points);
				
				//Add new level enemy
				int moreEnemies = enemySum * 2;
				//System.out.println(moreEnemies);
				for(int i=0; i < moreEnemies; i++){
					enemy.add(newenemy);
					//System.out.println("Enemy added " + enemy.size());
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
				touchEvents.clear();
				Thread.sleep(2000);
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
					if(HighscoreScreen.isNewHighScore(game, level.getCumScore())){
						state = GameState.HighScore;
					}else {
						game.setScreen(new MainMenuScreen(game));
						return;
					}
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
				touchEvents.clear();
				Thread.sleep(2000);
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
					Assets.youwinMusic.stop();
					if(HighscoreScreen.isNewHighScore(game, level.getCumScore())){
						state = GameState.HighScore;
					}else {
						game.setScreen(new MainMenuScreen(game));
						return;
					}				}
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
		//System.out.print("Drawing...");
		
		if (state == GameState.GameOver) {
			drawGameOver();
		}else if (state == GameState.LevelUp) {
			drawLevel();
		}else if(state == GameState.Win){
			drawWin();
		}else if (state == GameState.Running) {
			drawRunning();
		}else if (state == GameState.Paused) {
			drawPaused();
		}else if(state == GameState.HumanPhase){
			drawHumanPhase();
		}else if(state == GameState.HighScore){
			drawHighscore();
		}

	}

	private void drawHighscore() {
		g.clear(0);
		
		drawText(g, "NEW", g.getWidth()/2, 0, false, true);
		drawText(g, "HIGHSCORE", 0, 50, true, true);
		
		drawText(g, "ENTER NAME", 0, 110, true, true);
		
		int left = 70;
		int center = 130;
		int right = 190;
		
		int top = 160;
		int middle = 210;
		int bottom = 270;
		
		//Up arrows
		g.drawPixmap(Assets.up, left + 3, top);
		g.drawPixmap(Assets.up, center + 3, top);
		g.drawPixmap(Assets.up, right + 3, top);		
		
		//Name
		drawText(g, hsName[0] + "", left, middle, false, false);
		drawText(g, hsName[1] + "", center, middle, false, false);
		drawText(g, hsName[2] + "", right, middle, false, false);	
		
		//Down arrows
		g.drawPixmap(Assets.down, left + 6, bottom);
		g.drawPixmap(Assets.down, center + 6, bottom);
		g.drawPixmap(Assets.down, right + 6, bottom);	
		
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, 330);
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
		
		int heightStack = 0;
		g.drawPixmap(Assets.gameover, g.getWidth()/2 - Assets.gameover.getWidth()/2, 60);
		heightStack += Assets.gameover.getHeight() + 60;
		
		//Display scores
		//TOTAL SCORE
		g.drawPixmap(Assets.tScore, 0, heightStack + 5);
		drawText(g, "" + (level.getLevelScore()), Assets.tScore.getWidth() - 15, heightStack + 17, true, false);
		heightStack += Assets.tScore.getHeight() + 5;
		
		//CUM SCORE
		g.drawPixmap(Assets.cScore, -5, heightStack + 5);
		drawText(g, "" + (level.getCumScore()), Assets.cScore.getWidth() - 5, heightStack + 10, true, false);
		
		//Display buttons
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight());
	}
	
	private void drawWin(){
		g.clear(0);
		
		int heightStack = 0;
		g.drawPixmap(Assets.youWin, g.getWidth()/2 - Assets.youWin.getWidth()/2, 30);
		heightStack += Assets.youWin.getHeight() + 30;
		
		//Display scores
		//TOTAL SCORE
		g.drawPixmap(Assets.tScore, 0, heightStack + 5);
		drawText(g, "" + (level.getCumScore()), Assets.tScore.getWidth() - 15, heightStack + 17, true, false);
		heightStack += Assets.tScore.getHeight() + 5;
		
		//Display buttons
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight());
	}

	private void drawLevel(){
		g.clear(0);
		
		int heightStack = 0;
		g.drawPixmap(Assets.levelUp, g.getWidth()/2 - Assets.levelUp.getWidth()/2, 30);
		drawText(g, "" + (level.whatLevel()-1), Assets.levelUp.getWidth() - 15, (int) (Assets.levelUp.getHeight()*.25 + 20), false, false);
		heightStack += Assets.levelUp.getHeight() + 40;
		
		//Display scores
		//TOTAL SCORE
		g.drawPixmap(Assets.tScore, 0, heightStack + 5);
		drawText(g, "" + (level.getLevelScore()), Assets.tScore.getWidth() - 15, heightStack + 17, true, false);
		heightStack += Assets.tScore.getHeight() + 5;
		
		//CUM SCORE
		g.drawPixmap(Assets.cScore, -5, heightStack + 5);
		drawText(g, "" + (level.getCumScore()), Assets.cScore.getWidth() - 5, heightStack + 10, true, false);
		
		//Display buttons
		g.drawPixmap(Assets.contin, g.getWidth()/2 - Assets.contin.getWidth()/2, (int) (g.getHeight() - (g.getHeight()*.1)) - Assets.contin.getHeight());
	}
	
	private void drawRunning() {
		drawBackground();
		drawPauseButton();

		//Draw enemy
		if(enemyindex != -1){
			enemy.get(enemyindex).present();
		}

		//Draw you
		if (character != null) {
			character.present();
		}
		
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

		xOffset = 0;
		xEnemyOffset = 0;

		//Timer
		int time = (int)level.timeLeft();
		if(time > 9){
			drawText(g, Integer.toString(time), g.getWidth() - 60, g.getHeight() - 25, true, false);
		}else{
			drawText(g, Integer.toString(time), g.getWidth() - 30, g.getHeight() - 25, true, false);
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
		g.drawPixmap(Assets.levelBackground[level.whatLevel()-1], 0, 0);
	}

	/**
	 * Print out a String
	 * @param g - origin graphics
	 * @param line - the desired text to be displayed
	 * @param x - x coordinate of the desired string location
	 * @param y - y coordinate of the desired string location
	 */
	public static void drawText(Graphics g, String line, int x, int y, boolean small, boolean center) {
		int len = line.length();
		Pixmap asset;
		int spacing;
		final int CHAR_H;
		final int CHAR_W;
		
		if(small){
			spacing = 4;
			CHAR_H = 25;
			CHAR_W = 25;
			asset = Assets.letters_small;
		}else{
			spacing = 8;
			CHAR_H = 50;
			CHAR_W = 50;			
			asset = Assets.letters_large;
		}
		
		for (int i = 0; i < len; i++) {
			String character = line.charAt(i) + "";
			
			//Space
			if (character.equals(" ")) {
				x += 20;
				continue;
			}
			
			int srcX = 0;
			int srcY = 0;
			int srcWidth = CHAR_W;
			int srcHeight = CHAR_H;
			int charAsciiIndex = character.toUpperCase().charAt(0);
			
			//System.out.println(charAsciiIndex + " : " + (char)charAsciiIndex);
			
			//Alphabetic character
			if( (charAsciiIndex >= 65 && charAsciiIndex <=  90) ||
					charAsciiIndex >= 97 && charAsciiIndex <= 122){
				
				int charIndex = charAsciiIndex - 65;
				int column = charIndex % 11;
				int row = charIndex / 11;
				
				srcWidth = CHAR_W;
				
				srcX = column * CHAR_W;
				srcY = row * CHAR_H + ((spacing * row) + 1); //Second part is for spacing
								
				//Digits
			}else if(charAsciiIndex >= 48 && charAsciiIndex <= 57){
				
				int row, column;
				int digitIndex = charAsciiIndex - 49; //It's -49 and not -48 because we don't count the zero from the ASCII table
				
				//Apparently, 0 comes first in the ASCII table and last in the letters.png
				//TODO: Photoshop letters.png to support the ASCII table order
				if(charAsciiIndex == 48){
					row = 3;
					column = 2;					
				}else{
					column = (digitIndex + 4) % 11;
					row = (digitIndex + 4) / 11;
					
					row += 2;
				}
				
				srcWidth = CHAR_W;
				
				srcX = column * CHAR_W;
				srcY = row * CHAR_H + ((spacing * row) + 1); //Second part is for spacing
								
				//.
			}else if(charAsciiIndex == 46){
				
				srcX = 15 * CHAR_W + (spacing * 15);
				srcY = 3 * CHAR_H + ((spacing * 3) + 1); //Second part is for spacing
			}
			
			if(center){
				x = g.getWidth()/2 - (line.length() * CHAR_W)/2;
				center = false;
			}

			g.drawPixmap(asset, x, y, srcX, srcY, srcWidth, srcHeight);
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
