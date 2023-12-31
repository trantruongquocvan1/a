package othello;


import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.JPanel;

import java.util.ArrayList;
import java.util.List;

public class OthelloGame {

	public static void main(String[] args) {
		

		JFrame main_frame = new JFrame();
		main_frame.setTitle("Othello");
		main_frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


		JPanel top_panel = new JPanel();
		top_panel.setLayout(new BorderLayout());
		main_frame.setContentPane(top_panel);


		
		OthelloWidget ttt = new OthelloWidget();
		top_panel.add(ttt, BorderLayout.CENTER);




		main_frame.pack();
		main_frame.setVisible(true);	

	}
}
