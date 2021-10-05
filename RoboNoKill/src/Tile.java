import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Tile extends Entity {

    boolean isWall;
    private Vector overlayPos;

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

    // Store info on where this tile is in the 2d array
    public void setOverlayPos(final Vector pos) { this.overlayPos = pos; }
    public Vector getOverlayPos() { return this.overlayPos; }

    public int getOverlayX() { return (int)this.overlayPos.getX(); }
    public int getOverlayY() { return (int)this.overlayPos.getY(); }

    public boolean getIsWall() { return this.isWall; }

    public void update(final int delta) {
    }
}
