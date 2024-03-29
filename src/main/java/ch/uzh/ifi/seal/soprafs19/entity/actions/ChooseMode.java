package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.GodCard;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ChooseMode extends Moving {


    private boolean useGod;



    @Column
    private int row;

    @Column
    private int column;

    @Override
    public int getRow() {
        return row;
    }

    @Override
    public int getColumn() {
        return column;
    }

    public ChooseMode(){

    }

    public boolean isUseGod() {
        return useGod;
    }

    public ChooseMode(Game game, Figurine figurine, boolean useGod, int row, int column ){
        super(game);
        this.useGod=useGod;
        this.name="Choose Moving Mode";
        this.row=row;
        this.column=column;

        this.figurineNumber = figurine.getFigurineNumber();
        this.playerNumber = figurine.retrivePlayerNumber();

    }


    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game myGame = gameService.gameByID(this.myGameId);
        Player player = myGame.retrivePlayers()[this.playerNumber - 1];
        GodCard god;
        if ( (myGame.getStatus() == GameStatus.MOVING_STARTINGPLAYER || myGame.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER)
                && this.useGod) {
            Figurine figurine = myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];
            god = player.getAssignedGod();
            //Get all possible actions defined in the assigned god
            Action toPerfrom = god.getAction(myGame, figurine, this.row, this.column);
            toPerfrom.perfromAction(gameService);

        }
        else{
            Action normalMoving;
            Figurine figurine = myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];
            normalMoving = new Moving(myGame,figurine,this.row,this.column);
            normalMoving.perfromAction(gameService);

        }
        System.out.println("Hello, World");

        //gameService.saveGame(myGame);

    }


}
