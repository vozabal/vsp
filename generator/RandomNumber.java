package generator;


/**
 * Generator nahodnych cisel.
 *
 * @author Miroslav Vozabal
 */
public class RandomNumber {
    
    /** Maximalni delka jedne radky histogramu (maximalni pocet hvezdicek). */
    private static final int HISTOGRAM_MAX_LENGTH = 70;
    
    /** Pocet hodnot, ktere histogram sleduje. */
    private static final int HISTOGRAM_VALUES = 31;
    
    
    /**
     * Generuje nahodna cisla s normalnim (Gaussovym) rozdelenim.
     * @param meanValue - stredni hodnota
     * @param standardDeviation - smerodatna odchylka
     * @return nahodne cislo
     */
    public static double gauss(double meanValue, double standardDeviation) {
        double sum = 0.0;
        for (int i = 0; i < 12; i++)
            sum += Math.random();
        return (meanValue + standardDeviation * (sum - 6));
    }
    
    
    /**
     * Spusti test funkce {@link #gauss(double, double)}.
     * @param count - pocet generovanych cisel 
     * @param meanValue - stredni hodnota
     * @param standardDeviation - smerodatna odchylka
     */
    public static void testGauss(int count, double meanValue, double standardDeviation) {
        double e = 0.0, e2 = 0.0, d = 0.0;
        double random;
        int[] histogram = new int[HISTOGRAM_VALUES];
        double min = meanValue - 3 * standardDeviation;  // minimalni hodnota sledovana v histogramu
        double max = meanValue + 3 * standardDeviation;  // maximalni hodnota sledovana v histogramu
        double interval = (6 * standardDeviation) / histogram.length;  // delka jednoho intervalu v histogramu
        
        System.out.print("Spoustim test generatoru nahodnych cisel... ");

        for (int i = 0; i < count; i++) {
            random = gauss(meanValue, standardDeviation);
            e = (e * i + random) / (i + 1);             // prubezny vypocet E(X)
            e2 = (e2 * i + random * random) / (i + 1);  // prubezny vypocet E(X^2)
            d = e2 - e * e;                             // prubezny vypocet D(X)
            
            // aktualizace histogramu
            if (random > min && random < max) {
                int index = (int) ((random - min) / interval);
                histogram[index]++;
            }
        }
        
        System.out.println("hotovo\n\n*** Vysledky testu ***");
        System.out.println("Stredni hodnota E(X)");
        System.out.println("    pozadovana: " + meanValue);
        System.out.println("    test:       " + e);
        System.out.println("Smerodatna odchylka");
        System.out.println("    pozadovana: " + standardDeviation);
        System.out.println("    test:       " + Math.pow(d, 0.5) + "\n");
        printHistogram(histogram, min, interval);        
    }
    
    
    /**
     * Tiskne hvezdickovy histogram do konzole.
     * @param histogram pole predstavujici pocty hodnot v histogramu
     * @param min minimalni hodnota zanesena v histogramu
     * @param interval delka jednoho intervalu v histogramu
     */
    private static void printHistogram(int[] histogram, double min, double interval) {
        int max = 0;
        for (int i = 0; i < histogram.length; i++)
            if (histogram[i] > max) max = histogram[i];
        int factor = max / HISTOGRAM_MAX_LENGTH;

        double val = min;
        for (int i = 0; i < histogram.length; i++) {
            System.out.format("%6.3f - %6.3f: ", val, val + interval);
            val += interval;
            for (int j = 0; j < histogram[i] / factor; j++)
                System.out.print("*");
            System.out.println();
        }
    }

}
