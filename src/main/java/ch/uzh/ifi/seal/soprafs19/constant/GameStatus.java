package ch.uzh.ifi.seal.soprafs19.constant;

public enum GameStatus {
    CHOSING_GAME_MODE, CHOSING_GODCARDS, PICKING_GODCARDS,
    SettingFigurinesp1f1, SettingFigurinesp1f2, SettingFigurinesp2f1, SettingFigurinesp2f2,
    MOVING_STARTINGPLAYER, BUILDING_STARTINGPLAYER, MOVING_NONSTARTINGPLAYER, BUILDING_NONSTARTINGPLAYER,
    STARTINGPLAYER_WON, NONSTARTINGPLAYER_WON,
    GODMODE_STATE_STARTINGPLAYER, GODMODE_STATE_NONSTARTINGPLAYER;

    public int player(){
        if( this == PICKING_GODCARDS || this == SettingFigurinesp2f1 || this == SettingFigurinesp2f2 ||
        this == MOVING_NONSTARTINGPLAYER || this == BUILDING_NONSTARTINGPLAYER || this == GODMODE_STATE_NONSTARTINGPLAYER){
            return 2;
        }
        else{ return 1; }
    }
}
