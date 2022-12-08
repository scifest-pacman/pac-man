package com.btyoungscientist.pacman.object;

import com.btyoungscientist.pacman.Controls;
import com.btyoungscientist.pacman.Controls.Keys;
import com.btyoungscientist.pacman.render.*;
import com.btyoungscientist.pacman.render.Animation.AnimationFrame;
import com.btyoungscientist.pacman.util.Mth;

@SuppressWarnings("static-access")
public class PacMan extends GameObject {
	
	int testCoord = 0;
	int speed = 1;
	int curAnim = 0;
	int animTimer = 0;
	int animSpeedDiv = 3;
	
	int posxprev;
	int posyprev;
	
	public PacMan() {
		super();
		posx = posxprev = 104;
		posy = posyprev = 208;
	}
	
	Animation[] anims = new Animation[] {
		new Animation(new AnimationFrame[] { // left
				new AnimationFrame(16, 16, 0, 0),
				new AnimationFrame(16, 16, 16, 32),
				new AnimationFrame(16, 16, 0, 32),
				new AnimationFrame(16, 16, 16, 32),
		}),
		
		new Animation(new AnimationFrame[] { // right
				new AnimationFrame(16, 16, 0, 0),
				new AnimationFrame(16, 16, 16, 16),
				new AnimationFrame(16, 16, 0, 16),
				new AnimationFrame(16, 16, 16, 16),
		}),
		
		new Animation(new AnimationFrame[] { // up
				new AnimationFrame(16, 16, 0, 0),
				new AnimationFrame(16, 16, 16, 48),
				new AnimationFrame(16, 16, 0, 48),
				new AnimationFrame(16, 16, 16, 48),
		}),
		
		new Animation(new AnimationFrame[] { // down
				new AnimationFrame(16, 16, 0, 0),
				new AnimationFrame(16, 16, 16, 64),
				new AnimationFrame(16, 16, 0, 64),
				new AnimationFrame(16, 16, 16, 64),
		}),
		
		new Animation(new AnimationFrame[] { // death
				new AnimationFrame(16, 16, 0, 0),
				new AnimationFrame(16, 16, 16, 0),
				new AnimationFrame(16, 16, 32, 0),
				new AnimationFrame(16, 16, 48, 0),
				new AnimationFrame(16, 16, 64, 0),
				new AnimationFrame(16, 16, 80, 0),
				new AnimationFrame(16, 16, 96, 0),
				new AnimationFrame(16, 16, 112, 0),
				new AnimationFrame(16, 16, 128, 0),
				new AnimationFrame(16, 16, 144, 0),
				new AnimationFrame(16, 16, 160, 0),
				new AnimationFrame(16, 16, 176, 0),
				new AnimationFrame(16, 16, 176, 0),
				new AnimationFrame(16, 16, 176, 16), // blank
		}),
	};
	
	public void update() {
		super.update();
		testCoord++;
		
		int xColOff = 0;
		int yColOff = 0;		
		
		int upTile = (int)(Math.floor((posx+xColOff)/8.0d) + (Math.floor((posy-8+yColOff)/8.0d) * 28));
		int downTile = (int)(Math.floor((posx+xColOff)/8.0d) + (Math.floor((posy+8+yColOff)/8.0d) * 28));
		int leftTile = (int)(Math.floor((posx-8+xColOff)/8.0d) + (Math.floor((posy+yColOff)/8.0d) * 28));
		int rightTile = (int)(Math.floor((posx+8+xColOff)/8.0d) + (Math.floor((posy+yColOff)/8.0d) * 28));
		
		if (Controls.GetKey(Keys.up) && !Controls.GetKey(Keys.down) && !(pacMan.background[upTile] == 6) && posx<224) {
			rotation = rotations.up;
		} else if (Controls.GetKey(Keys.down) && !Controls.GetKey(Keys.up) && !(pacMan.background[downTile] == 6) && posx<224) {
			rotation = rotations.down;
		} else if (Controls.GetKey(Keys.left) && !Controls.GetKey(Keys.right) && !(pacMan.background[leftTile] == 6)) {
			rotation = rotations.left;
		} else if (Controls.GetKey(Keys.right) && !Controls.GetKey(Keys.left) && !(pacMan.background[rightTile] == 6)) {
			rotation = rotations.right;
		}
		
		switch (rotation) {
		case left:
			posy = ((int)Math.floor(posy/8)*8)+4;
			posx -= speed;
			break;
		case right:
			posy = ((int)Math.floor(posy/8)*8)+4;
			posx += speed;
			break;
		case up:
			posx = ((int)Math.floor(posx/8)*8)+4;
			posy -= speed;
			break;
		case down:
			posx = ((int)Math.floor(posx/8)*8)+4;
			posy += speed;
			break;
		default:
			break;
		}
		
		posx = Mth.mod(posx, pacMan.xRes);
		posy = Mth.mod(posy, pacMan.yRes);
		
		curTile = (int)(Math.floor((posx)/8.0d) + (Math.floor((posy)/8.0d) * 28));
		
		doCollision();
		
		eatDots();
		
		draw(false);
		
		//System.out.printf("%s, %s\r", posx, posy);
		
		posxprev = Mth.mod(posx, pacMan.xRes);
		posyprev = Mth.mod(posy, pacMan.yRes);
	}
	
