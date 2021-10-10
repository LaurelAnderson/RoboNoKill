import jig.Entity;
import jig.ResourceManager;
import jig.Vector;


class Robot extends Entity {

    public Robot(final float x, final float y, Tile start) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage("Resource/robot1.png"));
    }

    public void update(final int delta) {
    }
}
