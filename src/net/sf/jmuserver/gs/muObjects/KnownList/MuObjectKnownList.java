///*
// * To change this template, choose Tools | Templates
// * and open the template in the editor.
// */
//package net.sf.jmuserver.gs.muObjects.KnownList;
//
//import java.util.Collection;
//import java.util.Map;
//import javolution.util.FastMap;
//import net.sf.jmuserver.gs.muObjects.MuObject;
//import net.sf.jmuserver.gs.muObjects.MuCharacter;
//import net.sf.jmuserver.gs.muObjects.MuPcInstance;
//
///**
// *
// * @author Miki i Linka
// */
//public class MuObjectKnownList {
//
//    private MuObject _activeObject;
//    private Map<Integer, MuObject> _knownsObiects;
//
//    public MuObjectKnownList(MuObject active) {
//        _activeObject = active;
//    }
//
//    public boolean checkInRage(MuObject t) {
//        int chx = t.getX() / 5;
//        int chy = t.getY() / 5;
//        int myx = _activeObject.getX() / 5;
//        int myy = _activeObject.getY() / 5;
//        int rangeX1 = myx - 3;
//        int rangeX2 = myx + 3;
//        int rangeY1 = myy - 3;
//        int rangeY2 = myy + 3;
//        if ((rangeX1 <= chx) && (chx <= rangeX2) && (rangeY1 <= chy) && (chy <= rangeY2)) {
//            return true;
//        } else {
//            return false;
//        }
//    }
//
//    /**
//     * 
//     * @param object to check
//     * @return true objectis  in knownslist
//     */
//    public final boolean knowsObject(MuObject object) {
//        return getActiveObject() == object || getKnownObjects().containsKey(object.getObjectId());
//    }
//
//    /**
//     * 
//     * @return the Active Obiect 'holder of knowns  list'
//     */
//    public MuObject getActiveObject() {
//        return _activeObject;
//    }
//
//    /**
//     * @return the knowns map
//     */
//    public Map<Integer, MuObject> getKnownObjects() {
//        if (_knownsObiects == null) {
//            _knownsObiects = new FastMap<Integer, MuObject>().setShared(true);
//        }
//        return _knownsObiects;
//    }
//
//    /**
//     * remove all knownsobjects
//     */
//    public void removeAllKnownObjects() {
//        getKnownObjects().clear();
//    }
//
//    /**
//     * 
//     * @param object to remove
//     * @return true if succesful
//     */
//    public boolean removeKnownObject(MuObject object) {
//        if (object == null) {
//            return false;
//        }
//        return (getKnownObjects().remove(object.getObjectId()) != null);
//    }
//
//    public boolean addKnownObject(MuObject object) {
//        return addKnownObject(object, null);
//    }
//
//    public boolean addKnownObject(MuObject object, MuCharacter dropper) {
//        if (object == null || object == getActiveObject()) {
//            return false;
//        // Check if already know object
//        }
//        if (knowsObject(object)) {
//
//            if (!object.isVisible()) {
//                removeKnownObject(object);
//            }
//            return false;
//        }
//        return (getKnownObjects().put(object.getObjectId(), object) == null);
//    }
//
//    public final synchronized void updateKnownObjects() {
//        // Only bother updating knownobjects for MuCharacter; don't for MuObject
//        if (getActiveObject() instanceof MuCharacter) {
//            findCloseObjects();
//            forgetObjects();
//        }
//    }
//
//    private final void findCloseObjects() {
//        boolean isActiveObjectPlayable = (getActiveObject() instanceof MuPcInstance);
//
//        if (isActiveObjectPlayable) {
//            Collection<MuObject> objects = getActiveObject().getCurrentWorldRegion().getVisibleObjects(_activeObject);
//            if (objects == null) {
//                return;            // Go through all visible MuObject near the MuCharacter
//            }
//            for (MuObject object : objects) {
//                if (object == null) {
//                    continue;                // Try to add object to active object's known objects
//                
//                }
//                addKnownObject(object);
//
//                // Try to add active object to object's known objects
//                // Only if object is a MuCharacter and active object is a MuPcInstance
//                if (object instanceof MuCharacter) {
//                    object.getKnownObjects().addKnownObject(getActiveObject());
//                }
//            }
//        } else {
//            Collection<MuPcInstance> playables = _activeObject.getCurrentWorldRegion().getVisiblePlayers(_activeObject);
//            if (playables == null) {
//                return;            // Go through all visible MuObject near the MuCharacter
//            }
//            for (MuObject playable : playables) {
//                if (playable == null) {
//                    continue;                // Try to add object to active object's known objects
//                // MuCharacter only needs to see visible MuPcInstance ,
//                // when moving. Other l2characters are currently only known from initial spawn area.
//                // Possibly look into getDistanceToForgetObject values before modifying this approach...
//                }
//                addKnownObject(playable);
//            }
//        }
//    }
//
//    private final void forgetObjects() {
//        // Go through knownObjects
//        Collection<MuObject> knownObjects = getKnownObjects().values();
//
//        if (knownObjects == null || knownObjects.size() == 0) {
//            return;
//        }
//        for (MuObject object : knownObjects) {
//            if (object == null) {
//                continue;            // Remove all invisible object
//            // Remove all too far object
//            }
//            if (!object.isVisible() ||
//                    !checkInRage(object)) {
//                removeKnownObject(object);
//            }
//        }
//    }
//
//    public static class KnownListAsynchronousUpdateTask implements Runnable {
//
//        private MuObject _obj;
//
//        public KnownListAsynchronousUpdateTask(MuObject obj) {
//            _obj = obj;
//        }
//
//        /* (non-Javadoc)
//         * @see java.lang.Runnable#run()
//         */
//        public void run() {
//            if (_obj != null) {
//                _obj.getKnownObjects().updateKnownObjects();
//            }
//        }
//    }
//}
//
//
//
//
