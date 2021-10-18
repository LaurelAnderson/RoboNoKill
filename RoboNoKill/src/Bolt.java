import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Bolt extends Entity {

    Vector launch;

    public Bolt(final float x, final float y, Vector launch) {
        super(x, y);
        this.launch = launch;
        addImageWithBoundingBox(ResourceManager
                .getImage("Resource/Bolt.png"));


    }

    public void update(final int delta) {
        translate(this.launch);
    }

}
