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

    public static final String TEST_PIC = "Resource/test.png";
    public static final String TEST_WALL = "Resource/walltest.png";
    public static final String PATH_PIC = "Resource/path.png";
    public static final String ROBO_1_PIC = "Resource/robot1.png";
    public static final String ROBO_2_PIC = "Resource/robot2.png";
    public static final String ROBO_3_PIC = "Resource/robot3.png";

    public static final String GAMEOVER_BANNER_RSC = "Resource/GameOver.png";

    public static final String TEST_TXT = "RoboNoKill/RoboNoKill/src/Resource/maptest.txt";


    public final int ScreenWidth;
    public final int ScreenHeight;

    Tile [][] mapArray;
    String [][] overlay;

    Tile [] startingPos;

    // level1 - 2d array
    // level2 - 2d array

    Survivor survivor;
    Robot [] robots = new Robot[3];

    public MainGame(String title, int width, int height) {
        super(title);
        ScreenHeight = height;
        ScreenWidth = width;
        Entity.setDebug(true);
        Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);

    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {

        addState(new StartUpState());
        addState(new PlayingState());
        addState(new GameOverState());
        addState(new Level1());

        ResourceManager.loadImage(TEST_PIC);
        ResourceManager.loadImage(TEST_WALL);
        ResourceManager.loadImage(PATH_PIC);
        ResourceManager.loadImage(ROBO_1_PIC);
        ResourceManager.loadImage(ROBO_2_PIC);
        ResourceManager.loadImage(ROBO_3_PIC);
        ResourceManager.loadImage(GAMEOVER_BANNER_RSC);

        // create the array of walls
        // still do not know if I want to do it this way yet
//



        // testing
//        for (int temp = 0; temp < 21; temp++ ){
////            System.out.println(Arrays.toString(overlay[temp]));
//        }

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
