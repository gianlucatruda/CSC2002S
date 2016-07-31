/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Random;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author gianlucatruda
 */
public class TestQuicksortParallel {
    
    public TestQuicksortParallel() {
    }

    @Test
    public void testCompute() {
        final int ARRLEN = 400000;
        final int THRESH = 200000;
        Random rand = new Random();
        int[] sampleArr = {27, 43, 3, 9, 82, 10, 38};
        QuicksortParallel QSP = new QuicksortParallel(sampleArr, 0, 6, 4);
        QSP.compute();
        System.out.println(Arrays.toString(sampleArr)+"\n\n");
        
        int[] arr = new int[ARRLEN];
        for(int i=0; i<ARRLEN; i++){
            arr[i]=rand.nextInt(ARRLEN);
        }
        int[] sysArr = arr.clone();
        Arrays.sort(sysArr);
        QuicksortParallel qsp = new QuicksortParallel(arr, 0, ARRLEN, THRESH);
        qsp.compute();
        int trueCount = 0;
        for(int i=0; i<ARRLEN; i++){
            if(sysArr[i]==arr[i]){trueCount++;}
        }
        System.out.println("Correctness: "+(trueCount*100/ARRLEN)+"%");
        assert(Arrays.equals(sysArr, arr));
    }

 }
    
