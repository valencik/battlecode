package team353;

import battlecode.common.*;

import java.util.*;

public class RobotPlayer {

	public static class smuConstants {
		public static int roundToBuildAEROSPACELAB = 2000;
		public static int roundToBuildBARRACKS = 500;
		public static int roundToBuildBASHER = 1200;
		public static int roundToBuildBEAVER = 0;
		public static int roundToBuildCOMMANDER = 2000;
		public static int roundToBuildCOMPUTER = 2000;
		public static int roundToBuildDRONE = 2000;
		public static int roundToBuildHANDWASHSTATION = 1700;
		public static int roundToBuildHELIPAD = 2000;
		public static int roundToBuildLAUNCHER = 2000;
		public static int roundToBuildMINER = 1;
		public static int roundToBuildMINERFACTORY = 100;
		public static int roundToBuildMISSILE = 2000;
		public static int roundToBuildSOLDIER = 50;
		public static int roundToBuildSUPPLYDEPOT = 800;
		public static int roundToBuildTANK = 2000;
		public static int roundToBuildTANKFACTORY = 2000;
		public static int roundToBuildTECHNOLOGYINSTITUTE = 2000;
		public static int roundToBuildTRAININGFIELD = 2000;
		
		public static int roundToFinishAEROSPACELAB = 2000;
		public static int roundToFinishBARRACKS = 1500;
		public static int roundToFinishHANDWASHSTATION = 2000;
		public static int roundToFinishHELIPAD = 2000;
		public static int roundToFinishMINERFACTORY = 2000;
		public static int roundToFinishSUPPLYDEPOT = 1200;
		public static int roundToFinishTANKFACTORY = 2000;
		public static int roundToFinishTECHNOLOGYINSTITUTE = 2000;
		public static int roundToFinishTRAININGFIELD = 2000;
		
		public static int desiredNumOfAEROSPACELAB = 0;
		public static int desiredNumOfBARRACKS = 4;
		public static int desiredNumOfBASHER = 50;
		public static int desiredNumOfBEAVER = 15;
		public static int desiredNumOfCOMMANDER = 0;
		public static int desiredNumOfCOMPUTER = 0;
		public static int desiredNumOfDRONE = 0;
		public static int desiredNumOfHANDWASHSTATION = 3;
		public static int desiredNumOfHELIPAD = 0;
		public static int desiredNumOfLAUNCHER = 0;
		public static int desiredNumOfMINER = 70;
		public static int desiredNumOfMINERFACTORY = 2;
		public static int desiredNumOfMISSILE = 0;
		public static int desiredNumOfSOLDIER = 200;
		public static int desiredNumOfSUPPLYDEPOT = 4;
		public static int desiredNumOfTANK = 0;
		public static int desiredNumOfTANKFACTORY = 0;
		public static int desiredNumOfTECHNOLOGYINSTITUTE = 0;
		public static int desiredNumOfTRAININGFIELD = 0;
		
		
		public static int roundToLaunchAttack = 1600;
		public static int roundToFormSupplyConvoy = 50; // roundToBuildSOLDIERS;
		public static int RADIUS_FOR_SUPPLY_CONVOY = 2;
		public static int numTowersRemainingToAttackHQ = 1;
		
		public static int currentOreGoal = 100;
		
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
	}
	

	public static class smuIndices {
		public static int RALLY_POINT_X = 0;
		public static int RALLY_POINT_Y = 1;
		
		//Economy
		public static int freqCurrentlySavingOre = 10;
		
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
		
