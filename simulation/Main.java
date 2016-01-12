package simulation;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import cz.zcu.fav.kiv.jsim.JSimException;
import generator.RandomNumber;
import simulation.QueingNetworkSimulation.Distribution;


/**
 * Hlavni trida aplikace s funkci main. Slouzi ke spousteni programu.
 *
 * @author Miroslav Vozabal
 */
public class Main {
    
    /** Defaultni pocet pozadavku, ktere se nechaji projit simulaci. */
    public static final int DEFAULT_TRANSACTION_COUNT = 100000;
    
    /** Koeficient variace pro prvni spusteni simulace s normalnim rozdelenim. */
    public static final double DEFAULT_COEF_1 = 0.02;
    
    /** Koeficient variace pro druhe spusteni simulace s normalnim rozdelenim. */
    public static final double DEFAULT_COEF_2 = 0.3;
    
    /** Koeficient variace pro treti spusteni simulace s normalnim rozdelenim. */
    public static final double DEFAULT_COEF_3 = 0.7;
    
    /** Nazev konfiguracniho souboru s parametry site front. */
    public static final String CONFIG_FILE = "res\\simulation.properties";
    
    /** Horizontalni oddelovac (pro ucely vypisu do konzole). */
    public static final String LINE = "\n---------------------------------------------------------------------\n";
    
