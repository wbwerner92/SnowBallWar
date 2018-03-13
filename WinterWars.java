// WinterWars.java
/**
* CSCI E-10b, Term Project
*
* WinterWars.java is the main program for submission. It calls upon
* SnowBattle.java and PlayerWW.java to perform all of its functions.
* The point of the program is to create a playable game that pits one player
* (the user) against 1 - 3 opponents in a snowball fight. The player must
* choose between different battle options such as building a wall for defense,
* rolling a snowball to stockpile ammo, or attacking to throw a snowball at
* an enemy. Instructions are provided in-game by clicking the
* "Read Instructions" button on the main menu.
*
*	@Creator:	William B. Werner
* @Last Edited: 5/6/16
*/
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class WinterWars extends JFrame
{
	// Instance variables
	// Start screen display variables
	JButton newGame;
	JButton loadGame;
	JButton instructions;
	JButton exitGame;

	JPanel mainFrame;
	CardLayout layout;

	Boolean loadingGame;

	// Start screen
	JPanel titleScreen;
	// New Game screen
	JPanel newGameScreen;
	JLabel respondLabel;
	JTextField ngDisplay;
	JTextArea ngEntry;
	JButton submit;
	String data;
	int scene;	// tracks scenes of New Game menu
	int t1;	// team 1 int
	int t2;	// team 2 int
	int t3;	// number of walls int

	// Battle Screen
	SnowBattle battleScreen;
	PlayerWW user;
	PlayerWW [] team1;
	PlayerWW [] team2;
	PlayerWW [] walls;

	// Load Game Screen
	JPanel loadGameScreen;
	JTextArea lgSaves;
	JTextArea lgEntry;
	JLabel lgLabel;
	JLabel lgTitle;
	JButton load;
	String loadFile;
	// Main method
	public static void main(String [ ] args)
	{
		System.out.println ("Play game");
		WinterWars game = new WinterWars();
	}
	/**
  * This method is the constructor for WinterWars which is an extended JFrame
	* class. The constructor creates 4 different cards for a card layout and
	* switches between them depending on what the user wants to do.
  */
	WinterWars()
	{
		// Frame set up
		setTitle("Winter Wars");
		setSize(750, 450);
		setLocation(300, 150);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);

		// MouseTracker tracks button clicks
		MouseTracker mt = new MouseTracker();

		// Button List
		JPanel buttonList = new JPanel();

		newGame = new JButton("New Game");
		newGame.addMouseListener(mt);
		buttonList.add(newGame);

		loadGame = new JButton("Load Game");
		loadGame.addMouseListener(mt);
		buttonList.add(loadGame);

		instructions = new JButton("Read Instructions");
		instructions.addMouseListener(mt);
		buttonList.add(instructions);

		exitGame = new JButton("Exit");
		exitGame.addMouseListener(mt);
		buttonList.add(exitGame);

		// Center main display
		mainFrame = new JPanel();
		layout = new CardLayout();
		mainFrame.setLayout(layout);

		// Start screen display
		titleScreen = new JPanel();
		JTextField oTitle = new JTextField("Winter Wars");
		oTitle.setEditable(false);
		oTitle.setHorizontalAlignment(JTextField.CENTER);
		oTitle.setFont(new Font("Georgia", Font.PLAIN, 100));
		oTitle.setForeground(Color.BLUE.brighter());
		titleScreen.add(oTitle);
		mainFrame.add(titleScreen, "Start Screen");

		// Create PlayerWW screen display
		newGameScreen = new JPanel();
		newGameScreen.setLayout(new GridLayout(2, 1));
		ngDisplay = new JTextField();
		ngDisplay.setFont(new Font("Helvetica", Font.PLAIN, 18));
		ngDisplay.setHorizontalAlignment(JTextField.CENTER);
		ngDisplay.setEditable(false);
		JPanel south = new JPanel();
		ngEntry = new JTextArea(1, 10);
		ngEntry.setLineWrap(true);
		ngEntry.setWrapStyleWord(true);
		JScrollPane js = new JScrollPane(ngEntry);
		newGameScreen.add(ngDisplay);
		submit = new JButton("Submit");
		submit.addMouseListener(mt);
		respondLabel = new JLabel("ENTER RESPONSE HERE: ");
		south.add(respondLabel);
		south.add(js);
		south.add(submit);
		newGameScreen.add(south);
		mainFrame.add(newGameScreen, "New Game Screen");

		// Load Game screen display
		loadGameScreen = new JPanel();
		loadGameScreen.setLayout(new BorderLayout());
		JPanel tc4South = new JPanel();
		lgSaves = new JTextArea(20, 20);
		lgSaves.setLineWrap(true);
		lgSaves.setWrapStyleWord(true);
		lgSaves.setEditable(false);
		JScrollPane js2 = new JScrollPane(lgSaves);
		lgEntry = new JTextArea(1, 10);
		lgEntry.setLineWrap(true);
		lgEntry.setWrapStyleWord(true);
		JScrollPane js3 = new JScrollPane(lgEntry);
		lgLabel = new JLabel("File name: ");
		load = new JButton("Load");
		load.addMouseListener(mt);
		lgTitle = new JLabel("Save Files");
		JPanel tc4North = new JPanel();
		tc4North.add(lgTitle);
		tc4South.add(lgLabel);
		tc4South.add(js3);
		tc4South.add(load);
		loadGameScreen.add(tc4North, BorderLayout.NORTH);
		loadGameScreen.add(js2, BorderLayout.CENTER);
		loadGameScreen.add(tc4South, BorderLayout.SOUTH);
		mainFrame.add(loadGameScreen, "Load Screen");

		// Battle screen display
		// Creates default Battle State to avoid null entries
		t1 = 1; t2 = 1;	t3 = 0;
		team1 = new PlayerWW [t1];
		team2 = new PlayerWW [t2];
		walls = new PlayerWW [t3];
		user = new PlayerWW("AI");
		user.pType = "User";
		team1[0] = user;
		team2[0] = new PlayerWW("AI");
		battleScreen = new SnowBattle(team1, team2, walls, false);
		mainFrame.add(battleScreen, "Battle Screen");

		add(mainFrame, BorderLayout.CENTER);

		add(buttonList, BorderLayout.SOUTH);

		setVisible(true);
		loadingGame = false;
	}
	/**
  * This method opens the new game screen and character creationg interface.
	* A new PlayerWW object is created and altered to match different
	* specifications indicated by user input.
  */
	public void startNewGame()
	{
		layout.show(mainFrame, "New Game Screen");
		user = new PlayerWW("User");
		// Scene guides the display and input when the submit button is pressed
		// Different scense depict different messages when clicked
		scene = 1;
		ngDisplay.setText("Welcome to the Snowball fight of your life!"
												+ " What is your name soldier?");
		respondLabel.setText("ENTER RESPONSE HERE: ");
	}
	/**
  * This method switches the main display to the loading Screen where a user
	* can load a previous save file. The screen writes out all of the save files
	* and then the user types in the name of the desired file and the program
	* writes back the serialized data into the corresponding slots.
  */
	public void loadGame()
	{
		layout.show(mainFrame, "Load Screen");
		loadFile = null;
		try
		{
			Scanner fr = new Scanner(new File("SaveStates.txt"));
			lgSaves.setText("");
			int fileNum = 0;
			while (fr.hasNext())
			{
				fileNum ++;
				lgSaves.append(fileNum + ":" + fr.nextLine() + "     ");

			}
		}
		catch(FileNotFoundException fnfe){}
	}
	/**
  * This method starts a battle scene. If the boolean value "loadingGame" is
	* true then it will load a previous game state. Otherwise, the program will
	* create an entirely new and fresh battle scene.
  */
	public void battle()
	{
		// Updates Player Set for new Battle
		if (loadingGame == false)
		{
			team1 = new PlayerWW [t1 + 1];
			team2 = new PlayerWW [t2];
			walls = new PlayerWW [t3];
			team1[0] = user;
			for (int i = 1; i <= t1; i++)
			{
				team1[i] = new PlayerWW("AI");
			}
			for (int j = 0; j < t2; j++)
			{
				team2[j] = new PlayerWW("AI");
			}
		}

		battleScreen = new SnowBattle(team1, team2, walls, loadingGame);
		mainFrame.add(battleScreen, "Battle Screen");
		layout.show(mainFrame, "Battle Screen");
		battleScreen.battle();
	}
	//class mouseTracker
	class MouseTracker extends MouseAdapter implements MouseMotionListener
	{
		public void mouseClicked(MouseEvent e)
		{
			if (e.getSource() == newGame)
			{
				loadingGame = false;
				startNewGame();
			}
			else if (e.getSource() == exitGame)
			{
				System.exit(0);
			}
			else if (e.getSource() == submit)
			{
				if (scene == 1)
				{
					data = ngEntry.getText();
					ngEntry.setText("");
					if (data.length() > 8) data = data.substring(0, 8);	// Prevents character names that are too long
					if (data.length() < 1)	// Prevents empty character names
					{
						JOptionPane.showMessageDialog(null, "No name entered");
					}
					else
					{
						user.name = data;
						ngDisplay.setText("Are you sure your name is " + user.name + "? (Y/N)");
						scene = 2;
					}
				}
				else if (scene == 2)
				{
					data = ngEntry.getText();
					ngEntry.setText("");
					// Prevents blank responses
					if (data.length() < 1)
					{
						JOptionPane.showMessageDialog(null, "Invalid response");
					}
					else if (data.substring(0, 1).equalsIgnoreCase("Y"))
					{
						scene = 3;
						ngDisplay.setText("What type of Snowball Fighter are you? Strong? Fast?" +
															" Sturdy? Balanced?");
					}
					else if (data.substring(0, 1).equalsIgnoreCase("N"))
					{
						ngDisplay.setText("Welcome to the Snowball fight of your life!"
																+ " What is your name soldier?");
						scene = 1;
					}
				}
				else if (scene == 3)
				{
					data = ngEntry.getText();
					ngEntry.setText("");

					if (!data.equalsIgnoreCase("Strong")
							&& !data.equalsIgnoreCase("Fast")
							&& !data.equalsIgnoreCase("Sturdy")
							&& !data.equalsIgnoreCase("Balanced"))
					{
						return;
					}
					else
					{
						user.playerType = data;
						scene = 4;
						ngDisplay.setText("Are you sure you are a "
												+ user.playerType.toLowerCase() + " player?(Y/N)");
					}
				}
				else if (scene == 4)
				{
					data = ngEntry.getText();
					ngEntry.setText("");
					// Prevents blank responses
					if (data.length() < 1)
					{
						JOptionPane.showMessageDialog(null, "Invalid response");
					}
					else if (data.substring(0, 1).equalsIgnoreCase("Y"))
					{
						if (user.playerType.equalsIgnoreCase("Strong"))
						{
							user.genStrong();
						}
					  else if (user.playerType.equalsIgnoreCase("Fast"))
						{
							user.genFast();
						}
					  else if (user.playerType.equalsIgnoreCase("Sturdy"))
						{
							user.genSturdy();
						}
					  else if (user.playerType.equalsIgnoreCase("Balanced"))
						{
							user.genBalanced();
						}
						ngDisplay.setText("Name: " + user.name + " Type: " + user.playerType +
												" Health: " + user.health + " Strength: " + user.strength
												+ " Speed: " + user.speed);
						System.out.println(user.playerMenu());
						respondLabel.setText("Continue?(Y/N): ");
						scene = 5;
					}
					else if (data.substring(0, 1).equalsIgnoreCase("N"))
					{
						ngDisplay.setText("What type of Snowball Fighter are you? Strong? Fast?" +
															" Sturdy? Balanced?");
						scene = 3;
					}
				}
				else if (scene == 5)
				{
					data = ngEntry.getText();
					ngEntry.setText("");
					// Prevents blank responses
					if (data.length() < 1)
					{
						JOptionPane.showMessageDialog(null, "Invalid response");
					}
					else if (data.substring(0, 1).equalsIgnoreCase("Y"))
					{
						ngDisplay.setText("How many opponents are there " +
												user.name + " (1, 2, or 3)?");
						respondLabel.setText("ENTER RESPONSE HERE: ");
						scene = 6;
					}
					else if (data.substring(0, 1).equalsIgnoreCase("N"))
					{
						layout.show(mainFrame, "Start Screen");
					}
				}
				else if (scene == 6)
				{
					data = ngEntry.getText();
					ngEntry.setText("");
					try
					{
						int i = Integer.parseInt(data);
						if (i == 1 || i == 2 || i == 3)
						{
							t2 = i;
							ngDisplay.setText("How many allies are there " +
													user.name + " (0, 1, or 2)?");
							scene = 7;
						}
					}
					catch(NumberFormatException nfe)
					{
						System.out.println("Not an integer!");
					}
				}
				else if (scene == 7)
				{
					data = ngEntry.getText();
					ngEntry.setText("");
					try
					{
						int i = Integer.parseInt(data);
						if (i == 0 || i == 1 || i == 2)
						{
							t1 = i;
							battle();
						}
					}
					catch(NumberFormatException nfe)
					{
						System.out.println("Not an integer!");
					}
				}
			}
			else if (e.getSource() == loadGame)
			{
				loadGame();
			}
			else if (e.getSource() == load)
			{
				loadFile = lgEntry.getText();
				lgEntry.setText("");
				try
				{
					ObjectInputStream input = new ObjectInputStream(new FileInputStream(loadFile));
					t1 = input.readInt();	team1 = new PlayerWW [t1];
					t2 = input.readInt();	team2 = new PlayerWW [t2];
					t3 = input.readInt();	walls = new PlayerWW [t3];
					System.out.println (t1 + " " + t2 + " " + t3);
					try
					{
						for (int i = 0; i < t1; i++)
						{
							team1[i] = (PlayerWW)input.readObject();
						}
						for (int j = 0; j < t2; j++)
						{
							team2[j] = (PlayerWW)input.readObject();
						}
						for (int k = 0; k < t3; k++)
						{
							walls[k] = (PlayerWW)input.readObject();
						}

					}
					catch (ClassNotFoundException cnfe){}
					input.close();
					System.out.println ("Input has entered");
					System.out.println (team1[0].name + " has re-entered the game");
					loadingGame = true;
					battle();
				}
				catch(FileNotFoundException fnfe)
				{
					JOptionPane.showMessageDialog(null, "No such file");
				}
				catch(IOException ioe){}
			}
			else if (e.getSource() == instructions)
			{
				JFrame frame = new JFrame("Winter Wars Instruction Guide");
				frame.setSize(400, 500);
				frame.setLocation(400, 200);
				frame.setResizable(false);
				JTextArea ins = new JTextArea();
				ins.setEditable(false);
				JScrollPane jsp = new JScrollPane(ins);
				try
				{
					Scanner s = new Scanner(new File("Instructions.txt"));
					while (s.hasNext())
					{
						ins.append(s.nextLine() + "\n");
					}
				}
				catch (FileNotFoundException fnfe) {}
				frame.add(jsp);
				frame.setVisible(true);
				// Sets view of Instruction sheet to top of page
				ins.setSelectionStart(0);
				ins.setSelectionEnd(0);
			}
		}
	}
}
