package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.*;
import ch.uzh.ifi.seal.soprafs19.entity.Player;

import java.util.ArrayList;

public class ActionCreater {

    public static ArrayList<Action> createChoseGodActions(Game game){
        ArrayList<Action> possibleActions = new ArrayList<>();

        //creating all gods
        GodCard allGods[] = new GodCard[10];
        allGods[0] = new Apollo(game);
        allGods[1] = new Artemis(game);
        allGods[2] = new Athena(game);
        allGods[3] = new Atlas(game);
        allGods[4] = new Demeter(game);
        allGods[5] = new Hephastephus(game);
        allGods[6] = new Hermes(game);
        allGods[7] = new Minotaur(game);
        allGods[8] = new Pan(game);
        allGods[9] = new Prometheus(game);

        //create the actions
        for(int i = 0; i < 10; ++i){
            for(int j = i+1;j<10;++j){
                possibleActions.add(new ChooseGod(game, allGods[i],allGods[j]));
            }
        }

        return possibleActions;
    }

    public static ArrayList<Action> createPickGodActions(Game game){
        ArrayList<Action> possibleActions = new ArrayList<>();
        possibleActions.add(new ChoseYourGod(game,false));
        possibleActions.add(new ChoseYourGod(game,true));

        return possibleActions;
    }

    public static ArrayList<Action> createMovementActions(Game game, Player player){
        ArrayList<Action> possibleActions = new ArrayList<>();
        possibleActions.addAll(player.getFigurine1().getPossibleMovingActions(game));
        possibleActions.addAll(player.getFigurine2().getPossibleMovingActions(game));

        return possibleActions;
    }

    public static ArrayList<Action> createChooseModeActions(Game game, Player player){
        ArrayList<Action> possibleActions =new ArrayList<>();
        for( int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if( game.getBoard().isEmpty(i,j) ) {
                    possibleActions.add(new ChooseMode(game, player.getFigurine1(),true, i, j));
                    possibleActions.add(new ChooseMode(game, player.getFigurine1(),false, i, j));
                    possibleActions.add(new ChooseMode(game, player.getFigurine2(),true, i, j));
                    possibleActions.add(new ChooseMode(game, player.getFigurine2(),false, i, j));
                }
            }
        }
        return possibleActions;
    }



    public static ArrayList<Action> createPlaceWorkerActions(Game game, Player player){
        ArrayList<Action> possibleActions= new ArrayList<>();

        for( int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if( game.getBoard().isEmpty(i,j) ) {
                    possibleActions.add(new PlaceWorker(game, player.getFigurine1(), i, j));
                }
            }
        }
        return possibleActions;

    }

    public static ArrayList<Action> createPlaceWorker2Actions(Game game, Player player){
        ArrayList<Action> possibleActions= new ArrayList<>();

        for( int i = 0; i < 5; ++i) {
            for (int j = 0; j < 5; ++j) {
                if( game.getBoard().isEmpty(i,j) ) {
                    possibleActions.add(new PlaceWorker2(game, player.getFigurine2(), i, j));
                }
            }
        }

        return possibleActions;

    }

    public static ArrayList<Action> createBuildingActions(Game game, Player player){
        ArrayList<Action> possibleActions = new ArrayList<>();

        //figuring out which figurine moved
        ArrayList<Action> oldActions = game.retrivePerformedActions();

        int movedFigurine = 1;
        for( int i = oldActions.size()-1; i >= 0; --i){
            Action action = oldActions.get(i);
            if( action instanceof Moving ){
                movedFigurine = ((Moving) action).getFigurineNumber();
            }
        }

        possibleActions.addAll(player.retirveFigurines()[movedFigurine-1].getPossibleBuildingActions(game));

        return possibleActions;
    }

}
