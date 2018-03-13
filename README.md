# SnowBallWar
Game
 William Bradley Werner CSCIE 10b Java II Term Project Write Up
­ INSTRUCTIONS:
Winter Wars Term Project
To start the program, run WinterWars.java by typing java WinterWars into the terminal window or command line and press the ENTER key. This will open up the interactive JFrame and display the title page. The following files are all needed to run the operations in the main program...
Classes
● WinterWars.java
➞ Main program to be run. Extends the JFrame class and houses the display for the gameplay. This program calls the other sub programs to perform its functions.
● SnowBattle.java
➞ Sub program that extends the JPanel class. Displays the focus of the
program which is the battle scene. This program is called by the
WinterWars.java program during its run time.
● PlayerWW.java
➞ Object file for the character components of the game. The user controls one of these game pieces against other CPU versions.
Files
● SaveStates.txt
➞ Writes out all the titles of the save files. This file is read into the WinterWars.java program when a user loads a previous game state that they have saved.
● Instructions.txt
➞ List of game instructions for the user to reference at any time during
play. This file is read into a JTextField when the “Read Instructions”
button is clicked from the Main Menu Bar.
● Test1.ser
  
 ➞ A sample game state that may be loaded by users who would like to bypass the character creation portion of the game and simply play around with the battle mechanics.
­ DESCRIPTION:
Instructions for using game (Available in­game as well)
I. Getting Started
­ Creating a character
The first step in playing Winter Wars is creating a character. Selecting the “New Game” button from the Menu Bar on the bottom of the window will bring the user to a new screen that will greet the user and ask for a name. Any entry longer than 8 characters will be shortened to fit into the character display.
Next, the user will be asked to select a character type. Character types are built around the 3 main stats useful to play Winter Wars.
1. Health: Determines how much damage a player can take before being knocked out 2. Strength: Determines how much power your attacks have
3. Speed: Determines how quickly a Player moves in relation to the other combatants. (A Player with higher speed will always move before a slower Player. Players
with equal speeds will be decided by chance. There are 4 types of characters to choose from:
 1. Strong:
2. Fast:
3. Sturdy:
4. Balanced: Equally balances the 3 stats
More power but slower to act Health: 2, Strength: 3, Speed: 1 Quicker to act but easy to beat Health: 1, Strength: 2, Speed: 3
Can take more damage but hit softly Health: 3, Strength: 1, Speed: 2
Health: 2, Strength: 2, Speed: 2
Lastly in creating your character, the display will show the character information and ask for confirmation to proceed.

 ­ Choosing teams
After creating a character, the user must decide how many enemies will be faced from 1 ­ 3. Enemies will be generated at random between the 4 player types so matches will differ from each other.
Likewise, the user will pick from 0 ­ 2 random allies to be on their team.
Once the teams are set, the battle will commence.
­ Saving/Loading
Saving a game is performed in­game by clicking the “Save” button on the eastern border of the screen. The current game state will be suspended for later play.
Loading a saved game is performed by clicking the “Load Game” button on the Menu Bar on
the bottom of the window. This will open a new screen that lists the save files present. The user can type in the name of the file they which to load and then click the “Submit” button. This will resume a previous game save.
II. Battle Actions ­ Moving
In game, a player can either ADVANCE or RETREAT by clicking the “Move” button. In the forward position, there is a better chance of successfully attacking your enemies but also a greater chance to be hit in return.
­ Rolling
In order to attack an enemy, a player must first roll a snowball and add it to their ammo
store.
This is done by clicking the “Roll” button. Up to 3 snowballs can be held at one time.
­ Throwing
Having ammo, a player can then attack their enemies by clicking the “Throw” button and
then
clicking on the intended target. Targets that are farther away have a greater chance to
miss.
If the attack is successful, the target will lose health points equal to the attacker’s strength

 stat.
­ Building
One way for a player to protect themselves is to build up a wall of snow by clicking the
“Build
Wall” button. This will add a wall object in front of the player’s position. If there is already a wall in front of the player, the wall’s health will be increased by 1 up to 3 health points.
Once
built, walls will take damage instead of the players behind them.
III. Winning/Losing/Exiting ­ Winning/Losing
Players will perform battle actions until either all enemies are defeated or the user is defeated.
Completing one of these conditions will end the game and present a screen with the corresponding result.
­ Exiting Game
To leave the game, at any time, users may click the “Exit” button on the Menu Bar on the bottom of the window. Otherwise, a user may click the “Quit” button during a battle to exit
out.
­ FEATURES:
*See Instructions for a list of all in­game functions*
­ EXPANSION:
In the future, I would like expand WinterWars.java to include a much larger play scale. By this I mean that beyond a single battle, the gameplay would involve a more complete war campaign with multiple teams competing to hold different territories. Players would need to recruit team members and expand their hold over a map display with the ultimate goal of occupying all territory to achieve victory.
Apart from the gameplay, I would like to add graphics for battle actions such as throwing a snowball and add images to represent the different play objects to further immerse players into the game world. The game can definitely be upgraded

 to include design features such as colors, borders around text boxes, and player object animations.
The code for the main program is written in a way where new game functions such as recruitment and territory map displays could be easily added as different JPanel cards for the main JFrame’s Cardlayout. Object files would have to be created for these functions and then called upon as instance variables in the main program.
­ REACTIONS: 
The main lessons learned from this project were that planning program methods and information flow in advance is invaluable. Most of the complications came from not being prepared to have different objects interact with each other and not having the structure set up ahead of time. It became very time consuming to invent solutions to complications on the fly.
I’m overall pleased with the project, I believe it has solid bones and is written in a way that allows for further development. The game seems fun to me as it sets up a number of possible matchups and outcomes, it is not an easy win every time as one lucky or unlucky move can change the end result, and it still remains simple to understand. I’ve enjoyed building the WinterWars.java program and will definitely continue to develop its functionality to achieve further results.
­ William B. Werner
