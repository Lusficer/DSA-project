import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

/**

 *
 * 
 * 
 */
public class GamePanel extends JPanel implements MouseListener, MouseMotionListener {
    
    public enum GameState { PlacingShips, FiringShots, GameOver }


    
    private StatusPanel statusPanel;
    
    private ShipDefinition computer;
    
    private ShipDefinition player;
   
    private BattleshipBot aiController;

   
    private Ship placingShip;
    
    private Coordinate tempPlacingPosition;
    
    private int placingShipIndex;
    
    private GameState gameState;
    
    public static boolean debugModeActive;
    JLabel background, backgroundstatus;
    
    public GamePanel(int aiChoice) {
        computer = new ShipDefinition(0,0);
        player = new ShipDefinition(0,computer.getHeight()+50);
        ImageIcon backgroundimg = new ImageIcon("F:\\xstk\\ban tao\\Battleship-main\\Battleship 2.0\\img\\sea (1).png");
        background = new JLabel("",backgroundimg,JLabel.CENTER);
        background.setBounds(0,0,300,689);
        
        this.add(background);
        //this.add(backgroundstatus);       
        setPreferredSize(new Dimension(computer.getWidth(), player.getPosition().y + player.getHeight()));
        addMouseListener(this);
        addMouseMotionListener(this);
        if(aiChoice == 0) aiController = new EasyBot(player);
        else aiController = new HardBot(player,aiChoice == 2,aiChoice == 2);
        statusPanel = new StatusPanel(new Coordinate(0,computer.getHeight()+1),computer.getWidth(),49);
        restart();
        
        
        
    }

    
    public void paint(Graphics g) {
        super.paint(g);
        computer.paint(g);
        player.paint(g);
        if(gameState == GameState.PlacingShips) {
            placingShip.paint(g);
        }
        statusPanel.paint(g);
        
    }

    
    public void handleInput(int keyCode) {
        if(keyCode == KeyEvent.VK_ESCAPE) {
            System.exit(1);
        } else if(keyCode == KeyEvent.VK_R) {
            restart();
        } else if(gameState == GameState.PlacingShips && keyCode == KeyEvent.VK_SPACE) {
            placingShip.toggleSideways();
            updateShipPlacement(tempPlacingPosition);
        } else if(keyCode == KeyEvent.VK_D) {
            debugModeActive = !debugModeActive;
        }
        repaint();
    }

    
    public void restart() {
        computer.reset();
        player.reset();
        // Player can see their own ships by default
        player.setShowShips(true);
        aiController.reset();
        tempPlacingPosition = new Coordinate(0,0);
        placingShip = new Ship(new Coordinate(0,0),
                               new Coordinate(player.getPosition().x,player.getPosition().y),
                               ShipDefinition.BOAT_SIZES[0], true);
        placingShipIndex = 0;
        updateShipPlacement(tempPlacingPosition);
        computer.populateShips();
        debugModeActive = false;
        statusPanel.reset();
        gameState = GameState.PlacingShips;
    }

    
    private void tryPlaceShip(Coordinate mousePosition) {
        Coordinate targetPosition = player.getPositionInGrid(mousePosition.x, mousePosition.y);
        updateShipPlacement(targetPosition);
        if(player.canPlaceShipAt(targetPosition.x, targetPosition.y,
                ShipDefinition.BOAT_SIZES[placingShipIndex],placingShip.isSideways())) {
            placeShip(targetPosition);
        }
    }

    
    private void placeShip(Coordinate targetPosition) {
        placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Placed);
        player.placeShip(placingShip,tempPlacingPosition.x,tempPlacingPosition.y);
        placingShipIndex++;
        // If there are still ships to place
        if(placingShipIndex < ShipDefinition.BOAT_SIZES.length) {
            placingShip = new Ship(new Coordinate(targetPosition.x, targetPosition.y),
                          new Coordinate(player.getPosition().x + targetPosition.x * ShipDefinition.CELL_SIZE,
                       player.getPosition().y + targetPosition.y * ShipDefinition.CELL_SIZE),
                          ShipDefinition.BOAT_SIZES[placingShipIndex], true);
            updateShipPlacement(tempPlacingPosition);
        } else {
            gameState = GameState.FiringShots;
            statusPanel.setTopLine("Bot Board");
            statusPanel.setBottomLine("Player Board");
        }
    }

    
    private void tryFireAtComputer(Coordinate mousePosition) {
        Coordinate targetPosition = computer.getPositionInGrid(mousePosition.x,mousePosition.y);
        // Ignore if position was already clicked
        if(!computer.isPositionMarked(targetPosition)) {
            doPlayerTurn(targetPosition);
            // Only do the AI turn if the game didn't end from the player's turn.
            if(!computer.areAllShipsDestroyed()) {
                doAITurn();
            }
        }
    }

    
    private void doPlayerTurn(Coordinate targetPosition) {
        boolean hit = computer.markPosition(targetPosition);
        String hitMiss = hit ? "Hit" : "Missed";
        String destroyed = "";
        if(hit && computer.getMarkerAtPosition(targetPosition).getAssociatedShip().isDestroyed()) {
            destroyed = "(Destroyed)";
        }
        statusPanel.setTopLine("P:" + hitMiss + " " + targetPosition + destroyed);
        if(computer.areAllShipsDestroyed()) {
            // Player wins!
            gameState = GameState.GameOver;
            statusPanel.showGameOver(true);
        }
    }

    
    private void doAITurn() {
        Coordinate aiMove = aiController.selectMove();
        boolean hit = player.markPosition(aiMove);
        String hitMiss = hit ? "Hit" : "Missed";
        String destroyed = "";
        if(hit && player.getMarkerAtPosition(aiMove).getAssociatedShip().isDestroyed()) {
            destroyed = "(Destroyed)";
        }
        statusPanel.setBottomLine("C:" + hitMiss + " " + aiMove + destroyed);
        if(player.areAllShipsDestroyed()) {
            // Computer wins!
            gameState = GameState.GameOver;
            statusPanel.showGameOver(false);
        }
    }

    
    private void tryMovePlacingShip(Coordinate mousePosition) {
        if(player.isPositionInside(mousePosition)) {
            Coordinate targetPos = player.getPositionInGrid(mousePosition.x, mousePosition.y);
            updateShipPlacement(targetPos);
        }
    }

    
    private void updateShipPlacement(Coordinate targetPos) {
        // Constrain to fit inside the grid
        if(placingShip.isSideways()) {
            targetPos.x = Math.min(targetPos.x, ShipDefinition.GRID_WIDTH - ShipDefinition.BOAT_SIZES[placingShipIndex]);
        } else {
            targetPos.y = Math.min(targetPos.y, ShipDefinition.GRID_HEIGHT - ShipDefinition.BOAT_SIZES[placingShipIndex]);
        }
        // Update drawing position to use the new target position
        placingShip.setDrawPosition(new Coordinate(targetPos),
                                    new Coordinate(player.getPosition().x + targetPos.x * ShipDefinition.CELL_SIZE,
                                 player.getPosition().y + targetPos.y * ShipDefinition.CELL_SIZE));
        // Store the grid position for other testing cases
        tempPlacingPosition = targetPos;
        // Change the colour of the ship based on whether it could be placed at the current location.
        if(player.canPlaceShipAt(tempPlacingPosition.x, tempPlacingPosition.y,
                ShipDefinition.BOAT_SIZES[placingShipIndex],placingShip.isSideways())) {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Valid);
        } else {
            placingShip.setShipPlacementColour(Ship.ShipPlacementColour.Invalid);
        }
    }

    
    @Override
    public void mouseReleased(MouseEvent e) {
        Coordinate mousePosition = new Coordinate(e.getX(), e.getY());
        if(gameState == GameState.PlacingShips && player.isPositionInside(mousePosition)) {
            tryPlaceShip(mousePosition);
        } else if(gameState == GameState.FiringShots && computer.isPositionInside(mousePosition)) {
            tryFireAtComputer(mousePosition);
        }
        repaint();
    }

    
    @Override
    public void mouseMoved(MouseEvent e) {
        if(gameState != GameState.PlacingShips) return;
        tryMovePlacingShip(new Coordinate(e.getX(), e.getY()));
        repaint();
    }

    
    @Override
    public void mouseClicked(MouseEvent e) {}
    /**
     * Not used.
     *
     * @param e Not used.
     */
    @Override
    public void mousePressed(MouseEvent e) {}
    /**
     * Not used.
     *
     * @param e Not used.
     */
    @Override
    public void mouseEntered(MouseEvent e) {}
    /**
     * Not used.
     *
     * @param e Not used.
     */
    @Override
    public void mouseExited(MouseEvent e) {}
    /**
     * Not used.
     *
     * @param e Not used.
     */
    @Override
    public void mouseDragged(MouseEvent e) {}
}
