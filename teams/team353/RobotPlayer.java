package team353;

import battlecode.common.*;

import java.util.*;

public class RobotPlayer {

    //--Begin Parameters
    public static int roundToBuildAEROSPACELAB = 100;
    public static int roundToBuildBARRACKS = 500;
    public static int roundToBuildBASHER = 1500;
    public static int roundToBuildBEAVER = 0;
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
    public static int desiredNumOfBASHER = 50;
    public static int desiredNumOfBEAVER = 10;
    public static int desiredNumOfCOMMANDER = 8;
    public static int desiredNumOfCOMPUTER = 8;
    public static int desiredNumOfDRONE = 8;
    public static int desiredNumOfHANDWASHSTATION = 8;
    public static int desiredNumOfHELIPAD = 8;
    public static int desiredNumOfLAUNCHER = 8;
    public static int desiredNumOfMINER = 30;
    public static int desiredNumOfMINERFACTORY = 8;
    public static int desiredNumOfMISSILE = 8;
    public static int desiredNumOfSOLDIER = 200;
    public static int desiredNumOfSUPPLYDEPOT = 8;
    public static int desiredNumOfTANK = 8;
    public static int desiredNumOfTANKFACTORY = 8;
    public static int desiredNumOfTECHNOLOGYINSTITUTE = 8;
    public static int desiredNumOfTRAININGFIELD = 8;

    public static int numAEROSPACELAB;
    public static int numBARRACKS;
    public static int numBASHER;
    public static int numBEAVER;
    public static int numCOMMANDER;
    public static int numCOMPUTER;
    public static int numDRONE;
    public static int numHANDWASHSTATION;
    public static int numHELIPAD;
    public static int numLAUNCHER;
    public static int numMINER;
    public static int numMINERFACTORY;
    public static int numMISSILE;
    public static int numSOLDIER;
    public static int numSUPPLYDEPOT;
    public static int numTANK;
    public static int numTANKFACTORY;
    public static int numTECHNOLOGYINSTITUTE;
    public static int numTRAININGFIELD;

    public static final int freqNumAEROSPACELAB = 300;
    public static final int freqNumBARRACKS = 301;
    public static final int freqNumBASHER = 302;
    public static final int freqNumBEAVER = 303;
    public static final int freqNumCOMMANDER = 304;
    public static final int freqNumCOMPUTER = 305;
    public static final int freqNumDRONE = 306;
    public static final int freqNumHANDWASHSTATION = 307;
    public static final int freqNumHELIPAD = 308;
    public static final int freqNumLAUNCHER = 309;
    public static final int freqNumMINER = 310;
    public static final int freqNumMINERFACTORY = 311;
    public static final int freqNumMISSILE = 312;
    public static final int freqNumSOLDIER = 313;
    public static final int freqNumSUPPLYDEPOT = 314;
    public static final int freqNumTANK = 315;
    public static final int freqNumTANKFACTORY = 316;
    public static final int freqNumTECHNOLOGYINSTITUTE = 317;
    public static final int freqNumTRAININGFIELD = 318;

    public static int roundToLaunchAttack = 1600;
    public static int roundToFormSupplyConvoy = 50; // roundToBuildSOLDIERS;

    public static int currentOreGoal = 100;
	
	//Detect holes
	public static int TOWER_HOLES_BEGIN = 2000;
	public static int NUM_HANDWASH_STATIONS_BUILT = 0;
	
	// Defence
	public static int NUM_TOWER_PROTECTORS = 4;
	public static int NUM_HOLE_PROTECTORS = 3;
	public static int PROTECT_OTHERS_RANGE = 10;
	public static int DISTANCE_TO_START_PROTECTING_SQUARED = 200;
	
	// Idle States
	public static MapLocation defenseRallyPoint;
	public static int PROTECT_HOLE = 1;
	public static int PROTECT_TOWER = 2;
	
	// Supply
	public static int NUM_ROUNDS_TO_KEEP_SUPPLIED = 20;
	
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
        static Random rand;
        
        public BaseBot(RobotController rc) {
            this.rc = rc;
            this.myHQ = rc.senseHQLocation();
            this.theirHQ = rc.senseEnemyHQLocation();
            this.myTeam = rc.getTeam();
            this.theirTeam = this.myTeam.opponent();
            this.myType = rc.getType();
            this.myRange = myType.attackRadiusSquared;
            rand = new Random(rc.getID());
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
                    toDest.rotateLeft(), toDest.rotateRight(),
                toDest.rotateLeft().rotateLeft(), toDest.rotateRight().rotateRight()};

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

