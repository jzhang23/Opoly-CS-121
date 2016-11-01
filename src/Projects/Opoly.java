package Projects;

import java.util.Random;

public class Opoly {
 
    private static int size;
    private static int spin;
    private static int points;
    private static int numberOfTurns;
    private static char[] board;
    private static boolean first;
    private static Random random;
   
   
    public Opoly(int s){
        size = s;
        points = 100;
        numberOfTurns = 0;
        board = new char[size];
        first = true;
        this.random = new Random();
    }
   
    public Opoly(int s, long seed){
        size=s;
        points=100;
        numberOfTurns=0;
        board=new char[size];
        this.random = new Random(seed); //creates the seed
       
    }
 
    public void playGame(){
        drawBoard();
        while (isGameOver()){
            spinAndMove();
            drawBoard();
        }
        displayReport();
    }
 
    public void spin(){//used random not math.random
        spin = random.nextInt(5)+1;
    }
 
    public void move(){
        if (numberOfTurns%10==0) //rule 4
            points-=50;
        for(int i=0; i<size; i++) //gets positoon of player
            if (board[i]== 'o'){
                board[i]= '*';
                if (i==(size-1)){ //rule 2
                    board[i]= '*';
                    board[i-3]= 'o';
                    if (((i-3)%7==0)&&((i-3)!=0)) //rule 2
                        points*=2;
                    if (((i-3)+spin) >= size){
                        board[i-3] = '*';
                        points+=100; //rule 3
                        board[((i-3)+spin) - size] = 'o';
                    }else if(((i-3) + spin) <= size){
                        board[i-3] = '*';
                        board[(i-3)+spin]='o';
                    }
                }else if ((i+spin) >= size){
                    points+=100; //rule 3
                    board[(i+spin)-size]='o';
                }else if ((i+spin)<=size) //moves player
                    board[i+spin]='o';
 
                i=size; //resets
            }
    }
 
 
    public void spinAndMove(){
        numberOfTurns++;
        spin();
        move();
        for (int i =0; i<size; i++){  
            if (board[i] == 'o'){
                if (i==0)//rule 1
                    points*=2;
                else if ((i%7==0) && (i!=(size-1)))//rule 1
                    points*=2;
            }
        }
    }
 
    public boolean isGameOver(){
        boolean isOver = true;
        if (points >= 1000) //reward is >1000, gg
            isOver = false;
        return isOver;
    }
 
    public void drawBoard(){
        if (first){ //temp variable to make board for the first time
            board[0] = 'o';
            for(int i = 1; i < size; i++)
                board[i] = '*';
        }
 
        for(int i = 0; i < size; i++)
            System.out.print(board[i]);
 
        System.out.println(" " + points);
 
        first = false;
    }
 
    public void displayReport(){ //displays final results
        System.out.println("game over");
        System.out.println("rounds of play: " + numberOfTurns);
        System.out.println("final reward: " + points);
    }
}