package com.btyoungscientist.pacman;

public class Tilemaps {
	
	public static int[] intToTilemap(int N)
	{
	 
	    // Count digits in number N
	    int m = N;
	    int digit = 0;
	    while (m!=0) {
	 
	        // Increment number of digits
	        digit++;
	 
	        // Truncate the last
	        // digit from the number
	        m /= 10;
	    }
	 
	    // Declare char array for result
	    int[] arr;
	 
	    // Declare duplicate char array
	    int[] arr1 = new int[digit];
	 
	    // Memory allocation of array
	    arr = new int[digit];
	 
	    // Separating integer into digits and
	    // accommodate it to character array
	    int index = 0;
	    while (N!=0) {
	 
	        // Separate last digit from
	        // the number and add ASCII
	        // value of character '0' is 48
	        arr1[index] = N % 10 + chr.zero;
	        index++;
	 
	        // Truncate the last
	        // digit from the number
	        N /= 10;
	    }
	 
	    // Reverse the array for result
	    int i;
	    for (i = 0; i < index; i++) {
	        arr[i] = 
	        		arr1[index - 1 - i];
	    }
	 
	    // Char array truncate by null
	    //arr[i] = '\0';
	 
	    // Return char array
	    return arr;
	}
	
	public static void drawToMain(int[] map, int offset) {
		for (int i=0; i < map.length; i++) {
			PacMan.background[i+offset] = map[i];
		}
	}
	
	public static final int[] highScorePrint = {
			0, 0, 0, 0, chr.one, chr.u, chr.p, 0, 0, 8, 9, 7, 8, 0, 19, 3, 15, 18, 5, 0, 0, chr.two, chr.u, chr.p, 0, 0, 0, 0,
			0, 0, 0, 0, 0, 0, chr.zero, chr.zero, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
	};
	
	
	public static final int[] livesPrint = {
			chr.l, chr.i, chr.v, chr.e, chr.s, 0, chr.zero
	};
	
	public static class colFlg {
		public static final int nonSolid = 0;
		public static final int solid = 1;
		public static final int solidGhostExit = 2;
		public static final int dot = 3;
		public static final int dotSuper = 4;
		public static final int teleporter = 5;
		public static final int edibleItem = 6;
	}
	
	public static class chr {
		public static final int a = 1;
		public static final int b = 2;
		public static final int c = 3;
		public static final int d = 4;
		public static final int e = 5;
		public static final int f = 6;
		public static final int g = 7;
		public static final int h = 8;
		public static final int i = 9;
		public static final int j = 10;
		public static final int k = 11;
		public static final int l = 12;
		public static final int m = 13;
		public static final int n = 14;
		public static final int o = 15;
		public static final int p = 16;
		public static final int q = 17;
		public static final int r = 18;
		public static final int s = 19;
		public static final int t = 20;
		public static final int u = 21;
		public static final int v = 22;
		public static final int w = 23;
		public static final int x = 24;
		public static final int y = 25;
		public static final int z = 26;
		public static final int excl = 27;
		public static final int cpy = 28;
		public static final int pts1 = 29;
		public static final int pts2 = 30;
		public static final int pts3 = 31;
		public static final int zero = 32;
		public static final int one = 33;
		public static final int two = 34;
		public static final int three = 35;
		public static final int four = 36;
		public static final int five = 37;
		public static final int six = 38;
		public static final int seven = 39;
		public static final int eight = 40;
		public static final int nine = 41;
		public static final int slsh = 42;
		public static final int dsh = 43;
		public static final int quot = 44;
	}
}