        //Will return a single direction for spawning. (Uses getDirectionsToward())
        public Direction getSpawnDir(RobotType type) {
            Direction[] dirs = getDirectionsToward(this.theirHQ);
            for (Direction d : dirs) {
                if (rc.canSpawn(d, type)) {
                    return d;
                }
            }
            dirs = getDirectionsToward(this.myHQ);
            for (Direction d : dirs) {
                if (rc.canSpawn(d, type)) {
                    return d;
                } else {
                    System.out.println("Could not find valid spawn location!");
                }
            }
            return null;
        }

        //Will return a single direction for building. (Uses getDirectionsToward())
        public Direction getBuildDir(RobotType type) {
            Direction[] dirs = getDirectionsToward(this.theirHQ);
            for (Direction d : dirs) {
                if (rc.canBuild(d, type)) {
                    return d;
                }
            }
            dirs = getDirectionsToward(this.myHQ);
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

        //TODO has defend() replaced this?
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

        //TODO has defend() replaced this?
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
        
        //Returns the current rally point MapLocation
        public MapLocation getRallyPoint() throws GameActionException {            
            int rallyX = rc.readBroadcast(0);
            int rallyY = rc.readBroadcast(1);
            MapLocation rallyPoint = new MapLocation(rallyX, rallyY);
            return rallyPoint;
        }

        //Moves a random safe direction from input array
        public void moveOptimally(Direction[] optimalDirections) throws GameActionException {
            //TODO check safety
            if (optimalDirections != null) {
                boolean lookingForDirection = true;
                while(lookingForDirection){
                    MapLocation[] enemyTowers = rc.senseEnemyTowerLocations();
                    Direction randomDirection = optimalDirections[(int) (rand.nextDouble()*optimalDirections.length)];
                    MapLocation tileInFront = rc.getLocation().add(randomDirection);

                    //check that the direction in front is not a tile that can be attacked by the enemy towers
                    boolean tileInFrontSafe = true;
                    for(MapLocation m: enemyTowers){
                        if(m.distanceSquaredTo(tileInFront)<=RobotType.TOWER.attackRadiusSquared){
                            tileInFrontSafe = false;
                            break;
                        }
                    }

                    //check that we are not facing off the edge of the map
                    if(rc.senseTerrainTile(tileInFront)!=TerrainTile.NORMAL||!tileInFrontSafe){
                        randomDirection = randomDirection.rotateLeft();
                    }else{
                        //try to move in the randomDirection direction
                        if(rc.isCoreReady()&&rc.canMove(randomDirection)){
                            rc.move(randomDirection);
                            lookingForDirection = false;
                            return;
                        }
                    }
                }
                
                System.out.println("No suitable direction found!");
            }

        }
        
        //Gets optimal directions and then calls moveOptimally() with those directions.
        public void moveOptimally() throws GameActionException {
            Direction[] optimalDirections = getOptimalDirections();
            if (optimalDirections != null) {
                moveOptimally(optimalDirections);
            }
        }
        
        //TODO Finish
        public Direction[] getOptimalDirections() throws GameActionException {
            //  The switch statement should result in an array of directions that make sense
            //for the RobotType. Safety is considered in moveOptimally()

            int currentRound = Clock.getRoundNum();
            RobotType currentRobotType = rc.getType();
            Direction[] optimalDirections = null;

            switch(currentRobotType){
            case BASHER:
                break;
            case BEAVER:
                if (getDistanceSquared(this.myHQ) < 50){
                    optimalDirections = getDirectionsAway(this.myHQ);    
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
                if (getDistanceSquared(this.myHQ) < 50){
                    optimalDirections = getDirectionsToward(this.myHQ);    
                }
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
            return optimalDirections;

        }

        //Spawns unit based on calling type. Performs all checks.
        public void spawnUnit() throws GameActionException {
            if (rc.isCoreReady()){
                RobotType myType = rc.getType();
                RobotType spawnType;
                int round = Clock.getRoundNum();
                double ore = rc.getTeamOre();

                switch(myType){

                case BARRACKS:                    
                    if (round > roundToBuildSOLDIER && rc.readBroadcast(freqNumSOLDIER) < desiredNumOfSOLDIER && ore > 100){
                        spawnType = RobotType.SOLDIER;
                        break;
                    } else if (round > roundToBuildBASHER && rc.readBroadcast(freqNumBASHER) < desiredNumOfBASHER && ore > 100){
                        spawnType = RobotType.BASHER;
                        break;
                    }
                    return;
                case HQ:
                    if (round > roundToBuildBEAVER && rc.readBroadcast(freqNumBEAVER) < desiredNumOfBEAVER && ore > 100){
                        spawnType = RobotType.BEAVER;
                        break;
                    }
                    return;
                case HELIPAD:
                    if (round > roundToBuildDRONE && rc.readBroadcast(freqNumDRONE) < desiredNumOfDRONE && ore > 100){
                        spawnType = RobotType.DRONE;
                        break;
                    }
                    return;
                case AEROSPACELAB:
                    if (round > roundToBuildLAUNCHER && rc.readBroadcast(freqNumLAUNCHER) < desiredNumOfLAUNCHER && ore > 100){
                        spawnType = RobotType.LAUNCHER;
                        break;
                    }
                    return;
                case MINERFACTORY:
                    if (round > roundToBuildMINER && rc.readBroadcast(freqNumMINER) < desiredNumOfMINER && ore > 100){
                        spawnType = RobotType.MINER;
                        break;
                    }
                    return;
                case TANKFACTORY:
                    if (round > roundToBuildTANK && rc.readBroadcast(freqNumTANK) < desiredNumOfTANK && ore > 100){
                        spawnType = RobotType.TANK;
                        break;
                    }
                    return;
                case TECHNOLOGYINSTITUTE:
                    if (round > roundToBuildCOMPUTER && rc.readBroadcast(freqNumCOMPUTER) < desiredNumOfCOMPUTER && ore > 100){
                        spawnType = RobotType.COMPUTER;
                        break;
                    }
                    return;
                case TRAININGFIELD:
                    if (round > roundToBuildCOMMANDER && rc.readBroadcast(freqNumCOMMANDER) < desiredNumOfCOMMANDER && ore > 100){
                        spawnType = RobotType.COMMANDER;
                        break;
                    }
                    return;

                default:
                    System.out.println("ERRROR in spawnUnit()!");
                    return;
                }

                //Get a direction and then actually spawn the unit.
                Direction randomDir = getSpawnDir(spawnType);
                if(rc.isCoreReady()&& randomDir != null){
                    rc.spawn(randomDir, spawnType);
                    incrementCount(spawnType); 
                }
            } //isCoreReady
        }

        //TODO copy the work done on spawnUnit()
        public void buildUnit(RobotType type) throws GameActionException {
            Direction randomDir = getBuildDir(type);
            if(rc.isCoreReady()&& randomDir != null){
                rc.build(randomDir, type);
            }
        }

        //TODO find a way to deal with deaths.
        public void incrementCount(RobotType type) throws GameActionException {
            switch(type){
            case AEROSPACELAB:
                rc.broadcast(freqNumAEROSPACELAB, rc.readBroadcast(freqNumAEROSPACELAB)+1);
                break;
            case BARRACKS:
                rc.broadcast(freqNumBARRACKS, rc.readBroadcast(freqNumBARRACKS)+1);
                break;
            case BASHER:
                rc.broadcast(freqNumBASHER, rc.readBroadcast(freqNumBASHER)+1);
                break;
            case BEAVER:
                rc.broadcast(freqNumBEAVER, rc.readBroadcast(freqNumBEAVER)+1);
                break;
            case COMMANDER:
                rc.broadcast(freqNumCOMMANDER, rc.readBroadcast(freqNumCOMMANDER)+1);
                break;
            case COMPUTER:
                rc.broadcast(freqNumCOMPUTER, rc.readBroadcast(freqNumCOMPUTER)+1);
                break;
            case DRONE:
                rc.broadcast(freqNumDRONE, rc.readBroadcast(freqNumDRONE)+1);
                break;
            case HANDWASHSTATION:
                rc.broadcast(freqNumHANDWASHSTATION, rc.readBroadcast(freqNumHANDWASHSTATION)+1);
                break;
            case HELIPAD:
                rc.broadcast(freqNumHELIPAD, rc.readBroadcast(freqNumHELIPAD)+1);
                break;
            case LAUNCHER:
                rc.broadcast(freqNumLAUNCHER, rc.readBroadcast(freqNumLAUNCHER)+1);
                break;
            case MINER:
                rc.broadcast(freqNumMINER, rc.readBroadcast(freqNumMINER)+1);
                break;
            case MINERFACTORY:
                rc.broadcast(freqNumMINERFACTORY, rc.readBroadcast(freqNumMINERFACTORY)+1);
                break;
            case MISSILE:
                rc.broadcast(freqNumMISSILE, rc.readBroadcast(freqNumMISSILE)+1);
                break;
            case SOLDIER:
                rc.broadcast(freqNumSOLDIER, rc.readBroadcast(freqNumSOLDIER)+1);
                break;
            case SUPPLYDEPOT:
                rc.broadcast(freqNumSUPPLYDEPOT, rc.readBroadcast(freqNumSUPPLYDEPOT)+1);
                break;
            case TANK:
                rc.broadcast(freqNumTANK, rc.readBroadcast(freqNumTANK)+1);
                break;
            case TANKFACTORY:
                rc.broadcast(freqNumTANKFACTORY, rc.readBroadcast(freqNumTANKFACTORY)+1);
                break;
            case TECHNOLOGYINSTITUTE:
                rc.broadcast(freqNumTECHNOLOGYINSTITUTE, rc.readBroadcast(freqNumTECHNOLOGYINSTITUTE)+1);
                break;
            case TRAININGFIELD:
                rc.broadcast(freqNumTRAININGFIELD, rc.readBroadcast(freqNumTRAININGFIELD)+1);
                break;
            default:
                System.out.println("ERRROR!");
                return;
            }
        }

        public void transferSupplies() throws GameActionException {
        	double lowestSupply = rc.getSupplyLevel();
        	if (lowestSupply == 0) {
        		return;
        	}
        	int roundStart = Clock.getRoundNum();
        	final MapLocation myLocation = rc.getLocation();
        	RobotInfo[] nearbyAllies = rc.senseNearbyRobots(myLocation,GameConstants.SUPPLY_TRANSFER_RADIUS_SQUARED,rc.getTeam());
        	double transferAmount = 0;
        	MapLocation suppliesToThisLocation = null;
        	for(RobotInfo ri:nearbyAllies){
        			if (ri.supplyLevel < lowestSupply) {
        					lowestSupply = ri.supplyLevel;
    						transferAmount = (rc.getSupplyLevel()-ri.supplyLevel)/2;        				
        					suppliesToThisLocation = ri.location;
        		}
        	}
        	if(suppliesToThisLocation!=null){
//        		rc.setIndicatorString(1, "CurrRount:" + Clock.getRoundNum() + " Started Round: " + roundStart);
        		if (roundStart == Clock.getRoundNum() && transferAmount > 0) {
//        			rc.setIndicatorString(0, "NumAllies: " + nearbyAllies.length + " Byte: " + Clock.getBytecodeNum());
//        			rc.setIndicatorString(2, "Supplying " + (int)transferAmount + " to " + suppliesToThisLocation);
    				rc.transferSupplies((int)transferAmount, suppliesToThisLocation);
        		}
        	}
        }
        
        // true, I'm in the convoy or going to be
        // false, no place in the convoy for me
        public boolean formSupplyConvoy() {
        	RobotInfo minerAtEdge = getUnitAtEdgeOfSupplyRangeOf(RobotType.MINER, myHQ);
        	int radius = 2; // sqrt(15), the radius for supply
        	if (minerAtEdge != null && minerAtEdge.ID == rc.getID()) {
        		return true;
        	} else if (minerAtEdge == null){
            	goToLocation(myHQ.add(myHQ.directionTo(theirHQ), radius));
        		return true;
        	}
        	RobotInfo previousMiner = null;
        	try {
	            while (minerAtEdge != null && minerAtEdge.location.distanceSquaredTo(getRallyPoint()) > 4) {
	            	previousMiner = minerAtEdge;
	            	minerAtEdge = getUnitAtEdgeOfSupplyRangeOf(RobotType.MINER, minerAtEdge.location);
	            	if (minerAtEdge != null && minerAtEdge.ID == rc.getID()) {
	            		return true;
	            	} else if (minerAtEdge == null) {
	            		goToLocation(previousMiner.location.add(previousMiner.location.directionTo(theirHQ), radius));
	            		return true;
	            	}
	            }
            } catch (GameActionException e) {
	            e.printStackTrace();
            }
        	return false;
        }
        
        public RobotInfo getUnitAtEdgeOfSupplyRangeOf(RobotType unitType, MapLocation startLocation) {
        	MapLocation locationInChain =  startLocation.add(myHQ.directionTo(theirHQ), 2);
        	if (rc.canSenseLocation(locationInChain)) {
        		try {
	                return rc.senseRobotAtLocation(locationInChain);
                } catch (GameActionException e) {
	                e.printStackTrace();
                }
        	}
        	return null;
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
        
        public void supplyAndYield() throws GameActionException {
        	transferSupplies();
        	rc.yield();
        }
        
        public boolean defend() {
//        	defenseRallyPoint = null;
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
    					goToLocation(robot.location);
    				}
    			}
    			return true;
    		}
    		
    		if (Clock.getRoundNum() < roundToLaunchAttack 
//    				&& idleDefenseStatus != PROTECT_TOWER
//    				&& idleDefenseStatus != PROTECT_HOLE
    				) {
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
    							if (nearbyTeammates.length < NUM_HOLE_PROTECTORS && rc.getLocation().distanceSquaredTo(holeLocation) <= DISTANCE_TO_START_PROTECTING_SQUARED) {
    								defendingHole = true;
    								towerHoleX = -1;
									goToLocation(holeLocation);    									
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
    				MapLocation closestTower = null;
                    try {
                        closestTower = getRallyPoint();
                    } catch (GameActionException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
    				int closestDist = 999999;
    				for (MapLocation tower : myTowers) {
    					RobotInfo[] nearbyRobots = getTeammatesNearTower(tower);
    					if (nearbyRobots.length < NUM_TOWER_PROTECTORS && rc.getLocation().distanceSquaredTo(tower) <= DISTANCE_TO_START_PROTECTING_SQUARED) { //tower underprotected
    						int dist = tower.distanceSquaredTo(theirHQ);
    						if (dist < closestDist) {
    							closestDist = dist;
    							closestTower = tower;
    						}				
    					}
    				}
    				// TODO: End compute
    				goToLocation(closestTower);
    				//System.out.println("B2: Protect Towers.");
    				return true;
    			}
				// B3, Protect Other Buildings
				// TBD
    		}
    		return false;
    	}
        
        
    	/**
    	 *  Simple helpers, more logic for these later
    	 */
    	public RobotInfo[] getEnemiesInAttackRange() {
    		return rc.senseNearbyRobots(myRange, theirTeam);
    	}
    	
    	public void goToLocation(MapLocation location) {
    		try {
	            if (rc.canSenseLocation(location) && rc.senseRobotAtLocation(location) != null 
	            		&& rc.getLocation().distanceSquaredTo(location)<3) { // 3 squares
	            	return;
	            }
            } catch (GameActionException e1) {
	            e1.printStackTrace();
            }
			Direction direction = getMoveDir(location);
			if (direction != null && rc.isCoreReady()) {
				try {
					rc.move(direction);
				} catch (GameActionException e) {
					e.printStackTrace();
				}
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
            spawnUnit();

            //Broadcast rallyPoint
            MapLocation rallyPoint;
            if (Clock.getRoundNum() < roundToLaunchAttack) {
                rallyPoint = new MapLocation( (this.myHQ.x + this.theirHQ.x) / 2,
                                              (this.myHQ.y + this.theirHQ.y) / 2);
            }
            else {
                rallyPoint = rc.senseEnemyTowerLocations()[0]; //attack!
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
            defend();
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
                        moveOptimally();
                    }
                }
            }
            transferSupplies();
            rc.yield();
        }
    }

