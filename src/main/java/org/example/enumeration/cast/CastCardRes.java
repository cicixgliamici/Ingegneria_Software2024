package org.example.enumeration.cast;

import org.example.enumeration.CardRes;
import org.example.enumeration.PropertiesCorner;

/**
 * The CastCardRes class maps a CardRes enumeration to its corresponding PropertiesCorner enumeration.
 * This class takes a CardRes value as input and assigns the corresponding PropertiesCorner value based on predefined mappings.
 */
public class CastCardRes {
    private CardRes cardRes;
    private PropertiesCorner propertiesCorner;

    /**
     * Constructs a CastCardRes instance with the specified CardRes value.
     * Maps the CardRes value to its corresponding PropertiesCorner value.
     * 
     * @param cardRes the CardRes value to be mapped
     */
    public CastCardRes(CardRes cardRes) {
        this.cardRes = cardRes;
        if (this.cardRes == CardRes.PLANT) {
            this.propertiesCorner = PropertiesCorner.PLANT;
        } else if (this.cardRes == CardRes.ANIMAL) {
            this.propertiesCorner = PropertiesCorner.ANIMAL;
        } else if (this.cardRes == CardRes.FUNGI) {
            this.propertiesCorner = PropertiesCorner.FUNGI;
        } else {
            this.propertiesCorner = PropertiesCorner.INSECT;
        }
    }

    /**
     * Returns the PropertiesCorner value corresponding to the CardRes value.
     * 
     * @return the PropertiesCorner value
     */
    public PropertiesCorner getPropertiesCorner() {
        return propertiesCorner;
    }
}
