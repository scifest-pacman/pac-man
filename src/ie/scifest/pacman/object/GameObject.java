package ie.scifest.pacman.object;

import ie.scifest.pacman.PacMan;
import ie.scifest.pacman.util.Mth;

public class GameObject {
	int posx;
	int posy;
	
	enum rotations {
		left,
		right,
		up,
		down
	}
	
	rotations rotation = rotations.left;
	public PacMan pacMan;
	
	public GameObject() {
		
	}
	
	public void update() {
		posx = Mth.mod(posx, PacMan.xRes);
		posy = Mth.mod(posy, PacMan.yRes);
	}
	
	public void draw(boolean drawOnly) {};
	
}
