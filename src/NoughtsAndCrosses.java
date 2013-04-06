import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class NoughtsAndCrosses
{
    char playerSymbol;
    char computerSymbol;
    char[] board = new char[9];     // array to hold what is on each square of the board
    boolean[] spaceTaken = new boolean[9];      // array to hold whether a square of the board is free or not
    boolean over = false, winningMove = false, boardFull = false;
    Random randomGenerator = new Random();
    int playerMoves, computerMoves;
    private char difficulty;

    public static void main(String[] args)
    {
        NoughtsAndCrosses game = new NoughtsAndCrosses();
        game.start();
    }

    public NoughtsAndCrosses()
    {
        this.setUp();
    }

    public void setUp()
    {
        for (int i = 0; i < board.length; i++)
        {
            char ch = (char) ('1' + i);        // casts int to a char for our char array, and makes it 1-9 not 0-8
            board[i] = ch;
        }
    }

    public void start()
    {
        System.out.println("Welcome to Noughts and Crosses! Will you be X or O? ");
        getSymbols();
        System.out.println("Now please select a difficulty - (e)asy, (i)ntermediate or (h)ard?" );
        setDifficulty();
        System.out.println("You will be " + playerSymbol + ", the computer will be " + computerSymbol + ". Let's Play!");
        boolean turn = whoFirst();
        play(turn);
    }

    public void setDifficulty()
    {
        Scanner in = new Scanner(System.in);
        String input = in.next().toLowerCase();
        while (true)
        {
            if (input.charAt(0) == 'h' || input.charAt(0) == 'i' || input.charAt(0) == 'e')
            {
                difficulty = input.charAt(0);
                break;
            }
            else        // invalid input
            {
                System.out.println("Please choose hard, intermediate or easy!");
            }
        }
    }

    public void play(boolean playerTurn)
    {
        while (!over)
        {
            drawGrid(board);
            if (playerTurn)
            {
                getPlayerMove();
                playerMoves++;
                if (isWinningMove(board, playerSymbol) || isBoardFull(board))
                {
                    over = true;
                }
                else
                {
                    playerTurn = false;         // sets it to computer's turn
                }
            }
            else    // i.e. the computer's turn
            {
                getCompMove(difficulty);
                computerMoves++;
                if (isWinningMove(board, computerSymbol) || isBoardFull(board))
                {
                    over = true;
                }
                else
                {
                    playerTurn = true;      // sets it back to player's turn
                }
            }
        }
        endgame(playerTurn);
    }

    public void endgame(boolean isPlayerTurn)
    {
        if (isBoardFull(board) && !isWinningMove(board, playerSymbol) && !isWinningMove(board, computerSymbol))     // must be a tie
        {
            System.out.println("A tie after 9 moves total!");
            playAgain();
        }

        if (isPlayerTurn)   // player must have won
        {
            System.out.println("Congratulations! You beat the computer in " + playerMoves + " moves!");
        }
        else        // comp has won
        {
            System.out.println("Unlucky! The computer beat you in " + computerMoves + " moves!");
        }
        playAgain();

    }

    public void playAgain()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Would you like to play again? [y/n]");
        if (in.next().charAt(0) == 'y' || in.next().charAt(0) == 'Y')
        {
            NoughtsAndCrosses game = new NoughtsAndCrosses();
            game.start();
        }
        else
            System.exit(0);
    }

    public static boolean isBoardFull(char[] board)
    {
        boolean full = true;
        for (char ch : board)
        {
            if (ch != '✘' && ch != '●')
            {
                full = false;
            }
        }
        return full;
    }

    public static boolean isWinningMove(char[] board, char symbol)
    {
        boolean won = false;

        /*      6 7 8
                3 4 5                  // view of board
                0 1 2       */

        if (board[6] == symbol && board[7] == symbol && board[8] == symbol)         // line across top row
            won = true;
        else if (board[3] == symbol && board[4] == symbol && board[5] == symbol)    // line across middle
            won = true;
        else if (board[0] == symbol && board[1] == symbol && board[2] == symbol)    // line across bottom
            won = true;
        else if (board[8] == symbol && board[4] == symbol && board[0] == symbol)    // diag top right to bottom left
            won = true;
        else if (board[6] == symbol && board[4] == symbol && board[2] == symbol)    // diag top left to bottom right
            won = true;
        else if (board[6] == symbol && board[3] == symbol && board[0] == symbol)    // left column
            won = true;
        else if (board[7] == symbol && board[4] == symbol && board[1] == symbol)    // middle column
            won = true;
        else if (board[8] == symbol && board[5] == symbol && board[2] == symbol)    // right column
            won = true;

        return won;
    }

    public char[] move(char[] boardState, char x_or_o, int where)
    {
        board[where] = x_or_o;
        spaceTaken[where] = true;
        return board;
    }

    public void getCompMove(char difficulty)
    {
        System.out.println("Computer now moving...");
        char[] hypotheticalBoard = new char[9];
        ArrayList<Integer> moveOptions = new ArrayList<Integer>();    // list of possible squares computer can move

        for (int i = 0; i < board.length; i++)
        {
            hypotheticalBoard[i] = board[i];    // copy board into hypoBoard
            if (hypotheticalBoard[i] != playerSymbol || hypotheticalBoard[i] != computerSymbol) // then space is free
            {
                moveOptions.add(i);   // populate moveOptions
            }
        }

        switch (difficulty)
        {
            case 'h':
                for (int emptySquare : moveOptions)
                {
                    hypotheticalBoard[emptySquare] = computerSymbol;
                    if (isWinningMove(hypotheticalBoard, computerSymbol))
                    {
                        move(board, computerSymbol, emptySquare);
                        hypotheticalBoard[emptySquare] = (char) ('0' + emptySquare);
                        return;
                    }
                    hypotheticalBoard[emptySquare] = playerSymbol;
                    if (isWinningMove(hypotheticalBoard, playerSymbol))
                    {
                        move(board, computerSymbol, emptySquare);
                        hypotheticalBoard[emptySquare] = (char) ('0' + emptySquare);
                        return;
                    }
                }
                getCompMove('i');
            case 'i':
                if (board[6] == '7' || board[8] == '9' || board[0] == '1' || board[2] == '3') // if a corner is free
                {
                    Integer[] corners = new Integer[] {0, 2, 6, 8};
                    int mv = randomArrayElem(corners);
                    if (!spaceTaken[mv])
                        move(board, computerSymbol, mv);
                    else
                        getComputerMove('i');
                }
                else if (board[4] == '5') // if middle space is free
                {
                    move(board, computerSymbol, 4);
                }
                else if (board[3] == '4' || board[7] == '8' || board[5] == '6' || board[1] == '2') // if a side space is free
                {
                    Integer[] sides = new Integer[] {1, 3, 5, 7};
                    int mv = randomArrayElem(sides);
                    if (!spaceTaken[mv])
                        move(board, computerSymbol, mv);
                    else
                        getComputerMove('i');
                }
            case 'e':
                int randomMove = randomGenerator.nextInt(9);
                if (!spaceTaken[randomMove])
                    move(board, computerSymbol, randomMove);
                else
                    getComputerMove('e');
        }
    }

    public void getComputerMove(char difficulty)
    {
        System.out.println("Computer now moving...");
        char[] hypotheticalBoard = new char[9];
        ArrayList<Integer> moveOptions = new ArrayList<Integer>();
        for (int i = 0; i < board.length; i++)
        {
            hypotheticalBoard[i] = board[i];    // copy board into hypoBoard
            if (hypotheticalBoard[i] != playerSymbol || hypotheticalBoard[i] != computerSymbol) // then space is free
            {
                moveOptions.add(i);
            }
        }

        for (int i : moveOptions)
        {
            hypotheticalBoard[i] = computerSymbol;      // sets square to computer symbol, as if move had been played
            if (isWinningMove(hypotheticalBoard, computerSymbol))
            {
                move(board, computerSymbol, i);        // if computer can make winning move, make it
                hypotheticalBoard[i] = (char) i;    // resets square
                //return board;
            }
            hypotheticalBoard[i] = (char) i;    // resets square
        }
        for (int i : moveOptions)
        {
            hypotheticalBoard[i] = playerSymbol;        // sets square to player symbol, as if move had been played
            if (isWinningMove(hypotheticalBoard, playerSymbol))
            {
                move(board, computerSymbol, i);     // if computer can block the player's winning move, it does
                hypotheticalBoard[i] = (char) i;    // resets square
                //return board;
            }
            hypotheticalBoard[i] = (char) i;    // resets square
        }

        if (board[6] == '7' || board[8] == '9' || board[0] == '1' || board[2] == '3') // if a corner is free
        {
            Integer[] corners = new Integer[] {0, 2, 6, 8};
            int mv = randomArrayElem(corners);
            if (!spaceTaken[mv])
                move(board, computerSymbol, mv);
            else
                getComputerMove(difficulty);
        }
        else if (board[4] == '5') // if middle space is free
        {
            move(board, computerSymbol, 4);
        }
        else if (board[3] == '4' || board[7] == '8' || board[5] == '6' || board[1] == '2') // if a side space is free
        {
            Integer[] sides = new Integer[] {1, 3, 5, 7};
            int mv = randomArrayElem(sides);
            if (!spaceTaken[mv])
                move(board, computerSymbol, mv);
            else
                getComputerMove(difficulty);
        }

        //return boardState;
    }

    public <anyType> anyType randomArrayElem(anyType[] array)
    {
        int randomIndex = randomGenerator.nextInt(array.length);
        return array[randomIndex];
    }

    public void getPlayerMove()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a number 1-9, where you wish to place your counter");
        int move = (in.nextInt() - 1);

        if (!spaceTaken[move])
        {
            move(board, playerSymbol, move);
        }
        else
        {
            System.out.println("That space is taken! Please try again");
            getPlayerMove();
        }
    }

    public boolean whoFirst()
    {
        boolean playerTurn = randomGenerator.nextBoolean();
        if (!playerTurn)
        {
            System.out.println("The computer will go first... ");
        }
        else
        {
            System.out.println("You will go first... ");
        }
        return playerTurn;
    }

    public void getSymbols()
    {
        Scanner in = new Scanner(System.in);
        while (playerSymbol == '\u0000' && computerSymbol == '\u0000')
        {
            String input = in.next();
            if (input.length() > 1)
            {
                System.out.println("Please choose one symbol, either 'x' or 'o'!");
            }
            else
            {
                if (input.charAt(0) == 'o' || input.charAt(0) == 'O')
                {
                    playerSymbol = '\u25CF';
                    computerSymbol = '\u2718';
                }
                else if (input.charAt(0) == 'x' || input.charAt(0) == 'X')
                {
                    playerSymbol = '\u2718';
                    computerSymbol = '\u25CF';
                }
                else
                {
                    System.out.println("Selection must be either an 'x' or an 'o'!");
                }
            }
        }
        in.reset();
    }

    public void drawGrid(char[] board)
    {
        System.out.println("    |   |");
        System.out.println("  " + board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println("-------------");
        System.out.println("  " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("    |   |");
        System.out.println("-------------");
        System.out.println("  " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("    |   |");
    }
}
