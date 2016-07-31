
import java.util.concurrent.RecursiveAction;

/**
 * A class to facilitate parallelized prefix sum operation.
 * @author gianlucatruda
 * Created 31 July 2016
 */
public class PrefixSumParallel extends RecursiveAction {
    private int[] arr;
    private int[] out;
    private int start, end;
    private PrefixSumParallel parent;
    private int thresh = 2;
    private int fromLeft=0;
    private int sum =0;
    private boolean root = false;
    private boolean leaf = false;
    private PrefixSumParallel leftC;
    private PrefixSumParallel rightC;
    //Constructor method
    public PrefixSumParallel(int[] a, int[] output, int start, int end, PrefixSumParallel parent, int thresh, boolean isRoot){
        this.arr = a;
        this.out = output;
        this.start = start;
        this.end = end;
        this.parent = parent;
        this.thresh = thresh;
        this.root = isRoot;
}

    @Override
    protected void compute() {
        if((end-start)<= thresh){
            this.leaf = true;
            for (int i = start; i <= end; i++) {
                this.sum+=arr[i];
                this.out[i]=this.sum;
            }
        }
        else{
            int piv = (end-start)/2;
            leftC = new PrefixSumParallel(this.arr, this.out, start, piv, this, thresh, false);
            rightC = new PrefixSumParallel(this.arr, this.out, piv, end, this, thresh, false);
            invokeAll(leftC, rightC);
            this.sum = leftC.getSum()+rightC.getSum();
            leftC.setFromLeft(this.fromLeft);
            rightC.setFromLeft(leftC.getSum() + this.fromLeft);
        }
        
    }
    
    /**
     * Returns the sum of the elements in the chunk of array provided to it.
     * @return 
     */
    private int getFromLeft(){
        return this.fromLeft;
    }
    
    private int getSum(){
        return this.sum;
    }
    
    private void setFromLeft(int val){
        if(this.isRoot() == false){
            this.fromLeft = val;
        }
    }
    
    public boolean isRoot(){
        return root;   
    }
    
    public void apply(){
        if(this.leaf==true){
            //System.out.println("Leaf:\t"+this.fromLeft+"\t"+this.sum);
            out[start] = this.fromLeft + this.arr[start];
            for (int i = start+1; i <=end; i++) {
                this.out[i] = this.out[i-1] + this.arr[i];
            }
        }
        else{
            //System.out.println("Node:\t"+this.fromLeft+"\t"+this.sum);
            leftC.apply();
            rightC.apply();
        }
    }
    
}
