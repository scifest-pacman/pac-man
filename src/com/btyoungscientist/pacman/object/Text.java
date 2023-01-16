package com.btyoungscientist.pacman.object;

import com.btyoungscientist.pacman.render.*;
import com.btyoungscientist.pacman.render.Animation.AnimationFrame;

public class Text extends GameObject {
	
	Animation scores = new Animation(new AnimationFrame[] { // points
			new AnimationFrame(48, 8, 0, 0),
			new AnimationFrame(96, 8, 0, 8),
			new AnimationFrame(96, 8, 0, 16),
			new AnimationFrame(88, 8, 0, 24),
	});
	
	int textType = 0;
	
	public Text(int posX, int posY, int textType) {
		super();
		this.textType = textType;
		this.posx = posX;
		this.posy = posY;
	}
	
	
	public void draw(boolean drawOnly) {
		AnimationFrame curFrame = scores.frames[textType];
		Sprite.DrawSprite("/sprite/text.png", posx-8, posy-8, curFrame.width, curFrame.height, curFrame.rectX, curFrame.rectY);
	}
	
}
