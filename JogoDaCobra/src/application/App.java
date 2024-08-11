package application;

import javax.swing.*;

import entity.CobraJogo;

public class App {

	public static void main(String[] args) throws Exception{
		int width = 600;
		int height = width;

// Comando para criar uma janela.
		final JFrame frame = new JFrame("Cobra");
		frame.setVisible(true);
		frame.setSize(width, height);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		CobraJogo cobraJogo = new CobraJogo(width, height);
		frame.add(cobraJogo);
		frame.pack();
		cobraJogo.requestFocus();
		

	}

}
