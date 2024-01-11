import java.awt.*;

import javax.swing.ImageIcon;


 
public class StatusPanel extends Rectangle{
    
    private final Font font = new Font("Arial", Font.BOLD, 20);
    
    private final String placingShipLine1 = "You have 5 ships. Place yours!";
    
    private final String placingShipLine2 = "";
   
    private final String gameOverLossLine = "Game Over! You Lost ";
   
    private final String gameOverWinLine = "You won!";
    
    private final String gameOverBottomLine = "Press R to restart.";

   
    private String topLine;
    
    private String bottomLine;

    

   
    public StatusPanel(Coordinate position, int width, int height) {
        super(position, width, height);
        reset();
    }

    
    public void reset() {
        topLine = placingShipLine1;
        bottomLine = placingShipLine2;
    }

   
    public void showGameOver(boolean playerWon) {
        topLine = (playerWon) ? gameOverWinLine : gameOverLossLine;
        bottomLine = gameOverBottomLine;
    }

    
    public void setTopLine(String message) {
        topLine = message;
    }

    
    public void setBottomLine(String message) {
        bottomLine = message;
    }

    
    public void paint(Graphics g) {
        
        g.setColor(new Color(70,113,255));
        
        g.fillRect(position.x, position.y, width, height);
        
        g.setFont(new Font(Font.SERIF, Font.BOLD, 20));
        int strWidth = g.getFontMetrics().stringWidth(topLine);
        g.drawString(topLine, position.x+width/2-strWidth/2, position.y+20);
        strWidth = g.getFontMetrics().stringWidth(bottomLine);
        g.drawString(bottomLine, position.x+width/2-strWidth/2, position.y+40);
        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("F:\\\\xstk\\\\ban tao\\\\Battleship-main\\\\Battleship\\\\img\\\\status.png");
        g.drawImage(backgroundImage, position.x-3, position.y, null);
        
    }
}
