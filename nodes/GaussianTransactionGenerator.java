package nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import generator.NormalDistributionGenerator;





/**
 * The transaction generator of Normal Distribution. It generates the interval between transactions arrivals.
 *
 * @author Miroslav Vozabal
 */
public class GaussianTransactionGenerator extends AbstractTransGenerator {
    
	/** The mean value between the generations of transactions */
    private final double meanValue;
    
    /** The standard deviation of the mean value between the generations of transactions. */
    private final double standardDeviation;
    
    
    /**
     * Creates a new Gaussian distribution generator.
     * @param name - the node name
     * @param parent - the parent simulation
     * @param meanValue - the mean value between the generations of transactions
     * @param standardDeviation - the standard deviation of the mean value between the generations of transactions
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public GaussianTransactionGenerator(String name, JSimSimulation parent, double meanValue, double standardDeviation)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, parent);
        this.meanValue = meanValue;
        this.standardDeviation = standardDeviation;
    }
    
    
    /**
     * Creates a new Gaussian distribution generator.
     * @param name - the node name
     * @param parent - the parent simulation
     * @param meanValue - the mean value between the generations of transactions
     * @param standardDeviation - the standard deviation of the mean value between the generations of transactions
     * @param nextReceiver - the next node
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public GaussianTransactionGenerator(String name, JSimSimulation parent, double meanValue, double standardDeviation, IReceiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, parent, nextReceiver);
        this.meanValue = meanValue;
        this.standardDeviation = standardDeviation;
    }
    
    
    /**
     * Generates a random interval between 2 transactions arrivals into the system with Normal Distribution.
     */
    @Override
    protected double interval() {
    	double randomNumber = Math.abs(NormalDistributionGenerator.generateGauss(meanValue, standardDeviation));
        return randomNumber;
    }

}




