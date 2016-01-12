package simulation;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


/**
 * Zachycuje statistiky nahodne veliciny X - poskytuje stredni hodnotu E(X),
 * rozptyl D(X), smerodatnou odchylku &sigma;(X) a histogram.
 *
 * @author Miroslav Vozabal
 */
public class Statistics {
    
    /** Jemnost rozliseni hodnot v histogramu (cim vyssi cislo, tim vetsi jemnost rozliseni). */
    public static final int PRECISION_FACTOR = 1000;
    
    /** Pocet radek histogramu vypisovaneho do konzole. */
    public static final int HISTOGRAM_LINES = 16;
    
    /** Maximalni pocet hvezdicek u hodnoty v histogramu vypisovaneho do konzole. */
    public static final int HISTOGRAM_SIZE = 75;
    
    
    /** Pocitadlo realizaci nahodne veliciny. */
    private int counter = 0;
    
    /** Soucet vsech realizaci nahodne veliciny. */
    private double sum = 0.0;
    
    /** Stredni hodnota. */
    private double meanValue;
    
    /** Pomocna promenna obsahujici hodnotu E(X^2) - sluzi pro vypocet D(X). */
    private double ex2;
    
    /** Mapa poctu realizaci X s danou hodnotou - histogram. */
    private Map<Integer, Integer> histogram = new HashMap<>();
    
    
    
    /**
     * Prida realizaci nahodne veliciny.
     * @param value - realizace nahodne veliciny
     */
    public void addValue(double value) {
        sum += value;
        meanValue = (meanValue * counter + value) / (counter + 1);     // prubezny vypocet E(X)
        ex2 = (ex2 * counter + value * value) / (counter + 1);         // prubezny vypocet E(X^2)
        counter++;
        
        /* aktualizace histogramu */
        int key = (int) (value * PRECISION_FACTOR);
        if (histogram.containsKey(key))
            histogram.put(key, histogram.get(key) + 1);
        else
            histogram.put(key, 1);
    }
    
    
    /**
     * Vytvori retezec obsahujici aktualni histogram. Histogram je upraveny, aby mel maximalni pocet
     * radek {@link #HISTOGRAM_LINES} a maximalni pocet znaku zobrazenych u nejake hodnoty byl {@link #HISTOGRAM_SIZE}.
     * @return retezec obsahujici hvezdickovy histogram
     */
    public String getHistogram() {
        StringBuilder builder = new StringBuilder();
        Integer count;
        int step = histogram.size() / HISTOGRAM_LINES;
        if (step == 0) step = 1;
        int meanVal = (int) (meanValue * PRECISION_FACTOR);
        int first = meanVal - (HISTOGRAM_LINES / 2) * step;
        int last = meanVal + (HISTOGRAM_LINES / 2) * step;
        int factor = Collections.max(histogram.values()) / HISTOGRAM_SIZE + 1;

        for (int i = first; i <= last; i += step) {
            if (i < 0) continue;
            builder.append(String.format("%.3f: ", ((double) i / PRECISION_FACTOR)));
            count = histogram.get(i);
            if (count != null) {
                for (int j = 0; j < count / factor; j++)
                    builder.append("*");
                builder.append("  (" + count + ")");
            }
            builder.append('\n');
        }
        
        return builder.toString();
    }
    
    
    /**
     * Vraci stredni hodnotu E(X) sledovane veliciny X.
     * 
     * @return stredni hodnota E(X)
     */
    public double getMeanValue() {
        return meanValue;
    }
    
    
    /**
     * Vraci rozptyl D(X) sledovane veliciny X.
     * 
     * @return rozptyl D(X)
     */
    public double getVariance() {
        return ex2 - meanValue * meanValue;
    }
    
    
    /**
     * Vraci smerodatnou odchylku &sigma;(X) sledovane veliciny X.
     *  
     * @return smerodatna odchylka &sigma;(X)
     */
    public double getStandardDeviation() {
        return Math.pow(getVariance(), 0.5);
    }
    
    
    /**
     * Vraci soucet vsech hodnot sledovane veliciny X.
     * 
     * @return soucet vsech hodnot
     */
    public double getSum() {
        return sum;
    }

}
