package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Toolkit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import board.Board;
import board.Square;


public class Scrabble extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public static final int SQUARE_SIZE = 10;

	private Board board;

	private JPanel scorePanel = new JPanel();
	private JLabel score1Label = new JLabel();

	private JPanel boardPanel = new JPanel();
	private JLabel[][] grid = new JLabel[Board.BOARD_SIZE][Board.BOARD_SIZE];
	private JPanel[][] squares = new JPanel[Board.BOARD_SIZE][Board.BOARD_SIZE];
	private JPanel contentPane;
	private final JTextField scoreField = new JTextField();

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Scrabble frame = new Scrabble(new Board());
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 * @param board 
	 */
	public Scrabble(Board board) {
		scoreField.setDisabledTextColor(Color.BLACK);
		scoreField.setHorizontalAlignment(SwingConstants.CENTER);
		scoreField.setEnabled(false);
		scoreField.setColumns(10);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 500, 500);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		
		contentPane.setLayout(new BorderLayout());
		Toolkit tk = Toolkit.getDefaultToolkit();
		Dimension screenSize = tk.getScreenSize();
		setLocation(screenSize.width / 6, screenSize.height / 6);
		setSize(screenSize.width /3, screenSize.height / 2);
		
		this.board = board;
		
		initialize();
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Scrabble");
	}
	
	private void initialize() {
		
		initializeBoard();
		initializeScorePanel();
		getContentPane().add(boardPanel);
		getContentPane().add(scorePanel, BorderLayout.SOUTH);
		scorePanel.add(score1Label, "cell 0 0,growx,aligny top");
		
		scorePanel.add(scoreField);
	}

	private void initializeScorePanel() {
		score1Label.setText("Score : ");
		scorePanel.setSize(100, 100);
	}

	private void initializeBoard() {
		boardPanel.setLayout(new GridLayout(Board.BOARD_SIZE, Board.BOARD_SIZE));
		for (int row = 0; row < Board.BOARD_SIZE; row++) {
			for (int column = 0; column < Board.BOARD_SIZE; column++) {
				Square square = board.getSquare(row, column);
				JPanel panel = new JPanel();
				JLabel label = new JLabel(getContent(square));
				label.setBackground(getSquareColor(square));
				panel.setBackground(getSquareColor(square));
				panel.setSize(50, 50);
				panel.setBorder(BorderFactory.createEtchedBorder());
				panel.add(label);
				squares[row][column] = panel;
				grid[row][column] = label;
				boardPanel.add(panel);
			}
		}
	}

	public Color getSquareColor(Square square) {
		char content = square.getContent();
		if( content == Square.CENTER_SQUARE){
			return new Color(0, 0, 0);
		}
		return new Color(210, 210, 210);
	}

	private String getContent(Square square) {
		char content = square.getContent();
		if (square.containsLetter()) {
			return ""+content;
		} 
		return "";
	}
	
	public void updateBoard() {
		for (int row = 0; row < Board.BOARD_SIZE; row++) {
			for (int column = 0; column < Board.BOARD_SIZE; column++) {
				JLabel label = grid[row][column];
				Square square = board.getSquare(row, column);
				if (square.containsLetter())
					squares[row][column].setBackground(Color.WHITE);
				label.setText(getContent(square));
			}
		}
	}

	public void updateScores(int score) {
		scoreField.setText(String.valueOf(score));		
	}

}
