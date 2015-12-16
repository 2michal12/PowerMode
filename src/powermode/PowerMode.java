package powermode;

import java.io.IOException;
import java.math.BigInteger;
import jxl.write.WriteException;

public class PowerMode {

    private static long start = 0, diff = 0;

    private static final BigInteger n = new BigInteger("120000"); //modul n
    private static final BigInteger d = new BigInteger("7"); //exponent d (n > d > n/2)
    private static final BigInteger m = new BigInteger("100000"); //správa m (n > m > n/2).

    private static final Integer N = 1000;
    private static final Integer K = 900;

    public static void main(String[] args) throws IOException, WriteException {
        double[] uloha3 = new double[N];
        long sum = 0;
        int it = 0;

        for (int i = 0; i < N * K; i++) {
            if ((i % K == 0) && (i != 0)) {     
                uloha3[it] = sum / K;
                it++;
                sum = 0;
            }

            PowerMod();

            sum += diff;
        }
        
        WriteExcel excel = new WriteExcel("statistic.xls", uloha3, K);
        System.out.println("Zapisane v subore 'statistic.xls'");
    }

    private static void PowerMod() {
        start();
        m.modPow(d, n);
        diff();
    }

    private static void start() {
        start = System.nanoTime();
    }

    private static void diff() {
        diff = System.nanoTime() - start;
        //printTime(); 
    }

    private static void printTime() {
        //System.out.println("Elapsed time: ");
        //System.out.printf("Seconds: \t%f%n",diff / 1000000000.0);
        //System.out.printf("Milliseconds: \t%f%n",diff / 1000000.0);
        System.out.println("Nanoseconds: \t" + diff);
    }

}
