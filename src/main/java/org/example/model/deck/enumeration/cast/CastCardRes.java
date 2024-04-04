package org.example.model.deck.enumeration.cast;

import org.example.model.deck.enumeration.CardRes;
import org.example.model.deck.enumeration.PropertiesCorner;

public class CastCardRes {
    CardRes cardRes;
    PropertiesCorner propertiesCorner;

    public CastCardRes (CardRes cardRes){
        this.cardRes=cardRes;
        if( this.cardRes == CardRes.PLANT){
             this.propertiesCorner=PropertiesCorner.PLANT;
        }
        else if(this.cardRes == CardRes.ANIMAL){
            this.propertiesCorner=PropertiesCorner.ANIMAL;
        }
        else if(this.cardRes == CardRes.FUNGI){
             this.propertiesCorner=PropertiesCorner.FUNGI;
        }
        else {
            this.propertiesCorner= PropertiesCorner.INSECT;
        }
    }

    public PropertiesCorner getPropertiesCorner() {
        return propertiesCorner;
    }
}

