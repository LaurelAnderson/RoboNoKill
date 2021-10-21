import jig.Entity;
import jig.ResourceManager;

import jig.Vector;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.desktop.SystemEventListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MainGame extends StateBasedGame {

    // states
    public static final int STARTUPSTATE = 0;
    public static final int PLAYINGSTATE = 1;
    public static final int GAMEOVERSTATE = 2;
    public static final int LEVEL1STATE = 3;
    public static final int LEVEL2STATE = 4;
    public static final int WINSTATE = 5;
    public static final int CONTINUE = 6;

    // graphics
    public static final String TEST_PIC = "Resource/Survivor.png";
    public static final String TEST_WALL = "Resource/Level1Block.png";
    public static final String TEST_WALL2 = "Resource/Level2Block.png";
    public static final String PATH_PIC = "Resource/path.png";
    public static final String PANEL_PIC = "Resource/panelPath.png";
    public static final String ROBO_1_PIC = "Resource/Robo1.png";
    public static final String ROBO_2_PIC = "Resource/Robo2.png";
    public static final String ROBO_3_PIC = "Resource/Robo3.png";
    public static final String BOLT_PIC = "Resource/Bolt.png";

    public static final String GAMEOVER_BANNER_RSC = "Resource/GameOver.png";
    public static final String WIN_BANNER_RSC = "Resource/Win.png";
    public static final String CONTINEUE_RSC = "Resource/Continue.png";

    public static final String BACKGROUND_RSC = "Resource/Background.png";
    public static final String START_SCREEN_RSC = "Resource/StartScreen.png";

    // map files
    public static final String TEST_TXT = "RoboNoKill/RoboNoKill/src/Resource/maptest.txt";
    public static final String TEST2_TXT = "RoboNoKill/RoboNoKill/src/Resource/maptest2.txt";

    // sounds and music
    public static final String MAIN_TUNE_RSC = "Resource/Sound/404452__furbyguy__synthwave-loop.wav";
    public static final String HIT_SOUND_RSC = "Resource/Sound/66918__mikemunkie__synthtomlow.wav";
    public static final String BOLT_PICKUP = "Resource/Sound/411443__abbasgamez__powerup2.wav";
    public static final String GAME_OVER_RSC = "Resource/Sound/415079__harrietniamh__video-game-death-sound-effect.wav";
    public static final String WINNING_RSC = "Resource/Sound/518855__mrickey13__fight-win-tune.wav";

    public final int ScreenWidth;
    public final int ScreenHeight;

    Tile [][] mapArray;
    String [][] overlay;

    Tile [] startingPos;
    double [] panelHealth = new double[3];

    Survivor survivor;
    Robot [] robots = new Robot[3];

    ArrayList<Bolt> pickupBolts;

    int boltNum;

    Music tune;

    // test
    Bolt bolt = null;

    public MainGame(String title, int width, int height) {
        super(title);
        ScreenHeight = height;
        ScreenWidth = width;
        Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);

    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {

        addState(new StartUpState());
        addState(new PlayingState());
        addState(new GameOverState());
        addState(new Level1());
        addState(new Level2());
        addState(new WinState());
        addState(new Continue());

        ResourceManager.loadImage(TEST_PIC);
        ResourceManager.loadImage(TEST_WALL);
        ResourceManager.loadImage(TEST_WALL2);
        ResourceManager.loadImage(PATH_PIC);
        ResourceManager.loadImage(PANEL_PIC);
        ResourceManager.loadImage(ROBO_1_PIC);
        ResourceManager.loadImage(ROBO_2_PIC);
        ResourceManager.loadImage(ROBO_3_PIC);
        ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
        ResourceManager.loadImage(WIN_BANNER_RSC);
        ResourceManager.loadImage(CONTINEUE_RSC);
        ResourceManager.loadImage(BACKGROUND_RSC);
        ResourceManager.loadImage(START_SCREEN_RSC);
        ResourceManager.loadImage(BOLT_PIC);

        ResourceManager.loadMusic(MAIN_TUNE_RSC);
        ResourceManager.loadSound(HIT_SOUND_RSC);
        ResourceManager.loadSound(BOLT_PICKUP);
        ResourceManager.loadSound(GAME_OVER_RSC);
        ResourceManager.loadSound(WINNING_RSC);

    }

    public static void main(String[] args) {
        AppGameContainer app;
        try {
            app = new AppGameContainer(new MainGame("RoboNoKill!", 880, 960));
            app.setDisplayMode(880, 960, false);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }
    }
}
