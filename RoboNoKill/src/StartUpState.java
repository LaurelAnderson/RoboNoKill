import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

class StartUpState extends BasicGameState {

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        container.setSoundOn(false);
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {
        MainGame bg = (MainGame)game;
        g.drawString("Press SPACE to enter play state", 10, 30);
    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        MainGame bg = (MainGame)game;

        if (input.isKeyDown(Input.KEY_SPACE))
            bg.enterState(MainGame.LEVEL1STATE);

    }

    @Override
    public int getID() {
        return MainGame.STARTUPSTATE;
    }

}
