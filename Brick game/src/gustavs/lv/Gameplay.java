package gustavs.lv;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.net.URL;

import javax.swing.Timer;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Gameplay extends JPanel implements KeyListener, ActionListener{
private boolean play = false;
private int score = 0;

private int totalBricks = 35;

private Timer timer;
private int delay = 8;

private int playerX =310;

private int ballposX=120;
private int ballposY=350;
private int ballXider= -1;
private int ballYider= -2;

private MapGenerator map;

public Gameplay() {
	map = new MapGenerator(5, 7);
	addKeyListener(this);
	setFocusable(true);
	setFocusTraversalKeysEnabled(false);
	timer = new Timer(delay, this);
	timer.start();
}

public void paint(Graphics g) {
	// background
	g.setColor(Color.black);
	g.fillRect(1, 1, 700, 600);
	// Drawing map
	map.draw((Graphics2D)g);
	
	//borders
	g.setColor(Color.yellow);
	g.fillRect(0, 0, 3, 597); 
	g.fillRect(0, 0, 697,3);
	g.fillRect(691,0,3,597);
	
	// scores
	g.setColor(Color.white);
	g.setFont(new Font("serif",Font.BOLD,25));
	g.drawString(""+score, 590, 30);
	
	//the paddle
	g.setColor(Color.green);
	g.fillRect(playerX, 550, 100, 8);
	
	//the ball
	g.setColor(Color.yellow);
	g.fillOval(ballposX, ballposY, 20, 20);
	
	if(totalBricks<=0) {
		play=false;
		ballXider= 0;
		ballYider= 0;
		g.setColor(Color.red);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("You Won", 190, 250);
		
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Press Enter to Restart ", 190, 300);
		
	}
	
	if (ballposY>570) {
		play=false;
		ballXider= 0;
		ballYider= 0;
		g.setColor(Color.red);
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Game Over, Score: " + score, 190, 300);
		
		g.setFont(new Font("serif",Font.BOLD,25));
		g.drawString("Press Enter to Restart ", 190, 350);
		
	}
	
	g.dispose();
}
	@Override
	public void actionPerformed(ActionEvent e) {
		timer.start();
		if(play) {
	
		if(new Rectangle(ballposX,ballposY,20,20).intersects(new Rectangle(playerX,550,100,8))) {
			ballYider = -ballYider;
		}
		
		
	A:	for(int i=0;i<map.map.length;i++) {
		for (int j=0;j<map.map[0].length;j++) {
		if (map.map[i][j]>0) {
			int brickX =j*map.brickWidht+80;
			int brickY =i*map.brickHeight+50;
			int brickWidht =map.brickWidht;
			int brickHeight =map.brickHeight;
		
		
			
			Rectangle rect = new Rectangle(brickX,brickY,brickWidht,brickHeight);
			Rectangle ballRect = new Rectangle(ballposX,ballposY,20,20);
			Rectangle brickRect =rect;
			
			if(ballRect.intersects(brickRect)) {
				map.setBrickValue(0, i, j);
				totalBricks--;
				score += 5;
				
				if(ballposX + 19 <= brickRect.x||ballposX+1>=brickRect.x+brickRect.width) {
					ballXider = -ballXider;
					
				} else {
					ballYider = -ballYider;
	            }
				break A;	
			}
		}
		}
	}			ballposX += ballXider;
			ballposY += ballYider;

			
			if (ballposX < 0) {
				ballXider = -ballXider;
			}
			if (ballposY < 0) {
				ballYider = -ballYider;
			}
			if (ballposX > 670) {
				ballXider = -ballXider; 
				}
			
	
	repaint();
		}
	
	
		
	}
	

		
	

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
		if(playerX >=585) {
			playerX = 585;
		}else{
			moveRight();
		}
		}
	if (e.getKeyCode() == KeyEvent.VK_LEFT) {
		if(playerX <=10) {
			playerX = 10;
		} else {
				moveLeft();
		}
		}	
	if(e.getKeyCode() == KeyEvent.VK_ENTER) {
		if(!play) {
			play=true;
			ballposX=120;
			ballposY=350;
			ballXider= -1;
			ballYider= -2;
			playerX=310;
			score=0;
			totalBricks=35;
			map= new MapGenerator(5,7);
			repaint();
		}
	}
		
}
	public void moveRight() {
		play =true;
		playerX+=20;
	}
	public void moveLeft() {
		play =true;
		playerX-=20;
	}
	@Override
	public void keyReleased(KeyEvent e) {}

	@Override
	public void keyTyped(KeyEvent e) {}

}
