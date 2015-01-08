package smuHuskies;

import battlecode.common.*;

public class RobotPlayer {

	static RobotController rc;
	static Team myTeam;
	static Team enemyTeam;
	static MapLocation enemyHQ;
	static int myRange;
	
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
