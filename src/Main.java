import contract.MainContract;
import ui.GamePanel;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Main extends JFrame implements MainContract {

	private static final int GAME_WIDTH = 800;
	private static final int GAME_HEIGHT = 500;

	private JLabel timeLabel;
	private JLabel lifeLabel;
	private JLabel levelLabel;

	private JPanel setupInfoPanel() {
		JPanel infoPanel = new JPanel();
		infoPanel.setSize(400,400);
		GridLayout Legends = new GridLayout(12,1);
		infoPanel.setLayout(Legends);
		return infoPanel;
	}

	private JPanel setupTitlePanel() {
		JPanel titlePanel = new JPanel();
		JLabel title = new JLabel("Find the X-it");
		title.setFont(new Font("Serif", Font.PLAIN, 30));
		title.setHorizontalAlignment(SwingConstants.LEFT);
		titlePanel.add(title);
		return titlePanel;
	}

	private JPanel setupTimeLeftPanel() {
		JPanel timeLeftPanel = new JPanel();
		timeLabel = new JLabel("Time Left : 25");
		timeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		timeLabel.setVerticalAlignment(SwingConstants.CENTER);
		timeLeftPanel.add(timeLabel);
		return timeLeftPanel;
	}

	private JPanel setupTotalLifePanel() {
		JPanel totalLifePanel = new JPanel();
		lifeLabel = new JLabel("Life : 3");
		lifeLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		totalLifePanel.add(lifeLabel);
		return totalLifePanel;
	}

	private JPanel setupCurrentLevelPanel() {
		JPanel currentLevelPanel = new JPanel();
		levelLabel = new JLabel("Level : 1");
		levelLabel.setFont(new Font("Serif", Font.PLAIN, 24));
		currentLevelPanel.add(levelLabel);
		return currentLevelPanel;
	}

	private JPanel setupBlueBoxPanel() {
		JPanel blueBoxPanel = new JPanel();
		ImageIcon blueBoxImage = new ImageIcon("src/assets/BlueBox.png");
		JLabel blueBoxLabel = new JLabel("Your Goal");
		blueBoxLabel.setIcon(blueBoxImage);
		blueBoxPanel.add(blueBoxLabel);
		return blueBoxPanel;
	}

	private JPanel setupGreenBoxPanel() {
		JPanel greenBoxPanel = new JPanel();
		ImageIcon greenBoxImage = new ImageIcon("src/assets/GreenBox.png");
		JLabel greenBoxLabel = new JLabel("Player");
		greenBoxLabel.setIcon(greenBoxImage);
		greenBoxPanel.add(greenBoxLabel);
		return greenBoxPanel;
	}

	private JPanel setupYellowBoxPanel() {
		JPanel yellowBoxPanel = new JPanel();
		ImageIcon yellowBoxImage = new ImageIcon("src/assets/YellowBox.png");
		JLabel yellowBoxLabel = new JLabel("Coin (Extra Time)");
		yellowBoxLabel.setIcon(yellowBoxImage);
		yellowBoxPanel.add(yellowBoxLabel);
		return yellowBoxPanel;
	}

	private JPanel setupRedBoxPanel() {
		JPanel redBoxPanel = new JPanel();
		ImageIcon redBoxImage = new ImageIcon("src/assets/RedBox.png");
		JLabel redBoxLabel = new JLabel("Trap");
		redBoxLabel.setIcon(redBoxImage);
		redBoxPanel.add(redBoxLabel);
		return redBoxPanel;
	}

	private JPanel setupPauseInfoPanel() {
		JPanel pauseInfoPanel = new JPanel();
		JLabel spaceLabel = new JLabel("Press 'Space' to Pause the game");
		spaceLabel.setFont(new Font("Serif", Font.PLAIN, 20));
		spaceLabel.setForeground(Color.decode("#401ef9"));
		pauseInfoPanel.add(spaceLabel);
		return pauseInfoPanel;
	}

	private JPanel setupExitPanel() {
		JPanel exitPanel = new JPanel();
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
		return exitPanel;
	}

	private Main() {
		setLayout(new BorderLayout());

		GamePanel gamePanel = new GamePanel(this);
		JPanel infoPanel = setupInfoPanel();

		infoPanel.add(setupTitlePanel());
		infoPanel.add(setupTimeLeftPanel());
		infoPanel.add(setupTotalLifePanel());
		infoPanel.add(setupCurrentLevelPanel());
		infoPanel.add(setupBlueBoxPanel());
		infoPanel.add(setupGreenBoxPanel());
		infoPanel.add(setupYellowBoxPanel());
		infoPanel.add(setupRedBoxPanel());
		infoPanel.add(setupPauseInfoPanel());
		infoPanel.add(setupExitPanel());

		add(gamePanel,BorderLayout.CENTER);
		add(infoPanel, BorderLayout.EAST);
		setSize(GAME_WIDTH, GAME_HEIGHT);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setVisible(true);
	}

	public static void main(String[] args) {
		new Main();
	}

	@Override
	public void setTime(int time) {
		timeLabel.setText("Time Left : " + time);
	}

	@Override
	public void setTotalLife(int life) {
		lifeLabel.setText("Life : " + life);
	}

	@Override
	public void setLevel(int level) {
		levelLabel.setText("Level : " + level);
	}
}
