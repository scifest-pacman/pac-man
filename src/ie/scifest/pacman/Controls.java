package ie.scifest.pacman;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Controls extends KeyAdapter {     
	
	public enum Keys {
		up,
		down,
		left,
		right
	}

	// controls list
	public static boolean KeyUp = false;
	private static boolean KeyUpPrev = false;
	public static boolean KeyDown = false;
	private static boolean KeyDownPrev = false;
	public static boolean KeyLeft = false;
	private static boolean KeyLeftPrev = false;
	public static boolean KeyRight = false;
	private static boolean KeyRightPrev = false;
	
	public static boolean GetKey(Keys key) {
		boolean current = false;
		switch (key) {
		case up:
			current = KeyUp;
			break;
		case down:
			current = KeyDown;
			break;
		case left:
			current = KeyLeft;
			break;
		case right:
			current = KeyRight;
			break;
		}
		
		return current;
	}
	
	public static boolean GetKeyDown(Keys key) {
		boolean current = false;
		boolean previous = false;
		switch (key) {
		case up:
			current = KeyUp;
			previous = KeyUpPrev;
			break;
		case down:
			current = KeyDown;
			previous = KeyDownPrev;
			break;
		case left:
			current = KeyLeft;
			previous = KeyLeftPrev;
			break;
		case right:
			current = KeyRight;
			previous = KeyRightPrev;
			break;
		}
		
		return current && (current != previous);
	}
	
	public static boolean GetKeyUp(Keys key) {
		boolean current = false;
		boolean previous = false;
		switch (key) {
		case up:
			current = KeyUp;
			previous = KeyUpPrev;
			break;
		case down:
			current = KeyDown;
			previous = KeyDownPrev;
			break;
		case left:
			current = KeyLeft;
			previous = KeyLeftPrev;
			break;
		case right:
			current = KeyRight;
			previous = KeyRightPrev;
			break;
		}
		
		return !current && (current != previous);
	}
	
	public void keyPressed(KeyEvent e){        
		// New key press
		int key = e.getKeyCode();                                                

    	if (key == KeyEvent.VK_UP) {                                          
    		KeyUp = true;
    	} else if (key == KeyEvent.VK_DOWN) {                                    
    		KeyDown = true;
    	} else if (key == KeyEvent.VK_LEFT) {                                    
    		KeyLeft = true;
    	} else if (key == KeyEvent.VK_RIGHT) {                                    
    		KeyRight = true;
    	}             
	}
	
	public void keyReleased(KeyEvent e){       
		// New key press
		int key = e.getKeyCode();                                                
		
		if (key == KeyEvent.VK_UP) {                                          
			KeyUp = false;
		} else if (key == KeyEvent.VK_DOWN) {                                    
			KeyDown = false;
		} else if (key == KeyEvent.VK_LEFT) {                                    
			KeyLeft = false;
		} else if (key == KeyEvent.VK_RIGHT) {                                    
			KeyRight = false;
		}                              
	}
	
	public void updateOld() {
		KeyUpPrev = KeyUp;
		KeyDownPrev = KeyDown;
		KeyLeftPrev = KeyLeft;
		KeyRightPrev = KeyRight;
	}
	
}