package ch.uzh.ifi.seal.soprafs19.controller;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.*;
import ch.uzh.ifi.seal.soprafs19.entity.Space;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.service.ActionService;
import ch.uzh.ifi.seal.soprafs19.service.GameService;
import ch.uzh.ifi.seal.soprafs19.service.UserService;
import org.apache.coyote.Response;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.ArrayList;

//Uros was here, server works

@RestController
public class GameController {

    private final UserService service;

    private final GameService gameService;

    private final ActionService actionService;

    GameController(UserService service, GameService gameService, ActionService actionService) {
        this.service = service;
        this.gameService = gameService;
        this.actionService = actionService;
    }

    @GetMapping("/game/actions/{id}")
    ResponseEntity getActions(@PathVariable long id) {
        Iterable<Action> performableActions;
        Game myGame = gameService.gameByPlaxerId(id);
        if( myGame == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player is not in a game");
        }
        performableActions = myGame.getPossibleActions(id);

        //checking if actions not created yes:
        if( (id == myGame.getPlayer1id() && myGame.retriveActions1() == null) || (id == myGame.getPlayer2id() && myGame.retriveActions2() == null) ) {
            //storing the actions in repo and in Game
            ArrayList<Long> theactions;
            if (id == myGame.getPlayer1id()) {
                myGame.setActions1(new ArrayList<Long>());
                theactions = myGame.retriveActions1();
            } else {
                myGame.setActions2(new ArrayList<Long>());
                theactions = myGame.retriveActions2();
            }
            for (Action action : performableActions) {
                this.actionService.saveAction(action);
                theactions.add(action.getId());
            }
            gameService.saveGame(myGame);
            return ResponseEntity.status(HttpStatus.OK).body(performableActions);
        }
        //returning the already existing actions
        else{
            ArrayList<Long> theactions;
            if(id == myGame.getPlayer1id()){
                theactions = myGame.retriveActions1();
            } else {
                theactions = myGame.retriveActions2();
            }
            ArrayList<Action> actionsArray = new ArrayList<Action>();
            for(Long l:theactions){
                Action nextAction = actionService.getActionById(l);
                actionsArray.add(nextAction);
            }
            performableActions = actionsArray;
            return ResponseEntity.status(HttpStatus.OK).body(performableActions);
        }
    }


    @GetMapping("/game/Board/{id}")
    ResponseEntity getBoardStatus(@PathVariable Long id) {
        Game game = this.gameService.gameByPlaxerId(id);
        if(game == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Player doesn't have a game");
        }
        else{
            return ResponseEntity.status(HttpStatus.OK).body(game);
        }
    }

    ResponseEntity performAction(Long actionId) {
        if( this.actionService.runActionByID(actionId) ){
            return ResponseEntity.status(HttpStatus.OK).body("action was performed");
        }
        else if( this.gameService.gameByPlaxerId(actionId) != null){
            Game game = gameService.gameByPlaxerId(actionId);
            if(game.getPlayer1id() == actionId){
                game.setStatus(GameStatus.NONSTARTINGPLAYER_WON);
            } else {
                game.setStatus(GameStatus.STARTINGPLAYER_WON);
            }
            actionService.deleteActions(game);
            gameService.saveGame(game);
            return ResponseEntity.status(HttpStatus.OK).body("action was performed");
        }
        else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Request Not implemented");
        }
    }

    @PutMapping("/game/actions/{actionId}")
    ResponseEntity performActionWithoutBody(@PathVariable Long actionId) {
        return performAction(actionId);
    }

    @PutMapping("/game/{gameId}/actions")
    ResponseEntity performActionWithBody(@PathVariable Long gameId, @RequestBody Long actionId) {
        if( gameService.gameByPlaxerId(actionId) != null && gameService.gameByPlaxerId(actionId).getGameId() == gameId){
            return performAction(actionId);
        }
        Action action = actionService.getActionById(actionId);
        if( action != null && action.retriveGameId().equals(gameId)){
            return performAction(actionId);
        }
        else{
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("action-id and game-id do not match");
        }
    }

    @PutMapping("/game/justForTesting/{id}")
    ResponseEntity justForTesting(@PathVariable Long id, @RequestBody Long secondId) {

        User user1 = this.service.userByID(id);
        User user2 = this.service.userByID(secondId);
        if(user1 == null || user2 == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("one of the users not found");
        }
        //Game game = new Game(user1,user2);
        Game game = gameService.gameByPlaxerId(id);

        //setting up settings
        game.setPlayWithGodCards(true);
        game.setStatus(GameStatus.MOVING_NONSTARTINGPLAYER);
        game.getStartingPlayer().setAssignedGod(new Athena(game));
        game.getNonStartingPlayer().setAssignedGod(new Minotaur(game));

        //setting up buildings
        game.getBoard().getSpaces()[1][0] = new Space(); //
        game.getBoard().getSpaces()[2][0] = new Space(); //
        game.getBoard().getSpaces()[3][0] = new Space(); //
        game.getBoard().getSpaces()[1][2] = new Space(); //
        game.getBoard().getSpaces()[2][2] = new Space(); //
        game.getBoard().getSpaces()[3][2] = new Space(); //
        game.getBoard().getSpaces()[3][1] = new Space(); //
        game.getBoard().getSpaces()[0][1] = new Space(); //
        for(int i = 0; i < 4; ++i){
            game.getBoard().getSpaces()[1][0].build();
            game.getBoard().getSpaces()[2][2].build();
        }
        for(int i = 0; i < 3; ++i){ game.getBoard().getSpaces()[3][0].build(); }
        for( int i = 0; i < 2; ++i){
            game.getBoard().getSpaces()[1][2].build();
        }
        game.getBoard().getSpaces()[2][0].build();
        game.getBoard().getSpaces()[3][2].build();
        game.getBoard().getSpaces()[3][1].build();
        game.getBoard().getSpaces()[3][1].build();
        game.getBoard().getSpaces()[0][1].build();

        //setting figurines
        game.retrivePlayers()[0].getFigurine1().setPosition(2,1);
        game.retrivePlayers()[0].getFigurine2().setPosition(3,4);
        game.retrivePlayers()[1].getFigurine1().setPosition(0,0);
        game.retrivePlayers()[1].getFigurine2().setPosition(3,1);
        game.checkIfGameOver();

        //making all action-arrays null
        game.setActions2(null);
        game.setActions1(null);

        gameService.saveGame(game);
        return ResponseEntity.status(HttpStatus.OK).body("you cheater" );
    }


}
