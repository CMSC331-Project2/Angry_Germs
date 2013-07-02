package com.jeremyP.angrygerms;

import com.jeremyP.angrygerms.framework.Game;
import com.jeremyP.angrygerms.framework.Graphics;
import com.jeremyP.angrygerms.framework.Screen;

public class SplashScreen extends Screen {

	boolean isDisplay;

	public SplashScreen(Game game) {
		super(game);
		isDisplay = false;
	}

	@Override
	public void update(float deltaTime) {

		if (isDisplay) {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		game.setScreen(new MainMenuScreen(game));
	}

	@Override
	public void present(float deltaTime) {
		Graphics g = game.getGraphics();

		int h = (g.getHeight() / 2) - (Assets.splash.getHeight() / 2);
		int w = (g.getWidth() / 2) - (Assets.splash.getWidth() / 2);

		g.drawPixmap(Assets.splash, w, h);
		isDisplay = true;
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
