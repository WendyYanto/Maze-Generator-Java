import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main extends JFrame {

	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 500;

	private JPanel infoPanel = new JPanel();
	private JPanel titlePanel = new JPanel();
	private JPanel timeLeftPanel = new JPanel();
	private JPanel totalLifePanel = new JPanel();
	private JPanel currentLevelPanel = new JPanel();
	private JPanel blueBoxPanel = new JPanel();
	private JPanel greenBoxPanel = new JPanel();
	private JPanel yellowBoxPanel = new JPanel();
	private JPanel redBoxPanel = new JPanel();
	private JPanel pauseInfoPanel = new JPanel();
	private JPanel exitPanel = new JPanel();

	static JLabel timeLeft;
	static JLabel totalLife;
	static JLabel currLevel;

	private void setupInfoPanel() {
		infoPanel.setSize(400,400);
		GridLayout Legends = new GridLayout(12,1);
		infoPanel.setLayout(Legends);
	}

	private void setupTitlePanel() {
		JLabel title = new JLabel("Find the X-it");
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		title.setHorizontalAlignment(SwingConstants.LEFT);
		titlePanel.add(title);
	}

	private void setupTimeLeftPanel() {
		timeLeft = new JLabel("Time Left : 25");
		timeLeft.setFont(new Font("Serif", Font.PLAIN, 24));
		timeLeft.setVerticalAlignment(SwingConstants.CENTER);
		timeLeftPanel.add(timeLeft);
	}

	private void setupTotalLifePanel() {
		totalLife = new JLabel("Life : 3");
		totalLife.setFont(new Font("Serif", Font.PLAIN, 24));
		totalLifePanel.add(totalLife);
	}

	private void setupCurrentLevelPanel() {
		currLevel = new JLabel("Level : 1");
		currLevel.setFont(new Font("Serif", Font.PLAIN, 24));
		currentLevelPanel.add(currLevel);
	}

	private void setupBlueBoxPanel() {
		ImageIcon blueBoxImage = new ImageIcon("src/assets/BlueBox.png");
		JLabel blueBoxLabel = new JLabel("Your Goal");
		blueBoxLabel.setIcon(blueBoxImage);
		blueBoxPanel.add(blueBoxLabel);
	}

	private void setupGreenBoxPanel() {
		ImageIcon greenBoxImage = new ImageIcon("src/assets/GreenBox.png");
		JLabel greenBoxLabel = new JLabel("Player");
		greenBoxLabel.setIcon(greenBoxImage);
		greenBoxPanel.add(greenBoxLabel);
	}

	private void setupYellowBoxPanel() {
		ImageIcon yellowBoxImage = new ImageIcon("src/assets/YellowBox.png");
		JLabel yellowBoxLabel = new JLabel("Coin (Extra Time)");
		yellowBoxLabel.setIcon(yellowBoxImage);
		yellowBoxPanel.add(yellowBoxLabel);
	}

	private void setupRedBoxPanel() {
		ImageIcon redBoxImage = new ImageIcon("src/assets/RedBox.png");
		JLabel redBoxLabel = new JLabel("Trap");
		redBoxLabel.setIcon(redBoxImage);
		redBoxPanel.add(redBoxLabel);
	}

	private void setupPauseInfoPanel() {
		JLabel spaceLabel = new JLabel("Press 'Space' to Pause the game");
		spaceLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		spaceLabel.setForeground(Color.decode("#401ef9"));
		pauseInfoPanel.add(spaceLabel);
	}

	private void setupExitPanel() {
		JLabel CloseHoverActionLabel = new JLabel("Hover this to show exit button");
		JButton CloseButton = new JButton("Close");
		CloseButton.setRolloverEnabled(false);
		CloseButton.setVisible(false);

		CloseButton.addActionListener(e -> System.exit(0));

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

		exitPanel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				CloseButton.setVisible(true);
			}

			@Override
			public void mouseExited(MouseEvent e) {
				CloseButton.setVisible(false);
			}
		});

		exitPanel.add(CloseHoverActionLabel);
		exitPanel.add(CloseButton);
	}

	private Main() {
		setLayout(new BorderLayout());

		GamePanel gamePanel = new GamePanel();
		add(gamePanel,BorderLayout.CENTER);

		setupInfoPanel();
		setupTitlePanel();
		setupTimeLeftPanel();
		setupTotalLifePanel();
		setupCurrentLevelPanel();
		setupBlueBoxPanel();
		setupGreenBoxPanel();
		setupYellowBoxPanel();
		setupRedBoxPanel();
		setupPauseInfoPanel();
		setupExitPanel();

		infoPanel.add(titlePanel);
		infoPanel.add(timeLeftPanel);
		infoPanel.add(totalLifePanel);
		infoPanel.add(currentLevelPanel);
		infoPanel.add(blueBoxPanel);
		infoPanel.add(greenBoxPanel);
		infoPanel.add(yellowBoxPanel);
		infoPanel.add(redBoxPanel);
		infoPanel.add(pauseInfoPanel);
		infoPanel.add(exitPanel);

		add(infoPanel, BorderLayout.EAST);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

}
