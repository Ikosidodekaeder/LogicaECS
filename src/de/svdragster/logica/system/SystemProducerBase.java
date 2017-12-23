package de.svdragster.logica.system;

import java.util.List;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.ComponentProduct;
import de.svdragster.logica.components.ComponentResource;
import de.svdragster.logica.components.ComponentType;
import de.svdragster.logica.components.StdComponents;
import de.svdragster.logica.util.SystemNotifications.NotificationNewEntity;
import de.svdragster.logica.world.Engine;

/**
 * Created by Johannes Lüke on 09.12.2017.
 */

public abstract class SystemProducerBase extends System  {


    protected ComponentResource getEntityResource(int Entity){
        List<Component> EntityProperties = getLocalEntityCache().get(Entity);
        if(EntityProperties != null)
           for(Component c : EntityProperties)
               if(c instanceof ComponentResource)
                   return (ComponentResource) c;
        throw new NullPointerException();
    }
    public boolean isProducerEntity(int entity){
        return this.getGlobalEntityContext().hasComponent(entity, StdComponents.PRODUCER);
    }

    public void EmitProducts(ComponentResource Resource, ComponentType Type){

        //Emit new Product
        for(int i = 0; i < Resource.productionAmount; i++){
            int NewProduct = getGlobalEntityContext().createEntity(new ComponentProduct());
            Engine.getInstance().getSystemManager().BroadcastMessage(new NotificationNewEntity(NewProduct,Type));
        }
    }

    public void ResetProductionProgress(ComponentResource Resource){
        //Reset Progress
        Resource.productionProgress -= Resource.productionPeriod;
    }

    public void advanceProgress(ComponentResource Resource, float delta){
        Resource.productionProgress += Resource.productionRate*(float)(delta+Engine.FrameTime());
    }

}
