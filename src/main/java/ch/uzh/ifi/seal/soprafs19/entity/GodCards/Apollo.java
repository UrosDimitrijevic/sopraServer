package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.qos.logback.core.util.COWArrayList;
import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Board;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.entity.actions.MovingAsApollo;
import ch.uzh.ifi.seal.soprafs19.entity.actions.MovingAsArthemis;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Apollo extends GodCard {


    @Id
    @GeneratedValue
    private Long id;



    public Apollo(){
        super();
    }

    public Apollo(Game game){
        super(game);
        this.godnumber = 1;
        this.name = "Apollo";
    }


    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        ArrayList<Action> possibleActions = new ArrayList<Action>();

        if(game.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER || game.getStatus() == GameStatus.MOVING_STARTINGPLAYER){

            Player myPlayer = game.retrivePlayers()[game.getStatus().player()-1];
            Board board = game.getBoard();
            int row1 = myPlayer.getFigurine1().getPosition()[0];
            int column1 = myPlayer.getFigurine1().getPosition()[1];
            int row2 = myPlayer.getFigurine2().getPosition()[0];
            int column2 = myPlayer.getFigurine2().getPosition()[1];


            //figurine 1
            int rowTotest; int columnToTest;
            rowTotest = row1-1; columnToTest =column1-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            rowTotest = row1-1; columnToTest =column1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            rowTotest = row1-1; columnToTest =column1+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            rowTotest = row1; columnToTest =column1-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            rowTotest = row1; columnToTest =column1+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            rowTotest = row1+1; columnToTest =column1-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            rowTotest = row1+1; columnToTest =column1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            rowTotest = row1+1; columnToTest =column1+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine1(), rowTotest, columnToTest)); }

            //figurine 2
            rowTotest = row2-1; columnToTest =column2-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

            rowTotest = row2-1; columnToTest =column2;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

            rowTotest = row2-1; columnToTest =column2+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

            rowTotest = row2; columnToTest =column2-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

            rowTotest = row2; columnToTest =column2+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

            rowTotest = row2+1; columnToTest =column2-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

            rowTotest = row2+1; columnToTest =column2;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

            rowTotest = row2+1; columnToTest =column2+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  ){ possibleActions.add(new MovingAsApollo(game,myPlayer.getFigurine2(), rowTotest, columnToTest)); }

        }

        return possibleActions;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return new MovingAsApollo(game,figurine, row, column);
    }
}
