package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import generator.RandomNumber;


/**
 * Generator pozadavku s normalnim (gaussovym) rozdelenim intervalu mezi prichody.
 *
 * @author Miroslav Vozabal
 */
public class GaussianTransactionGenerator extends AbstractTransactionGenerator {
    
    /** Stredni doba mezi generovanim pozadavku. */
    private final double meanValue;
    
    /** Smerodatna odchylka stredni doby mezi generovanim pozadavku. */
    private final double standardDeviation;
    
    
    /**
     * Vytvori novy generator pozadavku s exponencialnim rozdelenim.
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param meanValue - stredni doba mezi generovanim pozadavku
     * @param standardDeviation - smerodatna odchylka stredni doby mezi generovanim pozadavku
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
     * Vytvori novy generator pozadavku s exponencialnim rozdelenim.
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param meanValue - stredni doba mezi generovanim pozadavku
     * @param standardDeviation - smerodatna odchylka stredni doby mezi generovanim pozadavku
     * @param nextReceiver - nasledujici uzel
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public GaussianTransactionGenerator(String name, JSimSimulation parent, double meanValue, double standardDeviation, Receiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException,
            JSimTooManyProcessesException {
        super(name, parent, nextReceiver);
        this.meanValue = meanValue;
        this.standardDeviation = standardDeviation;
    }
    
    
    /**
     * Generuje nahodny interval mezi prichodem dvou pozadavku do systemu s normalnim rozdelenim.
     */
    @Override
    protected double interval() {
        return Math.abs(RandomNumber.gauss(meanValue, standardDeviation));
    }

}
