package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.constant.GameStatus;
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

        int playerNumber;

        public int getRow() {
            return row;
        }

        public void setRow(int row) {
            this.row = row;
        }

        public int getColumn() {
            return column;
        }

        public void setColumn(int column) {
            this.column = column;
        }

        public int getFigurineNumber() {
            return figurineNumber;
        }

        public void setFigurineNumber(int figurineNumber) {
            this.figurineNumber = figurineNumber;
        }

        public PlaceWorker(){

        }

        public PlaceWorker(Game game, int row, int column){
            super(game);
            this.row = row;
            this.column = column;

            this.name = "PlaceWorker";


        }

        @java.lang.Override
        public void perfromAction(GameService gameService) {
            Game myGame = gameService.gameByID(this.myGameId);
            Player player1 = myGame.getStartingPlayer();
            Player player2 = myGame.getNonStartingPlayer();


            if(myGame.getStatus() == GameStatus.SettingFigurinesp1f1){
                Figurine figurine = player1.retirveFigurines()[0];
                figurine.setPosition(this.row, this.column);
                myGame.setStatus(GameStatus.SettingFigurinesp1f2);
            }

            if(myGame.getStatus() == GameStatus.SettingFigurinesp1f2){
                Figurine figurine = player1.retirveFigurines()[1];
                figurine.setPosition(this.row, this.column);
                myGame.setStatus(GameStatus.SettingFigurinesp2f1);
            }

            if(myGame.getStatus() == GameStatus.SettingFigurinesp2f1) {
                Figurine figurine = player2.retirveFigurines()[0];
                figurine.setPosition(this.row, this.column);
                myGame.setStatus(GameStatus.SettingFigurinesp2f2);
            }

            if(myGame.getStatus() == GameStatus.SettingFigurinesp2f2){
                Figurine figurine = player2.retirveFigurines()[1];
                figurine.setPosition(this.row, this.column);
                myGame.setStatus(GameStatus.MOVING_STARTINGPLAYER);
            }


            gameService.saveGame(myGame);
        }

    }


