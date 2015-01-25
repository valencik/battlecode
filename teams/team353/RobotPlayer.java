package team353;

import battlecode.common.*;

import java.util.*;

public class RobotPlayer {

	public static class smuConstants {


		
		public static int roundToLaunchAttack = 1600;
		public static int roundToDefendTowers = 500;
		public static int roundToFormSupplyConvoy = 400; // roundToBuildSOLDIERS;
		public static int RADIUS_FOR_SUPPLY_CONVOY = 2;
		public static int numTowersRemainingToAttackHQ = 1;
		public static double weightExponentMagic = 0.3;
		public static double weightScaleMagic = 0.8;
		
		public static int currentOreGoal = 100;
		
		public static double percentBeaversToGoToSecondBase = 0.4;
		
		// Defence
		public static int NUM_TOWER_PROTECTORS = 4;
		public static int NUM_HOLE_PROTECTORS = 3;
		public static int PROTECT_OTHERS_RANGE = 15;
		public static int DISTANCE_TO_START_PROTECTING_SQUARED = 200;
		
		// Idle States
		public static MapLocation defenseRallyPoint;
		public static int PROTECT_HOLE = 1;
		public static int PROTECT_TOWER = 2;
		
		// Supply
		public static int NUM_ROUNDS_TO_KEEP_SUPPLIED = 20;
		
		// Contain
		public static int CLOCKWISE = 0;
		public static int COUNTERCLOCKWISE = 1;
		public static int CURRENTLY_BEING_CONTAINED = 1;
		public static int NOT_CURRENTLY_BEING_CONTAINED = 2;
		
		// Strategies
		/*
		 * NOTE: If changing these values, alter smuTeamMemoryIndices accordingly
		 */
		public static int STRATEGY_DRONE_CONTAIN = 1;
		public static int STRATEGY_TANKS_AND_SOLDIERS = 2;
		public static int STRATEGY_DRONE_SWARM = 3;
		public static int STRATEGY_TANKS_AND_LAUNCHERS = 4;
		public static int STRATEGY_LAUNCHERS = 5;
		public static int STRATEGY_TANK_SWARM =6;
	}
	
	public static class smuIndices {
		public static int RALLY_POINT_X = 0;
		public static int RALLY_POINT_Y = 1;
		
		//Economy
		public static int freqQueue = 11;

		public static int STRATEGY = 19;
		public static int HQ_BEING_CONTAINED = 20;
		public static int HQ_BEING_CONTAINED_BY = 21;
		
		//Strategy broadcasts use frequencies 1000 through 3200!
		//TODO		
        public static final int channelAEROSPACELAB = 1100;
        public static final int channelBARRACKS = 1200;
        public static final int channelBASHER = 1300;
        public static final int channelBEAVER = 1400;
        public static final int channelCOMMANDER = 1500;
        public static final int channelCOMPUTER = 1600;
        public static final int channelDRONE = 1700;
        public static final int channelHANDWASHSTATION = 1800;
        public static final int channelHELIPAD = 1900;
        public static final int channelHQ = 2000;
        public static final int channelLAUNCHER = 2100;
        public static final int channelMINER = 2200;
        public static final int channelMINERFACTORY = 2300;
        public static final int channelMISSILE = 2400;
        public static final int channelSOLDIER = 2500;
        public static final int channelSUPPLYDEPOT = 2600;
        public static final int channelTANK = 2700;
        public static final int channelTANKFACTORY = 2800;
        public static final int channelTECHNOLOGYINSTITUTE = 2900;
        public static final int channelTOWER = 3000;
        public static final int channelTRAININGFIELD = 3100;
		public static final int[] channel = new int[] {0, channelAEROSPACELAB, channelBARRACKS, channelBASHER, channelBEAVER, channelCOMMANDER, 
		    channelCOMPUTER, channelDRONE, channelHANDWASHSTATION, channelHELIPAD, channelHQ, channelLAUNCHER, channelMINER, 
		    channelMINERFACTORY, channelMISSILE, channelSOLDIER, channelSUPPLYDEPOT, channelTANK, channelTANKFACTORY, 
		    channelTECHNOLOGYINSTITUTE, channelTOWER, channelTRAININGFIELD};
		
		public static int TOWER_HOLES_BEGIN = 4000;
	}
	
	public static class smuTeamMemoryIndices {
		public static int PREV_MAP_TOWER_THREAT = 0;
		public static int PREV_MAP_VOID_TYPE_PERCENT = 1;
		public static int NUM_ROUNDS_USING_STRATEGY_BASE = 1;
		// Strategies from smuConstants take values 2-7
		public static int ROUND_OUR_HQ_ATTACKED = 8; 
		
		// Round their HQ attacked?
		
		public static int ROUND_OUR_TOWER_DESTROYED_BASE = 10;
		// Base will expand to values 10-16
		public static int ROUND_THEIR_TOWER_DESTROYED_BASE = 17;
		// Base will expand to values 17-23
//		public static int ROUND_STARTED_USING_STRATEGY_BASE = 23;
//		// Base will expand to values 23-29
	}
	
	public static void run(RobotController rc) throws GameActionException {
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
        } else if (rc.getType() == RobotType.HELIPAD) {
            myself = new Helipad(rc);
        } else if (rc.getType() == RobotType.DRONE) {
            myself = new Drone(rc);
        } else if (rc.getType() == RobotType.TOWER) {
            myself = new Tower(rc);
        } else if (rc.getType() == RobotType.SUPPLYDEPOT) {
            myself = new Supplydepot(rc);
        } else if (rc.getType() == RobotType.HANDWASHSTATION) {
            myself = new Handwashstation(rc);
        } else if (rc.getType() == RobotType.TANKFACTORY) {
            myself = new Tankfactory(rc);
        } else if (rc.getType() == RobotType.TANK) {
            myself = new Tank(rc);
        } else if (rc.getType() == RobotType.AEROSPACELAB) {
            myself = new Aerospacelab(rc);
        } else if (rc.getType() == RobotType.LAUNCHER) {
            myself = new Launcher(rc);
        } else if (rc.getType() == RobotType.MISSILE) {
            myself = new Missile(rc);
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
            Direction[] dirs = new Direction[5];
            dirs[0] = toDest;
            if (rand.nextDouble() < 0.5){
                dirs[1] = toDest.rotateLeft();
                dirs[2] = toDest.rotateRight();
            } else {
                dirs[2] = toDest.rotateLeft();
                dirs[1] = toDest.rotateRight();
            }
            if (rand.nextDouble() < 0.5){
                dirs[3] = toDest.rotateLeft().rotateLeft();
                dirs[4] = toDest.rotateRight().rotateRight();
            } else {
                dirs[4] = toDest.rotateLeft().rotateLeft();
                dirs[3] = toDest.rotateRight().rotateRight();
            }

            return dirs;
        }

        public Direction[] getDirectionsAway(MapLocation dest) {
            Direction toDest = rc.getLocation().directionTo(dest).opposite();
            Direction[] dirs = new Direction[5];
            dirs[0] = toDest;
            if (rand.nextDouble() < 0.5){
                dirs[1] = toDest.rotateLeft();
                dirs[2] = toDest.rotateRight();
            } else {
                dirs[2] = toDest.rotateLeft();
                dirs[1] = toDest.rotateRight();
            }
            if (rand.nextDouble() < 0.5){
                dirs[3] = toDest.rotateLeft().rotateLeft();
                dirs[4] = toDest.rotateRight().rotateRight();
            } else {
                dirs[4] = toDest.rotateLeft().rotateLeft();
                dirs[3] = toDest.rotateRight().rotateRight();
            }

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
//                    //System.out.println("Could not find valid spawn location!");
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
//                    //System.out.println("Could not find valid build location!");
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

        public Direction getDirOfLauncherTarget() {
            int startingByteCode = Clock.getBytecodeNum();
            MapLocation myLocation = rc.getLocation();
            Direction[] targetDirections = getDirectionsToward(theirHQ);
            MapLocation[] targets = new MapLocation[5];
            
            for (int i=0; i<targetDirections.length; i++){
                targets[i] = myLocation.add(targetDirections[i], 3);
            }
            
            for (int j = 0; j < targets.length; j++) {
                if (Clock.getBytecodesLeft() > 500) {
                    int teammates = 0;

                    MapLocation targetCenter = targets[j];
                    RobotInfo[] allRobotsInTargetArea = rc.senseNearbyRobots(
                            targetCenter, 15, null);

                    for (RobotInfo robot : allRobotsInTargetArea) {
                        if (robot.team == myTeam)
                            teammates++;
                    }
                    if (allRobotsInTargetArea.length >= 2 * teammates) {
//                        //System.out.println("getPositionOfLauncherTarget [bytecode]:" + (Clock.getBytecodeNum()-startingByteCode));
                        return myLocation.directionTo(targetCenter);
                    }
                }
            }
//            System.out.println("FAILED getPositionOfLauncherTarget [bytecode]:" + (Clock.getBytecodeNum()-startingByteCode));
            return null;
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
            if (toAttack != null) {
            	rc.attackLocation(toAttack);
            }
        }

        public void attackLeastHealthEnemyInRange() throws GameActionException {
            RobotInfo[] enemies = getEnemiesInAttackingRange();

            if (enemies.length > 0) {
                if (rc.isWeaponReady()) {
                    attackLeastHealthEnemy(enemies);
                }
            }
        }
        
        public void moveToRallyPoint() throws GameActionException {
            if (rc.isCoreReady()) {
                int rallyX = rc.readBroadcast(smuIndices.RALLY_POINT_X);
                int rallyY = rc.readBroadcast(smuIndices.RALLY_POINT_Y);
                MapLocation rallyPoint = new MapLocation(rallyX, rallyY);
                
                if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack && 
                		rc.getLocation().distanceSquaredTo(rallyPoint) > 8 + Clock.getRoundNum() / 100) {

                    moveOptimally(getDirectionsToward(rallyPoint));
                } else {
                  Direction newDir = getMoveDir(rallyPoint);
                  
                  if (newDir != null && rc.canMove(newDir)) {
                      rc.move(newDir);
                  }                    
                }
            }

        }
        
