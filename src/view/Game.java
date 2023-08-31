package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import models.Board;
import models.Light;

public class Game extends JPanel {
	private JPanel panel;
	private static Color _color_off = new Color(0, 0, 0);
	private static Color _color_on = new Color(252, 232, 174);
	private static int xPosition;
	private static int yPosition;
	private int size;
	private int tries;
	JButton[][] buttons;
	Board game;
	Light[][] lights;
	JLabel triesLabel;

	public Game(Board board) {
		tries = 0;
		game = board;
		size = game.getSize();
		buttons = new JButton[size][size];
		lights = board.getLights();
		initComponents(game);
	}

	public void initComponents(Board board) {
		setLayout(null);
		panel = new JPanel();
		panel.setBounds(0, 0, 800, 600);
		add(panel);
		panel.setLayout(null);

		triesLabel = new JLabel("Tries: " + tries);
		triesLabel.setFont(new Font("Nirmala UI", Font.BOLD, 20));
		triesLabel.setHorizontalAlignment(SwingConstants.CENTER);
		triesLabel.setBackground(new Color(255, 255, 255));
		triesLabel.setBounds(30, 443, 119, 47);
		panel.add(triesLabel);

		managePositions(size);
		for (int i = 0; i < size; i++) {
			manageXPosition(size);
			for (int j = 0; j < size; j++) {
				buttons[i][j] = new JButton();
				if (lights[i][j].getState()) {
					buttons[i][j].setBackground(_color_on);
				} else {
					buttons[i][j].setBackground(_color_off);
				}
				buttons[i][j].setBounds(xPosition, yPosition, 50, 50);

				buttons[i][j].addActionListener(new ActionListener() {

					public void actionPerformed(ActionEvent e) {
						if (e.getSource() instanceof JButton) {
							JButton button = (JButton) e.getSource();

							for (int i = 0; i < buttons.length; i++) {
								for (int j = 0; j < buttons.length; j++) {
									if (buttons[i][j].equals(button)) {
										board.switchLightValue(i, j);
										tries++;
										board.isCompleted();
									}
								}
							}
							refresh();

						}

					}

				});
				panel.add(buttons[i][j]);
				_add_x(70);
			}
			_add_y(70);
		}
		setBackground();

	}

	private void setBackground() {
		JLabel background;

		new ImageIcon("background.jpg");
		background = new JLabel("",
				new ImageIcon("C:\\Users\\Santi\\eclipse-workspace\\LightsOut2\\src\\view\\background.jpg"),
				SwingConstants.TRAILING);
		background.setVerticalAlignment(SwingConstants.BOTTOM);
		background.setBounds(10, 11, 764, 539);
		panel.add(background);

	}

	private void managePositions(int size) {
		manageXPosition(size);
		manageYPosition(size);
	}

	private void manageXPosition(int size) {
		if (size == 3) {
			setXPosition(295);
		} else if (size == 4) {
			setXPosition(260);
		} else if (size == 5) {
			setXPosition(225);
		}
	}

	private void manageYPosition(int size) {
		if (size == 3) {
			setYPosition(195);
		} else if (size == 4) {
			setYPosition(160);
		} else if (size == 5) {
			setYPosition(125);
		}
	}

	private static void _add_x(int pixels) {
		xPosition += pixels;
	}

	private static void _add_y(int pixels) {
		yPosition += pixels;
	}

	private static void setXPosition(int pixels) {
		xPosition = pixels;
	}

	private static void setYPosition(int pixels) {
		yPosition = pixels;
	}

	private void refresh() {
		for (int i = 0; i < buttons.length; i++) {
			for (int j = 0; j < buttons.length; j++) {
				if (game.getLight(i, j).getState()) {
					buttons[i][j].setBackground(_color_on);
					triesLabel.setText("Tries: " + Integer.toString(tries));
				} else {
					buttons[i][j].setBackground(_color_off);
				}
			}
		}
		if (game.isCompleted()) {
			playerWon();
		}
	}

	public void playerWon() {
		WinScreen winScreen = new WinScreen(tries);
		panel.removeAll();
		panel.add(winScreen, CENTER_ALIGNMENT);
		winScreen.setSize(800, 600);
		winScreen.setLocation(0, 0);
		panel.revalidate();
		panel.repaint();
	}
}
