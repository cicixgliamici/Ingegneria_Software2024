package org.example.model.deck.enumeration;
public class CounterResources {
    private int AnimalCounter;
    private int PlantCounter;
    private int FungiCounter;
    private int InsectCounter;
    private int QuillCounter;
    private int InkwellCounter;
    private int ManuscriptCounter;

    public CounterResources() {
        this.AnimalCounter = 0;
        this.PlantCounter = 0;
        this.FungiCounter = 0;
        this.InsectCounter = 0;
        this.QuillCounter = 0;
        this.InkwellCounter = 0;
        this.ManuscriptCounter = 0;
    }

    public void AddResource(PropertiesCorner propertiesCorner){
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
    public void RemoveResource(PropertiesCorner propertiesCorner){
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
}

