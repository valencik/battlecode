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
        } else if (rc.getType() == RobotType.BEAVER) {
            myself = new Beaver(rc);
        } else if (rc.getType() == RobotType.BARRACKS) {
            myself = new Barracks(rc);
        } else if (rc.getType() == RobotType.SOLDIER) {
            myself = new Soldier(rc);
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
                }
            }
            return null;
        }

        public Direction getBuildDirection(RobotType type) {
            Direction[] dirs = getDirectionsToward(this.theirHQ);
            for (Direction d : dirs) {
                if (rc.canBuild(d, type)) {
                    return d;
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

    public static class HQ extends BaseBot {
        public HQ(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            int numBeavers = rc.readBroadcast(2);

            if (rc.isCoreReady() && rc.getTeamOre() > 100 && numBeavers < 10) {
                Direction newDir = getSpawnDirection(RobotType.BEAVER);
                if (newDir != null) {
                    rc.spawn(newDir, RobotType.BEAVER);
                    rc.broadcast(2, numBeavers + 1);
                }
            }
            MapLocation rallyPoint;
            if (Clock.getRoundNum() < 600) {
                rallyPoint = new MapLocation( (this.myHQ.x + this.theirHQ.x) / 2,
                                              (this.myHQ.y + this.theirHQ.y) / 2);
            }
            else {
                rallyPoint = this.theirHQ;
            }
            rc.broadcast(0, rallyPoint.x);
            rc.broadcast(1, rallyPoint.y);

            rc.yield();
        }
    }

    public static class Beaver extends BaseBot {
        public Beaver(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            if (rc.isCoreReady()) {
                if (rc.getTeamOre() < 500) {
                    //mine
                    if (rc.senseOre(rc.getLocation()) > 0) {
                        rc.mine();
                    }
                    else {
                        Direction newDir = getMoveDir(this.theirHQ);

                        if (newDir != null) {
                            rc.move(newDir);
                        }
                    }
                }
                else {
                    //build barracks
                    Direction newDir = getBuildDirection(RobotType.BARRACKS);
                    if (newDir != null) {
                        rc.build(newDir, RobotType.BARRACKS);
                    }
                }
            }

            rc.yield();
        }
    }

    public static class Barracks extends BaseBot {
        public Barracks(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            if (rc.isCoreReady() && rc.getTeamOre() > 200) {
                Direction newDir = getSpawnDirection(RobotType.SOLDIER);
                if (newDir != null) {
                    rc.spawn(newDir, RobotType.SOLDIER);
                }
            }

            rc.yield();
        }
    }

    public static class Soldier extends BaseBot {
        public Soldier(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            RobotInfo[] enemies = getEnemiesInAttackingRange();

            if (enemies.length > 0) {
                //attack!
                if (rc.isWeaponReady()) {
                    attackLeastHealthEnemy(enemies);
                }
            }
            else if (rc.isCoreReady()) {
                int rallyX = rc.readBroadcast(0);
                int rallyY = rc.readBroadcast(1);
                MapLocation rallyPoint = new MapLocation(rallyX, rallyY);

                Direction newDir = getMoveDir(rallyPoint);

                if (newDir != null) {
                    rc.move(newDir);
                }
            }
            rc.yield();
        }
    }

    public static class Tower extends BaseBot {
        public Tower(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            rc.yield();
        }
    }
}
