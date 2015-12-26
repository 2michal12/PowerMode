package powermode;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.BitSet;
import jxl.write.WriteException;

public class PowerMode {

    private static long start = 0, diff = 0; // nemenit

    private static final BigInteger n = new BigInteger("120000"); //modul n
    private static BigInteger d = new BigInteger("600100"); //exponent d (n > d > n/2)
    private static BigInteger m = new BigInteger("100000"); //správa m (n > m > n/2).

    private static final Integer N = 1000; // N musi byt vacsie ako K .. je to docasne prerobim to
    private static final Integer K = 900;
    
    private static BigInteger e0, e1, e2, e3;

    public static void main(String[] args) throws IOException, WriteException {
        exponent_e(); //vypocita exponenty pre dane "d"
        //pri ulohe4 zmenim potrebny Ei exponent inak zakomunetujem priradnie d = Ei;
        d = e0;
        
        double[] uloha3 = new double[N];
        long sum = 0;
        int it = 0;
        double n_double = n.doubleValue();
        
        for (int i = 1; i <= N * K; i++) {
            if ((i % K == 0) && (i != 0)) {     
                uloha3[it] = sum / K;
                it++;
                sum = 0;
                //random dlzka spravy m pre ulohu 3b
                //m = new BigDecimal((Math.random() * (n_double - n_double/2)) + n_double/2 ).toBigInteger();
            }
            PowerMod();
            sum += diff;  
        }
        
        WriteExcel excel = new WriteExcel("statistic.xls", uloha3, N);
        System.out.println("Zapisane v subore 'statistic.xls'");
    }

    private static void PowerMod() {
        start();
        m.modPow(d, n);
        diff();
    }
    
    private static void exponent_e(){
        e0 = d;
        e1 = d;
        e2 = d;
        e3 = d;
        
        int i1 = (d.bitLength()/2 );
        int i2 = (d.bitLength()/2 )+1;
        
        System.out.println("d : "+d.toString(2)+" "+d);
        
        //e0 = 0,0
        e0 = unsetBit(e0, i1);
        e0 = unsetBit(e0, i2);
        //e1 = 0,1
        e1 = setBit(e1, i1);
        e1 = unsetBit(e1, i2);
        //e2 = 1,0
        e2 = unsetBit(e2, i1);
        e2 = setBit(e2, i2);
        //e3 = 1,1
        e3 = setBit(e3, i1);
        e3 = setBit(e3, i2);

        System.out.println("e1: "+e0.toString(2)+" "+e0);
        System.out.println("e2: "+e1.toString(2)+" "+e1);  
        System.out.println("e3: "+e2.toString(2)+" "+e2);
        System.out.println("e4: "+e3.toString(2)+" "+e3);
    }
    
    private static BigInteger setBit(BigInteger x, int i){
        BigInteger one = BigInteger.valueOf(1);
        return x.or( one.shiftLeft(i) );
    }
    
    private static BigInteger unsetBit(BigInteger x, int i){
        BigInteger one = BigInteger.valueOf(1);
        return x.and( (one.shiftLeft(i)).not() );
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
