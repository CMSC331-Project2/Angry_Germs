package com.jeremyP.diseasedefense;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

import com.jeremyP.diseasedefense.GameScreen.GameState;
import com.jeremyP.diseasedefense.framework.FileIO;
import com.jeremyP.diseasedefense.framework.Game;
import com.jeremyP.diseasedefense.framework.Graphics;
import com.jeremyP.diseasedefense.framework.Screen;
import com.jeremyP.diseasedefense.framework.Input.TouchEvent;
import com.jeremyP.diseasedefense.framework.impl.AndroidFileIO;

public class HighscoreScreen extends Screen {

	private Graphics g;
	private static final String HIGHSCORE_FILE = ".ag_hs";
	private static final int MAX_SCORES_KEPT = 10;
	private static String[] names = new String[MAX_SCORES_KEPT];
	private static String[] scores = new String[MAX_SCORES_KEPT];

	public HighscoreScreen(Game game) {
		super(game);
		g = game.getGraphics();
		
	}

	public static void load(FileIO files){
		
		BufferedReader in = null;
		int scoreIndex = 0; 
		
		try{
			in = new BufferedReader(new InputStreamReader(files.readFile(HIGHSCORE_FILE)));
			
			String line;
			while ((line = in.readLine()) != null && scoreIndex < MAX_SCORES_KEPT) {

				String[] nas = line.split(" ");
				names[scoreIndex] = nas[0];
				scores[scoreIndex] = nas[1];
				scoreIndex++;
			}
		}catch(IOException ioe){
			System.out.println("ERROR: Could not open/read file.");
		}finally{
			try{
				if(in != null)
					in.close();
				
				int score = 100;
				char[] tempName = {'A', 'A', 'A'};
				for(int i=scoreIndex; i < MAX_SCORES_KEPT; i++){
					names[i] = new String(tempName);
//					for(int j=0; j < tempName.length; i++){
//						tempName[i]++;
//					}
					scores[i] = score + "";
					score -= 10;
				}
				
				}catch (Exception e) {}
		}		
		
	}
	
	public static void save(FileIO files){
		
		BufferedWriter out = null;
		
		try{
			
			out = new BufferedWriter(new OutputStreamWriter(files.writeFile(HIGHSCORE_FILE)));

			//to be sure, sort the scores
			sortScores();
			
			//save highscores and names
			for(int i=0; i < MAX_SCORES_KEPT; i++){
				out.write(names[i]);
				out.write(" ");
				out.write(scores[i]);
				if(i != MAX_SCORES_KEPT-1)
					out.write("\n");
			}
		}catch(IOException ioe) {
			System.out.println("ERROR: Could not write/save file.");
		}
		finally{
			try{
				if(out != null)
					out.close();
			}catch(IOException ioe) {}
		}
	}
	
	private static void sortScores() {

		System.out.println(scores.toString());
		System.out.println("length: " + scores.length);

		int i = 0;
		int j = 0;

		try {
			for (j = 0; j < MAX_SCORES_KEPT; j++) {
				for (i = j; i < MAX_SCORES_KEPT; i++) {
					if (Integer.parseInt(scores[i]) > Integer.parseInt(scores[j])) {
						// switch score
						String temp = scores[j];
						scores[j] = scores[i];
						scores[i] = temp;

						// switch name
						temp = names[j];
						names[j] = names[i];
						names[i] = temp;
					}
				}
			}
		} catch (NumberFormatException nfe) {
			System.out.println("i: " + i);
			System.out.println("scores[i]" + scores[i]);
			System.out.println("j: " + j);
			System.out.println("scores[j]" + scores[j]);
		}
	}
	
	@Override
	public void update(float deltaTime) {
		// TODO Auto-generated method stub
		List<TouchEvent> touchEvents = game.getInput().getTouchEvents();

		for (int i = 0; i < touchEvents.size(); i++) {
			TouchEvent event = touchEvents.get(i);
			if (event.type == TouchEvent.TOUCH_UP) {
				int home_x = 0;
				int home_y = g.getHeight() - Assets.home.getHeight();

				// Did you click the Continue button?
				if (event.x > home_x
						&& event.x < home_x + Assets.contin.getWidth()
						&& event.y > home_y
						&& event.y < home_y + Assets.contin.getHeight()) {
					Assets.click.play(1);
					game.setScreen(new MainMenuScreen(game));
					return;
				}
			}
		}

	}

	@Override
	public void present(float deltaTime) {
		// TODO Auto-generated method stub
		g.clear(0);

		GameScreen.drawText(g, "highscores", 0, 0, true, false);

		for(int i=0; i < MAX_SCORES_KEPT-1; i++){
			GameScreen.drawText(g, " " + (i+1) + " " + names[i] + " " + scores[i], 0, 40*(i+1), true, false);
		}
		GameScreen.drawText(g, "10 " + names[9] + " " + scores[9], 0, 40*(9+1), true, false);
		
		g.drawPixmap(Assets.home, 0, g.getHeight() - Assets.home.getHeight());
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

	public static boolean isNewHighScore(Game game, int cumScore) {
		
		//to be sure, sort the scores
		sortScores();
		
		for(int i=0; i < MAX_SCORES_KEPT; i++){
			
			int savedScore = Integer.parseInt(scores[i]);
			
			if(savedScore < cumScore)
				return true;		
		}
		
		return false;
	}

	public static void addHighScore(Game game, String name, String score) {

		String nComp = name;
		String sComp = score;
		
		//to be sure, sort the scores
		sortScores();
		
		for(int i=0; i < MAX_SCORES_KEPT; i++){
			if(Integer.parseInt(scores[i]) < Integer.parseInt(sComp)){
				String n = names[i];
				String s = scores[i] + "";
				
				names[i] = nComp;
				scores[i] = sComp;
				
				nComp = n;
				sComp = s;				
			}			
		}
		
	}
}
