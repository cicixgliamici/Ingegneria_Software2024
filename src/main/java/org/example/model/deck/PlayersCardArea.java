package org.example.model.deck;

import org.example.model.deck.enumeration.*;

import java.util.ArrayList;
import java.util.List;

public class PlayersCardArea {
    private MatrixCell[][] PlayedCard;
    private List<CardRes>  PlayerRes;
    private List<PropertiesCorner> PlayerObject;

    public PlayersCardArea() {
        this.PlayerRes = new ArrayList<CardRes>();
        this.PlayerObject = new ArrayList<PropertiesCorner>();
    }

    public void ModifyPlayedCard(int Line, int Column, Position Value, Card LinkCard){
        this.PlayedCard[Line][Column].setAvailabilityCorner(Value);
        this.PlayedCard[Line][Column].setCardCell(LinkCard);
    }

    public void InitializedPlayerArea(int[][] PlayedCard, Card cardStarter){
        ModifyPlayedCard(10,10, Position.TOPL, cardStarter);
        ModifyPlayedCard(10,11, Position.TOPR, cardStarter);
        ModifyPlayedCard(11,10, Position.BOTTOML, cardStarter);
        ModifyPlayedCard(11,11, Position.BOTTOMR, cardStarter);
        if (cardStarter.getSide().getSide() == Side.FRONT){
            //inserimento valore ricavato dagli angoli nella lista
        }

        else if(cardStarter.getSide().getSide() == Side.BACK){
            //inserimento valore ricavato dagli angoli nella lista
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
    public void InsertNewCard(Card TouchedCard, Card NewCard, Corner ChoosenCorner){
        if (ChoosenCorner.getPosition() == Position.TOPL){
            //aggiungere TOPR alla posizione i+1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.TOPR, NewCard);
            //aggiungere BOTTOML alla posizione i j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) , (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.BOTTOML, NewCard);
            //aggiungere TOPL alla posizione i-1 j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) - 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.TOPL, NewCard);
        }
        else if (ChoosenCorner.getPosition() == Position.TOPR){
            //aggiungere TOPR alla posizione  i+1 j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.TOPR, NewCard);
            //aggiungere BOTTOMR alla posizione i j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.BOTTOMR, NewCard);
            //aggiungere TOPL alla poszione TOPL i-1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) - 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.TOPL, NewCard);
        }
        else if (ChoosenCorner.getPosition() == Position.BOTTOMR){
            //aggiungere TOPR alla posizione i j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.TOPR, NewCard);
            //aggiungere BOTTOMR i+1 j+1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) + 1, Position.BOTTOMR, NewCard);
            //aggiungere BOTTOML i+1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.BOTTOML, NewCard);
        }
        else if(ChoosenCorner.getPosition() == Position.BOTTOML){
            //aggiungere TOPL i j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)), (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.TOPL, NewCard);
            //aggiungere BOTTOMR i +1 j
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)), Position.BOTTOMR, NewCard);
            //aggiungere BOTTOML i+1 j-1
            ModifyPlayedCard((SearchCardMatrixToLinkCornerColumn(TouchedCard, ChoosenCorner)) + 1, (SearchCardMatrixToLinkCornerColumn(TouchedCard,ChoosenCorner)) - 1, Position.BOTTOML, NewCard);
        }
    }

}
