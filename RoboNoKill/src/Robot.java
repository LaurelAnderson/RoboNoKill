import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import java.util.Hashtable;

class Robot extends Entity {

    private int minTime, maxTime;
    private Vector direction;
    private Tile where;
    private final int whatRobo;
    private int timer;
    private boolean roaming;
    private boolean stunned;

    public Robot(final float x, final float y, Tile start, int whatRobo) {
        super(x, y);
        this.whatRobo = whatRobo;
        if (whatRobo == 1) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo1.png"));
        } else if (whatRobo == 2) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo2.png"));
            this.maxTime = timer = 2000;
        } else if (whatRobo == 3) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo3.png"));
            this.maxTime = timer = 1000;
        }
        this.where = start;
    }

    public void setWhere(Tile where) { this.where = where; }

    public void setDirection(Vector direction) { this.direction = direction; }
    public Vector getDirection() { return this.direction; }

    public void setStunned() { this.stunned = true; }

    // check which robot this is, and update this.direction for which robot it is.
    public void checkRoboState(Tile current, Tile [][] mapArray) {
        if (this.roaming) {
            this.randomLogic(current, mapArray);
        } else {
            this.setDirection(current.getPi());
        }
    }

    // sets the roaming logic for the robot
    private void randomLogic(Tile current, Tile [][] mapArray) {

        Hashtable<Integer, Vector> canGo = new Hashtable<>();

        Vector prevDir = this.direction;

        int count = 0;
        int choice;

        int j = current.getOverlayX();
        int i = current.getOverlayY();

        if (!mapArray[i][j + 1].getIsWall()) { // check left
            canGo.put(++count, new Vector(1,0));
        }
        if (!mapArray[i][j - 1].getIsWall()) { // check right
            canGo.put(++count, new Vector(-1,0));
        }
        if (!mapArray[i + 1][j].getIsWall()) { // check down
            canGo.put(++count, new Vector(0,1));
        }
        if (!mapArray[i - 1][j].getIsWall()) { // check up
            canGo.put(++count, new Vector(0,-1));
        }

        // get a random number between 1 and count
        choice = (int)(Math.random()*(count-1+1)+1);

        // If you are going opposite direction that you were, re-roll.
        if (canGo.get(choice).add(prevDir).getX() == 0 && canGo.get(choice).add(prevDir).getY() == 0 &&
                canGo.size() > 1) {
            choice = (int)(Math.random()*(count-1+1)+1);
        }

        // set the direction to that random choice
        this.setDirection(canGo.get(choice));

    }


    public void update(final int delta) {

        this.timer -= delta;

        if (this.stunned) {
            translate(new Vector(0,0));

        } else {
            if (this.whatRobo != 1 && this.timer <= 0) {
                this.roaming = !this.roaming;
                this.timer = this.maxTime;
            }
            translate(this.direction);
        }
    }
}
