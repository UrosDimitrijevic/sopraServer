package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class swapPosition extends Action {


    private final int figurineNumber;
    private final int playerNumber;
    private final int oppFigNumber;
    private final int oppPlayerNumber;

    public swapPosition(){

    }

    public swapPosition (Game game, Figurine figurine, Figurine oppFigurine){
        super(game);

        this.name = " Swap Moving Position";
        this.figurineNumber = figurine.getFigurineNumber();
        this.playerNumber = figurine.retrivePlayerNumber();
        this.oppFigNumber=oppFigurine.getFigurineNumber();
        this.oppPlayerNumber= oppFigurine.retrivePlayerNumber();
    }


    boolean isNeighbor(Figurine fig1, Figurine fig2){
        int[] pos1=fig1.getPosition();
        int[] pos2=fig2.getPosition();

        if ((pos2[0]==pos1[0]-1 ||  pos2[0]==pos1[0] ||  pos2[0]== pos1[0]+1) &&
        ( pos2[1] == pos1[1]-1 ||  pos2[1] == pos1[1] ||  pos2[1] == pos1[1]+1 )) {
            return true;
        }else{
            return false;
        }
    }

    @Override
    public void perfromAction(GameService gameService) {
        Game myGame= gameService.gameByID(this.myGameId);
        Figurine figurine1= myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];
        Figurine figurine2= myGame.retrivePlayers()[this.oppPlayerNumber-1].retirveFigurines()[this.oppFigNumber-1];

        if(isNeighbor(figurine1,figurine2)){
            int[] pos=figurine1.getPosition();
            figurine1.changePosition(pos[0],pos[1]);
        }else{
            throw new Exception("The workers are not neighbors!")
        }

        gameService.saveGame(myGame);



    }
}
