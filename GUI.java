import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.*;

public class GUI {
	
	public void setUpWindow() {
		
		JFrame frame = new JFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(400, 400);
		frame.setFocusable(true);
		
		frame.addKeyListener(new KeyListener() {
			
			@Override
			public void keyTyped(KeyEvent e) {
				
				
			}
			
			@Override
			public void keyReleased(KeyEvent e) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				
				if (keyCode == KeyEvent.VK_Q) {
					Main.atomicBool = true;
//					System.exit(0);
				}
				
			}
		});
	}
}
