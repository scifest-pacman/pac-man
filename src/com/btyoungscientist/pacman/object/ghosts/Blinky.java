package com.btyoungscientist.pacman.object.ghosts;

public class Blinky extends Ghost{
	
	int homeX = 112;
	int homeY = 140;
	
	int[] getHome() {
		int targetX = (int)Math.floor(homeX/8)*8;
		int targetY = (int)Math.floor(homeY/8)*8;
		return new int[] { targetX, targetY };
	}
	
	int getGhostColorID() {
		return 0;
	}
	
	int minimumPointsToExit() {
		return 0;
	}
	
	boolean spawnsInGhostHouse() {
		return false;
	}
}
