package nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;


/**
 * The server with the exponential distribution of the transaction processing.
 *
 * @author Miroslav Vozabal
 */
public class ExponentialServer extends AbstractServer {
    
	/** The mean value of the transaction processing. Parameter &lambda; of the exponential distribution. */ 
    private double lambda;

    
    /**
     * Creates a new server with Exponential probability distribution.
     * 
     * @param name - the node name
     * @param parent - the parent simulation
     * @param lambda - the mean value of the transaction processing
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public ExponentialServer(String name, JSimSimulation parent, double lambda)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent);
        this.lambda = lambda;
    }
    
    
    /**
     * Creates a new server with Exponential probability distribution.
     * 
     * @param name - the node name
     * @param parent - the parent simulation
     * @param lambda - the mean value of the transaction processing
     * @param nextReceiver - the next node
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public ExponentialServer(String name, JSimSimulation parent, double lambda, IReceiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent, nextReceiver);
        this.lambda = lambda;
    }

    
    /**
     * Generates a random time of the transaction processing with Exponential probability distribution.
     */
    @Override
    protected double workDuration() {
    	double randomNumber = Math.abs (JSimSystem.negExp(lambda));
        return randomNumber;
    }

}
