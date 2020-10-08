class SegmentTree{
        int[] st;
        
        public SegmentTree(int nb_nodes){
            st = new int[nb_nodes];
        }
        public int update(int leaf_index, int value){
            st[leaf_index] = value;
            return update_up(leaf_index/2);
        }
        public int update_up(int current_node_index){
            if(current_node_index==1){
                return st[current_node_index] = Math.max(st[2],st[3]);
            }
            else{
                st[current_node_index]= Math.max(st[2*current_node_index],st[2*current_node_index+1]);
                return update_up(current_node_index/2);
            }
        }
    }
    
    