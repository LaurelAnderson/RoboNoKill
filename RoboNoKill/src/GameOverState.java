import java.util.Iterator;

import jig.ResourceManager;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;
import org.newdawn.slick.state.transition.*;

class GameOverState extends BasicGameState {

    private int timer;

    @Override
    public void init(GameContainer container, StateBasedGame game)
            throws SlickException {
    }

    @Override
    public void enter(GameContainer container, StateBasedGame game) {
        timer = 4000;
    }

    public void setUserScore(int bounces) {
    }

    @Override
    public void render(GameContainer container, StateBasedGame game,
                       Graphics g) throws SlickException {

        MainGame bg = (MainGame)game;

        g.drawImage(ResourceManager.getImage(MainGame.GAMEOVER_BANNER_RSC), 0,
                0);

    }

    @Override
    public void update(GameContainer container, StateBasedGame game,
                       int delta) throws SlickException {

        timer -= delta;
        if (timer <= 0)
            game.enterState(MainGame.STARTUPSTATE, new EmptyTransition(), new FadeInTransition());

    }

    @Override
    public int getID() {
        return MainGame.GAMEOVERSTATE;
    }

}