package com.jeremyP.diseasedefense;

import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Music;
import com.jeremyP.diseasedefense.framework.Pixmap;
import com.jeremyP.diseasedefense.framework.Screen;
import com.jeremyP.diseasedefense.framework.Sound;
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
		
		//Create level background images
		Assets.levelBackground = new Pixmap[5];
		Assets.levelBackground[0] = g.newPixmap("bloodstream.png", PixmapFormat.RGB565);
		Assets.levelBackground[1] = g.newPixmap("brain.png", PixmapFormat.RGB565);
		Assets.levelBackground[2] = g.newPixmap("blue_blood_cell.png", PixmapFormat.RGB565);
		Assets.levelBackground[3] = g.newPixmap("purple-blood-cells.png", PixmapFormat.RGB565);
		Assets.levelBackground[4] = g.newPixmap("inside_human_heart.png", PixmapFormat.RGB565);
		
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
		Assets.youWin = g.newPixmap("You_win.png", PixmapFormat.ARGB4444);
		Assets.contin = g.newPixmap("continue_button.png", PixmapFormat.ARGB4444);
		Assets.upgrade = g.newPixmap("upgrade_button.png", PixmapFormat.ARGB4444);
		Assets.mainmenu = g.newPixmap("menubuttons.png", PixmapFormat.ARGB4444);
		Assets.pauseMenu = g.newPixmap("pauseMenu.png", PixmapFormat.ARGB4444);
		Assets.pauseButton = g.newPixmap("pauseButton.png", PixmapFormat.ARGB4444);
		Assets.numbers = g.newPixmap("numbers.png", PixmapFormat.ARGB4444);
		Assets.levelUp = g.newPixmap("level_upgrade_default2.png", PixmapFormat.ARGB4444);
		
		//Human level transition animation phases
		Assets.human = new Pixmap[6];
		Assets.human[0] = g.newPixmap("human_12.png", PixmapFormat.RGB565);
		Assets.human[1] = g.newPixmap("human_22.png", PixmapFormat.RGB565);
		Assets.human[2] = g.newPixmap("human_32.png", PixmapFormat.RGB565);
		Assets.human[3] = g.newPixmap("human_42.png", PixmapFormat.RGB565);
		Assets.human[4] = g.newPixmap("human_52.png", PixmapFormat.RGB565);
		Assets.human[5] = g.newPixmap("human_62.png", PixmapFormat.RGB565);
		
		
		Assets.badGuys = new Pixmap[4][];
		
		//First badguy
		Assets.badGuys[0] = new Pixmap[7];
		Assets.badGuys[0][0] = g.newPixmap("badGuy.png", PixmapFormat.ARGB4444);
		Assets.badGuys[0][1] = g.newPixmap("badGuy2.png", PixmapFormat.ARGB4444);
		Assets.badGuys[0][2] = g.newPixmap("badGuy3.png", PixmapFormat.ARGB4444);
		Assets.badGuys[0][3] = g.newPixmap("badGuy4.png", PixmapFormat.ARGB4444);
		Assets.badGuys[0][4] = g.newPixmap("badGuy5.png", PixmapFormat.ARGB4444);
		Assets.badGuys[0][5] = g.newPixmap("badGuy6.png", PixmapFormat.ARGB4444);
		Assets.badGuys[0][6] = g.newPixmap("badGuy7.png", PixmapFormat.ARGB4444);
		
		//Second badguy
		Assets.badGuys[1] = new Pixmap[5];
		Assets.badGuys[1][0] = g.newPixmap("badGuy2-1.png", PixmapFormat.ARGB4444);
		Assets.badGuys[1][1] = g.newPixmap("badGuy2-2.png", PixmapFormat.ARGB4444);
		Assets.badGuys[1][2] = g.newPixmap("badGuy2-3.png", PixmapFormat.ARGB4444);
		Assets.badGuys[1][3] = g.newPixmap("badGuy2-4.png", PixmapFormat.ARGB4444);
		Assets.badGuys[1][4] = g.newPixmap("badGuy2-5.png", PixmapFormat.ARGB4444);
		
		//Third badguy
		Assets.badGuys[2] = new Pixmap[5];
		Assets.badGuys[2][0] = g.newPixmap("badGuy3_1.png", PixmapFormat.ARGB4444);
		Assets.badGuys[2][1] = g.newPixmap("badGuy3_2.png", PixmapFormat.ARGB4444);
		Assets.badGuys[2][2] = g.newPixmap("badGuy3_3.png", PixmapFormat.ARGB4444);
		Assets.badGuys[2][3] = g.newPixmap("badGuy3_4.png", PixmapFormat.ARGB4444);
		Assets.badGuys[2][4] = g.newPixmap("badGuy3_5.png", PixmapFormat.ARGB4444);
		
		//Fourth badguy
		Assets.badGuys[3] = new Pixmap[8];
		Assets.badGuys[3][0] = g.newPixmap("badGuy4_1.png", PixmapFormat.ARGB4444);
		Assets.badGuys[3][1] = g.newPixmap("badGuy4_2.png", PixmapFormat.ARGB4444);
		Assets.badGuys[3][2] = g.newPixmap("badGuy4_3.png", PixmapFormat.ARGB4444);
		Assets.badGuys[3][3] = g.newPixmap("badGuy4_4.png", PixmapFormat.ARGB4444);
		Assets.badGuys[3][4] = g.newPixmap("badGuy4_5.png", PixmapFormat.ARGB4444);
		Assets.badGuys[3][5] = g.newPixmap("badGuy4_6.png", PixmapFormat.ARGB4444);
		Assets.badGuys[3][6] = g.newPixmap("badGuy4_7.png", PixmapFormat.ARGB4444);
		Assets.badGuys[3][7] = g.newPixmap("badGuy4_8.png", PixmapFormat.ARGB4444);

		//Create sounds
		Assets.click = game.getAudio().newSound("click_button.ogg");
		Assets.kill = game.getAudio().newSound("enemy kill.ogg");
		Assets.hit = game.getAudio().newSound("hit.ogg");
		Assets.humanphase = game.getAudio().newSound("humanphase.ogg");
		Assets.level_up = game.getAudio().newSound("level_up.ogg");
		Assets.shoot = game.getAudio().newSound("shoot.ogg");
		Assets.game_over = game.getAudio().newSound("game_over.ogg");
		
		//Create music
		Assets.gameOverMusic = game.getAudio().newMusic("madworld 8bit.mp3");
		Assets.levelMusic = new Music[5];
		Assets.levelMusic[0] = game.getAudio().newMusic("01 A Night Of Dizzy Spells.mp3");
		Assets.levelMusic[1] = game.getAudio().newMusic("02 Underclocked (underunderclocked mix).mp3");
		Assets.levelMusic[2] = game.getAudio().newMusic("03 All of Us.mp3");
		Assets.levelMusic[3] = game.getAudio().newMusic("04 Jumpshot.mp3");
		Assets.levelMusic[4] = game.getAudio().newMusic("05 We're the Resistors.mp3");

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