        //Returns the current rally point MapLocation
        public MapLocation getRallyPoint() throws GameActionException {            
            int rallyX = rc.readBroadcast(smuIndices.RALLY_POINT_X);
            int rallyY = rc.readBroadcast(smuIndices.RALLY_POINT_Y);
            MapLocation rallyPoint = new MapLocation(rallyX, rallyY);
            return rallyPoint;
        }

        //Moves a random safe direction from input array
        public void moveOptimally(Direction[] optimalDirections) throws GameActionException {
            //TODO check safety
            if (optimalDirections != null) {
                boolean lookingForDirection = true;
                int attemptForDirection = 0;
                while(lookingForDirection){
                    MapLocation[] enemyTowers = rc.senseEnemyTowerLocations();
                    Direction currentDirection = optimalDirections[attemptForDirection];
                    //Direction currentDirection = optimalDirections[(int) (rand.nextDouble()*optimalDirections.length)];
                    MapLocation tileInFront = rc.getLocation().add(currentDirection);

                    //check that the direction in front is not a tile that can be attacked by the enemy towers
                    boolean tileInFrontSafe = true;
                    for(MapLocation m: enemyTowers){
                        if(m.distanceSquaredTo(tileInFront)<=RobotType.TOWER.attackRadiusSquared+1){
                            tileInFrontSafe = false;
                            break;
                        }
                    }

                    //check that we are not facing off the edge of the map
                    TerrainTile terrainTileInFront = rc.senseTerrainTile(tileInFront);
                    if(!tileInFrontSafe || 
                            !rc.isPathable(myType, tileInFront) ||
                            terrainTileInFront == TerrainTile.OFF_MAP ||
                            (myType != RobotType.DRONE && terrainTileInFront!=TerrainTile.NORMAL)){
                        //currentDirection = currentDirection.rotateLeft();
                        attemptForDirection++;
                        if (attemptForDirection == optimalDirections.length) {
//                            //System.out.println("No suitable direction found!");
                            return;
                        }
                    }else{
                        //try to move in the currentDirection direction
                        if(currentDirection != null && rc.isCoreReady() && rc.canMove(currentDirection)){
                            rc.move(currentDirection);
                            lookingForDirection = false;
                            return;
                        }
                    }

                }


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

            RobotType currentRobotType = rc.getType();
            Direction[] optimalDirections = null;

            switch(currentRobotType){
            case BASHER:
            	optimalDirections = getDirectionsToward(this.theirHQ);
                break;
            case BEAVER:
                if (getDistanceSquared(this.myHQ) < 50){
                    optimalDirections = getDirectionsAway(this.myHQ);    
                }
                optimalDirections = Direction.values();
                break;
            case COMMANDER:
                break;
            case COMPUTER:
                break;
            case DRONE:
            	optimalDirections = getDirectionsToward(this.theirHQ);
                break;
            case LAUNCHER:
            	optimalDirections = getDirectionsToward(this.theirHQ);
                break;
            case MINER:
                if (getDistanceSquared(this.myHQ) < 50){
                    optimalDirections = getDirectionsToward(this.myHQ);    
                }
                optimalDirections = Direction.values();
                break;
            case MISSILE:
                break;
            case SOLDIER:
            	optimalDirections = getDirectionsToward(this.theirHQ);
                break;
            case TANK:
            	optimalDirections = getDirectionsToward(this.theirHQ);
                break;
            default:
            } //Done RobotType specific actions.
            return optimalDirections;

        }

        //Spawns or Queues the unit if it is needed
        public boolean tryToSpawn(RobotType spawnType) throws GameActionException{
            int round = Clock.getRoundNum();
            double ore = rc.getTeamOre();
            int spawnTypeInt = RobotTypeToInt(spawnType);
            int spawnQueue = rc.readBroadcast(smuIndices.freqQueue);
            
            int currentAmount = rc.readBroadcast(smuIndices.channel[spawnTypeInt]+0);
            int desiredAmount = rc.readBroadcast(smuIndices.channel[spawnTypeInt]+1);
            int roundToBeginSpawning = rc.readBroadcast(smuIndices.channel[spawnTypeInt]+2);
            
            //Check if we actually need anymore spawnType units
            if (round > roundToBeginSpawning && currentAmount < desiredAmount){
                if(ore > myType.oreCost){
                    if (spawnUnit(spawnType)) return true;
                } else {
                    //Add spawnType to queue
                    if (spawnQueue == 0){
                        rc.broadcast(smuIndices.freqQueue, spawnTypeInt);
                    }
                    return false;
                }
            }
            return false;
        }
        
        //Spawns unit based on calling type. Performs all checks.
        public boolean spawnOptimally() throws GameActionException {
            if (rc.isCoreReady()){
                int spawnQueue = rc.readBroadcast(smuIndices.freqQueue);

                switch(myType){

                case BARRACKS:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.SOLDIER)){
                        if (tryToSpawn(RobotType.SOLDIER)) return true;
                    }
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.BASHER)){
                        if (tryToSpawn(RobotType.BASHER)) return true;
                    }
                    break;
                case HQ:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.BEAVER)){
                        if (tryToSpawn(RobotType.BEAVER)) return true;
                    }
                    break;
                case HELIPAD:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.DRONE)){
                        if (tryToSpawn(RobotType.DRONE)) return true;
                    }
                    break;
                case AEROSPACELAB:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.LAUNCHER)){
                        if (tryToSpawn(RobotType.LAUNCHER)) return true;
                    }
                    break;
                case MINERFACTORY:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.MINER)){
                       if (tryToSpawn(RobotType.MINER)) return true;
                    }
                    break;
                case TANKFACTORY:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.TANK)){
                        if (tryToSpawn(RobotType.TANK)) return true;
                    }
                    break;
                case TECHNOLOGYINSTITUTE:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.COMPUTER)){
                        if (tryToSpawn(RobotType.COMPUTER)) return true;
                    }
                    break;
                case TRAININGFIELD:
                    if (spawnQueue == 0 || spawnQueue == RobotTypeToInt(RobotType.COMMANDER)){
                        if (tryToSpawn(RobotType.COMMANDER)) return true;
                    }
                    break;
                default:
