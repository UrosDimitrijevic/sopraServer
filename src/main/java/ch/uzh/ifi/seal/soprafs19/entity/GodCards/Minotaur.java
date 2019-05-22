package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Board;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.MovingAsApollo;
import ch.uzh.ifi.seal.soprafs19.entity.actions.PushOpponent;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Minotaur extends GodCard {


    @Id
    @GeneratedValue
    private Long id;




    public Minotaur(){

        super();
    }


    public Minotaur(Game game){
        super(game);
        this.godnumber = 8;
        this.name = "Minotaur";
    }

    static private boolean checkIfPusheable(Game game, Figurine me, Figurine her){
        int difference [] = new int [2];
        difference[0] = her.getPosition()[0] - me.getPosition()[0];
        difference[1] = her.getPosition()[0] - me.getPosition()[0];
        if(difference[0] <-1 || difference[0] > 1 || difference[1] < -1 || difference[1] > 1){ return false; }
        boolean isWalkeable = game.getBoard().getSpaces()[her.getPosition()[0]][her.getPosition()[1]].getLevel() < me.retriveSpace().getLevel()+2;
        return game.getBoard().isEmpty(her.getPosition()[0]+difference[0], her.getPosition()[1]+difference[1]) & isWalkeable;
    }

    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        ArrayList<Action> possibleActions = new ArrayList<Action>();
        Player myPlayer = game.retrivePlayers()[game.getStatus().player()-1];
        Player player2;
        if(game.getStatus().player() == 1){
            player2 = game.retrivePlayers()[1];
        } else {
            player2 = game.retrivePlayers()[0];
        }

        if(game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER || game.getStatus() == GameStatus.MOVING_STARTINGPLAYER) {

            Board board = game.getBoard();
            Figurine fig1 = myPlayer.getFigurine1();
            Figurine fig2 = myPlayer.getFigurine2();
            Figurine oppFig1 = player2.getFigurine1();
            Figurine oppFig2 = player2.getFigurine2();


            if (checkIfPusheable(game, fig1, oppFig1)) {
                possibleActions.add(new PushOpponent(game, fig1, oppFig1));
            }
            if (checkIfPusheable(game, fig2, oppFig1)) {
                possibleActions.add(new PushOpponent(game, fig2, oppFig1));
            }
            if (checkIfPusheable(game, fig1, oppFig2)) {
                possibleActions.add(new PushOpponent(game, fig1, oppFig2));
            }
            if (checkIfPusheable(game, fig2, oppFig2)) {
                possibleActions.add(new PushOpponent(game, fig2, oppFig2));
            }


        game.removeActions(possibleActions,myPlayer.getPlayerNumber());


    }

        return possibleActions;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column ) {
        Player player2 = game.retrivePlayers()[2-game.getStatus().player()];

        Figurine oppFig = null;
        int[]  figPos = player2.getFigurine1().getPosition();
        int[]  fig2Pos = player2.getFigurine2().getPosition();
        if ( row == figPos[0] && column == figPos[1] ) {
           oppFig = player2.getFigurine1();

        }else if( row == fig2Pos[0] && column== fig2Pos[1]  ){
            oppFig = player2.getFigurine2();

        }

        return  new  PushOpponent(game, figurine, oppFig);

    }


}
