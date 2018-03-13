// PlayerWW.java
/**
* CSCI E-10b, Term Project
*
* The PlayerWW class is an object made to be a character in the game. There are
* three types of possible PlayerWW objects: The user which can be controlled,
* an AI which acts on its own, or a wall which stands still and protects other
* PlayerWW objects. The main variables that are important are the health,
* strength, and speed int values which determine the effectiveness of the
* PlayerWW performing in-game actions. PlayerWW.java implements Serializable
* so that its state can be saved and loaded back in later.
*
*	@Creator:	William B. Werner
* @Last Edited: 5/5/16
*/
import javax.swing.*;
import javax.swing.event.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

class PlayerWW implements Serializable
{
	// Instance variables
	String name;
	String playerType;
	String pType;
	int strength;
	int speed;
	int health;
	// Position values determine placement of battle screen
	int posX;
	int posY;
	int ammo;
	/**
  * This method is a constructor that creates a specified type of PlayerWW
	* object.
	*
	* @Param	String type		Determines type of PlayerWW object to be created
	*/
	PlayerWW(String type)
	{
		name = null;
		ammo = 0;
		posX = 0;
		posY = 0;

		if (type.equals("AI"))
		{
			name = "Compy";
			pType = "Enemy";

			// Randomly generates AI stat type
			int typeCast = rInt(1, 4);
			if (typeCast == 1) {genStrong(); playerType = "Strong";}
			else if (typeCast == 2) {genFast(); playerType = "Fast";}
			else if (typeCast == 3) {genSturdy(); playerType = "Sturdy";}
			else if (typeCast == 4) {genBalanced(); playerType = "Balanced";}
		}
		else if (type.equals("Wall"))
		{
			name = "Wall";
			pType = "Wall";
			health = 1;
		}
	}
	// Stat generation methods
  // This method creates a PlayerWW object with high strength and low speed
	public void genStrong ()
	{
		playerType = "Strong";
		strength = 3;	speed = 1;	health =2;
	}
	// This method creates a PlayerWW object with high speed and low health
	public void genFast ()
	{
		playerType = "Fast";
		strength = 2;	speed = 3;	health =1;
	}
	// This method creates a PlayerWW object with high health and low strength
	public void genSturdy()
	{
		playerType = "Sturdy";
		strength = 1;	speed = 2;	health =3;
	}
	// This method creates a PlayerWW object with equally balanced stats
	public void genBalanced()
	{
		playerType = "Balanced";
		strength = 2;	speed = 2;	health =2;
	}
	/**
  * This method creates a String value of the Players stats and info to be
	* read into a menu display during battles.
	*
	* @return	String	Returns with a String value of the Player's information
	*/
	public String playerMenu()
	{
		return (name + "\n" + playerType + " player" + "\nAmmo: " + ammo +
												"\nHealth: " + health + "\nStrength: " + strength +
												"\nSpeed: " + speed);
	}
	// Random number generator for creating random AI types
	public int rInt(int x, int y)
	{
		return x + (int) ((y - x + 1) * Math.random());
	}
}
