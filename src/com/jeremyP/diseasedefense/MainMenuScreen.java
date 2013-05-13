package com.jeremyP.diseasedefense;

import java.util.List;

import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Input.TouchEvent;
import com.jeremyP.diseasedefense.framework.Screen;


public class MainMenuScreen extends Screen 
{
	public MainMenuScreen(Game game)
	{
		super(game);
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
				if (inBounds(event, 0, g.getHeight() - 64, 64, 64))
				{
					Assets.click.play(1);
					/*Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled){
						Assets.click.play(1);
					}*/
					return;
				}
				
				if (inBounds(event, 64, 220, 192, 42))
				{
					Assets.click.play(1);
					game.setScreen(new GameScreen(game));
					/*Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
					{
						Assets.click.play(1);
					}*/
					return;
				}
				
				if (inBounds(event, 64, 220 + 42, 192, 42))
				{
					Assets.click.play(1);
					game.setScreen(new HelpScreen(game));
					/*Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
					{
						Assets.click.play(1);
					}*/
					return;
				}
				
				if (inBounds(event, 64, 220 + 84, 192, 42))
				{
					Assets.click.play(1);
					/*Settings.soundEnabled = !Settings.soundEnabled;
					if(Settings.soundEnabled)
					{
						Assets.click.play(1);
					}*/
					return;
				}
			}
		}
		
	}
	
	private boolean inBounds(TouchEvent event, int x, int y, int width, int height)
	{
		if (event.x > x && event.x < x + width - 1 && event.y > y && event.y < y + height - 1)
		{
			return true;
		}
		else
		{
			return false;
		}
	}
	
	public void present(float deltaTime)
	{
		Graphics g = game.getGraphics();
		
		g.drawPixmap(Assets.title, 32, 20);
		g.drawPixmap(Assets.mainmenu, 64, 220);
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
