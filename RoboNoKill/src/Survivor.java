import jig.Entity;
import jig.ResourceManager;

class Survivor extends Entity {

    Tile where;

    public Survivor(final float x, final float y) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage("Resource/test.png"));
    }

    public void setWhereYouAt(Tile tile) { this.where = tile; }
    public Tile whereYouAt() { return this.where; }

    public void update(final int delta) {
    }
}
