import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Survivor extends Entity {

    Tile where, prevWhere;
    Vector moving;
    Vector prevMove;

    public Survivor(final float x, final float y, Tile start) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage("Resource/test.png"));
        this.moving = new Vector(0,0);
        this.setWhereYouAt(start);

    }

    // keep track of where you are and which way you are going
    public void setMoving(Vector moving, Tile tile) {

        // keep track of moves made
        this.prevMove = this.moving;
        this.moving = moving;

        float moveX = moving.getX();
        float moveY = moving.getY();

        // check if there could be a collision where you are going
        if (tile.getIsWall()) {

//            System.out.println(moveX + " " + moveY);

            // if there could be a collision, where?
            if (moveX > 0 && moveY == 0 && this.getCoarseGrainedMaxX() >= tile.getCoarseGrainedMinX() ||
                    moveX < 0 && moveY == 0 && this.getCoarseGrainedMinX() <= tile.getCoarseGrainedMaxX()) {
                // moving right or left
                this.setPosition(this.where.getX(), this.getY());
            } else if (moveX == 0 && moveY > 0 && this.getCoarseGrainedMaxY() >= tile.getCoarseGrainedMinY() ||
                    moveX == 0 && moveY < 0 && this.getCoarseGrainedMinY() <= tile.getCoarseGrainedMaxY()) {
                // moving up or down
                this.setPosition(this.getX(), this.where.getY());
            } else {
                this.translate(moving);
            }

        } else {

            // may be able to refine this slightly, but basic logic is alright
            // check if you are moving to a perpendicular tile
            if (this.moving.dot(this.prevMove) == 0) {
                this.setPosition(this.where.getX(), this.where.getY());
            }
            this.translate(moving);
        }



    }
    public Vector getMoving() { return this.moving; }

    public void setWhereYouAt(Tile tile) { this.where = tile; }
    public Tile whereYouAt() { return this.where; }

    public void setWhereYouWere(Tile prevWhere) { this.prevWhere = prevWhere; }
    public Tile getPrevWhere() { return this.prevWhere; }

    public void update(final int delta) {
    }
}
