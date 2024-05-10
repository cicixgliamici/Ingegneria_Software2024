package org.example.view;

public class ViewTUI extends View{

    public ViewTUI() {

    }

    public void drawnCard(int id){
        //todo ricostruire carta da id
        System.out.println("You have drawn:" );
    }
    public void hasDrawn(String username, int id){
        //todo ricostruire carta da id
        System.out.println("Player:" + username + "has drawn"  );
    }
    public void playedCard(int id, int x, int y){
        //todo ricostruire carta da id
        System.out.println("You played card" + "at position:(" +x+ "," +y+")" );
    }
    public void hasPlayed(String username, int id){
        System.out.println("Player:" + username + "has played");
    }
    public void unplayable(int id, int x, int y){
        System.out.println("The card"+ "is unplayable at position:(" +x+ "," +y+")");
    }
    public void firstHand(int id1, int id2, int id3, int id4, int id5, int id6){
        System.out.println("In your hand:" +
                            "\n" +
                            "\n" +
                            "\n Now please choose the side of the starter card" +
                            "\n And what Objective Card you want to keep" +
                            "\n");
    }

    public void pubObj(int id1, int id2){
        System.out.println("These are the public objects:"+
                            "\n"+ ".");
    }

    public void printView(){
        printHand();
        printPlayerCardArea();
    }

    public void printHand(){
        for (String string : Hand){
            System.out.println(string);
        }
    };

    public void printPlayerCardArea(){
        for (String string : PlayerCardArea){
            System.out.println(string);
        }
    }

    public void Interpreter(String message){

    }

}
