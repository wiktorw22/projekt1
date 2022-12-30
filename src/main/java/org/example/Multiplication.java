package org.example;

import java.util.Random;

public class Multiplication {
    //klasa odpowiedzialna za rozmnazanie zwierzat
    private Animal animal1;
    private Animal animal2;
    private AnimalMap map;
    public Multiplication(Animal animal1, Animal animal2, AnimalMap map){
        this.animal1 = animal1;
        this.animal2 = animal2;
        this.map = map;
    }
    public Animal strongestAnimal(Animal animal1, Animal animal2){
        if(animal1.getAnimalEnergy() >= animal2.getAnimalEnergy()){
            return animal1;
        }
        else{
            return animal2;
        }
    }
    public Animal weakestAnimal(Animal animal1, Animal animal2){
        if(animal1.getAnimalEnergy() < animal2.getAnimalEnergy()){
            return animal1;
        }
        else{
            return animal2;
        }
    }
    //metoda odpowiedzialna za dokonywanie mutacji w genotypie nowej istoty
    public void mutations(Animal newAnimal){
        Gene[] prevGenes = newAnimal.getAnimalGenes();
        Random random = new Random();
        int randomAmountOfGenes = random.nextInt(this.map.getNumberOfGenesPerAnimal()+2);
        Gene[] newGenes = new Gene[this.map.getNumberOfGenesPerAnimal()];
        int[] indexes = new int[this.map.getNumberOfGenesPerAnimal()];
        for(int i=0; i<randomAmountOfGenes; i++){
            Random pomIdx = new Random();
            int idx = pomIdx.nextInt(this.map.getNumberOfGenesPerAnimal());
            indexes[idx] = 1;
        }
        for(int i=0; i<this.map.getNumberOfGenesPerAnimal(); i++){
            if(!(indexes[i] == 1)){
                indexes[i] = 0;
            }
        }
        for(int i=0; i<this.map.getNumberOfGenesPerAnimal(); i++){
            if(indexes[i] == 1){
                Random pomGene = new Random();
                int gene = pomGene.nextInt(8);
                newGenes[i] = new Gene(gene);
            }
            else{
                newGenes[i] = prevGenes[i];
            }
        }
        newAnimal.setAnimalGenes(newGenes);
    }
    public Animal makeNewAnimal(){
        //nalezy wylosowac strone po ktorej w genotypie potomka beda geny od silniejszego rodzica
        Random randomSite = new Random();
        int strongestSite = randomSite.nextInt(2); //moge wybrac 0 - strona lewa, 1 - strona prawa
        int sumEnergy = this.animal1.getAnimalEnergy() + this.animal2.getAnimalEnergy();
        int percentageOfGenesFromStrongestAnimal = (strongestAnimal(this.animal1, this.animal2).getAnimalEnergy()/sumEnergy)*100;
        int amountOfGenesFromStrongestParent = this.map.getNumberOfGenesPerAnimal()*(percentageOfGenesFromStrongestAnimal/100);
        Gene[] descendantsGenes = new Gene[this.map.getNumberOfGenesPerAnimal()];
        Animal animalStrong = strongestAnimal(this.animal1, this.animal2);
        Animal animalWeak = weakestAnimal(this.animal1, this.animal2);
        int j=0;
        if(strongestSite==0){
            for(int i=0; i<this.map.getNumberOfGenesPerAnimal(); i++){
                if(j<amountOfGenesFromStrongestParent){
                    descendantsGenes[i] = new Gene(animalStrong.getAnimalGenes()[i].getGene());
                    j++;
                }
                else{
                    descendantsGenes[i] = new Gene(animalWeak.getAnimalGenes()[i].getGene());
                }
            }
        }
        else{
            for(int i=this.map.getNumberOfGenesPerAnimal()-1; i>=0; i--){
                if(j<amountOfGenesFromStrongestParent){
                    descendantsGenes[i] = new Gene(animalStrong.getAnimalGenes()[i].getGene());
                    j++;
                }
                else{
                    descendantsGenes[i] = new Gene(animalWeak.getAnimalGenes()[i].getGene());
                }
            }
        }
        Animal newAnimal = new Animal(this.map.getEnergyUsedToCreateBabies(), this.map, descendantsGenes);
        animalStrong.setAnimalEnergy(animalStrong.getAnimalEnergy()-this.map.getEnergyUsedToCreateBabies());
        animalWeak.setAnimalEnergy(animalWeak.getAnimalEnergy()-this.map.getEnergyUsedToCreateBabies());
        newAnimal.setAnimalOrientation(animalStrong.getAnimalOrientation());
        newAnimal.setAnimalPosition(animalStrong.getAnimalPosition());

        animalWeak.setAnimalNumberOfDescendants(animalWeak.getAnimalNumberOfDescendants()+1);
        animalStrong.setAnimalNumberOfDescendants(animalStrong.getAnimalNumberOfDescendants()+1);
        mutations(newAnimal);
        return newAnimal;
    }

}
