package com.btyoungscientist.pacman.object.ghosts;

import com.btyoungscientist.pacman.PacMan;
import com.btyoungscientist.pacman.object.GameObject;
import com.btyoungscientist.pacman.render.*;
import com.btyoungscientist.pacman.render.Animation.AnimationFrame;

public class Score extends GameObject {
	
	Animation scores = new Animation(new AnimationFrame[] { // points
			new AnimationFrame(16, 16, 0, 96),
			new AnimationFrame(16, 16, 16, 96),
			new AnimationFrame(16, 16, 32, 96),
			new AnimationFrame(16, 16, 48, 96),
	});
	
	int scoreType = 0;
	
	public Score(int posX, int posY, int scoreType) {
		super();
		this.scoreType = scoreType;
		this.posx = posX;
		this.posy = posY;
	}
	
	int timer = 0;
	public void update() {
		if (PacMan.waitTimer == 0)
			PacMan.deleteObject(this);
	}
	
	public void draw(boolean drawOnly) {
		AnimationFrame curFrame = scores.frames[scoreType];
		Sprite.DrawSprite("/sprite/ghosts.png", posx-8, posy-8, curFrame.width, curFrame.height, curFrame.rectX, curFrame.rectY);
	}
	
}