    //MINERFACTORY
    public static class Minerfactory extends BaseBot {
        public Minerfactory(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
    		transferSupplies();
            spawnUnit();
            rc.yield();
        }
    }

    //MINER
    public static class Miner extends BaseBot {

    	public Miner(RobotController rc) {
    		super(rc);
    	}

    	public void execute() throws GameActionException {
    		boolean inConvoy = false;
    		if (Clock.getRoundNum()>roundToFormSupplyConvoy) {
    			inConvoy = formSupplyConvoy();
    		}
    		if (!inConvoy) {
    			if (!defend()) {
    				if (rc.isCoreReady()) {
    					//mine
    					if (rc.senseOre(rc.getLocation()) > 0) {
    						rc.mine();
    					}
    					else {
    						moveOptimally();
    					}
    				} 		
    			}
    		} else {
				if (rc.isCoreReady()) {
					if (rc.senseOre(rc.getLocation()) > 0) {
						rc.mine();
					}
				} 	
    		}
    		transferSupplies();
    		rc.yield();
    	}
    }

    //BARRACKS
    public static class Barracks extends BaseBot {
        public Barracks(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
    		transferSupplies();
            spawnUnit();
            rc.yield();
        }
    }

    //SOLDIER
    public static class Soldier extends BaseBot {
        public Soldier(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
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
            //TODO Bashers can not use defend() because they cannot call attack()
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
        	transferSupplies();
            attackLeastHealthEnemyInRange();
            rc.yield();
        }
    }


}
