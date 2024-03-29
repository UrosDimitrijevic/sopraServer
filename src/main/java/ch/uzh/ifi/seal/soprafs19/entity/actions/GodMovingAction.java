package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
abstract public class GodMovingAction extends Moving {

        public GodMovingAction(Game game){ super(game); }

        public GodMovingAction(){ super(); }

        public GodMovingAction(Game game, Figurine figurine, int row, int column){
            super(game, figurine, row, column);
        }

    public boolean isUseGod() {
        return true;
    }

}