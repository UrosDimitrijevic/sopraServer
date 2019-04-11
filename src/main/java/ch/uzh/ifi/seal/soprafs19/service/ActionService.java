package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.entity.actions.Action;
import ch.uzh.ifi.seal.soprafs19.repository.ActionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ActionService {

    @Autowired
    private final ActionRepository actionRepository;

    @Autowired
    private GameService gameService;


    @Autowired
    public ActionService(ActionRepository actionRepository) {
        this.actionRepository = actionRepository;
    }


    public void runActionByID(long id ){
        Action action = this.actionRepository.findById(id );
        action.run(this.gameService);
    }

    public void saveAction(Action action){
        this.actionRepository.save(action);
    }
}
