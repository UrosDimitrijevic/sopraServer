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

    public boolean getStatus(){
        return this.pickGod1;
    }

    @Column
    private boolean pickGod1;


    @Column(nullable = true, length = 800)
    private GodCard myGod;

    public ChoseYourGod(){}

    public ChoseYourGod(Game game,boolean pickGod1){
        super(game);
        this.name = "ChoseYourGod";
        this.pickGod1=pickGod1;
        if(pickGod1){
            this.myGod = game.retrivePlayers()[0].getAssignedGod();
        }
        else{
            this.myGod = game.retrivePlayers()[1].getAssignedGod();
        }
    }

    @java.lang.Override
    public void perfromAction(GameService gameservice){
        Game game = gameservice.gameByID(this.myGameId);
        GodCard otherGod;
        if(game.retrivePlayers()[1].getAssignedGod().getGodnumber() != this.myGod.getGodnumber() ){
            otherGod = game.retrivePlayers()[1].getAssignedGod();
            game.retrivePlayers()[1].setAssignedGod(this.myGod);
            game.retrivePlayers()[0].setAssignedGod(otherGod);
        }
        game.setStatus(GameStatus.SettingFigurinesp1f1);
        gameservice.saveGame(game);
    }
}
