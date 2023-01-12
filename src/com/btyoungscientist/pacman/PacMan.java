package com.btyoungscientist.pacman;

import java.awt.BorderLayout;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.awt.image.WritableRaster;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.btyoungscientist.pacman.object.GameObject;
import com.btyoungscientist.pacman.render.*;
import com.btyoungscientist.pacman.util.Mth;

public class PacMan {
	
	public static PacMan instance;
	public JFrame mainWindow;
	public static final int xRes = 224+12;
	public static final int yRes = 288+44;
	public static int windowScale = 2;
	public static final float framerate = 60.52469f;
	private long prevTime;
	public double deltaTime;
	public double deltaTimeMillis;
	public double FPS;
	public WritableRaster mainRaster;
	public BufferedImage mainImage;
	public JLabel mainJLabel;
	public JPanel jPanel;
	public static boolean currentGameIs2P;
	public static boolean playerIs2P;
	public static int score1p = 0; //multiplied by 10
	public static int scoreHigh = 0; //multiplied by 10
	public static int score2p = 0; //multiplied by 10
	public static int eatenDotCount = 0;
	private static final int flashCycle = 30; 
	private static double flashTimer = 0;
	private static double tmp_closeTimer = 0;
	BufferedImage mainTiles;
	public Graphics2D gMain;
	private static Controls controlsListener = new Controls();

	public static final int MAX_OBJECTS = 32;
	public static GameObject[] objectList = new GameObject[MAX_OBJECTS];
	public static int objectCount = 0;

	// deleted object management
	public static int[] freedIDs = new int[MAX_OBJECTS];
	public static int freedIDCount = 0;
	
	public static GameObject player;
	public static GameObject blinky;
	public static GameObject pinky;
	public static GameObject inky;
	public static GameObject clyde;
	public static GameObject readyText;
	public static GameObject playerText;
	public static GameObject gameOverText;
	
	public static int scatterTimer = 420;
	public static int scatterCount = 0;
	public static int timeToScatterAgain = 0;
	public static int bigDotTimer = 0;
	public static int lives = 3;
	public static int livesLeft = lives;
	
	public static int waitTimer = 300;
	public static boolean gameOver = true;
	
	private boolean _isSetToClose = false;
	
	public void close() {
		_isSetToClose = true;
	}
	
	public static GameObject spawnObject(GameObject object) {
		object.pacMan = instance;
		int idToPopulate = objectCount;
		if (freedIDCount != 0) {
			freedIDCount--; // pop it off
			idToPopulate = freedIDs[freedIDCount];
		} else {
			objectCount++;
		}
		object.id = idToPopulate;
		objectList[idToPopulate] = object;
		return object;
	}
	
	// make sure object is otherwise fully dereferenced first before using
	public static void deleteObject(GameObject object) {
		freedIDs[freedIDCount] = object.id;
		freedIDCount++; // push it on
		objectList[object.id] = null; // remove it from the list
	}
	
	private static void updateObjects() {
		for (int i=0; i<objectCount; i++) {
			if (objectList[i] == null)
				continue;
			objectList[i].update();
		}
	}
	
	private static void drawObjectsPaused() {
		for (int i=0; i<objectCount; i++) {
			if (objectList[i] == null)
				continue;
			objectList[i].draw(true);
		}
	}
	
	public static void addScore(int score) {
		if (playerIs2P) {
			score2p += score;
		} else {
			score1p += score;
			if (score1p == 1000)
				livesLeft++;
		}
	}
	
