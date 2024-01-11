import java.util.ArrayList;
import java.util.List;


public class BattleshipBot {
    
    protected ShipDefinition playerGrid;
    
    protected List<Coordinate> validMoves;

    
    public BattleshipBot(ShipDefinition playerGrid) {
        this.playerGrid = playerGrid;
        createValidMoveList();
    }

    
    
    public Coordinate selectMove() {
        return Coordinate.ZERO;
    }

    
    public void reset() {
        createValidMoveList();
    }

    
    private void createValidMoveList() {
        validMoves = new ArrayList<>();
        for(int x = 0; x < ShipDefinition.GRID_WIDTH; x++) {
            for(int y = 0; y < ShipDefinition.GRID_HEIGHT; y++) {
                validMoves.add(new Coordinate(x,y));
            }
        }
    }
}
