public class FrontendDeveloperTests {
	public static boolean test1() {
		//this method tests Loading file option in the menu
		TextUITester TT=new TextUITester("L\nrand\nq\n");
		TextUITester.run1();
		String output = TT.checkOutput();
        if(output.contains("Goodbye")&&output.startsWith("=")&&output.contains("File sucessfully loaded")) {
        	return true;
        }
        
		return false;
	}
	public static boolean test2() {
		//this method tests Search Post [T]itles option
				TextUITester TT=new TextUITester("l\nrand\nt\nword\nq\n");
				TextUITester.run1();
				String output = TT.checkOutput();
		        if(output.contains("findPostsByTitleWords")) {
		        	return true;
		        }
				return false;
	}
	public static boolean test3() {
		//this method tests [B]odies option
		TextUITester TT=new TextUITester("l\nrand\nb\nword\nq\n");
		TextUITester.run1();
		String output = TT.checkOutput();
        if(output.contains("called findPostsByBodyWords")) {
        	return true;
        }
        
		return false;
	}
	public static boolean test4() {
		//this method tests [P]ost titles and bodies option
		TextUITester TT=new TextUITester("l\nrand\np\nword\nq\n");
		TextUITester.run1();
		String output = TT.checkOutput();
        if(output.contains("called findPostsByTitleOrBodyWords")) {
        	return true;
        }
        
		return false;
	}
	public static boolean test5() {
		//this method tests [S]tatistics option
		TextUITester TT=new TextUITester("s\nq\n");
		TextUITester.run1();
		String output = TT.checkOutput();
        if(output.contains("called getStatisticsString")&&output.contains("Displaying statistics for dataset...")) {
        	return true;
        }
        
		return false;
	}
	
	public static void main(String[] args) {
		System.out.println("test1(): "+test1());
		System.out.println("test2(): "+test2());
		System.out.println("test3(): "+test3());
		System.out.println("test4(): "+test4());
		System.out.println("test5(): "+test5());
	}

}
