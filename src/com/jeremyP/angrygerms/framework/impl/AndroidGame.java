package com.jeremyP.angrygerms.framework.impl;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.PowerManager;
import android.os.PowerManager.WakeLock;
import android.view.Window;
import android.view.WindowManager;

import com.jeremyP.angrygerms.Assets;
import com.jeremyP.angrygerms.framework.Audio;
import com.jeremyP.angrygerms.framework.FileIO;
import com.jeremyP.angrygerms.framework.Game;
import com.jeremyP.angrygerms.framework.Graphics;
import com.jeremyP.angrygerms.framework.Input;
import com.jeremyP.angrygerms.framework.Screen;

public abstract class AndroidGame extends Activity implements Game
{
		AndroidFastRenderView renderView;
		Graphics graphics;
		Audio audio;
		Input input;
		FileIO fileIO;
		Screen screen;
		WakeLock wakeLock;
		Context context = this;
		int levelmusicindex;
		
		public void onCreate(Bundle savedInstanceState)
		{
			super.onCreate(savedInstanceState);
			
			requestWindowFeature(Window.FEATURE_NO_TITLE);
			getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
			
			boolean isLandscape = getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
			int frameBufferWidth = isLandscape ? 480 : 320;
			int frameBufferHeight = isLandscape ? 320 : 480;
			Bitmap frameBuffer = Bitmap.createBitmap(frameBufferWidth, frameBufferHeight, Config.RGB_565);
			
			float scaleX = (float) frameBufferWidth / getWindowManager().getDefaultDisplay().getWidth();
			float scaleY = (float) frameBufferHeight / getWindowManager().getDefaultDisplay().getHeight();
			
			renderView = new AndroidFastRenderView(this, frameBuffer);
			graphics = new AndroidGraphics(getAssets(), frameBuffer);
			fileIO = new AndroidFileIO(this);
			audio = new AndroidAudio(this);
			input = new AndroidInput(this, renderView, scaleX, scaleY);
			screen = getStartScreen();
			setContentView(renderView);
			
			PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
			wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "GLGame");
		}
		
		@Override
		public void onResume()
		{
			super.onResume();
			
			//if(Assets.levelMusic[levelmusicindex].isStopped())
				//Assets.levelMusic[levelmusicindex].play();
			
			//audio.
			
			wakeLock.acquire();
			screen.resume();
			renderView.resume();
		}
		
		@Override
		public void onPause()
		{
			super.onPause();
			
			for(int i=0; i < Assets.levelMusic.length; i++){
				if(Assets.levelMusic[i].isPlaying()){
					Assets.levelMusic[i].pause();
					levelmusicindex = i;
					break;
				}
			}
			
			if(Assets.gameOverMusic.isPlaying()){
				Assets.gameOverMusic.pause();
			}
				
			wakeLock.release();
			renderView.pause();
			screen.pause();
			
			if (isFinishing())
			{
				screen.dispose();
			}
		}
		
		public Input getInput()
		{
			return input;
		}
		
		public FileIO getFileIO()
		{
			return fileIO;
		}
		
		public Graphics getGraphics()
		{
			return graphics;
		}
		
		public Audio getAudio()
		{
			return audio;
		}

		public Context getContext()
		{
			return context;
		}
		public void setScreen(Screen screen)
		{
			if (screen == null)
			{
				throw new IllegalArgumentException("Screen must not be null");
			}	
				this.screen.pause();
				this.screen.dispose();
				screen.resume();
				screen.update(0);
				this.screen = screen;
		}
		
		public Screen getCurrentScreen()
		{
			return screen;
		}
		
		
}

