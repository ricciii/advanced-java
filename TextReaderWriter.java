import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter; 
import java.io.IOException; 
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class TextReaderWriter {
	private File file;
	public ArrayList<ArrayList<PairValue>> list = new ArrayList<ArrayList<PairValue>>();
	private KeyBank keyBank = new KeyBank();
	
	public TextReaderWriter() {
	}

	public void read(File file) { 
		 int i=0;
		 try {
		 	this.file = file;
		 	if(file.createNewFile()==true) {
		 		create();
		 	} else {
		 		FileReader fr = new FileReader(file);
		 		BufferedReader br = new BufferedReader(fr);
		 		String string = "«[\\x20-\\x7E]*・[\\x20-\\x7E]*»";
		 		Pattern pattern = Pattern.compile(string);
		 		String line;
		 		while((line = br.readLine()) != null) {
		 			this.list.add(new ArrayList<PairValue>());
	 		        Matcher matcher = pattern.matcher(line);
			        while (matcher.find()) {
			        int start = matcher.start(0);
			        int end = matcher.end(0);
			        String[] arr=line.substring(start+1, end-1).split("・"); 
			        if(keyBank.contains(arr[0])==false) {
			        	this.list.get(i).add(new PairValue(arr[0], arr[1]));
			        	keyBank.add(arr[0]);	
			        }
	 		      }
	 		      i++;
		 		}
		 	}
		 } catch(FileNotFoundException ee1) {
		 	System.out.println("File not Found!");
		 } catch(IOException ee2) {
		 	System.out.println("IO Exception");
		 }
	}

	public ArrayList<ArrayList<PairValue>> getList() {
		return this.list;
	}
	
	public KeyBank getKeyBank() {
		return this.keyBank;
	}

	public void updateFromProgram() {
		try {
			PrintWriter writer = new PrintWriter(this.file, "UTF-8");
			int i;
			int j;

			for(i=0; i<this.list.size();i++) {
				for(j=0; j<this.list.get(i).size(); j++) {
					writer.write("["+this.list.get(i).get(j).getKey()+"・"+this.list.get(i).get(j).getValue()+"] ");
				}
				writer.write("\n");
			}

			writer.close();

		} catch(FileNotFoundException fileNotFound) {
			System.out.println(fileNotFound);
		}catch(Exception exception) {
			System.out.println(exception);
		}
	}

	private void create() {
		this.keyBank.clear();
		this.list.clear();
		try {
			PrintWriter writer = new PrintWriter(this.file, "UTF-8");
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the number of rows: ");
			int rows = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.print("Enter the number of columns: ");
			int columns = scanner.nextInt();
			if(rows<0 || columns<0) {
				throw new NegativeIndexException();
			}
			int i;
			int j;
			String stringKey;
			String stringValue;
			for(i=0;i<rows;i++) {
				this.list.add(new ArrayList<PairValue>());
				for(j=0;j<columns;j++) {
					do {
						stringKey =randomAscii();
					} while(keyBank.contains(stringKey));
					stringValue = randomAscii();
					this.list.get(i).add(new PairValue(stringKey, stringValue));
					stringKey=null;
					stringValue=null;
					writer.print("«"+this.list.get(i).get(j).getKey()+"・"+this.list.get(i).get(j).getValue()+"» ");
				}
				writer.print("\n");
			}
			for(i=0; i<list.size();i++) {
				for(j=0; j<list.get(i).size(); j++) {
					this.keyBank.add(list.get(i).get(j).getKey());
				}
			}
			writer.close();
		} catch(NegativeIndexException e1) {
			System.out.println("Please input a positive integer. Try again.");
			create();
		} catch(FileNotFoundException e2) {
			System.out.println("Something went wrong. File not Found.");
		} catch(Exception e3) {
			System.out.println("Please input an integer. Try again.");
			create();
		}
	}

	private String randomAscii() {
		String string;
		Random random = new Random();
		string =(String.valueOf((char) (32 + random.nextInt(94))));
		string +=(String.valueOf((char) (32 + random.nextInt(94))));
		string +=(String.valueOf((char) (32 + random.nextInt(94))));
		return string;
	}
}