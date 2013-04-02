import java.util.Random;
import java.util.Scanner;

public class NoughtsAndCrosses
{
    char playerSymbol;
    char computerSymbol;
    char[] board = new char[9];     // array to hold what is on each square of the board
    boolean over = false;
    Random randomGenerator = new Random();

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
            board[i] = ' ';
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

    public void play(boolean playerGoesFirst)
    {
        if (playerGoesFirst)
        {

        }
        else
        {

        }



        while (!over)
        {
            drawGrid(board);


            over = true;
        }
    }

    public boolean whoFirst()
    {
        boolean playerFirst = randomGenerator.nextBoolean();
        if (playerFirst)
        {
            System.out.println("The computer will go first... ");
        }
        else
        {
            System.out.println("You will go first... ");
        }
        return playerFirst;
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