    /** Zvyrazneni nadpisu (pro ucely vypisu do konzole). */
    public static final String HIGHLIGHT = "=======";
    
    
    /*
     * Vypnuti logovani z JSimu.
     */
    static {
        Logger.getLogger("").setLevel(Level.OFF);
    }
    
    
    /**
     * Hlavni funkce programu. Parsuje argumenty prikazove radky a spousti simulaci
     * s pozadovanymi parametry.
     * 
     * @param args argumenty prikazove radky
     */
    public static void main(String[] args) {
        if (args.length == 2) {
            try {
                int transactionCount = Integer.parseInt(args[0]);
                if (args[1].equalsIgnoreCase("EXP"))
                    runSimulation(Distribution.EXPONENTIAL, transactionCount);
                else if (args[1].equalsIgnoreCase("GAUSS"))
                    runSimulation(Distribution.GAUSSIAN, transactionCount);
                else
                    printUsage();
            } catch (NumberFormatException e) {
                printUsage();
                return;
            }
        } else if (args.length == 0) {
            runSimulation(Distribution.EXPONENTIAL, DEFAULT_TRANSACTION_COUNT);
            System.out.println(LINE);
            runSimulation(Distribution.GAUSSIAN, DEFAULT_TRANSACTION_COUNT);
        } else if (args.length == 4) {
            if (args[0].equals("--test-generator")) {
                try {
                    int count = Integer.parseInt(args[1]);
                    double meanValue = Double.parseDouble(args[2]);
                    double standardDeviation = Double.parseDouble(args[3]);
                    RandomNumber.testGauss(count, meanValue, standardDeviation);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    printUsage();
                }
            } else {
                printUsage();
            }
        } else {
            printUsage();
        }
    }
    
    
    /**
     * Nacte z konfiguracniho souboru parametry site front a spusti simulaci pro dany pocet
     * pozadavku. Pokud je parametr distribution roven 
     * {@link cz.zcu.kiv.vsp.simulation.QueingNetworkSimulation.Distribution#GAUSSIAN Distribution.GAUSSIAN},
     * spusti simulaci trikrat s ruznymi koeficienty variace.
     *  
     * @param distribution rozdeleni nahodnych velicin
     * @param transactionCount pocet pozadavku ktere projdou simulaci
     */
    private static void runSimulation(Distribution distribution, int transactionCount) {
        try {
            SimulationParameters parameters = loadParameters();
            parameters.setDistribution(distribution);
            if (distribution == Distribution.EXPONENTIAL) {
                runSimulation(parameters, transactionCount);
            } else {
                parameters.setC(DEFAULT_COEF_1);
                runSimulation(parameters, transactionCount);
                System.out.println(LINE);
                parameters.setC(DEFAULT_COEF_2);
                runSimulation(parameters, transactionCount);
                System.out.println(LINE);
                parameters.setC(DEFAULT_COEF_3);
                runSimulation(parameters, transactionCount);
            }
        } catch (JSimException e) {
            e.printStackTrace();
            e.printComment(System.err);
        } catch (IOException | NumberFormatException e) {
            System.err.println("Nepodarilo se nacist parametry simulace z konfiguracniho souboru.");
            e.printStackTrace();
        }
    }
    
    
    /**
     * Spusti simulaci s danymi parametry.
     * 
     * @param parameters parametry simulace
     * @param transactionCount pocet pozadavku, ktere projdou simulaci
     * @throws JSimException
     */
    private static void runSimulation(SimulationParameters parameters, int transactionCount) throws JSimException {
        System.out.println("Rozdeleni nahodnych velicin: " + 
                (parameters.getDistribution() == Distribution.EXPONENTIAL ? "EXPONENCIALNI" : "NORMALNI"));
        if (parameters.getDistribution() == Distribution.GAUSSIAN)
            System.out.println("Koeficient variace: " + parameters.getC());
        
        QueingNetworkSimulation simulation = new QueingNetworkSimulation(parameters);
        SimulationStatistics stats = simulation.run(transactionCount);
        printStatistics(stats);
    }
    
    
    /**
     * Tiskne instrukce k pouziti programu.
     */
    private static void printUsage() {
        System.out.println("Pouziti: qn-simulation [ <pocetPozadavku> { EXP | GAUSS } ]");
        System.out.println("         qn-simulation --test-generator <pocetCisel> <stredniHodnota> <smerodatnaOdchylka>\n");
        System.out.println("Pri spusteni bez parametru se spusti simulace pro 100 000 pozadavku nejdrive s exponencialnim\n"
                + "a pote s normalnim rozdelenim nahodnych velicin.");
        System.out.println("Prepinac --test-generator slouzi ke spusteni testu generatoru nahodnych cisel s normalnim rozdelenim.");
    }
    
    
    /**
     * Tiskne statistiky namerene pri simulaci.
     * @param stats namerene statistiky
     */
    private static void printStatistics(SimulationStatistics stats) {
        System.out.println("Namerene statistiky:");
        
        System.out.println("\n" + HIGHLIGHT + " Server 1 " + HIGHLIGHT);
        System.out.println("frekvence toku = " + stats.getThroughputRate(0));
        System.out.println("zatizeni = " + stats.getLoad(0));
        System.out.println("Tq = " + stats.getTqi(0));
        System.out.println("Lq = " + stats.getLqi(0));
        
        System.out.println("\n" + HIGHLIGHT + " Server 2 " + HIGHLIGHT);
        System.out.println("frekvence toku = " + stats.getThroughputRate(1));
        System.out.println("zatizeni = " + stats.getLoad(1));
        System.out.println("Tq = " + stats.getTqi(1));
        System.out.println("Lq = " + stats.getLqi(1));
        
        System.out.println("\n" + HIGHLIGHT + " Server 3 " + HIGHLIGHT);
        System.out.println("frekvence toku = " + stats.getThroughputRate(2));
        System.out.println("zatizeni = " + stats.getLoad(2));
        System.out.println("Tq = " + stats.getTqi(2));
        System.out.println("Lq = " + stats.getLqi(2));
        
        System.out.println("\n" + HIGHLIGHT + " Server 4 " + HIGHLIGHT);
        System.out.println("frekvence toku = " + stats.getThroughputRate(3));
        System.out.println("zatizeni = " + stats.getLoad(3));
        System.out.println("Tq = " + stats.getTqi(3));
        System.out.println("Lq = " + stats.getLqi(3));
        
        System.out.println("\n" + HIGHLIGHT + " Cely system " + HIGHLIGHT);
        System.out.println("Tq = " + stats.getTq());
        System.out.println("Lq = " + stats.getLq());
        
        System.out.println("\n" + HIGHLIGHT + " Doba cinnosti serveru 2 " + HIGHLIGHT);
        System.out.println("E(X) = " + stats.getOperationStatsServer2().getMeanValue());
        System.out.println("D(X) = " + stats.getOperationStatsServer2().getVariance());
        System.out.println("\n" + stats.getOperationStatsServer2().getHistogram());
    }
    
    
    /**
     * Nacte parametry site front z konfiguracniho souboru {@link #CONFIG_FILE}.
     * 
     * @return parametry site front
     * @throws FileNotFoundException
     * @throws IOException
     * @throws NumberFormatException
     */
    private static SimulationParameters loadParameters() throws FileNotFoundException, IOException, NumberFormatException {
        Properties prop = new Properties();
        prop.load(Main.class.getClassLoader().getResourceAsStream(CONFIG_FILE));

        SimulationParameters params = new SimulationParameters();
        params.setLambda1(Double.parseDouble(prop.getProperty("lambda1")));
        params.setLambda2(Double.parseDouble(prop.getProperty("lambda2")));
        params.setTs1(Double.parseDouble(prop.getProperty("Ts1")));
        params.setTs2(Double.parseDouble(prop.getProperty("Ts2")));
        params.setTs3(Double.parseDouble(prop.getProperty("Ts3")));
        params.setTs4(Double.parseDouble(prop.getProperty("Ts4")));
        params.setP2(Double.parseDouble(prop.getProperty("p2")));
        params.setP3(Double.parseDouble(prop.getProperty("p3")));
        
        return params;
    }
   

}
