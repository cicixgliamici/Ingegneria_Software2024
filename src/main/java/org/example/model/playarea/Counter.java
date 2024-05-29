package org.example.model.playarea;

import org.example.enumeration.CardRes;
import org.example.enumeration.PropertiesCorner;
import org.example.enumeration.cast.CastCardRes;

/**
 * Class to count the number of resources each player has in their area,
 * useful for gold card placement requirements
 */

public class Counter {
    private int animalCounter;
    private int plantCounter;
    private int fungiCounter;
    private int insectCounter;
    private int quillCounter;
    private int inkwellCounter;
    private int manuscriptCounter;
    private int pointCounter;
    private int objectiveCounter;

    public Counter() {
        this.animalCounter = 0;
        this.plantCounter = 0;
        this.fungiCounter = 0;
        this.insectCounter = 0;
        this.quillCounter = 0;
        this.inkwellCounter = 0;
        this.manuscriptCounter = 0;
        this.pointCounter = 0;
        this.objectiveCounter =0;
    }

    /**
     * Increments the counter for each resource on the game area
     */
    public void addResource(PropertiesCorner propertiesCorner){
        if(propertiesCorner==PropertiesCorner.ANIMAL){
            this.animalCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.FUNGI){
            this.fungiCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.PLANT){
            this.plantCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.QUILL){
            this.quillCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.INKWELL){
            this.inkwellCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.INSECT){
            this.insectCounter++;
        }
        else if(propertiesCorner==PropertiesCorner.MANUSCRIPT){
            this.manuscriptCounter++;
        }
    }

    /**
     * Decrements the counter for each resource on the game area
     */
    public void removeResource(PropertiesCorner propertiesCorner){
        if(propertiesCorner==PropertiesCorner.ANIMAL){
            this.animalCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.INSECT){
            this.insectCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.FUNGI){
            this.fungiCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.PLANT){
            this.plantCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.QUILL){
            this.quillCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.INKWELL){
            this.inkwellCounter--;
        }
        else if(propertiesCorner==PropertiesCorner.MANUSCRIPT){
            this.manuscriptCounter--;
        }
    }

    /**
     * Adds points to each player setting a cap of 29 points
     */
    public void addPoint(int point){
        this.pointCounter += point;
        if(this.pointCounter >29){
            this.pointCounter =29;
        }
    }

    /**
     * Returns true if the passed resource is already present on the board
     */
    public boolean isPresent(CardRes cardRes){
        //ritorna true se il properties corner passato come parametro è presente
        CastCardRes cardRes1= new CastCardRes(cardRes);
        PropertiesCorner propertiesCorner= cardRes1.getPropertiesCorner();
        if(propertiesCorner==PropertiesCorner.ANIMAL){
            return this.animalCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.FUNGI){
            return this.fungiCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.PLANT){
            return this.plantCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.QUILL){
            return this.quillCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.INKWELL){
            return this.inkwellCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.INSECT){
            return this.insectCounter > 0;
        }
        else if(propertiesCorner==PropertiesCorner.MANUSCRIPT){
            return this.manuscriptCounter > 0;
        }
        return false;
    }

    //getter and setter
    public int getAnimalCounter() {
        return animalCounter;
    }

    public int getPlantCounter() {
        return plantCounter;
    }

    public int getFungiCounter() {
        return fungiCounter;
    }

    public int getInsectCounter() {
        return insectCounter;
    }

    public int getQuillCounter() {
        return quillCounter;
    }

    public int getInkwellCounter() {
        return inkwellCounter;
    }

    public int getManuscriptCounter() {
        return manuscriptCounter;
    }

    public int getPointCounter() {
        return pointCounter;
    }
    public void addObjectiveCounter(){
        this.objectiveCounter++;
    }

    public int getObjectiveCounter() {
        return objectiveCounter;
    }

    public void setPointCounter(int pointCounter) {
        this.pointCounter = pointCounter;
    }
}