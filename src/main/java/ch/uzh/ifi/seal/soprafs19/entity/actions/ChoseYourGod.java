package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.GodCard;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class ChoseYourGod extends Action  {
    public GodCard getMyGod() {
        return myGod;
    }

    @Column(nullable = true, length = 800)
    private GodCard myGod;

    public ChoseYourGod(){}

    public ChoseYourGod(Game game,boolean pickGod1){
        super(game);
        this.name = "ChoseYourGod";
        if(pickGod1){
            this.myGod = game.getPlayers()[0].getAssignedGod();
        }
        else{
            this.myGod = game.getPlayers()[1].getAssignedGod();
        }
    }

    @java.lang.Override
    public void perfromAction(GameService gameservice){
        Game game = gameservice.gameByID(this.myGameId);
        boolean P1startingplayer = game.getPlayers()[0].isStartingplayer();
        GodCard otherGod;
        if( P1startingplayer){
            if(game.getPlayers()[0].getAssignedGod().getGodnumber() != this.myGod.getGodnumber() ){
                otherGod = game.getPlayers()[0].getAssignedGod();
                game.getPlayers()[0].setAssignedGod(this.myGod);
                game.getPlayers()[1].setAssignedGod(otherGod);
            }
        }
        else{
            if(game.getPlayers()[1].getAssignedGod().getGodnumber() != this.myGod.getGodnumber() ){
                otherGod = game.getPlayers()[1].getAssignedGod();
                game.getPlayers()[1].setAssignedGod(this.myGod);
                game.getPlayers()[0].setAssignedGod(otherGod);
            }
        }
        game.setStatus(GameStatus.SettingFigurinesp1f1);
        gameservice.saveGame(game);
    }
}
