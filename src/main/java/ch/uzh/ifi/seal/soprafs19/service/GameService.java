package ch.uzh.ifi.seal.soprafs19.service;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.User;
import ch.uzh.ifi.seal.soprafs19.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class GameService {

    private final Logger log = LoggerFactory.getLogger(GameService.class);

    @Autowired
    private final UserService service;

    @Autowired
    private final GameRepository gameRepository;


    @Autowired
    public GameService(GameRepository gameRepository, UserService service) {
        this.service = service;
        this.gameRepository = gameRepository;
    }

    public Game gameByID(long id ){
        Game game = this.gameRepository.findById(id );
        if(game !=  null) {game.setBoardForFigurines(); }
        return game;
    }

    public Game gameByPlaxerId(long id ){
        Game game = this.gameRepository.findByPlayer1id(id);
        if( game != null){
            game.setBoardForFigurines();
            return game;
        }
        else{
            game = this.gameRepository.findByPlayer2id(id);
            game.setBoardForFigurines();
            return game;
        }
    }

    public Iterable<Game> getGames() {
        return this.gameRepository.findAll();
    }

    public void saveGame(Game newgame){
        this.gameRepository.save(newgame);
    }

    public UserService getUserService() {
        return service;
    }

    public void deleteGame(long gameId){
        Game game = this.gameByID(gameId);
        this.gameRepository.delete(game);
    }
}
