package com.btyoungscientist.pacman.object.ghosts;

public class Inky extends Ghost {
	
	public Inky() {
		super();
		posx = posxprev = 96;
		posy = posyprev = 144;
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
