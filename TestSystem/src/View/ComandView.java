package view;

import java.util.List;
import java.util.Scanner;

import control.Control;

public class ComandView {
	Control control = new Control();
	Scanner sc = new Scanner(System.in);
	int type;
	
	public void printFirstMenu(){
		System.out.println("1) Create a new Survey\n"
				+ "2) Create a new Test\n"
				+ "3) Display Survey\n"
				+ "4) Display a Test \n"
				+ "5) Save a Survey\n"
				+ "6) Save a Test \n"
				+ "7) Modify a Survey\n"
				+ "8) Modify a Test\n"
				+ "9) Take a Survey\n"
				+ "10)Take a Test\n"		
				+ "11)Look survey outcome\n"
				+ "12)Look test outcome\n"
				+ "13) Quit\n"
		);
		int next = sc.nextInt();
		switch(next){
		case 1: this.createSurvey();break;
		case 2: this.createTest();break;
		case 3: this.display(0);break;
		case 4: this.display(1);break;
		case 5:
		case 6: control.save();break;
		case 7: this.mofify(0);break;
		case 8:	this.mofify(1);break;
		case 9: this.answer(0);break;
		case 10:this.answer(1);break;
		case 11:this.displayOutcome(0);break;
		case 12:this.displayOutcome(1);break;
		case 13:System.exit(0);
		}
	}
	
	public void displayOutcome(int type){
		System.out.println("choose the page:");
		Scanner scan = new Scanner(System.in);
		List<String> pageNameList = control.getPageName(type);
		for(int i=0; i<pageNameList.size(); i++){
			System.out.println(i+": "+pageNameList.get(i));
		}
		int index = sc.nextInt();
		System.out.println(control.getOutcome(index, type));
		System.out.println("Enter any key to continu");
		scan.nextLine();
	}
	
	public void answer(int type){
		Scanner scan = new Scanner(System.in);
		List<String> nameList = control.getPageName(type);
		for(int i=0; i<nameList.size(); i++){
			System.out.println(i+") "+nameList.get(i));
		}
		
		int pageIndex = sc.nextInt();
		control.loadPage(pageIndex, 1);
		System.out.println("Input your name: ");
		String personName = scan.nextLine();
		control.setRecordName(personName);
		
		while(control.hasNextQuestion()){
			System.out.println(control.nextQuestion());
			String answer = scan.nextLine();
			control.answerQuestion(answer);
		}
		
		System.out.println("Ok, it's all!");
		control.saveAnswer();
	}
	
	public void mofify(int type){
		this.type = type;
		this.display(type);
		System.out.println("Please choose which question that you want to modify: ");
		int index = sc.nextInt();
		int next  = control.modify(index);
		switch(next){
		case 0:this.modifyTFQuestion();break;
		case 1:this.modifyChooicQuestion();break;
		case 2:this.modifyTFQuestion();break;
		case 3:this.modifyEssayQuestion();break;
		case 4:this.modifyRankQuestion();break;
		case 5:this.modifyMapQuestion();break;
		case -1:return;
		}
	}
	
	public void modifyTFQuestion(){
		Scanner scan = new Scanner(System.in);
		System.out.println("1. modify prompt: ");
		if(type == 1)
			System.out.println("2. modify answer");
		int next = sc.nextInt();
		if(next == 1){
			System.out.println("Please input new prompt: ");
			String prompt = scan.nextLine();
			control.setPrompt(prompt);
		}else if(next == 2 && type == 1){
			System.out.println("Please input new anwser: ");
			String answer = scan.nextLine();
			control.setAnswer(answer);
		}else{
			System.out.println("We don't hava this item");
		}
	}
	
