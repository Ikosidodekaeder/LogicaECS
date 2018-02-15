package de.svdragster.logica.world;



import java.util.ArrayDeque;
import java.util.Queue;

import de.svdragster.logica.manager.ComponentManager;
import de.svdragster.logica.manager.Entity.EntityManager;
import de.svdragster.logica.system.System;
import de.svdragster.logica.manager.SystemManager;


/**
 * Bundles all important parts of the ECS and gives an interface to the application to execute
 * or expand the capabilities of this System. Only one instance should be every created per application
 * but nothing forbids to create more.
 */
public class Engine {

	private static Engine instance;
	private static long EngineFrameTime;

    private static long totalRuntime;
    private boolean suspendedFlag = false;
    private long loop = 0;

    private ComponentManager componentManager   = new ComponentManager();
    private EntityManager entityManager         = new EntityManager(/*componentManager*/);
    private SystemManager systemManager         = new SystemManager();
    private Queue<Object> MessagePool           = new ArrayDeque<>();



    private void doMessageDelivery(){
        while(!MessagePool.isEmpty()){
            systemManager.BroadcastMessage(MessagePool.poll());
        }
    }

    public Engine(){
        instance = this;
        EngineFrameTime = 0;
    }

    public Engine(/*ComponentManager com,*/EntityManager entity,SystemManager system){
        //this.componentManager = com;
        this.entityManager = entity;
        this.systemManager = system;
        EngineFrameTime = 0;
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

    public void run(float delta) {
        //loop++;
        // java.lang.System.nanoTime();
        long startTime = java.lang.System.nanoTime();
        try{
            doMessageDelivery();
            for(System s : systemManager)
                if(s.isActive())
                    s.process( delta );

        }catch (Exception e)
        {
            e.printStackTrace();
        }


        EngineFrameTime =  java.lang.System.nanoTime() - startTime;
        totalRuntime += EngineFrameTime;

        //java.lang.System.out.println("LogicDelta " + ((float)loop/(endtime-startTime)) + " Iteration: "+ loop);

    }


    public void BroadcastMessage(Object Message){
        MessagePool.add(Message);
        //systemManager.BroadcastMessage(Message);
    }



    public static long FrameTime(){
        return EngineFrameTime;
    }

    public static double FramesPerSecond(){
        return 1.0D/((FrameTime()/Math.pow(10,6)) / 1000.0D);
    }

	public static synchronized Engine getInstance() {
		return instance;
	}

	public static long Runtime_ms(){
        return (long)(totalRuntime / Math.pow(10,6));
    }
}
