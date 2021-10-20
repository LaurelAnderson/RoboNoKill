import jig.ResourceManager;
import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;
import org.newdawn.slick.state.transition.RotateTransition;

import java.util.*;

class PlayingState extends BasicGameState {

    // create a min heap that rates via the f values. Unvisited tiles
    PriorityQueue<Tile> unvisited = new PriorityQueue<>((o1, o2) -> Integer.compare(o1.g, o2.g));
    // create a dict that keeps track of visited tiles
    Hashtable<Integer, Integer> visited = new Hashtable<>();

    private boolean debug = false;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {
        MainGame bg = (MainGame)game;

        g.drawImage(ResourceManager.getImage(MainGame.BACKGROUND_RSC), 0,
                0);

        int buffer = 120;

        // render the entities
        bg.survivor.render(g);
        if (bg.bolt != null) {
            bg.bolt.render(g);
        }
        for (int robot = 0; robot < 3; robot++) {
            bg.robots[robot].render(g);
        }

        // render all the tiles of the map
        for (int row = 0; row < bg.mapArray.length; row++) {
           for (int col = 0; col < bg.mapArray.length; col++){

               bg.mapArray[row][col].render(g);

               // draw the cost of each tile to the screen
               if (!bg.mapArray[row][col].getIsWall() && this.debug) {
                   g.drawString(Integer.toString(bg.mapArray[row][col].g), bg.mapArray[row][col].getX(),
                           bg.mapArray[row][col].getY());
               }
           }
        }

        // draw the number of bolts you have
        g.drawString("Bolts: ",20, 50);
        g.drawString(Integer.toString(bg.boltNum), 80, 50);

        // draw the panel health to the screen
        g.drawString("Panel Health: ",20, 75);
        for (int i = 0; i < 3; i ++) {
            g.drawString(" " + Math.round(bg.panelHealth[i]),20 + buffer, 75);
            buffer += 40;
        }

        // testing
        for (int i = 0; i < bg.pickupBolts.size(); i++) {
            bg.pickupBolts.get(i).render(g);
        }

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        MainGame bg = (MainGame)game;

        // look for the debug signal
        if (input.isKeyPressed(Input.KEY_LCONTROL))
            setDebug();

        // check where the player is
        for (int row = 0; row < bg.mapArray.length; row++) {
            for (int col = 0; col < bg.mapArray.length; col++){

                // check if survivor is on the tile
                if (bg.survivor.getX() == bg.mapArray[row][col].getX() &&
                        bg.survivor.getY() <= bg.mapArray[row][col].getY() + 10 &&
                        bg.survivor.getY() > bg.mapArray[row][col].getY() - 10 ||
                        bg.survivor.getY() == bg.mapArray[row][col].getY() &&
                        bg.survivor.getX() <= bg.mapArray[row][col].getX() + 10 &&
                        bg.survivor.getX() > bg.mapArray[row][col].getX() - 10) {
                    // set were you were and where you are currently
                    bg.survivor.setWhereYouWere(bg.survivor.whereYouAt());
                    bg.survivor.setWhereYouAt(bg.mapArray[row][col]);
                }

                // check robos
                for (int robot = 0; robot < 3; robot++) {

                    // check the tiles that the robos go through
                    if(bg.robots[robot].getX() == bg.mapArray[row][col].getX() &&
                            bg.robots[robot].getY() == bg.mapArray[row][col].getY()) {
                        if (bg.mapArray[row][col].getPi() == null) {
                            bg.robots[robot].setDirection(new Vector(0,0));
                        } else
                            bg.robots[robot].checkRoboState(bg.mapArray[row][col], bg.mapArray);
                    }

                    // check if any of the robos collides with the survivor while they are not stunned
                    if (bg.robots[robot].collides(bg.survivor) != null && !bg.robots[robot].getStunned())
                        game.enterState(MainGame.GAMEOVERSTATE, new EmptyTransition(), new RotateTransition());

                    // check if any of the robos collides with the bolt - do not need to do this here
                    if (bg.bolt != null && bg.robots[robot].collides(bg.bolt) != null) {
                        bg.bolt = null;
                        System.out.println("Robo " + bg.robots[robot].whatRobo + " got stunned" );
                        bg.robots[robot].stunnedLogic();
                    }
                }

                // check if the bolt collides with any of the walls
                if (bg.bolt != null && bg.mapArray[row][col].getIsWall() &&
                        bg.bolt.collides(bg.mapArray[row][col]) != null) {
                    bg.bolt = null;
                }
            }
        }

        // if we have just changed tiles, run Dijkstra's
        if (bg.survivor.getPrevWhere() != null && !checkTiles(bg.survivor.getPrevWhere(), bg.survivor.whereYouAt())) {
            // Dijkstra's Algo! Generate a filled map that can be turned off and on
            dijkstraAlgo(bg);
        }

        // check if the survivor collides with any of the pickup bolts
        for (int bolt = 0; bolt < bg.pickupBolts.size(); bolt++) {
            if (bg.survivor.collides(bg.pickupBolts.get(bolt)) != null) {
                bg.boltNum++;
                bg.pickupBolts.remove(bolt);
            }
        }

        // check if we need to update the panel. If so, do it
        if (bg.survivor.whereYouAt().getIsPanel() && input.isKeyDown(Input.KEY_W)) {
            if (bg.panelHealth[bg.survivor.whereYouAt().whatPanel] < 0) {
                bg.panelHealth[bg.survivor.whereYouAt().whatPanel] = 0;
            } else {
                bg.panelHealth[bg.survivor.whereYouAt().whatPanel] -= 0.5f;
            }
        }

        // get value of overlay where you currently are
        int j = bg.survivor.whereYouAt().getOverlayX();
        int i = bg.survivor.whereYouAt().getOverlayY();

        // basic movement can only click one at a time
        if (input.isKeyDown(Input.KEY_D)) {
            bg.survivor.setMoving(new Vector(4,0), bg.mapArray[i][j+1]);
        } else if (input.isKeyDown(Input.KEY_A)) {
            bg.survivor.setMoving(new Vector(-4, 0), bg.mapArray[i][j-1]);
        } else if (input.isKeyDown(Input.KEY_S)) {
            bg.survivor.setMoving(new Vector(0,4), bg.mapArray[i+1][j]);
        } else if (input.isKeyDown(Input.KEY_W)) {
            bg.survivor.setMoving(new Vector(0,-4), bg.mapArray[i-1][j]);
        }

        // fire a bolt!
        if (input.isKeyPressed(Input.KEY_E) && bg.boltNum > 0) {
            // launch the bolt according to the getMoving var
            bg.bolt = new Bolt(bg.survivor.getX(), bg.survivor.getY(), bg.survivor.getMoving());
            bg.boltNum--;
        }

        // update survivor
        bg.survivor.update(delta);

        // update bolt
        if (bg.bolt != null) {
            bg.bolt.update(delta);
        }

        // update the robots
        for (int robot = 0; robot < 3; robot++) {
            bg.robots[robot].update(delta);
        }

        // Check if you won the level
        if (bg.panelHealth[0] <= 0 && bg.panelHealth[1] <= 0 && bg.panelHealth[2] <= 0) {
            if (bg.getCurrentState().getID() == MainGame.LEVEL2STATE) {
                game.enterState(MainGame.WINSTATE);
            } else {
                game.enterState(MainGame.CONTINUE);
            }
        }

    }

