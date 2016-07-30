import java.util.Arrays;
import java.util.concurrent.*;

/**
 * A Mergesort algorithm that uses Java's lightweight threads from the Fork/Join Framework
 * to implement parallelism for the purpose of speedup.
 * @author gianlucatruda
 * Created 26 July 2016
 */
public class MergesortParallel extends RecursiveAction{
    //Declaration of the private variables for a Mergesort object
    private int[] arr;
    private int[] clone;
    private final int begin, end, thresh;
    
    //Constructor method for MergesortParallel
    public MergesortParallel(int[] a, int begin, int end, int threshold){
        this.arr = a;
        this.begin = begin;
        this.end = end;
        this.thresh = threshold;
    }
    
    //Override for the compute() method implemented from RecursiveAction (main body of code)
    @Override
    protected void compute() {
        //If statement to catch the instance where the threshold has been reached and to perform a sequential sort.
        if((end-begin) <= thresh){
            //A system-implemented sequential sort over the range provided.
            Arrays.sort(arr, begin, end);
        }
        else{
            //Parallel code to sort the two halves in parallel
            int mid = begin + (end - begin)/2;
            invokeAll(new MergesortParallel(arr, begin, mid, thresh),new MergesortParallel(arr, mid, arr.length, thresh));
            
            //Sequential merge of the two halves
            this.clone = arr.clone();
            merge(mid);
        }
    }
    
    private void merge(int m){
        int firstLeft = begin;
        int lastLeft = m-1;
        int firstRight = m;
        int lastRight = arr.length-1;
        int current = begin;
        while(firstLeft <= lastLeft && firstRight <= lastRight){
            if(clone[firstLeft]<= clone[firstRight]){
                arr[current++] = clone[firstLeft++];
            }
            else{
                arr[current++] = clone[firstRight++];
            }
        }
        while(firstLeft<=lastLeft){
            arr[current++] = clone[firstLeft++];
        }
           
        while(firstRight<=lastRight){
            arr[current++] = clone[firstRight++];
        } 
    }
}
