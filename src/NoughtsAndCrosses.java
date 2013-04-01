import java.util.Scanner;

public class NoughtsAndCrosses
{
    char playerSymbol;
    char computerSymbol;

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
    }

    public char getSymbols()
    {
        while (true)
        {
            Scanner in = new Scanner(System.in);
            String input = in.next();
            if (input.length() > 1)
            {
                System.out.println("Please choose one symbol, either 'x' or 'o'!");
            }
            else if (input.charAt(0) != 'o' || input.charAt(0) != 'x' ||
                    input.charAt(0) != 'O' || input.charAt(0) != 'X')
            {
                System.out.println("Selection must be either an 'x' or an 'o'!");
            }
            else
            {
                if (input.charAt(0) == 'o' || input.charAt(0) == 'O')
                {
                    playerSymbol = 'O';
                    computerSymbol = 'X';
                }
                else
                {
                    playerSymbol = 'X';
                    computerSymbol = 'O';
                }
                return playerSymbol;
            }
        }
    }
}
