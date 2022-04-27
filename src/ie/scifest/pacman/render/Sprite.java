package ie.scifest.pacman.render;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;

import javax.imageio.ImageIO;

import ie.scifest.pacman.PacMan;

public class Sprite {
	public static class DrawCall {
		public String path;
		public int width;
		public int height;
		public int posX;
		public int posY;
		public int rectX;
		public int rectY;
	};
	
	private static int length = 0;
	
	private static HashMap<String, BufferedImage> sprites = new HashMap<String, BufferedImage>();
	
	private static DrawCall[] DrawList = new DrawCall[64];
	
	public static void ClearDrawList() {
		length = 0;
	}
	
	public static void DrawSprite(String _path, int _posX, int _posY, int _width, int _height, int _rectX, int _rectY) {
		//DrawCall newCall = new DrawCall();
		if (DrawList[length] == null) {
			for (int i=0; i<DrawList.length; i++) {
				DrawList[i] = new DrawCall();
			}
		}
		DrawList[length].path = _path;
		DrawList[length].width = _width;
		DrawList[length].height = _height;
		DrawList[length].posX = _posX;
		DrawList[length].posY = _posY;
		DrawList[length].rectX = _rectX;
		DrawList[length].rectY = _rectY;
		//DrawList[length] = newCall;
		length++;
	}
	
	public static void RenderSprites() {
		for (int i=0; i<length; i++) {
			DrawCall curCall = DrawList[i];
			BufferedImage curImg;
			if (sprites.containsKey(curCall.path)) {
				curImg = sprites.get(curCall.path);
			} else {
				try {
					curImg = ImageIO.read(PacMan.class.getResourceAsStream(curCall.path));
					sprites.put(curCall.path, curImg);
				} catch (IOException e) {
					e.printStackTrace();
					continue;
				}
			}
			PacMan.instance.gMain.drawImage(curImg, curCall.posX, curCall.posY, curCall.posX+curCall.width, curCall.posY+curCall.height , curCall.rectX, curCall.rectY, curCall.rectX+curCall.width, curCall.rectY+curCall.height, null);
		}
		ClearDrawList();
	}
}
