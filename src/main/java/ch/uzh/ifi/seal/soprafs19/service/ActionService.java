package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import ch.uzh.ifi.seal.soprafs19.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActionService {

    private final ActionRepository actionRepository;

    private GameService gameService;

    @Autowired
    public ActionService(ActionRepository actionRepository, GameService gameService) {
        this.actionRepository = actionRepository;
        this.gameService = gameService;
    }

    public Action getActionById(long id){
        Action action = this.actionRepository.findById(id);
        if(action == null){
            return null;
        } else {
            return action;
        }
    }



    public boolean runActionByID(long id ){
        Action action = this.actionRepository.findById(id );
        if(action == null){
            return false;
        }
        action.perfromAction(this.gameService);
        long gameId = action.TheGameId();
        Game game = this.gameService.gameByID(gameId);
        if( game == null){
            return true;
        }
        game.addAction(action);
        if(!action.needToDelete()){
            return true;
        }
        if( game.retriveActions1() != null) {
            for (long actionId : game.retriveActions1()) {
                this.actionRepository.deleteById(actionId);
            }
        }
        if( game.retriveActions2() != null) {
            for (long actionId : game.retriveActions2()) {
                this.actionRepository.deleteById(actionId);
            }
        }
        game.setActions1(null);
        game.setActions2(null);
        game.checkIfGameOver();
        gameService.saveGame(game);
        return true;
    }

    public void saveAction(Action action){
        this.actionRepository.save(action);
    }

    public Iterable<Action> getActions() {
        return this.actionRepository.findAll();
    }
}
