import java.util.Iterator;

import com.sun.tools.javac.Main;
import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

class StartUpState extends BasicGameState {

    private int timer;
    boolean display = true;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {

        // start music in here
        // have a playing check to see if you are playing

    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        container.setSoundOn(false);
        timer = 700;
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {
        MainGame bg = (MainGame)game;

        g.drawImage(ResourceManager.getImage(MainGame.BACKGROUND_RSC), 0,
                0);

        g.drawImage(ResourceManager.getImage(MainGame.START_SCREEN_RSC), 120,
                150);

        if (display) {
            g.drawString("Press SPACE to start game", 330, 650);

        }
    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        Input input = container.getInput();
        MainGame bg = (MainGame)game;

        timer -= delta;
        if (timer <= 0) {
            display = !display;
            timer = 700;
        }

        if (input.isKeyDown(Input.KEY_SPACE))
            bg.enterState(MainGame.LEVEL1STATE);

    }

    @Override
    public int getID() {
        return MainGame.STARTUPSTATE;
    }

}
