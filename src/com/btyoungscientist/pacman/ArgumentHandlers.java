package com.btyoungscientist.pacman;

public class ArgumentHandlers {
	
	public static void parseArgs(String[] args) {
		for (int i=0; i<args.length; i++) {
			try {
				int _scoreHighNonDiv = -1;
				if (args[i].split("-")[1].toLowerCase().contentEquals("highscore")) {
					_scoreHighNonDiv = Integer.parseInt(args[i+1]);
				} else if (args[i].toLowerCase().contains("-highscore")) {
					_scoreHighNonDiv = Integer.parseInt(args[i].toLowerCase().split("highscore")[1]);
				}
				
				if (args[i].split("-")[1].toLowerCase().contentEquals("scale")) {
					PacMan.windowScale = Integer.parseInt(args[i+1]);
				} else if (args[i].toLowerCase().contains("-scale")) {
					PacMan.windowScale = Integer.parseInt(args[i].toLowerCase().split("scale")[1]);
				}
				if (_scoreHighNonDiv != -1) {
					PacMan.scoreHigh = _scoreHighNonDiv / 10;
					if (PacMan.scoreHigh * 10 != _scoreHighNonDiv) {
					Debug.println("WARNING: last digit of entered high score ignored!");
					}
				}
			} catch (Exception e) {
				Debug.println("Failed to parse argument.\n" + e);
			}
		}
	}
}
