package com.btyoungscientist.pacman.render;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import com.btyoungscientist.pacman.PacMan;

public class PacPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public PacPanel() {
		// pretty stupid hack if I say so myself lol
        this.setPreferredSize(new Dimension((PacMan.xRes*PacMan.windowScale)-((PacMan.windowScale-1)*16), (PacMan.yRes*PacMan.windowScale)-((PacMan.windowScale-1)*48)));
        this.setMaximumSize(this.getPreferredSize());
        this.setMinimumSize(this.getPreferredSize());
    }
	
	@Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        //Debug.println("Repainting PacPanel!");
        g.drawImage(PacMan.instance.mainImage, 0, 1, PacMan.xRes*PacMan.windowScale, PacMan.yRes*PacMan.windowScale, null);
    }
}
