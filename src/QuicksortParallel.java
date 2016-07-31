import java.util.Arrays;
import java.util.concurrent.*;
import java.util.Random;

/**
 * A quicksort algorithm that uses Java's lightweight threads from the Fork/Join Framework
 * to implement parallelism for the purpose of speedup.
 * Both the recursive call step AND the partitioning step are parallelized!
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class QuicksortParallel extends RecursiveAction{
    //Declaration of private variables for a Quicksort object
    private int[] arr;
    private final int begin, end, thresh;
    private int pivot;
    
    //Constructor method
    public QuicksortParallel(int[] a, int thresh){
        this.arr = a;
        this.begin = 0;
        this.end = arr.length;
        this.thresh = thresh;
        this.pivot = (end-begin)/2 +1;
}
    //Override for the compute() method implemented from RecursiveAction
    @Override
    protected void compute() {
        //If the array provided is smaller than the threshold, then a sequential sort is performed.
        if((end-begin)<= thresh){
            Arrays.sort(arr, begin, end);
        }
        else{
            //this.pivot = getMedian();
            //Perform a parallel pack
            this.pack(arr[pivot]);
        }
    }

    /**
     * A pack algorithm using the bit-bitsum-output method to enable parallelization.
     */
    private void pack(int val){
        int[] bits = new int[end+1];
        int[] bitsum = new int[end+1];
        for (int i = 0; i <end; i++) {
            if(arr[i]>= val){
                bits[i] = 1;
            }
            else if(arr[i]<val){
                bits[i] = 0;
            }
        }
        
        //Parallelised Prefix-Sum Algorithm
        PrefixSumParallel pfp = new PrefixSumParallel(bits, bitsum, begin, end, null, 4, true);
        invokeAll(pfp);
        pfp.apply();
        
        //Create and populate the array of items creater than pivot value
        int[] right = new int[bitsum[bitsum.length-1]];
        int[] left = new int[(end)-right.length];
        int count = 0;
        for (int i = 0; i <arr.length; i++){          
            //System.out.println("i: "+i+"\tbits[i]: "+bits[i]+"\tarr[i]: "+arr[i]+"\tcount: "+count+"\tleft.len: "+left.length);
            if(bits[i]==1){
                right[bitsum[i]-1] = arr[i];
            }
            else{
                left[count++] = arr[i];
            }
        }
        System.out.println(Arrays.toString(left)+"-"+val+"-"+Arrays.toString(right));
        
        invokeAll(  new QuicksortParallel(left, thresh),
                    new QuicksortParallel(right, thresh));
        
        int[] out = new int[left.length+right.length];
        System.arraycopy(left, 0, out, 0, left.length);
        System.arraycopy(right, 0, out, left.length, right.length);
        
        this.arr = out.clone();
    }
    
 
    
    
    /**
     * Estimates the median of the array in O(c) time and returns the index of the median.
     * @return pivot
     */
    private int getMedian(){
        //Sneaky trick to select a pretty good privot in O(1) time.
        Random rand = new Random();
        int[] medianator = new int[3];
        for (int i = 0; i <3; i++) {
            medianator[i] = rand.nextInt(end);
        }
        int hi = medianator[0];
        int lo = medianator[0];
        int piv = medianator[0];
        for (int i = 0; i < 3; i++) {
            if(arr[medianator[i]]<= arr[hi] && arr[medianator[i]]>=arr[lo]){
                piv = medianator[i];
            }
            if(arr[medianator[i]]>=arr[hi]){
                hi = medianator[i];
            }
            if(arr[medianator[i]]<=arr[lo]){
                lo = medianator[i];
            }
        }
        //System.out.println(arr[medianator[0]]+";"+arr[medianator[1]]+";"+arr[medianator[2]]+" :\t"+pivot+"\t"+arr[pivot]);
        return piv;
    }
    
}
