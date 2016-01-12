package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;


/**
 * Generator pozadavku s exponencialnim rozdelenim intervalu mezi prichody.
 *
 * @author Miroslav Vozabal
 */
public class ExponentialTransactionGenerator extends AbstractTransactionGenerator {
    
    /** Stredni frekvence prichodu pozadavku do systemu. */
    private double lambda;
    
    
    /**
     * Vytvori novy generator pozadavku s exponencialnim rozdelenim.
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param lambda - stredni frekvence prichodu pozadavku (parametr lambda exponencialniho rozdeleni)
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
     * Vytvori novy generator pozadavku s exponencialnim rozdelenim.
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param lambda - stredni frekvence prichodu pozadavku (parametr lambda exponencialniho rozdeleni)
     * @param nextReceiver - nasledujici uzel
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public ExponentialTransactionGenerator(String name, JSimSimulation parent, double lambda, Receiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, parent, nextReceiver);
        this.lambda = lambda;
    }
    
    
    /**
     * Generuje nahodny interval mezi prichodem dvou pozadavku do systemu s exponencialnim rozdelenim.
     */
    @Override
    protected double interval() {
        return Math.abs(JSimSystem.negExp(lambda));
    }

}