	AnimationFrame curFrame = anims[curAnim].frames[0];
	
	public void draw(boolean drawOnly) {
		if (!drawOnly) {
		curAnim = rotation.ordinal();
		animTimer += Mth.dist(posx, posy, posxprev, posyprev);
		curFrame = anims[curAnim].frames[(int)(animTimer/animSpeedDiv) % anims[curAnim].frames.length];
		}
		Sprite.DrawSprite("/sprite/pacman.png", posx-8, posy-8, curFrame.width, curFrame.height, curFrame.rectX, curFrame.rectY);
	}
	
	public void die() {
		pacMan.close();
	}
	
	private void doCollision() {
		int sensorPosX = posx;
		int sensorPosY = posy;
		
		boolean blockInTunnel = false;
		
		switch (rotation) {
		case left:
			sensorPosX -= 4;
			break;
		case right:
			sensorPosX += 4;
			break;
		case up:
			sensorPosY -= 4;
			if (posx >= 224)
				blockInTunnel = true;
			break;
		case down:
			sensorPosY += 4;
			if (posx >= 224)
				blockInTunnel = true;
			break;
		default:
			break;
		}
		
		sensorPosX = Mth.mod(sensorPosX, pacMan.xRes);
		sensorPosY = Mth.mod(sensorPosY, pacMan.yRes);
		
		//System.out.printf("%d, %d\r",sensorPosX,sensorPosY);
		
		int currentTile = (int)(Mth.mod(Math.floor(sensorPosX/8.0d), 28) + Mth.mod((Math.floor(sensorPosY/8.0d) * 28), 40*28));
		int iterationCount = 0;
		if ((pacMan.background[currentTile] == 6 || blockInTunnel) && iterationCount < 8) { // lets just say this is a wall for now
			iterationCount++;
			sensorPosX = posx;
			sensorPosY = posy;
			
			switch (rotation) {
			case left:
				sensorPosX -= 4;
				break;
			case right:
				sensorPosX += 4;
				break;
			case up:
				sensorPosY -= 4;
				break;
			case down:
				sensorPosY += 4;
				break;
			default:
				break;
			}
			sensorPosX = Mth.mod(sensorPosX, pacMan.xRes);
			sensorPosY = Mth.mod(sensorPosY, pacMan.yRes);
			currentTile = (int)(Math.floor(sensorPosX/8.0d) + (Math.floor(sensorPosY/8.0d) * 26));
			
			posx = posxprev;
			posy = posyprev;
		}
	}
	
	private void eatDots() {
		int currentTile = (int)(Math.floor(posx/8.0d) + (Math.floor(posy/8.0d) * 28));//(int)(((double)posx/net.torutheredfox.pacman.PacMan.xRes)*26) + (int)(((double)posy/net.torutheredfox.pacman.PacMan.yRes)*33*26);
		if (pacMan.background[currentTile] == 0x2D || pacMan.background[currentTile] == 0x2E) {
			boolean isBigDot = pacMan.background[currentTile] == 0x2E;
			pacMan.addScore(isBigDot ? 5 : 1);
			if (isBigDot) {
				pacMan.bigDot();
			}
			pacMan.eatenDotCount++;
			pacMan.background[currentTile] = 0;
			switch (rotation) { // stop for a frame by undoing movement
			case left:
				posx += speed;
				break;
			case right:
				posx -= speed;
				break;
			case up:
				posy += speed;
				break;
			case down:
				posy -= speed;
				break;
			default:
				break;
			}
		}
	}
}
