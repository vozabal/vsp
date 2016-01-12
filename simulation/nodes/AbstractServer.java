package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimHead;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSecurityException;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimTooManyHeadsException;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import simulation.Statistics;


/**
 * Abstraktni implementace serveru. Server se sklada z fronty a kanalu obsluhy.
 * Potomci musi implementovat metodu {@link #workDuration()}, ktera vraci nahodnou
 * dobu obsluhy pozadavku (v zavislosti na danem rozdeleni).
 *
 * @author Miroslav Vozabal
 */
public abstract class AbstractServer extends JSimProcess implements Receiver {
    
    /** Uzel kteremu server predava zpracovany pozadavek. */
    protected Receiver nextReceiver;
    
    /** Fronta pozadavku. */
    protected JSimHead queue;
    
    /** Pocet zpracovanych pozadavku. */
    protected int transactionCounter = 0;
    
    /** Statistika doby provozu serveru. */
    protected Statistics operationTime = new Statistics();
    

    
    /**
     * Vytvori v simulaci novy server s danym jmenem.
     * 
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public AbstractServer(String name, JSimSimulation parent)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        this(name, parent, null);
    }
    
    
    /**
     * Vytvori v simulaci novy server s danym jmenem.
     * 
     * @param name - jmeno uzlu
     * @param parent - rodicovska simulace
     * @param nextReceiver - nasledujici uzel
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     * @throws JSimTooManyHeadsException
     */
    public AbstractServer(String name, JSimSimulation parent, Receiver nextReceiver)
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException, JSimTooManyHeadsException {
        super(name, parent);
        this.nextReceiver = nextReceiver;
        this.queue = new JSimHead(name + " queue", parent);
    }
    
    
    /**
     * Vraci stredni pocet pozadavku v serveru v prubehu cele simulace.
     * @return stredni pocet pozadavku v serveru (Lq)
     */
    public double getLq() {
        /* pozadavek se v life() vybira z fronty az po zpracovani,
         * takze vlastne jde o Lw + Ls = Lq */
        return queue.getLw();
    }
    
    
    /**
     * Vraci stredni dobu zpracovani pozadavku v serveru v prubehu cele simulace.
     * @return stredni doba zpracovani pozadavku v serveru (Tq)
     */
    public double getTq() {
        /* pozadavek se v life() vybira z fronty az po zpracovani,
         * takze vlastne jde o Tw + Ts = Tq */
        return queue.getTw();
    }
    
    
    /**
     * Vraci aktualni pocet pozadavku v serveru, tj. ve fronte a v kanalu obsluhy.
     * @return aktualni pocet pozadavku v serveru
     */
    public long getTransactionCount() {
        /* pozadavek se v life() vybira z fronty az po zpracovani,
         * takze fronta obsahuje vsechny pozadavky v serveru */
        return queue.cardinal();
    }
    
    
    /**
     * Zjistuje zda server prave pracuje nebo ceka na prichod pozadavku.
     * @return true pokud server pracuje (prave probiha zpracovani pozadavku), jinak false
     */
    public boolean isWorking() {
        return !isIdle();
    }
    
    
    /**
     * Vraci zatez serveru v prubehu cele simulace. Zatez je vypoctena jako pomer doby
     * kdy server pracoval ku trvani cele simulace.
     * @return zatez serveru v prubehu simulace
     */
    public double getLoad() {
        return operationTime.getSum() / myParent.getCurrentTime();
    }
    
    
    /**
     * Generuje nahodnou dobu trvani vyrizeni pozadavku v zavislosti na danem rozdeleni.
     * @return nahodna doba trvani vyrizeni pozadavku
     */
    protected abstract double workDuration();
    
    
    /**
     * Vybira z fronty pozadavky a provadi jejich zpracovani. Doba zpracovani je urcena
     * metodou {@link #workDuration()}. Pokud je fronta prazdna, uspi se.
     */
    @Override
    protected void life() {
        JSimLink link;
        double time;
        
        try {
            while (true) {
                if (queue.empty())
                    passivate();
                else {
                    link = queue.first();
                    
                    /* zpracovani pozadavku */
                    time = myParent.getCurrentTime();
                    hold(workDuration());
                    operationTime.addValue(myParent.getCurrentTime() - time);
                    
                    /* pozadavek se z fronty vybere az po zpracovani,
                     * takze fronta vzdy obsahuje vsechny pozadavky v celem serveru */
                    link.out();
                    transactionCounter++;
                    
                    if (nextReceiver != null)
                        nextReceiver.receive(link);
                    else
                        link = null;
                }
            }
        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        }
    }
    

    @Override
    public void receive(JSimLink link) {
        try {
            link.into(queue);
            if (isIdle())
                activateNow();
        } catch (JSimSecurityException e) {
            // pozadavek uz ve fronte je
            e.printStackTrace();
            e.printComment(System.err);
        }
    }

    
    /**
     * Vraci nasledujici uzel v simulaci.
     * @return nasledujici uzel
     */
    public Receiver getNextReceiver() {
        return nextReceiver;
    }

    
    /**
     * Nastavi nasledujici uzel v simulaci.
     * @param nextReceiver - nasledujici uzel
     */
    public void setNextReceiver(Receiver nextReceiver) {
        this.nextReceiver = nextReceiver;
    }
    
    
    /**
     * Vraci pocet zpracovanych pozadavku.
     * @return pocet zpracovanych pozadavku
     */
    public int getProcessedTransactionsCount() {
        return transactionCounter;
    }
    
    
    /**
     * Vraci stredni frekvenci toku v uzlu.
     * @return stredni frekvence toku v uzlu
     */
    public double getMeanThroughputRate() {
        return transactionCounter / myParent.getCurrentTime();
    }
    
    
    /**
     * Vraci statistiky doby cinnosti serveru.
     * @return statistika doby cinnosti
     */
    public Statistics getOperationTimeStatistics() {
        return operationTime;
    }

}
