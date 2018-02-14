package de.svdragster.logica.system;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.ComponentProduct;
import de.svdragster.logica.components.ComponentResource;
import de.svdragster.logica.components.meta.ComponentType;
import de.svdragster.logica.manager.Entity.Entity;
import de.svdragster.logica.util.SystemNotifications.Notification;
import de.svdragster.logica.util.SystemNotifications.NotificationNewEntity;
import de.svdragster.logica.util.SystemNotifications.NotificationRemoveEntity;
import de.svdragster.logica.world.Engine;

/**
 * Created by Johannes Lüke on 21.12.2017.
 */

/**
 * Any Consumer system is defined by an entity which have the
 * component "Consumer" and "Resource" associated with it.
 *
 * Where "Consumer" simple marks it as an entity removing
 * system and "Resource" defines the actual resource which shall
 * be removed, in which rate and amount.
 *
 * An entity defined in Resource will only be removed if the period
 * of the resource has been reached. this is done by simply accumalate
 * the rate in which the entity shall be removed over time:
 *
 *      Resource.productProgress += Resource.productRate;
 *
 * To define a class of entities you have the field productType in
 * the "Resource" component which is a simple list containing a set
 * of components entities have to match so you are able to identify
 * possible candidates for removal
 */
public abstract class SystemConsumerBase extends System {

    /**
     * Removes entities from other systems which correspond to the
     * type specified in the >system defining component<
     *
     * Iterates through ALL entities.
     * @param Resource
     */
    public void ConsumesProducts(final ComponentResource Resource,Component...inject){


        final List<Component> tmp  = new ArrayList<Component>(Resource.productType);
        tmp.addAll(Arrays.asList(inject));
        //Consumes productionAmount of Product
        for(int i = 0; i < Resource.productionAmount; i++) {
            if(Resource.productType != null)
                //if any of the entities inside the local entity cache have the same
                //component signature aka the same types of components send a messages
                //to remove this entity from all local caches of all system currently
                //running
              getGlobalEntityContext().getEntityContext().forEach(new Consumer<Entity>() {
                  @Override
                  public void accept(Entity entity) {
                      //java.lang.System.out.print("Check Consumer " + entity + " <> " + tmp);
                      if(entity.getAssociatedComponents().equals( tmp )){
                          //java.lang.System.out.println(" TRUE");
                          Engine.getInstance().BroadcastMessage(new NotificationRemoveEntity(entity));
                      }else
                          ;//java.lang.System.out.println(" FALSE");
                  }
              });

        }
    }

    /**
     *
     * @param Resource
     */
    public void resetProductionProgress(ComponentResource Resource){
        //Reset Progress
        Resource.productionProgress -= Resource.productionPeriod;
        if(Resource.productionProgress > 2*Resource.productionPeriod)
            Resource.productionProgress = Resource.productionPeriod;
    }

    /**
     *
     * @param Resource
     * @param delta
     */
    public void advanceProgress(ComponentResource Resource, float delta){
        Resource.productionProgress += Resource.productionRate*(float)(delta);
    }

    /**
     *
     * @param resource
     * @return
     */
    public boolean isReady(ComponentResource resource){
        return resource.productionProgress > resource.productionPeriod;
    }



}
