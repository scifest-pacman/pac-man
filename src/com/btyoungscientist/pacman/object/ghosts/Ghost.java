package com.btyoungscientist.pacman.object.ghosts;

import java.util.*;

import com.btyoungscientist.pacman.object.*;
import com.btyoungscientist.pacman.render.*;
import com.btyoungscientist.pacman.render.Animation.AnimationFrame;
import com.btyoungscientist.pacman.util.Mth;

@SuppressWarnings("static-access")
public class Ghost extends GameObject {
	
	int testCoord = 0;
	int speed = 1;
	int curAnim = 0;
	int animTimer = 0;
	int animSpeedDiv = 3;
	
	int posxprev;
	int posyprev;
	int prevTile;
	
	boolean ghostHouseHoverPhase;
	boolean isInGhostHouse = true;
	
	boolean isFrightened = false;
	
	boolean halfStep;
	
	// AI modes
	boolean isScared() { // basically in other terms, is it Clyde
		return false;
	}
	
	int[] getScatterTarget() {
		return new int[] { 204, 4 };
	}
	
	int[] getTargetOffset(rotations rot) {
		return new int[] { 0, 0 };
	}
	
	int getGhostColorID() {
		return 0;
	}
	
	boolean IsAwareOfBlinky() {
		return false;
	}
	
	boolean spawnsInGhostHouse() {
		return false;
	}
	
	int minimumPointsToExit() {
		return 0;
	}

	PacMan player;
	
	int dbg_targetX;
	int dbg_targetY;
	
	// AI
	rotations nextDirection = rotations.left;
	
	public Ghost() {
		super();
		posx = posxprev = 112;
		posy = posyprev = 116;
		player = (PacMan)pacMan.player;
		rotation = nextDirection = spawnsInGhostHouse() ? rotations.up : rotations.left;
		isInGhostHouse = spawnsInGhostHouse();
	}
	
	Animation[] anims = new Animation[] {
		new Animation(new AnimationFrame[] { // left
				new AnimationFrame(16, 16, 32, 0),
		}),
		
		new Animation(new AnimationFrame[] { // right
				new AnimationFrame(16, 16, 32, 16),
		}),
		
		new Animation(new AnimationFrame[] { // up
				new AnimationFrame(16, 16, 32, 32),
		}),
		
		new Animation(new AnimationFrame[] { // down
				new AnimationFrame(16, 16, 32, 48),
		}),
		
		new Animation(new AnimationFrame[] { // body
				new AnimationFrame(16, 16, 0, 0),
				new AnimationFrame(16, 16, 16, 0),
		}),
		
	};
	
	private rotations DoFrightenedAI() {
		List<rotations> rotationsAbleToBeUsed = new ArrayList<>();
		try {
			int xColOff = 0;
			int yColOff = 0;		
			
			xColOff = (rotation == rotations.right ? 8 : 0) + (rotation == rotations.left ? -8 : 0);
			yColOff = (rotation == rotations.down ? 8 : 0) + (rotation == rotations.up ? -8 : 0);
			
			int _xres = com.btyoungscientist.pacman.PacMan.xRes;
			int _yres = com.btyoungscientist.pacman.PacMan.yRes;
			
			int fposx = (posx+xColOff);
			int fposy = (posy+yColOff);
			
			int upTile = (int)(Math.floor(Mth.mod(fposx,_xres)/8.0d) + (Math.floor(Mth.mod((fposy-8),_yres)/8.0d) * 28));
			int downTile = (int)(Math.floor(Mth.mod((fposx),_xres)/8.0d) + (Math.floor(Mth.mod((fposy+8),_yres)/8.0d) * 28));
			int leftTile = (int)(Math.floor(Mth.mod((fposx-8),_xres)/8.0d) + (Math.floor(Mth.mod((fposy),_yres)/8.0d) * 28));
			int rightTile = (int)(Math.floor(Mth.mod((fposx+8),_xres)/8.0d) + (Math.floor(Mth.mod((fposy),_yres)/8.0d) * 28));
			
		boolean blockInTunnel = Mth.mod(posx+xColOff, _xres)>=224;	
		
		// sorry for the YandereDev programming ^^;
		if (pacMan.background[upTile] != 6 && !blockInTunnel && rotation != rotations.down) {
			rotationsAbleToBeUsed.add(rotations.up); 
		}
		if (pacMan.background[downTile] != 6 && !blockInTunnel && rotation != rotations.up) {
			rotationsAbleToBeUsed.add(rotations.down);
		}
		if (pacMan.background[leftTile] != 6 && rotation != rotations.right) {
			rotationsAbleToBeUsed.add(rotations.left);
		}
		if (pacMan.background[rightTile] != 6 && rotation != rotations.left) {
			rotationsAbleToBeUsed.add(rotations.right);
		}
		} catch (Exception e) {
			// fixes crash when ghost wraps around screen
		}
		
		rotations tmprotation = rotation;
		
		if (rotationsAbleToBeUsed.size() > 0) {
			tmprotation = rotationsAbleToBeUsed.get((int)(Math.random()*(rotationsAbleToBeUsed.size()-1)));
		}
		
		return tmprotation;
	}
	
