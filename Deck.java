import java.util.Random;
public class Deck
{
  public static void main(String [] args)
  {
    Random rand = new Random();
    String[] suits = {"Spades", "Clubs", "Diamonds", "Hearts"};
    String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
    int n = suits.length*ranks.length;
    String[] deck = new String[n];

    for (int i = 0; i < suits.length; i++)
    {
      for (int j = 0; j < ranks.length; j++)
      {
        deck[ranks.length*i+j] = ranks[j] + " of " + suits[i];
      }
    }
    for (int i = 0; i < n; i++)
    {
      System.out.println(deck[i]);
    }
    for (int i = 0; i < n; i++)
    {
      int x = i + (int)(rand.nextInt(n-i));
      String temp = deck[x];
      deck[x] = deck[i];
      deck[i] = temp;
    }
    //for (int i = 0; i < n; i++)
    //{
      //System.out.println(deck[i]);
    //}
  }
}
