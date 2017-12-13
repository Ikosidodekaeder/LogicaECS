package de.svdragster.logica.components;

/**
 * Created by Sven on 08.12.2017.
 */

public class ComponentMovement extends Component {

    public double dX;
    public double dY;

    public ComponentMovement(double dx, double dy){
        super.setType(ComponentType.MOVEMENT);
        dX = dx;
        dY = dy;
    }

}
