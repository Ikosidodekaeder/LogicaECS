package de.svdragster.logica.manager;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.meta.ComponentType;

import java.util.*;

/**
 * Created by Sven on 08.12.2017.
 */

public class EntityManager {
    private Map<Integer, List<UUID>>        EntityContext = new HashMap<Integer, List<UUID>>();;
    private ComponentManager                ComponentStorage = null;

    private int currentId = 0;
    private Queue<Integer> freeIds = new LinkedList<Integer>();

    public EntityManager(ComponentManager componentStorage) {
        this.ComponentStorage = componentStorage;
    }

    /**
     * @return returns the next free available ID components can associate against it.
     */
    private int nextId() {
        if (freeIds.size() > 0) {
            return freeIds.poll();
        }
        if (currentId == Integer.MAX_VALUE) {
            return -1;
        }
        currentId++;
        return currentId;
    }


    /**
     * @param id reclaims the given entity ID.
     */
    private void freeId(int id) {
        freeIds.offer(id);
    }

    /**
     * @return creates entity ID with an empty component context.
     */
    public int createEntity(){
        int id =  nextId();
        EntityContext.put(id,new ArrayList<UUID>());
        return id;
    }

    /**
     * @return entity ID which has components associated with it
     */
    public int createEntity(Component... component){

        int id = createEntity();

        for (Component c : component) {
            addComponent(id, c);
        }
        return id;
    }

    /**
     * @param entity reclaims the entity ID and frees the components
     */
    public void removeEntity(int entity) {

        if(isEntityAlive(entity))
        {
            List<Component> e = getEntity(entity);
            this.EntityContext.remove(entity);

            if(e != null)
                for (Component c : e)
                    ComponentStorage.remove(c);

            freeId(entity);
        }

    }

    /**
     * @param entity
     * @param component
     */
    public void addComponent(int entity, Component component){
        if (hasComponent(entity, component.getType())) {
            return;
        }

        ComponentStorage.emplaceComponent(component);

        List<UUID> list; // Create List Reference Holder
        if (this.EntityContext.containsKey(entity)) {
            list = this.EntityContext.get(entity); //Fill Holder with existing Reference
        } else {
            list = new ArrayList<>();
            this.EntityContext.put(entity, list); // Create new Reference Holder
        }
        list.add(component.getID());
    }

    /**
     * @param entity
     * @param component
     */
    public void removeComponent(int entity, Component component) {
        if (!hasComponent(entity, component.getType())) {
            return;
        }

        ComponentStorage.remove(component);

        this.EntityContext.get(entity).remove(component.getType());
    }

    /**
     * @param entityId
     * @param type
     * @return true if an Entity has a certain component
     */
    public boolean hasComponent(int entityId, ComponentType type) {
        List<Component> components = getEntity(entityId);

        for(Component c : components)
            if( c.getType() == type)
                return true;
        return false;
    }

    /**
     * @param entityId
     * @param types
     * @return true if an Entity has a certain set of components
     */
    public boolean hasComponents(int entityId, ComponentType... types) {

            for(ComponentType t : types)
                if(!hasComponent(entityId, t))
                    return false;
        return true;
    }

    /**
     * @param entityID
     * @param type
     * @return the instance of a component requested via the type and is associated to the entityID
     */
    public Component retrieveComponent(int entityID, ComponentType type){
        if(isEntityAlive(entityID)){
            List<Component> entity = getEntity(entityID);
            if(entity != null)
                for(Component c : entity)
                    if(c != null && c.getType() == type)
                        return c;
            return null;
        }
        return null;
    }

    public boolean isEntityAlive(int entityID){
        for(int id : this.EntityContext.keySet())
            if(id==entityID)
                return true;
        return false;
    }

    /**
     * @return context of entities.
     */
    public Map<Integer, List<UUID>> getEntityContext() {
        return EntityContext;
    }

    /**
     * @param entityID key to the associated components
     * @return returns a list of components which define an entity
     */
    public  List<Component> getEntity(int entityID) {
        List<Component> Entity = new ArrayList<>();
        for(UUID id : EntityContext.get(entityID)){
            Entity.add(ComponentStorage.queryComponent(id));
        }
        return Entity;
    }
}
