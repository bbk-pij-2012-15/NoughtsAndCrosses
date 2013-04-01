import java.util.Scanner;

public class NoughtsAndCrosses
{
    char playerSymbol;
    char computerSymbol;
    char[] board = new char[9];     // array to hold what is on each square of the board
    boolean over = false;

    public static void main(String[] args)
    {
        NoughtsAndCrosses game = new NoughtsAndCrosses();
        game.play();
    }

    public NoughtsAndCrosses()
    {
        this.setUp();
    }

    public void setUp()
    {

    }

    public void play()
    {
        System.out.println("Welcome to Noughts and Crosses! Will you be X or O? ");
        getSymbols();
        System.out.println("You will be " + playerSymbol + ", the computer will be " + computerSymbol + ". Let's Play!");

        while (!over)
        {
            drawGrid(board);
            over = true;
        }
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
                    playerSymbol = 'O';
                    computerSymbol = 'X';return;
                }
                else if (input.charAt(0) == 'x' || input.charAt(0) == 'X')
                {
                    playerSymbol = 'X';
                    computerSymbol = 'O';return;
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
        System.out.println("   |   |");
        System.out.println(" " + board[6] + " | " + board[7] + " | " + board[8]);
        System.out.println("   |   |");
        System.out.println("-----------");
        System.out.println("   |   |");
        System.out.println(" " + board[3] + " | " + board[4] + " | " + board[5]);
        System.out.println("   |   |");
        System.out.println("-----------");
        System.out.println("   |   |");
        System.out.println(" " + board[0] + " | " + board[1] + " | " + board[2]);
        System.out.println("   |   |");
    }
}
