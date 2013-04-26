package com.jeremyP.diseasedefense.framework;

import com.jeremyP.diseasedefense.framework.Graphics.PixmapFormat;

public interface Pixmap 
{
	public int getWidth();
	
	public int getHeight();
	
	public PixmapFormat getFormat();
	
	public void dispose();
	
}
