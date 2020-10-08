class NumMatrix {
    //O(nlogn) implementation using a list of Segment Tree
    class SegmentTree{
    int[] arr;
    int[] sgt;
    int[] lows;
    int[] highs;
    int N;

    public SegmentTree(int[] array){
        arr = array;
        int N = 1;
        while(N < array.length) N*=2;
        if(array.length==1){
            N = 2;
        }
        this.N = N;
        sgt = new int[2*N];
        lows = new int[2*N];
        highs = new int[2*N];
        init_boundary(1,0,N-1);
        init_values();
    }

    public void init_boundary(int current_node, int low, int high){
        lows[current_node]=low;
        highs[current_node]=high;

        if(low==high){
            return;
        }
        else{
            init_boundary(2*current_node,low,(high+low)/2);
            init_boundary(2*current_node+1,((high+low)/2)+1,high);
        }
    }

    public void init_values(){
        int i = 0;
        for(int x:arr){
            update_add(N+i,x);
            i += 1;
        }
    }

    public void update_val(int index_node_to_update, int val){
        sgt[index_node_to_update] = val;
        update_up(index_node_to_update/2);
    }

    public void update_add(int index_node_to_update, int delta){
        sgt[index_node_to_update] += delta;
        update_up(index_node_to_update/2);
    }

    public void update_up(int current_node){
        sgt[current_node] = sgt[2*current_node] + sgt[2*current_node +1];
        if(current_node==1){
            return;
        }
        update_up(current_node/2);
    }

    public int query(int low, int high){
        return query(1, low, high);
    }

    public int query(int current_node, int low, int high){
        if(low<=lows[current_node] && highs[current_node]<= high){
            return sgt[current_node];
        }
        else if(low>highs[current_node] ||high<lows[current_node]){
            return 0;
        }
        else{
            return query(2*current_node,low,high) + query(2*current_node+1,low,high);
        }
    }
}
    
    
    
    ArrayList<SegmentTree> arr;
    public NumMatrix(int[][] matrix) {
        this.arr = new ArrayList<>();
        for(int[] row: matrix){
            this.arr.add(new SegmentTree(row));
        }

    }

    public void update(int row, int col, int val) {
        this.arr.get(row).update_val(col+this.arr.get(row).N,val);
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        for(int i=row1;i<=row2;i++){
            sum += arr.get(i).query(col1,col2);
        }
        return sum;
    }
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */