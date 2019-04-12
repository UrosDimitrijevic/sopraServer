package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
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


    public boolean runActionByID(long id ){
        Action action = this.actionRepository.findById(id );
        if(action == null){
            return false;
        }
        action.perfromAction(this.gameService);
        return true;
    }

    public void saveAction(Action action){
        this.actionRepository.save(action);
    }
}
