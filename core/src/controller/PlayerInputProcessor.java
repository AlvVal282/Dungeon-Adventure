package controller;

import com.badlogic.gdx.Gdx;
import model.GameMaster;
import model.Tile;
import view.CombatScreen;
import view.GameScreen;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.dungeonadventure.game.DungeonAdventure;
import view.SettingsScreen;

import java.util.Random;

import static com.dungeonadventure.game.DungeonAdventure.mySETTINGS;

/**
 * Handles user input for player actions and settings navigation in the game.
 * @author alvarovaldez-duran
 * @version 1.0
 */
public class PlayerInputProcessor extends InputAdapter {
    private final DungeonAdventure myGame;
    private final GameScreen myPreviousScreen;

    /**
     * Constructs a new PlayerInputProcessor.
     *
     * @param theGame the main game instance
     * @param thePreviousScreen the previous screen to return to
     */
    public PlayerInputProcessor(final DungeonAdventure theGame, final GameScreen thePreviousScreen) {
        myGame = theGame;
        myPreviousScreen = thePreviousScreen;
    }

    /**
     * Handles key down events for player movement and actions.
     *
     * @param keycode the key code of the pressed key
     * @return true if the key event is handled, false otherwise
     */
    @Override
    public boolean keyDown(int keycode) {
        GameMaster gm = GameMaster.getInstance();
        switch (keycode) {
            case Input.Keys.UP:
                //check if cell at up is walkable
                if(gm.getMap()[gm.getPlayerX()][gm.getPlayerY()+1].isWalkable()){
                    gm.getPlayer().moveCharacterUp();
                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                if (gm.isHeroNearHealthPotion()) {
                    gm.heroPicksHealthPotion();
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Twinkle.ogg")));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPoisonPotion()) {
                    final Random rand = new Random();
                    final int damage = rand.nextInt(20);
                    myPreviousScreen.showTrapMessage("Hero affected by poison!!!\n" + gm.heroTrapDamage(damage));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearKey()) {
                    gm.heroPicksKey();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearBomb()) {
                    gm.heroPicksBomb();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPitTrap()) {
                    fallenInTrap(gm);
                }
//                if (gm.isHeroNearExit() && gm.getHeroKeys() == 4) {
//
//                }
                break;
            case Input.Keys.DOWN:
                if(gm.getMap()[gm.getPlayerX()][gm.getPlayerY()-1].isWalkable()){
                    gm.getPlayer().moveCharacterDown();
                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                if (gm.isHeroNearHealthPotion()) {
                    gm.heroPicksHealthPotion();
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Twinkle.ogg")));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPoisonPotion()) {
                    final Random rand = new Random();
                    final int damage = rand.nextInt(20);
                    myPreviousScreen.showTrapMessage("Hero affected by poison!!!\n" + gm.heroTrapDamage(damage));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearKey()) {
                    gm.heroPicksKey();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearBomb()) {
                    gm.heroPicksBomb();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPitTrap()) {
                    fallenInTrap(gm);
                }
                break;
            case Input.Keys.LEFT:
                if(gm.getMap()[gm.getPlayerX()-1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterLeft();
                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                if (gm.isHeroNearHealthPotion()) {
                    gm.heroPicksHealthPotion();
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Twinkle.ogg")));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPoisonPotion()) {
                    final Random rand = new Random();
                    final int damage = rand.nextInt(20);
                    myPreviousScreen.showTrapMessage("Hero affected by poison!!!\n" + gm.heroTrapDamage(damage));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearKey()) {
                    gm.heroPicksKey();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearBomb()) {
                    gm.heroPicksBomb();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPitTrap()) {
                    fallenInTrap(gm);
                }
                break;
            case Input.Keys.RIGHT:
                if(gm.getMap()[gm.getPlayerX()+1][gm.getPlayerY()].isWalkable()){
                    gm.getPlayer().moveCharacterRight();
                }
                if(gm.isHeroNearEnemy()){
                    myGame.setScreen(new CombatScreen(myGame, myPreviousScreen));
                }
                if (gm.isHeroNearHealthPotion()) {
                    gm.heroPicksHealthPotion();
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Twinkle.ogg")));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPoisonPotion()) {
                    final Random rand = new Random();
                    final int damage = rand.nextInt(20);
                    myPreviousScreen.showTrapMessage("Hero affected by poison!!!\n" + gm.heroTrapDamage(damage));
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearKey()) {
                    gm.heroPicksKey();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearBomb()) {
                    gm.heroPicksBomb();
                    gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
                }
                if (gm.isHeroNearPitTrap()) {
                    fallenInTrap(gm);
                }
                break;
            case Input.Keys.ESCAPE:
                myPreviousScreen.showMenu();
                break;
            case Input.Keys.E:
                openDoors();
                break;
            default:
                return false; // Indicates that the key event was not handled
        }
        myPreviousScreen.setPlayerImagePosition();
        return true; // Indicates that the key event was handled
    }

    private void fallenInTrap(final GameMaster theGm) {
        final GameMaster gm = theGm;
        final Random rand = new Random();
        final boolean hasFallen = rand.nextBoolean();
        final int damage = rand.nextInt(20);
        if (hasFallen) {
            myPreviousScreen.showTrapMessage("Hero has fallen in a pit trap!!!\n" + gm.heroTrapDamage(damage));
        } else {
            myPreviousScreen.showTrapMessage("Hero has fallen in a pit trap, but caught the ledge before falling!\n");
        }
        gm.getMap()[gm.getPlayerX()][gm.getPlayerY()] = Tile.FLOOR;
    }

    private void openDoors() {
        final GameMaster gm = GameMaster.getInstance();
        for (int i = gm.getPlayerX() - 1; i <= gm.getPlayerX() + 1; i++) {
            for (int j = gm.getPlayerY() - 1; j <= gm.getPlayerY() + 1; j++) {
                if (gm.getMap()[i][j] == Tile.DOOR) {
                    gm.getMap()[i][j] = Tile.OPEN_DOOR;
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Door Creak.ogg")));
                } else if (gm.getMap()[i][j] == Tile.OPEN_DOOR) {
                    gm.getMap()[i][j] = Tile.DOOR;
                    mySETTINGS.playSound(Gdx.audio.newSound(Gdx.files.internal("sounds/Door Creak2.ogg")));
                }
            }
        }

    }
}