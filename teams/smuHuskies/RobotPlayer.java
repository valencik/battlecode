package smuHuskies;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.TreeSet;

import battlecode.common.*;

public class RobotPlayer {

	static RobotController rc;
	static Team myTeam;
	static Team enemyTeam;
	static MapLocation enemyHQ;
	static int myRange;
	
	//Detect holes
	static int minX;
	static int minY;
	static MapLocation[][] towerMap;
	
	// See shamelessly borrowed functions at bottom of file
	static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};

	
	// To be Data Mined/Tested
	
	// Defence
	static int NUM_TOWER_PROTECTORS = 10;
	static int PROTECT_OTHERS_RANGE = 10;
	
	public static void run(RobotController incomingRc) {
		rc = incomingRc;
		myTeam = rc.getTeam();
		enemyTeam = myTeam.opponent();
		enemyHQ = rc.senseEnemyHQLocation();
		myRange = rc.getType().attackRadiusSquared;
		
		while(true) {
			if(rc.getType() == RobotType.HQ) {
				runHQ();
			} else if(rc.getType() == RobotType.TOWER) {
				runTower();
			} else if(rc.getType() == RobotType.BEAVER) {
				runBeaver();
			}
		}
		
	}
	
	public static void runHQ() {
		if(Clock.getRoundNum() == 1) {
			computeHoles();
		}
		if (rc.isCoreReady() && rc.getTeamOre()>=100) {
			try {
				if (!rc.isLocationOccupied(rc.getLocation().add(Direction.NORTH))) {
					rc.spawn(Direction.NORTH, RobotType.BEAVER);		
				}
				if (rc.isWeaponReady()) {
					attackSomething();
				}
			} catch (GameActionException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void runTower() {
		try {
			if (rc.isWeaponReady()) {
				attackSomething();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public static void runBeaver() {
		if (rc.isCoreReady()) {
			defend();
			try {
				int numHandWashStations = rc.readBroadcast(0);
				if (numHandWashStations < 1 && rc.isCoreReady()) {
					boolean successful = tryBuild(Direction.NORTH, RobotType.HANDWASHSTATION);
					if (successful) {
						rc.broadcast(0, numHandWashStations++);
					}
				}
			} catch (GameActionException e) {
				e.printStackTrace();
			}
		}
		rc.yield();
	}
	
	public static void defend() {

		// A1, Protect Self
		RobotInfo[] nearbyEnemies = getEnemiesInAttackRange();
		if(nearbyEnemies != null && nearbyEnemies.length > 0) {
			try {
				if (rc.isWeaponReady()) {
					attackSomething();
				}
			} catch (GameActionException e) {
				e.printStackTrace();
			}
			return;
		}
		
		// A2, Protect Nearby
		RobotInfo[] engagedRobots = getRobotsEngagedInAttack();
		if(engagedRobots != null && engagedRobots.length>0) { // Check broadcasts for enemies that are being attacked
			// TODO: Calculate which enemy is attacking/within range/closest to teammate
			// For now, just picking the first enemy
			for (RobotInfo robot : engagedRobots) {
				if (robot.team == enemyTeam) {
					Direction[] directions = getDirectionsToward(robot.location);
					for (Direction d : directions) {
						if (rc.isCoreReady()) {
							if(rc.canMove(d)) {
								try {
									rc.move(d);
								} catch (GameActionException e) {
									e.printStackTrace();
								}
							}
						}
						rc.yield();
					}
				}
			}
			// Once our unit is in range of the other unit, A1 will takeover
		}
		
		if(false) {
			// B1, Protect Holes From Other Towers
			// Holes to be computed once, shared in broadcast
			
			
		} else if(true) {
			// B2, Protect Towers
			// TODO: Compute in advance at HQ, check Broadcasts for tower that needs units
			MapLocation[] myTowers = rc.senseTowerLocations();
			MapLocation closestTower = myTowers[0];
			int closestDist = 999999;
			for (MapLocation tower : myTowers) {
				RobotInfo[] nearbyRobots = getTeammatesNearTower(tower);
				if (nearbyRobots.length < NUM_TOWER_PROTECTORS) {
					int dist = tower.distanceSquaredTo(enemyHQ);
					if (dist < closestDist) {
						closestDist = dist;
						closestTower = tower;
					}				
				}
			}
			// TODO: End compute
			Direction[] directions = getDirectionsToward(closestTower);
			for (Direction d : directions) {
				if (rc.isCoreReady()) {
					if(rc.canMove(d)) {
						try {
							rc.move(d);
						} catch (GameActionException e) {
							e.printStackTrace();
						}
					}
				}
				rc.yield();
			}
		} else if(false) {
			// B3, Protect Other Buildings
			// TBD
		}
	}
	
	/**
	 *  Simple helpers, more logic for these later
	 */
	public static RobotInfo[] getEnemiesInAttackRange() {
		return rc.senseNearbyRobots(myRange, enemyTeam);
	}
	
	public static RobotInfo[] getRobotsEngagedInAttack() {
		RobotInfo[] nearbyRobots = rc.senseNearbyRobots(PROTECT_OTHERS_RANGE);
		boolean hasEnemy = false;
		boolean hasFriendly = false;
		for (RobotInfo robot : nearbyRobots) {
			if(robot.team == enemyTeam) {
				hasEnemy = true;
				if (hasFriendly) {
					return nearbyRobots;
				}
			} else {
				hasFriendly = true;
				if (hasEnemy) {
					return nearbyRobots;
				}
			}
		}
		return null;
	}

	public static RobotInfo[] getTeammatesNearTower(MapLocation towerLocation) {
		return rc.senseNearbyRobots(towerLocation, RobotType.TOWER.attackRadiusSquared, myTeam);
	}
	
	// Find out if there are any holes between a teams tower and their HQ
	public static MapLocation[] computeHoles() {
		System.out.println("BYTESTART: " + Clock.getBytecodeNum());
		MapLocation[] towerLocations = rc.senseTowerLocations();
//		TreeSet<MapLocation> towerRadii = new TreeSet<MapLocation>();
		LinkedList<MapLocation[]> towerRadii = new LinkedList<MapLocation[]>();
		for(MapLocation tower : towerLocations) {
			// Get all map locations that a tower can attack
			MapLocation[] locations = MapLocation.getAllMapLocationsWithinRadiusSq(tower, RobotType.TOWER.attackRadiusSquared);
			Arrays.sort(locations);
			towerRadii.add(locations);
		}
		if(towerRadii.size()==0) {
			return null;
		}
//		Collections.sort(towerRadii);
		// Map Locations sorted by row primarily
//		minX = 999999;
//		int maxX = -1;
//		minY = 999999;
//		int maxY = -1;
		// Naively say, if overlapping by two towers, there is no path
		int[] overlapped = new int[towerRadii.size()];
		for(int i = 0; i<towerRadii.size(); i++) {
			MapLocation[] locations = towerRadii.get(i);
//			for (MapLocation location : locations) {
//				System.out.println("MAPLOC" + location.x + "," + location.y);
//				
//				if(location.y < minY) {
//					minY = location.y;
//				} else if(location.y > maxY) {
//					maxY = location.y;
//				}
//				if(Math.abs(location.x) < minX) {
//					minX = location.x;
//				} else if(Math.abs(location.x) > maxX) {
//					maxX = location.x;
//				}
//			}
			for (int j = 0; j < towerRadii.size(); j++) {
				if (j != i) {
					MapLocation[] otherLocations = towerRadii.get(j);
					if (locations[0].x <= otherLocations[otherLocations.length-1].x &&
							otherLocations[0].x <= locations[locations.length-1].x && 
							locations[0].y <= otherLocations[otherLocations.length-1].y &&
							otherLocations[0].y <= locations[locations.length-1].y) {
						overlapped[i]++;
					}
				}
			}
			if(!rc.isPathable(RobotType.DRONE, new MapLocation(locations[0].x - 1, locations[0].y))) {
				System.out.println("Overlapped wall left");
				overlapped[i]++;
			}
			if(!rc.isPathable(RobotType.DRONE, new MapLocation(locations[locations.length-1].x + 1, locations[0].y))) {
				System.out.println("Overlapped wall right");
				overlapped[i]++;
			}
			if(!rc.isPathable(RobotType.DRONE, new MapLocation(locations[0].x, locations[0].y - 1))) {
				System.out.println("Overlapped wall up");
				overlapped[i]++;
			}
			if(!rc.isPathable(RobotType.DRONE, new MapLocation(locations[0].x, locations[locations.length-1].y + 1))) {
				System.out.println("Overlapped wall down");
				overlapped[i]++;
			}
			System.out.println("Tower " + i + " overlapped " + overlapped[i] + " " + towerLocations[i]);
		}
		// Determine if the range of the tower goes up against a wall
//		int xModifier = 1;
//		boolean wallLeft = false;
//		if(!rc.isPathable(RobotType.DRONE, new MapLocation(minX - 1, minY))) {
//			wallLeft = true;
//			xModifier++;
//		}
//		boolean wallRight = false;
//		if(!rc.isPathable(RobotType.DRONE, new MapLocation(maxX + 1, minY))) {
//			wallRight = true;
//			xModifier++;
//		}
//		int yModifier = 1;
//		boolean wallTop = false;
//		if(!rc.isPathable(RobotType.DRONE, new MapLocation(minX, minY - 1))) {
//			wallTop = true;
//			yModifier++;
//		}
//		boolean wallBottom = false;
//		if(!rc.isPathable(RobotType.DRONE, new MapLocation(minX, maxY + 1))) {
//			wallBottom = true;
//			yModifier++;
//		}
//		System.out.println("X size: " + (Math.abs(minX)-Math.abs(maxX)+xModifier) + " Y size: " + (Math.abs(maxY)-Math.abs(minY)+yModifier));
//		towerMap = new MapLocation[Math.abs(minX)-Math.abs(maxX)+xModifier][(Math.abs(maxY)-Math.abs(minY)+yModifier)];
//		if (towerMap[0][0] == null) {
//			System.out.println("NULLUPINTHIS");
//		} else {
//			System.out.println("NOTNULL!");
//		}
//		for(MapLocation location : towerRadii) {
//			int locationX = location.x - minX;
//			if (wallLeft) {
//				locationX++;
//			}
//			int locationY = location.y - minY;
//			if (wallTop) {
//				locationY++;
//			}
////			System.out.println("MIN:" + minX + ", " + minY);
////			System.out.println(location + " - " + locationX + ", " + locationY);
//			towerMap[locationX][locationY] = location; // -1 due to index starts at 0
//		}
		// Run Depth First Search to find holes in map
//		LinkedList<MapLocation> visited = new LinkedList<MapLocation>();
//		visited.add(new MapLocation(maxX - 1, maxY - 1));
//		breadthFirst(visited);
		
		
		System.out.println("BYTEEND on " + Clock.getRoundNum() + ": " + Clock.getBytecodeNum());
		return null;
	}

	static int bred = 0;
	
	private static void breadthFirst(LinkedList<MapLocation> visited) {
		LinkedList<MapLocation> nodes = neighbourNodes(visited.getLast());
		for (MapLocation node : nodes) {
			if (visited.contains(node)) {
				continue;
			}
			if (node.equals(rc.senseHQLocation())) {
				visited.add(node);
				storePath(visited);
				visited.removeLast();
				break;
			}
		}
		for (MapLocation node : nodes) {
			if (visited.contains(node) || node.equals(rc.senseHQLocation())) {
				continue;
			}
			visited.addLast(node);
			breadthFirst(visited);
			visited.removeLast();
		}
//		System.out.println("Exiting breadth " + bred++);
	}
	
	private static LinkedList<MapLocation> neighbourNodes(MapLocation current) {
		LinkedList<MapLocation> nodes = new LinkedList<MapLocation>();
		int currentX = current.x - minX;
		int currentY = current.y - minY;
//		boolean validRight = currentX + 1 < towerMap.length;
//		if (validRight) {
//			MapLocation right = towerMap[currentX + 1][currentY];
//			if(right != null) nodes.add(right);
//		}
		boolean validLeft = currentX - 1 > 0; 
		if (validLeft) {
			MapLocation left = towerMap[currentX - 1][currentY];
			if(left != null) nodes.add(left);
		}
//		boolean validUp = currentY + 1 < towerMap[0].length;
//		if (validUp) {
//			MapLocation up = towerMap[currentX][currentY + 1];
//			if(up != null) nodes.add(up);			
//		}
		boolean validDown = currentY - 1 > 0;
		if (validDown) {
			MapLocation down = towerMap[currentX][currentY - 1];
			if(down != null) nodes.add(down);
		}
//		if (validRight && validUp) {
//			MapLocation rightup = towerMap[currentX + 1][currentY + 1];
//			if(rightup != null) nodes.add(rightup);
//		}
//		if (validLeft && validUp) {
//			MapLocation leftup = towerMap[currentX - 1][currentY + 1];
//			if(leftup != null) nodes.add(leftup);
//		}
//		if (validRight && validDown) {
//			MapLocation rightdown = towerMap[currentX + 1][currentY - 1];
//			if(rightdown != null) nodes.add(rightdown);
//		}
//		if (validLeft && validDown) {
//			MapLocation leftdown = towerMap[currentX - 1][currentY - 1];
//			if(leftdown != null) nodes.add(leftdown);
//		}
		return nodes;
	}
	
	private static void storePath(LinkedList<MapLocation> viablePath) {
		System.out.println("Viable Path:" + viablePath.toString());
	}
	
	/**
	 * Shamelessly borrowed from swarm bot and examplefuncsplayer
	 */
	public static Direction[] getDirectionsToward(MapLocation dest) {
        Direction toDest = rc.getLocation().directionTo(dest);
        Direction[] dirs = {toDest,
	    		toDest.rotateLeft(), toDest.rotateRight(),
			toDest.rotateLeft().rotateLeft(), toDest.rotateRight().rotateRight()};

        return dirs;
    }
	
	static void attackSomething() throws GameActionException {
		RobotInfo[] enemies = rc.senseNearbyRobots(myRange, enemyTeam);
		if (enemies.length > 0) {
			rc.attackLocation(enemies[0].location);
		}
	}
	
	// This method will attempt to build in the given direction (or as close to it as possible)
	static boolean tryBuild(Direction d, RobotType type) throws GameActionException {
		int offsetIndex = 0;
		int[] offsets = {0,1,-1,2,-2,3,-3,4};
		int dirint = directionToInt(d);
		boolean blocked = false;
		while (offsetIndex < 8 && !rc.canMove(directions[(dirint+offsets[offsetIndex]+8)%8])) {
			offsetIndex++;
		}
		if (offsetIndex < 8 && rc.canBuild(directions[(dirint+offsets[offsetIndex]+8)%8], type)) {
			rc.build(directions[(dirint+offsets[offsetIndex]+8)%8], type);
			return true;
		}
		return false;
	}
	
	static int directionToInt(Direction d) {
		switch(d) {
			case NORTH:
				return 0;
			case NORTH_EAST:
				return 1;
			case EAST:
				return 2;
			case SOUTH_EAST:
				return 3;
			case SOUTH:
				return 4;
			case SOUTH_WEST:
				return 5;
			case WEST:
				return 6;
			case NORTH_WEST:
				return 7;
			default:
				return -1;
		}
	}
}
