package de.svdragster.logica.manager.Entity;

import java.util.ArrayList;
import java.util.List;

import de.svdragster.logica.util.Pair;
import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.meta.ComponentType;
import de.svdragster.logica.world.Engine;

import static de.svdragster.logica.util.Pair.of;

/**
 * Created by Johannes on 24.01.2018.
 */

public class Entity {

    private List<Component> associatedComponents = new ArrayList<>();

    public Entity(){};
    public Entity(Component... components){
        for(Component c : components){
            associateComponent(c);
            Engine.getInstance().getComponentManager().add(c);
        }


    }

    /**
     * Simply checks if the current Entity has any number of Components associated with it.
     * @return Boolean
     */
    public boolean hasAnyAssociations() { return !associatedComponents.isEmpty();}

    /**
     * Checks if a certain type of Component is associated with the Entity. If this is the case
     * the returned Pair contains two Values. The first on indicates the positive found a a type the
     * second parameter holds a non-null reference to the component you just checked.
     * If the type you try to check for is not found to be associated with the entity the first paramter
     * indicates this by the boolean value of "false" and the second paramter should not be touched
     * at all else you will find a null-reference.
     *
     *
     * @param type Component Type which is going to be looked up
     * @param <BOOL_HasComponent> boolean type. If true the second param contains a non null ref to a Component
     * @param <ComponentReference> Reference to a Component associated with the current Entity
     * @return
     */
    public Pair hasAssociationWith(ComponentType type){

        for( Component c : associatedComponents)
            if( c.getType() == type)
                return of(true,c);
        return of(false,null);
    }

    /**
     * Removes a associated component from the Entity. If successful it returns true otherwise false;
     * if false this indicates that the component was never associated with the entity in the first
     * plase
     * @param type
     * @return true on success
     */
    public boolean removeAssociation(ComponentType type){
        Pair<Boolean,Component> ret = hasAssociationWith(type);

        if(ret.getFirst() == true){
            ret.getSecond().setBackAssociation(null);
            associatedComponents.remove(ret.getSecond());
            Engine.getInstance().getComponentManager().remove(ret.getSecond());
            return ret.getFirst();
        }
        return ret.getFirst();
    }

    /**
     * associate a new component to a entity. If the entity already contains the same type
     * of component this component will be removed and replaced by the new one of the same type.
     * @param component
     */
    public void associateComponent(Component component){
        Pair<Boolean,Component> ret = hasAssociationWith(component.getType());
        if(ret.getFirst() == false) {
            component.setBackAssociation(this);
            associatedComponents.add(component);
            Engine.getInstance().getComponentManager().add(component);
        }else{
            removeAssociation(ret.getSecond().getType());
            associateComponent(component);
            Engine.getInstance().getComponentManager().add(component);
        }
    }

}
