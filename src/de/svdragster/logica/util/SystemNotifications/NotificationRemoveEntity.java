package de.svdragster.logica.util.SystemNotifications;

/**
 * Created by Johannes Lüke on 20.12.2017.
 */

public class NotificationRemoveEntity extends Notification {

    int entity;

    public NotificationRemoveEntity(int Entity){ entity = Entity;}

    public int Entity(){
        return entity;
    }
}
