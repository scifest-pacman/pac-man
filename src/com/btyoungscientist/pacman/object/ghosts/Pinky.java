package com.btyoungscientist.pacman.object.ghosts;

public class Pinky extends Ghost {
	
	int homeX = 112;
	int homeY = 144;
	
	int[] getHome() {
		int targetX = (int)Math.floor(homeX/8)*8;
		int targetY = (int)Math.floor(homeY/8)*8;
		return new int[] { targetX, targetY };
	}
	
	public Pinky() {
		super();
		posx = posxprev = homeX;
		posy = posyprev = homeY;
	}
	
	int getGhostColorID() {
		return 1;
	}
	
	int minimumPointsToExit() {
		return 0;
	}
	
	boolean spawnsInGhostHouse() {
		return true;
	}
	
	int[] getScatterTarget() {
		return new int[] { 20, 4 };
	}
	
	int[] getTargetOffset(rotations rot) {
		int[] target = new int[] { 0, 0 };
		switch(rot) {
		case right:
			target = new int[] {32,0};
			break;
		case left:
			target = new int[] {-32,0};
			break;
		case up:
			target = new int[] {-32,-32};
			break;
		case down:
			target = new int[] {0, 32};
			break;
		}
		return target;
	}
	
	
}
