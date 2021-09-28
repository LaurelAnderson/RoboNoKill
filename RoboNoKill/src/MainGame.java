import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Arrays;
import java.util.Scanner;

public class MainGame extends StateBasedGame {

    public static final int STARTUPSTATE = 0;
    public static final int PLAYINGSTATE = 1;

    public static final String TEST_PIC = "Resource/test.png";
    public static final String TEST_WALL = "Resource/walltest.png";

    public static final String TEST_TXT = "RoboNoKill/RoboNoKill/src/Resource/maptest.txt";

    public final int ScreenWidth;
    public final int ScreenHeight;

    Wall [][] mapArray;
    String [][] overlay;

    // level1 - 2d array
    // level2 - 2d array

    Survivor survivor;

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

        ResourceManager.loadImage(TEST_PIC);
        ResourceManager.loadImage(TEST_WALL);


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

        mapArray = new Wall[rows][cols];
        overlay = new String[rows][cols];

        while(sc.hasNextLine()) {
            for (int i = 0; i < overlay.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    overlay[i][j] = line[j];
                    if (overlay[i][j].compareTo("X") == 0) {
                        mapArray[i][j] = new Wall(x,y);
                    }
                    y += 40;
                }
                y = 120;
                x += 40;
            }
        }

        survivor = new Survivor(440,480);

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
