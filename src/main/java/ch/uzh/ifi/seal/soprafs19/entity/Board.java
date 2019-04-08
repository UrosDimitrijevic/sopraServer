package ch.uzh.ifi.seal.soprafs19.entity;

public class Board {
    private Space [][] spaces;
    private Figurine figurines[][]; //p1f1 = [0][0], p1f2 =[0][1], ...
    private Figurine p1f1;
    private Figurine p1f2;
    private Figurine p2f1;
    private Figurine p2f2;

    Board(){
        this.spaces = new Space[5][5];
        this.figurines = new Figurine[2][2];
        updateFigs();
    }

    private void updateFigs(){
        this.p1f1 = this.figurines[0][0];
        this.p1f2 = this.figurines[0][1];
        this.p2f1 = this.figurines[1][0];
        this.p2f2 = this.figurines[1][1];
    }

    public void setFigurine(Figurine f, int player, int figNumber){
        this.figurines[player-1][figNumber-1] = f;
        updateFigs();
    }
}