	private rotations DoTargetedAI() {
		List<rotations> rotationsAbleToBeUsed = new ArrayList<>();
		
		int xColOff = 0;
		int yColOff = 0;		
		
		xColOff = (rotation == rotations.right ? 8 : 0) + (rotation == rotations.left ? -8 : 0);
		yColOff = (rotation == rotations.down ? 8 : 0) + (rotation == rotations.up ? -8 : 0);
		
		int _xres = com.btyoungscientist.pacman.PacMan.xRes;
		int _yres = com.btyoungscientist.pacman.PacMan.yRes;
		
		int fposx = (posx+xColOff);
		int fposy = (posy+yColOff);
		
		int upTile = (int)(Math.floor(Mth.mod(fposx,_xres)/8.0d) + (Math.floor(Mth.mod((fposy-8),_yres)/8.0d) * 28));
		int downTile = (int)(Math.floor(Mth.mod((fposx),_xres)/8.0d) + (Math.floor(Mth.mod((fposy+8),_yres)/8.0d) * 28));
		int leftTile = (int)(Math.floor(Mth.mod((fposx-8),_xres)/8.0d) + (Math.floor(Mth.mod((fposy),_yres)/8.0d) * 28));
		int rightTile = (int)(Math.floor(Mth.mod((fposx+8),_xres)/8.0d) + (Math.floor(Mth.mod((fposy),_yres)/8.0d) * 28));
		
		try {
		
		boolean blockInTunnel = Mth.mod(posx+xColOff, _xres)>=224;	
		boolean canGoUp = !(fposx >= 84 && fposx <= 140 &&
				fposy >= 116 && fposy <= 212) && !blockInTunnel;
		
		// sorry for the YandereDev programming ^^;
		if (pacMan.background[upTile] != 6 && canGoUp && rotation != rotations.down) {
			rotationsAbleToBeUsed.add(rotations.up);
		}
		if (pacMan.background[downTile] != 6 && !blockInTunnel && rotation != rotations.up) {
			rotationsAbleToBeUsed.add(rotations.down);
		}
		if (pacMan.background[leftTile] != 6 && rotation != rotations.right) {
			rotationsAbleToBeUsed.add(rotations.left);
		}
		if (pacMan.background[rightTile] != 6 && rotation != rotations.left) {
			rotationsAbleToBeUsed.add(rotations.right);
		}
		} catch (Exception e) {
			// fixes crash when ghost wraps around screen
		}
		
		rotations tmprotation = rotation;
		
		int lowestDist = Integer.MAX_VALUE;
		
		GameObject Player = pacMan.player;
		
		if (pacMan.scatterTimer < 1 && isScared() && Mth.dist(Player.posx, Player.posy, fposx, fposy) < 64) {
			return DoFrightenedAI();
		}
		
		int[] targetOffset = getTargetOffset(player.rotation);
		
		int targetX = (int)Math.floor(Player.posx/8)*8 + targetOffset[0];
		int targetY = (int)Math.floor(Player.posy/8)*8 + targetOffset[1];
		
		if (IsAwareOfBlinky()) {
			GameObject Blinky = pacMan.blinky;
			targetX += -1 * (Math.floor(Blinky.posx/8)*8-targetX);
			targetY += -1 * (Math.floor(Blinky.posx/8)*8-targetY);
		}
		
		// center the targets onto the tiles
		targetX += 4;
		targetY += 4;
		
		if (pacMan.scatterTimer > 0) {
			int[] target = getScatterTarget();
			targetX = target[0];
			targetY = target[1];
		}
		
		targetX = Mth.mod(targetX, pacMan.xRes);
		targetY = Mth.mod(targetY, pacMan.yRes);
		
		dbg_targetX = targetX-4;
		dbg_targetY = targetY-4;
		
		if (rotationsAbleToBeUsed.contains(rotations.left)) {
			int _lowestDist = Mth.dist(posx-8, posy, targetX, targetY);
			if (_lowestDist < lowestDist) {
				tmprotation = rotations.left;
				lowestDist = _lowestDist;
			}
		}
		if (rotationsAbleToBeUsed.contains(rotations.right)) {
			int _lowestDist = Mth.dist(posx+8, posy, targetX, targetY);
			if (_lowestDist < lowestDist) {
				tmprotation = rotations.right;
				lowestDist = _lowestDist;
			}
		}
		if (rotationsAbleToBeUsed.contains(rotations.up)) {
			int _lowestDist = Mth.dist(posx, posy-8, targetX, targetY);
			if (_lowestDist < lowestDist) {
				tmprotation = rotations.up;
				lowestDist = _lowestDist;
			}
		}
		if (rotationsAbleToBeUsed.contains(rotations.down)) {
			int _lowestDist = Mth.dist(posx, posy+8, targetX, targetY);
			if (_lowestDist < lowestDist) {
				tmprotation = rotations.down;
			}
		}
		
		return tmprotation;
	}
	
