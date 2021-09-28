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

        // render all the walls of the map
        // still do not know if I want to do it this way yet
        for (int row = 0; row < bg.mapArray.length; row++) {
           for (int col = 0; col < bg.mapArray[row].length; col++){
               if (bg.mapArray[row][col] != null) {
                   bg.mapArray[row][col].render(g);
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

        // basic movement
        if (input.isKeyDown(Input.KEY_D)) {
            bg.survivor.translate(5,0);
        }
        if (input.isKeyDown(Input.KEY_A)) {
            bg.survivor.translate(-5,0);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            bg.survivor.translate(0,5);
        }
        if (input.isKeyDown(Input.KEY_W)) {
            bg.survivor.translate(0,-5);
        }

        bg.survivor.update(delta);

    }

    @Override
    public int getID() {
        return MainGame.PLAYINGSTATE;
    }

}
