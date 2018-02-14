package de.svdragster.logica.manager.Entity;

import org.jetbrains.annotations.NotNull;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.meta.ComponentType;
import de.svdragster.logica.util.Pair;
import de.svdragster.logica.util.SystemNotifications.NotificationNewEntity;
import de.svdragster.logica.world.Engine;

import java.util.*;
import java.util.function.Consumer;

import static de.svdragster.logica.util.Pair.of;

/**
 *
 */
public class EntityManager {

    private List<Entity>        EntityContext = new ArrayList<>();


    /**
     *  Creates an Entity which is not stored anywhere!!! If you loose the Reference to it, it is lost!
     * @param components
     * @return
     */
    @NotNull
    public static Entity createRawEntity(Component... components){
        return new Entity(components);
    }

    /**
     * Checks if a given entity exist already in storage
     * @param id
     * @return true if exist
     */
    public boolean hasEntity(Entity id){
        for(Entity e : EntityContext)
            if(e == id)
                return true;
        return false;
    }

    /**
     * Creates an entity which is stored globally so it can be used as
     * ID to identify a entity class
     * @param array components
     * @return
     */
    public Entity createID(Component...components){
        //Engine.getInstance().BroadcastMessage(new NotificationNewEntity(1));
        if(components != null){
            Entity id = EntityManager.createRawEntity(components);
            EntityContext.add(id);
            return id;
        }

        return new Entity();
    }

    /**
     *
     * @param List<Component> components
     * @return
     */
    public Entity createID(List<Component> components){
        //Engine.getInstance().BroadcastMessage(new NotificationNewEntity(1));
        if(components != null){
            Entity id = EntityManager.createRawEntity(components.toArray(new Component[components.size()]));
            EntityContext.add(id);
            return id;
        }

        return new Entity();
    }

    /**
     * Returns only the first find;
     * @param ComponentType array types
     * @return
     */
    public Entity queryEntityOfType(ComponentType...types){
        Entity r = null;
        if(types != null || types.length == 0)
            for(Entity e : EntityContext){
                for(ComponentType t : types) {
                    Pair<Boolean,Component> res = e.hasAssociationWith(t);
                    if(!res.getFirst()){
                        r = null;
                        continue;
                    }else{
                        r = e;
                    }
                }
            }
        else
            for(Entity e : EntityContext){
                    if(e.hasAnyAssociations())
                        continue;
                    else
                        return e;
            }

        return r;
    }

    /**
     * Returns only the first find;
     * @param Component array types
     * @return
     */
    public Entity queryEntityOfType(Component...types){
        Entity r = null;
        if(types != null || types.length == 0)
            for(Entity e : EntityContext){
                for(Component c : types) {
                    Pair<Boolean,Component> res = e.hasAssociationWith(c.getType());
                    if(!res.getFirst()){
                        r = null;
                        continue;
                    }else{
                        r = e;
                    }
                }
            }
        else
            for(Entity e : EntityContext){
                if(e.hasAnyAssociations())
                    continue;
                else
                    return e;
            }

        return r;
    }

    /**
     * Removes given entity from storage
     * @param id
     * @return
     */
    public boolean removeID(Entity id){

        if(hasEntity(id))
        {
            EntityContext.remove(id);
            id.getAssociatedComponents().forEach(new Consumer<Component>() {
                @Override
                public void accept(Component component) {
                    Engine.getInstance().getComponentManager().remove(component);
                }
            });
            return true;
        }
        return false;
    }

    /**
     *
     * @return List<Entity>
     */
    public List<Entity> getEntityContext() {
        return EntityContext;
    }

}
