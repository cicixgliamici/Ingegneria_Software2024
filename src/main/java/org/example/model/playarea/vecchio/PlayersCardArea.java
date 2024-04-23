/*
package org.example.model.PlayArea.vecchio;


import org.example.model.PlayArea.CounterResources;
import org.example.model.deck.Card;
import org.example.model.deck.Corner;
import org.example.model.deck.SideCard;
import org.example.model.deck.enumeration.*;
import org.example.model.deck.enumeration.cast.CastCardRes;

import java.util.ArrayList;
import java.util.List;


public class PlayersCardArea {
    private MatrixCell[][] PlayedCard;
    private final int DIM = 10;
    CounterResources counter = new CounterResources();

    List<MatrixCell> AvailableCorners = new ArrayList<MatrixCell>();

    public void insertElement(MatrixCell matrixCell, int Line, int Column){
        this.PlayedCard[Line][Column] = matrixCell;
    }


    //metodo che va a modificare la cella effettiva della matrice e a inserire il nuovo elemento nella lista di angoli disponibili
    public void InsertPlayedCard(int Line, int Column, Position position, Card LinkCard, SideCard sideCard){
        MatrixCell matrixCell = new MatrixCell( LinkCard, position, sideCard);
        insertElement(matrixCell, Line, Column );
        this.AvailableCorners.add(matrixCell);
        //ricordarsi di inserire un metodo che rimuova un angolo dalla lista se viene coperto dal nuovo angolo aggiunto
    }



    public void InitializedPlayerArea(Card cardStarter, SideCard choosenSide){

        //aggiunge i corner alla matrice
        InsertPlayedCard(this.DIM, this.DIM, Position.TOPL, cardStarter, choosenSide);
        // aggiunta dell'angolo
        InsertPlayedCard(this.DIM,this.DIM + 1, Position.TOPR, cardStarter, choosenSide);
        InsertPlayedCard(this.DIM + 1,this.DIM, Position.BOTTOML, cardStarter, choosenSide);
        InsertPlayedCard(this.DIM + 1,this.DIM + 1, Position.BOTTOMR, cardStarter, choosenSide);

        //aumenta le risosre della carta iniziale
        if (choosenSide.getSide() == Side.BACK) {
            if ((choosenSide.getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getBackCorners().get(1).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(choosenSide.getBackCorners().get(1).getPropertiesCorner());
            } else if ((choosenSide.getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getBackCorners().get(2).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(cardStarter.getPropCorn(2));
            } else if ((choosenSide.getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getBackCorners().get(3).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(cardStarter.getPropCorn(3));
            } else if ((choosenSide.getBackCorners().get(4).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getBackCorners().get(4).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(cardStarter.getPropCorn(4));
            }
        }
        else if(choosenSide.getSide() == Side.FRONT){
            if ((choosenSide.getFrontCorners().get(1).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getFrontCorners().get(1).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(cardStarter.getPropCorn(1));
            } else if ((choosenSide.getFrontCorners().get(2).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getFrontCorners().get(2).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(cardStarter.getPropCorn(2));
            } else if ((choosenSide.getFrontCorners().get(3).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getFrontCorners().get(3).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(cardStarter.getPropCorn(3));
            } else if ((choosenSide.getFrontCorners().get(4).getPropertiesCorner() != PropertiesCorner.HIDDEN) && (choosenSide.getFrontCorners().get(4).getPropertiesCorner() != PropertiesCorner.EMPTY)) {
                counter.AddResource(cardStarter.getPropCorn(4));
            }
            //aggiunta nuova classe per fare il cast
            for (CardRes cardRes : cardStarter.getRequireGold()) {
                CastCardRes castCardRes= new CastCardRes(cardRes);
                counter.AddResource(castCardRes.getPropertiesCorner());
            }
        }
    }
    public int SearchCardMatrixToLinkCornerColumn(Card searchCard, Corner linkCorner){
        int j = 0;
        int i = 0;
        while ((this.PlayedCard[i][j].getCardCell() != searchCard) && (this.PlayedCard[i][j].getAvailabilityCorner() != linkCorner.getPosition())){
            while ((this.PlayedCard[i][j].getCardCell() != searchCard) && (this.PlayedCard[i][j].getAvailabilityCorner() != linkCorner.getPosition())) {
                i++;
            };
            j++;
        };
        return i;
    }


    public int SearchCardMatrixToLinkCornerLine(Card searchCard, Corner linkCorner){
        int j = 0;
        int i = 0;
        while ((this.PlayedCard[i][j].getCardCell() != searchCard) && (this.PlayedCard[i][j].getAvailabilityCorner() != linkCorner.getPosition())){
            while ((this.PlayedCard[i][j].getCardCell() != searchCard) && (this.PlayedCard[i][j].getAvailabilityCorner() != linkCorner.getPosition())) {
                i++;
            };
            j++;
        };
        return j;
    } //tocca trovare un search che sia più elegante e che non faccia venire un infarto a bill gates

    /*public void  UpdateCornerList(Corner RemDataCorner, Card AddDataCard){
        if(RemDataCorner.getPropertiesCorner() == PropertiesCorner.INKWELL || RemDataCorner.getPropertiesCorner() == PropertiesCorner.MANUSCRIPT || RemDataCorner.getPropertiesCorner() == PropertiesCorner.QUILL){
            this.PlayerObject.remove(RemDataCorner.getPropertiesCorner());
        }
        else if(RemDataCorner.getPropertiesCorner() == PropertiesCorner.ANIMAL || RemDataCorner.getPropertiesCorner() == PropertiesCorner.PLANT || RemDataCorner.getPropertiesCorner() == PropertiesCorner.INSECT || RemDataCorner.getPropertiesCorner() == PropertiesCorner.FUNGI){
            this.PlayerRes.remove(RemDataCorner.getPropertiesCorner());
        }
        if (AddDataCard.getSide().getSide() == Side.FRONT){
            //AddDataCard.getSide().getFrontCorners().iterator(); informarsi meglio sull'iterator
        }
    }
    public void InsertNewCard(Card TouchedCard, Card NewCard, Corner ChoosenCorner){
        if (ChoosenCorner.getPosition() == Position.TOPL){
            UpdateListData(ChoosenCorner,NewCard);
            //aggiungere TOPR alla posizione i+1 j
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.TOPR, NewCard);
            //aggiungere BOTTOML alla posizione i j-1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) , (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.BOTTOML, NewCard);
            //aggiungere TOPL alla posizione i-1 j-1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) - 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.TOPL, NewCard);

        }
        else if (ChoosenCorner.getPosition() == Position.TOPR){
            //aggiungere TOPR alla posizione  i+1 j+1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.TOPR, NewCard);
            //aggiungere BOTTOMR alla posizione i j+1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.BOTTOMR, NewCard);
            //aggiungere TOPL alla poszione TOPL i-1 j
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) - 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.TOPL, NewCard);
            UpdateListData(ChoosenCorner,NewCard);
        }
        else if (ChoosenCorner.getPosition() == Position.BOTTOMR){
            //aggiungere TOPR alla posizione i j+1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.TOPR, NewCard);
            //aggiungere BOTTOMR i+1 j+1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.BOTTOMR, NewCard);
            //aggiungere BOTTOML i+1 j
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.BOTTOML, NewCard);
            UpdateListData(ChoosenCorner,NewCard);
        }
        else if(ChoosenCorner.getPosition() == Position.BOTTOML){
            //aggiungere TOPL i j-1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.TOPL, NewCard);
            //aggiungere BOTTOMR i +1 j
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.BOTTOMR, NewCard);
            //aggiungere BOTTOML i+1 j-1
            InsertPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.BOTTOML, NewCard);
            UpdateListData(ChoosenCorner,NewCard);
        }
    }

    }*/

