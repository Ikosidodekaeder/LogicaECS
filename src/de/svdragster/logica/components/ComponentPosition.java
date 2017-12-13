package de.svdragster.logica.components;

/**
 * Created by Sven on 08.12.2017.
 */

public class ComponentPosition extends Component {

    public double X;
    public double Y;

    public ComponentPosition(double x, double y){
        super.setType(ComponentType.POSITION);
        X = x;
        Y = y;
    }

}
