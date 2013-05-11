package com.jeremyP.diseasedefense;

import java.util.List;

import com.jeremyP.diseasedefense.GameScreen.GameState;
import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Screen;
import com.jeremyP.diseasedefense.framework.Input.TouchEvent;

public class NextLevelScreen extends Screen {

	private Graphics g;
	private GameScreen returnScreen;
	private Level theLevel;
	
	
	public NextLevelScreen(Game game, Level level, GameScreen rtnScrn){
		super(game);
		System.out.println("Creating NextLevelScreen");
		g = game.getGraphics();
		returnScreen = rtnScrn;
		theLevel = level;
		drawInitialScreen();
		System.out.println("Finished Creating NextLevelScreen");

	}

	private void drawInitialScreen() {
		
		//TODO: Display "Level (level.whatLevel()) \n COMPLETE \n Total Score: returnScreen.getScore()"
		g.drawPixmap(Assets.levelUp, 0, 0);
		
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		while(true){
			touchEvents = game.getInput().getTouchEvents();
			
			for(int i=0; i < touchEvents.size(); i++){
				if(touchEvents.get(i).type == TouchEvent.TOUCH_UP){
					g.clear(0);
					game.setScreen(returnScreen);
				}
				
			}
		}
	}

	private void updateContinue(List<TouchEvent> touchEvents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		System.out.println("Printing NextLevelScreen");
		drawInitialScreen();

	}

	@Override
	public void pause() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void resume() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub
		
	}
	
	
}
