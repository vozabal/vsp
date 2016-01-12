package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import simulation.Transaction;



/**
 * Abstraktni generator pozadavku. V nahodnych intervalech generuje nove prichozi pozadavky.
 * Potomci musi implementovat metodu {@link #interval()}, ktera vraci nahodnou dobu intervalu
 * do prichodu dalsiho pozadavku (v zavislosti na danem rozdeleni).
 *
 * @author Miroslav Vozabal
 */
public abstract class AbstractTransactionGenerator extends JSimProcess {
    
    /** Nasledujici uzel v simulaci. */
    private Receiver nextReceiver;

    
    /**
     * Vytvori generator s danym jmenem.
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public AbstractTransactionGenerator(String name, JSimSimulation parent)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        this(name, parent, null);
    }
    
    
    /**
     * Vytvori generator s danym jmenem.
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param nextReceiver - nasledujici uzel v simulaci
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public AbstractTransactionGenerator(String name, JSimSimulation parent, Receiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(name, parent);
        this.nextReceiver = nextReceiver;
    }
    
    
    /**
     * Generuje nahodnou dobu intervalu mezi prichodem dvou pozadavku do systemu v zaislosti
     * na danem rozdeleni.
     * @return nahodny interval
     */
    protected abstract double interval();
    
    
    /**
     * V nahodnych intervalech generuje nove pozadavky prichazejici do systemu.
     */
    @Override
    protected void life() {
        JSimLink link;
        try {
            while (true) {
                link = new JSimLink(new Transaction(myParent.getCurrentTime()));
                nextReceiver.receive(link);
                hold(interval());
            }
        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        }
    }


    
    /**
     * Vraci nasledujici uzel.
     * @return nasledujici uzel
     */
    public Receiver getNextReceiver() {
        return nextReceiver;
    }


    
    /**
     * Nastavi nasledujic uzel.
     * @param nextReceiver - nasledujici uzel
     */
    public void setNextReceiver(Receiver nextReceiver) {
        this.nextReceiver = nextReceiver;
    }

}
