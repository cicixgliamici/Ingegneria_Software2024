package org.example.model.playarea;

import org.example.enumeration.CardRes;
import org.example.enumeration.PropertiesCorner;
import org.example.enumeration.cast.CastCardRes;

/**
 * Class to count the number of resources each player has in their area,
 * useful for gold card placement requirements.
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

    /**
     * Constructs a Counter and initializes all counters to zero.
     */
    public Counter() {
        this.animalCounter = 0;
        this.plantCounter = 0;
        this.fungiCounter = 0;
        this.insectCounter = 0;
        this.quillCounter = 0;
        this.inkwellCounter = 0;
        this.manuscriptCounter = 0;
        this.pointCounter = 0;
        this.objectiveCounter = 0;
    }

    /**
     * Increments the counter for the specified resource.
     *
     * @param propertiesCorner the properties of the corner to add
     */
    public void addResource(PropertiesCorner propertiesCorner) {
        switch (propertiesCorner) {
            case ANIMAL:
                this.animalCounter++;
                break;
            case FUNGI:
                this.fungiCounter++;
                break;
            case PLANT:
                this.plantCounter++;
                break;
            case QUILL:
                this.quillCounter++;
                break;
            case INKWELL:
                this.inkwellCounter++;
                break;
            case INSECT:
                this.insectCounter++;
                break;
            case MANUSCRIPT:
                this.manuscriptCounter++;
                break;
            default:
                break;
        }
    }

    /**
     * Decrements the counter for the specified resource.
     *
     * @param propertiesCorner the properties of the corner to remove
     */
    public void removeResource(PropertiesCorner propertiesCorner) {
        switch (propertiesCorner) {
            case ANIMAL:
                this.animalCounter--;
                break;
            case INSECT:
                this.insectCounter--;
                break;
            case FUNGI:
                this.fungiCounter--;
                break;
            case PLANT:
                this.plantCounter--;
                break;
            case QUILL:
                this.quillCounter--;
                break;
            case INKWELL:
                this.inkwellCounter--;
                break;
            case MANUSCRIPT:
                this.manuscriptCounter--;
                break;
            default:
                break;
        }
    }

    /**
     * Adds points to the player's score, with a maximum cap of 29 points.
     *
     * @param point the number of points to add
     */
    public void addPoint(int point) {
        this.pointCounter += point;
        if (this.pointCounter > 29) {
            this.pointCounter = 29;
        }
    }

    /**
     * Checks if the specified resource is present on the board.
     *
     * @param cardRes the card resource to check
     * @return true if the resource is present, false otherwise
     */
    public boolean isPresent(CardRes cardRes) {
        CastCardRes cardRes1 = new CastCardRes(cardRes);
        PropertiesCorner propertiesCorner = cardRes1.getPropertiesCorner();
        switch (propertiesCorner) {
            case ANIMAL:
                return this.animalCounter > 0;
            case FUNGI:
                return this.fungiCounter > 0;
            case PLANT:
                return this.plantCounter > 0;
            case QUILL:
                return this.quillCounter > 0;
            case INKWELL:
                return this.inkwellCounter > 0;
            case INSECT:
                return this.insectCounter > 0;
            case MANUSCRIPT:
                return this.manuscriptCounter > 0;
            default:
                return false;
        }
    }

    /**
     * Checks if the specified resource is present a certain number of times on the board.
     *
     * @param cardRes the card resource to check
     * @param num the number of times the resource should be present
     * @return true if the resource is present the specified number of times, false otherwise
     */
    public boolean isPresent(CardRes cardRes, int num) {
        CastCardRes cardRes1 = new CastCardRes(cardRes); //ottieni la cardRes che gli stai passando
        PropertiesCorner propertiesCorner = cardRes1.getPropertiesCorner(); //converti la cardRes in un properties corner
        switch (propertiesCorner) {
            case ANIMAL:
                return this.animalCounter >= num;
            case FUNGI:
                return this.fungiCounter >= num;
            case PLANT:
                return this.plantCounter >= num;
            case QUILL:
                return this.quillCounter >= num;
            case INKWELL:
                return this.inkwellCounter >= num;
            case INSECT:
                return this.insectCounter >= num;
            case MANUSCRIPT:
                return this.manuscriptCounter >= num;
            default:
                return false;
        }
    }


    // Getter and setter methods

    /**
     * Getter for the animal counter.
     *
     * @return the animal counter
     */
    public int getAnimalCounter() {
        return animalCounter;
    }

    /**
     * Getter for the plant counter.
     *
     * @return the plant counter
     */
    public int getPlantCounter() {
        return plantCounter;
    }

    /**
     * Getter for the fungi counter.
     *
     * @return the fungi counter
     */
    public int getFungiCounter() {
        return fungiCounter;
    }

    /**
     * Getter for the insect counter.
     *
     * @return the insect counter
     */
    public int getInsectCounter() {
        return insectCounter;
    }

    /**
     * Getter for the quill counter.
     *
     * @return the quill counter
     */
    public int getQuillCounter() {
        return quillCounter;
    }

    /**
     * Getter for the inkwell counter.
     *
     * @return the inkwell counter
     */
    public int getInkwellCounter() {
        return inkwellCounter;
    }

    /**
     * Getter for the manuscript counter.
     *
     * @return the manuscript counter
     */
    public int getManuscriptCounter() {
        return manuscriptCounter;
    }

    /**
     * Getter for the point counter.
     *
     * @return the point counter
     */
    public int getPointCounter() {
        return pointCounter;
    }

    /**
     * Adds to the objective counter.
     */
    public void addObjectiveCounter() {
        this.objectiveCounter++;
    }

    /**
     * Getter for the objective counter.
     *
     * @return the objective counter
     */
    public int getObjectiveCounter() {
        return objectiveCounter;
    }

    /**
     * Setter for the point counter.
     *
     * @param pointCounter the new point counter value
     */
    public void setPointCounter(int pointCounter) {
        this.pointCounter = pointCounter;
    }
}
