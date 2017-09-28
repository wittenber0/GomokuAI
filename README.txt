CS 4341 Project 2

Team RIPHughHefner
Chris Bianco, Luke Buquicchio, Ryan Wittenberg

We have provided a compiled JAR that can be run from the command line with the command “java -jar RIPHughHefner.jar” We
have also included the uncompiled source code for you to examine. When running the JAR, it should be in the same
directory as the referee and the other team’s executable, as well as the game files. It does not need to be kept with
the source code.

The utility function that we used was to assing a winning board a value of 10000, a losing board a value of -10000, and
an otherwise full board a value of 0. Any nonterminal board could be run through our evaluation function.

After playing the game, we discovered that the best indicator of success was the number and length of chains. However,
the only chains that were useful were those which had at least one terminal end not blocked by the other player. Chains
with both ends unbolcked were especially useful. We created a system in our board class to store all of the chains for
both players, including their length and terminal ends. Every turn, the new move is used to update all of the existing
chains. To calculate the heuristic value of the board, we created a heuristic value of each tile based on how many
active chains it could create/extend for us as well as how many active chains we could block of the other player. In
the event of a tie, the Manhattan distance of the tile from the center of the board was used, since we generally felt
that tiles closest to the center gave the player the most options. In the event of a tie in that as well, the move
evaluated first would be chosen We found that moves that were not adjacent to previous moves were not advantageous, and
as such, we discarded all moves that had only empty tiles adjacent to it. This allowed us to save time in calculating
the board heuristic.

To save time in expanding the tree, we decided to use a depth-limited algorithm with depth 2, at which point we
calculate the heuristic of each leaf board rather than continue to expand the tree. This strategy, coupled with
alpha-beta pruning, we found to be the best combination of thinking more than a move ahead while still seing the time
gains offered by how heuristic evaluation function. IF we expanded further, we found we could not meet the 10 second
time limit.