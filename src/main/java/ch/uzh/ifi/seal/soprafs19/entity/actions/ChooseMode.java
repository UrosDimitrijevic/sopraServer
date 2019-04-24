package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.GodCard;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class ChooseMode extends Action {


    private boolean useGod;
    private int figurineNumber;
    private int playerNumber;
    private int row;
    private int column;


    public ChooseMode(){

    }


    public ChooseMode(Game game, Figurine figurine, boolean useGod, int row, int column ){
        super(game);
        this.useGod=useGod;
        this.name="Choose Mode";
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
        if (myGame.getStatus() == GameStatus.MOVING_STARTINGPLAYER || myGame.getStatus() == GameStatus.MOVING_NONSTARTINGPLAYER
                && this.useGod) {
            god = player.getAssignedGod();
            god.perfromAction(gameService);
        }
        else{
            Action normalMoving;
            Figurine figurine = myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];
            normalMoving = new Moving(myGame,figurine,this.row,this.column);
            normalMoving.perfromAction(gameService);

        }

        gameService.saveGame(myGame);

    }







}
