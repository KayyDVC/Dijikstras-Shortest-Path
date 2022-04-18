package com.kayydvc;

import java.util.ArrayList;

public class Node {

	Node upper, lower, next, prev = null;
	Character move;
	Integer[] coords;
	Character val;
	//For use during Djikstra's Algo. will be replaced with the distance val of
	// the shorest path to Tron
	Integer distance = Integer.MAX_VALUE;

	//will contain the contain the shortest path from bug to Tron in order
	ArrayList <Node> path;

	public Node(Integer []c, char v){
		coords = c;
		val = Character.valueOf(v);

		//initialize path to only hold at least one node to begin with;
		// Should contain starting coords

		path = new ArrayList<>();

	}

	//getters and setters for nodes; Should not have to create new Node as it
	// should just link to a temp node

	public void setUpper(Node upper) {
		this.upper = upper;
	}

	public void setLower(Node lower) {
		this.lower = lower;
	}

	public void setNext(Node next) {
		this.next = next;
	}

	public void setPrev(Node prev) {
		this.prev = prev;
	}

	public Node getUpper() {
		return upper;
	}

	public Node getLower() {
		return lower;
	}

	public Node getNext() {
		return next;
	}

	public Node getPrev() {
		return prev;
	}

	public Character getVal() {
		return val;
	}

	public void setVal(char val) {
		this.val = Character.valueOf(val);
	}

	public Integer[] getCoords() {
		return coords;
	}

	public void checkDistance(Integer parentDistance, Node prev,
	                          ArrayList<Node> bugs){
		//temporarily holds the distance that is currently being calculated
		Integer temp = parentDistance +1;
		//take the shorter of two distances and set that to the node's
		// distance from Tron or other Node
		if (temp < distance){

			distance = temp;
				//adds path in reverse order; will sort before output using
				// Collections.reverse
			path.add(prev);
			for (Node n : prev.path){
				if (!path.contains(n)){
					path.add(n);
				}
			}
		}
	}

}