		public static int TOWER_HOLES_BEGIN = 2000;
	}
	
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
        } else if (rc.getType() == RobotType.SUPPLYDEPOT) {
            myself = new Supplydepot(rc);
        } else if (rc.getType() == RobotType.HANDWASHSTATION) {
            myself = new Handwashstation(rc);
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
                    //System.out.println("Could not find valid spawn location!");
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
                    //System.out.println("Could not find valid build location!");
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

                Direction newDir = getMoveDir(rallyPoint);

                if (newDir != null) {
                    rc.move(newDir);
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
                
                //System.out.println("No suitable direction found!");
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
                break;
            case LAUNCHER:
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
                //System.out.println(myType + " wants to spawn with " + ore);
                
                //Check if we are currently saving ore
                if (rc.readBroadcast(smuIndices.freqCurrentlySavingOre) == 1){
                    //System.out.println("We are saving, can't spawn.");
                    return;
                }

                switch(myType){

                case BARRACKS:                    
                    if (round > smuConstants.roundToBuildSOLDIER && rc.readBroadcast(smuIndices.freqNumSOLDIER) < smuConstants.desiredNumOfSOLDIER && ore > 60){
                        spawnType = RobotType.SOLDIER;
                        break;
                    } else if (round > smuConstants.roundToBuildBASHER && rc.readBroadcast(smuIndices.freqNumBASHER) < smuConstants.desiredNumOfBASHER && ore > 80){
                        spawnType = RobotType.BASHER;
                        break;
                    }
                    return;
                case HQ:
                    if (round > smuConstants.roundToBuildBEAVER && rc.readBroadcast(smuIndices.freqNumBEAVER) < smuConstants.desiredNumOfBEAVER && ore > 100){
                        spawnType = RobotType.BEAVER;
                        break;
                    }
                    return;
                case HELIPAD:
                    if (round > smuConstants.roundToBuildDRONE && rc.readBroadcast(smuIndices.freqNumDRONE) < smuConstants.desiredNumOfDRONE && ore > 125){
                        spawnType = RobotType.DRONE;
                        break;
                    }
                    return;
                case AEROSPACELAB:
                    if (round > smuConstants.roundToBuildLAUNCHER && rc.readBroadcast(smuIndices.freqNumLAUNCHER) < smuConstants.desiredNumOfLAUNCHER && ore > 400){
                        spawnType = RobotType.LAUNCHER;
                        break;
                    }
                    return;
                case MINERFACTORY:
                    if (round > smuConstants.roundToBuildMINER && rc.readBroadcast(smuIndices.freqNumMINER) < smuConstants.desiredNumOfMINER && ore > 50){
                        spawnType = RobotType.MINER;
                        break;
                    }
                    return;
                case TANKFACTORY:
                    if (round > smuConstants.roundToBuildTANK && rc.readBroadcast(smuIndices.freqNumTANK) < smuConstants.desiredNumOfTANK && ore > 250){
                        spawnType = RobotType.TANK;
                        break;
                    }
                    return;
                case TECHNOLOGYINSTITUTE:
                    if (round > smuConstants.roundToBuildCOMPUTER && rc.readBroadcast(smuIndices.freqNumCOMPUTER) < smuConstants.desiredNumOfCOMPUTER && ore > 10){
                        spawnType = RobotType.COMPUTER;
                        break;
                    }
                    return;
                case TRAININGFIELD:
                    if (round > smuConstants.roundToBuildCOMMANDER && rc.readBroadcast(smuIndices.freqNumCOMMANDER) < smuConstants.desiredNumOfCOMMANDER && ore > 100){
                        spawnType = RobotType.COMMANDER;
                        break;
                    }
                    return;

                default:
                    //System.out.println("ERRROR in spawnUnit()!");
                    return;
                }

                //Get a direction and then actually spawn the unit.
                Direction randomDir = getSpawnDir(spawnType);
                if(rc.isCoreReady()&& randomDir != null){
                    //System.out.println("Spawning... - " + myType);
                    rc.spawn(randomDir, spawnType);
                    incrementCount(spawnType); 
                }
            } //isCoreReady
        }
        
        //Spawns unit based on calling type. Performs all checks.
        public void buildOptimally() throws GameActionException {
            if (rc.isCoreReady()){
                int round = Clock.getRoundNum();
                double ore = rc.getTeamOre();

                if (round > smuConstants.roundToBuildAEROSPACELAB && rc.readBroadcast(smuIndices.freqNumAEROSPACELAB) < smuConstants.desiredNumOfAEROSPACELAB){
                    //We don't have as many Barracks as we want...
                    if (ore > 500){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.AEROSPACELAB);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildAEROSPACELAB + (smuConstants.roundToFinishAEROSPACELAB - smuConstants.roundToBuildAEROSPACELAB) / smuConstants.desiredNumOfAEROSPACELAB * rc.readBroadcast(smuIndices.freqNumAEROSPACELAB))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildBARRACKS && rc.readBroadcast(smuIndices.freqNumBARRACKS) < smuConstants.desiredNumOfBARRACKS){
                    //We don't have as many Barracks as we want...
                    if (ore > 300){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.BARRACKS);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildBARRACKS + (smuConstants.roundToFinishBARRACKS - smuConstants.roundToBuildBARRACKS) / smuConstants.desiredNumOfBARRACKS * rc.readBroadcast(smuIndices.freqNumBARRACKS))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            //System.out.println("Saving Ore!!!!");
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildHANDWASHSTATION && rc.readBroadcast(smuIndices.freqNumHANDWASHSTATION) < smuConstants.desiredNumOfHANDWASHSTATION){
                    //We don't have as many Barracks as we want...
                    if (ore > 200){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.HANDWASHSTATION);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildHANDWASHSTATION + (smuConstants.roundToFinishHANDWASHSTATION - smuConstants.roundToBuildHANDWASHSTATION) / smuConstants.desiredNumOfHANDWASHSTATION * rc.readBroadcast(smuIndices.freqNumHANDWASHSTATION))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildHELIPAD && rc.readBroadcast(smuIndices.freqNumHELIPAD) < smuConstants.desiredNumOfHELIPAD){
                    //We don't have as many Barracks as we want...
                    if (ore > 300){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.HELIPAD);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildHELIPAD + (smuConstants.roundToFinishHELIPAD - smuConstants.roundToBuildHELIPAD) / smuConstants.desiredNumOfHELIPAD * rc.readBroadcast(smuIndices.freqNumHELIPAD))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildMINERFACTORY && rc.readBroadcast(smuIndices.freqNumMINERFACTORY) < smuConstants.desiredNumOfMINERFACTORY){
                    //We don't have as many Barracks as we want...
                    if (ore > 500){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.MINERFACTORY);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildMINERFACTORY + (smuConstants.roundToFinishMINERFACTORY - smuConstants.roundToBuildMINERFACTORY) / smuConstants.desiredNumOfMINERFACTORY * rc.readBroadcast(smuIndices.freqNumMINERFACTORY))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildSUPPLYDEPOT && rc.readBroadcast(smuIndices.freqNumSUPPLYDEPOT) < smuConstants.desiredNumOfSUPPLYDEPOT){
                    //We don't have as many Barracks as we want...
                    if (ore > 100){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.SUPPLYDEPOT);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildSUPPLYDEPOT + (smuConstants.roundToFinishSUPPLYDEPOT - smuConstants.roundToBuildSUPPLYDEPOT) / smuConstants.desiredNumOfSUPPLYDEPOT * rc.readBroadcast(smuIndices.freqNumSUPPLYDEPOT))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildTANKFACTORY && rc.readBroadcast(smuIndices.freqNumTANKFACTORY) < smuConstants.desiredNumOfTANKFACTORY){
                    //We don't have as many Barracks as we want...
                    if (ore > 500){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.TANKFACTORY);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildTANKFACTORY + (smuConstants.roundToFinishTANKFACTORY - smuConstants.roundToBuildTANKFACTORY) / smuConstants.desiredNumOfTANKFACTORY * rc.readBroadcast(smuIndices.freqNumTANKFACTORY))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildTECHNOLOGYINSTITUTE && rc.readBroadcast(smuIndices.freqNumTECHNOLOGYINSTITUTE) < smuConstants.desiredNumOfTECHNOLOGYINSTITUTE){
                    //We don't have as many Barracks as we want...
                    if (ore > 200){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.TECHNOLOGYINSTITUTE);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildTECHNOLOGYINSTITUTE + (smuConstants.roundToFinishTECHNOLOGYINSTITUTE - smuConstants.roundToBuildTECHNOLOGYINSTITUTE) / smuConstants.desiredNumOfTECHNOLOGYINSTITUTE * rc.readBroadcast(smuIndices.freqNumTECHNOLOGYINSTITUTE))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
                if (round > smuConstants.roundToBuildTRAININGFIELD && rc.readBroadcast(smuIndices.freqNumTRAININGFIELD) < smuConstants.desiredNumOfTRAININGFIELD){
                    //We don't have as many Barracks as we want...
                    if (ore > 200){
                        rc.broadcast(smuIndices.freqCurrentlySavingOre, 0);
                        buildUnit(RobotType.TRAININGFIELD);
                        return;
                    } else {
                        if (round > (smuConstants.roundToBuildTRAININGFIELD + (smuConstants.roundToFinishTRAININGFIELD - smuConstants.roundToBuildTRAININGFIELD) / smuConstants.desiredNumOfTRAININGFIELD * rc.readBroadcast(smuIndices.freqNumTRAININGFIELD))) {
                            rc.broadcast(smuIndices.freqCurrentlySavingOre, 1);
                            return;
                        }
                    }
                }
            }
        }

        //TODO copy the work done on spawnUnit()
        public void buildUnit(RobotType type) throws GameActionException {
            Direction randomDir = getBuildDir(type);
            if(rc.isCoreReady()&& randomDir != null){
                rc.build(randomDir, type);
                incrementCount(type);
            }
        }

        //TODO find a way to deal with deaths.
        public void incrementCount(RobotType type) throws GameActionException {
            switch(type){
            case AEROSPACELAB:
                rc.broadcast(smuIndices.freqNumAEROSPACELAB, rc.readBroadcast(smuIndices.freqNumAEROSPACELAB)+1);
                break;
            case BARRACKS:
                rc.broadcast(smuIndices.freqNumBARRACKS, rc.readBroadcast(smuIndices.freqNumBARRACKS)+1);
                break;
            case BASHER:
                rc.broadcast(smuIndices.freqNumBASHER, rc.readBroadcast(smuIndices.freqNumBASHER)+1);
                break;
            case BEAVER:
                rc.broadcast(smuIndices.freqNumBEAVER, rc.readBroadcast(smuIndices.freqNumBEAVER)+1);
                break;
            case COMMANDER:
                rc.broadcast(smuIndices.freqNumCOMMANDER, rc.readBroadcast(smuIndices.freqNumCOMMANDER)+1);
                break;
            case COMPUTER:
                rc.broadcast(smuIndices.freqNumCOMPUTER, rc.readBroadcast(smuIndices.freqNumCOMPUTER)+1);
                break;
            case DRONE:
                rc.broadcast(smuIndices.freqNumDRONE, rc.readBroadcast(smuIndices.freqNumDRONE)+1);
                break;
            case HANDWASHSTATION:
                rc.broadcast(smuIndices.freqNumHANDWASHSTATION, rc.readBroadcast(smuIndices.freqNumHANDWASHSTATION)+1);
                break;
            case HELIPAD:
                rc.broadcast(smuIndices.freqNumHELIPAD, rc.readBroadcast(smuIndices.freqNumHELIPAD)+1);
                break;
            case LAUNCHER:
                rc.broadcast(smuIndices.freqNumLAUNCHER, rc.readBroadcast(smuIndices.freqNumLAUNCHER)+1);
                break;
            case MINER:
                rc.broadcast(smuIndices.freqNumMINER, rc.readBroadcast(smuIndices.freqNumMINER)+1);
                break;
            case MINERFACTORY:
                rc.broadcast(smuIndices.freqNumMINERFACTORY, rc.readBroadcast(smuIndices.freqNumMINERFACTORY)+1);
                break;
            case MISSILE:
                rc.broadcast(smuIndices.freqNumMISSILE, rc.readBroadcast(smuIndices.freqNumMISSILE)+1);
                break;
            case SOLDIER:
                rc.broadcast(smuIndices.freqNumSOLDIER, rc.readBroadcast(smuIndices.freqNumSOLDIER)+1);
                break;
            case SUPPLYDEPOT:
                rc.broadcast(smuIndices.freqNumSUPPLYDEPOT, rc.readBroadcast(smuIndices.freqNumSUPPLYDEPOT)+1);
                break;
            case TANK:
                rc.broadcast(smuIndices.freqNumTANK, rc.readBroadcast(smuIndices.freqNumTANK)+1);
                break;
            case TANKFACTORY:
                rc.broadcast(smuIndices.freqNumTANKFACTORY, rc.readBroadcast(smuIndices.freqNumTANKFACTORY)+1);
                break;
            case TECHNOLOGYINSTITUTE:
                rc.broadcast(smuIndices.freqNumTECHNOLOGYINSTITUTE, rc.readBroadcast(smuIndices.freqNumTECHNOLOGYINSTITUTE)+1);
                break;
            case TRAININGFIELD:
                rc.broadcast(smuIndices.freqNumTRAININGFIELD, rc.readBroadcast(smuIndices.freqNumTRAININGFIELD)+1);
                break;
            default:
                //System.out.println("ERRROR!");
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
        		if (roundStart == Clock.getRoundNum() && transferAmount > 0) {
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
        	RobotInfo minerAtEdge = getUnitAtEdgeOfSupplyRangeOf(RobotType.MINER, myHQ);
        	if (minerAtEdge != null && minerAtEdge.ID == rc.getID()) {
        		return true;
        	} else if (minerAtEdge == null){
            	goToLocation(myHQ.add(myHQ.directionTo(theirHQ), smuConstants.RADIUS_FOR_SUPPLY_CONVOY));
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
	            		goToLocation(previousMiner.location.add(previousMiner.location.directionTo(theirHQ), smuConstants.RADIUS_FOR_SUPPLY_CONVOY));
	            		return true;
	            	}
	            }
            } catch (GameActionException e) {
	            e.printStackTrace();
            }
        	return false;
        }
        
        public RobotInfo getUnitAtEdgeOfSupplyRangeOf(RobotType unitType, MapLocation startLocation) {
        	MapLocation locationInChain =  startLocation.add(myHQ.directionTo(theirHQ), smuConstants.RADIUS_FOR_SUPPLY_CONVOY);
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
        
        public boolean defendTowerHoles() {
        	int towerHoleX = -1;
			try {
				towerHoleX = rc.readBroadcast(smuIndices.TOWER_HOLES_BEGIN);
			} catch (GameActionException e1) {
				e1.printStackTrace();
			}
			boolean defendingHole = false;
			if(towerHoleX != -1) {
				int towerHolesIndex = smuIndices.TOWER_HOLES_BEGIN;
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
							if (nearbyTeammates.length < smuConstants.NUM_HOLE_PROTECTORS && rc.getLocation().distanceSquaredTo(holeLocation) <= smuConstants.DISTANCE_TO_START_PROTECTING_SQUARED) {
								defendingHole = true;
								towerHoleX = -1;
								goToLocation(holeLocation);    									
							}
						}
					} catch (GameActionException e) {
						e.printStackTrace();
					}
				} while(towerHoleX != -1);
			}
			return defendingHole;
        }
        
        public boolean defendTowers() {
        	MapLocation[] myTowers = rc.senseTowerLocations();
			MapLocation closestTower = null;
            try {
                closestTower = getRallyPoint();
            } catch (GameActionException e) {
                e.printStackTrace();
            }
			int closestDist = Integer.MAX_VALUE;
			for (MapLocation tower : myTowers) {
				RobotInfo[] nearbyRobots = getTeammatesNearTower(tower);
				if (nearbyRobots.length < smuConstants.NUM_TOWER_PROTECTORS && rc.getLocation().distanceSquaredTo(tower) <= smuConstants.DISTANCE_TO_START_PROTECTING_SQUARED) { //tower underprotected
					int dist = tower.distanceSquaredTo(theirHQ);
					if (dist < closestDist) {
						closestDist = dist;
						closestTower = tower;
					}				
				}
			}
			goToLocation(closestTower);
			return true;
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

    		if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
    			// B1, Protect Holes Between Towers
    			boolean isProtectingHoles = defendTowerHoles();
    			if (isProtectingHoles) {
    				return true;
    			}
    			
    			if(myType != RobotType.BEAVER && myType != RobotType.MINER) {
    				// B2, Protect Towers
    				boolean isProtectingTowers = defendTowers();
    				if (isProtectingTowers) {
    					return true;
    				}
    			}
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
    		RobotInfo[] nearbyRobots = rc.senseNearbyRobots(smuConstants.PROTECT_OTHERS_RANGE);
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
    			//System.out.println("Tower " + i + " overlapped " + overlapped[i] + " " + towerLocations[i]);
    			if (overlapped[i] < 2) {
    				try {
    					int towerAttackRadius = (int) Math.sqrt(RobotType.TOWER.attackRadiusSquared) + 1;
    					if (!coveredLeft) {
    						//System.out.println("Tower " + towerLocations[i] + " Not covered left");
    						rc.broadcast(holesBroadcastIndex, towerLocations[i].x - towerAttackRadius);
    						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y);
    						holesBroadcastIndex+=2;
    					}
    					if (!coveredRight) {
    						//System.out.println("Tower " + towerLocations[i] + " Not covered right");
    						rc.broadcast(holesBroadcastIndex, towerLocations[i].x + towerAttackRadius);
    						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y);
    						holesBroadcastIndex+=2;
    					}
    					if (!coveredTop) {
    						//System.out.println("Tower " + towerLocations[i] + " Not covered top");
    						rc.broadcast(holesBroadcastIndex, towerLocations[i].x);
    						rc.broadcast(holesBroadcastIndex + 1, towerLocations[i].y - towerAttackRadius);
    						holesBroadcastIndex+=2;
    					}
    					if (!coveredBottom) {
    						//System.out.println("Tower " + towerLocations[i] + " Not covered bottom");
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
    		
    		//System.out.println("BYTEEND on " + Clock.getRoundNum() + ": " + Clock.getBytecodeNum());
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
            if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
                rallyPoint = new MapLocation( (this.myHQ.x + this.theirHQ.x) / 2,
                                              (this.myHQ.y + this.theirHQ.y) / 2);
            }
            else {
            	MapLocation[] enemyTowers = rc.senseEnemyTowerLocations();
                if (enemyTowers == null || enemyTowers.length <= smuConstants.numTowersRemainingToAttackHQ) {
                	rallyPoint = rc.senseEnemyHQLocation();
                } else {
                	rallyPoint = enemyTowers[0];
                }
            }
            rc.broadcast(smuIndices.RALLY_POINT_X, rallyPoint.x);
            rc.broadcast(smuIndices.RALLY_POINT_Y, rallyPoint.y);
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

            //rc.setIndicatorString(1, "dist:" + getDistanceSquared(this.myHQ));
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
    		if (Clock.getRoundNum()>smuConstants.roundToFormSupplyConvoy) {
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
    		boolean hasTakenAction = false;
        	if (Clock.getRoundNum() < smuConstants.roundToLaunchAttack) {
    			hasTakenAction = defendTowerHoles();
        		if (!hasTakenAction) {
    				hasTakenAction = defendTowers();
    			}
    		}
        	if (!hasTakenAction) {
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

}
