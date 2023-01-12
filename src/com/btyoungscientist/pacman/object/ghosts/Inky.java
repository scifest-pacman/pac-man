package com.btyoungscientist.pacman.object.ghosts;

public class Inky extends Ghost {
	
	int homeX = 96;
	int homeY = 140;
	
	int[] getHome() {
		int targetX = (int)Math.floor(homeX/8)*8;
		int targetY = (int)Math.floor(homeY/8)*8;
		return new int[] { targetX, targetY };
	}
	
	public Inky() {
		super();
		posx = posxprev = homeX;
		posy = posyprev = homeY;
	}
	
	Ghost getGhostToWaitOn() {
		return (Ghost) pacMan.pinky;
	}
	
	int getGhostColorID() {
		return 2;
	}
	
	boolean IsAwareOfBlinky() {
		return true;
	}
	
	boolean spawnsInGhostHouse() {
		return true;
	}
	
	int minimumPointsToExit() {
		return 30;
	}
	
	int[] getScatterTarget() {
		return new int[] { 220, 290 };
	}
}
