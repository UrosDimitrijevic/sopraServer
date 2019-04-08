package ch.uzh.ifi.seal.soprafs19.entity;

public class Player {
    private User myUser;
    private boolean startingplayer;
    private boolean GodMode;
    private Figurine figurine1;
    private Figurine figurine2;

    Player(User me, Board board, boolean doIstart){
        this.myUser = me;
        this.startingplayer = doIstart;
        this.figurine1 = new Figurine(this,board,1);
        this.figurine2 = new Figurine(this,board,2);
    }

    public boolean isStartingplayer() {
        return startingplayer;
    }
}
