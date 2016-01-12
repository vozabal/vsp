package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import generator.RandomNumber;


/**
 * Server s gaussovskym rozdelenim doby vyrizeni pozadavku.
 *
 * @author Miroslav Vozabal
 */
public class GaussianServer extends AbstractServer {
    
    /** Stredni doba vyrizeni pozadavku. */
    private final double meanValue;
    
    /** Smerodatna odchylka stredni doby vyrizeni pozadavku. */
    private final double standardDeviation;
    
    
    /**
     * Vytvori novy server s gaussovskym rozdelenim pravdepodobnosti.
     * 
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param meanValue - stredni doba vyrizeni pozadavku
     * @param standardDeviation - smerodatna odchylka stredni doby vyrizeni pozadavku
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
     * Vytvori novy server s gaussovskym rozdelenim pravdepodobnosti.
     * 
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param meanValue - stredni doba vyrizeni pozadavku
     * @param standardDeviation - smerodatna odchylka stredni doby vyrizeni pozadavku
     * @param nextReceiver - nasledujici uzel
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public GaussianServer(String name, JSimSimulation parent, double meanValue, double standardDeviation, Receiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent, nextReceiver);
        this.meanValue = meanValue;
        this.standardDeviation = standardDeviation;
    }
    
    
    /**
     * Generuje nahodnou dobu trvani vyrizeni pozadavku s gaussovskym rozdelenim.
     */
    @Override
    protected double workDuration() {
        return Math.abs(RandomNumber.gauss(meanValue, standardDeviation));
    }
    

}
