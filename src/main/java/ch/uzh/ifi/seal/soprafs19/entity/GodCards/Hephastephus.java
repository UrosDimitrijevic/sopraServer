package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Board;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Building;
import ch.uzh.ifi.seal.soprafs19.entity.actions.DoubleBlockBuild;
import ch.uzh.ifi.seal.soprafs19.entity.actions.MovingAsApollo;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Hephastephus extends GodCard {


    @Id
    @GeneratedValue
    private Long id;


    public Hephastephus(){
        super();
    }


    public Hephastephus(Game game){
        super(game);
        this.godnumber = 6;
        this.name = "Hephastephus";
    }


    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {


        ArrayList<Action> possibleActions = new ArrayList<Action>();
        Player myPlayer = game.retrivePlayers()[game.getStatus().player()-1];

        if(game.getStatus() == GameStatus.BUILDING_STARTINGPLAYER || game.getStatus() == GameStatus.BUILDING_NONSTARTINGPLAYER){

            Board board = game.getBoard();
            ArrayList<Action> simpleActions = myPlayer.retirveFigurines()[game.retriveBuildingFigurine()-1].getPossibleBuildingActions(game);
            for(int i = 0; i < simpleActions.size(); ++i){
                Building action = (Building)simpleActions.get(i);
                if(game.getBoard().getSpaces()[action.getRow()][action.getColumn()].getLevel() < 2){
                    possibleActions.add(new DoubleBlockBuild(game, action.getRow(), action.getColumn()));
                }
            }

            //Libis code
            /*int row1 = myPlayer.getFigurine1().getPosition()[0];
            int column1 = myPlayer.getFigurine1().getPosition()[1];
            int row2 = myPlayer.getFigurine2().getPosition()[0];
            int column2 = myPlayer.getFigurine2().getPosition()[1];


            //figurine 1
            int rowTotest; int columnToTest;
            rowTotest = row1-1; columnToTest =column1-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(new DoubleBlockBuild(game, rowTotest, columnToTest)); }

            rowTotest = row1-1; columnToTest =column1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add( new DoubleBlockBuild(game, rowTotest, columnToTest)); }

            rowTotest = row1-1; columnToTest =column1+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add( new DoubleBlockBuild(game, rowTotest, columnToTest)  ); }

            rowTotest = row1; columnToTest =column1-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)         ); }

            rowTotest = row1; columnToTest =column1+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)        ); }

            rowTotest = row1+1; columnToTest =column1-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add( new DoubleBlockBuild(game, rowTotest, columnToTest)              ); }

            rowTotest = row1+1; columnToTest =column1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)               ); }

            rowTotest = row1+1; columnToTest =column1+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)       ); }

            //figurine 2
            rowTotest = row2-1; columnToTest =column2-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)        ); }

            rowTotest = row2-1; columnToTest =column2;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)           ); }

            rowTotest = row2-1; columnToTest =column2+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)     ); }

            rowTotest = row2; columnToTest =column2-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)          ); }

            rowTotest = row2; columnToTest =column2+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(   new DoubleBlockBuild(game, rowTotest, columnToTest)              ); }

            rowTotest = row2+1; columnToTest =column2-1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(   new DoubleBlockBuild(game, rowTotest, columnToTest)               ); }

            rowTotest = row2+1; columnToTest =column2;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)            ); }

            rowTotest = row2+1; columnToTest =column2+1;
            if( (rowTotest>=0 && rowTotest < 5 && columnToTest>=0 && columnToTest<5) && !board.isEmpty(rowTotest,columnToTest ) && board.getSpaces()[rowTotest][columnToTest].retriveFigurine()[0] != myPlayer.getPlayerNumber()  )
            { possibleActions.add(  new DoubleBlockBuild(game, rowTotest, columnToTest)        ); }*/

        }
        game.removeActions(possibleActions,myPlayer.getPlayerNumber());
        return possibleActions;


    }




    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return new DoubleBlockBuild(game, row, column);
    }
}
