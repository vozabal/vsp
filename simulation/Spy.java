package simulation;

import java.util.ArrayList;
import java.util.List;
import cz.zcu.fav.kiv.jsim.JSimException;
import cz.zcu.fav.kiv.jsim.JSimInvalidParametersException;
import cz.zcu.fav.kiv.jsim.JSimProcess;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import cz.zcu.fav.kiv.jsim.JSimSimulationAlreadyTerminatedException;
import cz.zcu.fav.kiv.jsim.JSimSystem;
import cz.zcu.fav.kiv.jsim.JSimTooManyProcessesException;
import simulation.nodes.AbstractServer;


/**
 * Objekt Spion sluzi k zachycovani statistik o systemu.
 * Sleduje stredni pocet pozadavku v systemu. V nepravidelnych
 * intervalech zjistuje stav systemu a aktualizuje si sledovane statistiky.
 *
 * @author Miroslav Vozabal
 */
public class Spy extends JSimProcess {
    
    /** Stredni frekvence sledovani systemu. */
    public static final double LAMBDA = 2; 
    
    /** Stredni pocet pozadavku v systemu. */
    private double meanTransactionCount = 0;
    
    /** Pocitadlo poctu provedenych vzorkovani systemu. */
    private int counter = 0;
    
    /** Seznam serveru, ktere jsou sledovany. */
    private List<AbstractServer> servers;


    
    /**
     * Vytvori novy objekt spion.
     * @param name - nazev objektu
     * @param parent - rodicovska simulace
     * @param servers - sledovane servery (vsechny servery v simulaci)
     * @throws JSimSimulationAlreadyTerminatedException
     * @throws JSimInvalidParametersException
     * @throws JSimTooManyProcessesException
     */
    public Spy(String name, JSimSimulation parent, AbstractServer... servers) 
            throws JSimSimulationAlreadyTerminatedException, JSimInvalidParametersException, JSimTooManyProcessesException {
        super(name, parent);
        this.servers = new ArrayList<AbstractServer>(servers.length);
        for (AbstractServer server : servers)
            this.servers.add(server);
    }
    
    
    /**
     * Vraci stredni pocet pozadavku v systemu (v prubehu cele simulace).
     * @return stredni pocet pozadavku v systemu
     */
    public double getMeanTransactionCount() {
        return this.meanTransactionCount;
    }
    
    
    /**
     * V nepravidelnych intervalech provadi vzorkovani systemu a aktualizuje
     * sledovanou statistiku (stredni pocet pozadavku v systemu).
     */
    @Override
    protected void life() {
        long currentL;
        
        try {
            while (true) {
                hold(Math.abs(JSimSystem.negExp(LAMBDA)));
                currentL = 0;
                for (AbstractServer server : servers)
                    currentL += server.getTransactionCount();
                meanTransactionCount = (meanTransactionCount * counter + currentL) / (counter + 1);
                counter++;
            }
        } catch (JSimException e) {
            e.printStackTrace();
        }
    }


}