    public void dijkstraAlgo(MainGame bg) {

        // put all non wall tiles in priority queue
        for (int row = 0; row < bg.mapArray.length; row++) {
            for (int col = 0; col < bg.mapArray.length; col++) {
                if (!bg.mapArray[row][col].getIsWall()) {
                    bg.mapArray[row][col].g = 1000;
                    unvisited.add(bg.mapArray[row][col]);
                }
            }
        }

        // Set up the fist tile you are on with the proper values and add it to visited
        Tile start = bg.survivor.whereYouAt();
        unvisited.remove(start);
        start.g = 0;
        unvisited.add(start);

        while (unvisited.size() > 0) {

            // get element with smallest g value off of heap
            Tile current = unvisited.remove();

            int j = current.getOverlayX();
            int i = current.getOverlayY();

            if (!bg.mapArray[i][j + 1].getIsWall()) { // check left
                relax(current, bg.mapArray[i][j + 1], new Vector(-1,0));
            }
            if (!bg.mapArray[i][j - 1].getIsWall()) { // check right
                relax(current, bg.mapArray[i][j - 1], new Vector(1, 0));
            }
            if (!bg.mapArray[i + 1][j].getIsWall()) { // check down
                relax(current, bg.mapArray[i + 1][j], new Vector(0,-1));
            }
            if (!bg.mapArray[i - 1][j].getIsWall()) { // check up
                relax(current, bg.mapArray[i - 1][j], new Vector(0, 1));
            }

            visited.put(current.key, current.g);

        }

        // Empty the visited table
        visited.clear();

    }

    public void relax(Tile current, Tile succ, Vector pi) {

        // Calculate cost for the successor
        int tempG = current.g + 1;

        boolean isReached = visited.containsKey(succ.key);

        // check if the next tile is not in visited or if it's g value is more than this g value
        if (!isReached || tempG < succ.g) {

            unvisited.remove(succ);
            succ.g = tempG;
            succ.setPi(pi);
            unvisited.add(succ);

        }
    }

    // check to see if 2 tiles are the same
    public boolean checkTiles(Tile tile1, Tile tile2) {
        return tile1.getOverlayX() == tile2.getOverlayX() && tile1.getOverlayY() == tile2.getOverlayY();
    }

    public void setDebug() { this.debug = !this.debug; }

    @Override
    public int getID() {
        return MainGame.PLAYINGSTATE;
    }

}
