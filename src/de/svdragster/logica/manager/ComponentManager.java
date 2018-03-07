package de.svdragster.logica.manager;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import de.svdragster.logica.components.Component;
import de.svdragster.logica.components.meta.ComponentType;

/**
 * Created by Johannes on 12.02.2018.
 */

public class ComponentManager {

    private List<Component>         componentList = new ArrayList<>();


    public ComponentManager(){

    }

    public void add(Component c){
        this.componentList.add(c);
    }

    public boolean remove(Component c)
    {
        return this.componentList.remove(c);
    }


    public List<Component> groupByType(ComponentType type)
    {
        List<Component> tmp = new LinkedList<>();
        //for(Component c : this.componentList){
            for(int i = 0; i < componentList.size(); i++){
                Component c = componentList.get(i);
            if(c.getType() == type)
            {
                tmp.add(c);
            }
        }
        return tmp;
    }

    public List<List<Component>> groupByTypes(ComponentType... types){
        List<List<Component>> tmp = new LinkedList<>();
        for(ComponentType c : types)
        {
            tmp.add(groupByType(c));
        }

        return tmp;
    }




}
