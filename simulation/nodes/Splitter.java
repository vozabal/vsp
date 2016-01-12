package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;


/**
 * Krizovatka - rozdeluje tok pozadavku do dvou smeru podle zadanych pravdepodobnosti.
 *
 * @author Miroslav Vozabal
 */
public class Splitter implements Receiver {
    
    /** Nasledujici uzel pro 1. smer. */
    private Receiver receiver1;
    
    /** Pravdepodobnost 1. smeru. */
    private double probability;
    
    /** Nasledujici uzel pro 2. smer. */
    private Receiver receiver2;
    
    
    /**
     * Vytvori novou krizovatku.
     * @param firstReceiver - nasledujici uzel pro 1. smer
     * @param probabilityOfFirst - pravdepodobnost 1. smeru
     * @param secondReceiver - nasledujici uzel pro 2. smer
     */
    public Splitter(Receiver firstReceiver, double probabilityOfFirst, Receiver secondReceiver) {
        this.receiver1 = firstReceiver;
        this.probability = probabilityOfFirst;
        this.receiver2 = secondReceiver;
    }

    
    /**
     * Prijme pozadavek a s pravdepodobnosti {@link #probability} ho preda uzlu {@link #receiver1},
     * s pravdepodobnosti (1 - {@link #probability}) do uzlu {@link #receiver2}.
     */
    @Override
    public void receive(JSimLink link) {
        if (Math.random() < probability)
            receiver1.receive(link);
        else
            receiver2.receive(link);
    }

}
