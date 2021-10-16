import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

public class Tile extends Entity {

    int g;
    private final boolean isWall;
    private Vector overlayPos;
    final int key;
    private Vector pi;

    boolean healthGone = false;
    boolean isPanel = false;
    int whatPanel;

    public Tile(final float x, final float y, boolean isWall, int key, int whatPanel, int level) {
        super(x, y);
        this.isWall = isWall;
        this.key = key;
        if (isWall) {

            // check for which level
            if (level == 1) {
                addImageWithBoundingBox(ResourceManager
                        .getImage("Resource/Level1Block.png"));
            } else {
                addImageWithBoundingBox(ResourceManager
                        .getImage("Resource/Level2Block.png"));
            }

        } else if (whatPanel != 100) {

            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/panelPath.png"));
            this.whatPanel = whatPanel;
            this.isPanel = true;

        } else {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/path.png"));
        }
    }

    // Store info on where this tile is in the 2d array
    public void setOverlayPos(final Vector pos) { this.overlayPos = pos; }
    public Vector getOverlayPos() { return this.overlayPos; }

    public int getOverlayX() { return Math.round(this.overlayPos.getX()); }
    public int getOverlayY() { return Math.round(this.overlayPos.getY()); }

    public void setPi(Vector pi) { this.pi = pi; }
    public Vector getPi() { return this.pi; }

    public boolean getIsWall() { return this.isWall; }
    public boolean getIsPanel() { return this.isPanel; }
    public int getWhichPanel() { return this.whatPanel; }

    public int getG() { return this.g; }

    public void update(final int delta) { }
}
