package simulation;


/**
 * Objekt nesouci informace spojene s pozadavkem.
 *
 * @author Miroslav Vozabal
 */
public class Transaction {
    
    /** Doba vytvoreni pozadavku (prichodu do systemu). */
    private double creationTime;

    
    /**
     * Vytvori novy pozadavek.
     * @param creationTime - doba vytvoreni (prichodu do systemu)
     */
    public Transaction(double creationTime) {
        this.creationTime = creationTime;
    }

    
    /**
     * Vraci dobu prichodu pozadavku do systemu.
     * @return doba prichodu pozadavku
     */
    public double getCreationTime() {
        return creationTime;
    }
    
}
