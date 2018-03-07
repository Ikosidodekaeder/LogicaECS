package de.svdragster.logica.manager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;

import de.svdragster.logica.system.System;

/**
 * Created by Sven on 08.12.2017.
 */

/**
 *  The SystemManager is responsible for keeping track of all available systems. It inherit
 *        the Observable class so that systems which have implemented the Observer Interface can
 *        be notified by the SystemManager.
 *
 *        Different Systems expect different Notification Objects, these objects are specified by
 *        each system them self.
 */
public class SystemManager extends Observable implements Iterable<System> {

    private List<System> SystemPool;
    public static SystemManager Instance;

    public SystemManager() {
        this.SystemPool = new ArrayList<System>();
        Instance = this;
    }

    public void addSystem(System... systems){
        //for(System s : systems)
            for(int i = 0; i < systems.length; i++)
            if(!SystemPool.contains(systems[i]))
                SystemPool.add(systems[i]);
    }

    public void removeSystem(System system) {
            if(!SystemPool.contains(system))
                SystemPool.remove(system);
    }

    public void BroadcastMessage(Object arg){
        setChanged();
        notifyObservers(arg);
    }

    public static SystemManager getInstance() { return Instance;}

    @Override
    public Iterator<System> iterator() {
        return SystemPool.iterator();
    }


}
