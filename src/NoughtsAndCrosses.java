import java.util.*;

public class NoughtsAndCrosses
{
    private char playerSymbol, computerSymbol, difficulty;
    private char[] board = new char[9];     // array to hold what is on each square of the board
    private boolean[] spaceTaken = new boolean[9];      // array to hold whether a square of the board is free or not
    private boolean over = false;
    private final Random randomGenerator = new Random();
    private int playerMoves, computerMoves;

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
            draw(board);
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
        draw(board);
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
        String input = in.next().toLowerCase();
        if (input.charAt(0) == 'y')
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

    public void move(char x_or_o, int where)
    {
        board[where] = x_or_o;
        spaceTaken[where] = true;
    }

    public void getCompMove(char difficulty)
    {
        System.out.println("Computer now moving...");
        char[] hypotheticalBoard = new char[9];
        ArrayList<Integer> moveOptions = new ArrayList<Integer>();    // list of possible squares computer can move

        for (int i = 0; i < board.length; i++)
        {
            hypotheticalBoard[i] = board[i];    // copy board into hypoBoard
            if (hypotheticalBoard[i] != playerSymbol && hypotheticalBoard[i] != computerSymbol) // then space is free
            {
                moveOptions.add(i);   // populate moveOptions
            }
        }

        switch (difficulty)
        {
            case 'h':
                System.out.println("IN UR HARD");
                for (Integer emptySquare : moveOptions)
                {
                    hypotheticalBoard[emptySquare] = playerSymbol;
                    if (isWinningMove(hypotheticalBoard, playerSymbol))
                    {
                        move(computerSymbol, emptySquare);
                        return;
                    }
                    hypotheticalBoard[emptySquare] = (char) ('0' + emptySquare);
                }
                getCompMove('i');
                break;
            case 'i':
                System.out.println("IN UR INTERMEDIATE");
                for (Integer emptySquare : moveOptions)
                {
                    hypotheticalBoard[emptySquare] = computerSymbol;
                    if (isWinningMove(hypotheticalBoard, computerSymbol))
                    {
                        System.out.println("IN UR INT WINNING MV");
                        draw(hypotheticalBoard);
                        System.out.println("END DRAW SEQUENCE");
                        move(computerSymbol, emptySquare);
                        return;
                    }
                    hypotheticalBoard[emptySquare] = (char) ('0' + emptySquare);
                }
                if (moveOptions.contains(0) || moveOptions.contains(2) || moveOptions.contains(6) || moveOptions.contains(8)) // if a corner is free
                {
                    System.out.println("IN UR CORNERZ");
                    int[] corners = new int[] {0, 2, 6, 8};
                    List<Integer> freeCorners = getLegitMoves(corners);
                    int mv = randomElement(freeCorners);
                    move(computerSymbol, mv); return;
                }
                else if (moveOptions.contains(4)) // if middle space is free
                {
                    System.out.println("IN UR MIDDLE");
                    move(computerSymbol, 4);     // no need to check if it's free, the else if does it for us
                    return;
                }
                else     // if a side space is free
                {
                    System.out.println("IN UR SIDEZ");
                    int[] sides = new int[] {1, 3, 5, 7};
                    List<Integer> freeSides = getLegitMoves(sides);
                    int mv = randomElement(freeSides);
                    move(computerSymbol, mv); return;
                }
            case 'e':
                System.out.println("IN UR EASY");
                int[] allPossMoves = new int[] {0, 1, 2, 3, 4, 5, 6, 7, 8};
                List<Integer> freeRandomSpaces = getLegitMoves(allPossMoves);
                int mv = randomElement(freeRandomSpaces);
                move(computerSymbol, mv);
                break;
        }
    }

    public <anyType> anyType randomElement(List<anyType> list)
    {
        int randomIndex = randomGenerator.nextInt(list.size());
        return list.get(randomIndex);
    }

    public List<Integer> getLegitMoves(int[] array)
    {
        List<Integer> freeSpaces = new ArrayList<Integer>();
        for (int i : array)
        {
            if (!spaceTaken[i])
                freeSpaces.add(i);
        }
        return freeSpaces;
    }

    public void getPlayerMove()
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Please enter a number 1-9, where you wish to place your counter");
        int move = (in.nextInt() - 1);

        if (!spaceTaken[move])
        {
            move(playerSymbol, move);
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

    public void draw(char[] board)
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
