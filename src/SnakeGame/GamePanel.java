package SnakeGame;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;
import java.util.*;
import javax.swing.Timer;

public class GamePanel extends JPanel  implements ActionListener {

	static final int screen_width=500;
	static final int screen_height=600;
	static final int UNIT_SIZE=20;
	static final int GAME_UNITS=(screen_width*screen_height)/UNIT_SIZE;
    int DELAY=150;
	final int X[]=new int[GAME_UNITS];
	final int Y[]=new int[GAME_UNITS];
	int score=0;
	JButton restart;
	int bodyPart=2;
	int appleEaten;
	int appleX,appleY;
	char Direction='R'; // R RIGHT//L LEFT//D DOWN//U UP
	boolean running=false;
	Timer timer;
	Random random;
	GamePanel()
	{
		random=new Random();
		this.setPreferredSize(new Dimension(screen_width,screen_height));
		this.setBackground(Color.black);
		
		this.addKeyListener(new MykeyAdapter());
		this.setFocusable(true);
		startGame();
	}
	public void startGame()
	{
		newApple();
		running=true;
		timer = new Timer( DELAY,this);
        timer.start();
	}
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g);
		draw(g);
		
	}
	public void draw(Graphics g)
	{
		if(running)
		{
			scorefunction(g);
			g.setColor(Color.red);
			g.fillOval(appleX, appleY, UNIT_SIZE,UNIT_SIZE);
			
			for(int i=0;i<bodyPart;i++)
			{
				if(i==0)
				{
					g.setColor(Color.blue);
					g.fillOval(X[i], Y[i], UNIT_SIZE,UNIT_SIZE);
				}
				else
				{
					g.setColor(Color.green);
					g.fillOval(X[i], Y[i], UNIT_SIZE,UNIT_SIZE);
					
				}
			}
		}
		else
		{
			Gameover(g);
		}
		
	}
	public void move()
	{
		for(int i=bodyPart;i>0;i--)
		{
			X[i]=X[i-1];
			Y[i]=Y[i-1];
		}
		switch (Direction)
		{
			case 'R':
				X[0]=X[0]+UNIT_SIZE;
				break;
			case 'L':
				X[0]=X[0]-UNIT_SIZE;
				break;
			case 'U':
				Y[0]=Y[0]-UNIT_SIZE;
				break;
			case 'D':
				Y[0]=Y[0]+UNIT_SIZE;
				break;
		}
		
	}
	public void newApple()
	{
		appleX=random.nextInt((int)(screen_width/UNIT_SIZE))*UNIT_SIZE;
		appleY=random.nextInt((int)(screen_height/UNIT_SIZE))*UNIT_SIZE;
		
	}
	public void checkApple()
	{
		if(X[0]==appleX && Y[0]==appleY)
		{
			score++;
			bodyPart++;
			System.out.print(score);
			newApple();
		}
		
	}
	public void checkcollosion()
	{
		//if collide body
		for(int i=bodyPart;i>0;i--)
		{
			if(X[0]==X[i] && Y[0]==Y[i] ) {
				running=false;
			}
		}
		//if head collide left wall
		  if(X[0]<0)running=false;
		//if head collide right wall
		  if(X[0]>screen_width)running=false;
		//if head collide upper wall
		  if(Y[0]<0)running=false;
		//if head collide lower wall
		  if(Y[0]>screen_height)running=false;
		  
		//stop timer
		  if(!running)
		  {
			  timer.stop();
		  }
		
	}
	public void Gameover(Graphics g)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,75));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score "+ score,(screen_width-metrics.stringWidth("Game Over"))/2, screen_height/2);
		g.setFont(new Font("Ink Free",Font.BOLD,20));
		g.drawString("CLICK ENTER TO RESTART THE GAME ",(screen_width-metrics.stringWidth("RESTART"))/2, screen_height-50);
		
		
		
	}
	public void restart()
	{
		new Main().main(null);
	}
	public void scorefunction(Graphics g)
	{
		g.setColor(Color.RED);
		g.setFont(new Font("Ink Free",Font.BOLD,25));
		FontMetrics metrics=getFontMetrics(g.getFont());
		g.drawString("Score "+ score,screen_width-metrics.stringWidth("Game Over"),20);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
		
		 
		if(running)
		{
			move();
			checkApple();
			checkcollosion();
			if(score%10==0)
			{
				int x=DELAY;
				x=DELAY-25;
				DELAY=x;
			}
			
		}
		repaint();
	}
	public class MykeyAdapter extends KeyAdapter
	{
		@Override
		public void keyPressed(KeyEvent e) {

		    
		}
		
		@Override
		public void keyReleased(KeyEvent e) {

		    int key = e.getKeyCode();

		    switch(key)
		    {
		        case KeyEvent.VK_RIGHT:
		        {
		        	if(Direction!='L') {Direction='R';}
		        	break;
		        }
		        case KeyEvent.VK_LEFT:
		        {
		        	if(Direction!='R') {Direction='L';}
		        	break;
		        }
		        	
		        case KeyEvent.VK_UP:
		        {
		        	if(Direction!='D') {Direction='U';}
		        	break;
		        }
		        	
		        case KeyEvent.VK_DOWN:
		        {
		        	if(Direction!='U') {Direction='D';}
		        	break;
		        }
		        case KeyEvent.VK_ENTER:
		        {
		        	 restart();
		        	 break;
		        }
		        
		        
		    }
		}
		
	}
	
	

}
