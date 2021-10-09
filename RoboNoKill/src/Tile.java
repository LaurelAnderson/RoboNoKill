import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Tile extends Entity {

    int g;
    private final boolean isWall;
    private Vector overlayPos;
    final int key;
    private Tile prevTile;

    public Tile(final float x, final float y, boolean isWall, int key) {
        super(x, y);
        this.isWall = isWall;
        this.key = key;
        if (isWall)
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/walltest.png"));
        else {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/path.png"));
        }
    }

    // Store info on where this tile is in the 2d array
    public void setOverlayPos(final Vector pos) { this.overlayPos = pos; }
    public Vector getOverlayPos() { return this.overlayPos; }

    public int getOverlayX() { return Math.round(this.overlayPos.getX()); }
    public int getOverlayY() { return Math.round(this.overlayPos.getY()); }

    public Tile getPrevTile() { return this.prevTile; }
    public void setPrevTile(Tile prevTile) { this.prevTile = prevTile; }

    public boolean getIsWall() { return this.isWall; }

    public int getG() { return this.g; }

    public void update(final int delta) {
    }
}
