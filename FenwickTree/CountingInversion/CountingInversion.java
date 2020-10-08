class Solution {
    //input example [0,3,2,4,5,1]
    // will be a permutation of [0, 1, ..., A.length - 1].
    // otherwise for random array, solution will not work  (as explained below)
    class FenwickTree{
        int[] fenwick ;
        public FenwickTree(int size){
            fenwick = new int[size];
        }
        
        public void update_add(int index, int delta){
            fenwick[index] += delta;
            index = index + Integer.lowestOneBit(index);
            while(index<fenwick.length){
                fenwick[index] += delta;
                index = index + Integer.lowestOneBit(index);
            }
        }
        
        public void update_value(int index, int value){
            ;
        }
        
        public int query_sum_prefix(int index){
            int sum = 0;
            while(index>0){
                sum += fenwick[index];
                index = index - Integer.lowestOneBit(index);
            }
            return sum;
        }
    }
    public boolean isIdealPermutation(int[] A) {
        int nb_local_inv = count_local_inv(A);
        int nb_global_inv = count_global_inv(A);
        if(nb_local_inv==nb_global_inv){
            return true;
        }
        else{
            return false;
        }
    }
    
    public int count_local_inv(int[] A){
        int total=0;
        for(int i=1;i<A.length;i++){
            if(A[i-1]>A[i]){
                total += 1;
            }
        }
        return total;
    }
    
    public int count_global_inv(int[] A){
        //Actually this would work in this case, because we know the input guarantees us that A.length == max(A)-1
        FenwickTree ft = new FenwickTree(A.length+1);
        //But in the more general case where we don't the know the value of the max element we need to increase the array size
        //Fenwick ft = new FenwickTree(Arrays.stream(A).min().getAsInt());
        
        int sum = 0;
        for(int x:A){
            sum += ft.query_sum_prefix(A.length)-ft.query_sum_prefix(x+1);
            ft.update_add(x+1,1);
        }
        return sum;
    }
}