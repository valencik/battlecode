package smuHuskies;

import java.util.Arrays;

import battlecode.common.*;

public class RobotPlayer {

	static RobotController rc;
	static Team myTeam;
	static Team enemyTeam;
	static MapLocation enemyHQ;
	static int myRange;
	
	//Detect holes
	static int TOWER_HOLES_BEGIN = 2000;
	static int NUM_HANDWASH_STATIONS_BUILT = 0;
	
	// See shamelessly borrowed functions at bottom of file
	static Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};

	
	// To be Data Mined/Tested
	
	// Defence
	static int NUM_TOWER_PROTECTORS = 10;
	static int NUM_HOLE_PROTECTORS = 2;
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
				int numHandWashStations = rc.readBroadcast(NUM_HANDWASH_STATIONS_BUILT);
				if (numHandWashStations < 1 && rc.isCoreReady()) {
					boolean successful = tryBuild(Direction.NORTH, RobotType.HANDWASHSTATION);
					if (successful) {
						numHandWashStations++;
						rc.broadcast(NUM_HANDWASH_STATIONS_BUILT, numHandWashStations);
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
			// Once our unit is in range of the other unit, A1 will takeover
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
		}
		int towerHoleX = -1;
		try {
			towerHoleX = rc.readBroadcast(TOWER_HOLES_BEGIN);
		} catch (GameActionException e1) {
			e1.printStackTrace();
		}
		boolean defendingHole = false;
		if(towerHoleX != -1) {
			// B1, Protect Holes From Other Towers
			// Holes to be computed once, shared in broadcast
			// Go to first hole with < threshold units there, or, pick one at random
			int towerHolesIndex = TOWER_HOLES_BEGIN;
			int towerHoleY;
			do {
				try {
					towerHoleX = rc.readBroadcast(towerHolesIndex);
					towerHolesIndex++;
					towerHoleY = rc.readBroadcast(towerHolesIndex);
					towerHolesIndex++;
					if (towerHoleX != -1) {
						MapLocation holeLocation = new MapLocation(towerHoleX, towerHoleY);
						RobotInfo[] nearbyTeammates = rc.senseNearbyRobots(holeLocation, 5, myTeam);
						if (nearbyTeammates.length < NUM_HOLE_PROTECTORS) {
							defendingHole = true;
							goToLocation(holeLocation);
							towerHoleX = -1;
						}
					}
				} catch (GameActionException e) {
					e.printStackTrace();
				}
			} while(towerHoleX != -1);
		} else if(!defendingHole) {
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
			goToLocation(closestTower);
		} else if(false) {
			// B3, Protect Other Buildings
			// TBD
		}
		
		// Default --> Get out of the way!/Go to rally point.
	}
	
	/**
	 *  Simple helpers, more logic for these later
	 */
	public static RobotInfo[] getEnemiesInAttackRange() {
		return rc.senseNearbyRobots(myRange, enemyTeam);
	}
	
	public static void goToLocation(MapLocation location) {
		Direction[] directions = getDirectionsToward(location);
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
		MapLocation[][] towerRadii = new MapLocation[towerLocations.length][];
		for(int i = 0; i < towerLocations.length; i++) {
			// Get all map locations that a tower can attack
			MapLocation[] locations = MapLocation.getAllMapLocationsWithinRadiusSq(towerLocations[i], RobotType.TOWER.attackRadiusSquared);
			Arrays.sort(locations);
			towerRadii[i] = locations;
		}
		if(towerRadii.length == 0 || towerRadii[0] == null) {
			return null;
		}
		// Naively say, if overlapping by two towers, there is no path
		int[] overlapped = new int[towerRadii.length];
		int holesBroadcastIndex = TOWER_HOLES_BEGIN;
		for(int i = 0; i<towerRadii.length; i++) {
			MapLocation[] locations = towerRadii[i];
			boolean coveredLeft = false;
			boolean coveredRight = false;
			boolean coveredTop = false;
			boolean coveredBottom = false;
			for (int j = 0; j < towerRadii.length; j++) {
				if (j != i) {
					MapLocation[] otherLocations = towerRadii[j];
					if (locations[0].x <= otherLocations[otherLocations.length-1].x &&
							otherLocations[0].x <= locations[locations.length-1].x && 
							locations[0].y <= otherLocations[otherLocations.length-1].y &&
							otherLocations[0].y <= locations[locations.length-1].y) {
						overlapped[i]++;
						Direction otherTowerDir = towerLocations[i].directionTo(towerLocations[j]);
						if (otherTowerDir.equals(Direction.EAST) || otherTowerDir.equals(Direction.NORTH_EAST) || otherTowerDir.equals(Direction.SOUTH_EAST)) {
							coveredLeft = true;
						}
						if (otherTowerDir.equals(Direction.WEST) || otherTowerDir.equals(Direction.NORTH_WEST) || otherTowerDir.equals(Direction.SOUTH_WEST)) {
							coveredRight = true;
						}
						if (otherTowerDir.equals(Direction.NORTH) || otherTowerDir.equals(Direction.NORTH_EAST) || otherTowerDir.equals(Direction.NORTH_WEST)) {
							coveredTop = true;
						}
						if (otherTowerDir.equals(Direction.SOUTH) || otherTowerDir.equals(Direction.SOUTH_EAST) || otherTowerDir.equals(Direction.SOUTH_WEST)) {
							coveredBottom = true;
						}
					}
				}
			}
			// TODO: Account for walls
			if(overlapped[i]<2 && !rc.isPathable(RobotType.BEAVER, new MapLocation(locations[0].x - 1, locations[0].y))) {
				overlapped[i]++;
				coveredLeft = true;
			}
			if(overlapped[i]<2 && !rc.isPathable(RobotType.BEAVER, new MapLocation(locations[locations.length-1].x + 1, locations[0].y))) {
				overlapped[i]++;
				coveredRight = true;
			}
			if(overlapped[i]<2 && !rc.isPathable(RobotType.BEAVER, new MapLocation(locations[0].x, locations[0].y - 1))) {
				overlapped[i]++;
				coveredTop = true;
			}
			if(overlapped[i]<2 && !rc.isPathable(RobotType.BEAVER, new MapLocation(locations[0].x, locations[locations.length-1].y + 1))) {
				overlapped[i]++;
				coveredBottom = true;
			}
//			System.out.println("Tower " + i + " overlapped " + overlapped[i] + " " + towerLocations[i]);
			if (overlapped[i] < 2) {
				try {
					int towerAttackRadius = (int) Math.sqrt(RobotType.TOWER.attackRadiusSquared) + 1;
					if (!coveredLeft) {
						System.out.println("Tower " + towerLocations[i] + " Not covered left");
						rc.broadcast(holesBroadcastIndex, towerLocations[i].x - towerAttackRadius);
						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y);
						holesBroadcastIndex+=2;
					}
					if (!coveredRight) {
						System.out.println("Tower " + towerLocations[i] + " Not covered right");
						rc.broadcast(holesBroadcastIndex, towerLocations[i].x + towerAttackRadius);
						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y);
						holesBroadcastIndex+=2;
					}
					if (!coveredTop) {
						System.out.println("Tower " + towerLocations[i] + " Not covered top");
						rc.broadcast(holesBroadcastIndex, towerLocations[i].x);
						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y - towerAttackRadius);
						holesBroadcastIndex+=2;
					}
					if (!coveredBottom) {
						System.out.println("Tower " + towerLocations[i] + " Not covered bottom");
						rc.broadcast(holesBroadcastIndex, towerLocations[i].x);
						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y + towerAttackRadius);
						holesBroadcastIndex+=2;
					}
				} catch (GameActionException e) {
					e.printStackTrace();
				}
			}
		}
		// Signify end of holes
		try {
			rc.broadcast(holesBroadcastIndex, -1);
		} catch (GameActionException e) {
			e.printStackTrace();
		}
		
		System.out.println("BYTEEND on " + Clock.getRoundNum() + ": " + Clock.getBytecodeNum());
		return null;
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
