package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;
import cz.zcu.fav.kiv.jsim.JSimSimulation;
import simulation.Statistics;

public class Probe implements Receiver {

	 /** Nasledujici uzel pro 1. smer. */
    private Receiver receiver;
    
    /** Pravdepodobnost 1. smeru. */
    
    /** Statistika doby provozu serveru. */
    protected Statistics operationTime = new Statistics();
    
    /** Nasledujici uzel pro 2. smer. */
    private Receiver receiver2;
    
    private Boolean first = true;
    
    private double lastLinkTime;
    
    
    
    /** Rodicovska simulace. */
    private JSimSimulation myParent;
    
    /** Stredni doba odezvy systemu. */
    private double meanResponseTime = 0.0;
    
    /** Pocet zpracovanych pozadavku. */
    private int transactionCounter = 0;
	
	public Probe(Receiver receiver, JSimSimulation parent) {
	    this.receiver = receiver;
	    this.myParent = parent;
	}

	@Override
	public void receive(JSimLink link) {
		// TODO Auto-generated method stub
		
		if (first)
		{
			lastLinkTime = myParent.getCurrentTime(); // - startime TODO
			receiver.receive(link);
			first = false;
		}
		else
		{				
			lastLinkTime = myParent.getCurrentTime() - lastLinkTime;
			receiver.receive(link);
			operationTime.addValue(lastLinkTime); // Ziskani casu prichodu a casu dalsiho prichodu
		}
	}
	
	 /**
     * Vraci statistiky doby cinnosti serveru.
     * @return statistika doby cinnosti
     */
    public Statistics getOperationTimeStatistics() {
        return operationTime;
    }

}
