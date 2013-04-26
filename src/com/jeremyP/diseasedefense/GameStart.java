package com.jeremyP.diseasedefense;

import com.jeremyP.diseasedefense.framework.Screen;
import com.jeremyP.diseasedefense.framework.impl.AndroidGame;

public class GameStart extends AndroidGame
{
	public Screen getStartScreen()
	{
		return new LoadingScreen(this);
	}
}
