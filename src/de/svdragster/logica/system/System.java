package de.svdragster.logica.system;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.manager.Entity.Entity;
import de.svdragster.logica.manager.Entity.EntityManager;
import de.svdragster.logica.world.Engine;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Observer;


/**
 * Created by Sven on 08.12.2017.
 */

/**
 *  Abstract base class for any system in the application. A System describes a rule for
 * c      certain kind of entities. Systems do not know about any entities directly they simply
 *        process components which are bundled together via an association. Any bundle of components
 *        are identified via an ID which describes unique entities.
 *
 *        Systems can be enabled and disabled. But the user is responsible for asking if thats the
 *        case.
 *
 *
 */
public abstract class System  implements Observer {

    private EntityManager GlobalEntityContext;
    private List<Entity> LocalEntityCache = new ArrayList<>();

    private boolean isActive=true;

    /**
     *
     */
    public System(){
        subscribe();
    }

    /**
     *
     * @return
     */
    public  List<Entity> getLocalEntityCache() {
        return LocalEntityCache;
    }

    /**
     *
     * @param l
     */
    public void setLocalEntityCache( List<Entity> l){
        LocalEntityCache = l;
    }

    /**
     * @return
     */
    public boolean isActive() {
        return isActive;
    }

    /**
     * @param active
     */
    protected void setActive(boolean active) {
        isActive = active;
    }

    /**
     * @return
     */
    public EntityManager getGlobalEntityContext() {
        return GlobalEntityContext;
    }

    /**
     * @param globalEntityContext
     */
    public void setGlobalEntityContext(EntityManager globalEntityContext) {
        GlobalEntityContext = globalEntityContext;
    }

    /**
     *  The process-method is called every game tick. Any system is required to implement
     *        this method.
     * @param delta is the time between two ticks the application needs to process everything
    */
    public abstract  void process( double delta );

    /**
     *  The subscribe method enables you get notified if something has changed so that
     *        your system can react on it. It is not required to let you subscribe to notifications.
     *        but it is quite useful. E.g. new entities has been created but your system does not
     *        know about themm through the subscription the system learns about it and can decide
     *        what to do about it.
     */
    protected void subscribe(){
        Engine.getInstance().getSystemManager().addObserver(this);
    }

    /**
     *  The unsubscribe method enables you to reverse the, you guessed it, subscribe method.
     */
    protected void unsubscribe(){
        Engine.getInstance().getSystemManager().deleteObserver(this);
    }

    /**
     *
     */
    public void disableSystem(){
        setActive(false);
    }

    /**
     *
     */
    public void enableSystem(){
        setActive(true);
    }
}
