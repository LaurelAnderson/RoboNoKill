import jig.Entity;
import jig.ResourceManager;

import jig.Vector;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.awt.desktop.SystemEventListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class MainGame extends StateBasedGame {

    public static final int STARTUPSTATE = 0;
    public static final int PLAYINGSTATE = 1;
    public static final int GAMEOVERSTATE = 2;
    public static final int LEVEL1STATE = 3;
    public static final int LEVEL2STATE = 4;
    public static final int WINSTATE = 5;
    public static final int CONTINUE = 6;

    public static final String TEST_PIC = "Resource/Survivor.png";
    public static final String TEST_WALL = "Resource/walltest.png";
    public static final String PATH_PIC = "Resource/path.png";
    public static final String PANEL_PIC = "Resource/panelPath.png";
    public static final String ROBO_1_PIC = "Resource/Robo1.png;";
    public static final String ROBO_2_PIC = "Resource/Robo2.png";
    public static final String ROBO_3_PIC = "Resource/Robo3.png";

    public static final String GAMEOVER_BANNER_RSC = "Resource/GameOver.png";
    public static final String WIN_BANNER_RSC = "Resource/Win.png";
    public static final String CONTINEUE_RSC = "Resource/Continue.png";

    public static final String TEST_TXT = "RoboNoKill/RoboNoKill/src/Resource/maptest.txt";
    public static final String TEST2_TXT = "RoboNoKill/RoboNoKill/src/Resource/maptest2.txt";

    public final int ScreenWidth;
    public final int ScreenHeight;

    Tile [][] mapArray;
    String [][] overlay;

    Tile [] startingPos;
    double [] panelHealth = new double[3];

    Survivor survivor;
    Robot [] robots = new Robot[3];

    public MainGame(String title, int width, int height) {
        super(title);
        ScreenHeight = height;
        ScreenWidth = width;
//        Entity.setDebug(true);
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
        ResourceManager.loadImage(PATH_PIC);
        ResourceManager.loadImage(PANEL_PIC);
        ResourceManager.loadImage(ROBO_1_PIC);
        ResourceManager.loadImage(ROBO_2_PIC);
        ResourceManager.loadImage(ROBO_3_PIC);
        ResourceManager.loadImage(GAMEOVER_BANNER_RSC);
        ResourceManager.loadImage(WIN_BANNER_RSC);
        ResourceManager.loadImage(CONTINEUE_RSC);

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
