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
	private ScreenState state;
	private Level theLevel;
	
	enum ScreenState {
		StandBy, Continue
	}
	
	public NextLevelScreen(Game game, Level level, GameScreen rtnScrn){
		super(game);
		g = game.getGraphics();
		returnScreen = rtnScrn;
		state = ScreenState.StandBy;
		theLevel = level;
		drawInitialScreen();
	}

	private void drawInitialScreen() {
		
		//TODO: Display "Level (level.whatLevel()) \n COMPLETE \n Total Score: returnScreen.getScore()"
		
		
	}

	@Override
	public void update(float deltaTime) {
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();

		if (state == ScreenState.Continue) {
			updateContinue(touchEvents);
		}		
	}

	private void updateContinue(List<TouchEvent> touchEvents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		
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
