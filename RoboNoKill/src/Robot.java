import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import java.util.Hashtable;

class Robot extends Entity {

    private Vector direction;
    private Tile where;
    private int whatRobo;

    public Robot(final float x, final float y, Tile start, int whatRobo) {
        super(x, y);
        this.whatRobo = whatRobo;
        if (whatRobo == 1) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo1.png"));
        } else if (whatRobo == 2) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo2.png"));
        } else if (whatRobo == 3) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo3.png"));
        }
        this.where = start;
    }

    public void setWhere(Tile where) { this.where = where; }

    public void setDirection(Vector direction) { this.direction = direction; }
    public Vector getDirection() { return this.direction; }

    // check which robot this is, and update this.direction for which robot it is.
    public void checkRoboState(Tile current, Tile [][] mapArray) {

        // 3rd robo roams the map randomly
        if (this.whatRobo == 3) {

            // this sets a random direction that you can go
            this.randomLogic(current, mapArray);

        } else {
            // other robots will chase the survivor for now

            this.setDirection(current.getPi());
        }

    }

    // sets
    private void randomLogic(Tile current, Tile [][] mapArray) {

        Hashtable<Integer, Vector> canGo = new Hashtable<>();

        int count = 1;
        int choice;

        int j = current.getOverlayX();
        int i = current.getOverlayY();

        if (!mapArray[i][j + 1].getIsWall()) { // check left
            canGo.put(count, new Vector(1,0));
            count++;
        }
        if (!mapArray[i][j - 1].getIsWall()) { // check right
            canGo.put(count, new Vector(-1,0));
            count++;
        }
        if (!mapArray[i + 1][j].getIsWall()) { // check down
            canGo.put(count, new Vector(0,1));
            count++;
        }
        if (!mapArray[i - 1][j].getIsWall()) { // check up
            canGo.put(count, new Vector(0,-1));
            count++;
        }

        // get a random number between 1 and count
        choice = (int)(Math.random()*(count-1+1)+1);

        // set the direction to that random choice
        this.setDirection(canGo.get(choice));

    }


    public void update(final int delta) {
        translate(this.direction);
    }
}
