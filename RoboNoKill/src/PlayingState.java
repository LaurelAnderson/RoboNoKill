import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

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
    public void enter(GameContainer container, StateBasedGame game) {
        container.setSoundOn(true);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {
        MainGame bg = (MainGame)game;

        bg.survivor.render(g);
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
//        g.drawString("Bounces: " + bounces, 10, 30);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        MainGame bg = (MainGame)game;

        // look for the debug signal
        if (input.isKeyPressed(Input.KEY_ESCAPE))
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
                    break;
                }
            }
        }

        // if we have just changed tiles, run Dijkstra's
        if (bg.survivor.getPrevWhere() != null && !checkTiles(bg.survivor.getPrevWhere(), bg.survivor.whereYouAt())) {
            // Dijkstra's Algo! Generate a filled map that can be turned off and on
            dijkstraAlgo(bg);
        }

        // get value of overlay where you currently are
        int j = bg.survivor.whereYouAt().getOverlayX();
        int i = bg.survivor.whereYouAt().getOverlayY();

        // basic movement can only click one at a time
        if (input.isKeyDown(Input.KEY_D)) {
            bg.survivor.setMoving(new Vector(5,0), bg.mapArray[i][j+1]);
        } else if (input.isKeyDown(Input.KEY_A)) {
            bg.survivor.setMoving(new Vector(-5, 0), bg.mapArray[i][j-1]);
        } else if (input.isKeyDown(Input.KEY_S)) {
            bg.survivor.setMoving(new Vector(0,5), bg.mapArray[i+1][j]);
        } else if (input.isKeyDown(Input.KEY_W)) {
            bg.survivor.setMoving(new Vector(0,-5), bg.mapArray[i-1][j]);
        }

        bg.survivor.update(delta);

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
        start.setPrevTile(null);
        unvisited.add(start);

        while (unvisited.size() > 0) {

            // get element with smallest g value off of heap
            Tile current = unvisited.remove();
            if (current.g != 0) {
                System.out.println(current.getPrevTile().g);
            }

            int j = current.getOverlayX();
            int i = current.getOverlayY();

            if (!bg.mapArray[i][j + 1].getIsWall()) { // check left
                relax(current, bg.mapArray[i][j + 1]);
            }
            if (!bg.mapArray[i][j - 1].getIsWall()) { // check right
                relax(current, bg.mapArray[i][j - 1]);
            }
            if (!bg.mapArray[i + 1][j].getIsWall()) { // check down
                relax(current, bg.mapArray[i + 1][j]);
            }
            if (!bg.mapArray[i - 1][j].getIsWall()) { // check up
                relax(current, bg.mapArray[i - 1][j]);
            }

            visited.put(current.key, current.g);

        }

        // Empty the visited table
        visited.clear();

    }

    public void relax(Tile current, Tile succ) {

        // Calculate cost for the successor
        int tempG = current.g + 1;

        boolean isReached = visited.containsKey(succ.key);

        // check if the next tile is not in visited or if it's g value is more than this g value
        if (!isReached || tempG < succ.g) {

            unvisited.remove(succ);
            succ.g = tempG;
            succ.setPrevTile(current);
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
