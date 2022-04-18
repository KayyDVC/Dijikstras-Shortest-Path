
package com.kayydvc;


import java.util.*;

public class Graph {

	ArrayList<Node> board;
	Integer row, column = 0;

	//keeps track of initial coordinates of Tron and Bugs
	Node tron;
	ArrayList<Node> bugs;

	//constructor
	public Graph(){
		board = new ArrayList<>();
		bugs = new ArrayList<>();
	}

	//Getters & Setters
	public void setTron(Node tron) {
		this.tron = tron;
	}

	public Node getTron() {
		return tron;
	}

	public void setColumn(Integer column) {
		this.column = column;
	}

	public void setRow(Integer row) {
		this.row = row;
	}

	public Integer getColumn() {
		return column;
	}

	public Integer getRow() {
		return row;
	}

	//sorts bug in ascending alphabetical order using simple "check behind"
	// algorithm
	public void sortBugs(){
		Node temp;
		bugs.sort(new Comparator<Node>() {
			@Override
			public int compare(Node o1, Node o2) {
				return o1.getVal().compareTo(o2.getVal());
			}
		});
		for (Node bug: bugs){
			if (bug.getCoords()[0]> bug.path.get(0).getCoords()[0]){
				bug.move = 'u';
			}
			if (bug.getCoords()[0]< bug.path.get(0).getCoords()[0]){
				bug.move = 'd';
			}
			if (bug.getCoords()[1]> bug.path.get(0).getCoords()[1]){
				bug.move = 'l';
			}
			if (bug.getCoords()[1]< bug.path.get(0).getCoords()[1]){
				bug.move = 'r';
			}
		}
	}

	//previous method was did not work. Replaced with a comparator overriden
	// function
//		for (int i = 0; i<bugs.size(); i++){
//			ar
			//compares ascii values
//			if (bugs.get(i).getVal() < bugs.get(i-1).getVal()){
//				temp = bugs.get(i-1);
//				bugs.set(i-1, bugs.get(i));
//				bugs.set(i, temp);
//			}




	public void printBugs(){
		sortBugs();
		System.out.println("\n");
		for (Node n : bugs){
			//prints out bug name, distance and initial position
			System.out.print("Bug "+ n.getVal()+ ": " + n.move+ " " + n.distance+ " " +
					"(" + n.getCoords()[0] + ", " + n.getCoords()[1] + ")");
			for (Node p : n.path){
				//prints out shortest path
				System.out.print(" (" + p.coords[0] + ", " + p.coords[1] + ")");
			}
			System.out.println();
			//Testing
//			System.out.println("\tAbove Coords: ("  + n.getUpper().getCoords()[0] + ", "+ n.getUpper().getCoords()[1] + ")");
//			System.out.println("\tBelow Coords: ("  + n.getLower().getCoords()[0] + ", "+ n.getLower().getCoords()[1] + ")");
//			System.out.println("\tNext Coords: ("  + n.getNext().getCoords()[0] + ", "+ n.getNext().getCoords()[1] + ")");
//			System.out.println("\tPrev Coords: ("  + n.getPrev().getCoords()[0] + ", "+ n.getPrev().getCoords()[1] + ")");
		}

	}


	public void printBoard(){
		//tempRow to be used to differentiate between rows
		int tempRow = -1;

		//create space then add top row of numbs corresponding to columns
		System.out.print("\n  ");

		for (int i=0; i<column; i++){
			System.out.print(i);
		}

		for (Node n: board){
			Integer nRow = n.getCoords()[0];
			//only evaluates to true on the first position of each row aka first
			// "#" node of row
			if (nRow>tempRow){
				System.out.print("\n" + nRow + " " +n.getVal());
				tempRow++;
			}
			//if not first "#" of row, just append to console
			else{
				System.out.print(n.getVal());
			}

		}

		}

			//get player first move then check if move is valid using
			// validEntry
	public void startGame(){

			//provides readin functionality
			Scanner input = new Scanner(System.in);

			System.out.print("\n\nPlease enter your move [u(p), d(own), l(eft)," +
					" or r(ight)]: ");


			String choice = input.next();

			validEntry(choice);


			printBoard();

			shortestPath();
	}

