package com.btyoungscientist.pacman.object;

import com.btyoungscientist.pacman.PacMan;
import com.btyoungscientist.pacman.util.Mth;

public class GameObject {
	public int id;
	public int posx = 0;
	public int posy = 0;
	public int curTile = 0;
	
	public enum rotations {
		left,
		right,
		up,
		down
	}
	
	public rotations rotation = rotations.left;
	public PacMan pacMan;
	
	public GameObject() {
		
	}
	
	public void update() {
		posx = Mth.mod(posx, PacMan.xRes);
		posy = Mth.mod(posy, PacMan.yRes);
		curTile = (int)(Math.floor((posx)/8.0d) + (Math.floor((posy)/8.0d) * 28));
	}
	
	public void draw(boolean drawOnly) {};
	
}
