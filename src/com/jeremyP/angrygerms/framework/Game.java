package com.jeremyP.angrygerms.framework;

import android.content.Context;

import com.jeremyP.angrygerms.MainMenuScreen;

public interface Game 
{
	public Input getInput();
	
	public FileIO getFileIO();
	
	public Graphics getGraphics();
	
	public Audio getAudio();
	
	public Context getContext();
	
	public void setScreen(Screen screen);
	
	public Screen getCurrentScreen();
	
	public Screen getStartScreen();
}
