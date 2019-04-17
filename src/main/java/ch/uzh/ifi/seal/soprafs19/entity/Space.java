package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.io.Serializable;

@Entity
public class Space implements Serializable{

    @Id
    @GeneratedValue
    private Long id;

    private int level;
    private boolean doam;
    private Figurine figurine;

    public Space(){
        level = 0;
        doam = false;
    }


    public boolean isDoam() {
        return doam;
    }

    public void build(){
        if(level <= 3) {
            this.level += 1;
        }
        else{
            doam = true;
        }
    }

    public void buildDome(){
        doam = true;
    }

    public int getLevel(){
        return this.level;
    }

    public void setFigurine(Figurine figurine) {
        this.figurine = figurine;
    }

    public boolean isWalkeable(int yourLevel){
        if(yourLevel < this.level-1){ return false; }
        else{ return true && this.doam; }
    }
}
