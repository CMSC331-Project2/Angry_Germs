package com.jeremyP.diseasedefense;

public class Level {

	//Scores needed to get to next level
	//private static int LEVEL_ONE 	= 20;
	//private static int LEVEL_TWO 	= 50;
	//private static int LEVEL_THREE 	= 100;
	//private static int LEVEL_FOUR 	= 325;
	//private static int LEVEL_FIVE 	= 700;
	//private static int LEVEL_SIX 	= 1000;
	//private final static int LEVEL_X[] = {2, 50, 100, 325, 700, 1000};
	private final static int LEVEL_X[] = {2, 4, 6, 8, 10};

	private int currentLevel;	/* Holds the current playing level */
	private int score;			/* Holds the current score */
	private boolean changed;	/* Tells whether the level has changed */
	
	/**
	 * Plain constructor that starts you off with score of 0 at LEVEL one
	 */
	public Level(){
		currentLevel = 1;
		score = 0;
		changed = false;
	}
	
	/**
	 * Returns whether the last level has been beaten or not
	 * @return whether the game has been beaten
	 */
	public boolean isEnd(){
		return currentLevel > LEVEL_X.length;
	}
	/**
	 * Tells if the level has changed
	 * NOTE: When this method is called and it returns true, this will return false until
	 * 		the level changes again.
	 * @return
	 */
	public boolean isChanged(){
		
		if(changed){
			changed = false;
			score = 0;
			return true;
		}else {
			return false;
		}
	}
	
	/**
	 * Calls addScore(1) - adds one point
	 * @return if 1, your just went to the next level.
	 */
	public int addScore(){
		return this.addScore(1);
	}
	
	/**
	 * Adds more points to total score
	 * @param points - the amount of points being added
	 * @return if 1, your just went to the next level.
	 */
	public int addScore(int points){
		score += points;
		
		//Check to see if got the next level
		if(score > this.pointsNeeded()){
			currentLevel++;
			changed = true;
			return 1;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * Gives the scores
	 * @return score
	 */
	public int getScore(){
		return score;
	}
	
	/**
	 * Gives the current level
	 * @return current level
	 */
	public int whatLevel(){
		return currentLevel;
	}
	
	/**
	 * Get the amount of points needed to get the next level
	 * @return points needed
	 */
	private int pointsNeeded(){
		
		/*if(currentLevel == LEVEL_ONE){
			return LEVEL_ONE;
		}else if(currentLevel == LEVEL_TWO){
			return LEVEL_TWO;
		}else if(currentLevel == LEVEL_THREE){
			return LEVEL_THREE;
		}else if(currentLevel == LEVEL_FOUR){
			return LEVEL_ONE;
		}else if(currentLevel == LEVEL_FIVE){
			return LEVEL_ONE;
		}else if(currentLevel == LEVEL_SIX){
			return LEVEL_ONE;
		}else{
			
			//You should really never get to here
			return 0;
		}*/
		
		return LEVEL_X[currentLevel-1];
	}
}
