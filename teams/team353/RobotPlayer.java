package team353;

import battlecode.common.*;

import java.util.*;

public class RobotPlayer {
	
	//--Begin Parameters
	public static int roundToBuildAEROSPACELAB = 100;
	public static int roundToBuildBARRACKS = 500;
	public static int roundToBuildBASHER = 1500;
	public static int roundToBuildBEAVER = 100;
	public static int roundToBuildCOMMANDER = 100;
	public static int roundToBuildCOMPUTER = 100;
	public static int roundToBuildDRONE = 100;
	public static int roundToBuildHANDWASHSTATION = 100;
	public static int roundToBuildHELIPAD = 100;
	public static int roundToBuildLAUNCHER = 100;
	public static int roundToBuildMINER = 1;
	public static int roundToBuildMINERFACTORY = 100;
	public static int roundToBuildMISSILE = 100;
	public static int roundToBuildSOLDIER = 50;
	public static int roundToBuildSUPPLYDEPOT = 100;
	public static int roundToBuildTANK = 100;
	public static int roundToBuildTANKFACTORY = 100;
	public static int roundToBuildTECHNOLOGYINSTITUTE = 100;
	public static int roundToBuildTRAININGFIELD = 100;

	public static int desiredNumOfAEROSPACELAB = 8;
	public static int desiredNumOfBARRACKS = 8;
	public static int desiredNumOfBASHER = 10;
	public static int desiredNumOfBEAVER = 10;
	public static int desiredNumOfCOMMANDER = 8;
	public static int desiredNumOfCOMPUTER = 8;
	public static int desiredNumOfDRONE = 8;
	public static int desiredNumOfHANDWASHSTATION = 8;
	public static int desiredNumOfHELIPAD = 8;
	public static int desiredNumOfLAUNCHER = 8;
	public static int desiredNumOfMINER = 15;
	public static int desiredNumOfMINERFACTORY = 8;
	public static int desiredNumOfMISSILE = 8;
	public static int desiredNumOfSOLDIER = 20;
	public static int desiredNumOfSUPPLYDEPOT = 8;
	public static int desiredNumOfTANK = 8;
	public static int desiredNumOfTANKFACTORY = 8;
	public static int desiredNumOfTECHNOLOGYINSTITUTE = 8;
	public static int desiredNumOfTRAININGFIELD = 8;

	public static int roundToLaunchAttack = 1500;
	
	public static int currentOreGoal = 100;
	
	//Detect holes
	public static int TOWER_HOLES_BEGIN = 2000;
	public static int NUM_HANDWASH_STATIONS_BUILT = 0;
	
	// Defence
	public static int NUM_TOWER_PROTECTORS = 10;
	public static int NUM_HOLE_PROTECTORS = 2;
	public static int PROTECT_OTHERS_RANGE = 10;
	
	//--End Parameters
	

