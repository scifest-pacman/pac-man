package ie.scifest.pacman.render;

public class Animation {
	
	public static class AnimationFrame {
		public int width;
		public int height;
		public int rectX;
		public int rectY;
		
		public AnimationFrame(int width, int height, int rectX, int rectY) {
			this.width = width;
			this.height = height;
			this.rectX = rectX;
			this.rectY = rectY;
		}
	}
	
	public AnimationFrame[] frames;
	
	public Animation(AnimationFrame[] frames) {
		this.frames = frames;
	}
}
