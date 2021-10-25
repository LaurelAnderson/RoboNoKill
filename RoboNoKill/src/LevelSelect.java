import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.EmptyTransition;
import org.newdawn.slick.state.transition.HorizontalSplitTransition;

public class LevelSelect extends BasicGameState {

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {

        g.drawImage(ResourceManager.getImage(MainGame.BACKGROUND_RSC), 0,
                0);

        g.drawImage(ResourceManager.getImage(MainGame.LEVEL_SELECT_RSC),145,
                230);

        g.drawString("Press 1 for level one", 345, 500);
        g.drawString("Press 2 for level two", 345, 550);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {
        Input input = container.getInput();
        MainGame bg = (MainGame)game;

        if (input.isKeyDown(Input.KEY_1))
            bg.enterState(MainGame.LEVEL1STATE);
        else if (input.isKeyDown(Input.KEY_2))
            bg.enterState(MainGame.LEVEL2STATE);

    }

    @Override
    public int getID() {
        return MainGame.LEVEL_SELECT;
    }

}
