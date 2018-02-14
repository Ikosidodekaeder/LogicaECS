package de.svdragster.logica.util.SystemNotifications;

import de.svdragster.logica.components.meta.ComponentType;
import de.svdragster.logica.manager.Entity.Entity;

/**
 * Created by Johannes Lüke on 20.12.2017.
 */

public class NotificationRemoveEntity extends Notification {

   public Entity entity;

    public NotificationRemoveEntity(Entity Entity){ entity = Entity;}

    public Entity getEntity(){
        return entity;
    }

    public boolean isOfType(ComponentType...Types){
        for(int i = 0; i < Types.length;i++)
            if((boolean)entity.hasAssociationWith(Types[i]).getFirst() )
                continue;
            else
                return false;
        return true;
    }
}
