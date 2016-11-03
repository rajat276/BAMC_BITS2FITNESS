package com.bitsaa.bamc.Interface;

/**
 * Created by Animesh on 10/19/2016.
 */
public class Participant_Details {
    String name;
    String score;
    String rank;
    public Participant_Details(String name, String rank, String score){
        this.name=name;
        this.rank=rank;
        this.score=score;


    }
    public String getName(){
        return name;
    }
    public String getRank(){
        return rank;
    }
    public String getScore(){
        return score;
    }

}