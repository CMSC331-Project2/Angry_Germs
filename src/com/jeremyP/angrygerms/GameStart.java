package com.jeremyP.angrygerms;

import com.jeremyP.angrygerms.framework.Screen;
import com.jeremyP.angrygerms.framework.impl.AndroidGame;

public class GameStart extends AndroidGame
{
	public Screen getStartScreen()
	{
		return new LoadingScreen(this);
	}
}
