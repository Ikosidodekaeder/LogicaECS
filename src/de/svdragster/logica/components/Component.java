package de.svdragster.logica.components;
import java.util.UUID;

import de.svdragster.logica.components.meta.ComponentType;

/**
 * Created by Sven on 08.12.2017.
 */

public abstract class Component {
    private ComponentType type;
    private UUID           ID;

    public Component(){
        ID = UUID.randomUUID();
    }

    public UUID getID() {
        return ID;
    }

    public void setType(ComponentType type) {
        this.type = type;
    }
    public ComponentType getType() {
        return type;
    }
}
