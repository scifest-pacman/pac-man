package com.btyoungscientist.pacman.object.ghosts;

public class Clyde extends Ghost {
	
	int homeX = 128;
	int homeY = 144;
	
	public Clyde() {
		super();
		posx = posxprev = homeX;
		posy = posyprev = homeY;
	}
	
	int[] getHome() {
		int targetX = (int)Math.floor(homeX/8)*8;
		int targetY = (int)Math.floor(homeY/8)*8;
		return new int[] { targetX, targetY };
	}
	
	int getGhostColorID() {
		return 3;
	}
	
	boolean isScared() {
		return true;
	}
	
	boolean spawnsInGhostHouse() {
		return true;
	}
	
	int minimumPointsToExit() {
		return 90;
	}
	
	int[] getScatterTarget() {
		return new int[] { 4, 290 };
	}
}
