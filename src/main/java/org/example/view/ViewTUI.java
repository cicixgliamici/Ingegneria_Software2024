package org.example.view;

public class ViewTUI extends View{

    public ViewTUI() {

    }

    public void PrintView(){
        PrintHand();
        PrintPlayerCardArea();
    }

    public void PrintHand(){
        for (String string : Hand){
            System.out.println(string);
        }
    };

    public void PrintPlayerCardArea(){
        for (String string : PlayerCardArea){
            System.out.println(string);
        }
    }

}