	public void update() {
		super.update();
		
		testCoord++;
		
		curTile = (int)(Math.floor((posx)/8.0d) + (Math.floor((posy-8)/8.0d) * 28));
		
		if (!isFrightened && pacMan.bigDotTimer > 0) 
		{
			int xColOff = 0;
			int yColOff = 0;		
			
			int upTile = (int)(Math.floor((posx+xColOff)/8.0d) + (Math.floor((posy-8+yColOff)/8.0d) * 28));
			int downTile = (int)(Math.floor((posx+xColOff)/8.0d) + (Math.floor((posy+8+yColOff)/8.0d) * 28));
			int leftTile = (int)(Math.floor((posx-8+xColOff)/8.0d) + (Math.floor((posy+yColOff)/8.0d) * 28));
			int rightTile = (int)(Math.floor((posx+8+xColOff)/8.0d) + (Math.floor((posy+yColOff)/8.0d) * 28));
			
			isFrightened = true;
			// reverse direction
			if (rotation == rotations.right && pacMan.background[leftTile] != 6)
				nextDirection = rotation = rotations.left;
			else if (rotation == rotations.left && pacMan.background[rightTile] != 6)
				nextDirection = rotation = rotations.right;
			else if (rotation == rotations.up && pacMan.background[downTile] != 6)
				nextDirection = rotation = rotations.down;
			else if (rotation == rotations.down && pacMan.background[upTile] != 6)
				nextDirection = rotation = rotations.up;
		} else if (pacMan.bigDotTimer <= 0) {
			isFrightened = false;
		}
		
		//System.out.printf("%s, %s\r", (posx % 8) == 4, ((posy % 8) == 4));
		
		// do AI
		if (((posx % 8) == 4 && (posy % 8) == 4) || (isInGhostHouse && (posy % 8) == 4)) {
			if (prevTile != curTile) {
			
				rotation = nextDirection;
			
				if (!isInGhostHouse) {
					if (isFrightened)
						nextDirection = DoFrightenedAI();
					else	
						nextDirection = DoTargetedAI();
				} else if (posy >= 144) {
					rotation = nextDirection = rotations.up;
				} else if (posy <= 132) {
					rotation = nextDirection = rotations.down;
				}
				
				if (isInGhostHouse && pacMan.score1p >= minimumPointsToExit()) {
					if (posy < 144 && posx != 112) {
						rotation = nextDirection = rotations.down;
					} else if (posx < 112) {
						rotation = nextDirection = rotations.right;
					} else if (posx > 112) {
						rotation = nextDirection = rotations.left;
					} else {
						rotation = nextDirection = rotations.up;
					}
					
					if (posy <= 116) {
						isInGhostHouse = false;
						rotation = nextDirection = rotations.left;
						posy = 116;
						nextDirection = DoTargetedAI();
					}
				}
			}

			prevTile = curTile;
		}
		
		boolean halfSpeed = false;
		if (isFrightened || isInGhostHouse || ((posx < 44 || posx > 180) && posy == 140 )) {
			halfSpeed = true;
		}
		
		halfStep = !halfStep;
		
		int _speed = isFrightened ? 1 : speed;
		
		if (halfSpeed == false || (halfSpeed == true && halfStep)) {
		
		switch (rotation) {
		case left:
			if (!isInGhostHouse)
				posy = ((int)Math.floor(posy/8)*8)+4;
			posx -= _speed;
			break;
		case right:
			if (!isInGhostHouse)
				posy = ((int)Math.floor(posy/8)*8)+4;
			posx += _speed;
			break;
		case up:
			if (!isInGhostHouse)
				posx = ((int)Math.floor(posx/8)*8)+4;
			posy -= _speed;
			break;
		case down:
			if (!isInGhostHouse)
				posx = ((int)Math.floor(posx/8)*8)+4;
			posy += _speed;
			break;
		default:
			break;
		}
		}
		
		posx = Mth.mod(posx, pacMan.xRes);
		posy = Mth.mod(posy, pacMan.yRes);
		
		curTile = (int)(Math.floor((posx)/8.0d) + (Math.floor((posy)/8.0d) * 28));
		
		if (curTile == player.curTile) { // kill pacman
			if (!isFrightened)
				player.die();
			else {
				pacMan.deleteObject(this);
			}
		}
		
		doCollision();
		
		//eatDots();
		
		draw(false);
		
		posxprev = Mth.mod(posx, pacMan.xRes);
		posyprev = Mth.mod(posy, pacMan.yRes);
	}
	
