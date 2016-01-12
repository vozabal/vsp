package simulation.nodes;

import cz.zcu.fav.kiv.jsim.JSimLink;


/**
 * Rozhrani pro uzel simulace schopny prijimat pozadavky.
 *
 * @author Miroslav Vozabal
 */
public interface Receiver {
    
    /**
     * Prijme pozadavek.
     * 
     * @param link - pozadavek
     */
    void receive(JSimLink link);

}
