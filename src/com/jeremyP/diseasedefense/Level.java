package com.jeremyP.diseasedefense;

public class Level {

	//Scores needed to get to next level
	private static int LEVEL_ONE 	= 15;
	private static int LEVEL_TWO 	= 16;
	private static int LEVEL_THREE 	= 17;
	private static int LEVEL_FOUR 	= 18;
	private static int LEVEL_FIVE 	= 19;
	private static int LEVEL_SIX 	= 20;
	
	private int currentLevel;
	private int score;
	
	/**
	 * Plain constructor that starts you off with score of 0 at LEVEL one
	 */
	public Level(){
		currentLevel = 0;
		currentLevel = LEVEL_ONE;
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
			return 1;
		}else{
			return 0;
		}
		
	}
	
	/**
	 * Get the amount of points needed to get the next level
	 * @return points needed
	 */
	private int pointsNeeded(){
		
		if(currentLevel == LEVEL_ONE){
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
		}
	}
	
	
	
}
