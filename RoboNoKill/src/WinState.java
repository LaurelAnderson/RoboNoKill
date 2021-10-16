import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.BlobbyTransition;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

class WinState extends BasicGameState {

    private int timer;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        timer = 4000;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {

        g.drawImage(ResourceManager.getImage(MainGame.BACKGROUND_RSC), 0,
                0);

        g.drawImage(ResourceManager.getImage(MainGame.WIN_BANNER_RSC), 150,
                230);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        timer -= delta;
        if (timer <= 0)
            game.enterState(MainGame.STARTUPSTATE, new EmptyTransition(), new BlobbyTransition());

    }

    @Override
    public int getID() {
        return MainGame.WINSTATE;
    }

}
