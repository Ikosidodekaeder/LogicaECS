package de.svdragster.logica.components;

/**
 * Created by z003pksw on 20.12.2017.
 */

public class ComponentResource extends Component {


    public float      productionRate;     //How fast 1 production unit is produced
    public float      productionPeriod;   //Total time 1 production unit consumes
    public float      productionAmount;   //Amount of units 1 production cycle actually creates
    public float      productionProgress; //
    public int        productType;        //Flag indicating The Resource

    public ComponentResource(float rate,float period,float amount,int type){
        super.setType(StdComponents.RESOURCE);

        productionAmount = amount;
        productionPeriod = period;
        productionRate   = rate;
        productType      = type;
        productionProgress = 0;
    }
}
