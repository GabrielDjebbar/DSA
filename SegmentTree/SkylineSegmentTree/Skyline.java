class Skyline {

  public static void main(String[] args) {
        //https://leetcode.com/problems/the-skyline-problem/submissions/
        //MOST INCREDIBLE CORNER CASE EVEERR [[2,4,7],[2,4,5],[2,4,6]]
        int[][] buildings = {{2,9,10},{3,7,15},{5,12,12},{15,20,10},{19,24,8}};
        System.out.println(getSkyline(buildings));
    }
  
  public List<List<Integer>> getSkyline(int[][] buildings) {
    
    ArrayList<Point> points = new ArrayList<>();
    int building_id = 0;
    for(int[] building: buildings){
         points.add(new Point(building[0],building[2],building_id));
         points.add(new Point(building[1],0,building_id,building[2]));
        building_id += 1;
     }
     
  Collections.sort(points,(a,b)->{
      if(a.x>b.x){
          return 1;
      }
      else if(a.x==b.x){
          if(a.height==b.height && a.height==0){
              if(a.beginning_height > b.beginning_height){
                  return 1;
              }
              else{
                  return -1;
              }
          }
          
          if(a.height>b.height){
              return -1;
          }
          else{
              return 1;
          }
      }
      else{
          return -1;
      }
      });
  
  int N =1;
  while(N<buildings.length) N*=2;
  //in other words we want the last layer of the complete binary tree to have more nods than there are buildings
  //Segment st = new SegmentTree(2*N-1);
  SegmentTree st = new SegmentTree(2*N);
  //Actually i use one more node than necessary but it gives a nice solution to not bother about the indices
  List<List<Integer>> solution = new ArrayList<>();
  if(buildings.length==0){
         return solution;
  }
  if(buildings.length==1){
      solution.add(new ArrayList<Integer>(List.of(points.get(0).x,points.get(0).height)));
      solution.add(new ArrayList<Integer>(List.of(points.get(1).x,points.get(1).height)));
      return solution;
  }
    
  int current_skyline_height = 0;
  for(Point point:points){
      
      //we need a shift value so that when we target building number i, the correct leaft node will be targeted
      //i.e leaf node at index N+i inside the st array
      int new_skyline_height = st.update(point.building_id+N,point.height);
      if(current_skyline_height!=new_skyline_height){
          solution.add(new ArrayList<Integer>(List.of(point.x,new_skyline_height)));
          current_skyline_height = new_skyline_height;
      }
  }
     return solution;
 }
    
}