	//checks for move validity using moveTron; If moveTron returns false,
	// recursively call itself until the move is valid
	public void validEntry(String choice){
		Scanner input = new Scanner(System.in);

		//evaluates true when anything other than {"L, R, D, U, r, d, l ,
		// u" } are entered
		while (!choice.equals("u")&& !choice.equals("r") && !choice.equals(
				"l") && !choice.equals("d") && !choice.equals("U")&& !choice.equals("R") && !choice.equals(
				"L") && !choice.equals("D") ){
			//System.out.println("Choice: " + choice);
			System.out.println("\nInvalid entry; Try again");

			System.out.print("\n\nPlease enter your move [u(p), d(own), l(eft)," +
					" or r(ight)]: ");
			choice = input.next();
		}

		if (!moveTron(choice)){
			validEntry("1");
		}

	}

	//checks if move can be done; Allows move as long as it's not into a
	// barrier.
	public boolean moveTron(String choice){

		switch (choice) {
			case "L", "l" -> {
				//checks to make sure move is valid
				if (tron.getPrev().getVal() == '#') {
					return false;
				}
				//sets value to space
				tron.val = 32;
				//move assigns Tron correct Node
				setTron(tron.prev);
				//set new node's val to T for desired graphic functionality
				tron.val = Character.valueOf('T');
			}
			case "R", "r" -> {
				if (tron.getNext().getVal() == '#') {
					return false;
				}
				tron.val = 32;
				setTron(tron.next);
				tron.val = Character.valueOf('T');
			}
			case "U", "u" -> {
				if (tron.getUpper().getVal() == '#') {
					return false;
				}
				tron.val = 32;
				setTron(tron.getUpper());
				tron.val = Character.valueOf('T');
			}
			case "D", "d" -> {
				if (tron.getLower().getVal() == '#') {
					return false;
				}
				tron.val = 32;
				setTron(tron.getLower());
				tron.val = Character.valueOf('T');
			}
		}
		return  true;
	}

	//Implements Dijikstra's algorithm by adding the shortest distance from
	// Tron to every other tile on the board excluding barrier
	public void shortestPath(){

		//no moves needed for tron to get to itself
		tron.distance = Integer.valueOf(0);
		//creates a sort of queue stucture for nodes that need to be visited
		ArrayList<Node> unvisited = board;

		Node curr = tron;

		//queue to hold all Nodes to search
		Queue<Node> q = new LinkedList<Node>();

		q.add(curr);

		while(!q.isEmpty()) {

			//check if Node is in unvisited list; Prevents checking the same node
			// multiple times O(4n) each iteration, for a total about O(4n* logn)
			// , O(n) time complexity
			if (curr.getUpper() != null && unvisited.contains(curr.getUpper()) && curr.getUpper().getVal() != '#') {
				curr.getUpper().checkDistance(curr.distance, curr, bugs);
				q.add(curr.getUpper());
			}
			//removes barrier node from list of unvisited nodes since the program
			// must traverse around the node.
			else {
				unvisited.remove(curr.getUpper());
			}


			if (curr.getLower() != null && unvisited.contains(curr.getLower()) && curr.getLower().getVal() != '#') {
				curr.getLower().checkDistance(curr.distance,curr, bugs);
				q.add(curr.getLower());
			} else {
				unvisited.remove(curr.getLower());
			}

			if (curr.getPrev() != null && unvisited.contains(curr.getPrev()) && curr.getPrev().getVal() != '#') {
				curr.getPrev().checkDistance(curr.distance, curr, bugs);
				q.add(curr.getPrev());
			} else {
				unvisited.remove(curr.getPrev());
			}
			if (curr.getNext() != null && unvisited.contains(curr.getNext()) && curr.getNext().getVal() != '#') {
				curr.getNext().checkDistance(curr.distance,curr, bugs);
				q.add(curr.getNext());
			} else {
				unvisited.remove(curr.getNext());
			}

			//remove curr node from list of nodes to be explored and get new
			// node from queue
			q.remove();
			unvisited.remove(curr);
			curr = q.peek();
		}

	}


}