	public static void run(RobotController rc) {
        BaseBot myself;

        if (rc.getType() == RobotType.HQ) {
            myself = new HQ(rc);
        } else if (rc.getType() == RobotType.MINER) {
            myself = new Miner(rc);
        } else if (rc.getType() == RobotType.MINERFACTORY) {
            myself = new Minerfactory(rc);
        } else if (rc.getType() == RobotType.BEAVER) {
            myself = new Beaver(rc);
        } else if (rc.getType() == RobotType.BARRACKS) {
            myself = new Barracks(rc);
        } else if (rc.getType() == RobotType.SOLDIER) {
            myself = new Soldier(rc);
        } else if (rc.getType() == RobotType.BASHER) {
            myself = new Basher(rc);
        } else if (rc.getType() == RobotType.TOWER) {
            myself = new Tower(rc);
        } else {
            myself = new BaseBot(rc);
        }

        while (true) {
            try {
                myself.go();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

    public static class BaseBot {
        protected RobotController rc;
        protected MapLocation myHQ, theirHQ;
        protected Team myTeam, theirTeam;
        protected int myRange;
        protected RobotType myType;
        
        public BaseBot(RobotController rc) {
            this.rc = rc;
            this.myHQ = rc.senseHQLocation();
            this.theirHQ = rc.senseEnemyHQLocation();
            this.myTeam = rc.getTeam();
            this.theirTeam = this.myTeam.opponent();
            this.myType = rc.getType();
            this.myRange = myType.attackRadiusSquared;
        }

        public int getDistanceSquared(MapLocation A, MapLocation B){
        	return (A.x - B.x)*(A.x - B.x) + (A.y - B.y)*(A.y - B.y);
        }
        
        public int getDistanceSquared(MapLocation A){
			MapLocation B = rc.getLocation();
        	return (A.x - B.x)*(A.x - B.x) + (A.y - B.y)*(A.y - B.y);
        }

        public Direction[] getDirectionsToward(MapLocation dest) {
            Direction toDest = rc.getLocation().directionTo(dest);
            Direction[] dirs = {toDest,
		    		toDest.rotateLeft(), toDest.rotateRight(),
				toDest.rotateLeft().rotateLeft(), toDest.rotateRight().rotateRight()};

            return dirs;
        }
        
        public Direction[] getDirectionsAway(MapLocation dest) {
        	Direction toDest = rc.getLocation().directionTo(dest).opposite();
        	Direction[] dirs = {toDest,
        			toDest.rotateLeft().rotateLeft().rotateLeft(), toDest.rotateRight().rotateRight().rotateRight(),
        			toDest.rotateLeft(), toDest.rotateRight(),
        			toDest.rotateLeft().rotateLeft(), toDest.rotateRight().rotateRight()
        			};

        	return dirs;
        }

        public Direction getMoveDir(MapLocation dest) {
            Direction[] dirs = getDirectionsToward(dest);
            for (Direction d : dirs) {
                if (rc.canMove(d)) {
                    return d;
                }
            }
            return null;
        }

        public Direction getMoveDirAway(MapLocation dest) {
            Direction[] dirs = getDirectionsAway(dest);
            for (Direction d : dirs) {
                if (rc.canMove(d)) {
                    return d;
                }
            }
            return null;
        }

        public Direction getMoveDirRand(MapLocation dest) {
        	//TODO return a random direction
        	Direction[] dirs = getDirectionsToward(dest);
            for (Direction d : dirs) {
                if (rc.canMove(d)) {
                    return d;
                }
            }
            return null;
        }

        public Direction getMoveDirAwayRand(MapLocation dest) {
        	//TODO return a random direction
            Direction[] dirs = getDirectionsAway(dest);
            for (Direction d : dirs) {
                if (rc.canMove(d)) {
                    return d;
                }
            }
            return null;
        }

        public Direction getSpawnDirection(RobotType type) {
            Direction[] dirs = getDirectionsToward(this.theirHQ);
            for (Direction d : dirs) {
                if (rc.canSpawn(d, type)) {
                    return d;
                } else {
                	System.out.println("Could not find valid spawn location!");
                }
            }
            return null;
        }

        public Direction getBuildDirection(RobotType type) {
            Direction[] dirs = getDirectionsToward(this.theirHQ);
            for (Direction d : dirs) {
                if (rc.canBuild(d, type)) {
                    return d;
                } else {
                	System.out.println("Could not find valid build location!");
                }
            }
            return null;
        }

        public RobotInfo[] getAllies() {
            RobotInfo[] allies = rc.senseNearbyRobots(Integer.MAX_VALUE, myTeam);
            return allies;
        }

        public RobotInfo[] getEnemiesInAttackingRange() {
            RobotInfo[] enemies = rc.senseNearbyRobots(myRange, theirTeam);
            return enemies;
        }

        public void attackLeastHealthEnemy(RobotInfo[] enemies) throws GameActionException {
            if (enemies.length == 0) {
                return;
            }

            double minEnergon = Double.MAX_VALUE;
            MapLocation toAttack = null;
            for (RobotInfo info : enemies) {
                if (info.health < minEnergon) {
                    toAttack = info.location;
                    minEnergon = info.health;
                }
            }

            rc.attackLocation(toAttack);
        }
        
        public void attackLeastHealthEnemyInRange() throws GameActionException {
            RobotInfo[] enemies = getEnemiesInAttackingRange();

            if (enemies.length > 0) {
                //attack!
                if (rc.isWeaponReady()) {
                    attackLeastHealthEnemy(enemies);
                }
            }
        }
        
        public void moveToRallyPoint() throws GameActionException {
            if (rc.isCoreReady()) {
                int rallyX = rc.readBroadcast(0);
                int rallyY = rc.readBroadcast(1);
                MapLocation rallyPoint = new MapLocation(rallyX, rallyY);

                Direction newDir = getMoveDir(rallyPoint);

                if (newDir != null) {
                    rc.move(newDir);
                }
            }
    		
    	}
        
        public void moveRealGood() throws GameActionException {
        	int currentRound = Clock.getRoundNum();
			RobotType currentRobotType = rc.getType();
			
			switch(currentRobotType){
			case BASHER:
			    break;
			case BEAVER:
				Direction newDir = null;
                if (getDistanceSquared(this.myHQ) < 50){
                	newDir = getMoveDirAway(this.myHQ);	
                } else {
                	newDir = getMoveDirAway(this.theirHQ);
                }

				if (newDir != null) {
                    rc.move(newDir);
                }
			    break;
			case COMMANDER:
			    break;
			case COMPUTER:
			    break;
			case DRONE:
			    break;
			case LAUNCHER:
			    break;
			case MINER:
			    break;
			case MISSILE:
			    break;
			case SOLDIER:
			    break;
			case TANK:
			    break;
			default:
				//error
			} //Done RobotType specific actions.

        }

        public void transferSupplies() throws GameActionException {
        	RobotInfo[] nearbyAllies = rc.senseNearbyRobots(rc.getLocation(),GameConstants.SUPPLY_TRANSFER_RADIUS_SQUARED,rc.getTeam());
        	double lowestSupply = rc.getSupplyLevel();
        	double transferAmount = 0;
        	MapLocation suppliesToThisLocation = null;
        	for(RobotInfo ri:nearbyAllies){
        		if(ri.supplyLevel<lowestSupply){
        			lowestSupply = ri.supplyLevel;
        			transferAmount = (rc.getSupplyLevel()-ri.supplyLevel)/2;
        			suppliesToThisLocation = ri.location;
        		}
        	}
        	if(suppliesToThisLocation!=null){
        		rc.transferSupplies((int)transferAmount, suppliesToThisLocation);
        	}
        }
        
    	public void spawnUnit(RobotType type) throws GameActionException {
    		Direction randomDir = getSpawnDirection(type);
    		if(rc.isCoreReady()&& randomDir != null){
    			rc.spawn(randomDir, type);
    		}
    	}
    	
    	public void buildUnit(RobotType type) throws GameActionException {
    		Direction randomDir = getBuildDirection(type);
    		if(rc.isCoreReady()&& randomDir != null){
    			rc.build(randomDir, type);
    		}
    	}
    	
        public void beginningOfTurn() {
            if (rc.senseEnemyHQLocation() != null) {
                this.theirHQ = rc.senseEnemyHQLocation();
            }
        }

        public void endOfTurn() {
        }

        public void go() throws GameActionException {
            beginningOfTurn();
            execute();
            endOfTurn();
        }

        public void execute() throws GameActionException {
        	rc.yield();
        }
        
        public boolean defend() {

    		// A1, Protect Self
    		RobotInfo[] nearbyEnemies = getEnemiesInAttackRange();
    		if(nearbyEnemies != null && nearbyEnemies.length > 0) {
    			try {
    				if (rc.isWeaponReady()) {
    					attackLeastHealthEnemyInRange();
    				}
    			} catch (GameActionException e) {
    				e.printStackTrace();
    			}
    			return true;
    		}
    		// A2, Protect Nearby
    		RobotInfo[] engagedRobots = getRobotsEngagedInAttack();
    		if(engagedRobots != null && engagedRobots.length>0) { // Check broadcasts for enemies that are being attacked
    			// TODO: Calculate which enemy is attacking/within range/closest to teammate
    			// For now, just picking the first enemy
    			// Once our unit is in range of the other unit, A1 will takeover
    			for (RobotInfo robot : engagedRobots) {
    				if (robot.team == theirTeam) {
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
    			return true;
    		}
    		
    		if (Clock.getRoundNum() < roundToLaunchAttack) {
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
    				if (defendingHole) {
    					return true;
    				}
    			}
    			
    			if(myType != RobotType.BEAVER && myType != RobotType.MINER) {
    				// B2, Protect Towers
    				// TODO: Compute in advance at HQ, check Broadcasts for tower that needs units
    				MapLocation[] myTowers = rc.senseTowerLocations();
    				MapLocation closestTower = myTowers[0];
    				int closestDist = 999999;
    				for (MapLocation tower : myTowers) {
    					RobotInfo[] nearbyRobots = getTeammatesNearTower(tower);
    					if (nearbyRobots.length < NUM_TOWER_PROTECTORS) {
    						int dist = tower.distanceSquaredTo(theirHQ);
    						if (dist < closestDist) {
    							closestDist = dist;
    							closestTower = tower;
    						}				
    					}
    				}
    				// TODO: End compute
    				goToLocation(closestTower);
    				return true;
    			} else if(false) {
    				// B3, Protect Other Buildings
    				// TBD
    			}
    		}
    		
    		
    		// Default --> Get out of the way!/Go to rally point.
    		return false;
    	}
        
        
    	/**
    	 *  Simple helpers, more logic for these later
    	 */
    	public RobotInfo[] getEnemiesInAttackRange() {
    		return rc.senseNearbyRobots(myRange, theirTeam);
    	}
    	
    	public void goToLocation(MapLocation location) {
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
    	
    	public RobotInfo[] getRobotsEngagedInAttack() {
    		RobotInfo[] nearbyRobots = rc.senseNearbyRobots(PROTECT_OTHERS_RANGE);
    		boolean hasEnemy = false;
    		boolean hasFriendly = false;
    		for (RobotInfo robot : nearbyRobots) {
    			if(robot.team == theirTeam) {
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

    	public RobotInfo[] getTeammatesNearTower(MapLocation towerLocation) {
    		return rc.senseNearbyRobots(towerLocation, RobotType.TOWER.attackRadiusSquared, myTeam);
    	}
    	
    	// Find out if there are any holes between a teams tower and their HQ
    	public MapLocation[] computeHoles() {
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
//    			System.out.println("Tower " + i + " overlapped " + overlapped[i] + " " + towerLocations[i]);
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
    	
    }

    //----- Per RoboType code below -----
    
    //HQ
    public static class HQ extends BaseBot {
        public HQ(RobotController rc) {
            super(rc);
            computeHoles();
        }

        public void execute() throws GameActionException {
            int numBeavers = rc.readBroadcast(2);

            if (rc.isCoreReady() && rc.getTeamOre() > 100 && numBeavers < desiredNumOfBEAVER) {
                spawnUnit(RobotType.BEAVER);
            	rc.broadcast(2, numBeavers + 1);
            }
            
            //Broadcast rallyPoint
            MapLocation rallyPoint;
            if (Clock.getRoundNum() < roundToLaunchAttack) {
                rallyPoint = new MapLocation( (this.myHQ.x + this.theirHQ.x) / 2,
                                              (this.myHQ.y + this.theirHQ.y) / 2);
            }
            else {
                rallyPoint = this.theirHQ; //attack!
            }
            rc.broadcast(0, rallyPoint.x);
            rc.broadcast(1, rallyPoint.y);
            attackLeastHealthEnemyInRange();
            transferSupplies();
            rc.yield();
        }
    }

    //BEAVER
    public static class Beaver extends BaseBot {
        public Beaver(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
        	
        	rc.setIndicatorString(1, "dist:" + getDistanceSquared(this.myHQ));
            if (rc.isCoreReady()) {
                if (Clock.getRoundNum() > roundToBuildMINERFACTORY && rc.getTeamOre() > 500) {
                    buildUnit(RobotType.MINERFACTORY);
                } else if (Clock.getRoundNum() > roundToBuildBARRACKS && rc.getTeamOre() > 300) {
                    buildUnit(RobotType.BARRACKS);
                } else {
                    //mine
                    if (rc.senseOre(rc.getLocation()) > 1) {
                        rc.mine();
                    }
                    else {
                    	moveRealGood();
                    }
                }
            }
            transferSupplies();
//            attackLeastHealthEnemyInRange();
            defend();
            rc.yield();
        }
    }

    //MINERFACTORY
    public static class Minerfactory extends BaseBot {
        public Minerfactory(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            int numMiners = rc.readBroadcast(3);
           
            if (rc.isCoreReady() && rc.getTeamOre() > 200 && numMiners < desiredNumOfMINER) {
                spawnUnit(RobotType.MINER);
            	rc.broadcast(3, numMiners + 1);
            }

            rc.yield();
        }
    }
    
    //MINER
    public static class Miner extends BaseBot {
    	public Miner(RobotController rc) {
    		super(rc);
    	}

    	public void execute() throws GameActionException {
    		if (rc.isCoreReady()) {
    			//mine
    			if (rc.senseOre(rc.getLocation()) > 0) {
    				rc.mine();
    			}
    			else {
    				Direction newDir = getMoveDirAway(this.theirHQ);

    				if (newDir != null) {
    					rc.move(newDir);
    				}
    			}
    			defend();
    			transferSupplies();
    			rc.yield();
    		}
    	}
    }
    
    //BARRACKS
    public static class Barracks extends BaseBot {
        public Barracks(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            if (rc.isCoreReady() && rc.getTeamOre() > 200){
            	if (Clock.getRoundNum() > roundToBuildSOLDIER) {
                    Direction newDir = getSpawnDirection(RobotType.SOLDIER);
                    if (newDir != null) {
                        rc.spawn(newDir, RobotType.SOLDIER);
                    }
            	} else if (Clock.getRoundNum() > roundToBuildBASHER) {
                    Direction newDir = getSpawnDirection(RobotType.BASHER);
                    if (newDir != null) {
                        rc.spawn(newDir, RobotType.BASHER);
                    }
            	}

            }

            rc.yield();
        }
    }

    //SOLDIER
    public static class Soldier extends BaseBot {
        public Soldier(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
//        	attackLeastHealthEnemyInRange();
        	if (!defend()) {
        		moveToRallyPoint();
        	}
        	transferSupplies();
            rc.yield();
        }
    }

    //BASHER
    public static class Basher extends BaseBot {
        public Basher(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
        	attackLeastHealthEnemyInRange();
        	if (!defend()) {
        		moveToRallyPoint();
        	}
        	transferSupplies();
            rc.yield();
        }
    }

    //TOWER
    public static class Tower extends BaseBot {
        public Tower(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            attackLeastHealthEnemyInRange();
        	rc.yield();
        }
    }

	
}
