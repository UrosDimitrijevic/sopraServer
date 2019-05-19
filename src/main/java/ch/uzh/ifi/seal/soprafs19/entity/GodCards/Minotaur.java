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


    @java.lang.Override
    public ArrayList<Action> getActions(Game game, Figurine figurine, Figurine oppFigurine ) {
            ArrayList<Action> possibleActions = new ArrayList<Action>();
            Player myPlayer = game.retrivePlayers()[game.getStatus().player()-1];

            if(game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER || game.getStatus() == GameStatus.MOVING_STARTINGPLAYER){
                possibleActions.add(new  PushOpponent(game, figurine, oppFigurine));
            }

            game.removeActions(possibleActions,myPlayer.getPlayerNumber());
            return possibleActions;

    }


    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, Figurine oppFigurine) {
        return new PushOpponent(game, figurine, oppFigurine);
    }
}
