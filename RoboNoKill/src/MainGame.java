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

    public static final String TEST_PIC = "Resource/test.png";
    public static final String TEST_WALL = "Resource/walltest.png";
    public static final String PATH_PIC = "Resource/path.png";
    public static final String ROBO_1_PIC = "Resource/robot1.png";

    public static final String GAMEOVER_BANNER_RSC = "Resource/GameOver.png";

    public static final String TEST_TXT = "RoboNoKill/RoboNoKill/src/Resource/maptest.txt";

    public final int ScreenWidth;
    public final int ScreenHeight;

    Tile [][] mapArray;
    String [][] overlay;

    // level1 - 2d array
    // level2 - 2d array

    Survivor survivor;
    Robot robot1;

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

        ResourceManager.loadImage(TEST_PIC);
        ResourceManager.loadImage(TEST_WALL);
        ResourceManager.loadImage(PATH_PIC);
        ResourceManager.loadImage(ROBO_1_PIC);
        ResourceManager.loadImage(GAMEOVER_BANNER_RSC);

        // create the array of walls
        // still do not know if I want to do it this way yet
        Scanner sc = null;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(TEST_TXT)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int rows = 21;
        int cols = 21;
        int x = 40;
        int y = 120;

        mapArray = new Tile[rows][cols];
        overlay = new String[rows][cols];

//        Vector overlayPos;

        int key = 0;

        while(sc.hasNextLine()) {
            for (int i = 0; i < overlay.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    overlay[i][j] = line[j];
                    if (line[j].compareTo("X") == 0) {
                        mapArray[i][j] = new Tile(x,y,true, key);
                    }else {
                        mapArray[i][j] = new Tile(x,y,false, key);
                    }
                    mapArray[i][j].setOverlayPos(new Vector(j, i));
//                    System.out.println(mapArray[i][j].getPosition());
                    x += 40;
                    key++;
                }
                y += 40;
                x = 40;
            }
        }

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
