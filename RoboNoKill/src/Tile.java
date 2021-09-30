import jig.Entity;
import jig.ResourceManager;

public class Tile extends Entity {

    private boolean stepped;
    private boolean isWall;

    public Tile(final float x, final float y, boolean isWall) {
        super(x, y);
        this.isWall = isWall;
        if (isWall)
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/walltest.png"));
        else
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/path.png"));
    }

    public boolean isThisWall() { return isWall; };

    public boolean areWeHere() { return stepped; };

    public void weAreHere() { this.stepped = true; };

    public void update(final int delta) {
    }
}
