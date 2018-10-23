# 2018 Atomic Games

This repository contains the winning solution to the 2018 Atomic Games, hosted by [Atomic Object](https://atomicobject.com/).  The 2018 Atomic Games centered around devleoping an AI to play the game Othello.  This solution uses the Minimax algorithm to determine optimal moves.  The strength of this AI stems from the use of a weighted table to determine the best positions on the board to make a move.

## Running The Game 

Note: The [Instructions.md](Instructions.md) file contains more information about how the game works and how to run it.

### MacOS

* Start the server: 
  * `$ cd ~/AtomicGames2018/`
  * `$ java -jar othello.jar --p1-type remote --p2-type random --wait-for-ui`
* Compile the client code:
  * `$ cd AtomicGames2018/sdks/java/AI/src/com/atomicobject/othello/`
  * `$ javac -cp "../../../../lib/*" *.java`
* Run the client:
  * `$ cd AtomicGames2018/sdks/java/AI/src/`
  * `$ java -cp ".:../lib/*" com.atomicobject.othello.Main`
* Open a browser and enter the URL: http://localhost:8080/

## Authors

* [Atomic Object](https://atomicobject.com/)
* [Tanush Samson](https://github.com/Tanflare)
* [Alex Cadigan](https://github.com/AlexCadigan)