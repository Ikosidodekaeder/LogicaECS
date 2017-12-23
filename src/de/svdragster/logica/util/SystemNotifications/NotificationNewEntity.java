package de.svdragster.logica.util.SystemNotifications;

import de.svdragster.logica.components.meta.ComponentType;

/**
 * Created by z003pksw on 20.12.2017.
 */

public class NotificationNewEntity extends Notification {

    int                 entity;
    ComponentType[]     EntityProperties;

    public NotificationNewEntity(int Entity, ComponentType...Types){
        entity = Entity;
        EntityProperties = Types;
    }


    public int Entity(){
        return entity;
    }

    public boolean isOfType(ComponentType...Types){
        for(int i = 0; i < Types.length;i++)
            if(EntityProperties[i] == Types[i])
                continue;
            else
                return false;
        return true;
    }
}
