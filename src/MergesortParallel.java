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
    private int begin, end;
    private int thresh;
    
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
            int mid = (begin + (end - begin)/2);
            invokeAll(      //Executes the given tasks, returning a list of Futures holding their status and results when all complete. Future.isDone() is true for each element of the returned list.
                    new MergesortParallel(arr, begin, mid, thresh),
                    new MergesortParallel(arr, mid, end, thresh)
            );
            
            //Sequential merge of the two halves
            merge(mid);
            
        }
            
        
    }
    
    private void merge(int m){
        int[] clone = arr.clone();
        int firstLeft = 0;
        int lastLeft = m-1;
        int firstRight = m;
        int lastRight = arr.length-1;
        int current = 0;
        System.out.println(firstLeft+" "+lastLeft+" - "+firstRight+" "+lastRight);
        //System.out.println(Arrays.toString(clone));
        while(firstLeft <= lastLeft && firstRight <= lastRight){
            //System.out.println(Arrays.toString(arr));
            if(clone[firstLeft]<= clone[firstRight]){
                arr[current] = clone[firstLeft];
                current++;
                firstLeft++;
            }
            else{
                arr[current] = clone[firstRight];
                current++;
                firstRight++;
            }
            
        }
        if(firstLeft <= lastLeft){
            arr[current] = clone[firstLeft];
        }
        else if(firstRight <= lastRight){
            arr[current] = clone[firstRight]; 
       }
        
    }
    
}
