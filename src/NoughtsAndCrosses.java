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
        System.out.println("You will be " + playerSymbol + ", the computer will be " + computerSymbol + ". Let's Play!");
        boolean turn = whoFirst();
        play(turn);
    }

    public void play(boolean playerTurn)
    {
        while (!over)
        {
            drawGrid(board);
            if (playerTurn)
            {
                board = getPlayerMove(board);
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
                board = getComputerMove(board);
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

    public char[] getComputerMove(char[] boardState)
    {
        int move = randomGenerator.nextInt(9);
        if (!spaceTaken[move])
        {
            move(board, computerSymbol, move);
            System.out.println("Computer now moving...");
        }
        else
            getComputerMove(board);

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
            }
            hypotheticalBoard[i] = (char) i;    // resets square
            hypotheticalBoard[i] = playerSymbol;        // sets square to player symbol, as if move had been played
            if (isWinningMove(hypotheticalBoard, playerSymbol))
            {
                move(board, computerSymbol, i);     // if computer can block the player's winning move, it does
            }
            hypotheticalBoard[i] = (char) i;    // resets square
            if
        }

        return boardState;
    }

    public char[] getPlayerMove(char[] boardState)
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a number 0-9, where you wish to place your counter");
        int move = (in.nextInt() - 1);

        if (!spaceTaken[move])
        {
            move(board, playerSymbol, move);
        }
        else
        {
            System.out.println("That space is taken! Please try again");
            getPlayerMove(board);
        }
        return boardState;
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
        while (true)
        {
            Scanner in = new Scanner(System.in);
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
                    computerSymbol = '\u2718';return;
                }
                else if (input.charAt(0) == 'x' || input.charAt(0) == 'X')
                {
                    playerSymbol = '\u2718';
                    computerSymbol = '\u25CF';return;
                }
                else
                {
                    System.out.println("Selection must be either an 'x' or an 'o'!");
                }
            }
        }
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
