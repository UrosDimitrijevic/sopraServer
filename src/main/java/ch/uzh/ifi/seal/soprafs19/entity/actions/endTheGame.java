package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.UserStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;

@Entity
public class endTheGame extends Action{

    int playerNumber;

    public endTheGame(){
        super();
    }

    public endTheGame( Game game, int player){
        super(game);
        this.name = "endTheGame";
        this.playerNumber = player;
        this.deleteAfter = false;
    }

    @java.lang.Override
    public void perfromAction(GameService gameService) {
        Game game = gameService.gameByID(this.myGameId);
        User user [] = new User[2];
        user[0] = gameService.getUserService().userByID(game.getPlayer1id());
        user[1] = gameService.getUserService().userByID(game.getPlayer2id());
        int pl = this.playerNumber-1;

        //addapting my player
        user[pl].setStatus(UserStatus.ONLINE);
        user[pl].setChallenging(null);
        user[pl].setGettingChallengedBy(null);
        gameService.getUserService().updateProfile(user[pl],user[pl].getId(),gameService);

        //checking if oponent has also ended game
        if( user[1-pl].getChallenging() != user[pl].getId() ){
            gameService.deleteGame( this.myGameId );
        }
    }
}
