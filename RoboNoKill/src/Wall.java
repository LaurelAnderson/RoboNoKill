import jig.Entity;
import jig.ResourceManager;

class Wall extends Entity {

    public Wall(final float x, final float y) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage("Resource/walltest.png"));
    }

    public void update(final int delta) {
    }
}