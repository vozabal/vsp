package nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import generator.NormalDistributionGenerator;


/**
 * The server with a Gaussian distribution of the transaction processing.
 *
 * @author Miroslav Vozabal
 */
public class GaussianServer extends AbstractServer {
    
    /** The mean value of the transaction processing. */
    private final double meanValue;
    
    /** The standard deviation. */
    private final double standardDeviation;
    
    
    
    /**
     * Creates a new server with Gaussian probability distribution.
     * 
     * @param name - the node name
     * @param parent - the parent simulation
     * @param meanValue - the mean value of the transaction processing
     * @param standardDeviation - the standardDeviation of the transaction processing
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public GaussianServer(String name, JSimSimulation parent, double meanValue, double standardDeviation)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent);
        this.meanValue = meanValue;
        this.standardDeviation = standardDeviation;
    }
    
    
    
    /**
     * Creates a new server with Gaussian probability distribution.
     * 
     * @param name - the node name
     * @param parent - the parent simulation
     * @param meanValue - the mean value of the transaction processing
     * @param standardDeviation - the standardDeviation of the transaction processing
     * @param nextReceiver - the next node
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public GaussianServer(String name, JSimSimulation parent, double meanValue, double standardDeviation, IReceiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent, nextReceiver);
        this.meanValue = meanValue;
        this.standardDeviation = standardDeviation;
    }
    
    
    /**
     * Generates a random time of the transaction processing with Gaussian probability distribution.
     */
    @Override
    protected double workDuration() {
    	double randomNumber = Math.abs(NormalDistributionGenerator.generateGauss(meanValue, standardDeviation));
        return randomNumber;
    }
    

}
