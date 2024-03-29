package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
import ch.uzh.ifi.seal.soprafs19.constant.PlayerStatus;
import ch.uzh.ifi.seal.soprafs19.entity.Figurine;
import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.Player;
import ch.uzh.ifi.seal.soprafs19.service.GameService;

import javax.persistence.Entity;



@Entity
public class PlaceWorker extends Action {
        int row;
        int column;
        int figurineNumber;
        Player player;


        int playerNumber;

        public int getRow() {
            return row;
        }

        public int getColumn() {
            return column;
        }

        public int getFigurineNumber() {
            return figurineNumber;
        }

        public PlaceWorker(){

        }

        public PlaceWorker(Game game, Figurine figurine, int row, int column){
            super(game);
            this.row = row;
            this.column = column;
            this.name = "PlaceWorker";


            this.figurineNumber = figurine.getFigurineNumber();
            this.playerNumber = figurine.retrivePlayerNumber();

        }


        @java.lang.Override
        public void perfromAction(GameService gameService) {
            Game myGame = gameService.gameByID(this.myGameId);
            Figurine figurine = myGame.retrivePlayers()[this.playerNumber-1].retirveFigurines()[0];
            figurine.setPosition(this.row, this.column);

            if(myGame.getStatus() == GameStatus.SettingFigurinesp1f1){
                myGame.setStatus(GameStatus.SettingFigurinesp1f2);
            }
            if(myGame.getStatus() == GameStatus.SettingFigurinesp2f1) {
                myGame.setStatus(GameStatus.SettingFigurinesp2f2);
            }

            gameService.saveGame(myGame);

        }

    }


