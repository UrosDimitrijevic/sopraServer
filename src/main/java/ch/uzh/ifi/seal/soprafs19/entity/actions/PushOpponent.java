package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class PushOpponent extends GodMovingAction {



    private final int oppFigNumber;
    private final int oppPlayerNumber;

    public PushOpponent(){
        figurineNumber =0;
        playerNumber = 0;
        oppFigNumber = 0;
        oppPlayerNumber = 0;
    }


    public PushOpponent (Game game, Figurine figurine, Figurine oppFigurine){
        super(game);

        this.name = " Push Opponent Moving ";
        this.figurineNumber = figurine.getFigurineNumber();
        this.playerNumber = figurine.retrivePlayerNumber();
        this.oppFigNumber = oppFigurine.getFigurineNumber();
        this.oppPlayerNumber= oppFigurine.retrivePlayerNumber();
        this.row = oppFigurine.getPosition()[0];
        this.column = oppFigurine.getPosition()[1];
    }

/*    @Override
    public PushOpponent(Game game, Figurine figurine, int row, int column){
        int oponentPlayer = game.getBoard().getSpaces()[row][column].retriveFigurine()[0];
        int oponentFigurine = game.getBoard().getSpaces()[row][column].retriveFigurine()[0];
        Figurine oppFigruine = game.retrivePlayers()[oponentPlayer-1].retirveFigurines()[oponentFigurine-1];
        PushOpponent(game, figurine,oponentFigurine);
    }*/


    @Override
    public void perfromAction(GameService gameService) {
        Game myGame= gameService.gameByID(this.myGameId);
        Figurine figurine1= myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];
        Figurine figurine2= myGame.retrivePlayers()[this.oppPlayerNumber-1].retirveFigurines()[this.oppFigNumber-1];
        int[] pos1=figurine1.getPosition();
        int[] pos2=figurine2.getPosition();
        int[] difference = new int [2];
        difference[0] = pos2[0]-pos1[0];
        difference[1] = pos2[1]-pos1[1];

        //debug printing
        System.out.print("pos1[0]:" + Integer.toString(pos1[0]) + "\n");
        System.out.print("pos1[1]:" + Integer.toString(pos1[1]) + "\n");
        System.out.print("pos2[0]:" + Integer.toString(pos2[0]) + "\n");
        System.out.print("pos2[1]:" + Integer.toString(pos2[1]) + "\n");
        System.out.print("difference[0]:" + Integer.toString(difference[0]) + "\n");
        System.out.print("difference[1]:" + Integer.toString(difference[1]) + "\n");

        //moving the figurines
        figurine2.changePosition(pos2[0]+difference[0], pos2[1]+difference[1]);
        figurine1.changePosition(pos1[0]+difference[0], pos1[1]+difference[1]);

        if(myGame.getStatus().player() == 1){
            myGame.setStatus(GameStatus.BUILDING_STARTINGPLAYER);
        } else {
            myGame.setStatus(GameStatus.BUILDING_NONSTARTINGPLAYER);
        }

        /*if( (pos2[0]==0 || pos2[0]== 4) || (pos2[1]==0 || pos2[1]==4) ){
            if (pos2[0] > pos1[0]) {
                figurine2.changePosition(pos2[0]+1,pos2[1]);
            }else if( pos2[0]<=pos1[0]){
                figurine2.changePosition(pos2[0]-1,pos2[1]);
            }
        }
        else{
            if(pos1[0]<pos2[0]){
                if(pos1[1]<pos2[1]){
                    figurine2.changePosition(pos2[0]+1,pos1[1]+1);
                }else if(pos1[1]==pos2[1]){
                    figurine2.changePosition(pos2[0]+1,pos2[1]);
                }else if(pos1[1]>pos2[1]){
                    figurine2.changePosition(pos2[0]+1,pos2[1]-1);
                }
            }else if (pos1[0]==pos2[0]){
                if(pos1[1]<pos2[1]){
                    figurine2.changePosition(pos2[0],pos2[1]+1);
                }else if(pos1[1]>pos2[1]){
                    figurine2.changePosition(pos2[0],pos2[1]-1);
                }
            }else if(pos1[0]>pos2[0]){
                if(pos1[1]<pos2[1]) {
                    figurine2.changePosition(pos2[0]-1,pos2[1]+1);
                }
                else if (pos1[1]==pos2[1]){
                    figurine2.changePosition(pos2[0]-1,pos2[1]);
                }
                else if (pos1[1]>pos2[1]){
                    figurine2.changePosition(pos2[0]-1,pos2[1]-1);
                }
            }

        }*/


        gameService.saveGame(myGame);

    }

}
