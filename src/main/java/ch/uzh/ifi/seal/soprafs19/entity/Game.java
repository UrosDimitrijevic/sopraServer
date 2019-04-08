package ch.uzh.ifi.seal.soprafs19.entity;
import java.util.Random;

public class Game {
    private int status;
    private boolean playWithGodCards;
    private Board board;
    private Player players[];
    private Player p1;
    private Player p2;

    private boolean DoesP1Start(User u1, User u2){
        Random rand = new Random();
        if( u1.getBirthday() == null && u2.getBirthday() == null){
            return (rand.nextInt(2) == 1)?true:false;
        }
        if( u1.getBirthday() == null){
            return false;
        }
        if( u2.getBirthday() == null){
            return true;
        }
        if( u1.getBirthday().compareTo( u2.getBirthday() ) > 0){
            return true;
        }
        if (u1.getBirthday().compareTo( u2.getBirthday() ) < 0){
            return false;
        }
        return (rand.nextInt(2) == 1)?true:false;
    }

    Game(User user1, User user2){
        this.board = new Board();
        this.players = new Player[2];
        boolean doesP1start = this.DoesP1Start(user1,user2);

        this.p1 = players[0] = new Player(user1,this.board, doesP1start );
        this.p2 = players[0] = new Player(user2,this.board, !doesP1start );
        players[0] = p1;
        players[1] = p2;
    }
}
