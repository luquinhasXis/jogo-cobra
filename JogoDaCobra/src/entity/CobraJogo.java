package entity;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.beans.EventHandler;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.JPanel;
import javax.swing.Timer;

public class CobraJogo extends JPanel implements ActionListener, KeyListener {
	 private class blocos{
		int x;
		int y;
	
		blocos(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	
	int width;
	int height;
	int cellSize = 25;
	
	// snake
	blocos snakeHead;
	ArrayList<blocos> snakeBody;
	
	// food
	blocos food;
	Random random;
	
	// game logic
	int velocityX = 1;
    int velocityY = 0;
    Timer gameLoop;

    boolean gameOver = false;
	
	
// Comando para criar o desenho do jogo
	public CobraJogo(final int width, final int height) {
		this.width = width;
		this.height = height;
		setPreferredSize(new Dimension(this.width, this.height));
		setBackground(Color.BLACK);
		addKeyListener(this);
		setFocusable(true);
		
		snakeHead = new blocos(5, 5);
        snakeBody = new ArrayList<blocos>();

        food = new blocos(10, 10);
        random = new Random();
        placeFood();

        velocityX = 1;
        velocityY = 0;
        
        gameLoop = new Timer(120, this);
        gameLoop.start();
	}

	public void paintComponent(final Graphics graphics) {
	   super.paintComponent(graphics);
	   draw(graphics);
	}
	
	private void draw(Graphics graphics) {
		 //Linhas 
        for(int i = 0; i < width/cellSize; i++) {
        	graphics.drawLine(i*cellSize, 0, i*cellSize, height);
        	graphics.drawLine(0, i*cellSize, width, i*cellSize); 
        }
        
        //Food color
        graphics.setColor(Color.PINK);
        graphics.fill3DRect(food.x * cellSize, food.y * cellSize, cellSize, cellSize, true);

        //Cabeça da cobra 
        graphics.setColor(Color.green);
        graphics.fill3DRect(snakeHead.x * cellSize, snakeHead.y*cellSize, cellSize, cellSize, true);
        
        //Corpo da cobra
        for (int i = 0; i < snakeBody.size(); i++) {
            blocos snakePart = snakeBody.get(i);
            graphics.fill3DRect(snakePart.x * cellSize, snakePart.y * cellSize, cellSize, cellSize, true);
		}

        //Score
        graphics.setFont(new Font("", Font.CENTER_BASELINE, 16));
        if (gameOver) {
        	graphics.setColor(Color.red);
        	graphics.drawString("Game Over: " + String.valueOf(snakeBody.size()), cellSize - 16, cellSize);
        }
        else {
        	graphics.drawString("Score: " + String.valueOf(snakeBody.size()), cellSize - 16, cellSize);
        }
	}

	public void placeFood(){
	        food.x = random.nextInt(width/cellSize);
			food.y = random.nextInt(height/cellSize);
		}
	
	private void move() {
		// movimentos para comer a comida
		if(collision(snakeHead, food)) {
			snakeBody.add(new blocos(food.x, food.y));
			placeFood();
		}
		
		// movimento da cobra
		for(int i=snakeBody.size()-1; i>=0; i--) {
			blocos snakePart = snakeBody.get(i);
			if(i == 0) {
				snakePart.x = snakeHead.x;
				snakePart.y = snakeHead.y;
			}else {
				blocos prevSnakePart = snakeBody.get(i-1);
				snakePart.x = prevSnakePart.x;
				snakePart.y = prevSnakePart.y;
			}
		}
		
		//move snake head
        snakeHead.x += velocityX;
        snakeHead.y += velocityY;

        //game over conditions
        for (int i = 0; i < snakeBody.size(); i++) {
            blocos snakePart = snakeBody.get(i);

        // comando para colidir na parede resultar em jogo perdido
            if (collision(snakeHead, snakePart)) {
                gameOver = true;
            }
        }

        if (snakeHead.x * cellSize < 0 || snakeHead.x * cellSize > width || 
            snakeHead.y * cellSize < 0 || snakeHead.y * cellSize > height ) { 
            gameOver = true;
        }
    }	

	private boolean collision(blocos blocos1, blocos blocos2) {
		return blocos1.x == blocos2.x && blocos1.y == blocos2.y;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		move();
		repaint();
		if(gameOver) {
			gameLoop.stop();
		}
	}	
	
	 @Override
	 public void keyPressed(KeyEvent e) {
	    // System.out.println("KeyEvent: " + e.getKeyCode());
	    if (e.getKeyCode() == KeyEvent.VK_UP && velocityY != 1) {
	        velocityX = 0;
	        velocityY = -1;
	   } else if (e.getKeyCode() == KeyEvent.VK_DOWN && velocityY != -1) {
	        velocityX = 0;
	        velocityY = 1;
	   } else if (e.getKeyCode() == KeyEvent.VK_LEFT && velocityX != 1) {
	        velocityX = -1;
	        velocityY = 0;
	   } else if (e.getKeyCode() == KeyEvent.VK_RIGHT && velocityX != -1) {
	        velocityX = 1;
	        velocityY = 0;
	   }
   }
	    @Override
	    public void keyTyped(KeyEvent e) {}

	    @Override
	    public void keyReleased(KeyEvent e) {}
	}