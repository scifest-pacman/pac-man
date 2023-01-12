package com.btyoungscientist.pacman;

public class Debug {
	static final boolean enableprintln = false;
	public static void println(String a) {
		if (enableprintln)
			System.out.println(a);
	}
	public static void printf(String a, Object... args) {
		if (enableprintln)
			System.out.printf(a, args);
	}
}
