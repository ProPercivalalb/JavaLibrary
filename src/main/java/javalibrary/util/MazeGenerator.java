package javalibrary.util;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;

public class MazeGenerator {

	public static class Maze {
		
		public static int EMPTY_MASK = 1;
		public static int VISITED_MASK = 2;
		
		public int[] data;
		public int width, height;
		public Random random;
		
		public Maze(int width, int height) {
			this.data = new int[width * height];
			Arrays.fill(this.data, 0);
			this.width = width;
			this.height = height;
			this.random = new Random(System.currentTimeMillis());
		}
		
		public void setBlock(int x, int y, int mask) {
			this.setBlock(x * this.width + y, mask);
		}
		
		public void setBlock(int postion, int mask) {
			this.data[postion] |= mask;
		}
		
		public boolean isBlockEmpty(int x, int y) {
			return (this.data[x * this.width + y] & Maze.EMPTY_MASK) != 0;
		}
		
		public boolean isBlockEmpty(int postion) {
			return (this.data[postion] & Maze.EMPTY_MASK) != 0;
		}
		
		public boolean isBlockVisited(int x, int y) {
			return (this.data[x * this.width + y] & Maze.VISITED_MASK) != 0;
		}
		
		public boolean isBlockVisited(int postion) {
			return (this.data[postion] & Maze.VISITED_MASK) != 0;
		}
		
		public void printMaze() {
			for(int x = 0; x < this.width; x += 1) {
				String str = "";
				for(int y = 0; y < this.height; y += 1)
					str += this.isBlockEmpty(x, y) ? " " : new String(Character.toChars(0x25A0));
				
				System.out.println(str);
			}
		}
	}
	
	public static Maze createMaze(int width, int height) {
		Maze maze = new Maze(width, height);

		HashMap<Integer, Integer> groups = new HashMap<Integer, Integer>();
		
		int group = 0;
		for(int x = 1; x < width; x += 2) {
			for(int y = 1; y < height; y += 2) {
				maze.setBlock(x, y, 1);
				groups.put(x * width + y, group++);
			}
		}
		
		while(true) {
			int intersection = RandomUtil.pickRandomKey(groups);

			int dir = RandomUtil.pickRandomElement(new Integer[] {-width, +width, -1, +1});
			
			int nextPosition = intersection + dir;
			
			if(nextPosition < 0 || nextPosition >= maze.data.length || maze.isBlockEmpty(nextPosition))
				continue;
			
			int nextIntersection = intersection + dir * 2;
			
			//Spot it wants to connect to doesn't exist
			if(!groups.containsKey(nextIntersection))
				continue;
			
			int thisid = groups.get(intersection);
			int oldid = groups.get(nextIntersection);
			
			if(oldid == thisid) //Already Connected
				continue;
			
			//Set the old group to the next one combining the groups
			for(Integer spot : groups.keySet())
				if(groups.get(spot) == oldid)
					groups.put(spot, thisid);

			maze.setBlock(nextPosition, Maze.EMPTY_MASK);
			
			//Checks if all groups are the same - all pathways are connected
			boolean done = true;
			for(Integer spot : groups.keySet()) {
				if(groups.get(spot) != thisid) {
					done = false;
					break;
				}
			}
			
			if(done)
				break;
		}
		
		
		return maze;
	}
	
	public static Maze createMaze2(int width, int height) {
		Maze maze = new Maze(width, height);
		int randomPos = RandomUtil.pickRandomInt(width * height);
		maze.setBlock(randomPos, Maze.EMPTY_MASK);
		
		int lastPos = randomPos;
		int lastPos2 = lastPos;
		while(true) {
			int dir = RandomUtil.pickRandomElement(new Integer[] {-width, +width, -1, +1});
			int nextPosition = lastPos + dir;
			int nextIntersection = lastPos + dir * 2;
			
			lastPos2 = lastPos;
			lastPos = nextIntersection;
			
			if(nextPosition < 0 || nextPosition >= maze.data.length || maze.isBlockEmpty(nextPosition) || nextIntersection < 0 || nextIntersection >= maze.data.length) {
				lastPos = lastPos2;
				continue;
			}

			
			maze.setBlock(nextPosition, Maze.EMPTY_MASK);
			maze.setBlock(nextIntersection, Maze.EMPTY_MASK);

			if(RandomUtil.pickRandomInt(100) == 0)
				break;
		}
		
		return maze;
	}
}
