import java.util.Iterator;

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

        bg.suvivor.render(g);

//        g.drawString("Bounces: " + bounces, 10, 30);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        MainGame bg = (MainGame)game;

        // basic movement
        if (input.isKeyDown(Input.KEY_D)) {
            bg.suvivor.translate(5,0);
        }
        if (input.isKeyDown(Input.KEY_A)) {
            bg.suvivor.translate(-5,0);
        }
        if (input.isKeyDown(Input.KEY_S)) {
            bg.suvivor.translate(0,5);
        }
        if (input.isKeyDown(Input.KEY_W)) {
            bg.suvivor.translate(0,-5);
        }

        bg.suvivor.update(delta);

    }

    @Override
    public int getID() {
        return MainGame.PLAYINGSTATE;
    }

}
