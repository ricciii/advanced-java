import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class AsciiTable implements AsciiTableInterface{
	
	public ArrayList<ArrayList<PairValue>> list;
	public KeyBank keyBank;

	public AsciiTable(ArrayList<ArrayList<PairValue>> list, KeyBank keyBank) {
		this.list=list;
		this.keyBank=keyBank;
	}

	public void print() {
		int i;
		int j;
		for(i=0; i<this.list.size();i++) {
			for(j=0; j<this.list.get(i).size(); j++) {
				System.out.print("«"+this.list.get(i).get(j).getKey()+"・"+this.list.get(i).get(j).getValue()+"» ");
			}
			System.out.println();
		}
	}

	public void search() {
		System.out.print("What do you want to search: ");
		Scanner patternScanner = new Scanner(System.in);
		String pattern = patternScanner.nextLine();
		int patternLength = pattern.length();
		int i;
		int j;
		int counterKey=0;
		int counterValue=0;
		int total=0;
		for(i=0; i<this.list.size();i++){
			for(j=0; j<this.list.get(i).size(); j++){
				String string = new String();
				string = list.get(i).get(j).getKey();
				int length = string.length();
				int a;
				int b;
				for(a=0; a<=length-patternLength;a++) {
					 for(b=0; b<patternLength; b++) {
					 	if(string.charAt(a+b)!=pattern.charAt(b)) {
					 		break;
					 	}
					 } 
					 if(b==patternLength) {
					 	counterKey++;
					 	b=0;
					 }
				}
				string=list.get(i).get(j).getValue();
				length=string.length();
				for(a=0; a<=length-patternLength;a++) {
					 for(b=0; b<patternLength; b++) {
					 	if(string.charAt(a+b)!=pattern.charAt(b)) {
					 		break;
					 	}
					 } 
					 if(b==patternLength) {
					 	counterValue++;
					 	b=0;
					 }
				}

				if(counterKey>0 || counterValue>0) {
					System.out.format("Found at: Index[%d][%d]\n", i,j);
					if(counterKey>0) {
						System.out.format("Key occurences in this index: %d\n", counterKey);
						total+=counterKey;
						counterKey=0;
					} 
					if(counterValue>0) {
						System.out.format("Value occurences in this index: %d\n", counterValue);
						total+=counterValue;
						counterValue=0;
					}
				}
			}
		}
		System.out.format("Total # of \"%s\" Occurences: %d \n", pattern, total);
	}

	public void addRow() throws IndexOutOfBoundsException{
		int i=0;
		int size=this.list.size();
		String stringKey;
		String stringValue;
		if(size<=0) {
			System.out.println("Table is empty. No max column found.");
			try {
				Scanner scanner = new Scanner(System.in);
				System.out.print("Enter the number of columns: ");
				int column = scanner.nextInt();
				if(column<0) {
					throw new NegativeIndexException();
				}
				this.list.add(new ArrayList<PairValue>());
				for(i=0; i<column;i++) {
					do {
						stringKey = randomAscii();
					} while(keyBank.contains(stringKey)==true);
					this.keyBank.add(stringKey);
					stringValue = randomAscii();
					this.list.get(this.list.size()-1).add(new PairValue(stringKey, stringValue));
				}

			} catch(NegativeIndexException e1) {
				
				System.out.println("Please input a positive integer. Try again.");
				addRow();

			} catch(Exception e2) {
				
				System.out.println("Please input an integer. Try again.");
				addRow();

			}
		} else {
			i=0;
			size=this.list.get(i).size();
			for(i=0;i<this.list.size();i++) {
				if(this.list.get(i).size()>size) {
					size=this.list.get(i).size();
				}
			}
			this.list.add(new ArrayList<PairValue>());

			for(i=0; i<size;i++) {
				do {
					stringKey = randomAscii();
				} while(keyBank.contains(stringKey)==true);
				stringValue = randomAscii();
				this.list.get(this.list.size()-1).add(new PairValue(stringKey, stringValue));
			}
		}
	}

	public void reset() {
		
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.print("Enter the number of rows: ");
			int rows = scanner.nextInt();
			if(rows<=0) {
				throw new NegativeIndexException();
			}
			scanner = new Scanner(System.in);
			System.out.print("Enter the number of columns: ");
			int columns = scanner.nextInt();
			if(columns<=0) {
				throw new NegativeIndexException();
			}
			this.list.clear();
			this.keyBank.clear();
			int i;
			int j;
			Random random = new Random();
			String stringKey;
			String stringValue;
			for(i=0;i<rows;i++) {
				this.list.add(new ArrayList<PairValue>());
				for(j=0;j<columns;j++) {
					do {
						stringKey =randomAscii();
					} while(keyBank.contains(stringKey));
					this.keyBank.add(stringKey);
					stringValue =randomAscii();
					this.list.get(i).add(new PairValue(stringKey, stringValue));
				}
			}
		} catch(NegativeIndexException e1) {
			System.out.println("Please input a positive integer which is more than 0. Try again.");
			reset();
		} catch(Exception e2) {
			System.out.println("Please input an integer. Try again.");
			reset();
		}
	}

	public void sort() {
		try {
			Scanner scanner = new Scanner(System.in);
			System.out.println("What order do you want to sort?\n1=Ascending 2=Descending");
			int choice = scanner.nextInt();

			switch(choice) {
				case 1:
						sortAscending();
				break;
				
				case 2:
						sortDescending();
				break;
				
				default:
						System.out.println("Not in the choices. Please try again.");
						sort();
			} 
		} catch(Exception e) {
			System.out.println(e);
			sort();
		}
	}

	private void sortAscending() {
		ArrayList<PairValue> tempList = new ArrayList<PairValue>();
		int i;
		int j;
		for(i=0; i<this.list.size();i++) {
			for(j=0;j<this.list.get(i).size();j++) {
				tempList.add(this.list.get(i).get(j));
			}
		}

		int size = tempList.size();
      	int a;
      	int b;
     	for(a = 0; a<size-1; a++) {
     		for (b = a+1; b<size; b++) {
         		String string1 = new String();
         		string1=tempList.get(a).getCode();
         		String string2 = new String();
         		string2=tempList.get(b).getCode();

            	if(string1.compareTo(string2)>0) {
                	PairValue tempKeyValue = new PairValue(tempList.get(a).getKey(),tempList.get(a).getValue());
               		tempList.get(a).setKey(tempList.get(b).getKey());
               		tempList.get(a).setValue(tempList.get(b).getValue());
               		tempList.get(b).setKey(tempKeyValue.getKey());
               		tempList.get(b).setValue(tempKeyValue.getValue());
            }
         }
      }
      int x=0;
      for(i=0; i<this.list.size();i++) {
      			if(x>size) {
      				break;
      			}
      			for(j=0; j<this.list.get(i).size(); j++) {
      				this.list.get(i).get(j).setKey(tempList.get(x).getKey());
      				this.list.get(i).get(j).setValue(tempList.get(x).getValue());
      				x++;
      			}
      		} 
	}

	private void sortDescending() {
		ArrayList<PairValue> tempList = new ArrayList<PairValue>();
		int i;
		int j;
		for(i=0; i<this.list.size();i++) {
			for(j=0;j<this.list.get(i).size();j++) {
				tempList.add(this.list.get(i).get(j));
			}
		}

		int size = tempList.size();
      	int a;
      	int b;
     	for(a = 0; a<size-1; a++) {
     		for (b = a+1; b<size; b++) {
         		String string1 = new String();
         		string1=tempList.get(a).getCode();
         		String string2 = new String();
         		string2=tempList.get(b).getCode();

            	if(string1.compareTo(string2)<0) {
                	PairValue tempKeyValue = new PairValue(tempList.get(a).getKey(),tempList.get(a).getValue());
               		tempList.get(a).setKey(tempList.get(b).getKey());
               		tempList.get(a).setValue(tempList.get(b).getValue());
               		tempList.get(b).setKey(tempKeyValue.getKey());
               		tempList.get(b).setValue(tempKeyValue.getValue());
            }
         }
      }
      int x=0;
      for(i=0; i<this.list.size();i++) {
      			if(x>size) {
      				break;
      			}
      			for(j=0; j<this.list.get(i).size(); j++) {
      				this.list.get(i).get(j).setKey(tempList.get(x).getKey());
      				this.list.get(i).get(j).setValue(tempList.get(x).getValue());
      				x++;
      			}
      		} 
	}

	public void exit() {
		System.exit(0);
	}

	public void edit() {
		Scanner scanner = new Scanner(System.in);
		try {
			int choice;
			System.out.println("Which one do you want to edit?\n1=Key 2=Value 3=Both");
			choice=scanner.nextInt();
			
			switch(choice) {
				case 1:
						editKey();
						break;
				case 2: 
						editValue();
						break;
				case 3: 
						editBoth();
						break;
				default: 
						System.out.println("Not in the choices. Please try again.");
						edit();
			}
			
		} catch(Exception e) {
			System.out.println("Not in the choices. Please try again.");
			edit();
		}
	}

	private void editKey() {
		int row;
		int column;
		String newKey;
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter the row index: ");
			row = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.println("Enter the column index: ");
			column = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.println("Enter the new key: ");
			newKey = scanner.nextLine();
			if(keyBank.contains(newKey)) {
				throw new PairValueKeyException();
			}
			this.list.get(row).get(column).setKey(newKey);
			this.keyBank.add(newKey);
			this.keyBank.remove(this.list.get(row).get(column).getKey());

		} catch(IndexOutOfBoundsException e1) {
			System.out.println("No such index.");
		} catch(PairValueKeyException e2) {
			System.out.println("Key already exists.");
		} catch(Exception e3) {
			System.out.println("Something went wrong. Going back to menu.");
		}
	}

	private void editValue() {
		int row;
		int column;
		String newValue;
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter the row index: ");
			row = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.println("Enter the column index: ");
			column = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.println("Enter the new key: ");
			newValue = scanner.nextLine();
			this.list.get(row).get(column).setValue(newValue);

		} catch(IndexOutOfBoundsException e1) {
			System.out.println("No such index.");
		} catch(Exception e2) {
			System.out.println("Something went wrong. Going back to menu.");
		}
	}

	private void editBoth() {
		int row;
		int column;
		String newKey;
		String newValue;
		Scanner scanner = new Scanner(System.in);
		try {
			System.out.println("Enter the row index: ");
			row = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.println("Enter the column index: ");
			column = scanner.nextInt();
			scanner = new Scanner(System.in);
			System.out.println("Enter the new key: ");
			newKey = scanner.nextLine();
			if(keyBank.contains(newKey)) {
				throw new PairValueKeyException();
			}
			this.list.get(row).get(column).setKey(newKey);
			this.keyBank.add(newKey);
			this.keyBank.remove(this.list.get(row).get(column).getKey());
			scanner = new Scanner(System.in);
			System.out.println("Enter the new value: ");
			newValue = scanner.nextLine();
			this.list.get(row).get(column).setValue(newValue);

		} catch(IndexOutOfBoundsException e1) {
			System.out.println("No such index. Please try again.");
			editBoth();
		} catch(PairValueKeyException e2) {
			System.out.println("Key already exists. Please try again.");
			editBoth();
		} catch(Exception e3) {
			System.out.println("Something went wrong. Please try again.");
			editBoth();
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