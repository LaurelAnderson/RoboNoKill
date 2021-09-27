import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Survivor extends Entity {

    public Survivor(final float x, final float y) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage("RoboNoKill/Resource/test.png"));
    }

    public void update(final int delta) {
    }
}
