import java.awt.*;
import java.awt.geom.AffineTransform;


 
public class StatusPanel extends Rectangle{
    
    private final Font font = new Font("Dialog", Font.BOLD, 20);
    
    private final String placingShipLine1 = "";
    
    private final String placingShipLine2 = "";
   
    private final String gameOverLossLine = "You Lost ";
   
    private final String gameOverWinLine = "You won!";
    
    private final String gameOverBottomLine = "";

   
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
        g.setFont(new Font(Font.DIALOG, Font.BOLD, 10));
        g.setColor(Color.black);
        
        g.fillRect(position.x, position.y, width, height);
        int strWidth = g.getFontMetrics().stringWidth(topLine);
        strWidth = g.getFontMetrics().stringWidth(bottomLine);
        
        
       
        
        g.drawString(topLine, 305, position.y-250);
        
        g.drawString(bottomLine,305, position.y+100);
         
        
        
        Image backgroundImage = Toolkit.getDefaultToolkit().getImage("F:\\\\xstk\\\\ban tao\\\\Battleship-main\\\\Battleship\\\\img\\\\status.png");
        g.drawImage(backgroundImage, position.x-3, position.y, null);
        
    }
}