	private void gameLoop() {
		while (mainWindow != null && mainWindow.isVisible()) { // gameloop!
			
			while (System.nanoTime()-prevTime < 1000.0d/framerate/0.000001d) {
				// ghetto vsync
			}
			
			if (_isSetToClose)
				break;
			
			if (tmp_closeTimer > 120)
				reset();
			
			//score1p++;
			//Date date = new Date();
			deltaTime = (System.nanoTime()-prevTime)*0.06d*0.000001d; // 60fps mapping I hope
			deltaTimeMillis = (System.nanoTime()-prevTime)*0.000001d;
			FPS = ((1000/0.000001d/(System.nanoTime()+1-prevTime)));
			//Debug.println("Loop! Previous frame took "+String.format("%.2f",deltaTimeMillis)+"ms, which is approx. "+String.format("%.2f",FPS)+"fps");
			mainWindow.setTitle(String.format("Pac-Man (%.2f FPS)", FPS));
			int clear[] = new int[xRes*yRes];
			
			if (waitTimer > 0)
				flashTimer = 0;
			else
				flashTimer = Mth.mod(flashTimer + 1, flashCycle);
			
			// clear
			for (int i=0; i < xRes*yRes; i++) {
				clear[i] = 0;
			}
			
			final int[] a = ( (DataBufferInt) mainImage.getRaster().getDataBuffer() ).getData();
			System.arraycopy(clear, 0, a, 0, clear.length);
			
			Tilemaps.drawToMain(Tilemaps.highScorePrint, 0);
			
			int[] _1upBlackout = new int[] {0,0,0};
			
			if ((Math.floor(flashTimer/(flashCycle/2)) != 0))
				Tilemaps.drawToMain(_1upBlackout, 4);
			
			// draw 1up
		    int[] player1ScoreTilemap = Tilemaps.intToTilemap(score1p);
		    for (int i = 0; i < player1ScoreTilemap.length; i++) {
		    	background[i+(35-player1ScoreTilemap.length)] = player1ScoreTilemap[i];
		    }
		    
		    background[35] = Tilemaps.chr.zero;
		    scoreHigh = Math.max(scoreHigh, Math.max(score2p, score1p));
		    
			// draw high score
			if (scoreHigh != 0) {
				int[] highScoreTilemap = Tilemaps.intToTilemap(scoreHigh);
				for (int i = 0; i < highScoreTilemap.length; i++) {
					background[i+(44-highScoreTilemap.length)] = highScoreTilemap[i];
				}
				background[44] = Tilemaps.chr.zero;
			}
			
			// draw 2up
			if (currentGameIs2P) {
				int[] player2ScoreTilemap = Tilemaps.intToTilemap(score2p);
				for (int i = 0; i < player2ScoreTilemap.length; i++) {
					background[i+(44-player2ScoreTilemap.length)] = player2ScoreTilemap[i];
				}
				background[44] = Tilemaps.chr.zero;
			}
			
			// draw background tiles
			for (int i = 0; i < background.length; i++) {
				int xcoord = (i % 28)*8;
				int ycoord = (i / 28)*8;
				if (!((background[i] == 0x2E) && (Math.floor(flashTimer/(flashCycle/2)) != 0)))
					gMain.drawImage(mainTiles, xcoord, ycoord, xcoord+8, ycoord+8 , 0, background[i]*8, 8, (background[i]*8)+8, null);
			}
			
			Tilemaps.drawToMain(Tilemaps.livesPrint, 982);
			
			if (waitTimer > 0)
				waitTimer--;
			
			int[] livesTilemap = Tilemaps.intToTilemap(livesLeft);
			Tilemaps.drawToMain(livesTilemap, 988);
			
			if (waitTimer > 0 || gameOver) {
				drawObjectsPaused();
				if (waitTimer <= 150) {
					if (playerText != null)
					{
						deleteObject(playerText);
						playerText = null;
						livesLeft--;
					}
				}
				
				if (gameOver) {
					waitTimer = 300;
					
					if (Controls.keyGotten) {
						gameOver = false;
						resetAll();
					}
					
					if (gameOverText == null) {
						gameOverText = spawnObject(new com.btyoungscientist.pacman.object.Text(84, 168, 3));
					}
				} else if (gameOverText != null) {
					deleteObject(gameOverText);
					gameOverText = null;
				}
				
				Sprite.RenderSprites();
				FinishUpFrame();
				continue;
			} else {
				if (readyText != null)
				{
					deleteObject(readyText);
					readyText = null;
				}
			}
			
			if (eatenDotCount >= 248) {
				tmp_closeTimer++;
				drawObjectsPaused();
				if (waitTimer <= 150)
					Sprite.RenderSprites();
				FinishUpFrame();
				continue;
			}
			
			if (waitTimer <= 0)
				updateObjects();

			//int[] eatenDotCountTilemap = Tilemaps.intToTilemap(eatenDotCount);
			//Tilemaps.drawToMain(eatenDotCountTilemap, 999);
			
			if (bigDotTimer > 0 && waitTimer <= 0) {
				bigDotTimer--;
			} else {
			if (scatterTimer > 0 && waitTimer <= 0)
				scatterTimer--;
			else if (waitTimer <= 0)
				timeToScatterAgain++;
			if (timeToScatterAgain >= 1200 && scatterCount < 3) {
				timeToScatterAgain = 1;
				scatterCount++;
				if (scatterCount >= 2) 
					scatterTimer = 300;
				else
					scatterTimer = 420;
			}
			}
			
			Sprite.RenderSprites();
			
			FinishUpFrame();
		}
	}
	
	private void FinishUpFrame() {
		// frame end stuff 
		mainWindow.repaint();
		prevTime = System.nanoTime();
		controlsListener.updateOld();
	}
	
