import javax.swing.*;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.Image;
/**
 
 * Game class:
 * Tao Jframe add Jpanel
 */
public class Game implements KeyListener, ActionListener {
    
    public static void main(String[] args) {
        Game game = new Game();
    }

    
    private GamePanel gamePanel;
    public int level;
    public JFrame frame;
    public JPanel contain;
    public JButton easy, medium, hard;
    JLabel background;
    
    public Game() {
        setupMenu();
    }
        public void setupMenu(){
            frame = new JFrame("Battleship");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setResizable(false);
            frame.setSize(400,689);
            frame.setLocationRelativeTo(null);

            
            ImageIcon backgroundimg = new ImageIcon("F:\\xstk\\ban tao\\Battleship-main\\Battleship 2.0\\img\\background(1).jpg");
            background = new JLabel("",backgroundimg,JLabel.CENTER);
            background.setBounds(0,0,389,689);

            contain = new JPanel();
            contain.setBackground(Color.blue);
            contain.setLayout(null);
            
        easy = new JButton("Easy");
        easy.setFont(new Font("Algerian", Font.BOLD, 35));
        
        easy.setForeground(Color.yellow);
        easy.setFocusable(false);
        easy.setSize(150,35);
        easy.setLocation(110, 350);
        easy.setBorderPainted(false); 
        easy.setContentAreaFilled(false); 
        easy.setFocusPainted(false); 
        easy.setOpaque(false);
        
       

        medium = new JButton("Medium");
        medium.setFocusable(false);
        medium.setSize(150,35);
        medium.setLocation(110, 450);
        medium.setFont(new Font("Algerian", Font.BOLD, 30));
        medium.setForeground(new Color(255,119,0));
        medium.setFocusable(false);
        medium.setBorderPainted(false); 
        medium.setContentAreaFilled(false); 
        medium.setFocusPainted(false); 
        medium.setOpaque(false);
        
        hard = new JButton("Hard");
        hard.setFocusable(false);
        hard.setSize(150,35);
        hard.setLocation(110, 550);
        hard.setFont(new Font("Algerian", Font.BOLD, 35));
        hard.setForeground(Color.red);
        hard.setFocusable(false);
        hard.setBorderPainted(false); 
        hard.setContentAreaFilled(false); 
        hard.setFocusPainted(false); 
        hard.setOpaque(false);
        
        frame.add(contain);
        contain.add(easy);
        contain.add(medium);
        contain.add(hard);
        contain.add(background);
        easy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                
                contain.setVisible(false);
                setLevel(0);
                startGame();
                
                
                
                
                
                
                

            }

        });
         medium.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                
                contain.setVisible(false);
                
                
                
                
                setLevel(1);
                //System.out.println( level);
                startGame();
                
                
                
                
                
               
              

            }

        });
        hard.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e){
                contain.setVisible(false);
                
                setLevel(2);
                 System.out.println(level);
                startGame();
                
                

            }

        });

           

            

    
            
            //startGame();
            frame.addKeyListener(this);
            //frame.pack();
            frame.setVisible(true);
            //gamePanel.setVisible(false);
        }
    public void startGame(){
        gamePanel = new GamePanel(getLevel());
        frame.getContentPane().add(gamePanel);
        gamePanel.setLayout(null);
        
    }   
        
    public void setLevel(int levell) {
        this.level = levell;
        
    }
    public int getLevel(){
        return this.level;
    }
       

    /**
     * @param e Information about what key was pressed.
     */
    @Override
    public void keyPressed(KeyEvent e) {
        gamePanel.handleInput(e.getKeyCode());
    }

    /**
     * Not used.
     *
     * @param e Not used.
     */
    @Override
    public void keyTyped(KeyEvent e) {}
    /**
     * Not used.
     *
     * @param e Not used.
     */
    @Override
    public void keyReleased(KeyEvent e) {}
    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'actionPerformed'");
    }
}