package de.svdragster.logica.components;

import de.svdragster.logica.components.meta.StdComponents;

/**
 * Created by Sven on 08.12.2017.
 */

public class ComponentMovement extends Component {

    public double dX;
    public double dY;

    public ComponentMovement(double dx, double dy){
        super.setType(StdComponents.MOVEMENT);
        dX = dx;
        dY = dy;
    }

}