	private void createWindow() {
		try {
			mainTiles = ImageIO.read(getClass().getResourceAsStream("/sprite/tiles.png"));
		} catch (IOException e) {
			e.printStackTrace();
			System.exit(0);
		}
		
		mainImage   = new BufferedImage(xRes, yRes, BufferedImage.TYPE_INT_RGB);
		gMain = (Graphics2D) mainImage.getGraphics();
		mainRaster = mainImage.getRaster();
		mainWindow = new PacFrame();
		mainWindow.setLayout(new BorderLayout());
		mainWindow.addKeyListener(controlsListener);
		jPanel = new PacPanel();
		mainWindow.add(jPanel, BorderLayout.CENTER);
		mainWindow.pack();
		mainWindow.setVisible(true);
		if (gameOver) {
			gameOverText = spawnObject(new com.btyoungscientist.pacman.object.Text(84, 168, 3));
		} else {
			playerText = spawnObject(new com.btyoungscientist.pacman.object.Text(80, 120, 1));
			readyText = spawnObject(new com.btyoungscientist.pacman.object.Text(96, 168, 0));
		}
		clyde = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Clyde());
		inky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Inky());
		pinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Pinky());
		blinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Blinky());
		player = spawnObject(new com.btyoungscientist.pacman.object.PacMan());
	}
	
	// entry point
	public static void main(String[] args) {
		Debug.println("Starting Pac-Man!");
		instance = new PacMan();
		
		ArgumentHandlers.parseArgs(args);
		instance.createWindow();
		instance.gameLoop();
		Debug.println("Exiting!");
		System.exit(0);
	}
	
	public void bigDot() {
		bigDotTimer = 380;
		eatenGhostCount = 0;
	}
	
	public void die() {
		waitTimer = 150;
		bigDotTimer = 0;
		scatterTimer = 300;
		livesLeft--;
		if (livesLeft < 0) {
			//resetAll();
			livesLeft = 0;
			gameOver = true;
			return;
		}
		deleteObject(player);
		deleteObject(blinky);
		deleteObject(pinky);
		deleteObject(inky);
		deleteObject(clyde);
		if (playerText != null)
			deleteObject(playerText);
		if (readyText != null)
			deleteObject(readyText);
		readyText = spawnObject(new com.btyoungscientist.pacman.object.Text(96, 168, 0));
		clyde = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Clyde());
		inky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Inky());
		pinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Pinky());
		blinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Blinky());
		player = spawnObject(new com.btyoungscientist.pacman.object.PacMan());
	}
	
	public void reset() {
		waitTimer = 150;
		bigDotTimer = 0;
		eatenDotCount = 0;
		tmp_closeTimer = 0;
		scatterTimer = 300;
		deleteObject(player);
		deleteObject(blinky);
		deleteObject(pinky);
		deleteObject(inky);
		deleteObject(clyde);
		if (playerText != null)
			deleteObject(playerText);
		if (readyText != null)
			deleteObject(readyText);
		readyText = spawnObject(new com.btyoungscientist.pacman.object.Text(96, 168, 0));
		clyde = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Clyde());
		inky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Inky());
		pinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Pinky());
		blinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Blinky());
		player = spawnObject(new com.btyoungscientist.pacman.object.PacMan());
		background = Maze.maze.clone();
	}
	
	public void resetAll() {
		waitTimer = 300;
		bigDotTimer = 0;
		eatenDotCount = 0;
		tmp_closeTimer = 0;
		scatterTimer = 420;
		scatterCount = 0;
		livesLeft = lives;
		score1p = 0;
		score2p = 0;
		try {
			deleteObject(player);
			deleteObject(blinky);
			deleteObject(pinky);
			deleteObject(inky);
			deleteObject(clyde);
		} catch (Exception e) {
			Debug.println("No objects to delete in resetAll");
		}
		if (playerText != null)
			deleteObject(playerText);
		if (readyText != null)
			deleteObject(readyText);
		playerText = spawnObject(new com.btyoungscientist.pacman.object.Text(80, 120, 1));
		readyText = spawnObject(new com.btyoungscientist.pacman.object.Text(96, 168, 0));
		clyde = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Clyde());
		inky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Inky());
		pinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Pinky());
		blinky = spawnObject(new com.btyoungscientist.pacman.object.ghosts.Blinky());
		player = spawnObject(new com.btyoungscientist.pacman.object.PacMan());
		background = Maze.maze.clone();
	}
	
	public int eatenGhostCount = 0;
	
	public void eatGhost() {
		eatenGhostCount++;
		int scoreToAdd = (int)Math.pow(2, eatenGhostCount) * 10;
		addScore(scoreToAdd);
		Debug.printf("Awarded player with %d points for eating a ghost.\n", scoreToAdd*10);
	}
	
	public static int[] background = Maze.maze.clone();
}