//                    System.out.println("ERROR: No building type match found in spawnOptimally()!");
                    return false;
                }
            }//isCoreReady
            return false;
        }

        //Gets direction, checks delays, and spawns unit
        public boolean spawnUnit(RobotType spawnType) {
            //Get a direction and then actually spawn the unit.
            Direction randomDir = getSpawnDir(spawnType);
            if(rc.isCoreReady() && randomDir != null){
                try {
                    if (rc.canSpawn(randomDir, spawnType)) {
                        rc.spawn(randomDir, spawnType);
                        if (IntToRobotType(rc.readBroadcast(smuIndices.freqQueue)) == spawnType) {
                            rc.broadcast(smuIndices.freqQueue, 0);
                        }
                        incrementCount(spawnType);
                        return true;
                    }
                } catch (GameActionException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }
        
        //Spawns unit based on calling type. Performs all checks.
        public void buildOptimally() throws GameActionException {

            int round = Clock.getRoundNum();
            double ore = rc.getTeamOre();
            boolean buildingsOutrankUnits = true;
            int queue = rc.readBroadcast(smuIndices.freqQueue);
            RobotType[] arrayOfStructures = new RobotType[] {RobotType.AEROSPACELAB,
                    RobotType.BARRACKS, RobotType.HANDWASHSTATION, RobotType.HELIPAD, RobotType.HQ,
                    RobotType.MINERFACTORY, RobotType.SUPPLYDEPOT, RobotType.TANKFACTORY,
                    RobotType.TECHNOLOGYINSTITUTE, RobotType.TOWER, RobotType.TRAININGFIELD};

            //If there is something in the queue and we can not replace it, then return
            if (queue != 0 && !buildingsOutrankUnits){
                System.out.println("Queue full, can't outrank");
                return;
            }

            //Check if there is a building in queue
            if (Arrays.asList(arrayOfStructures).contains(IntToRobotType(queue))) {
                //Build it if we can afford it
                if (ore > IntToRobotType(queue).oreCost) {
                    System.out.println("INFO: Satisfying queue. ("+IntToRobotType(queue).name()+")");
                    buildUnit(IntToRobotType(queue));
                }
                //Return either way, we can't replace buildings in the queue
                return;
            }

            //Loop over structures and build or queue any needed ones
            for (RobotType structure : arrayOfStructures) {
                int intStructure = RobotTypeToInt(structure);
                double weightOfStructure = getWeightOfRobotType(IntToRobotType(intStructure));

                if (weightOfStructure == 1.0){
                    if (ore > IntToRobotType(intStructure).oreCost){
                        buildUnit(IntToRobotType(intStructure));
                        System.out.println("Tried to build "+ IntToRobotType(intStructure));
                    } else {
                        rc.broadcast(smuIndices.freqQueue, intStructure);
                        System.out.println("Scheduled a " + IntToRobotType(intStructure).name() +
                                ". Need " + (IntToRobotType(intStructure).oreCost-ore) + " more ore.");
                    }
                    return;
                }                        
            }//end loop of structures

        }

        
        //Gets direction, checks delays, and builds unit
        public boolean buildUnit(RobotType buildType) {
            //Get a direction and then actually build the unit.
            Direction randomDir = getBuildDir(buildType);
            if(rc.isCoreReady() && randomDir != null){
                try {
                    if (rc.canBuild(randomDir, buildType)) {
                        rc.build(randomDir, buildType);
                        if (IntToRobotType(rc.readBroadcast(smuIndices.freqQueue)) == buildType) {
                            rc.broadcast(smuIndices.freqQueue, 0);
                        }
                        incrementCount(buildType);
                        return true;
                    }
                } catch (GameActionException e) {
                    e.printStackTrace();
                }
            }
            return false;
        }

        //Increment the currentAmount of a RobotType
        public void incrementCount(RobotType type) throws GameActionException {
            int intType = RobotTypeToInt(type);
            int currentAmount = rc.readBroadcast(smuIndices.channel[intType]+0);
            rc.broadcast(smuIndices.channel[intType+0], currentAmount+1);
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
        			if (!ri.type.isBuilding && ri.supplyLevel < lowestSupply) {
        					lowestSupply = ri.supplyLevel;
    						transferAmount = (rc.getSupplyLevel()-ri.supplyLevel)/2;        				
        					suppliesToThisLocation = ri.location;
        		}
        	}
        	if(suppliesToThisLocation!=null){
        	    if (roundStart == Clock.getRoundNum() && transferAmount > 0 && Clock.getBytecodesLeft() > 550) {
    				try {
    					rc.transferSupplies((int)transferAmount, suppliesToThisLocation);
    				} catch(GameActionException gax) {
    					gax.printStackTrace();
    				}
        		}
        	}
        }
        
        // true, I'm in the convoy or going to be
        // false, no place in the convoy for me
        public boolean formSupplyConvoy() {
        	try {
	            if (rc.readBroadcast(smuIndices.STRATEGY) == smuConstants.STRATEGY_DRONE_CONTAIN) {
	            	return false;
	            }
            } catch (GameActionException e1) {
	            e1.printStackTrace();
            }
    		Direction directionForChain = getSupplyConvoyDirection(myHQ);
        	RobotInfo minerAtEdge = getUnitAtEdgeOfSupplyRangeOf(RobotType.MINER, myHQ, directionForChain);

        	if (minerAtEdge == null){
            	goToLocation(myHQ.add(directionForChain, smuConstants.RADIUS_FOR_SUPPLY_CONVOY));
        		return true;
        	} else if (minerAtEdge.ID == rc.getID()) {
        		return true;
        	}
        	RobotInfo previousMiner = null;
        	try {
	            while ((minerAtEdge != null || !minerAtEdge.type.isBuilding) && minerAtEdge.location.distanceSquaredTo(getRallyPoint()) > 4) {
	            	directionForChain = getSupplyConvoyDirection(minerAtEdge.location);
	            	previousMiner = minerAtEdge;
	            	minerAtEdge = getUnitAtEdgeOfSupplyRangeOf(RobotType.MINER, minerAtEdge.location, directionForChain);
	            	if (minerAtEdge == null) {
	            		goToLocation(previousMiner.location.add(directionForChain, smuConstants.RADIUS_FOR_SUPPLY_CONVOY));
	            		return true;
	            	} else if (minerAtEdge.ID == rc.getID()) {
	            		return true;
	            	}
	            }
            } catch (GameActionException e) {
	            e.printStackTrace();
            }
        	return false;
        }
        
        public Direction getSupplyConvoyDirection(MapLocation startLocation) {
        	Direction directionForChain = myHQ.directionTo(theirHQ);
        	MapLocation locationToGo = startLocation.add(directionForChain, smuConstants.RADIUS_FOR_SUPPLY_CONVOY);
        	if (rc.senseTerrainTile(locationToGo) == TerrainTile.NORMAL && rc.canSenseLocation(locationToGo)) {
                RobotInfo robotInDirection;
                try {
	                robotInDirection = rc.senseRobotAtLocation(locationToGo);
	                if (robotInDirection == null || !robotInDirection.type.isBuilding) {
	                	return directionForChain;
	                }
                } catch (GameActionException e) {
	                e.printStackTrace();
                }
        	}
        	Direction[] directions = {Direction.NORTH, Direction.NORTH_EAST, Direction.EAST, Direction.SOUTH_EAST, Direction.SOUTH, Direction.SOUTH_WEST, Direction.WEST, Direction.NORTH_WEST};
        	for (int i = 0; i < directions.length; i++) {
        		locationToGo = startLocation.add(directions[i], smuConstants.RADIUS_FOR_SUPPLY_CONVOY);
        		if (directions[i] != directionForChain && directions[i] != Direction.OMNI && directions[i] != Direction.NONE 
        				&& rc.senseTerrainTile(locationToGo) == TerrainTile.NORMAL) {
        			RobotInfo robotInDirection;
                    try {
	                    robotInDirection = rc.senseRobotAtLocation(locationToGo);
	                    if (robotInDirection == null || !robotInDirection.type.isBuilding) {
	                    	return directions[i];
	                    }
                    } catch (GameActionException e) {
	                    e.printStackTrace();
                    }
        		}
        	}
        	return Direction.NONE;
        }
        
        public RobotInfo getUnitAtEdgeOfSupplyRangeOf(RobotType unitType, MapLocation startLocation, Direction directionForChain) {
        	MapLocation locationInChain =  startLocation.add(directionForChain, smuConstants.RADIUS_FOR_SUPPLY_CONVOY);
        	if (rc.canSenseLocation(locationInChain)) {
        		try {
        			return rc.senseRobotAtLocation(locationInChain);
        		} catch (GameActionException e) {
        			e.printStackTrace();
        		}
        	}
        	return null;
        }
    	
        public MapLocation getSecondBaseLocation() {
			Direction directionToTheirHQ = myHQ.directionTo(theirHQ);
			MapLocation secondBase = null;
			if (directionToTheirHQ == Direction.EAST || directionToTheirHQ == Direction.WEST) {
				secondBase = getSecondBaseLocationInDirections(Direction.NORTH, Direction.SOUTH);
			} else if (directionToTheirHQ == Direction.NORTH || directionToTheirHQ == Direction.SOUTH) {
				secondBase = getSecondBaseLocationInDirections(Direction.EAST, Direction.WEST);
			} else {
				Direction[] directions = breakdownDirection(directionToTheirHQ);
				secondBase = getSecondBaseLocationInDirections(directions[0], directions[1]);
			}
			if (secondBase != null) {
				secondBase = secondBase.add(myHQ.directionTo(secondBase), 4);
			}
			return secondBase;
        }
        
    	public MapLocation getSecondBaseLocationInDirections(Direction dir1, Direction dir2) {
    		MapLocation towers[] = rc.senseTowerLocations();
    		int maxDistance = Integer.MIN_VALUE;
    		int maxDistanceIndex = -1;
    		Direction dirToEnemy = myHQ.directionTo(theirHQ);
    		Direction dir1left = dir1.rotateLeft();
    		Direction dir1right = dir1.rotateRight();
    		Direction dir2left = dir2.rotateLeft();
    		Direction dir2right =  dir2.rotateRight();
    		for (int i = 0; i<towers.length; i++) {
    			Direction dirToTower = myHQ.directionTo(towers[i]);
    			if (dirToTower == dir1 || dirToTower == dir2
    					|| (dir1left != dirToEnemy && dirToTower == dir1left) 
    					|| (dir1right != dirToEnemy && dirToTower == dir1right)
    					|| (dir2left != dirToEnemy && dirToTower == dir2left)
    					|| (dir2right != dirToEnemy && dirToTower == dir2right)) {
    				int distanceToTower = myHQ.distanceSquaredTo(towers[i]);
    				if (distanceToTower > maxDistance) {
    					maxDistance = distanceToTower;
    					maxDistanceIndex = i;
    				}
    			}
    		}
    		if (maxDistanceIndex != -1) {
    			return towers[maxDistanceIndex];
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
        
        public int myContainDirection = smuConstants.CLOCKWISE;
        public MapLocation myContainPreviousLocation;
        public void contain() {
        	MapLocation enemyHQ = rc.senseEnemyHQLocation();
        	MapLocation[] enemyTowers = rc.senseEnemyTowerLocations();
        	MapLocation myLocation = rc.getLocation();
        	int radiusFromHQ = 24;
        	if (enemyTowers.length >= 2) {
        		radiusFromHQ = 35;
        	}
        	
        	if (myLocation.distanceSquaredTo(enemyHQ) > radiusFromHQ + 3) {
        		// move towards the HQ
        		try {
        			RobotInfo[] nearbyTeammates = rc.senseNearbyRobots(4, myTeam);
        			int numAttackers = 0;
        			for (RobotInfo teammate : nearbyTeammates) {
        				if (!teammate.type.isBuilding && teammate.type != RobotType.MINER && teammate.type != RobotType.BEAVER) numAttackers++;
        			}
        			if (numAttackers >= getNumAttackersToContain()) {
        				moveOptimally();
        			}
                } catch (GameActionException e) {
	                e.printStackTrace();
                }
        	} else {
        		MapLocation locationToGo = null;
        		Direction directionToGo = null;
        		if (myContainDirection == smuConstants.CLOCKWISE) {
        			directionToGo = getClockwiseDirection(myLocation, enemyHQ);
        		} else {
        			directionToGo = getCounterClockwiseDirection(myLocation, enemyHQ);
        		}
        		locationToGo = myLocation.add(directionToGo);
        		if (rc.isPathable(myType, locationToGo)) {
        			if (isLocationSafe(locationToGo)) {
        				goToLocation(locationToGo);
        			} else {
        				Direction[] directions = breakdownDirection(directionToGo);
        				for (int i = 0; i < directions.length; i++) {
        					locationToGo = myLocation.add(directions[i]);
        					if (isLocationSafe(locationToGo)) {
        						goToLocation(locationToGo);
        						myContainPreviousLocation = myLocation;
        						return;
        					}
        				}
        			}
        		} else if (myContainPreviousLocation.equals(myLocation)){
        			if (myContainDirection == smuConstants.CLOCKWISE) {
        				myContainDirection = smuConstants.COUNTERCLOCKWISE;
        			} else {
        				myContainDirection = smuConstants.CLOCKWISE;
        			}
        		}
        	}
        	myContainPreviousLocation = myLocation;
        }
        
        public int getNumAttackersToContain() {
        	if (myType == RobotType.SOLDIER) {
        		return 2;
        	} else if (myType == RobotType.TANK) {
        		return 1;
        	} else {
        		return 2;
        	}
        }
        
        public boolean isLocationSafe(MapLocation location) {
        	int hqAttackRadius = RobotType.HQ.attackRadiusSquared;
        	if (rc.senseEnemyTowerLocations().length >= 2) hqAttackRadius = 35;
        	if (location.distanceSquaredTo(theirHQ) > hqAttackRadius) {
        		for (MapLocation tower : rc.senseEnemyTowerLocations()) {
        			if (location.distanceSquaredTo(tower) <= RobotType.TOWER.attackRadiusSquared) {
        				return false;
        			}
        		}
        		return true;
        	}
        	return false;
        }
        
        public Direction[] breakdownDirection(Direction direction) {
        	Direction[] breakdown = new Direction[2];
        	switch(direction) {
        		case NORTH_EAST:
        			breakdown[0] = Direction.NORTH;
        			breakdown[1] = Direction.EAST;
        			break;
        		case SOUTH_EAST:
        			breakdown[0] = Direction.SOUTH;
        			breakdown[1] = Direction.EAST;
        			break;
        		case NORTH_WEST:
        			breakdown[0] = Direction.NORTH;
        			breakdown[1] = Direction.WEST;
        			break;
        		case SOUTH_WEST:
        			breakdown[0] = Direction.SOUTH;
        			breakdown[1] = Direction.WEST;
        			break;
        		default:
        			break;
        	}
        	return breakdown;
        }
        
        public Direction getClockwiseDirection(MapLocation myLocation, MapLocation anchor) {
        	Direction directionToAnchor = myLocation.directionTo(anchor);
        	if (directionToAnchor.equals(Direction.EAST) || directionToAnchor.equals(Direction.SOUTH_EAST)) {
        		return Direction.NORTH_EAST;
        	} else if (directionToAnchor.equals(Direction.SOUTH) || directionToAnchor.equals(Direction.SOUTH_WEST)) {
        		return Direction.SOUTH_EAST;
        	} else if (directionToAnchor.equals(Direction.WEST) || directionToAnchor.equals(Direction.NORTH_WEST)) {
        		return Direction.SOUTH_WEST;
        	} else if (directionToAnchor.equals(Direction.NORTH) || directionToAnchor.equals(Direction.NORTH_EAST)) {
        		return Direction.NORTH_WEST;
        	}
        	return Direction.NONE;
        }
        
        public Direction getCounterClockwiseDirection(MapLocation myLocation, MapLocation anchor) {
        	Direction oppositeDirection = getClockwiseDirection(myLocation, anchor);
        	if (oppositeDirection.equals(Direction.NORTH_EAST)) {
        		return Direction.SOUTH_WEST;
        	} else if (oppositeDirection.equals(Direction.SOUTH_EAST)) {
        		return Direction.NORTH_WEST;
        	} else if (oppositeDirection.equals(Direction.SOUTH_WEST)) {
        		return Direction.NORTH_EAST;
        	} else if (oppositeDirection.equals(Direction.NORTH_WEST)) {
        		return Direction.SOUTH_WEST;
        	}
        	return Direction.NONE;
        }
        
        // Defend
        public boolean defendSelf() {
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
    		return false;
        }
        
        public boolean defendTeammates() {
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
    		return false;
        }
        
        public boolean defend() {
    		// A1, Protect Self
        	boolean isProtectingSelf = defendSelf();
        	if (isProtectingSelf) {
        		return true;
        	}
        	
        	// A2, Protect Nearby
        	boolean isProtectingTeammates = defendTeammates();
        	if (isProtectingTeammates) {
        		return true;
        	}

    		return false;
    	}
        
        public int RobotTypeToInt(RobotType type){
            switch(type) {
            case AEROSPACELAB:
                return 1;
            case BARRACKS:
                return 2;
            case BASHER:
                return 3;
            case BEAVER:
                return 4;
            case COMMANDER:
                return 5;
            case COMPUTER:
                return 6;
            case DRONE:
                return 7;
            case HANDWASHSTATION:
                return 8;
            case HELIPAD:
                return 9;
            case HQ:
                return 10;
            case LAUNCHER:
                return 11;
            case MINER:
                return 12;
            case MINERFACTORY:
                return 13;
            case MISSILE:
                return 14;
            case SOLDIER:
                return 15;
            case SUPPLYDEPOT:
                return 16;
            case TANK:
                return 17;
            case TANKFACTORY:
                return 18;
            case TECHNOLOGYINSTITUTE:
                return 19;
            case TOWER:
                return 20;
            case TRAININGFIELD:
                return 21;

            default:
                return -1;
            }
        }

        public RobotType IntToRobotType(int type){
            switch(type) {
            case 1:
                return RobotType.AEROSPACELAB;
            case 2:
                return RobotType.BARRACKS;
            case 3:
                return RobotType.BASHER;
            case 4:
                return RobotType.BEAVER;
            case 5:
                return RobotType.COMMANDER;
            case 6:
                return RobotType.COMPUTER;
            case 7:
                return RobotType.DRONE;
            case 8:
                return RobotType.HANDWASHSTATION;
            case 9:
                return RobotType.HELIPAD;
            case 10:
                return RobotType.HQ;
            case 11:
                return RobotType.LAUNCHER;
            case 12:
                return RobotType.MINER;
            case 13:
                return RobotType.MINERFACTORY;
            case 14:
                return RobotType.MISSILE;
            case 15:
                return RobotType.SOLDIER;
            case 16:
                return RobotType.SUPPLYDEPOT;
            case 17:
                return RobotType.TANK;
            case 18:
                return RobotType.TANKFACTORY;
            case 19:
                return RobotType.TECHNOLOGYINSTITUTE;
            case 20:
                return RobotType.TOWER;
            case 21:
                return RobotType.TRAININGFIELD;

            default:
                return null;
            }
        }
        
//        //Returns a weight representing the 'need' for the RobotType
//        public double getWeightOfRobotType(RobotType type) throws GameActionException {
//            int typeInt = RobotTypeToInt(type);
//            if (rc.readBroadcast(smuIndices.freqDesiredNumOf + typeInt) == 0) return 0;
//            double weight = rc.readBroadcast(smuIndices.freqRoundToBuild + typeInt) + 
//                    (rc.readBroadcast(smuIndices.freqRoundToFinish + typeInt) - rc.readBroadcast(smuIndices.freqRoundToBuild + typeInt)) / 
//                    rc.readBroadcast(smuIndices.freqDesiredNumOf + typeInt) * rc.readBroadcast(smuIndices.freqNum[typeInt]);
//            return weight;
//        }
        
        //Returns a weight representing the 'need' for the RobotType
        public double getWeightOfRobotType(RobotType type) throws GameActionException {
            int typeInt = RobotTypeToInt(type);
            int round = Clock.getRoundNum();
            double weight = 0;
            
            if (!type.isBuilding) {
                int currentAmount = rc.readBroadcast(smuIndices.channel[typeInt]+0);
                int desiredAmount = rc.readBroadcast(smuIndices.channel[typeInt]+1);
                int roundToBeginSpawning = rc.readBroadcast(smuIndices.channel[typeInt]+2);
                int roundToFinishSpawning = rc.readBroadcast(smuIndices.channel[typeInt]+3);
                
                //Return zero if unit is not desired. (Divide by zero protection)
                if (desiredAmount == 0) {
                    //System.out.println("Error: No desired "+IntToRobotType(typeInt));
                    return 0;
                }
                if (roundToBeginSpawning >= roundToFinishSpawning) {
                    //System.out.println("Error: build > finish for: "+IntToRobotType(typeInt));
                    return 0;
                }
                if (round < roundToBeginSpawning) {
                    //System.out.println("Error: Too early for "+IntToRobotType(typeInt));
                    return 0;
                }
                
                //The weight is equal to a point on the surface drawn by z = x^(m*y) where
                double x = (double) (round - roundToBeginSpawning)
                        / (double) (roundToFinishSpawning - roundToBeginSpawning);
                double y = (double) currentAmount / (double) desiredAmount;
                weight = smuConstants.weightScaleMagic
                        * Math.pow(x, (smuConstants.weightExponentMagic + y));
                //System.out.println("type: "+IntToRobotType(typeInt)+" x: " + x + " y: " + y + " weight: " + weight);
                return weight;
            } //end units
            
            if (type.isBuilding){
                int currentAmount = rc.readBroadcast(smuIndices.channel[typeInt]+0);
                int desiredAmount = rc.readBroadcast(smuIndices.channel[typeInt]+1);
                int nextRoundToBuild = rc.readBroadcast(smuIndices.channel[typeInt]+2+currentAmount);
                int intQueuedType = rc.readBroadcast(smuIndices.freqQueue);
                
                if (currentAmount < desiredAmount && nextRoundToBuild != 0) {
                    if (round > nextRoundToBuild && typeInt != intQueuedType) {
                        //TODO We need to check if we are currently building it...
                        System.out.println("Warning: round > nextRoundToBuild && not queued >> " + type.name());
                        return 1.0;
                    }
                    if (round == nextRoundToBuild) {
                        return 1.0;
                    }
                    if (round < nextRoundToBuild) {
                        return 0.0;
                    }
                    System.out.println("ERROR: Unexpected return path in getWeightOfRobotType " + type.name());
                    return weight;
                } else {
                    return 0;
                }
            } //end structures
            
            System.out.println("ERROR: Unexpected return path in getWeightOfRobotType");
            return weight;
        }
        
    	public RobotInfo[] getEnemiesInAttackRange() {
    		return rc.senseNearbyRobots(myRange, theirTeam);
    	}
    	
    	//TODO Use optimally instead?
    	public void goToLocation(MapLocation location) {
    		try {
	            if (rc.canSenseLocation(location) && rc.senseRobotAtLocation(location) != null 
	            		&& rc.getLocation().distanceSquaredTo(location)<3) { // 3 squares
	            	return;
	            }
	            Direction direction = getMoveDir(location);
	            RobotInfo[] nearbyEnemies = rc.senseNearbyRobots(rc.getLocation().add(direction), RobotType.TOWER.attackRadiusSquared, theirTeam);
	            boolean towersNearby = false;
	            for (RobotInfo enemy : nearbyEnemies) {
	            	if (enemy.type == RobotType.TOWER || enemy.type == RobotType.HQ) {
	            		towersNearby = true;
	            		break;
	            	}
	            }
	            if (!towersNearby || Clock.getRoundNum() > smuConstants.roundToLaunchAttack) {
	            	if (direction != null && rc.isCoreReady() && rc.canMove(direction) && Clock.getBytecodesLeft() > 55) {
	            		rc.move(direction);
	            	}
	            }
            } catch (GameActionException e1) {
	            e1.printStackTrace();
            }
    	}
    	
    	public RobotInfo[] getRobotsEngagedInAttack() {
    		RobotInfo[] nearbyRobots = rc.senseNearbyRobots(smuConstants.PROTECT_OTHERS_RANGE);
    		boolean hasEnemy = false;
    		boolean hasFriendly = false;
    		for (RobotInfo robot : nearbyRobots) {
    			if(robot.team == theirTeam && robot.type != RobotType.TOWER) {
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
    	
        public RobotInfo[] getTeammatesInAttackRange() {
            return rc.senseNearbyRobots(myRange, myTeam);
        }

    	public RobotInfo[] getTeammatesNearTower(MapLocation towerLocation) {
    		return rc.senseNearbyRobots(towerLocation, RobotType.TOWER.attackRadiusSquared, myTeam);
    	}
    	
    	// Find out if there are any holes between a teams tower and their HQ
    	public MapLocation[] computeHoles() {
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
    		int holesBroadcastIndex = smuIndices.TOWER_HOLES_BEGIN;
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
//    			//System.out.println("Tower " + i + " overlapped " + overlapped[i] + " " + towerLocations[i]);
    			if (overlapped[i] < 2) {
    				try {
    					int towerAttackRadius = (int) Math.sqrt(RobotType.TOWER.attackRadiusSquared) + 1;
    					if (!coveredLeft) {
//    						//System.out.println("Tower " + towerLocations[i] + " Not covered left");
    						rc.broadcast(holesBroadcastIndex, towerLocations[i].x - towerAttackRadius);
    						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y);
    						holesBroadcastIndex+=2;
    					}
    					if (!coveredRight) {
//    						//System.out.println("Tower " + towerLocations[i] + " Not covered right");
    						rc.broadcast(holesBroadcastIndex, towerLocations[i].x + towerAttackRadius);
    						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y);
    						holesBroadcastIndex+=2;
    					}
    					if (!coveredTop) {
//    						//System.out.println("Tower " + towerLocations[i] + " Not covered top");
    						rc.broadcast(holesBroadcastIndex, towerLocations[i].x);
    						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y - towerAttackRadius);
    						holesBroadcastIndex+=2;
    					}
    					if (!coveredBottom) {
//    						//System.out.println("Tower " + towerLocations[i] + " Not covered bottom");
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
    		
//    		//System.out.println("BYTEEND on " + Clock.getRoundNum() + ": " + Clock.getBytecodeNum());
    		return null;
    	}
    	
    }

    //----- Per RoboType code below -----

    //HQ
    public static class HQ extends BaseBot {
        public int xMin, xMax, yMin, yMax;
        public int xpos, ypos;
        public int totalNormal, totalVoid, totalProcessed;
        public int towerThreat;

        public static double ratio;
        public boolean isFinishedAnalyzing = false;
        public boolean analyzedTowers = false;
        
        public int defaultStrategy = smuConstants.STRATEGY_TANKS_AND_SOLDIERS;
        public boolean hasChosenStrategyPrior = false;
        public int strategy = defaultStrategy;
        public int prevStrategy = 0;
        public int numTowers;
        public int numEnemyTowers;
        public boolean dronesFailed = false;
        public boolean analyzedPrevMatch = false;
        
    	public HQ(RobotController rc) {
            super(rc);
            
            numTowers = rc.senseTowerLocations().length;
            numEnemyTowers = rc.senseEnemyTowerLocations().length;
            
            xMin = Math.min(this.myHQ.x, this.theirHQ.x);
            xMax = Math.max(this.myHQ.x, this.theirHQ.x);
            yMin = Math.min(this.myHQ.y, this.theirHQ.y);
            yMax = Math.max(this.myHQ.y, this.theirHQ.y);

            xpos = xMin;
            ypos = yMin;
            
            totalNormal = totalVoid = totalProcessed = 0;
            towerThreat = 0;
            isFinishedAnalyzing = false;
            
            try {
	            computeStrategy();
            } catch (GameActionException e) {
	            e.printStackTrace();
            }
            computeHoles();
        }
    	
    	public void analyzePreviousMatch() {
    		long[] prevGameTeamMemory = rc.getTeamMemory();
    		boolean hasData = false;
    		for (long memory : prevGameTeamMemory) {
    			if (memory != 0) {
    				hasData = true;
    				break;
    			}
    		}
    		if (hasData) {
    			double prevRatio = ((double) prevGameTeamMemory[smuTeamMemoryIndices.PREV_MAP_VOID_TYPE_PERCENT])/100.0;
    			
    			int mostUsed = 0;
    			int mostUsedIndex = -1;
    			int secondMostUsed = 0;
    			int secondMostUsedIndex = -1;
    			
    			for(int i = 0; i < 6; i++) { // 6 is number of different strategies
    				int numRoundsUsed = (int) prevGameTeamMemory[smuTeamMemoryIndices.NUM_ROUNDS_USING_STRATEGY_BASE + i];
    				if (numRoundsUsed > mostUsed) {
    					secondMostUsed = mostUsed;
    					secondMostUsedIndex = mostUsedIndex;
    					mostUsed = numRoundsUsed;
    					mostUsedIndex = i;
    				} else if (numRoundsUsed > secondMostUsed) {
    					secondMostUsed = numRoundsUsed;
    					secondMostUsedIndex = i;
    				}
    			}
    			
    			if (mostUsed == smuConstants.STRATEGY_DRONE_CONTAIN || mostUsed == smuConstants.STRATEGY_DRONE_SWARM) {
    				dronesFailed = true;
//    				System.out.println(Clock.getRoundNum() + " Drones failed.");
    			} else {
    				if (ratio > 0.85 && prevRatio > 0.85) { 
    					// Both Traversables
    					int i = 0;
    					while (prevGameTeamMemory[smuTeamMemoryIndices.ROUND_OUR_TOWER_DESTROYED_BASE + i] != 0) {
    						i++;
    					}
    					int ourTowersDestroyed = i;
    					int j = 0;
    					while (prevGameTeamMemory[smuTeamMemoryIndices.ROUND_THEIR_TOWER_DESTROYED_BASE + j] != 0) {
    						j++;
    					}
    					int theirTowersDestroyed = j;
    					if (ourTowersDestroyed < theirTowersDestroyed) {
    						defaultStrategy = mostUsedIndex + 1;
    					} else {
    						if (secondMostUsedIndex != -1) {
    							defaultStrategy = secondMostUsedIndex + 1;
    						}
    					}
    				}
    				
    			}
    			
    			for (int i = 0; i < GameConstants.TEAM_MEMORY_LENGTH; i++) {
    				rc.setTeamMemory(i,	0);
    			}
    		}
    		analyzedPrevMatch = true;
    	}
    	
    	public void analyzeMap() {
            while (ypos < yMax + 1) {
                TerrainTile t = rc.senseTerrainTile(new MapLocation(xpos, ypos));

                if (t == TerrainTile.NORMAL) {
                    totalNormal++;
                    totalProcessed++;
                }
                else if (t == TerrainTile.VOID) {
                    totalVoid++;
                    totalProcessed++;
                }
                xpos++;
                if (xpos == xMax + 1) {
                    xpos = xMin;
                    ypos++;
                }

                if (Clock.getBytecodesLeft() < 50) {
                    return;
                }
            }
            ratio = (double) totalNormal / totalProcessed;
            isFinishedAnalyzing = true;
        }
    	
        public void analyzeTowers() {
            MapLocation[] towers = rc.senseEnemyTowerLocations();
            towerThreat = 0;

            for (int i=0; i<towers.length; ++i) {
                MapLocation towerLoc = towers[i];

                if ((xMin <= towerLoc.x && towerLoc.x <= xMax && yMin <= towerLoc.y && towerLoc.y <= yMax) || towerLoc.distanceSquaredTo(this.theirHQ) <= 50) {
                    for (int j=0; j<towers.length; ++j) {
                        if (towers[j].distanceSquaredTo(towerLoc) <= 50) {
                            towerThreat++;
                        }
                    }
                }
            }
            analyzedTowers = true;
        }

        public void chooseStrategy() throws GameActionException {
        	if (hasChosenStrategyPrior && Clock.getRoundNum() % 250 != 0) {
        		return;
        	}
            if (rc.readBroadcast(smuIndices.HQ_BEING_CONTAINED) == smuConstants.NOT_CURRENTLY_BEING_CONTAINED) {
            	// Test for Swarms
        		MapLocation[] ourTowers = rc.senseTowerLocations();
        		RobotType swarmingType = null;
        		if (ourTowers != null && ourTowers.length > 0) {
        			int closestTower = -1;
        			int closestDistanceToEnemyHQ = Integer.MAX_VALUE;
        			for (int i = 0; i < ourTowers.length; i++) {
        				int currDistanceToEnemyHQ = ourTowers[i].distanceSquaredTo(theirHQ);
        				if (currDistanceToEnemyHQ < closestDistanceToEnemyHQ) {
        					closestDistanceToEnemyHQ = currDistanceToEnemyHQ;
        					closestTower = i;
        				}
        			}
        			RobotInfo[] enemiesSwarming = rc.senseNearbyRobots(ourTowers[closestTower], 100, theirTeam);
        			if (enemiesSwarming != null && enemiesSwarming.length > 0) {
        				swarmingType = IntToRobotType(getMajorityRobotType(enemiesSwarming));
        			}
        		}
            	
        		if (ratio <= 0.85) {
        			// Void heavy map
        			if (towerThreat >= 10) {
        				// Defensive Map
            			strategy = smuConstants.STRATEGY_DRONE_SWARM;
        			} else {
        				// Offensive Map
            			strategy = smuConstants.STRATEGY_DRONE_CONTAIN;
        			}
        		} else {
        			// Traversable Map
        			if (swarmingType == RobotType.SOLDIER) {
        				strategy = smuConstants.STRATEGY_TANKS_AND_LAUNCHERS;
        			} else if (swarmingType == RobotType.DRONE) {
        				strategy = smuConstants.STRATEGY_LAUNCHERS;
        			} else if (swarmingType == RobotType.TANK) {
        				strategy = smuConstants.STRATEGY_TANK_SWARM;
        			} else {
        				strategy = defaultStrategy;
        			}
        		}
            } else {
            	RobotType containingType = IntToRobotType(rc.readBroadcast(smuIndices.HQ_BEING_CONTAINED_BY));
            	if (containingType == RobotType.DRONE) {
            		strategy = smuConstants.STRATEGY_LAUNCHERS;
            	} else if (containingType == RobotType.TANK) {
            		strategy = smuConstants.STRATEGY_LAUNCHERS;
            	}
            }

            //STRATEGY_DRONE_CONTAIN = 1;
            //STRATEGY_TANKS_AND_SOLDIERS = 2;
            //STRATEGY_DRONE_SWARM = 3;
            //STRATEGY_TANKS_AND_LAUNCHERS = 4;
            //STRATEGY_LAUNCHERS = 5;
            //STRATEGY_TANK_SWARM = 6;
            //strategy = 1;
            rc.broadcast(smuIndices.STRATEGY, strategy);
            hasChosenStrategyPrior = true;
        }

        public void computeStrategy() throws GameActionException{
        	if (prevStrategy == strategy) {
        		return;
        	}
            // [desiredNumOf, roundToBuild, roundToFinish]
            int[] strategyAEROSPACELAB = new int[3];
            int[] strategyBARRACKS = new int[3];
            int[] strategyBASHER = new int[3];
            int[] strategyBEAVER = new int[3];
            int[] strategyCOMMANDER = new int[3];
            int[] strategyCOMPUTER = new int[3];
            int[] strategyDRONE = new int[3];
            int[] strategyHANDWASHSTATION = new int[3];
            int[] strategyHELIPAD = new int[3];
            int[] strategyHQ = new int[3];
            int[] strategyLAUNCHER = new int[3];
            int[] strategyMINER = new int[3];
            int[] strategyMINERFACTORY = new int[3];
            int[] strategyMISSILE = new int[3];
            int[] strategySOLDIER = new int[3];
            int[] strategySUPPLYDEPOT = new int[3];
            int[] strategyTANK = new int[3];
            int[] strategyTANKFACTORY = new int[3];
            int[] strategyTECHNOLOGYINSTITUTE = new int[3];
            int[] strategyTOWER = new int[3];
            int[] strategyTRAININGFIELD = new int[3];

            if (strategy == smuConstants.STRATEGY_TANKS_AND_SOLDIERS) {
                System.out.println("COMPUTE STRATEGY: Tanks and Soldiers");
                strategyAEROSPACELAB = new int[] {0};
                strategyBARRACKS = new int[] {100, 300, 650, 800};
                strategyBASHER = new int[] {50, 1200, 1700};
                strategyBEAVER = new int[] {10, 0, 0};
                strategyCOMMANDER = new int[] {0, 0, 0};
                strategyCOMPUTER = new int[] {0, 0, 0};
                strategyDRONE = new int[] {0, 0, 0};
                strategyHANDWASHSTATION = new int[] {1850, 1860, 1870};
                strategyHELIPAD = new int[] {0};
                strategyHQ = new int[] {0};
                strategyLAUNCHER = new int[] {0, 0, 0};
                strategyMINER = new int[] {50, 1, 500};
                strategyMINERFACTORY = new int[] {1, 200};
                strategyMISSILE = new int[] {0, 0, 0};
                strategySOLDIER = new int[] {120, 200, 1200};
//                strategySUPPLYDEPOT = new int[] {700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
                strategySUPPLYDEPOT = new int[] {1000, 1400, 1500};
                strategyTANK = new int[] {20, 1100, 1800};
                strategyTANKFACTORY = new int[] {1000, 1200};
                strategyTECHNOLOGYINSTITUTE = new int[] {0};
                strategyTOWER = new int[] {0};
                strategyTRAININGFIELD = new int[] {0};
            } else if(strategy == smuConstants.STRATEGY_LAUNCHERS){
                System.out.println("COMPUTE STRATEGY: Launchers");
                strategyAEROSPACELAB = new int[] {800, 1000};
                strategyBARRACKS = new int[] {4, 500, 1500};
                strategyBASHER = new int[] {0, 1200, 1700};
                strategyBEAVER = new int[] {10, 0, 0};
                strategyCOMMANDER = new int[] {0, 0, 0};
                strategyCOMPUTER = new int[] {0, 0, 0};
                strategyDRONE = new int[] {0, 0, 0};
                strategyHANDWASHSTATION = new int[] {1850, 1860, 1870};
                strategyHELIPAD = new int[] {1};
                strategyHQ = new int[] {0};
                strategyLAUNCHER = new int[] {20, 1100, 1700};
                strategyMINER = new int[] {30, 1, 500};
                strategyMINERFACTORY = new int[] {2, 1, 250};
                strategyMISSILE = new int[] {0, 0, 0};
                strategySOLDIER = new int[] {120, 200, 1200};
                strategySUPPLYDEPOT = new int[] {700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
                strategyTANK = new int[] {0, 1100, 1800};
                strategyTANKFACTORY = new int[] {0};
                strategyTECHNOLOGYINSTITUTE = new int[] {0};
                strategyTOWER = new int[] {0};
                strategyTRAININGFIELD = new int[] {0};
            } else if(strategy == smuConstants.STRATEGY_DRONE_CONTAIN) {
                System.out.println("COMPUTE STRATEGY: Drone Contain");
                strategyAEROSPACELAB = new int[] {0};
                strategyBARRACKS = new int[] {0};
                strategyBASHER = new int[] {0, 1200, 1700};
                strategyBEAVER = new int[] {10, 0, 100};
                strategyCOMMANDER = new int[] {0, 0, 0};
                strategyCOMPUTER = new int[] {0, 0, 0};
                strategyDRONE = new int[] {50, 100, 1800};
                strategyHANDWASHSTATION = new int[] {1850, 1860, 1870};
                strategyHELIPAD = new int[] {1, 300};
                strategyHQ = new int[] {0};
                strategyLAUNCHER = new int[] {0, 1100, 1700};
                strategyMINER = new int[] {20, 100, 500};
                strategyMINERFACTORY = new int[] {100, 500};
                strategyMISSILE = new int[] {0, 0, 0};
                strategySOLDIER = new int[] {0, 200, 1200};
                strategySUPPLYDEPOT = new int[] {700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
                strategyTANK = new int[] {0, 1100, 1800};
                strategyTANKFACTORY = new int[] {0};
                strategyTECHNOLOGYINSTITUTE = new int[] {0};
                strategyTOWER = new int[] {0};
                strategyTRAININGFIELD = new int[] {0};
            } else if(strategy == smuConstants.STRATEGY_DRONE_SWARM) {
                System.out.println("COMPUTE STRATEGY: Drone Swarm");
                strategyAEROSPACELAB = new int[] {0};
                strategyBARRACKS = new int[] {2, 500, 1500};
                strategyBASHER = new int[] {0, 1200, 1700};
                strategyBEAVER = new int[] {10, 0, 0};
                strategyCOMMANDER = new int[] {0, 0, 0};
                strategyCOMPUTER = new int[] {0, 0, 0};
                strategyDRONE = new int[] {120, 100, 1800};
                strategyHANDWASHSTATION = new int[] {1850, 1860, 1870};
                strategyHELIPAD = new int[] {1, 400};
                strategyHQ = new int[] {0};
                strategyLAUNCHER = new int[] {0, 1100, 1700};
                strategyMINER = new int[] {30, 1, 500};
                strategyMINERFACTORY = new int[] {2, 1, 250};
                strategyMISSILE = new int[] {0, 0, 0};
                strategySOLDIER = new int[] {25, 200, 1200};
                strategySUPPLYDEPOT = new int[] {700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
                strategyTANK = new int[] {0, 0, 0};
                strategyTANKFACTORY = new int[] {0};
                strategyTECHNOLOGYINSTITUTE = new int[] {0};
                strategyTOWER = new int[] {0};
                strategyTRAININGFIELD = new int[] {0};
            } else if(strategy == smuConstants.STRATEGY_TANK_SWARM) {
                System.out.println("COMPUTE STRATEGY: Tank Swarm");
                strategyAEROSPACELAB = new int[] {0};
                strategyBARRACKS = new int[] {2, 100, 1500};
                strategyBASHER = new int[] {0, 1200, 1700};
                strategyBEAVER = new int[] {10, 0, 0};
                strategyCOMMANDER = new int[] {0, 0, 0};
                strategyCOMPUTER = new int[] {0, 0, 0};
                strategyDRONE = new int[] {0, 100, 1800};
                strategyHANDWASHSTATION = new int[] {1850, 1860, 1870};
                strategyHELIPAD = new int[] {00};
                strategyHQ = new int[] {0};
                strategyLAUNCHER = new int[] {0, 0, 0};
                strategyMINER = new int[] {30, 1, 500};
                strategyMINERFACTORY = new int[] {2, 1, 250};
                strategyMISSILE = new int[] {0, 0, 0};
                strategySOLDIER = new int[] {60, 200, 1200};
                strategySUPPLYDEPOT = new int[] {700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
                strategyTANK = new int[] {100, 300, 1800};
                strategyTANKFACTORY = new int[] {200, 400, 600, 800, 1000};
                strategyTECHNOLOGYINSTITUTE = new int[] {0};
                strategyTOWER = new int[] {0};
                strategyTRAININGFIELD = new int[] {0};
            } else if(strategy == smuConstants.STRATEGY_TANKS_AND_LAUNCHERS) {
                System.out.println("COMPUTE STRATEGY: Tanks and Launchers");
                strategyAEROSPACELAB = new int[] {1000, 1200};
                strategyBARRACKS = new int[] {0, 100, 1500};
                strategyBASHER = new int[] {0, 1200, 1700};
                strategyBEAVER = new int[] {10, 0, 0};
                strategyCOMMANDER = new int[] {0, 0, 0};
                strategyCOMPUTER = new int[] {0, 0, 0};
                strategyDRONE = new int[] {0, 100, 1800};
                strategyHANDWASHSTATION = new int[] {1850, 1860, 1870};
                strategyHELIPAD = new int[] {1, 500, 1000};
                strategyHQ = new int[] {0};
                strategyLAUNCHER = new int[] {30, 1100, 1700};
                strategyMINER = new int[] {30, 1, 500};
                strategyMINERFACTORY = new int[] {2, 1, 250};
                strategyMISSILE = new int[] {0, 0, 0};
                strategySOLDIER = new int[] {0, 200, 1200};
                strategySUPPLYDEPOT = new int[] {700, 800, 900, 1000, 1100, 1200, 1300, 1400, 1500};
                strategyTANK = new int[] {100, 800, 1800};
                strategyTANKFACTORY = new int[] {5, 500, 1400};
                strategyTECHNOLOGYINSTITUTE = new int[] {0};
                strategyTOWER = new int[] {0};
                strategyTRAININGFIELD = new int[] {0};
            }
            
            //BE CAREFUL!!!
            //TODO
            int[][] strategyUnitArray = new int[][] {strategyBASHER, strategyBEAVER, strategyCOMMANDER, strategyCOMPUTER, strategyDRONE, strategyLAUNCHER, strategyMINER, strategyMISSILE, strategySOLDIER, strategyTANK};
            int[] channelUnitArray = new int[] {smuIndices.channelBASHER, smuIndices.channelBEAVER, smuIndices.channelCOMMANDER, smuIndices.channelCOMPUTER, smuIndices.channelDRONE, smuIndices.channelLAUNCHER, smuIndices.channelMINER, smuIndices.channelMISSILE, smuIndices.channelSOLDIER, smuIndices.channelTANK};
            int[][] strategyStructureArray = new int[][] {strategyAEROSPACELAB, strategyBARRACKS, strategyHANDWASHSTATION, strategyHELIPAD, strategyHQ, strategyMINERFACTORY, strategySUPPLYDEPOT, strategyTANKFACTORY, strategyTECHNOLOGYINSTITUTE, strategyTOWER, strategyTRAININGFIELD};
            int[] channelStructureArray = new int[] {smuIndices.channelAEROSPACELAB, smuIndices.channelBARRACKS, smuIndices.channelHANDWASHSTATION, smuIndices.channelHELIPAD, smuIndices.channelHQ, smuIndices.channelMINERFACTORY, smuIndices.channelSUPPLYDEPOT, smuIndices.channelTANKFACTORY, smuIndices.channelTECHNOLOGYINSTITUTE, smuIndices.channelTOWER, smuIndices.channelTRAININGFIELD};

            //Broadcast unit strategies
            for (int i = 1; i < strategyUnitArray.length; i++) {
                rc.broadcast(channelUnitArray[i] + 0, 0);
                rc.broadcast(channelUnitArray[i] + 1, strategyUnitArray[i][0]);
                rc.broadcast(channelUnitArray[i] + 2, strategyUnitArray[i][1]);
                rc.broadcast(channelUnitArray[i] + 3, strategyUnitArray[i][2]);
            }
            
            //Broadcast structure strategies
            for (int i = 1; i < strategyStructureArray.length; i++) {
                rc.broadcast(channelStructureArray[i] + 0, 0);
                if (strategyStructureArray[i].length > 1) {
                    rc.broadcast(channelStructureArray[i] + 1, strategyStructureArray[i].length);
                    for (int j = 0; j < strategyStructureArray[i].length; j++) {
                        rc.broadcast(channelStructureArray[i] + j + 2, strategyStructureArray[i][j]);
                    }
                } else {
                    rc.broadcast(channelStructureArray[i] + 1, 0);
                    rc.broadcast(channelStructureArray[i] + 2, 0);
                }
            }

            prevStrategy = strategy;
        }

        
        public void saveTeamMemory() {
        	long[] teamMemory = rc.getTeamMemory();;
        	int currRound = Clock.getRoundNum();
        	if (analyzedTowers && teamMemory[smuTeamMemoryIndices.PREV_MAP_TOWER_THREAT] == 0) {
        		rc.setTeamMemory(smuTeamMemoryIndices.PREV_MAP_TOWER_THREAT, towerThreat);;
        	}
        	if (isFinishedAnalyzing && teamMemory[smuTeamMemoryIndices.PREV_MAP_VOID_TYPE_PERCENT] == 0) {
        		rc.setTeamMemory(smuTeamMemoryIndices.PREV_MAP_VOID_TYPE_PERCENT, (long) (ratio * 100));
        	}
        	rc.setTeamMemory(smuTeamMemoryIndices.NUM_ROUNDS_USING_STRATEGY_BASE + strategy, teamMemory[smuTeamMemoryIndices.NUM_ROUNDS_USING_STRATEGY_BASE + strategy] + 1);
        	if (teamMemory[smuTeamMemoryIndices.ROUND_OUR_HQ_ATTACKED] == 0) {
        		if (rc.getHealth() < RobotType.HQ.maxHealth) {
        			rc.setTeamMemory(smuTeamMemoryIndices.ROUND_OUR_HQ_ATTACKED, currRound);
        		}
        	}
        	int currNumTowers = rc.senseTowerLocations().length;
        	if (currNumTowers < numTowers) {
        		int towersIndex =  0;
        		while (teamMemory[smuTeamMemoryIndices.ROUND_OUR_TOWER_DESTROYED_BASE + towersIndex] != 0) {
        			towersIndex++;
        		}
        		for (int i = 0; i < numTowers - currNumTowers; i++) {
        			rc.setTeamMemory(smuTeamMemoryIndices.ROUND_OUR_TOWER_DESTROYED_BASE + towersIndex + i, currRound);
        		}
        		numTowers = currNumTowers;
        	}
        	int currNumEnemyTowers = rc.senseEnemyTowerLocations().length;
        	if (currNumEnemyTowers < numEnemyTowers) {
        		int towersIndex = 0;
        		while (teamMemory[smuTeamMemoryIndices.ROUND_THEIR_TOWER_DESTROYED_BASE + towersIndex] != 0) {
        			towersIndex++;
        		}
        		for (int i = 0; i < numEnemyTowers - currNumEnemyTowers; i++) {
        			rc.setTeamMemory(smuTeamMemoryIndices.ROUND_THEIR_TOWER_DESTROYED_BASE + towersIndex + i, currRound);
        		}
        		numEnemyTowers = currNumEnemyTowers;
        	}
        }
        
        public RobotType checkContainment() throws GameActionException {
        	RobotInfo[] enemyRobotsContaining = rc.senseNearbyRobots(50, theirTeam);
        	if (enemyRobotsContaining.length > 4) {
        		rc.broadcast(smuIndices.HQ_BEING_CONTAINED, smuConstants.CURRENTLY_BEING_CONTAINED);
        		int robotTypeContaining = getMajorityRobotType(enemyRobotsContaining);
        		rc.broadcast(smuIndices.HQ_BEING_CONTAINED_BY, robotTypeContaining);
        		return IntToRobotType(robotTypeContaining);
        	} else {
        		rc.broadcast(smuIndices.HQ_BEING_CONTAINED, smuConstants.NOT_CURRENTLY_BEING_CONTAINED);
        		return null;
        	}
        }
        
        public int getMajorityRobotType(RobotInfo[] enemyRobots) {
        	int[] enemyRobotTypes = new int[22]; //Max RobotType -> int value + 1  
        	int highestValue = 0;
    		int highestValueIndex = -1;
    		for (int i = 0; i < enemyRobots.length; i++) {
    			int robotType = RobotTypeToInt(enemyRobots[i].type);
    			enemyRobotTypes[robotType] = enemyRobotTypes[robotType] + 1;
    			if (enemyRobotTypes[robotType] > highestValue) {
    				highestValue = enemyRobotTypes[robotType];
    				highestValueIndex = i;
    			}
    		}
    		return highestValueIndex;
        }
        
        public void setRallyPoint() throws GameActionException {
        	MapLocation rallyPoint = null;
        	RobotType beingContained = checkContainment();
            if (beingContained == null) {
            	if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
            		MapLocation[] ourTowers = rc.senseTowerLocations();
            		if (ourTowers != null && ourTowers.length > 0) {
            			int closestTower = -1;
            			int closestDistanceToEnemyHQ = Integer.MAX_VALUE;
            			for (int i = 0; i < ourTowers.length; i++) {
            				int currDistanceToEnemyHQ = ourTowers[i].distanceSquaredTo(theirHQ);
            				if (currDistanceToEnemyHQ < closestDistanceToEnemyHQ) {
            					closestDistanceToEnemyHQ = currDistanceToEnemyHQ;
            					closestTower = i;
            				}
            			}
            			rallyPoint = ourTowers[closestTower].add(ourTowers[closestTower].directionTo(theirHQ), 2);
            		}
            	} else {
            		MapLocation[] enemyTowers = rc.senseEnemyTowerLocations();
            		if (enemyTowers == null || enemyTowers.length <= smuConstants.numTowersRemainingToAttackHQ) {
            			rallyPoint = rc.senseEnemyHQLocation();
            		} else {
            			rallyPoint = enemyTowers[0];
            		}
            	}
            } else {
        		rallyPoint = getSecondBaseLocation();
        		if (rallyPoint != null) {
        			rallyPoint = rallyPoint.add(rallyPoint.directionTo(theirHQ), 8);
        		}
            }
            if (rallyPoint != null) {
            	rc.broadcast(smuIndices.RALLY_POINT_X, rallyPoint.x);
            	rc.broadcast(smuIndices.RALLY_POINT_Y, rallyPoint.y);
            }
        }
        
        public void execute() throws GameActionException {
            spawnOptimally();
            setRallyPoint();
            attackLeastHealthEnemyInRange();
            transferSupplies();
            
            if (!isFinishedAnalyzing) {
            	analyzeMap();
            	if (!analyzedTowers) {
            		analyzeTowers();
            	}
            } else {
            	if (!analyzedPrevMatch) analyzePreviousMatch();
            	chooseStrategy();
            	try {
            		computeStrategy();
            	} catch (GameActionException e) {
            		e.printStackTrace();
            	}
            }
            
            if (analyzedPrevMatch) saveTeamMemory();
            rc.yield();
        }
    }

    //BEAVER
    public static class Beaver extends BaseBot {
        public MapLocation secondBase;
    	
        
    	public Beaver(RobotController rc) {
    		super(rc);
    		
    		Random rand = new Random(rc.getID());
    		if (rand.nextDouble() < smuConstants.percentBeaversToGoToSecondBase) {
    			secondBase = getSecondBaseLocation();
    		}
        }

        public void execute() throws GameActionException {
        	if (secondBase != null && rc.getLocation().distanceSquaredTo(secondBase) > 6) {
        		goToLocation(secondBase);
        	}
        	
            buildOptimally();
            transferSupplies();
            if (Clock.getRoundNum() > 400) defend();
            if (rc.isCoreReady()) {
                //mine
                if (rc.senseOre(rc.getLocation()) > 0) {
                    rc.mine();
                }
                else {
                    moveOptimally();
                }
            }               
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
    		spawnOptimally();
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
    		if (Clock.getRoundNum()>smuConstants.roundToFormSupplyConvoy 
    				&& rc.readBroadcast(smuIndices.HQ_BEING_CONTAINED) != smuConstants.CURRENTLY_BEING_CONTAINED) {
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
    		} else if(!defendSelf()) {
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
    		spawnOptimally();
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
          		if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
          			contain();
          		} else {
          			moveToRallyPoint();
          		}
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
      		if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
      			contain();
      		} else {
      			moveToRallyPoint();
      		}
            transferSupplies();
            rc.yield();
        }
    }
    
    //TANK
    public static class Tank extends BaseBot {
        public Tank(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            if (!defend()) {
          		if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
          			contain();
          		} else {
          			moveToRallyPoint();
          		}
            }
            transferSupplies();
            rc.yield();
        }
    }

    //DRONE
    public static class Drone extends BaseBot {
        public Drone(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
        	if (!defendSelf()) {
        		if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
        			contain();
        		} else {
        			moveToRallyPoint();
        		}
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
        	transferSupplies();
            attackLeastHealthEnemyInRange();
            rc.yield();
        }
    }

    //SUPPLYDEPOT
    public static class Supplydepot extends BaseBot {
        public Supplydepot(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            rc.yield();
        }
    }
    
    //HANDWASHSTATION
    public static class Handwashstation extends BaseBot {
        public Handwashstation(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            rc.yield();
        }
    }
    
    //TANKFACTORY
    public static class Tankfactory extends BaseBot {
        public Tankfactory(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            transferSupplies();
            spawnOptimally();
            rc.yield();
        }
    }
    
    //HELIPAD
    public static class Helipad extends BaseBot {
        public Helipad(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            transferSupplies();
            spawnOptimally();
            rc.yield();
        }
    }
    
    //AEROSPACELAB
    public static class Aerospacelab extends BaseBot {
        public Aerospacelab(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
            transferSupplies();
            spawnOptimally();
            rc.yield();
        }
    }
    
    //LAUNCHER
    public static class Launcher extends BaseBot {
        public Launcher(RobotController rc) {
            super(rc);
        }

        public void execute() throws GameActionException {
        	boolean launched = false;
            if (rc.getMissileCount() > 0) {
                Direction targetDir = getDirOfLauncherTarget();
                if (targetDir != null && rc.isWeaponReady()){
                    if (rc.canLaunch(targetDir)){
                        rc.launchMissile(targetDir);
                        launched = true;
                    }
                }
            }
            if (!launched) {
            	contain();
            }
            rc.yield();
        }
    }
    
    //MISSILE
    public static class Missile extends BaseBot {
        public Direction dirToTarget = null;
        public Missile(RobotController rc) {
            super(rc);
            
        }

        public void execute() throws GameActionException {
            if (dirToTarget == null) {
                RobotInfo[] nearbyRobots = rc.senseNearbyRobots(2, myTeam);
                for (RobotInfo robot : nearbyRobots) {
                    if (robot.type == RobotType.LAUNCHER) {
                        dirToTarget = rc.getLocation().directionTo(robot.location).opposite();
                    }
                }
//                //System.out.println("dirToTarget: "+dirToTarget.name());
            }
            if (rc.isCoreReady() && rc.canMove(dirToTarget)){
                rc.move(dirToTarget);
            }
//            if (getTeammatesInAttackRange().length <= 1 && getTeammatesInAttackRange().length > 4){
//                rc.explode();
//            }
            rc.yield();
        }
    }
}
