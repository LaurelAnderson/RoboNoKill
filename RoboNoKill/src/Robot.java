import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Robot extends Entity {

    private Vector direction;
    private Tile where;

    public Robot(final float x, final float y, Tile start) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage("Resource/robot1.png"));
        this.where = start;
    }

    public void setWhere(Tile where) { this.where = where; }

    public void setDirection(Vector direction) { this.direction = direction; }
    public Vector getDirection() { return this.direction; }

    public void update(final int delta) {
        translate(this.direction);
    }
}
