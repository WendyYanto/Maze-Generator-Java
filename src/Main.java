import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main extends JFrame {
	
	final int width = 800;
	final int height = 500;
	static JLabel timeLeft;
	static JLabel totalLife;
	static JLabel currLevel;
	
	public Main() {
		setLayout(new BorderLayout());
		
		GamePanel gamePanel = new GamePanel();
		add(gamePanel,BorderLayout.CENTER);
		
		JPanel infoPanel = new JPanel();
		infoPanel.setSize(400,400);
		GridLayout Legends = new GridLayout(12,1);
		infoPanel.setLayout(Legends);
		
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Find the X-it");
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		title.setHorizontalAlignment(SwingConstants.LEFT);

		titlePanel.add(title);
		infoPanel.add(titlePanel);
		
		JPanel timeLeftPanel = new JPanel();
		timeLeft = new JLabel("Time Left : 25");
		timeLeft.setFont(new Font("Serif", Font.PLAIN, 24));
		timeLeft.setVerticalAlignment(SwingConstants.CENTER);
		
		timeLeftPanel.add(timeLeft);
		infoPanel.add(timeLeftPanel);
		
		JPanel totalLifePanel = new JPanel();
		totalLife = new JLabel("Life : 3");
		totalLife.setFont(new Font("Serif", Font.PLAIN, 24));
		
		totalLifePanel.add(totalLife);
		infoPanel.add(totalLifePanel);	
		
		JPanel currLevelPanel = new JPanel();
		currLevel = new JLabel("Level : 1");
		currLevel.setFont(new Font("Serif", Font.PLAIN, 24));
		currLevelPanel.add(currLevel);
		infoPanel.add(currLevelPanel);	
		
		JPanel blueBoxPanel = new JPanel();
		ImageIcon blueBoxImage = new ImageIcon("src/blueBox.png");
		JLabel blueBoxLabel = new JLabel("Your Goal");
		blueBoxLabel.setIcon(blueBoxImage);
		blueBoxPanel.add(blueBoxLabel);
		infoPanel.add(blueBoxPanel);
		
		JPanel greenBoxPanel = new JPanel();
		ImageIcon greenBoxImage = new ImageIcon("src/greenBox.png");
		JLabel greenBoxLabel = new JLabel("Player");
		greenBoxLabel.setIcon(greenBoxImage);
		greenBoxPanel.add(greenBoxLabel);
		infoPanel.add(greenBoxPanel);
		
		JPanel yellowBoxPanel = new JPanel();
		ImageIcon yellowBoxImage = new ImageIcon("src/yellowBox.png");
		JLabel yellowBoxLabel = new JLabel("Coin (Extra Time)");
		yellowBoxLabel.setIcon(yellowBoxImage);
		yellowBoxPanel.add(yellowBoxLabel);
		infoPanel.add(yellowBoxPanel);		
		
		JPanel redBoxPanel = new JPanel();
		ImageIcon redBoxImage = new ImageIcon("src/redBox.png");
		JLabel redBoxLabel = new JLabel("Trap");
		redBoxLabel.setIcon(redBoxImage);
		redBoxPanel.add(redBoxLabel);
		infoPanel.add(redBoxPanel);
		
		JPanel spacePressPanel = new JPanel();
		JLabel spaceLabel = new JLabel("Press 'Space' to Pause the game");
		spaceLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		spaceLabel.setForeground(Color.decode("#401ef9"));
		spacePressPanel.add(spaceLabel);
		infoPanel.add(spacePressPanel);
		
		JPanel ClosePanel = new JPanel();
		JLabel CloseHoverActionLabel = new JLabel("Hover this to show exit button");
		JButton CloseButton = new JButton("Close");
		CloseButton.setRolloverEnabled(false);
		CloseButton.setVisible(false);
		
		CloseButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
		});
		
		CloseButton.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				CloseButton.setVisible(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				CloseButton.setVisible(false);
			}
		});
		
		ClosePanel.addMouseListener(new MouseAdapter() {
			
			@Override
			public void mouseEntered(MouseEvent e) {
				CloseButton.setVisible(true);
			}
			
			@Override
			public void mouseExited(MouseEvent e) {
				CloseButton.setVisible(false);
			}
		});
		
		ClosePanel.add(CloseHoverActionLabel);
		ClosePanel.add(CloseButton);
		infoPanel.add(ClosePanel);
		
		add(infoPanel,BorderLayout.EAST);
		setSize(width,height);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);	
	}

	public static void main(String[] args) {
		new Main();
	}


}
