package de.svdragster.logica.system;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.ComponentProduct;
import de.svdragster.logica.components.ComponentResource;
import de.svdragster.logica.components.meta.ComponentType;
import de.svdragster.logica.components.meta.StdComponents;
import de.svdragster.logica.manager.Entity.Entity;
import de.svdragster.logica.util.SystemNotifications.NotificationNewEntity;
import de.svdragster.logica.world.Engine;

/**
 * Created by Johannes Lüke on 09.12.2017.
 */

/**
 *
 */
public abstract class SystemProducerBase extends System  {

    /**
     *
     * @param Resource
     * @param inject
     */
    public void EmitProducts(ComponentResource Resource,Component...inject){


        List<Component> tmp  = new ArrayList<Component>(Resource.productType);
        tmp.addAll(Arrays.asList(inject));
        //Emit new Product
        for(int i = 0; i < Resource.productionAmount; i++){
            Entity NewProduct = getGlobalEntityContext().createID(
                    new ArrayList<Component>(tmp)
            );

            //java.lang.System.out.println("Emit Product: " + NewProduct );
            Engine.getInstance().getSystemManager().BroadcastMessage(new NotificationNewEntity(NewProduct));
        }
    }

    /**
     *
     * @param Resource
     */
    public void resetProductionProgress(ComponentResource Resource){
        //Reset Progress
        Resource.productionProgress -= Resource.productionPeriod;
    }

    /**
     *
     * @param Resource
     * @param delta
     */
    public void advanceProgress(ComponentResource Resource, float delta){
        Resource.productionProgress += Resource.productionRate*(float)(delta+Engine.FrameTime());
    }

    /**
     *
     * @param Resource
     * @return
     */
    public boolean isReady(ComponentResource Resource){
        return Resource.productionProgress > Resource.productionPeriod;
    }



}
