package org.example.model.PlayArea;

import org.example.enumeration.CardRes;
import org.example.enumeration.PropertiesCorner;
import org.example.enumeration.cast.CastCardRes;

/**
 * Class to count the number of resources each player has in their area,
 * useful for gold card placement requirements
 */

public class Counter {
    private int AnimalCounter;
    private int PlantCounter;
    private int FungiCounter;
    private int InsectCounter;
    private int QuillCounter;
    private int InkwellCounter;
    private int ManuscriptCounter;
    private int PointCounter;

    public Counter() {
        this.AnimalCounter = 0;
        this.PlantCounter = 0;
        this.FungiCounter = 0;
        this.InsectCounter = 0;
        this.QuillCounter = 0;
        this.InkwellCounter = 0;
        this.ManuscriptCounter = 0;
        this.PointCounter = 0;
    }

    public void AddResource(PropertiesCorner propertiesCorner){     //increments counter for each corner resource
        if(propertiesCorner==PropertiesCorner.ANIMAL){
            this.AnimalCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.FUNGI){
            this.FungiCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.PLANT){
            this.PlantCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.QUILL){
            this.QuillCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.INKWELL){
            this.InkwellCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.INSECT){
            this.InsectCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.MANUSCRIPT){
            this.ManuscriptCounter++;
        }
    }
    public void RemoveResource(PropertiesCorner propertiesCorner){      //decrements counter whenever a resource gets covered
        if(propertiesCorner==PropertiesCorner.ANIMAL){
            this.AnimalCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.FUNGI){
            this.FungiCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.PLANT){
            this.PlantCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.QUILL){
            this.QuillCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.INKWELL){
            this.InkwellCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.INSECT){
            this.InsectCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.MANUSCRIPT){
            this.ManuscriptCounter--;
        }
    }
    public void AddPoint(int point ){
        this.PointCounter =+ point;
    }

    public boolean IsPresent(CardRes cardRes){
        //ritorna true se il properties corner passato come parametro è presente
        CastCardRes cardRes1= new CastCardRes(cardRes);
        PropertiesCorner propertiesCorner= cardRes1.getPropertiesCorner();
        if(propertiesCorner==PropertiesCorner.ANIMAL){
            return this.AnimalCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.FUNGI){
            return this.FungiCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.PLANT){
            return this.PlantCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.QUILL){
            return this.QuillCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.INKWELL){
            return this.InkwellCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.INSECT){
            return this.InsectCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.MANUSCRIPT){
            return this.ManuscriptCounter > 0;
        }
        return false;
    }

    //getter and setter
    public int getAnimalCounter() {
        return AnimalCounter;
    }

    public int getPlantCounter() {
        return PlantCounter;
    }

    public int getFungiCounter() {
        return FungiCounter;
    }

    public int getInsectCounter() {
        return InsectCounter;
    }

    public int getQuillCounter() {
        return QuillCounter;
    }

    public int getInkwellCounter() {
        return InkwellCounter;
    }

    public int getManuscriptCounter() {
        return ManuscriptCounter;
    }

    public int getPointCounter() {
        return PointCounter;
    }

}

