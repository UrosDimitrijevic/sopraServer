package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class ChoseGameModeAction extends Action {

    private boolean withGodCards;

    public ChoseGameModeAction() {
    }

    public ChoseGameModeAction (Game game, boolean withGodCards){
        super();
        this.withGodCards = withGodCards;
        this.name = "ChoseMode";
        this.myGameId = game.getId();
    }



    public void setWithGodCards(boolean withGodCards) {
        this.withGodCards = withGodCards;
    }

    public boolean isWithGodCards() {
        return withGodCards;
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {

        System.out.println("\n\n\n\n\n\nI chose my godMode here\n\n\n\n\n\n");
        Game myGame = gameService.gameByID(this.myGameId);
        myGame.setPlayWithGodCards(this.withGodCards);
        gameService.saveGame(myGame);
    }


}
