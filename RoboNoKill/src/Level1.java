import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

import jig.Collision;
import jig.ResourceManager;
import jig.Vector;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

public class Level1 extends PlayingState {

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        MainGame bg = (MainGame)game;

        container.setSoundOn(true);

        Scanner sc = null;
        try {
            sc = new Scanner(new BufferedReader(new FileReader(MainGame.TEST_TXT)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        int rows = 21;
        int cols = 21;
        int x = 40;
        int y = 120;

        bg.mapArray = new Tile[rows][cols];
        bg.overlay = new String[rows][cols];

//        Vector overlayPos;

        int key = 0;

        while(sc.hasNextLine()) {
            for (int i = 0; i < bg.overlay.length; i++) {
                String[] line = sc.nextLine().trim().split(" ");
                for (int j = 0; j < line.length; j++) {
                    bg.overlay[i][j] = line[j];

                    if (line[j].compareTo("X") == 0) {
                        bg.mapArray[i][j] = new Tile(x,y,true, key, false);
                    } else if (line[j].compareTo("0") == 0) {
                        bg.mapArray[i][j] = new Tile(x,y,false, key, false);
                    } else {
                        bg.mapArray[i][j] = new Tile(x,y,false, key, true);
                    }

                    bg.mapArray[i][j].setOverlayPos(new Vector(j, i));
                    x += 40;
                    key++;

                }

                y += 40;
                x = 40;

            }
        }

        // change current tile to correct????
        bg.survivor = new Survivor(bg.mapArray[9][10].getX(),bg.mapArray[9][10].getY(), bg.mapArray[10][10]);

        // init starting positions, survivor, robot1, robot2, robot3
        bg.startingPos = new Tile[]{bg.mapArray[10][10], bg.mapArray[1][1], bg.mapArray[1][19], bg.mapArray[19][1]};

        // init each of the robots
        for (int i = 0; i < 3; i++) {
            bg.robots[i] = new Robot(bg.startingPos[i+1].getX(), bg.startingPos[i+1].getY(),
                    bg.startingPos[i+1], i+1);
        }

    }


    @Override
    public int getID() {
        return MainGame.LEVEL1STATE;
    }

}
