package de.svdragster.logica.manager.Entity;

import org.jetbrains.annotations.NotNull;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.meta.ComponentType;
import de.svdragster.logica.util.Pair;

import java.util.*;

import static de.svdragster.logica.util.Pair.of;


public class EntityManager {

    private List<Entity>        EntityContext = new ArrayList<>();


    @NotNull
    static Entity createRawEntity(Component... components){
        return new Entity(components);
    }

    public boolean hasEntity(Entity id){
        for(Entity e : EntityContext)
            if(e == id)
                return true;
        return false;
    }

    public Entity createID(Component...components){
        Entity id = EntityManager.createRawEntity(components);
        EntityContext.add(id);
        return id;
    }

    public boolean removeID(Entity id){

        if(hasEntity(id))
        {
            EntityContext.remove(id);
            return true;
        }
        return false;
    }

    public List<Entity> getEntityContext() {
        return EntityContext;
    }

}
