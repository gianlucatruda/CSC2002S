/**
 * A JUnit test to ensure that the corresponding sorting algorithm provides the correct output.
 */

import java.util.Arrays;
import java.util.Random;
import org.junit.Test;

/**
 *
 * @author gianlucatruda
 */
public class TestMergesortParallel {

    @Test
    public void testCompute() {
        System.out.println("Mergesort");
        final int ARRLEN = 400000;
        final int THRESH = 200000;
        Random rand = new Random();
        
        //Test on a small known array
        int[] sampleArr = {27, 43, 3, 9, 82, 10, 38};
        int[] sampleComp = sampleArr.clone();
        Arrays.sort(sampleComp);
        MergesortParallel MSP = new MergesortParallel(sampleArr, 0, sampleArr.length, 4);
        MSP.compute();
        System.out.println("Small sample: "+Arrays.toString(sampleArr)+"");
        assert(Arrays.equals(sampleArr, sampleComp));
        
        //Test on a large, randomised array
        int[] arr = new int[ARRLEN];
        for(int i=0; i<ARRLEN; i++){
            arr[i]=rand.nextInt(ARRLEN);
        }
        int[] sysArr = arr.clone();
        Arrays.sort(sysArr);
        MergesortParallel msp = new MergesortParallel(arr, 0, ARRLEN, THRESH);
        msp.compute();
        int trueCount = 0;
        for(int i=0; i<ARRLEN; i++){
            if(sysArr[i]==arr[i]){trueCount++;}
        }
         System.out.println("Large sample (400 000 vals) - Correctness: "+(trueCount*100/ARRLEN)+"%");
        assert(Arrays.equals(sysArr, arr));
    }
    
}
