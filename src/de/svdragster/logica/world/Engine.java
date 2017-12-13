package de.svdragster.logica.world;



import de.svdragster.logica.components.ComponentManager;
import de.svdragster.logica.entities.EntityManager;
import de.svdragster.logica.system.System;
import de.svdragster.logica.system.SystemManager;


/**
 * Bundles all important parts of the ECS and gives an interface to the application to execute
 * or expand the capabilities of this System. Only one instance should be every created per application
 * but nothing forbids to create more.
 */
public class Engine {

	private static Engine instance;

    private ComponentManager componentManager;
    private EntityManager entityManager;
    private SystemManager systemManager;


    public Engine(){
        this.componentManager = new ComponentManager();
        this.entityManager = new EntityManager(componentManager);
        this.systemManager = new SystemManager();

        instance = this;
    }


    public ComponentManager getComponentManager() {
        return componentManager;
    }

    public EntityManager getEntityManager() {
        return entityManager;
    }

    public SystemManager getSystemManager() {
        return systemManager;
    }

    public void run() {
        for(System s : systemManager)
            if(s.isActive())
                s.process( 1);
    }

	public static Engine getInstance() {
		return instance;
	}
}
