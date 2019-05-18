package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

public class PushOpponent extends Action {




    private final int figurineNumber;
    private final int playerNumber;
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

        this.name = " Swap Moving Position";
        this.figurineNumber = figurine.getFigurineNumber();
        this.playerNumber = figurine.retrivePlayerNumber();
        this.oppFigNumber= oppFigurine.getFigurineNumber();
        this.oppPlayerNumber= oppFigurine.retrivePlayerNumber();

    }






    @Override
    public void perfromAction(GameService gameService) {
        Game myGame= gameService.gameByID(this.myGameId);
        Figurine figurine1= myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[this.figurineNumber-1];
        Figurine figurine2= myGame.retrivePlayers()[this.oppPlayerNumber-1].retirveFigurines()[this.oppFigNumber-1];
        int[] pos1=figurine1.getPosition();
        int[] pos2=figurine2.getPosition();

        if( (pos2[0]==0 || pos2[0]== 4) || (pos2[1]==0 || pos2[1]==4) ){
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
            
        }


        gameService.saveGame(myGame);

    }

}
