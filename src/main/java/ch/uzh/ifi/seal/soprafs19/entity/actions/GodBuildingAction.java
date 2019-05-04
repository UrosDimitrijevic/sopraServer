package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;

import javax.persistence.Entity;

@Entity
abstract public class GodBuildingAction extends Building {

    public GodBuildingAction(){ super(); }

    public GodBuildingAction(Game game, Figurine figurine, int row, int column){
        super(game, row, column);
    }

}
