package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Moving;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Pan extends GodCard {


    @Id
    @GeneratedValue
    private Long id;

    private int hight1;
    private int hight2;

    public Pan(){
        super();
        this.hight1 = 0;
        this.hight2 = 0;
    }


    public Pan(Game game){
        super(game);
        this.godnumber = 9;
        this.name = "Pan";
        this.hight1 = 0;
        this.hight2 = 0;
    }



    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        return null;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return null;
    }

    @java.lang.Override
    public boolean didWin(Game game, Player player){
        Figurine fig1 = player.getFigurine1();
        Figurine fig2 = player.getFigurine2();
        if( fig1 != null &&  fig2 != null){
            if(fig1.retriveSpace() != null && fig2.retriveSpace() != null){
                if(((this.hight1-fig1.retriveSpace().getLevel()) >=2) || ((this.hight2-fig2.retriveSpace().getLevel()) >= 2) ){
                    return true;
                }
                else{
                    this.hight1 = fig1.retriveSpace().getLevel();
                    this.hight2 = fig2.retriveSpace().getLevel();
                }
            }
        }
        return false;
    }
}
