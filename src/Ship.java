import java.awt.*;
import java.util.ArrayList;
import java.util.List;


public class Ship {
    
    public enum ShipPlacementColour {Valid, Invalid, Placed}

    
    private Coordinate gridPosition;
    
    private Coordinate drawPosition;
    
    private int segments;
    
    private boolean isSideways;
    
    private int destroyedSections;
    
    private ShipPlacementColour shipPlacementColour;

   
    public Ship(Coordinate gridPosition, Coordinate drawPosition, int segments, boolean isSideways) {
        this.gridPosition = gridPosition;
        this.drawPosition = drawPosition;
        this.segments = segments;
        this.isSideways = isSideways;
        destroyedSections = 0;
        shipPlacementColour = ShipPlacementColour.Placed;
    }

   
    public void paint(Graphics g) {
        if(shipPlacementColour == ShipPlacementColour.Placed) {
            g.setColor(destroyedSections >= segments ? Color.RED : Color.BLACK);
        } else {
            g.setColor(shipPlacementColour == ShipPlacementColour.Valid ? Color.YELLOW : Color.RED);
        }
        if(isSideways) paintHorizontal(g);
        else paintVertical(g);
    }

    
    public void setShipPlacementColour(ShipPlacementColour shipPlacementColour) {
        this.shipPlacementColour = shipPlacementColour;
    }

    
    public void toggleSideways() {
        isSideways = !isSideways;
    }

    
    public void destroySection() {
        destroyedSections++;
    }

    
    public boolean isDestroyed() { return destroyedSections >= segments; }

   
    public void setDrawPosition(Coordinate gridPosition, Coordinate drawPosition) {
        this.drawPosition = drawPosition;
        this.gridPosition = gridPosition;
    }

    
    public boolean isSideways() {
        return isSideways;
    }

    
    public int getSegments() {
        return segments;
    }

    
    public List<Coordinate> getOccupiedCoordinates() {
        List<Coordinate> result = new ArrayList<>();
        if(isSideways) { // handle the case when horizontal
            for(int x = 0; x < segments; x++) {
                result.add(new Coordinate(gridPosition.x+x, gridPosition.y));
            }
        } else { // handle the case when vertical
            for(int y = 0; y < segments; y++) {
                result.add(new Coordinate(gridPosition.x, gridPosition.y+y));
            }
        }
        return result;
    }

    
    public void paintVertical(Graphics g) {
        int boatWidth = (int)(ShipDefinition.CELL_SIZE * 0.8);
        int boatLeftX = drawPosition.x + ShipDefinition.CELL_SIZE / 2 - boatWidth / 2;
        g.fillPolygon(new int[]{drawPosition.x+ShipDefinition.CELL_SIZE/2,boatLeftX,boatLeftX+boatWidth},
                new int[]{drawPosition.y+ShipDefinition.CELL_SIZE/4,drawPosition.y+ShipDefinition.CELL_SIZE,drawPosition.y+ShipDefinition.CELL_SIZE},3);
        g.fillRect(boatLeftX,drawPosition.y+ShipDefinition.CELL_SIZE, boatWidth,
                (int)(ShipDefinition.CELL_SIZE * (segments-1.2)));
    }

    
    public void paintHorizontal(Graphics g) {
        int boatWidth = (int)(ShipDefinition.CELL_SIZE * 0.8);
        int boatTopY = drawPosition.y + ShipDefinition.CELL_SIZE / 2 - boatWidth / 2;
         g.fillPolygon(new int[]{drawPosition.x+ShipDefinition.CELL_SIZE/6,drawPosition.x+ShipDefinition.CELL_SIZE,drawPosition.x+ShipDefinition.CELL_SIZE},
                  new int[]{drawPosition.y+ShipDefinition.CELL_SIZE/4,boatTopY,boatTopY+boatWidth},3);
        g.fillRect(drawPosition.x+ShipDefinition.CELL_SIZE,boatTopY,
                (int)(ShipDefinition.CELL_SIZE * (segments-1.2)), boatWidth);
        g.setColor(Color.BLACK);
        g.drawPolygon(new int[]{drawPosition.x+ShipDefinition.CELL_SIZE/6,drawPosition.x+ShipDefinition.CELL_SIZE,drawPosition.x+ShipDefinition.CELL_SIZE},
                  new int[]{drawPosition.y+ShipDefinition.CELL_SIZE/4,boatTopY,boatTopY-2+boatWidth},3);;
        g.setColor(Color.BLACK);
        g.drawRect(drawPosition.x+ShipDefinition.CELL_SIZE,boatTopY,
                (int)(ShipDefinition.CELL_SIZE * (segments-1.2)), boatWidth);
    }
}
