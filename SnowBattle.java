// SnowBattle.java
/**
* CSCI E-10b, Term Project
*
* SnowBattle.java is an object created to display the battle component of the
* game. It extends the JPanel class and is input into the main WinterWars
* program to be displayed. It contains PlayerWW arrays to hold the combatants
* and uses other JPanels and JButtons to allow the user to interact with the
* scene and make decisions.
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

class SnowBattle extends JPanel

{
	// Instance Variables
	PlayerWW [][] combatants;
	PlayerWW [] team1;
	PlayerWW [] team2;
	PlayerWW [] walls;
	PlayerWW [] order;
	int orderPos;
	boolean throwing;
	boolean complete;

	// North Panel
	JTextArea display;

	// Center Panel
	JPanel center;
	JButton [][] jba;

	// East Panel
	JPanel east;
	JButton save;
	JButton quit;

	// West Panel
	JPanel west;
	JTextArea status;

	// South Panel
	JPanel south;
	JButton move;
	JButton attack;
	JButton build;
	JButton roll;


	/**
  * This method is the constructor for the SnowBattle class. It takes in a
	* layout of parameters to set the scene. 3 PlayerWW arrays are taken to set
	* the teams and a Boolean value of loading is taken to indicate if this is
	* a new game with fresh data or an old game with existing data.
	* The Jpanel is constructed to house differen display and JButton options
	* as well as a JTextArea that is continually updated to show the actions
	* each player takes as the game progresses
	*
	* @Param  PlayerWW [] t1		t1 is set to the User's team
	* @Param  PlayerWW [] t2		t2 is set to the enemy team
	* @Param  PlayerWW [] t3		t3 is the set of wall objects present
	* @Param  Boolean loading		loading determines if it is a new game or not
  */
	SnowBattle(PlayerWW [] t1, PlayerWW [] t2, PlayerWW [] t3, Boolean loading)
	{
		// Parent tracker telling program that battle is in progress
		complete = false;

		// Adjust combatants enterance
		team1 = t1;
		team2 = t2;
		walls = t3;

		MouseTracker mt = new MouseTracker();

		setLayout(new BorderLayout());

		// North Text Display
		display = new JTextArea();
		display.setFont(new Font("Helvetica", Font.BOLD, 18));
		display.setLineWrap(true);
		display.setWrapStyleWord(true);
		display.setEditable(true);
		JScrollPane js = new JScrollPane(display);
		js.setPreferredSize(new Dimension(200, 50));

		// Center Panel
		center = new JPanel();
		center.setLayout(new GridLayout(3, 8));
		jba = new JButton [3][8];
		for (int i = 0; i < 3; i++)
		{
			for (int j = 0; j < 8; j++)
			{
				jba[i][j] = new JButton("Empty");
				jba[i][j].addMouseListener(mt);
				jba[i][j].setVisible(false);
				center.add(jba[i][j]);
			}
		}
		// Placment sorting system
		if (!loading)
		{
			team1[0].posX = 0;	team1[0].posY = 1;
			if (team1.length > 1){team1[1].posX = 0; team1[1].posY = 0; team1[1].name = "Ally:1";}
			if (team1.length > 2){team1[2].posX = 0; team1[2].posY = 2; team1[2].name = "Ally:2";}
			team2[0].posX = 7;	team2[0].posY = 1;	team2[0].name = "Enemy:1";
			if (team2.length > 1){team2[1].posX = 7; team2[1].posY = 0; team2[1].name = "Enemy:2";}
			if (team2.length > 2){team2[2].posX = 7; team2[2].posY = 2; team2[2].name = "Enemy:3";}
		}
		team1[0].pType = "User";	// Ensures first member is the user
		// South JPanel
		south = new JPanel();
		// move switches “Advance” with “Retreat” upon movement and vice versa
		move = new JButton("Advance");
		move.addMouseListener(mt);
		south.add(move);

		attack = new JButton("Throw");
		attack.addMouseListener(mt);
		south.add(attack);

		build = new JButton("Build");
		build.addMouseListener(mt);
		south.add(build);

		roll = new JButton("Roll");
		roll.addMouseListener(mt);
		south.add(roll);

		// Save/Quit Panel
		east = new JPanel();
		east.setLayout(new GridLayout(2, 1));

		save = new JButton("Save");
		save.addMouseListener(mt);
		east.add(save);

		quit = new JButton("Quit");
		quit.addMouseListener(mt);
		east.add(quit);

		// Status Panel
		west = new JPanel();
		west.setLayout(new BorderLayout());

		status = new JTextArea();
		status.setEditable(false);
		west.add(status);

		// Add all elements
		add(js, BorderLayout.PAGE_START);
		add(west, BorderLayout.LINE_START);
		add(center, BorderLayout.CENTER);
		add(east, BorderLayout.LINE_END);
		add(south, BorderLayout.PAGE_END);
		// Sets the screen after the data is created and organized
		setScreen();
		if (!loading) display.append("Battle begin!!!");
	}
	/**
  * This method sets the battle and AI actions in motion. It sets the initial
	* battle data and updates the screen display.
  */
	public void battle()
	{
		throwing = false;
		setScreen();

		order = turnOrder(team1, team2);

		orderPos = 0;
		runAI();
	}
	/**
  * This method runs AI action until it is the User's turn to act. A PlayerWW
	* array is used to cycle through all playable characters and has them make
	* an action.
  */
	public void runAI()
	{
		if (complete) return; // Doesn't allow action if game is over

		boolean userTurn = false; // allows for AI to act
		// Resets the order if the cycle of PlayerWW is complete
		if (orderPos >= order.length)
		{
			order = turnOrder(team1, team2);
			orderPos = 0;
		}

		for (int i = orderPos; i < order.length; i++)
		{
			if (complete) break;
			orderPos++;
			System.out.println ("Order Postion = " + orderPos);
			if (order[i].health == 0) {continue;}
			if (order[i].pType.equals("User"))
			{
				System.out.println ("User turn");
				display.append("\nWhat will you do?");
				userTurn = true;
				break;
			}
			else AITurn(order[i]);
		}

		if (complete) return;
		// *IMPORTANT* reruns the method if it is not the user's turn to continue
		// the game
		if (!userTurn) runAI();
	}
	/**
  * This method has AI performing actions based on current scenario. Depending
	* on different scenarios of having or not having ammo, a built wall, or
	* being in the forward or back position, an AI will act based on likelyehood
	* or reaction to that scenario using randomly generated numbers.
	*
	* @Param  PlayerWW pw		AI to perform action
  */
	public void AITurn(PlayerWW pw)
	{
		// Selects nearest enemy to be target if attack is executed
		PlayerWW target = new PlayerWW("AI");
		int distHold = 10;
		int dist = 0;

		if (pw.posX > 4)
		{
			for (int i = 0; i < team1.length; i++)
			{
				if (team1[i].health <= 0) continue;
				dist=(Math.abs(pw.posX-team1[i].posX)+Math.abs(pw.posY-team1[i].posY));
				if (dist < distHold)
				{
					target = team1[i];
					distHold = dist;
				}
			}
		}
		else
		{
			for (int j = 0; j < team2.length; j++)
			{
				if (team2[j].health <= 0) continue;
				dist=(Math.abs(pw.posX-team2[j].posX)+Math.abs(pw.posY-team2[j].posY));
				if (dist < distHold)
				{
					target = team2[j];
					distHold = dist;
				}
			}
		}
		// Case1: No ammo, no wall, back space		(Likely to roll or slightly likely to build)
		// Case2: No ammo, no wall, front space		(Likely to roll or retreat)
		// Case3: No ammo, some wall, back space	(Roll)
		// Case4: No ammo, some wall, front space (Likely to roll or somewhat likely to retreat)
		// Case5: No ammo, full wall, back space	(Roll)
		// Case6: No ammo, full wall, front space (Likely to roll or slightly likely retreat)
		if (pw.ammo == 0)	// Cases 1 - 6
		{
			if (pw.posX > 4)	// Enemy case
			{
				for (PlayerWW w : walls)
				{
					if ((w.posY == pw.posY) && (w.posX == (pw.posX - 1)))
					{
						if ((w.health < 3) && (pw.posX == 7)) // Case3
						{
							roll(pw); return;
						}
						else if ((w.health < 3) && (pw.posX == 5)) // Case4
						{
							if (rInt(1,5) > 2) {roll(pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 7)) // Case5
						{
							roll(pw); return;
						}
						else if ((w.health == 3) && (pw.posX == 5)) // Case6
						{
							if (rInt(1,5) > 1) {roll(pw);}
							else {move(pw);}
							return;
						}
					}
				}
				if (pw.posX == 7)	// Case1
				{
					if (rInt(1,5) > 1) {roll(pw);}
					else {buildWall(pw);}
					return;
				}
				else if (pw.posX == 5) // Case2
				{
					if (rInt(1,4) > 2) {roll(pw);}
					else {move(pw);}
					return;
				}
			}
			else							// Ally case
			{
				for (PlayerWW w : walls)
				{
					if ((w.posY == pw.posY) && (w.posX == (pw.posX + 1)))
					{
						if ((w.health < 3) && (pw.posX == 0)) // Case3
						{
							roll(pw); return;
						}
						else if ((w.health < 3) && (pw.posX == 2)) // Case4
						{
							if (rInt(1,5) > 2) {roll(pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 0)) // Case5
						{
							roll(pw); return;
						}
						else if ((w.health == 3) && (pw.posX == 2)) // Case6
						{
							if (rInt(1,5) > 1) {roll(pw);}
							else {move(pw);}
							return;
						}
					}
				}
				if (pw.posX == 0)	// Case1
				{
					if (rInt(1,5) > 1) {roll(pw);}
					else {buildWall(pw);}
					return;
				}
				else if (pw.posX == 2) // Case2
				{
					if (rInt(1,4) > 2) {roll(pw);}
					else {move(pw);}
					return;
				}
			}
		}
		// Case7: Some ammo, no wall, back space			(Likely to roll or slightly likely to build)
		// Case8: Some ammo, no wall, front space			(Likely to build or slightly likely to throw)
		// Case9: Some ammo, some wall, back space		(Likely to roll or somewhat likely to advance)
		// Case10: Some ammo, some wall, front space	(Liekly to throw or somewhat likely to roll)
		// Case11: Some ammo, full wall, back space		(Likely to advance or slightly likely to roll)
		// Case12: Some ammo, full wall, front space	(Throw)
		else if (pw.ammo < 3)
		{
			if (pw.posX > 4)	// Enemy case
			{
				for (PlayerWW w : walls)
				{
					if ((w.posY == pw.posY) && (w.posX == (pw.posX - 1)))
					{
						if ((w.health < 3) && (pw.posX == 7)) // Case9
						{
							if (rInt(1, 5) > 2) {roll(pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health < 3) && (pw.posX == 5)) // Case10
						{
							if (rInt(1,5) > 2) {attack(target, pw);}
							else {roll(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 7)) // Case11
						{
							if (rInt(1,5) > 1) {move(pw);}
							else {roll(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 5)) // Case12
						{
							attack(target, pw); return;
						}
					}
				}
				if (pw.posX == 7)	// Case7
				{
					if (rInt(1,5) > 1) {roll(pw);}
					else {buildWall(pw);}
					return;
				}
				else if (pw.posX == 5) // Case8
				{
					if (rInt(1,5) > 1) {buildWall(pw);}
					else {attack(target, pw);}
					return;
				}
			}
			else							// Ally case
			{
				for (PlayerWW w : walls)
				{
					if ((w.posY == pw.posY) && (w.posX == (pw.posX + 1)))
					{
						if ((w.health < 3) && (pw.posX == 0)) // Case9
						{
							if (rInt(1, 5) > 2) {roll(pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health < 3) && (pw.posX == 2)) // Case10
						{
							if (rInt(1,5) > 2) {attack(target, pw);}
							else {roll(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 0)) // Case11
						{
							if (rInt(1,5) > 1) {move(pw);}
							else {roll(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 2)) // Case12
						{
							attack(target, pw); return;
						}
					}
				}
				if (pw.posX == 0)	// Case7
				{
					if (rInt(1,5) > 1) {roll(pw);}
					else {buildWall(pw);}
					return;
				}
				else if (pw.posX == 2) // Case8
				{
					if (rInt(1,5) > 1) {buildWall(pw);}
					else {attack(target, pw);}
					return;
				}
			}
		}
		// Case13: Full ammo, no wall, back space			(Likely to advance or somewhat likely to throw)
		// Case14: Full ammo, no wall, front space		(Likely to throw or somewhat likely to build)
		// Case15: Full ammo, some wall, back space		(Likely to throw or slightly likely to advance)
		// Case16: Full ammo, some wall, front space	(Throw)
		// Case17: Full ammo, full wall, back space		(Liekly to throw or somewhat likely to advance)
		// Case18: Full ammo, full wall, front space	(Throw)
		else if (pw.ammo == 3)
		{
			if (pw.posX > 4)	// Enemy case
			{
				for (PlayerWW w : walls)
				{
					if ((w.posY == pw.posY) && (w.posX == (pw.posX - 1)))
					{
						if ((w.health < 3) && (pw.posX == 7)) // Case15
						{
							if (rInt(1, 5) > 1) {attack(target, pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health < 3) && (pw.posX == 5)) // Case16
						{
							attack(target, pw);
							return;
						}
						else if ((w.health == 3) && (pw.posX == 7)) // Case17
						{
							if (rInt(1,5) > 2) {attack(target, pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 5)) // Case18
						{
							attack(target, pw);
							return;
						}
					}
				}
				if (pw.posX == 7)	// Case13
				{
					if (rInt(1,5) > 2) {move(pw);}
					else {attack(target, pw);}
					return;
				}
				else if (pw.posX == 5) // Case14
				{
					if (rInt(1,5) > 2) {attack(target, pw);}
					else {buildWall(pw);}
					return;
				}
			}
			else 									// Ally case
			{
				for (PlayerWW w : walls)
				{
					if ((w.posY == pw.posY) && (w.posX == (pw.posX + 1)))
					{
						if ((w.health < 3) && (pw.posX == 0)) // Case15
						{
							if (rInt(1, 5) > 1) {attack(target, pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health < 3) && (pw.posX == 2)) // Case16
						{
							attack(target, pw);
							return;
						}
						else if ((w.health == 3) && (pw.posX == 0)) // Case17
						{
							if (rInt(1,5) > 2) {attack(target, pw);}
							else {move(pw);}
							return;
						}
						else if ((w.health == 3) && (pw.posX == 2)) // Case18
						{
							attack(target, pw);
							return;
						}
					}
				}
				if (pw.posX == 0)	// Case13
				{
					if (rInt(1,5) > 2) {move(pw);}
					else {attack(target, pw);}
					return;
				}
				else if (pw.posX == 2) // Case14
				{
					if (rInt(1,5) > 2) {attack(target, pw);}
					else {buildWall(pw);}
					return;
				}
			}
		}
	}
	/**
  * This method orders all combatants by their speed stat into one array to
	* arrange the order they will perform actions. A nested for loop is used to
	* parse through all combatants and determine whose speed is the highest. If
	* there is a tie, then a random chance is used to pick the faster.
	*
	* @Param		PlayerWW [] one		User's team to be ordered
	* @Param		PlayerWW [] two		Enemy's team to be ordered
	* @return		PlayerWW []		Returns with the speed ordered array
  */
	public PlayerWW [] turnOrder(PlayerWW [] one, PlayerWW [] two)
	{
		// Prevents knocked out players from being entered into greater array
		int num = 0;
		for (PlayerWW pw1 : one)
		{
			if (pw1.health > 0) num++;
		}
		for (PlayerWW pw2 : two)
		{
			if (pw2.health > 0) num++;
		}
		// New array to hold all combatants
		PlayerWW [] order = new PlayerWW[num];
		// Fills new array with all combatants
		num = 0;
		for (int i = num; i < one.length; i++)
		{
			if (one[i].health <= 0) continue;
			order[num] = one[i];
			num++;
		}
		for (int j = 0; j < two.length; j++)
		{
			if (two[j].health <= 0) continue;
			order[num] = two[j];
			num++;
		}
		// Sorts PlayerWW objects by speed rolls
		for (int k = 1; k < order.length; k++)
    {
      PlayerWW temp; // hold variable to switch order

			for (int l = 0; l < order.length - 1; l++)
			{
				if (order[l].speed < order[l+1].speed)
				{
					temp = order[l];
					order[l] = order[l+1];
					order[l+1] = temp;
				}
				else if (order[l].speed == order[l+1].speed)
				{
					if (rInt(1, 2) == 1)
					{
						temp = order[l];
						order[l] = order[l+1];
						order[l+1] = temp;
					}
				}
			}
		}
		int o = 1;
		for (PlayerWW pw : order)
		{
			System.out.println (pw.name + " #" + o);
			o++;
		}
		return order;
	}
	/**
  * This method allows a PlayerWW to move either back or forward when the
	* corresponding button is clicked or action is needed by changing the
	* PlayerWW's posX variable and then updating the screen to reflect this
	* change.
	*
	* @Param PlayerWW pw	PlayerWW to be moved
	*/
	public void move(PlayerWW pw)
	{
		if (pw.posX == 0 || pw.posX == 5)
		{
			jba[pw.posY][pw.posX].setVisible(false);
			pw.posX += 2;
			if (pw.pType.equals("User")) move.setText("Retreat");
		}
		else if (pw.posX == 2 || pw.posX == 7)
		{
			jba[pw.posY][pw.posX].setVisible(false);
			pw.posX -= 2;
			if (pw.pType.equals("User")) move.setText("Advance");
		}
		display.append("\n" + pw.name + " moves");
		setScreen();
	}
	/**
  * This method increases a PlayerWW's ammo by 1 to a max of 3 so that they
	* can use their ammo to attack other players.
	*
	* @Param	PlayerWW pw		PlayerWW to increase ammo
	*/
	public void roll(PlayerWW pw)
	{
		if (pw.ammo >= 3)
		{
			display.append("\n" + pw.name + " can't hold anymore.");
			return;
		}
		display.append("\n" + pw.name + " rolls one snowball!");
		pw.ammo ++;
		setScreen();
	}
	/**
  * This method has one PlayerWW attack another. An integer atk is set to the
	* attacking PlayerWW's strength stat and then based on their distance from
	* the target has a chance to connect. If there is a wall object in front of
	* the target, the wall takes the attack instead.
	*
	* @Param	PlayerWW pw2	PlayerWW be attacked
	* @Param	PlayerWW pw		Attacking PlayerWW
	*/
	public void attack(PlayerWW pw2, PlayerWW pw)
	{
		System.out.println("Strength = " + pw.strength);
		int atk = pw.strength;
		System.out.println ("Attack = " + atk);
		PlayerWW target = pw2;
		// Returns blank if the attacker is out of ammo
		if (pw.ammo <= 0)
		{
			display.append("\n" + pw.name + " is out of ammo.");
			return;
		}
		// Switches target to wall if target has built protection
		if (pw2.posX > 4)
		{
			for (PlayerWW w : walls)
			{
				if ((w.posY == pw2.posY) && (w.posX == (pw2.posX - 1)) && w.health > 0)
				{
					target = w;
					break;
				}
			}
		}
		else if (pw2.posX < 4)
		{
			for (PlayerWW w : walls)
			{
				if ((w.posY == pw2.posY) && (w.posX == (pw2.posX + 1)) && w.health > 0)
				{
					target = w;
					break;
				}
			}
		}
		// Calculates distance between thrower and target
		int dist = (Math.abs(pw.posX - target.posX)
								+ Math.abs(pw.posY - target.posY));
		display.append("\nA snowball is thrown at " + target.name + "!");
		System.out.println ("Attack = " + atk);
		System.out.println ("Distance = " + dist);
		// Calculate strike accuracy
		int strike = rInt(1, 10);
		// If strike is larger than the distance from the target it is a hit!
		if (strike > dist)
		{
			display.append("\nIt's a HIT! " + target.name + " takes " + atk
										 + " damage!");
			target.health -= atk;
			if (target.health <= 0) playerKnockOut(target);
		}
		else {display.append("\nIt's a MISS!");}

		pw.ammo --;
		throwing = false;
	}
	/**
  * This method creates a PlayerWW object named "Wall" in front of a PlayerWW
	* to protect them from attacks. The wall object starts with 1 health but can
	* be built up to 3.
	*
	* @Param	PlayerWW pw		PlayerWW to build wall
	*/
	public void buildWall(PlayerWW pw)
	{
		PlayerWW newWall = new PlayerWW("Wall");

		if (pw.posX < 4)
		{
			// Checks for existing Wall objects
			for (PlayerWW w : walls)
			{
				if ((w.posY == pw.posY) && (w.posX == (pw.posX + 1)))
				{
					if (w.health < 3)
					{
						w.health ++;
						display.append("\n" + pw.name + " packs snow onto their wall.");
					}
					setScreen();
					return;
				}
			}
			// Continues with new wall construction if the slot is empty
			newWall.posX = pw.posX + 1;
			newWall.posY = pw.posY;
		}
		else
		{
			for (PlayerWW w : walls)
			{
				if ((w.posY == pw.posY) && (w.posX == (pw.posX - 1)))
				{
					if (w.health < 3)
					{
						w.health ++;
						display.append("\n" + pw.name + " packs snow onto their wall.");
					}
					setScreen();
					return;
				}
			}
			newWall.posX = pw.posX - 1;
			newWall.posY = pw.posY;
		}
		// Adds new wall if no wall is present in front of PlayerWW
		PlayerWW [] temp = new PlayerWW [walls.length + 1];
		for (int i = 0; i < walls.length; i++)
		{
			if (walls.length > 0){temp[i] = walls[i];}
		}

		temp [temp.length - 1] = newWall;
		walls = temp;
		display.append("\n" + pw.name + " builds a protective wall of Snow");
		setScreen();
	}
	/**
  * This method handles PlayerWW's who have been knocked out of battle from
	* losing all their health. It also ends the game if either all enemies are
	* defeated or the user is defeated and displays the corresponding defeat or
	* victory message.
	*
	* @Param	PlayerWW pw		PlayerWW to be removed from battle
	*/
	public void playerKnockOut(PlayerWW pw)
	{
		display.append("\n" + pw.name + " is knocked out!");
		pw.health = 0;	// Prevents negative health when rebuilding broken walls
		jba[pw.posY][pw.posX].setVisible(false);
		// Determines if all enemies have been defeated
		int enemyHP = 0;
		for (PlayerWW p2 : team2)
		{
			enemyHP += p2.health;
		}
		if (enemyHP == 0)
		{
			JOptionPane.showMessageDialog(null, "YOU WIN!!!");
			complete = true;
		}

		if (team1[0].health == 0)
		{
			JOptionPane.showMessageDialog(null, "YOU LOSE");
			complete = true;
		}
	}
	// Creates a super Array to hold all PlayerWW objects
	public void setCombatants()
	{
		combatants = new PlayerWW [3][];
		combatants[0] = team1;
		combatants[1] = team2;
		combatants[2] = walls;
	}
  //This method sets the display screen to reflect the current data.
	public void setScreen()
	{
		status.setText("\n\n\n\n\n\n\n" + team1[0].playerMenu());
		setCombatants();
		throwing = false;
		// Makes all JButtons with corresponding PLayerWW objects visible on screen
		for (int i = 0; i < combatants.length; i++)
		{
			for (int j = 0; j < combatants[i].length; j++)
			{
				if (combatants[i][j].health <= 0) continue;	// Prevents Knocked out objects from being visible
				jba[combatants[i][j].posY][combatants[i][j].posX].setVisible(true);
				jba[combatants[i][j].posY][combatants[i][j].posX].setText(combatants[i][j].name);
			}
		}
		// Updates wall displays to include remaining health
		for (PlayerWW w : walls)
		{
			jba[w.posY][w.posX].setText(w.name + ": " + w.health);
		}
	}
	// Saves all PlayerWW objects in a serialized state to a file with the user's
	// name
	public void saveState() throws IOException, ClassNotFoundException
	{
		String saveName = (combatants[0][0].name + ".ser");
		// Verifies if Save name has already been made to avoid duplicates
		// Writes new Save name if it doesn't already exist
		System.out.println (combatants[0][0].name);
		try
		{
			Scanner input = new Scanner(new File("SaveStates.txt"));
			int intCheck = 0;
			while (input.hasNext())
			{
				if (saveName.equals(input.nextLine())) {intCheck++; break;}
			}
			if (intCheck == 0)
			{
				FileWriter fw = new FileWriter("SaveStates.txt", true);
				fw.write(saveName + "\n");
				fw.close();
			}
		}
		catch (IOException ioe){}
		// Seriales Objects from current SnowBattle Object to be read back later
		ObjectOutputStream os = new ObjectOutputStream (
								new FileOutputStream (saveName));

		// Tracks number of objects to be read in later for each array grouping
		os.writeInt(team1.length);
		os.writeInt(team2.length);
		os.writeInt(walls.length);
		// Writes all objects to file
		for (PlayerWW pw1 : team1)
		{
			os.writeObject(pw1);
			System.out.println ("Writing PlayerWW: " + pw1.name);
		}
		for (PlayerWW pw2 : team2)
		{
			os.writeObject(pw2);
			System.out.println ("Writing PlayerWW: " + pw2.name);
		}
		for (PlayerWW pw3 : walls)
		{
			os.writeObject(pw3);
			System.out.println ("Writing PlayerWW: " + pw3.name);
		}
		os.close();
	}
	//class mouseTracker
	class MouseTracker extends MouseAdapter implements MouseMotionListener
	{
		public void mouseClicked(MouseEvent e)
		{
			if (complete) return;
			// Checks all possible targets for a possible attack.
			// Attack is only exectued if boolean throwing is true and the target in
			// question is not the user.
			for (int i = 0; i < combatants.length; i++)
			{
				for (int j = 0; j < combatants[i].length; j++)
				{
					if (e.getSource() == jba[combatants[i][j].posY][combatants[i][j].posX]
							&& throwing && !combatants[i][j].pType.equals("User"))
					{
						attack(combatants[i][j], team1[0]);
						setScreen();
						runAI();
					}
				}
			}
			if (e.getSource() == move)
			{
				move(team1[0]);
				setScreen();
				runAI();
			}
			if (e.getSource() == roll)
			{
				roll(team1[0]);
				setScreen();
				runAI();
			}
			if (e.getSource() == attack)
			{
				throwing = true;
				display.append("\nAttack who?");
			}
			if (e.getSource() == build)
			{
				buildWall(team1[0]);
				setScreen();
				runAI();
			}
			if (e.getSource() == save)
			{
				if (JOptionPane.showConfirmDialog(null, "Would you like to save?") == 0)
				{
					System.out.println ("Saving...");
					try {saveState();}
					catch (IOException ioE){}
					catch (ClassNotFoundException cnfe){}
					System.out.println ("Save complete: " + team1[0].name + ".ser");
				}
				else System.out.println("Not saved");
			}
			if (e.getSource() == quit)
			{
				if (JOptionPane.showConfirmDialog(null, "Quit game?") == 0)
				{
					System.exit(0);
				}
			}
		}
	}
	// Random number generator for attack and defense rolls
	public int rInt(int x, int y)
	{
		return x + (int) ((y - x + 1) * Math.random());
	}

}
