import java.util.Collections;



public class EasyBot extends BattleshipBot{
    
    public EasyBot(ShipDefinition playerGrid) {
        super(playerGrid);
        Collections.shuffle(validMoves);
    }

    
    @Override
    public void reset() {
        super.reset();
        Collections.shuffle(validMoves);
    }

    /**
     * Lay cai move dau tien trong queue
     *
     */
    @Override
    public Coordinate selectMove() {
        Coordinate nextMove = validMoves.get(0);
        validMoves.remove(0);
        return nextMove;
    }
}
