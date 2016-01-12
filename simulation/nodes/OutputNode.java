package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import simulation.Transaction;


/**
 * Vystupni uzel simulace. Shromazduje nasledujici statistiky:
 * <ul>
 *      <li>stredni doba odezvy celeho systemu</li>
 *      <li>pocet zpracovanych pozadavku</li>
 *      <li>stredni frekvence vystupniho toku pozadavku</li>
 * </ul>
 *
 * @author Miroslav Vozabal
 */
public class OutputNode implements Receiver {
    
    /** Rodicovska simulace. */
    private JSimSimulation myParent;
    
    /** Stredni doba odezvy systemu. */
    private double meanResponseTime = 0.0;
    
    /** Pocet zpracovanych pozadavku. */
    private int transactionCounter = 0;
    
    
    /**
     * Vytvori novy vystupni uzel.
     * @param parent - rodicovska simulace
     */
    public OutputNode(JSimSimulation parent) {
        this.myParent = parent;
    }

    
    /**
     * Prijme pozadavek a aktualizuje shromazdovane statistiky.
     */
    @Override
    public void receive(JSimLink link) {
        Transaction transaction = (Transaction) link.getData();
        double responseTime = myParent.getCurrentTime() - transaction.getCreationTime();
        meanResponseTime = (meanResponseTime * transactionCounter + responseTime) / (transactionCounter + 1);
        transactionCounter++;
    }
    
    
    /**
     * Vraci pocet zpracovanych pozadavku.
     * @return pocet zpracovanych pozadavku
     */
    public int getProcessedTransactionsCount() {
        return transactionCounter;
    }

    
    /**
     * Vraci stredni dobu odezvy systemu.
     * @return stredni doba odezvy
     */
    public double getMeanResponseTime() {
        return meanResponseTime;
    }
    
    
    /**
     * Vraci stredni frekvenci vystupniho toku.
     * @return stredni frekvence vystupniho toku
     */
    public double getMeanOutputRate() {
        return transactionCounter / myParent.getCurrentTime();
    }

}
