package ch.uzh.ifi.seal.soprafs19.entity;

public class Space {

    private int level;
    private boolean doam;

    Space(){
        level = 0;
        doam = false;
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
}
