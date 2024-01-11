import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class HardBot extends BattleshipBot {
    
    private List<Coordinate> shipHits;
    
    private final boolean debugAI = false;
    
    private boolean preferMovesFormingLine;
    
    private boolean maximiseAdjacentRandomisation;

    
    public HardBot(ShipDefinition playerGrid, boolean preferMovesFormingLine, boolean maximiseAdjacentRandomisation) {
        super(playerGrid);
        shipHits = new ArrayList<>();
        this.preferMovesFormingLine = preferMovesFormingLine;
        this.maximiseAdjacentRandomisation = maximiseAdjacentRandomisation;
        Collections.shuffle(validMoves);
    }

    
    @Override
    public void reset() {
        super.reset();
        shipHits.clear();
        Collections.shuffle(validMoves);
    }

    
    @Override
    public Coordinate selectMove() {
        if(debugAI) System.out.println("\nBEGIN TURN===========");
        Coordinate selectedMove;
        // If a ship has been hit, but not destroyed
        if(shipHits.size() > 0) {
            if(preferMovesFormingLine) {
                selectedMove = getSmarterAttack();
            } else {
                selectedMove = getSmartAttack();
            }
        } else {
            if(maximiseAdjacentRandomisation) {
                selectedMove = findMostOpenPosition();
            } else {
                // Use a random move
                selectedMove = validMoves.get(0);
            }
        }
        updateShipHits(selectedMove);
        validMoves.remove(selectedMove);
        if(debugAI) {
            System.out.println("Selected Move: " + selectedMove);
            System.out.println("END TURN===========");
        }
        return selectedMove;
    }

    
    private Coordinate getSmartAttack() {
        List<Coordinate> suggestedMoves = getAdjacentSmartMoves();
        Collections.shuffle(suggestedMoves);
        return  suggestedMoves.get(0);
    }


    
    private Coordinate getSmarterAttack() {
        List<Coordinate> suggestedMoves = getAdjacentSmartMoves();
        for(Coordinate possibleOptimalMove : suggestedMoves) {
            if(atLeastTwoHitsInDirection(possibleOptimalMove,Coordinate.LEFT)) return possibleOptimalMove;
            if(atLeastTwoHitsInDirection(possibleOptimalMove,Coordinate.RIGHT)) return possibleOptimalMove;
            if(atLeastTwoHitsInDirection(possibleOptimalMove,Coordinate.DOWN)) return possibleOptimalMove;
            if(atLeastTwoHitsInDirection(possibleOptimalMove,Coordinate.UP)) return possibleOptimalMove;
        }
        // No optimal choice found, just randomise the move.
        Collections.shuffle(suggestedMoves);
        return  suggestedMoves.get(0);
    }

    
    private Coordinate findMostOpenPosition() {
        Coordinate position = validMoves.get(0);;
        int highestNotAttacked = -1;
        for(int i = 0; i < validMoves.size(); i++) {
            int testCount = getAdjacentNotAttackedCount(validMoves.get(i));
            if(testCount == 4) { // Maximum found, just return immediately
                return validMoves.get(i);
            } else if(testCount > highestNotAttacked) {
                highestNotAttacked = testCount;
                position = validMoves.get(i);
            }
        }
        return position;
    }

    
    private int getAdjacentNotAttackedCount(Coordinate position) {
        List<Coordinate> adjacentCells = getAdjacentCells(position);
        int notAttackedCount = 0;
        for(Coordinate adjacentCell : adjacentCells) {
            if(!playerGrid.getMarkerAtPosition(adjacentCell).isMarked()) {
                notAttackedCount++;
            }
        }
        return notAttackedCount;
    }

    
    private boolean atLeastTwoHitsInDirection(Coordinate start, Coordinate direction) {
        Coordinate testPosition = new Coordinate(start);
        testPosition.add(direction);
        if(!shipHits.contains(testPosition)) return false;
        testPosition.add(direction);
        if(!shipHits.contains(testPosition)) return false;
        if(debugAI) System.out.println("Smarter match found AT: " + start + " TO: " + testPosition);
        return true;
    }

    
    private List<Coordinate> getAdjacentSmartMoves() {
        List<Coordinate> result = new ArrayList<>();
        for(Coordinate shipHitPos : shipHits) {
            List<Coordinate> adjacentPositions = getAdjacentCells(shipHitPos);
            for(Coordinate adjacentPosition : adjacentPositions) {
                if(!result.contains(adjacentPosition) && validMoves.contains(adjacentPosition)) {
                    result.add(adjacentPosition);
                }
            }
        }
        if(debugAI) {
            printPositionList("Ship Hits: ", shipHits);
            printPositionList("Adjacent Smart Moves: ", result);
        }
        return result;
    }

    
    private void printPositionList(String messagePrefix, List<Coordinate> data) {
        String result = "[";
        for(int i = 0; i < data.size(); i++) {
            result += data.get(i);
            if(i != data.size()-1) {
                result += ", ";
            }
        }
        result += "]";
        System.out.println(messagePrefix + " " + result);
    }

    
    private List<Coordinate> getAdjacentCells(Coordinate position) {
        List<Coordinate> result = new ArrayList<>();
        if(position.x != 0) {
            Coordinate left = new Coordinate(position);
            left.add(Coordinate.LEFT);
            result.add(left);
        }
        if(position.x != ShipDefinition.GRID_WIDTH-1) {
            Coordinate right = new Coordinate(position);
            right.add(Coordinate.RIGHT);
            result.add(right);
        }
        if(position.y != 0) {
            Coordinate up = new Coordinate(position);
            up.add(Coordinate.UP);
            result.add(up);
        }
        if(position.y != ShipDefinition.GRID_HEIGHT-1) {
            Coordinate down = new Coordinate(position);
            down.add(Coordinate.DOWN);
            result.add(down);
        }
        return result;
    }

    
    private void updateShipHits(Coordinate testPosition) {
        StatusPoint marker = playerGrid.getMarkerAtPosition(testPosition);
        if(marker.isShip()) {
            shipHits.add(testPosition);
            // Check to find if this was the last place to hit on the targeted ship
            List<Coordinate> allPositionsOfLastShip = marker.getAssociatedShip().getOccupiedCoordinates();
            if(debugAI) printPositionList("Last Ship", allPositionsOfLastShip);
            boolean hitAllOfShip = containsAllPositions(allPositionsOfLastShip, shipHits);
            // If it was remove the ship data from history to now ignore it
            if(hitAllOfShip) {
                for(Coordinate shipPosition : allPositionsOfLastShip) {
                    for(int i = 0; i < shipHits.size(); i++) {
                        if(shipHits.get(i).equals(shipPosition)) {
                            shipHits.remove(i);
                            if(debugAI) System.out.println("Removed " + shipPosition);
                            break;
                        }
                    }
                }
            }
        }
    }

    
    private boolean containsAllPositions(List<Coordinate> positionsToSearch, List<Coordinate> listToSearchIn) {
        for(Coordinate searchPosition : positionsToSearch) {
            boolean found = false;
            for(Coordinate searchInPosition : listToSearchIn) {
                if(searchInPosition.equals(searchPosition)) {
                    found = true;
                    break;
                }
            }
            if(!found) return false;
        }
        return true;
    }
}
