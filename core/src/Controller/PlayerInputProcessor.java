package Controller;

import model.Hero;
import View.SettingsScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.dungeonadventure.game.DungeonAdventure;

import static com.dungeonadventure.game.DungeonAdventure.*;
import static com.dungeonadventure.game.DungeonAdventure.SETTINGS_BUTTON_Y;

public class PlayerInputProcessor extends InputAdapter {
    private final Hero myPlayer;
    private final DungeonAdventure myGame;
    private final Screen myPreviousScreen;

    public PlayerInputProcessor(final Hero thePlayer, final DungeonAdventure theGame, final Screen thePreviousScreen) {
        myPlayer = thePlayer;
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        // Convert screen coordinates to match the game's coordinate system
        int x = DungeonAdventure.WIDTH - SETTINGS_BUTTON_WIDTH;
        int y = DungeonAdventure.HEIGHT - SETTINGS_BUTTON_Y - SETTINGS_BUTTON_HEIGHT;

        if (screenX >= x && screenX <= x + SETTINGS_BUTTON_WIDTH &&
                screenY >= y && screenY <= y + SETTINGS_BUTTON_HEIGHT) {
            myGame.setScreen(new SettingsScreen(myGame, myPreviousScreen));
            return true; // Indicates that the touch event was handled
        }

        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                myPlayer.moveCharacterUp();
                break;
            case Input.Keys.DOWN:
                myPlayer.moveCharacterDown();
                break;
            case Input.Keys.LEFT:
                myPlayer.moveCharacterLeft();
                break;
            case Input.Keys.RIGHT:
                myPlayer.moveCharacterRight();
                break;
            case Input.Keys.SPACE:
                myPlayer.attack();
                break;
            default:
                return false; // Indicates that the key event was not handled
        }
        return true; // Indicates that the key event was handled
    }
}


