/**
 * 
 */
package packhacks2018.binary_battle.ui;

import java.awt.BasicStroke;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import packhacks2018.binary_battle.Direction;
import packhacks2018.binary_battle.model.action.Action;
import packhacks2018.binary_battle.model.action.Action.ActionType;
import packhacks2018.binary_battle.model.character.BinaryCharacter;
import packhacks2018.binary_battle.model.context.BinaryBattleField;
import packhacks2018.binary_battle.util.IntNode;
import packhacks2018.binary_battle.util.SimpleIntBinaryTree;

/**
 * @author Andrew Wock
 *
 */
public class BinaryBattleGUI extends JFrame {
	
	private static final String START = "Start";
	
	private static final String STORY = "Story";
	
	private static final String GAME = "Game";

	private StartPanel start = new StartPanel();
	
	private StoryPanel story = new StoryPanel();

	private JPanel game = new GamePanel();

	private JPanel panel;
	
	private CardLayout cardLayout;
	
	public BinaryBattleGUI(String name) {
		super(name);
		setSize(910, 1050);
		setResizable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		panel = new JPanel();
		cardLayout = new CardLayout();
		panel.setLayout(cardLayout);
		panel.add(start, START);
		panel.add(story, STORY);
		panel.add(game, GAME);
		cardLayout.show(panel, START);
		
		Container c = getContentPane();
		c.add(panel,  BorderLayout.CENTER);
		setVisible(true);

	}
	
	

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		new BinaryBattleGUI("Binary Battle");
	}
	

	private class GamePanel extends JPanel implements ActionListener {
		
		BinaryBattleField bbf = BinaryBattleField.getInstance();
		
		InnerPanel iPanel = new InnerPanel();
		
		DisplayPanel dPanel = new DisplayPanel();
		
		public GamePanel() {
			super(new GridLayout(1, 2));
			bbf.reset(4);
			add(dPanel);
			add(iPanel);
		}
		
		
		private class DisplayPanel extends JPanel {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;

			public boolean animated;
			
			private BinaryCharacter current;
			
			private int apparentXPos;
			
			private int apparentYPos;
			
			private ArrayList<SimpleIntBinaryTree> trees = new ArrayList<>();
			
			private ArrayList<Direction> treeDirections = new ArrayList<Direction>();
			
			private ArrayList<Color> treeColors = new ArrayList<Color>();
			
			private ArrayList<Integer> xPoss = new ArrayList<>();
			
			private ArrayList<Integer> yPoss = new ArrayList<>();
			
			
			public DisplayPanel() {
				
			}
			
			public void animateMoves(Action[] moves) {
				current = bbf.getCurrentCharacter();
				int tempX = current.getLocation().xPos();
				int tempY = current.getLocation().yPos();
				
				for (int i = 0; i < moves.length; i++) {
					if (moves[i] != null && moves[i].getActionType() == ActionType.FIRE) {
						trees.add(current.getTree());
						treeDirections.add(moves[i].getDirection());
						treeColors.add(current.getColor());
						xPoss.add(tempX);
						yPoss.add(tempY);
					} else if (moves[i] != null && moves[i].getActionType() == ActionType.MOVE) {
						switch(moves[i].getDirection()) {
						case DOWN:
							tempY++;
							break;
						case LEFT:
							tempX--;
							break;
						case RIGHT:
							tempX++;
							break;
						case UP:
							tempY--;
							break;
						}
					}
				}
			}
			

			
			public void paintTree(IntNode first, Direction direction, Graphics g, int xi, int yi) {
				IntNode currentNode = first;
				switch(direction) {
				case DOWN:
					paintTreeDown(currentNode, g, xi, yi);//getTreeHitDown(currentNode, currentCharacter.getLocation().xPos(), currentCharacter.getLocation().yPos());
					break;
				case LEFT:
					paintTreeLeft(currentNode, g, xi, yi);//getTreeHitLeft(currentNode, currentCharacter.getLocation().xPos(), currentCharacter.getLocation().yPos());
					break;
				case RIGHT:
					paintTreeRight(currentNode, g, xi, yi);//getTreeHitRight(currentNode, currentCharacter.getLocation().xPos(), currentCharacter.getLocation().yPos());
					break;
				case UP:
					paintTreeUp(currentNode, g, xi, yi);
					break;
				}
			}
			
			public void paintTreeUp(IntNode node, Graphics g, int currentX, int currentY) {
				if (node.left() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX + 1)*30 + 15, (currentY - 1)*30 + 15);
					paintTreeUp(node.left(), g2, currentX + 1, currentY - 1);
				}
				if (node.right() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX - 1)*30 + 15, (currentY - 1)*30 + 15);
					paintTreeUp(node.right(), g2, currentX - 1, currentY - 1);
				}
			}
			
			public void paintTreeDown(IntNode node, Graphics g, int currentX, int currentY) {
				if (node.left() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX - 1)*30 + 15, (currentY + 1)*30 + 15);
					paintTreeDown(node.left(), g2, currentX - 1, currentY + 1);
				}
				if (node.right() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX + 1)*30 + 15, (currentY + 1)*30 + 15);
					paintTreeDown(node.right(), g2, currentX + 1, currentY + 1);
				}
			}
			
			public void paintTreeLeft(IntNode node, Graphics g, int currentX, int currentY) {
				if (node.left() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX - 1)*30 + 15, (currentY - 1)*30 + 15);
					paintTreeLeft(node.left(), g2, currentX - 1, currentY - 1);
				}
				if (node.right() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX - 1)*30 + 15, (currentY + 1)*30 + 15);
					paintTreeLeft(node.right(), g2, currentX - 1, currentY + 1);
				}
			}
			
			public void paintTreeRight(IntNode node, Graphics g, int currentX, int currentY) {
				if (node.left() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX + 1)*30 + 15, (currentY + 1)*30 + 15);
					paintTreeRight(node.left(), g2, currentX + 1, currentY + 1);
				}
				if (node.right() != null) {
					Graphics2D g2 = (Graphics2D) g;
					g2.setStroke(new BasicStroke(5));
					g2.drawLine(currentX*30 + 15, currentY*30 + 15, (currentX + 1)*30 + 15, (currentY - 1)*30 + 15);
					paintTreeRight(node.right(), g2, currentX + 1, currentY - 1);
				}
			}
			
			
			
			public void paintComponent(Graphics g) {
					for (int c = 0; c < bbf.xSize(); c++) {
						for (int r = 0; r < bbf.ySize(); r++) {
							Image img = null;
							try {
								img = ImageIO.read(new File(bbf.getGrid()[c][r].getPaintFile()));
							} catch (IOException e) {
								e.printStackTrace();
							}
							
							g.drawImage(img, c*30, r*30, this);
							
							if (bbf.getGrid()[c][r].hasNumber()) {
								g.drawString(Integer.toString(bbf.getGrid()[c][r].getNumber()), c*30 + 5, r*30 + 15);
							}
							
							if (bbf.getGrid()[c][r].hasCharacter()) {
								Image charImage = null;
								try {
									charImage = ImageIO.read(new File(bbf.getGrid()[c][r].getOccupant().getRestSprite()));
								} catch (IOException e) {
									e.printStackTrace();
								} 
								g.drawImage(charImage, c*30, r*30, this);
							}
						}
					}
					for (int i = 0; i < trees.size(); i++) {
						IntNode first = trees.get(i).getRoot();
						Direction direction = treeDirections.get(i);
						g.setColor(treeColors.get(i));
						paintTree(first, direction, g, xPoss.get(i), yPoss.get(i));
						trees.remove(i);
						treeDirections.remove(i);
						treeColors.remove(i);
						xPoss.remove(i);
						yPoss.remove(i);
					}
			}
		}
		
		private class InnerPanel extends JPanel {
			/**
			 * 
			 */
			private static final long serialVersionUID = 1L;
			protected JButton upAtk = new JButton("Attack Up");
			protected JButton rtAtk = new JButton("Attack Right");
			protected JButton dnAtk = new JButton("Attack Down");
			protected JButton lfAtk = new JButton("Attack Left");
			
			protected JButton upMv = new JButton("Move Up");
			protected JButton rtMv = new JButton("Move Right");
			protected JButton dnMv = new JButton("Move Down");
			protected JButton lfMv = new JButton("Move Left");
			
			protected JButton clearMoves = new JButton("Clear moves");
			protected JButton execute = new JButton("Execute");

			public InnerPanel() {
				super(new GridLayout(5, 2));
				upAtk.addActionListener(GamePanel.this);
				rtAtk.addActionListener(GamePanel.this);
				dnAtk.addActionListener(GamePanel.this);
				lfAtk.addActionListener(GamePanel.this);
				upMv.addActionListener(GamePanel.this);
				rtMv.addActionListener(GamePanel.this);
				dnMv.addActionListener(GamePanel.this);
				lfMv.addActionListener(GamePanel.this);
				clearMoves.addActionListener(GamePanel.this);
				execute.addActionListener(GamePanel.this);
				add(upAtk);
				add(rtAtk);
				add(dnAtk);
				add(lfAtk);
				add(upMv);
				add(rtMv);
				add(dnMv);
				add(lfMv);
				add(clearMoves);
				add(execute);
			}
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			if (e.getSource() == iPanel.upAtk) {
				dPanel.animated = false;
				bbf.setAttack(Direction.UP);
			} else if (e.getSource() == iPanel.rtAtk) {
				dPanel.animated = false;
				bbf.setAttack(Direction.RIGHT);
			} else if (e.getSource() == iPanel.dnAtk) {
				dPanel.animated = false;
				bbf.setAttack(Direction.DOWN);
			} else if (e.getSource() == iPanel.lfAtk) {
				dPanel.animated = false;
				bbf.setAttack(Direction.LEFT);
			} else if (e.getSource() == iPanel.upMv) {
				dPanel.animated = false;
				bbf.setMove(Direction.UP);
			} else if (e.getSource() == iPanel.rtMv) {
				dPanel.animated = false;
				bbf.setMove(Direction.RIGHT);
			} else if (e.getSource() == iPanel.dnMv) {
				dPanel.animated = false;
				bbf.setMove(Direction.DOWN);
			} else if (e.getSource() == iPanel.lfMv) {
				dPanel.animated = false;
				bbf.setMove(Direction.LEFT);
			} else if (e.getSource() == iPanel.clearMoves) {
				dPanel.animated = false;
				bbf.clearMoveSet();
			} else if (e.getSource() == iPanel.execute) {
				Action[] moves = bbf.getCurrentCharacter().getMoveSet();
				dPanel.animateMoves(moves);
				bbf.executeTurn();
			} 
			dPanel.repaint();
		}
		
		
	}
	
	private class StoryPanel extends JPanel implements ActionListener {
		
		protected JButton next = new JButton("Next");
		
		protected JLabel story1 = new JLabel("4,000 years ago four tribes lived in harmony in Thear…  ");
		
		protected JLabel story2 = new JLabel("The land saw peace for centuries until a plague killed off all of the livestock.  ");
		
		protected JLabel story3 = new JLabel("Starving, the four tribes realized that with the resources left after the plague there was only enough for one tribe to survive. ");
		
		protected JLabel story4 = new JLabel("Each tribe called their best mage forward to decide the fate of their people. ");
		
		private JLabel instruct1 = new JLabel();
		
		private JLabel instruct2 = new JLabel("The mages must first gather data from the map to build their attack trees.");
		
		private JLabel instruct3 = new JLabel("Each turn, they may move twice or move once and attack once.");
		
		private JLabel instruct4 = new JLabel("The order in which data is collected is changes the shape of the tree.  It maps to binary search trees.");
		
		private JLabel instruct5 = new JLabel("Try to overlap your enemies with the path of your attack tree in any of the four directions up, down, left, and right.  Each of you have three lives");
		
		
		
		public StoryPanel() {
			add(story1, BorderLayout.CENTER);
			add(story2, BorderLayout.CENTER);
			add(story3, BorderLayout.CENTER);
			add(story4, BorderLayout.CENTER);
			add(instruct1, BorderLayout.CENTER);
			add(instruct2, BorderLayout.CENTER);
			add(instruct3, BorderLayout.CENTER);
			add(instruct4, BorderLayout.CENTER);
			add(instruct5, BorderLayout.CENTER);
			next.addActionListener(this);
			add(next, BorderLayout.CENTER);
			
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			cardLayout.show(panel, GAME);
			BinaryBattleGUI.this.setSize(1900, 1050);
		}
		
	}

	private class StartPanel extends JPanel implements ActionListener {

		protected JButton next = new JButton("Start");
		
		JPanel subWindow = new JPanel();
		JPanel subWindow5 = new JPanel(); 
		
		Image storyImage;
		
		public StartPanel() {
			super(new BorderLayout());

			next.addActionListener(this);
			subWindow5.add(next);
			
			try {
				storyImage = ImageIO.read(new File("images/Story_Background.PNG"));
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			
			subWindow.add(new JLabel("Binary Battle"));
			add(subWindow, BorderLayout.NORTH);
			add(subWindow5, BorderLayout.SOUTH);
		}
		
		protected void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawImage(storyImage, 0, 0, this);
		}

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		@Override
		public void actionPerformed(ActionEvent e) {
			cardLayout.show(panel, STORY);
		}
	}

	

}
