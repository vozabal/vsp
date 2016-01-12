package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;


/**
 * Server s exponencialnim rozdelenim doby vyrizeni pozadavku.
 *
 * @author Miroslav Vozabal
 */
public class ExponentialServer extends AbstractServer {
    
    /** Stredni frekvence vyrizeni pozadavku - parametr &lambda; exponencialniho rozdeleni. */
    private double lambda;

    
    /**
     * Vytvori novy server s exponencialnim rozdelenim pravdepodobnosti.
     * 
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param lambda - stredni frekvence vyrizeni pozadavku
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
     * Vytvori novy server s exponencialnim rozdelenim pravdepodobnosti.
     * 
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param lambda - stredni frekvence vyrizeni pozadavku
     * @param nextReceiver - nasledujici uzel
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public ExponentialServer(String name, JSimSimulation parent, double lambda, Receiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent, nextReceiver);
        this.lambda = lambda;
    }

    
    /**
     * Generuje nahodnou dobu trvani vyrizeni pozadavku s exponencialnim rozdelenim.
     */
    @Override
    protected double workDuration() {
        return Math.abs(JSimSystem.negExp(lambda));
    }

}
