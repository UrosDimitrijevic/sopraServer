package ch.uzh.ifi.seal.soprafs19.entity;

import javax.persistence.Column;
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
    private boolean dome;

    @Column(nullable = false, length = 300)
    private int figurine[];

    public Space(){
        level = 0;
        dome = false;
    }


    public boolean isDome() {
        return dome;
    }

    public void build(){
        if(level < 3) {
            this.level += 1;
        }
        else{
            dome = true;
        }
    }

    public void buildDome(){
        dome = true;
    }

    public int getLevel(){

        return this.level;
    }

    public void setFigurine(Figurine figurine) {
        if(figurine != null) {
            this.figurine = new int[2];
            this.figurine[0] = figurine.retrivePlayerNumber();
            this.figurine[1] = figurine.getFigurineNumber();
        } else {
            this.figurine = null;
        }
    }

    public boolean checkIfWalkeable(int yourLevel){
        if(yourLevel < (this.level-1)){ return false; }
        else{ return true && !this.dome && (this.figurine == null); }
    }

    public boolean checkIfBuildiable(){
        return !dome && (this.figurine == null);
    }

    public boolean checkIfEmtpy(){ return (this.figurine == null && !this.isDome()); }

    public int [] retriveFigurine(){
        return this.figurine;
    }
}
