package org.example.model.deck;

public class ResourceCard extends Card{
    public int Point;
    ResourceCard(CardRes res,
                 PropertiesCorner ang1,
                 PropertiesCorner ang2,
                 PropertiesCorner ang3,
                 PropertiesCorner ang4,
                 PropertiesCorner ang5,
                 PropertiesCorner ang6,
                 PropertiesCorner ang7,
                 PropertiesCorner ang8,
                 int points){

        new SideCard(Side.FRONT, ang1, ang2, ang3, ang4);
        new SideCard(Side.BACK, ang5, ang6, ang7, ang8);



    }
}
