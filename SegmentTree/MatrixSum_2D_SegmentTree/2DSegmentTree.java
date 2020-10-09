class NumMatrix {
    
    
    class SegmentTree{
    int[] arr;
    int[] sgt;
    int[] lows;
    int[] highs;
    int N;

    public SegmentTree(SegmentTree sgtToCopy){
        this.arr = sgtToCopy.arr;
        //we make copy of the objects
        this.sgt = Arrays.stream(sgtToCopy.sgt).toArray();
        this.lows = Arrays.stream(sgtToCopy.lows).toArray() ;
        this.highs = Arrays.stream(sgtToCopy.highs).toArray();
        this.N = sgtToCopy.N;
    }

    public SegmentTree combine(SegmentTree sgt2){
        SegmentTree combinedSGT = new SegmentTree(this);
        combinedSGT.sgt = IntStream.range(0,sgt.length).map(i->sgt[i]+sgt2.sgt[i]).toArray();
        return combinedSGT;
    }
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
    
    
class SegmentLord{
    SegmentTree[] arr;
    SegmentTree[] sgt;
    int[] lows;
    int[] highs;
    int N;

    public SegmentLord(ArrayList<SegmentTree> array) {
        arr = new SegmentTree[array.size()];
        IntStream.range(0,array.size()).forEach(i->arr[i]=array.get(i));
        int N = 1;
        while (N < arr.length ) N *= 2;
        if (arr.length == 1) {
            N = 2;
        }
        this.N = N;
        sgt = new SegmentTree[2 * N];
        lows = new int[2 * N];
        highs = new int[2 * N];
        init_boundary(1, 0, N - 1);
        init_values();
    }

    public void init_boundary(int current_node, int low, int high) {
        lows[current_node] = low;
        highs[current_node] = high;

        if (low == high) {
            return;
        } else {
            init_boundary(2 * current_node, low, (high + low) / 2);
            init_boundary(2 * current_node + 1, ((high + low) / 2) + 1, high);
        }
    }

    public void init_values() {
        int i = 0;
        for (SegmentTree x : arr) {
            //we update the leaf here
            update_val(N + i, x);
            i += 1;
        }
    }

    public void update_val(int node_to_update_row,  SegmentTree segment) {
        sgt[node_to_update_row] = segment;
        update_up(node_to_update_row / 2);
    }

    public void update_val(int node_to_update_row, int index_node_to_update_col, int val) {
        sgt[node_to_update_row].update_val(index_node_to_update_col,val);
        update_up(node_to_update_row / 2);
    }


    public void update_up(int current_node) {

        SegmentTree sgt1 = sgt[2 * current_node];
        SegmentTree sgt2 = sgt[2 * current_node + 1];

        if(sgt1==null && sgt2 == null){
            //to need to update further, it means nothing has changed here
            return;
        }

        else if(sgt1==null){
            sgt[current_node] = new SegmentTree(sgt2);
        }

        else if(sgt2==null){
            sgt[current_node] = new SegmentTree(sgt1);
        }
        else {
            SegmentTree new_sgt = sgt1.combine(sgt2);
            sgt[current_node] = new_sgt;
        }


        if (current_node == 1) {
            return;
        }
        update_up(current_node / 2);
    }

    public int query(int low, int high, int lowColumn, int highColumn) {
        return query(1, low, high, lowColumn, highColumn);
    }

    public int query(int current_node, int low, int high, int lowColumn, int highColumn) {
        if (low <= lows[current_node] && highs[current_node] <= high) {
            return sgt[current_node].query(lowColumn, highColumn);
        } else if (low > highs[current_node] || high < lows[current_node]) {
            return 0;
        } else {
            return query(2 * current_node, low, high, lowColumn, highColumn) + query(2 * current_node + 1, low, high, lowColumn, highColumn);
        }
    }
}
    
    ArrayList<SegmentTree> SGTrows;
    SegmentLord segmentLord;

    public NumMatrix(int[][] matrix) {
        SGTrows = new ArrayList<>();
        for (int[] row : matrix) {
            SGTrows.add(new SegmentTree(row));
        }
        segmentLord = new SegmentLord(SGTrows);

    }

    public void update(int row, int col, int val) {
        segmentLord.update_val(row+segmentLord.N,col + SGTrows.get(0).N, val);
    }

    public int sumRegion(int row1, int col1, int row2, int col2) {
        int sum = 0;
        return segmentLord.query(row1, row2, col1, col2);
    }
    
}

/**
 * Your NumMatrix object will be instantiated and called as such:
 * NumMatrix obj = new NumMatrix(matrix);
 * obj.update(row,col,val);
 * int param_2 = obj.sumRegion(row1,col1,row2,col2);
 */