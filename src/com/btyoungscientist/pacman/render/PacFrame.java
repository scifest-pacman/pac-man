package com.btyoungscientist.pacman.render;

import java.awt.*;
import java.awt.image.*;

import javax.swing.JFrame;

import com.btyoungscientist.pacman.PacMan;

public class PacFrame extends JFrame implements ImageObserver {

	private static final long serialVersionUID = 1L;

	
	public PacFrame() {
		// pretty stupid hack if I say so myself lol
        this.setPreferredSize(new Dimension((PacMan.xRes*PacMan.windowScale)-((PacMan.windowScale-1)*16), (PacMan.yRes*PacMan.windowScale)-((PacMan.windowScale-1)*48)));
        this.setMaximumSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        Container contentPane = this.getContentPane();
        // Set the blank cursor to the JFrame.
        contentPane.setCursor( contentPane.getToolkit().createCustomCursor(
                new BufferedImage( 1, 1, BufferedImage.TYPE_INT_ARGB ),
                new Point(),
                null ) );
    }

    
}
