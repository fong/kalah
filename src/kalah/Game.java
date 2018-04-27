/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kalah;

import com.qualitascorpus.testsupport.IO;

/**
 *
 * @author tofutaco
 */
public class Game {
    boolean lastTurn = false;
    int nextTurn = 2;
    int currentTurn = 1;
    int housePosition;
    boolean victory = false;
    public boolean running;
    boolean quit = false;
    String prompt = "Player P1's turn - Specify house number or 'q' to quit: ";
    Store p1Store;
    House[] p1Houses = new House[6];
    Store p2Store;
    House[] p2Houses = new House[6];

    public Game(){
        p1Store = new Store();
        p2Store = new Store();

        for (int i = 0; i < 6; i++){
            p1Houses[i] = new House(i);
            p2Houses[i] = new House(i);
        }
    }
    
    public void tick(IO io){
        int command;
        do {            
            command = parseInput(io);
            
            if (command > 0){
                nextTurn = move(command);
                this.draw(io); 
                
                prompt = "Player P" + nextTurn + "'s turn - Specify house number or 'q' to quit: ";
                currentTurn = nextTurn;
            } else {
                io.println("Game over");
                this.draw(io);
                nextTurn = 0;
                running = false;
            }
            

        } while (!gameOver() && nextTurn != 0);
                  
        if (gameOver()){
            clearBoard();
            victory = true;
            this.draw(io);
            running = false;
        }
    }
    
    public void draw(IO io){
        io.println("+----+-------+-------+-------+-------+-------+-------+----+");
        io.println("| P2 | 6[" + p2Houses[5].draw() +
                    "] | 5[" + p2Houses[4].draw() +
                    "] | 4[" + p2Houses[3].draw() +
                    "] | 3[" + p2Houses[2].draw() +
                    "] | 2[" + p2Houses[1].draw() +
                    "] | 1[" + p2Houses[0].draw() +
                    "] | " + p1Store.draw() + " |");
        io.println("|    |-------+-------+-------+-------+-------+-------|    |");
        io.println("| " + p2Store.draw() + " | 1[" + p1Houses[0].draw() +
                    "] | 2[" + p1Houses[1].draw() + 
                    "] | 3[" + p1Houses[2].draw() +
                    "] | 4[" + p1Houses[3].draw() +
                    "] | 5[" + p1Houses[4].draw() +
                    "] | 6[" + p1Houses[5].draw() + "] | P1 |");

        io.println("+----+-------+-------+-------+-------+-------+-------+----+");

        if (victory){
            io.println("\tplayer 1:" + p1Store.draw());
            io.println("\tplayer 2:" + p2Store.draw());
        }
    }
    
    private int parseInput(IO io){
        String input;
        int house;
        boolean validInput = false;
        
        while (!validInput) {
            
            input = io.readFromKeyboard(prompt);
            
            try {
                house = Integer.parseInt(input);
                
                if ((house < 0) || (house > 6)){
                    validInput = false;
                    prompt = "Bad input: ";
                } else if (nextTurn == 2) {
                    if (p2Houses[house].numberOfSeeds == 0){
                        io.println("House is empty. Move again.");
                        this.draw(io);
                        validInput = false;
                    } else {
                        return house;
                    }
                } else if (nextTurn == 1) {
                    if (p1Houses[house].numberOfSeeds == 0){
                        io.println("House is empty. Move again.");
                        this.draw(io);
                        validInput = false;
                    } else {
                        return house;
                    }
                } 
            } catch (NumberFormatException e){
                if (!"q".equals(input)){
                    validInput = false;
                } else {
                    validInput = true;
                    return 0;
                }
            }
        }
        return 0;
    }
        
    private int move(int command){
        plantSeeds(command);
        return lastSeed(command);
    }
    
    private void plantSeeds(int house){
        int housePosition = house;
        
        if (currentTurn == 1){
            
        } else if (currentTurn == 2){
            
        }
        
        

    }
    
    private int lastSeed(int house){
        
        return 0;
    }
    
    private boolean gameOver(){
        int player1Sum = 0, player2Sum = 0;
                
        for (int i = 0; i < 6; i++){
            player2Sum += p2Houses[i].numberOfSeeds;
            player1Sum += p1Houses[i].numberOfSeeds;
        }
        
        return (player1Sum == 0 || player2Sum == 0);
    }
    
    private void clearBoard(){
        for (int i = 0; i < 6; i++){
            p2Store.addNSeeds(p2Houses[i].removeAllSeeds());
            p1Store.addNSeeds(p1Houses[i].removeAllSeeds());
        }
    }
}

