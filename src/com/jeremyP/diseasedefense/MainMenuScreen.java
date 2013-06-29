package com.jeremyP.diseasedefense;

import java.util.List;

import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Input.TouchEvent;
import com.jeremyP.diseasedefense.framework.Screen;


public class MainMenuScreen extends Screen 
{
	
	private int mainmenubuttons[];
	
	public MainMenuScreen(Game game)
	{
		super(game);
		mainmenubuttons = new int[]{36, 250};
	}
	
	public void update(float deltaTime)
	{
		Graphics g = game.getGraphics();
		
		List<TouchEvent> touchEvents  = game.getInput().getTouchEvents();
		game.getInput().getKeyEvents();
		
		int len = touchEvents.size();
		for (int i = 0; i < len; i++)
		{
			TouchEvent event = touchEvents.get(i);
			if(event.type == TouchEvent.TOUCH_UP)
			{
				
				//Did they press the entire button?
				if(inBounds(event, mainmenubuttons[0], mainmenubuttons[1], Assets.mainmenu.getWidth(), Assets.mainmenu.getHeight())){
					
					int button_pressed = event.y - mainmenubuttons[1];
					
					//Start
					if(button_pressed < Assets.mainmenu.getHeight()/3){
						Assets.click.play(1);
						game.setScreen(new GameScreen(game));
						return;
					}
					
					//Highscores
					if(button_pressed < 2 * (Assets.mainmenu.getHeight()/3)){
						Assets.click.play(1);
						game.setScreen(new HighscoreScreen(game));
						return;
					}
					
					//Help
					if(button_pressed < 3 * (Assets.mainmenu.getHeight()/3)){
						Assets.click.play(1);
						game.setScreen(new HelpScreen(game, 0));
						return;
					}
				}
				
			}
		}
		
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height)
	{
		return (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1);
	}
	
	public void present(float deltaTime)
	{
		Graphics g = game.getGraphics();
		g.clear(0);
		
		g.drawPixmap(Assets.title, 30, 10);
		g.drawPixmap(Assets.mainmenu, mainmenubuttons[0], mainmenubuttons[1]);
	}  

	@Override
	public void pause() {
		HighscoreScreen.save(game.getFileIO());
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
