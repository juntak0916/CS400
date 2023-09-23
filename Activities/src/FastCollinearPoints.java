import java.util.*;

public class FastCollinearPoints {
	static public class Point implements Comparable<Point>{
		int x,y;
		int slope;
		public Point(int x,int y,Point p){
			this.x=x;this.y=y;
			try {
				this.slope=(p.y-y)/(p.x-x);
			}catch(Exception e) {
				this.slope=9999999;
			}
		}
		
		@Override
		public int compareTo(Point o) {
			if(this.slope>o.slope)return 1;
			else if(this.slope<o.slope)return -1;
			return 0;
		}
		
	}
	public List<Point> arr=new ArrayList<>();
	public static Point p;
	public FastCollinearPoints(List<Point> pts) {
		for(int i=0;i<pts.size();i++) {
			int x=pts.get(i).x,y=pts.get(i).y;
			arr.add(new Point(x,y,p));
		}
		Collections.sort(arr);
		for(int i=0;i<arr.size();i++) 
			System.out.println("x: "+arr.get(i).x+" Y: "+arr.get(i).y);
	}
	
	
	public static void main(String[] args) {
		List<Point> input=new ArrayList<>();
		FastCollinearPoints.p=new Point(1,2,null);
		input.add(new Point(3,4,FastCollinearPoints.p));
		input.add(new Point(8,9,FastCollinearPoints.p));
		input.add(new Point(6,9,FastCollinearPoints.p));
		input.add(new Point(9,7,FastCollinearPoints.p));
		input.add(new Point(2,8,FastCollinearPoints.p));
		input.add(new Point(9,9,FastCollinearPoints.p));
		input.add(new Point(1,0,FastCollinearPoints.p));
		new FastCollinearPoints(input);
	}

}
