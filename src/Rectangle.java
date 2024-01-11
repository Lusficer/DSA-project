
public class Rectangle {
    
    protected Coordinate position;
    
    protected int width;
    
    protected int height;

    
    public Rectangle(Coordinate position, int width, int height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    
    public Rectangle(int x, int y, int width, int height) {
        this(new Coordinate(x,y),width,height);
    }

    
    public int getHeight() {
        return height;
    }

    
    public int getWidth() {
        return width;
    }

    
    public Coordinate getPosition() {
        return position;
    }

    
    public boolean isPositionInside(Coordinate targetPosition) {
        return targetPosition.x >= position.x && targetPosition.y >= position.y
                && targetPosition.x < position.x + width && targetPosition.y < position.y + height;
    }
}
