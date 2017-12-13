package de.svdragster.logica.components;

/**
 * Created by johannes Lüke on 09.12.2017.
 */

public class ComponentProducer extends Component {


    public double      productionRate;     //How fast 1 production unit is produced
    public double      productionPeriod;   //Total time 1 production unit consumes
    public double      productionAmount;   //Amount of units 1 production cycle actually creates

    public ComponentProducer(){
        super.setType(ComponentType.PRODUCER);
    }
}
