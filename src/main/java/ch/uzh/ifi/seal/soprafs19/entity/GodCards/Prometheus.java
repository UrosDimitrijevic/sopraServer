package ch.uzh.ifi.seal.soprafs19.entity.GodCards;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.ArrayList;


@Entity
public class Prometheus extends GodCard {


    @Id
    @GeneratedValue
    private Long id;




    public Prometheus(){

        super();
    }


    public Prometheus(Game game){
        super(game);
        this.godnumber = 10;
        this.name = "Prometheus";
    }


    @java.lang.Override
    public ArrayList<Action> getActions(Game game) {
        return null;
    }

    @java.lang.Override
    public Action getAction(Game game, Figurine figurine, int row, int column) {
        return null;
    }
}
