
public class Coordinate {
    
    public static final Coordinate DOWN = new Coordinate(0,1);
   
    public static final Coordinate UP = new Coordinate(0,-1);
    
    public static final Coordinate LEFT = new Coordinate(-1,0);
    
    public static final Coordinate RIGHT = new Coordinate(1,0);
    
    public static final Coordinate ZERO = new Coordinate(0,0);

   
    public int x;
    
    public int y;

    
    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    public Coordinate(Coordinate positionToCopy) {
        this.x = positionToCopy.x;
        this.y = positionToCopy.y;
    }

    
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    
    public void add(Coordinate otherPosition) {
        this.x += otherPosition.x;
        this.y += otherPosition.y;
    }

    
    public double distanceTo(Coordinate otherPosition) {
        return Math.sqrt(Math.pow(x-otherPosition.x,2)+Math.pow(y-otherPosition.y,2));
    }

    
    public void multiply(int amount) {
        x *= amount;
        y *= amount;
    }

    
    public void subtract(Coordinate otherPosition) {
        this.x -= otherPosition.x;
        this.y -= otherPosition.y;
    }

    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Coordinate position = (Coordinate) o;
        return x == position.x && y == position.y;
    }

    
    @Override
    public String toString() {
        return "(" + x + ", " + y + ")";
    }
}
