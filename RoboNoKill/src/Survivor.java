import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

class Survivor extends Entity {

    Tile where;
    Vector moving, prevMove;

    public Survivor(final float x, final float y, Tile start) {
        super(x, y);
        addImageWithBoundingBox(ResourceManager
                .getImage("Resource/test.png"));
        this.moving = new Vector(0,0);
        this.setWhereYouAt(start);

    }

    // keep track of where you are and which way you are going
    public void setMoving(Vector moving, Tile tile) {

//        // if vector is opposite of last vector
//        if (moving.add(this.moving).length() == 0) {
//            // set value here
//            this.moving = moving;
//            System.out.println("DON'T CLIP " + moving.add(this.moving).length());
//        }

        // if the new vector is different from the last
//        if (this.moving.getX() != moving.getX() || this.moving.getY() != moving.getY()) {
            this.prevMove = this.moving;
            this.moving = moving;
//            System.out.println("CLIP " + moving.add(this.moving).length());
//            this.setPosition(this.where.getX(), this.where.getY());
//        }

        // at this point moving is set to current input
        // grab components of where you are moving
        float moveX = moving.getX();
        float moveY = moving.getY();

        // check if there could be a collision where you are going
        if (tile.getIsWall() == true) {

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

        } else if (this.moving.dot(this.prevMove) == 0) {

            // position this entity more towards tile center for each input
            this.setPosition(this.where.getX(), this.where.getY());
            this.translate(moving);

        }else {
            this.translate(moving);
        }



    }
    public Vector getMoving() { return this.moving; }

    public void setWhereYouAt(Tile tile) { this.where = tile; }
    public Tile whereYouAt() { return this.where; }

    public void update(final int delta) {
    }
}
