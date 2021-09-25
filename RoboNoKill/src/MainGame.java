import java.util.ArrayList;

import jig.Entity;
import jig.ResourceManager;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {

    public static final String TEST_PIC = "RoboNoKill/RoboNoKill/src/RoboNoKill/Resource/test.png";

    public final int ScreenWidth;
    public final int ScreenHeight;

    public MainGame(String title, int width, int height) {
        super(title);
        ScreenHeight = height;
        ScreenWidth = width;

        Entity.setCoarseGrainedCollisionBoundary(Entity.AABB);
    }

    @Override
    public void initStatesList(GameContainer container) throws SlickException {

        ResourceManager.loadImage(TEST_PIC);

    }

    public static void main(String[] args) {
        AppGameContainer app;
        try {
            app = new AppGameContainer(new MainGame("RoboNoKill!", 800, 600));
            app.setDisplayMode(1100, 1200, false);
            app.setVSync(true);
            app.start();
        } catch (SlickException e) {
            e.printStackTrace();
        }

    }
}
