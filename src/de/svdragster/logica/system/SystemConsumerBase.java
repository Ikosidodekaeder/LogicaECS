package de.svdragster.logica.system;

import java.util.List;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.ComponentProduct;
import de.svdragster.logica.components.ComponentResource;

/**
 * Created by Johannes Lüke on 21.12.2017.
 */

public abstract class SystemConsumerBase extends System {


    protected ComponentProduct getEntityResource(int Entity){
        List<Component> EntityProperties = getLocalEntityCache().get(Entity);
        if(EntityProperties != null)
            for(Component c : EntityProperties)
                if(c instanceof ComponentProduct)
                    return (ComponentProduct) c;
        throw new NullPointerException();
    }



}
