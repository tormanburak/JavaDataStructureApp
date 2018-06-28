package p1;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import p1.HashTable;

public class Demo extends Application {
	
	private static ArrayList<Long> array = new ArrayList<>();	//array to store one while loop analyzing time
	private static ArrayList<Long> array2 = new ArrayList<>();	//array to store three while loops analyzing time


	private static TextArea textarea = new TextArea();		// static Java GUI items 
	
	private static Label statusBar = new Label("Status");

	private static Menu menu2 = new Menu("Edit");
	private static MenuItem saveCounts = new MenuItem("Save Counts");

	private static MenuItem wordCount = new MenuItem("Word count");
	private static MenuItem sentenceCount = new MenuItem("Sentence count");
	private static MenuItem syllables = new MenuItem("Syllable count");
	private static MenuItem fleschScore = new MenuItem("Flesch score");
	
	private static Menu menu3 = new Menu("Graph");
	private static MenuItem graph = new MenuItem("Line Graph");



	private static Alert alert = new Alert(AlertType.ERROR);
	private static String countType = "word";
	
	private static long result;
	
	private static String file= "Data/warAndPeace.txt";
	/* i created 2 results file because it was easier to write the analyzed time results to two separate files
	 * and read the time, store it in separate lists and plug it into data
	 */
	private static File f1 = new File("outputData/OneWhileLoopResult.txt");	
	private static File f2 = new File("outputData/StringGeneratorResult.txt");	
	private static File f3 = new File("outputData/ThreeWhileLoopResult.txt");
	
	private static File DataFile = new File(file);	//Original "War and Peace" file to store in linked list
	private static LinkedList list = new LinkedList();
	
	private static String dateFile = "Data/date.txt";	// read dates from text file
	private static String valueFile = "Data/value.txt";	// read values from text file

	private static int[] dateArray = new int[2610];		//initially store dates in array to shuffle, then into tree
	private static int dateArrayIndex = 0;
	private static double[] valueArray = new double[2610];	//initially store values in array to shuffle, then into tree
	private static int valueArrayIndex = 0;

	private static BinaryTree tree = new BinaryTree();
	
	private static String dictionaryfile = "Data/dictionary.txt";
	private static HashTable hashTable = new HashTable(3);
	private static String[] arrayForDictionary = new String [99171];
	private static int DictionaryArrayIndex = 0;






