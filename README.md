# Reversi

| Contents                                     |
|----------------------------------------------|
| [Description](#description)                  |
| [Original Brief](#original-brief)            |

## Description
A Java program for a two-player version of a Reversi game using Swing.

The original project was given as coursework in my first year of university. The project brief was very specific and made a few changes to the original game. The first few commits will feature the version submitted for that coursework, which received full marks. The following commits will then feature changes and additions I included to improve the game.

## Original Brief

A summary of the original brief, which outlines the requirements for a Reversi/Othello game implementation in Java. The main points are as follows:

1. General Requirements:
    - Create a Java program for a Reversi/Othello game.
    - Use seperate classes for the controller and GUI to separate their functionality.
    - Use standard swing/awt GUI classes, provided classes, and custom classes.

2. Program Requirements:
    - Implement GUI and controller for playing Reversi/Othello.
    - Use `GUIView` class to initialize and refresh the GUI based on the model data.
    - Use `ReversiController` class to initialize, start/restart the game, update status, handle square selection, and implement a greedy AI move.

3. GUI Requirements:
    - Display two frames representing the board from the perspective of each player.
    - Each frame should have a title specifying the player.
    - Include buttons for running a "Greedy AI" and restarting the game.
    - Ensure the boards are rotated 180 degrees with respect to each other.

4. Square Representation in GUI:
    - Create a class to draw and handle a square of the board.
    - Use buttons to represent the squares.
    - Draw a green square with a black border and a white/black circle with a border in the opposite color.
    - Override functions for drawing and implement appropriate drawing techniques.
    - Handle square selection and notify the controller.

5. Model and Data Handling:
    - Use `SimpleModel` to store the board state, current player, and game status.
    - Avoid storing this information in the controller or GUI.
    - Ensure both GUI and controller use the same model.
    - Place initial pieces in the center of the board.

6. Handling Square Selection:
    - When a square is clicked, notify the controller.
    - Let the controller decide whether to place a piece in the selected square.
    - GUI should not make decisions about move validity.

7. Controller Logic:
    - Valid moves follow the standard Reversi rules, which can be found [here](https://en.wikipedia.org/wiki/Reversi#Rules)
    - Implement logic for different scenarios:
        - Switching players when no valid move is available.
        - Ending the game if neither player can move.
        - Handling incorrect player's turn.
        - Ignoring clicks on invalid locations.
        - Validating and capturing opponent's pieces in a straight line.

8. Turn Management:
    - White player plays first.
    - Players take turns to play.
    - Active player should be indicated in the GUI.