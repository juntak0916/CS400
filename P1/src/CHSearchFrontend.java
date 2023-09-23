import java.util.List;
import java.util.Scanner;

public class CHSearchFrontend implements CHSearchFrontendInterface{
	private static CHSearchBackendFD BD;
	private static Scanner input;
	private static String FileName,terms;
	private static boolean FileLoaded;
	
	//constructor
	public CHSearchFrontend(Scanner userInput, CHSearchBackendFD backend) {
		this.input=userInput;
		this.BD=backend;
	}
	@Override
	public void runCommandLoop() {
		boolean shouldQuit=false;
		while(!shouldQuit) {
			char choice=Character.toLowerCase(this.mainMenuPrompt());//open menu and get input
            switch (choice) {
                case 'l':
                    this.loadDataCommand();
                    if(!FileLoaded) {
                    	System.out.println("File not sucessfully loaded");
                    }else {
                    	System.out.println("File sucessfully loaded");
                    }
                    break;
                case 't':
                	if(!FileLoaded) {
                		System.out.println("File not sucessfully loaded");
                	}else {
                		System.out.print("search terms: ");
                		terms=input.nextLine();
                		this.searchTitleCommand(BD.findPostsByTitleWords(terms));
                	}
                	break;
                case 'b':
                	if(!FileLoaded) {
                		System.out.println("File not sucessfully loaded");
                	}else {
                		System.out.print("search terms: ");
                		terms=input.nextLine();
                		this.searchBodyCommand(BD.findPostsByBodyWords(terms));
                	}
                    break;
                case 'p':
                	if(!FileLoaded) {
                		System.out.println("File not sucessfully loaded");
                	}else {
                		System.out.print("search terms: ");
                		terms=input.nextLine();
                		this.searchPostCommand(BD.findPostsByTitleOrBodyWords(terms));
                	}
                    break;
                case 's':
                    this.displayStatsCommand();
                    break;
                case 'q':
                    System.out.println("Goodbye");
                    shouldQuit = true;
                    break;
                    
                default:
                    System.out.println("Invalid choice. Please try again.");
                    break;
            }
		} 	
	}
	@Override
	public char mainMenuPrompt() {
		char res='\0';
		System.out.println("================================");
        System.out.println("[L]oad data from file");
        System.out.println("Search Post [T]itles");
        System.out.println("Search Post [B]odies");
        System.out.println("Search [P]ost titles and bodies");
        System.out.println("Display [S]tatistics for dataset");
        System.out.println("[Q]uit");
        
        System.out.print("Select: ");
        String s=input.nextLine();
        if(s.length()!=1)return '\0';
        res=s.charAt(0);
        if(res=='L'||res=='l'||res=='T'||res=='t'||res=='B'||
        		res=='b'||res=='P'||res=='p'||res=='S'||res=='s'||res=='Q'||res=='q') {
        	return res;
        }
        
        return '\0';
	}

	@Override
	public void loadDataCommand() {
		System.out.print("File name:");
		FileName = input.nextLine();
		 try {
             System.out.println("Loding file...");
         	 BD.loadData(FileName);
         }catch(Exception e) {
        	 FileLoaded=false;
        	 return;
         }
		 FileLoaded=true;
	}

	@Override
	public List<String> chooseSearchWordsPrompt() {
		return null;
	}

	@Override
	public void searchTitleCommand(List<String> words) {
		for(int i=0;i<words.size();i++)
			System.out.println(words.get(i));
		
	}

	@Override
	public void searchBodyCommand(List<String> words) {
		for(int i=0;i<words.size();i++)
			System.out.println(words.get(i));
	}

	@Override
	public void searchPostCommand(List<String> words) {
		for(int i=0;i<words.size();i++)
			System.out.println(words.get(i));
		
	}

	@Override
	public void displayStatsCommand() {
		System.out.println("Displaying statistics for dataset...");
		System.out.println(BD.getStatisticsString()); 
	}
	

}
