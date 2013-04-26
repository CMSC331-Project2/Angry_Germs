package com.jeremyP.diseasedefense;

import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Screen;
import com.jeremyP.diseasedefense.framework.Graphics.PixmapFormat;

public class LoadingScreen extends Screen
{
	public LoadingScreen(Game game)
	{
		super(game);
	}
	
	public void update(float deltaTime)
	{
		Graphics g = game.getGraphics();
		Assets.background1 = g.newPixmap("bloodstream.png", PixmapFormat.RGB565);
		Assets.background2 = g.newPixmap("brain.png", PixmapFormat.RGB565);
		Assets.helpScreen1 = g.newPixmap("helpScreen1.png", PixmapFormat.RGB565);
		Assets.helpScreen2 = g.newPixmap("helpScreen2.png", PixmapFormat.RGB565);
		Assets.helpScreen3 = g.newPixmap("helpScreen3.png", PixmapFormat.RGB565);
		Assets.helpScreen4 = g.newPixmap("helpScreen4.png", PixmapFormat.RGB565);
		Assets.helpScreen5 = g.newPixmap("helpScreen5.png", PixmapFormat.RGB565);
		Assets.helpScreen6 = g.newPixmap("helpScreen6.png", PixmapFormat.RGB565);
		Assets.pc1 = g.newPixmap("luketheleukocyte1.png", PixmapFormat.ARGB4444);
		Assets.pc2 = g.newPixmap("luketheleukocyte2.png", PixmapFormat.ARGB4444);
		Assets.pc3 = g.newPixmap("luketheleukocyte3.png", PixmapFormat.ARGB4444);
		Assets.pc4 = g.newPixmap("luketheleukocyte4.png", PixmapFormat.ARGB4444);
		Assets.pc5 = g.newPixmap("luketheleukocyte5.png", PixmapFormat.ARGB4444);
		Assets.pc6 = g.newPixmap("luketheleukocyte6.png", PixmapFormat.ARGB4444);
		Assets.pc7 = g.newPixmap("luketheleukocyte7.png", PixmapFormat.ARGB4444);
		Assets.pc8 = g.newPixmap("luketheleukocyte8.png", PixmapFormat.ARGB4444);
		Assets.pc9 = g.newPixmap("luketheleukocyte9.png", PixmapFormat.ARGB4444);
		Assets.pc10 = g.newPixmap("luketheleukocyte10.png", PixmapFormat.ARGB4444);
		Assets.pc11 = g.newPixmap("luketheleukocyte11.png", PixmapFormat.ARGB4444);
		Assets.pc12 = g.newPixmap("luketheleukocyte12.png", PixmapFormat.ARGB4444);
		Assets.pc13 = g.newPixmap("luketheleukocyte13.png", PixmapFormat.ARGB4444);
		Assets.title = g.newPixmap("diseasedefensetitle.png", PixmapFormat.ARGB4444);
		Assets.gameover = g.newPixmap("gameover.png", PixmapFormat.ARGB4444);
		Assets.mainmenu = g.newPixmap("menubuttons.png", PixmapFormat.ARGB4444);
		Assets.pauseMenu = g.newPixmap("pauseMenu.png", PixmapFormat.ARGB4444);
		Assets.pauseButton = g.newPixmap("pauseButton.png", PixmapFormat.ARGB4444);
		Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
		Assets.badGuy1 = g.newPixmap("badGuy.png", PixmapFormat.ARGB4444);
		Assets.badGuy2 = g.newPixmap("badGuy2.png", PixmapFormat.ARGB4444);
		Assets.badGuy3 = g.newPixmap("badGuy3.png", PixmapFormat.ARGB4444);
		Assets.badGuy4 = g.newPixmap("badGuy4.png", PixmapFormat.ARGB4444);
		Assets.badGuy5 = g.newPixmap("badGuy5.png", PixmapFormat.ARGB4444);
		Assets.badGuy6 = g.newPixmap("badGuy6.png", PixmapFormat.ARGB4444);
		Assets.badGuy7 = g.newPixmap("badGuy7.png", PixmapFormat.ARGB4444);
		Assets.badGuy2_1 = g.newPixmap("badGuy2-1.png", PixmapFormat.ARGB4444);
		Assets.badGuy2_2 = g.newPixmap("badGuy2-2.png", PixmapFormat.ARGB4444);
		Assets.badGuy2_3 = g.newPixmap("badGuy2-3.png", PixmapFormat.ARGB4444);
		Assets.badGuy2_4 = g.newPixmap("badGuy2-4.png", PixmapFormat.ARGB4444);
		Assets.badGuy2_5 = g.newPixmap("badGuy2-5.png", PixmapFormat.ARGB4444);
		Assets.click = game.getAudio().newSound("click.ogg");
		game.setScreen(new MainMenuScreen(game));
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
