package ch.uzh.ifi.seal.soprafs19.entity.actions;

import ch.uzh.ifi.seal.soprafs19.entity.Game;
import ch.uzh.ifi.seal.soprafs19.entity.GodCards.*;
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

}