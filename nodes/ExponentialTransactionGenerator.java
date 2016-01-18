package nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;





/**
 *  The transactions arrivals generator of exponential distribution. It generates intervals between transactions arrivals.
 *
 * @author Miroslav Vozabal
 */
public class ExponentialTransactionGenerator extends AbstractTransGenerator {
    
	/** The mean value of the transaction arrival into the system */
    private double lambda;
    
    
        /**
     * Creates a new exponential distribution generator.
     * @param name - the node name
     * @param parent - the parent simulation
     * @param lambda - the mean value between the arrivals of transactions (the lambda parameter of the exponential distribution)
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public ExponentialTransactionGenerator(String name, JSimSimulation parent, double lambda)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, parent);
        this.lambda = lambda;
    }
    
    
    
    /**
     * Creates a new exponential distribution generator.
     * @param name - the node name
     * @param parent - the parent simulation
     * @param lambda - the mean value between the arrivals of transactions (the lambda parameter of the exponential distribution)
     * @param nextReceiver - the next node
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public ExponentialTransactionGenerator(String name, JSimSimulation parent, double lambda, IReceiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, parent, nextReceiver);
        this.lambda = lambda;
    }
    
    
    
    /**
     * Generates a random interval between 2 transactions arrivals into the system with exponential distribution.
     */
    @Override
    protected double interval() {
    	double randomNumber = Math.abs(JSimSystem.negExp(lambda));
        return randomNumber;
    }

}


