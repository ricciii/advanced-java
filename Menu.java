import java.util.Scanner;
import java.util.ArrayList;
import java.io.File; 

public class Menu {

	public void start(AsciiTable table, TextReaderWriter text) {
		
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("What do you want to do?\n1=Print 2=Search 3=Edit 4=Sort\n5=Add Row 6=Reset 7=Exit");
			System.out.print("Input: ");
			int choice = scanner.nextInt();

			switch(choice) {
				case 1:
						table.print();
						start(table, text);
						break;				
				case 2:
						table.search();
						start(table, text);
						break;
				case 3:
						table.edit();
						text.updateFromProgram();
						start(table, text);
						break;
				case 4:
						table.sort();
						text.updateFromProgram();
						start(table, text);
						break;
				case 5:
						table.addRow();
						text.updateFromProgram();
						start(table, text);
						break;
				case 6:
						table.reset();
						text.updateFromProgram();
						start(table, text);
						break;
				case 7:
						table.exit();
						break;
				default:
						System.out.println("Not in the choices. Please try again.");
						start(table, text);
			} 
		} catch(IndexOutOfBoundsException e1) {
			System.out.println(e1);
			start(table, text);
		} catch(Exception e2) {
			System.out.println(e2);
			start(table, text);
		}
	}

	public static void main(String[] args) {
		Menu menu=new Menu(); 
		File file;
		String path;
		try {
			path = args[0];
			file = new File(path); 
	        if(file.exists()==false) {
	        	System.out.println("File not found. Using default file...");
	        	file = new File("text.txt");
	        }
		}
		catch(Exception error) {
			System.out.println("No file given. Using default file...");
			file = new File("text.txt");
		}
		TextReaderWriter text = new TextReaderWriter();
		text.read(file);
		ArrayList<ArrayList<PairValue>> list = text.getList();
		KeyBank key_bank = text.getKeyBank();
		AsciiTable table = new AsciiTable(list, key_bank);
		menu.start(table, text);
	}
}