	public static void main(String[] args) throws IOException {	
		//methods for Text Generator
		storeInLinkedList(list, DataFile); // store the novel in link list make list and baby list for each non repeating word
		readFileAndTruncate(file);
		//methods for Stock Project
		readStockDates(dateFile,dateArray,dateArrayIndex);
		readStockValues(valueFile,valueArray,valueArrayIndex);
		shuffleArray(dateArray,valueArray);
		insertValues(tree);
		
		
		insertToHashTable(dictionaryfile, arrayForDictionary, DictionaryArrayIndex);
	
		launch(args);

		System.exit(0);

	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setTitle("Text Editor");
		stage.setResizable(false);
		Group root = new Group();
		Scene scene = new Scene(root, 1600, 1000,Color.GAINSBORO);

		BorderPane pane = new BorderPane();

		pane.setCenter(getGridPane());
		MenuBar menuBar = new MenuBar();

		menuBar.prefWidthProperty().bind(stage.widthProperty());
		pane.setTop(menuBar);

		Menu menu = new Menu("File");
		MenuItem open = new MenuItem("Open");
		MenuItem newItem = new MenuItem("New");
		MenuItem saveText = new MenuItem("Save Text");
		MenuItem exit = new MenuItem("Exit");

		open.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Choose a file to analize");
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
			fc.getExtensionFilters().add(extFilter);
			File file = fc.showOpenDialog(stage);
			String s = "";

			try {
				
				s = open(file);
				textarea.appendText(s);
				//result = r2.doAll();
				
			} catch (Exception e1) {
			}

		});
		newItem.setOnAction(e -> {
			textarea.clear();
		});
		saveCounts.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save Counts");
			File file = fc.showOpenDialog(stage);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
			fc.getExtensionFilters().add(extFilter);
			String s = textarea.getText();
			try {
				Readability r = new Readability(s);
				String s2 = "Word count- " + r.getNumberOfWords() + " Sentence count- " + r.getSentences()
						+ " Syllable count- " + r.getSyllables() + " Flesch score is- "
						+ Math.round(r.getFleschScore());
				save("\r\n"+s2, file,true);
			} catch (Exception e1) {

			}

		});
		
		saveText.setOnAction(e -> {
			FileChooser fc = new FileChooser();
			fc.setTitle("Save Text");
			File file = fc.showOpenDialog(stage);
			FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("TEXT files (*.txt)", "*.txt");
			fc.getExtensionFilters().add(extFilter);
			String s = textarea.getText();
			try {
				save(s, file,true);
			} catch (Exception e1) {
			}

		});
		exit.setOnAction(e -> {
			System.exit(0);

		});
		graph.setOnAction(e->{
			try {
				graph();
			} catch (Exception e1) {
				e1.printStackTrace();
			}
		});

		menu.getItems().addAll(open, newItem, saveCounts, saveText, exit);
		menu2.getItems().addAll(wordCount, sentenceCount, syllables, fleschScore);
		menu3.getItems().add(graph);
		menuBar.getMenus().addAll(menu, menu2,menu3);
		root.getChildren().add(pane);
		stage.setScene(scene);
		stage.show();
	}

	public static String truncate(String s, double percent) { // not used in the program, it was built to test truncate algorithm
		double percent2 = percent / 100;
		s=s.trim();
		if (percent >= 100) {
			System.out.print("100 percent: ");
			return s;
		}
		String str = "";
		double n = s.length() * percent2;
		str = s.substring(0, (int) n);

		System.out.print(percent + " Percent: "+ str);
		return str;

	}

	public static void truncateRec(String s, double percent) throws FileNotFoundException {
		String str = "";
		s=s.trim();
		double p = percent;								// recursive method that truncates the given string by increments of 10%
		String filename = "";
		filename = Double.toString(p);
		double n;
		double percent2 = percent / 100;
		File f = new File("inputData/"+filename+" percent.txt");
		PrintWriter writer = new PrintWriter(f);
		
		if (percent == 100) {
			writer.write(s);	//writes the 100% of the file in inputData folder
			writer.close();
			
		} else {
			n = s.length() * percent2;
			str = s.substring(0, (int) n);



			writer.write(str);	//writes the current percent of the file to inputData folder
			 truncateRec(s, percent + 10);
			 writer.close();
		}
	}
		
	

	public static void updateCount() {
		Readability r = new Readability(textarea.getText());	/* this method is used for real time count of sentences
																	words, syllables and flesh score	*/

		if (countType.equals("word")) {
			statusBar.setText("Word count: " + r.getNumberOfWords());
		} else if (countType.equals("sentence")) {
			statusBar.setText("Sentence count: " + r.getSentences());
		}
		if (countType.equals("syllables")) {
			statusBar.setText("Syllable count: " + r.getSyllables());
		} else if (countType.equals("flesch")) {
			statusBar.setText("Flesch score: " + r.getFleschScore());
		}

	}

	public static void save(String s, File f,boolean value) {
			FileWriter fileWriter;		//save method, writes a given string to a given file
		try {							//boolean variable, false if we want to clear and write new data in file, true else
			fileWriter = new FileWriter(f,value);
			fileWriter.write(s+"\r ");
			fileWriter.close();
			
		} catch (IOException e) {
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Something went wrong while trying to save");
			alert.setContentText("Try again or try another file");

			alert.showAndWait();
		}
	}
	@SuppressWarnings("unchecked")
	public static void read(File f,@SuppressWarnings("rawtypes") List a) throws FileNotFoundException{ // reads data from file and adds to arrayList
		Scanner input = new Scanner(f);									// those values in the List is automatically plugged into graph
		
		while(input.hasNextLong()){
			a.add(input.nextLong());
		}
		input.close();
	}
	
	public static void storeInLinkedList(LinkedList list,File f) throws FileNotFoundException{
		// reads words from a text file and stores it in a linked list
		Scanner input = new Scanner(f);	
		String word = null;
		while(input.hasNext()){
			word = input.next();
			list.insertLast(word);		
		}
		input.close();
		
	}
	public static String generator(String word, int num){// random string generator method
		String s = "";
		s += word+" ";
		String s2 = word;
		for(int i =0; i<num ; i++){
		s2 = list.find(s2).babyLinkList.getRandomWord();
		s += s2+" ";
		}
		return s;	
	}

	public static String open(File f) {
		Scanner input;						//takes a file and returns it as a string, used in filechooser part
		String result = "";
		StringBuilder builder = new StringBuilder();
		
		try {
			input = new Scanner(f);
			String word = null;
			while (input.hasNext()) {
				word = input.next();
				builder.append(word+" ");
			}
			result = builder.toString();
			input.close();

		} catch (FileNotFoundException e) {
			alert.setTitle("Error Dialog");
			alert.setHeaderText("Something went wrong while trying to open a file");
			alert.setContentText("Try again");

			alert.showAndWait();
		}

		return result;

	}

	public static GridPane getGridPane() {
		GridPane gridPane = new GridPane();	// where the texteditor is build
		gridPane.setPadding(new Insets(5));
		gridPane.setAlignment(Pos.TOP_LEFT);
		gridPane.setHgap(10);
		gridPane.setVgap(10);

		ColumnConstraints column1 = new ColumnConstraints(50);
		ColumnConstraints column2 = new ColumnConstraints(50);
		ColumnConstraints column3 = new ColumnConstraints(50);


		gridPane.getColumnConstraints().addAll(column1, column2, column3);
		
		
		textarea.setWrapText(true);
		
		
		
		Label info = new Label("Choose a file from the menu bar above.");
		info.setFont(new Font("Arial", 12));
		
		Label keyWord = new Label("Keyword");
		TextField keyWordField = new TextField();
		
		Label number = new Label("Number");
		TextField numberField = new TextField();
		
		Label spellCheck = new Label("Spell Check");
		TextField spellCheckField = new TextField();
		Label spellCheckStatus = new Label("");
		
		Button create = new Button("Create");
		Button show = new Button("Show");
		Button checkButton = new Button("Check");
		
		ComboBox<String> yearComboBox = new ComboBox<String>();
		yearComboBox.getItems().addAll("2006","2007","2008","2009","2010","2011","2012","2013","2014","2015","2016");
	    yearComboBox.setValue("Year");
	    yearComboBox.setEditable(false);  
	    
	    ComboBox<String> monthComboBox = new ComboBox<String>();
	    monthComboBox.getItems().addAll("01","02","03","04","05","06","07","08","08","09","10","11","12");
	    monthComboBox.setValue("Month");
	    monthComboBox.setEditable(false);
	    
	    ComboBox<String> dayComboBox = new ComboBox<String>();
	    dayComboBox.getItems().addAll("01","02","03","04","05","06","07","08","09","10","11","12","13","14","15",
	    		"16","17","18","19","20","21","22","23","24","25","26","27","28","29","30","31");
	    dayComboBox.setValue("Day");
	    dayComboBox.setEditable(false);
	    
	    HBox box = new HBox();
	    box.getChildren().addAll(yearComboBox,monthComboBox,dayComboBox);
	    box.setSpacing(10);
	    
	    HBox box2 = new HBox();
	    box2.getChildren().addAll(spellCheck,spellCheckField,checkButton,spellCheckStatus);
	    box2.setSpacing(10);
																					
		create.setOnAction(e->{	
																					// takes the keyword in keyWordField and number in numberField 
																					//then creates a random string of words based on that word and amount of number
			try{																	// if you put 10 for number field you will get 11 total words because the keyword + 10 words.
			textarea.clear();														
			String keyword = keyWordField.getText();
			int num = Integer.parseInt(numberField.getText());
			String s = generator(keyword,num);
			textarea.appendText(s);
				
				save(s,f2,false);
			} catch (Exception e1) {
				textarea.setText("Make sure keyword and number fields are entered correctly.");
			}
		});
		
		show.setOnAction(e->{
			String year = yearComboBox.getValue();
			String month = monthComboBox.getValue();
			String day = dayComboBox.getValue();
			
			String date = year+month+day;
			int key = Integer.parseInt(date);
			
			try{
				double time1 = System.nanoTime();
				double data = tree.find(key).getdData();
				double time2 = System.nanoTime();
				double result = time2-time1;
				String time = Double.toString(result);

				if(data==0){
					textarea.clear();
					textarea.setText("Data is invalid for that date, pick another date please.");
				}else{
					textarea.clear();
					String value = Double.toString(data);
					statusBar.setText("Stock Value: "+value+"\n|Time it took to find in nano seconds: "+time);
				}
			}catch(NullPointerException ex){
				textarea.clear();
				textarea.setText("Chosen date is not in range between 11/12/2006 - 09/12/2016 , please pick a valid date.");
			}
			
		});
		
		checkButton.setOnAction(e->{
			String word = spellCheckField.getText();
			word = word.toLowerCase();
			if((hashTable.find(word) == null) || (word.contains("[0-9]+"))){
				spellCheckStatus.setText("Incorrect");
			}else {
				spellCheckStatus.setText("Correct");
			}
		});
		

		GridPane.setHalignment(info, HPos.LEFT);
		gridPane.add(info, 3, 1);
		gridPane.add(keyWord, 5, 1);
		gridPane.add(keyWordField, 6, 1);
		gridPane.add(number, 7, 1);
		gridPane.add(numberField,8 , 1);
		gridPane.add(create, 9, 1);
		gridPane.add(show, 7, 45);		
		
		gridPane.add(box, 6,45);
		gridPane.add(box2, 6,47);

		gridPane.add(textarea, 1, 3, 30, 40);

		GridPane.setHalignment(statusBar, HPos.LEFT);
		GridPane.setValignment(statusBar, VPos.BOTTOM);
		gridPane.add(statusBar, 3, 45);
		

		textarea.textProperty().addListener(new ChangeListener<String>(){

			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				updateCount();
			}
		});

		wordCount.setOnAction(e -> {		// event handlers used for the real time status bar
			countType = "word";
			updateCount();
		});
		sentenceCount.setOnAction(e -> {
			countType = "sentence";
			updateCount();
		});
		syllables.setOnAction(e -> {
			countType = "syllables";
			updateCount();
		});
		fleschScore.setOnAction(e -> {
			countType = "flesch";
			updateCount();
			countType = "word";

		});

		return gridPane;

	}

	private static String readFileAndTruncate(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();	/*method that reads the file and calls the truncate method
															this method allows efficiency by just combining reading and truncating*/
		String ls = System.getProperty("line.separator");		
			
		String result = "";

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			result = stringBuilder.toString();
			truncateRec(result,10);
			return result;
		} finally {
			reader.close();
		}
	}
	private static String readFile(String file) throws IOException {
		BufferedReader reader = new BufferedReader(new FileReader(file));	// reading a file and building a string method
		String line = null;
		StringBuilder stringBuilder = new StringBuilder();
		String ls = System.getProperty("line.separator");
		String result = "";

		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line);
				stringBuilder.append(ls);
			}
			result = stringBuilder.toString();
			return result;
		} finally {
			reader.close();
		}
	}
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static void graph() throws FileNotFoundException, IOException{ // where the graph is built
			//methods for analyzing 2 algorithms efficiency project
		save("",f1,false);	//Clears the existing content in the file f1
		save("",f3,false);	//Clears the existing content in the file f3
		timeAnalyzeOneLoop(readFile(file),10);	//analyzes time for one while loop for each 10% data and writes to oneWhileLoopResults file
		timeAnalyzeThreeLoop(readFile(file),10);	//analyzes time for three while loop for each 10% data and writes to threeWhileLoopResults file
		read(f1,array);	// reads the times, adds it in to arrayList 
		read(f3,array2); // reads the times, adds it in to arrayList
				
				
		Stage stage2 = new Stage();
		stage2.setTitle("Graph");
		// defining the axes
		final NumberAxis xAxis = new NumberAxis();
		final NumberAxis yAxis = new NumberAxis();
		xAxis.setLabel("Percent of the file");
		// creating the chart
		final LineChart<Number, Number> lineChart = new LineChart<Number, Number>(xAxis, yAxis);

		lineChart.setTitle("Algorithm difference");

		XYChart.Series<Number, Number> line = new XYChart.Series();
		line.setName("Three while-loops");
		line.getData().add(new XYChart.Data(10, array2.get(0)));	
		line.getData().add(new XYChart.Data(20, array2.get(1)));
		line.getData().add(new XYChart.Data(30, array2.get(2)));
		line.getData().add(new XYChart.Data(40, array2.get(3)));
		line.getData().add(new XYChart.Data(50, array2.get(4)));
		line.getData().add(new XYChart.Data(60, array2.get(5)));
		line.getData().add(new XYChart.Data(70, array2.get(6)));
		line.getData().add(new XYChart.Data(80, array2.get(7)));
		line.getData().add(new XYChart.Data(90, array2.get(8)));
		line.getData().add(new XYChart.Data(100, array2.get(9)));
		

		XYChart.Series<Number, Number> line2 = new XYChart.Series();
		line2.setName("One while-loop");
		line2.getData().add(new XYChart.Data(10, array.get(0)));	
		line2.getData().add(new XYChart.Data(20, array.get(1)));
		line2.getData().add(new XYChart.Data(30, array.get(2)));
		line2.getData().add(new XYChart.Data(40, array.get(3)));
		line2.getData().add(new XYChart.Data(50, array.get(4)));
		line2.getData().add(new XYChart.Data(60, array.get(5)));
		line2.getData().add(new XYChart.Data(70, array.get(6)));
		line2.getData().add(new XYChart.Data(80, array.get(7)));
		line2.getData().add(new XYChart.Data(90, array.get(8)));
		line2.getData().add(new XYChart.Data(100, array.get(9)));

		Scene scene2 = new Scene(lineChart, 800, 600);
		lineChart.getData().addAll(line, line2);

		stage2.setScene(scene2);
		stage2.show();
	}
	public static ArrayList<Long> timeAnalyzeOneLoop(String s, double percent) throws FileNotFoundException {
		String str = "";							// recursive method that truncates the given string by increments of 10%
		double n;									// and writes the analyzed time to a results file
		double percent2 = percent / 100;
		String time2 = " ";
		
		if (percent == 100) {
			Readability r = new Readability(s);
			result= r.doAll();
			time2 = Long.toString(result);
			save(time2,f1,true);
			
		} else {
			n = s.length() * percent2;
			str = s.substring(0, (int) n);
			
			Readability r = new Readability(str);	// loop to analyze from 10-90% of data and write it to results file
			for(int i =0; i<10;i++){
				result = r.doAll();
				time2 = Long.toString(result);
				
			}

			save(time2,f1,true);

			 timeAnalyzeOneLoop(s, percent + 10);
			
		}
		return array;
	}
	public static ArrayList<Long> timeAnalyzeThreeLoop(String s, double percent) throws FileNotFoundException {
		String str = "";				
		double n;
		double percent2 = percent / 100;
		String time2 = " ";


		if (percent == 100) {
			Readability r = new Readability(s);
			result= r.do3Loops();

			time2 = Long.toString(result);
			save(time2,f3,true);
			
		} else {
			n = s.length() * percent2;
			str = s.substring(0, (int) n);
			
			Readability r = new Readability(str);	// loop to analyze from 10-90% of data and write it to results file
			for(int i =0; i<10;i++){
				result = r.do3Loops();
				time2 = Long.toString(result);
				
			}
			save(time2,f3,true);


			 timeAnalyzeThreeLoop(s, percent + 10);

		}
		return array2;
	}
	public static void readStockDates(String s,int[]a, int index) throws FileNotFoundException{
		File file = new File(s);
		Scanner input = new Scanner(file);	// reads the stock dates from the file, initially in order and stores in dateArray
		
		while(input.hasNextLine()){
			String date = input.nextLine();
			String date2 = date.substring(0,4)+date.substring(5,7)+date.substring(8,10);
			int intDate = Integer.parseInt(date2);
			a[index] = intDate;
			index++;
		}
		input.close();
	}
	public static void readStockValues(String s,double[]a, int index) throws FileNotFoundException{
		File file = new File(s);
		Scanner input = new Scanner(file);	// reads the stock values from the file, initially in order and stores in valueArray
		
		while(input.hasNext()){
			String value = input.nextLine();
			double dValue = Double.parseDouble(value);
			a[index] = dValue;
			index++;
		}
		input.close();
	}
	private static void shuffleArray(int[] array,double [] array2){
	    int index;			// takes the dateArray and valueArray then shuffles their indexes-
	    int temp;			//creating shuffled arrays so when we insert to tree, the tree will be balanced
	    double temp2;
	    Random random = new Random();
	    for (int i = array.length - 1; i > 0; i--){
	        index = random.nextInt(i + 1);
	        temp = array[index];
	        temp2 = array2[index];
	        
	        array[index] = array[i];
	        array2[index] = array2[i];

	        array[i] = temp;
	        array2[i] = temp2;

	    }
	}
	public static void insertValues(BinaryTree tree){	//just a for loop insert method
		for(int i=0;i<dateArray.length;i++){
			tree.insert(dateArray[i], valueArray[i]);
			}
	}
	public static void insertToHashTable(String f,String[]array,int arrayIndex) throws FileNotFoundException{
		File file = new File(f);
		Scanner input = new Scanner(file);
		while(input.hasNextLine()){
			String word = input.next();
			word = word.toLowerCase();
			array[arrayIndex]=word;
			arrayIndex++;	
		}
		input.close();
		
		hashTable.addTheArray(array);
	}

}