	public void modifyChooicQuestion(){
		Scanner scan = new Scanner(System.in);
		System.out.println("1.modify the prompt\n"	
				+"2.modify the number of choices available\n"	
				+"3.add choices\n"
				+"4.remove choices\n"	
				+"5.modify any choices\n"	
				+"6.the number of selections allowed\n");
		if(type == 1)
			System.out.println("7. modify correct answer:");
		int next = sc.nextInt();
		switch(next){
		case 1: System.out.println("Please input new prompt:");
				String prompt = scan.nextLine();
				control.setPrompt(prompt);
				break;
		case 2: System.out.println("Please input new item number");
				int num = sc.nextInt();
				boolean outcome2 = control.changeItemNumber(num);
				if(outcome2){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
		case 3: System.out.println("Please input new choice:");
				String item = scan.nextLine();
				control.setItem(item);
				break;
		case 4: System.out.println("Please input the index of the choice");
				int index = sc.nextInt();
				boolean outcome = control.remove(index);
				if(outcome){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
				break;
		case 5: System.out.println("Please input the index of the choice:");
				int index1 = sc.nextInt();
				System.out.println("Please input the new item:");
				String item1 = scan.nextLine();
				boolean outcome1 = control.changeItem(index1, item1);
				if(outcome1){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
				break;
		case 6: 
		case 7: if(type == 0)
					return;
				System.out.println("Please input new answer:");
				String answer = scan.nextLine();
				control.setAnswer(answer);
				break;
		default: System.out.println("This is a wrong input");
		}
	}
	
	public void modifyRankQuestion(){
		Scanner scan = new Scanner(System.in);
		System.out.println("1. modify the prompt\n"
							+"2. modify the number of items available\n"	
							+"3. modify the items to be ranked\n"	);
		if(type == 1)
			System.out.println("4. modify correct answer:");
		int next = sc.nextInt();
		switch(next){
		case 1: System.out.println("Please input new prompt:");
				String prompt = scan.nextLine();
				control.setPrompt(prompt);
				break;
		case 2: System.out.println("Please input new number:");
				int num = sc.nextInt();
				boolean outcome2 = control.changeItemNumber(num);
				if(outcome2){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
				break;
		case 3: System.out.println("Please input the item index: ");
				int index = sc.nextInt();
				System.out.println("Please input the new item");
				String item = scan.nextLine();
				boolean outcome = control.changeItem(index, item);
				if(outcome){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
				break;
		case 4: if(type == 0)
					return;
				System.out.println("Please input new answer:");
				String answer = scan.nextLine();
				control.setAnswer(answer);
				break;
		default: System.out.println("This is a wrong input");
		}
	}
	
	public void modifyMapQuestion(){
		Scanner scan = new Scanner(System.in);
		System.out.println("1. modify the prompt\n"	
							+"2. modify the number of items available\n"	
							+"3. modify the items on the left\n"	
							+"4. modify the items on the right\n");
		if(type == 1)
			System.out.println("5. modify correct answer:");
		int next = sc.nextInt();
		switch(next){
		case 1: System.out.println("Please input new prompt:");
				String prompt = scan.nextLine();
				control.setPrompt(prompt);
				break;
		case 2: System.out.println("Please input new Number: ");
				int num = sc.nextInt();
				boolean outcome2 = control.changeItemNumber(num);
				if(outcome2){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
				break;
		case 3: control.setSide(1);
				boolean outcome = this.changeItem();
				if(outcome){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
				break;
		case 4: control.setSide(2);
				boolean outcome1 = this.changeItem();
				if(outcome1){
					System.out.println("Ok, it has changed");
				}{
					System.out.println("We don't have this item");
				}
				break;				
		case 5: if(type == 0)
					return;
				System.out.println("Please input new answer: ");
				String answer = scan.nextLine();
				control.setAnswer(answer);
				break;
		}
	}
	
	private boolean changeItem(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input question index");
		int index = sc.nextInt();
		System.out.println("Please input new item");
		String item = scan.nextLine();
		return control.changeItem(index, item);
	}
	
	public void modifyEssayQuestion(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Please input new prompt: ");
		String prompt = scan.nextLine();
		control.setPrompt(prompt);
	}
	
	
	public void display(int type){
		System.out.println("choose the page:");
		List<String> pageNameList = control.getPageName(type);
		for(int i=0; i<pageNameList.size(); i++){
			System.out.println(i+": "+pageNameList.get(i));
		}
		int index = sc.nextInt();
		List<String> question = control.displayPage(index, type);
		for(int i=0; i<question.size(); i++){
			System.out.println(question.get(i));
		}
		System.out.println("Enter any key to continue");
		String any = sc.next();
	}
	
	
	public void createTest(){
		control.createPage(1);
		System.out.println("Please input your pageName");
		String name = sc.next();
		control.setPageName(name);
		type = 1;
		this.addQuestion();
		System.out.println();
	}
	
	public void createSurvey(){
		control.createPage(0);
		System.out.println("Please input your pageName");
		String name = sc.next();
		control.setPageName(name);
		type = 0;
		this.addQuestion();
		System.out.println();
	}
	
	public void addQuestion(){
		System.out.println("1. add a new question\n"
				+ "2. compele this page\n");
		int next = sc.nextInt();
		if(next == 1){
			System.out.println("1) Add a new T/F question\n"
					+ "2) Add a new multiple choice question\n"
					+ "3) Add a new short answer question\n"
					+ "4) Add a new essay question\n"
					+ "5) Add a new ranking question\n"
					+ "6) Add a new map question\n");
			int temp = sc.nextInt();
			switch(temp){
			case 1: this.addDecideQuestion();break;
			case 2: this.addChoiceQuestion();break;
			case 3: this.addTextQuestion(); break;
			case 4: this.addEssayQuestion();break;
			case 5: this.addRankQuestion();break;
			case 6: this.addMapQuestion();break;
			}
			this.addQuestion();
		}
	}
	
	public void addDecideQuestion(){
		System.out.println("Enter the prompt for you True/False question:\n");
		Scanner scan = new Scanner(System.in);
		String prompt = scan.nextLine();
		if(type == 1){
			System.out.println("Please enter you anwser:\n");
			String answer = scan.nextLine();
			System.out.println("Please enter your score\n");
			int score = sc.nextInt();
			control.createDecideQuestion(prompt, score, answer);
			return;
		}
		control.createDecideQuestion(prompt);
	}
	
	public void addChoiceQuestion(){
		Scanner scan = new Scanner(System.in);
		System.out.println("Enter the prompt for you Choice question:");
		String prompt = scan.nextLine();
		System.out.println("Please enter your choice number");
		int number = sc.nextInt();
		String[] items = new String[number];
		for(int i=0; i<number; i++){
			System.out.println("\nEnter your choice "+i);
			items[i] = scan.nextLine();
		}
		if(type == 1){
			System.out.println("Please enter you anwser:\n");
			String answer = scan.nextLine();
			System.out.println("Please enter your score\n");
			int score = sc.nextInt();
			control.createChoiceQuestion(prompt, items, score, answer);
			return;
		}
		control.createChoiceQuestion(prompt, items);
	}
	
	public void addTextQuestion(){
		System.out.println("Enter the prompt for you text question:");
		Scanner scan = new Scanner(System.in);
		String prompt = scan.nextLine();
		if(type == 1){
			System.out.println("Please enter you anwser:\n");
			String answer = scan.nextLine();
			System.out.println("Please enter your score\n");
			int score = sc.nextInt();
			control.createTextQuestion(prompt, score, answer);
			return;
		}
		control.createTextQuestion(prompt);
	}
	
	public void addEssayQuestion(){
		System.out.println("Enter the prompt for you essay question:");
		Scanner scan = new Scanner(System.in);
		String prompt = scan.nextLine();
		control.createEssayQuestion(prompt);
	}
	
	public void addMapQuestion(){
		System.out.println("Enter the prompt for you map question:");
		Scanner scan = new Scanner(System.in);
		String prompt = scan.nextLine();
		System.out.println("Please enter your left side choice number");
		int number = sc.nextInt();
		String[] side1 = new String[number];
		for(int i=0; i<number; i++){
			System.out.println("\nEnter your choice "+i);
			side1[i] = scan.nextLine();
		}
		System.out.println("Please enter your right side choice number");
		number = sc.nextInt();
		String[] side2 = new String[number];
		for(int i=0; i<number; i++){
			System.out.println("\nEnter your choice "+i);
			side2[i] = scan.nextLine();
		}
		if(type == 1){
			System.out.println("Please enter you anwser:\n");
			String answer = scan.nextLine();
			System.out.println("Please enter your score\n");
			int score = sc.nextInt();
			control.createMapQuestion(prompt, side1, side2, score, answer);
			return;
		}
		control.createMapQuestion(prompt, side1, side2);
	}
	
	public void addRankQuestion(){
		System.out.println("Enter the prompt for you rank question:");
		Scanner scan = new Scanner(System.in);
		String prompt = scan.nextLine();
		System.out.println("Please enter your choice number");
		int number = sc.nextInt();
		String[] items = new String[number];
		for(int i=0; i<number; i++){
			System.out.println("\nEnter your choice "+i);
			items[i] = scan.nextLine();
		}
		if(type == 1){
			System.out.println("Please enter you anwser:\n");
			String answer = scan.nextLine();
			System.out.println("Please enter your score\n");
			int score = sc.nextInt();
			control.createRankQuestion(prompt, items, score, answer);
			return;
		}
		control.createRankQuestion(prompt, items);
	}
	

	
}
