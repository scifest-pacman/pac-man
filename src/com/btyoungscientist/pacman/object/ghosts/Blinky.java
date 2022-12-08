package com.btyoungscientist.pacman.object.ghosts;

public class Blinky extends Ghost{
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
