package com.btyoungscientist.pacman.object.ghosts;

public class Clyde extends Ghost {
	
	public Clyde() {
		super();
		posx = posxprev = 128;
		posy = posyprev = 144;
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
