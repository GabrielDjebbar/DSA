class Solution {
    
    class SegmentTree{
    int[] sgt;
    int[] lows;
    int[] highs;

    public SegmentTree(int size){
        sgt = new int[size];
        lows = new int[size];
        highs = new int[size];
        init_boundaries(1,0,(size/2)-1);
    }

    public void init_boundaries(int current_node, int low, int high){
        if(current_node>sgt.length){
            return;
        }

        lows[current_node] = low;
        highs[current_node] = high;

        if(low==high){
            return;
        }

        init_boundaries(2*current_node,low,(high+low)/2);
        init_boundaries(2*current_node+1,((high+low)/2)+1,high);
    }

    public int query(int current_node, int low,int high){
        if(low<=lows[current_node]&&highs[current_node]<=high){
            return sgt[current_node];
        }
        else if(highs[current_node]<low||high<lows[current_node]){
            return 0;
        }

        else{
            int leftTree = query(2*current_node,low,high);
            int rightTree = query(2*current_node+1,low,high);
            return leftTree+rightTree;
        }
    }
    public void update_add(int leaf_index,int delta){
        /*leaf_index usually is the index in the array, but in this case this is simply the value we are targetting (you know, to count the number of occurence ...)
         */
        sgt[leaf_index] += delta;
        update_up(leaf_index/2);
    }
    public void update_up(int node_index){
        if(node_index==0){
            return;
        }
        else{
            sgt[node_index]=sgt[2*node_index]+sgt[2*node_index+1];
            update_up(node_index/2);
        }
    }
}
    
    public  boolean isIdealPermutation(int[] A) {
            int nb_local_inv = count_local_inv(A);
            int nb_global_inv = count_global_inv(A);
            if(nb_local_inv==nb_global_inv){
                return true;
            }
            else{
                return false;
            }
        }

        public  int count_local_inv(int[] A){
            int total=0;
            for(int i=1;i<A.length;i++){
                if(A[i-1]>A[i]){
                    total += 1;
                }
            }
            return total;
        }

        public  int count_global_inv(int[] A){

            int N = 1;
            while(N<A.length) N*=2;
            SegmentTree sgt = new SegmentTree(2*N);

            int sum = 0;
            for(int x:A){
                sum += sgt.query(1,x+1,A.length-1);
                sgt.update_add(N+x,1);
            }
            return sum;
        }
}