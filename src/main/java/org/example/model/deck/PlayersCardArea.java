package org.example.model.deck;

import org.example.model.deck.enumeration.*;
import org.example.model.deck.enumeration.cast.CastCardRes;

public class PlayersCardArea {
    private MatrixCell[][] PlayedCard;

    private final int DIM = 10;
    CounterResources counter = new CounterResources();

    public void ModifyPlayedCard(int Line, int Column, Position Value, Card LinkCard){
        this.PlayedCard[Line][Column].setAvailabilityCorner(Value);
        this.PlayedCard[Line][Column].setCardCell(LinkCard);
    }

    public void InitializedPlayerArea(Card cardStarter){

        //aggiunge i corner alla matrice
        ModifyPlayedCard( this.DIM, this.DIM, Position.TOPL, cardStarter);
        ModifyPlayedCard(this.DIM,this.DIM + 1, Position.TOPR, cardStarter);
        ModifyPlayedCard(this.DIM + 1,this.DIM, Position.BOTTOML, cardStarter);
        ModifyPlayedCard(this.DIM + 1,this.DIM + 1, Position.BOTTOMR, cardStarter);

        //aumenta le risosre della carta iniziale
        if (cardStarter.getSide().getSide() == Side.BACK) {
            if (cardStarter.getPropCorn(1) != null) {
                counter.AddResource(cardStarter.getPropCorn(1));
            } else if (cardStarter.getPropCorn(2)  != null) {
                counter.AddResource(cardStarter.getPropCorn(2));
            } else if (cardStarter.getPropCorn(3) != null) {
                counter.AddResource(cardStarter.getPropCorn(3));
            } else if (cardStarter.getPropCorn(4)  != null) {
                counter.AddResource(cardStarter.getPropCorn(4));
            }
        }
        else if(cardStarter.getSide().getSide() == Side.FRONT){
            if (cardStarter.getPropCorn(1) != null) {
                counter.AddResource(cardStarter.getPropCorn(1));
            } else if (cardStarter.getPropCorn(2)  != null) {
                counter.AddResource(cardStarter.getPropCorn(2));
            } else if (cardStarter.getPropCorn(3) != null) {
                counter.AddResource(cardStarter.getPropCorn(3));
            } else if (cardStarter.getPropCorn(4)  != null) {
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

    public void UpdateListData(Corner RemDataCorner, Card AddDataCard){
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
            //aggiungere TOPR alla posizione i+1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.TOPR, NewCard);
            //aggiungere BOTTOML alla posizione i j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) , (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.BOTTOML, NewCard);
            //aggiungere TOPL alla posizione i-1 j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) - 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.TOPL, NewCard);
            UpdateListData(ChoosenCorner,NewCard);
        }
        else if (ChoosenCorner.getPosition() == Position.TOPR){
            //aggiungere TOPR alla posizione  i+1 j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.TOPR, NewCard);
            //aggiungere BOTTOMR alla posizione i j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.BOTTOMR, NewCard);
            //aggiungere TOPL alla poszione TOPL i-1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) - 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.TOPL, NewCard);
            UpdateListData(ChoosenCorner,NewCard);
        }
        else if (ChoosenCorner.getPosition() == Position.BOTTOMR){
            //aggiungere TOPR alla posizione i j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.TOPR, NewCard);
            //aggiungere BOTTOMR i+1 j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.BOTTOMR, NewCard);
            //aggiungere BOTTOML i+1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.BOTTOML, NewCard);
            UpdateListData(ChoosenCorner,NewCard);
        }
        else if(ChoosenCorner.getPosition() == Position.BOTTOML){
            //aggiungere TOPL i j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.TOPL, NewCard);
            //aggiungere BOTTOMR i +1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.BOTTOMR, NewCard);
            //aggiungere BOTTOML i+1 j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerLine(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.BOTTOML, NewCard);
            UpdateListData(ChoosenCorner,NewCard);
        }
    }
}
