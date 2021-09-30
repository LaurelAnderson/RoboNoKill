import jig.Vector;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class PlayingState extends BasicGameState {

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
           }
        }
//        g.drawString("Bounces: " + bounces, 10, 30);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        MainGame bg = (MainGame)game;

        // check where the player is
        // double check that this is working or better way?
        for (int row = 0; row < bg.mapArray.length; row++) {
            for (int col = 0; col < bg.mapArray.length; col++){

                // create a helper for this
                if (bg.survivor.getX() == bg.mapArray[row][col].getX() &&
                        bg.survivor.getY() <= bg.mapArray[row][col].getY() + 10 &&
                        bg.survivor.getY() > bg.mapArray[row][col].getY() - 10 ||
                        bg.survivor.getY() == bg.mapArray[row][col].getY() &&
                        bg.survivor.getX() <= bg.mapArray[row][col].getX() + 10 &&
                        bg.survivor.getX() > bg.mapArray[row][col].getX() - 10) {
                    bg.survivor.setWhereYouAt(bg.mapArray[row][col]);
                    break;
                }
            }
        }

        System.out.println(bg.survivor.whereYouAt().getOverlayPos());
        int j = bg.survivor.whereYouAt().getOverlayX();
        int i = bg.survivor.whereYouAt().getOverlayY();

        // basic movement
        if (input.isKeyDown(Input.KEY_D) && bg.overlay[i][j+1].compareTo("X") != 0) {
            bg.survivor.translate(5,0);
        }
        if (input.isKeyDown(Input.KEY_A) && bg.overlay[i][j-1].compareTo("X") != 0) {
            bg.survivor.translate(-5,0);
        }
        if (input.isKeyDown(Input.KEY_S) && bg.overlay[i+1][j].compareTo("X") != 0) {
            bg.survivor.translate(0,5);
        }
        if (input.isKeyDown(Input.KEY_W) && bg.overlay[i-1][j].compareTo("X") != 0) {
            bg.survivor.translate(0,-5);
        }

        bg.survivor.update(delta);

    }

    @Override
    public int getID() {
        return MainGame.PLAYINGSTATE;
    }

}
