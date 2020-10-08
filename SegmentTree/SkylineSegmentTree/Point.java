class Point{
    int x;
    int height;
    int building_id;
    int beginning_height;
    public Point(int x,int height, int building_id){
        this.x = x;
        this.height = height;
        this.building_id = building_id;
    }
        
    public Point(int x,int height, int building_id, int beginning_height){
        this.x = x;
        this.height = height;
        this.building_id = building_id;
        this.beginning_height = beginning_height;
    }
}