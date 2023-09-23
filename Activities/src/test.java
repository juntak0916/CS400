import java.util.*;

public class test {
	protected class Node {
		public Node(String data,List<String> adj,List<String> edgeval) {
			this.data=data;
			this.adj=adj;
			this.edgeVal=edgeval;
		}
        public String data;
        public List<String> adj;
        public List<String> edgeVal;
        int value;
    }
	protected Hashtable<String,Node> nodes = new Hashtable<>();//course number, node
	protected Hashtable<String,List<String>> students = new Hashtable<>();//student, courses
	
	public List<List<String>> solution(List<List<String>> graph,List<String> times){
		/**
		 *  nodes
			1 ->a, b, c
			2 ->a, b
			3 ->c
		 * */
		
		/**
		 * students
		 * a->1 2 
		 * b->1 2
		 * c->1 3
		 * 
		 * */
		
		//add students 
		for(var arr:graph) {
			String CourseNum=arr.get(0);
			for(int i=1;i<arr.size();i++) {
				String key=arr.get(i);
				if(students.contains(key)) {//&& !students.get(key).contains(CourseNum)
					students.get(key).add(CourseNum);
				}else {
					//new student
					List<String> arrtmp=new ArrayList<String>();
					arrtmp.add(CourseNum);
					students.put(key,arrtmp);
				}
			}
		}
		
		//produce nodes
		for(var arr:graph) {
			String CourseNum=arr.get(0);
			List<String> edgeVal=new ArrayList<String>();
			for(int i=1;i<arr.size();i++) edgeVal.add(arr.get(i));
			
			nodes.put(CourseNum, new Node(CourseNum,new ArrayList<String>(),edgeVal));
		}
		
		//traverse map and add adjs 
		Enumeration<String> e = students.keys();
		while(e.hasMoreElements()) {
			 String key = e.nextElement();
			 List<String> arrtmp=students.get(key);
			 for(int i=0;i<arrtmp.size()-1;i++) {
				 Node n = nodes.get(key);
				 for(int j=i+1;j<arrtmp.size();j++) {
					 insertEdge(arrtmp.get(i),arrtmp.get(j));
				 }
			 }
		}
		//그래프 완성!	
		
		
		
		return null;
	}
	public void insertEdge(String c1, String c2) {
		if(!nodes.get(c1).adj.contains(c2))
			nodes.get(c1).adj.add(c2);
    }
	
	
	public static void main(String args[]){
		
	}

}
