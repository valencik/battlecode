package smuHuskies;

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

	public static int roundToLaunchAttack = 600;
	
	public static int currentOreGoal = 100;
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

        public BaseBot(RobotController rc) {
            this.rc = rc;
            this.myHQ = rc.senseHQLocation();
            this.theirHQ = rc.senseEnemyHQLocation();
            this.myTeam = rc.getTeam();
            this.theirTeam = this.myTeam.opponent();
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
            RobotInfo[] enemies = rc.senseNearbyRobots(RobotType.SOLDIER.attackRadiusSquared, theirTeam);
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
    }

    //----- Per RoboType code below -----
    
    //HQ
    public static class HQ extends BaseBot {
        public HQ(RobotController rc) {
            super(rc);
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
            attackLeastHealthEnemyInRange();
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
    			attackLeastHealthEnemyInRange();
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
        	attackLeastHealthEnemyInRange();
        	moveToRallyPoint();
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
        	moveToRallyPoint();
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
