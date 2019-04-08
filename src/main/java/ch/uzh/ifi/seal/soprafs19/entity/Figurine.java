package ch.uzh.ifi.seal.soprafs19.entity;

public class Figurine {
    private Board board;
    private Player player;
    int playerNumber;
    int figurineNumber;

    Figurine(Player player, Board board, int figurineNumber){
        this.board = board;
        this.player = player;
        this.figurineNumber = figurineNumber;
        this.playerNumber = (player.isStartingplayer())?1:2;
        board.setFigurine(this,playerNumber,figurineNumber);
    }
}
