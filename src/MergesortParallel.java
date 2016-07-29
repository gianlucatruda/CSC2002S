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
    private final int begin, end;
    private final int thresh;
    
    //Constructor method for MergesortParallel
    public MergesortParallel(int[] a, int begin, int end, int threshold){
        this.arr = a;
        this.clone = arr.clone();
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
            //System.out.println("Sys");
        }
        else{
            //Parallel code to sort the two halves in parallel
            int mid = (begin + (end - begin)/2);
            //System.out.println("T1\t"+begin+"\t"+mid+"\t"+thresh);
            //System.out.println("T2\t"+mid+"\t"+end+"\t"+thresh);
            invokeAll(      //Executes the given tasks, returning a list of Futures holding their status and results when all complete. Future.isDone() is true for each element of the returned list.
                    new MergesortParallel(arr, begin, mid, thresh),
                    new MergesortParallel(arr, mid, end, thresh)
            );
            
            //Sequential merge of the two halves
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
            System.out.println(Arrays.toString(arr));
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
            while(firstLeft<=lastLeft){
                arr[current] = clone[firstLeft];
                current++;
                firstLeft++;
            }
            
        }
        else if(firstRight <= lastRight){
            while(firstRight<=lastRight){
                //System.out.println(firstRight+":"+lastRight);
                arr[current] = clone[firstRight];
                current++;
                firstRight++;
            }
        }
        System.out.println(firstLeft + ":"+ lastLeft+ " "+ firstRight +":"+lastRight);
       
    }
    
}
