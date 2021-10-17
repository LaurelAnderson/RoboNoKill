import jig.Entity;
import jig.ResourceManager;
import jig.Vector;

import java.util.Hashtable;

class Robot extends Entity {

    private Vector direction;
    private Tile where;
    private final int whatRobo;
    private int timer = 1000, maxRoamTime, maxStalkTime;
    private boolean roaming;
    private boolean stalking;

    public Robot(final float x, final float y, Tile start, int whatRobo) {
        super(x, y);
        this.whatRobo = whatRobo;
        if (whatRobo == 1) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo1.png"));
        } else if (whatRobo == 2) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo2.png"));
            this.maxRoamTime = 2000;
            this.maxStalkTime = 2000;
        } else if (whatRobo == 3) {
            addImageWithBoundingBox(ResourceManager
                    .getImage("Resource/Robo3.png"));
            this.maxRoamTime = 2000;
            this.maxStalkTime = 1000;
        }
        this.where = start;
    }

    public void setWhere(Tile where) { this.where = where; }

    public void setDirection(Vector direction) { this.direction = direction; }
    public Vector getDirection() { return this.direction; }

    // check which robot this is, and update this.direction for which robot it is.
    public void checkRoboState(Tile current, Tile [][] mapArray) {

        // 3rd robo roams the map randomly
        if (this.roaming) {
            this.randomLogic(current, mapArray);
        } else {
            this.setDirection(current.getPi());
        }
//        if (this.whatRobo == 3) {
//
//            // this sets a random direction that you can go
//            this.randomLogic(current, mapArray);
//
//        } else if (this.whatRobo == 2) {
//            // other robots will chase the survivor for now
//            this.setDirection(new Vector(0, 0));
//        } else {
//            this.setDirection(current.getPi());
//        }

    }

    // sets
    private void randomLogic(Tile current, Tile [][] mapArray) {

        Hashtable<Integer, Vector> canGo = new Hashtable<>();

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

        System.out.println(canGo.toString());

        // get a random number between 1 and count
        choice = (int)(Math.random()*(count-1+1)+1);

        System.out.println(choice);
        System.out.println(count);

        // set the direction to that random choice
        this.setDirection(canGo.get(choice));

    }


    public void update(final int delta) {

        this.timer -= delta;

        if (this.whatRobo == 3) {
            if (this.timer <= 0) {
                this.roaming = !this.roaming;
                this.timer = this.maxStalkTime;
            }
        }
        if (this.whatRobo == 2) {
            if (this.timer <= 0) {
                this.roaming = !this.roaming;
                this.timer = this.maxStalkTime;
            }
        }

        translate(this.direction);
    }
}