	AnimationFrame curFrameBody = anims[curAnim].frames[0];
	AnimationFrame curFrameEyes = anims[curAnim].frames[0];
	
	public void draw(boolean drawOnly) {
		if (!drawOnly) {
			curAnim = rotation.ordinal();
			animTimer += Mth.dist(posx, posy, posxprev, posyprev);
			curFrameEyes = anims[curAnim].frames[(int)(animTimer/animSpeedDiv) % anims[curAnim].frames.length];
			curFrameBody = anims[4].frames[(int)(animTimer/animSpeedDiv) % anims[4].frames.length];
		}
		int colorOffset = (getGhostColorID() * 16);
		if (isFrightened)
			colorOffset = 4 * 16;
		Sprite.DrawSprite("/sprite/ghosts.png", posx-8, posy-8, curFrameBody.width, curFrameBody.height, curFrameBody.rectX, curFrameBody.rectY + colorOffset);
		if (!isFrightened)
			Sprite.DrawSprite("/sprite/ghosts.png", posx-8, posy-8, curFrameEyes.width, curFrameEyes.height, curFrameEyes.rectX, curFrameEyes.rectY);
		Sprite.DrawSprite("/sprite/tiles.png", dbg_targetX, dbg_targetY, 8, 8, 0, 32); // debug only!!!
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
		
		int iterationCount = 0;
		if (blockInTunnel && iterationCount < 8) { // lets just say this is a wall for now
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
			
			posx = posxprev;
			posy = posyprev;
		}
	}
	
}
