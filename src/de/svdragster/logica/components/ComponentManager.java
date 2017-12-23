package de.svdragster.logica.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Sven on 08.12.2017.
 */

public class ComponentManager {

    private Map<ComponentType, List<Component>> componentList;
    private Map<UUID,Component>                 ComponentHash = new HashMap<>();

    public  Map<ComponentType, List<Component>> getComponentList() {
        return componentList;
    }

    public ComponentManager() {
        this.componentList = new HashMap<ComponentType, List<Component>>();

        for (ComponentType componentType : StdComponents.values()) {
            this.componentList.put(componentType, new ArrayList<Component>());
        }

    }

    /**
     * @param component the instance added to the ECS
     */
    public void emplaceComponent(Component component)
    {
        componentList.get(component.getType()).add(component);
    }

    /**
     * @param component instance of the component which shall be eliminated from the ECS
     * @return true on success
     */
    public boolean remove(Component component){
        ComponentHash.remove(component.getID());
        return componentList.get(component.getType()).remove(component);
    }



    /**
     * @param type of the component which shall be eliminated from the ECS
     * @return a List of all instances of that type which were removed
     */
    public List<Component> remove(ComponentType type){
       return componentList.remove(type);
    }

    public int queryAmount(ComponentType type) {
        return componentList.get(type).size();
    }

    public Component queryComponent(UUID ID){
        if(ComponentHash.containsKey(ID))
            return ComponentHash.get(ID);

        for( List<Component> types : componentList.values()){
            for(Component component : types){
                if (component.getID().equals(ID)){
                    ComponentHash.put(component.getID(),component);
                    return component;
                }

            }
        }
        return null;
    }
}
