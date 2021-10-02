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

                // create a helper for this - double check logic
                if (bg.survivor.getX() == bg.mapArray[row][col].getX() &&
                        bg.survivor.getY() <= bg.mapArray[row][col].getY() + 20 &&
                        bg.survivor.getY() > bg.mapArray[row][col].getY() - 20 ||
                        bg.survivor.getY() == bg.mapArray[row][col].getY() &&
                        bg.survivor.getX() <= bg.mapArray[row][col].getX() + 20 &&
                        bg.survivor.getX() > bg.mapArray[row][col].getX() - 20) {
                    bg.survivor.setWhereYouAt(bg.mapArray[row][col]);
                    break;
                }
            }
        }

//        System.out.println(bg.survivor.whereYouAt().getOverlayPos());
        // get value of overlay where you currently are
        int j = bg.survivor.whereYouAt().getOverlayX();
        int i = bg.survivor.whereYouAt().getOverlayY();

        // basic movement
        if (input.isKeyDown(Input.KEY_D)) {
            bg.survivor.setMoving(new Vector(5,0), bg.mapArray[i][j+1]);
        }
        if (input.isKeyDown(Input.KEY_A)) {
            bg.survivor.setMoving(new Vector(-5, 0), bg.mapArray[i][j-1]);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            bg.survivor.setMoving(new Vector(0,5), bg.mapArray[i+1][j]);
        }
        if (input.isKeyDown(Input.KEY_W)) {
            bg.survivor.setMoving(new Vector(0,-5), bg.mapArray[i-1][j]);
        }

        bg.survivor.update(delta);

    }

    @Override
    public int getID() {
        return MainGame.PLAYINGSTATE;
    }

}
