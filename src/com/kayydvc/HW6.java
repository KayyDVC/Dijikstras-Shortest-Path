package com.kayydvc;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class HW6 {

    public static void main(String[] args) throws IOException {

        Graph game = new Graph();
        // Read in functionality
        File in = null;
        if (args.length > 0) {
            in = new File(args[0]);
        } else {
            System.err.println("Did not receive a file from command line;");
            System.exit(0);
        }

        // file in for queries
        BufferedReader reader = new BufferedReader(new FileReader(in));

        String readLine = null;



        ArrayList<Node> tempQ = new ArrayList<>();
        Node tempNode;
        int lineNumb = -1;

        while ((readLine = reader.readLine()) != null) {
            //System.out.println(readLine);
            char[] individualWords = readLine.toCharArray();



            //should only evaluate on the first line of input; skips first
            // row containing board size
            if (individualWords[0] != '#'){

                //saving row and column count for future use
                StringBuilder s = new StringBuilder();
                int i = 0;
                while (Character.isDigit(individualWords[i])){
                    s.append(individualWords[i]);
                    i++;
                }

                i++;

                game.setRow(Integer.valueOf(s.toString()));

                StringBuilder t = new StringBuilder();

                while (i<individualWords.length && Character.isDigit(individualWords[i])){

                    t.append(individualWords[i]);
                    i++;
                }

                game.setColumn(Integer.valueOf(t.toString()));

            }

            else {
                for (int i = 0; i< game.getColumn(); i++){
                    //create new node then add to game board array list
                    tempNode = new Node(new Integer[]{lineNumb, i},
                            individualWords[i]);

                    if (tempNode.getVal() == 'T'){
                        game.setTron(tempNode);
                    }
                    else if (tempNode.getVal() == '#' || Character.isSpaceChar(tempNode.getVal()) || tempNode.getVal() == 'I'){
                        //do nothing
                    }
                    else {
                            game.bugs.add(tempNode);
                        }

                    game.board.add(tempNode);

                    //testing
                    //System.out.println("Queue Size: "+ tempQ.size() + ", i:
                    // " + i);

                    //add to temp queue for linking to other Nodes; tempQ.size
                        // returns column so, this should only evaluate true if
                        // there has been a whole row added
                        if (tempQ.size()>i){
                            //sets upper Node to

                            tempNode.setUpper(tempQ.get(i));
                            tempNode.getUpper().setLower(tempNode);

                            //only evaluates true when the Node is not the first
                            // of the row; if i=0, 0-1 !>0
                            if ((i-1)>=0){
                                //get previous node from tempQ then, set the
                                // previous node's "next" to current Node
                                tempNode.setPrev(tempQ.get((i-1)));
                                tempNode.getPrev().setNext(tempNode);
                            }
                        }
                        //should only be of size column-1 index

                        if (tempQ.size()==game.getColumn()){
                            tempQ.set(i,tempNode);
                        }
                        else{
                            tempQ.add(tempNode);
                        }

                    }

                }

                lineNumb++;
            }

        game.printBoard();

        game.startGame();

        game.printBugs();

    }
}
