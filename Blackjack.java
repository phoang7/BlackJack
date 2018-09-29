import java.util.*; //comments are referring to the rules. type 'r' to get the rules to really understand the comments.
public class Blackjack
{
    public static void main(String [] args)
    {
        System.out.println("Blackjack (Also known as twenty-one)");
        System.out.println();
        Scanner scan = new Scanner(System.in);
        String choice;
        do
        {
            System.out.println("Select a command to continue.");
            System.out.println("Command options: ");
            System.out.println("p: Play a game of Blackjack against the dealer");
            System.out.println("q: quit the program");
            System.out.println("r: See the rules of the Blackjack");
            System.out.println("?: Help");
            System.out.println();
            choice = scan.nextLine();
            switch(choice)
            {
                case "p": //input causes the game to start
                System.out.println();
                Random rand = new Random();
                String[] suits = {"Spades", "Clubs", "Diamonds", "Hearts"};
                String[] ranks = {"2", "3", "4", "5", "6", "7", "8", "9", "10", "Jack", "Queen", "King", "Ace"};
                int n = suits.length*ranks.length;
                String[] deck = new String[n]; //deck used to play the game
                String[] deckCheck = new String[n]; //deck used to check the other deck to give integer values to scores
                for (int i = 0; i < suits.length; i++)
                {
                    for (int j = 0; j < ranks.length; j++)
                    {
                        deck[ranks.length*i+j] = ranks[j] + " of " + suits[i];
                    }
                }
                for (int i = 0; i < suits.length; i++)
                {
                    for (int j = 0; j < ranks.length; j++)
                    {
                        deckCheck[ranks.length*i+j] = ranks[j] + " of " + suits[i];
                    }
                }
                for (int i = 0; i < n; i++) //shuffles the deck randomly
                {
                    int x = i + (int)(rand.nextInt(n-i));
                    String temp = deck[x];
                    deck[x] = deck[i];
                    deck[i] = temp;
                }
                ArrayList<String> playerHand = new ArrayList<String>(52); //creates players hand
                ArrayList<String> dealerHand = new ArrayList<String>(52); //creates dealers hand
                playerHand.add(deck[0]); //adds first card to player's hand
                dealerHand.add(deck[1]); //adds second card to dealer's hand
                playerHand.add(deck[2]); //adds third card to player's hand
                dealerHand.add(deck[3]); //adds fourth card to dealer's hand
                String viewHand = playerHand.get(0) + " and " + playerHand.get(1); //displays the users hand
                int cardsDealt = 4; //the amount of cards that have been dealed. It starts out as four because 4 cards are initially dealed out
                int dealerScore = 0;
                boolean dealerBust = false;
                int dealerAceQuantity = 0; //number of aces the dealer has
                boolean gameOver = false; //used to determine if the game is over or not
                boolean playerTurn = true; //player goes first
                boolean dealerTurn = false; //dealer goes second
                System.out.println("The dealer is dealing out cards.");
                System.out.println("The dealers deals you the first card, then deals himself the second card.");
                System.out.println("The dealer deals you the third card, then deals himself the fourth card.");
                System.out.println();
                do
                {
                    while (playerTurn == true) //player goes first
                    {
                        System.out.println("Your hand is the " + viewHand);
                        System.out.println("Type h to hit or s to stand.");
                        System.out.println("Would you like to hit or stand?");
                        choice = scan.nextLine();
                        System.out.println();
                        if (cardsDealt == deck.length) //if all cards in deck are drawn, player's turn is ended because no more cards can be drawn
                        {
                            System.out.println("You cannot draw anymore cards. You have drew the whole entire deck.");
                            playerTurn = false; //command make player end turn
                            dealerTurn = true;
                        }
                        else if (choice.equals("h")) //input causes player to pick up another card
                        {
                            playerHand.add(deck[cardsDealt]);
                            viewHand += " and " + playerHand.get(playerHand.size()-1);
                            cardsDealt = addCard(cardsDealt);
                            System.out.println("The dealer gives you the next card from the deck.");
                            System.out.println();
                            System.out.println(playerHand);
                            System.out.println();
                        }
                        else if (choice.equals("s")) //input causes player to end turn
                        {
                            System.out.println("You have ended your turn.");
                            System.out.println();
                            playerTurn = false;
                            dealerTurn = true;
                        }
                        else
                        {
                            System.out.println("Incorrect input. Please try again. Please type only \'h\' or \'s\' only.");
                        }
                    }
                    System.out.println("It is now the dealer's turn.");
                    System.out.println("The dealer is looking at his cards.");
                    while (dealerTurn == true) //dealer goes after the player
                    {
                        dealerAceQuantity = dealerHasAce(dealerHand, deckCheck);
                        dealerScore = getDealerScore(dealerHand, deckCheck);
                        if (dealerAceQuantity == 4) //if dealer has four ace cards
                        {
                            if (dealerScore == 17 || dealerScore == 7) // if the dealerScore is 17, 4 ace values of one is blackjack. if the dealerScore is 7, 3 ace values of one and 1 ace value of 11 is blackjack
                            {
                                dealerScore = 21; //dealer score is set to 21
                                dealerTurn = false; //dealer ends his turn
                            }
                            if (dealerScore < 17 && dealerScore > 7) //for dealerScore values from 8 to 16
                            {
                                if ((dealerScore + 4) == 20) //dealerScore of 16 with 4 ace values of 1 is 20
                                {
                                    if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand, deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 4; //because the dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 4) == 19) //if dealerScore is 15 with four ace values of 19
                                {
                                    if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 4; //because the dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 4) == 18) //dealerScore of 14 with four ace values of 18
                                {
                                    if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 4; //because the dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 4) == 17) //dealerScore of 13 with four ace values of 17
                                {
                                    if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 4; //because the dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 4) < 17) //if dealerScore is less than 13 because with four ace values of 1 is less than the required dealer value
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore < 7 && dealerScore >= 0) //for dealerScore values 0,2,3,4,5,6
                            {
                                if ((dealerScore + 14) == 20) //dealerScore is 6 with three aces values of 1 and one value of 11 is 20
                                {
                                    if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 14; //because dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 14) == 19) //if dealerScore is 5 with three ace values of 1 and one value of 11 is 19
                                {
                                    if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 14; //because dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 14) == 18) //if dealerScore is 4 with three aces values of 1 and one value of 11 is 18
                                {
                                    if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 14; //because dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 14) == 17) //if dealerScore is 3 with three aces values of 1 and one value of 11 is 17
                                {
                                    if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 14; //because dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 14) < 17) //for values 0,2 because with three ace values of 1 and one ace value of 11 is less than the required dealer value
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore > 17) //if score is greater than 17 with four ace values of 1, then the dealer has busted
                            {
                                dealerScore += 4; //adds four ace values of one to dealer score
                                dealerBust = true; //dealer busted
                                dealerTurn = false; //dealer ends turn
                            }
                        }
                        if (dealerAceQuantity == 3) //if the dealer has three aces
                        {
                            if (dealerScore == 18 || dealerScore == 8) //if dealerScore is 18, 3 ace values of one is a total of blackjack. if dealerScore is 8, 2 ace values of one and 1 ace value of 11 is a total of blackjack
                            {
                                dealerScore = 21; //dealer score is set to 21
                                dealerTurn = false; //dealer ends turn
                            }
                            if (dealerScore < 18 && dealerScore > 8) //for values 9 to 17, added with 3 ace values of one
                            {
                                if ((dealerScore + 3) == 20) //dealerScore of 17 with three ace values of 1 is 20
                                {
                                    if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 3; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 3) == 19) //dealerScore of 17 with three ace values of 1 is 19
                                {
                                    if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 3; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 3) == 18) //dealerScore of 15 with three ace values of 1 is 18
                                {
                                    if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 3; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 3) == 17) //dealerScore of 14 with three ace values of 1 is 17
                                {
                                    if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 3; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 3) < 17) //dealer score less than 14: with three ace values of 1 is less than 17 which is less than the required dealer value
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore < 8 && dealerScore >= 0) //dealerScore values: 0,2,3,4,5,6,7,
                            {
                                dealerScore += 13;
                                if ((dealerScore + 13) == 20) //dealer score of 7 with two ace values of 1 and one ace value of 11 is 20
                                {
                                    if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 13; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 13) == 19) //dealer score of 6 with two ace values of 1 and one ace value of 11 is 19
                                {
                                    if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 13; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 13) == 18) //dealer score of 5 with two ace values of 1 and one ace value of 11 is 18
                                {
                                    if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 13; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 13) == 17) //dealer score of 4 with two ace values of 1 and one ace value of 11 is 17
                                {
                                    if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 13; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 13) < 17) //for values 0,2,3 because two ace values of 1 and one value of 11 is less than the required dealer value
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore > 18) //if dealerScore is greater than 18 with three ace values of 1 then the dealer has busted
                            {
                                dealerScore += 3; //three ace values of one is added to the score
                                dealerBust = true; //dealer busted
                                dealerTurn = false; //dealer ends turn
                            }
                        }
                        if (dealerAceQuantity == 2) //dealer has two aces in his hand
                        {
                            if (dealerScore == 19 || dealerScore == 9) //19 + 1 + 1 = 21 if the two ace values are both one. 9 + 1 + 11 = 21 if the one ace value is one and the other is eleven
                            {
                                dealerScore = 21; //dealer score is set to 21
                                dealerTurn = false; //dealer ends turn
                            }
                            if (dealerScore < 19 && dealerScore > 14) //for values 15, 16, 17, 18
                            {
                                if ((dealerScore + 2) == 20) //if dealer score is 18 with two ace values of 1
                                {
                                    if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore+=2; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 2) == 19) //if dealer score is 17 with two ace values of 1
                                {
                                    if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore+=2; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 2) == 18) //if the dealer score is 16 with two ace values of 1
                                {
                                    if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore+=2; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 2) == 17) //if the dealer score is 15 with two ace values of 1
                                {
                                    if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore+=2; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                            }
                            if (dealerScore < 15 && dealerScore > 9) //for values 10,11,12,13,14 if both ace values are one, then dealerScore would be less than seventeen
                            {
                                if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                {
                                    addToDealerHand(dealerHand,deck, cardsDealt);
                                    cardsDealt = addCard(cardsDealt);
                                }
                            }
                            if (dealerScore < 9 && dealerScore > 4) //for values 5,6,7,8 if one ace value is eleven and the other is one, then dealerScore would be equal to seventeen or greater
                            {
                                if ((dealerScore + 12) == 20) //if dealer score is 8 with one ace value of 11 and one ace value of one
                                {
                                    if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 12; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 12) == 19) //if dealer score is 7 with one ace value of 11 and one ace value of one
                                {
                                    if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 12; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 12) == 18) //if dealer score is 6 with one ace value of 11 and one ace value of one
                                {
                                    if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 12; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                if ((dealerScore + 12) == 17) //if dealer score is 5 with one ace value of 11 and one ace value of one
                                {
                                    if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 12; //since no card is drawn, dealerScore can be added with the two aces value of 1
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                            }
                            if (dealerScore < 5) //dealerScore is 0,2,3,4. If you have one ace have a value of 11 and the other be one, the dealerScore is less than seventeen.
                            {
                                if (cardsDealt == 52)
                                {
                                    dealerScore += 12;
                                    dealerTurn = false;
                                }
                                if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                {
                                    addToDealerHand(dealerHand,deck, cardsDealt);
                                    cardsDealt = addCard(cardsDealt);
                                }
                            }
                            if (dealerScore > 19) //had a score of over 19 with two aces of 1, then the dealer has busted
                            {
                                dealerScore += 2; //two ace values of one is added to dealerScore
                                dealerBust = true; //dealer busted
                                dealerTurn = false; //dealer ends turn
                            }
                        }
                        if (dealerAceQuantity == 1) //if dealer has one ace in his hand
                        {
                            if ((dealerScore + 11) == 21) //dealerScore would be ten
                            {
                                dealerScore = 21; //dealer score is set to 21
                                dealerTurn = false; //dealer ends turn
                            }
                            if (dealerScore == 20) //dealerScore is twenty, plus an ace is a blackjack
                            {
                                dealerScore = 21; //dealer score is set to 21
                                dealerTurn = false; //dealer ends turn
                            }
                            if (dealerScore == 19) //dealerScore is 19, plus an ace would be 20
                            {
                                if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                                else
                                {
                                    dealerScore = 20;
                                    dealerTurn = false; //dealer ends turn
                                }
                            }
                            if (dealerScore == 18) //dealerScore is 18, plus an ace would be 19
                            {
                                if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                                else
                                {
                                    dealerScore = 19;
                                    dealerTurn = false; //dealer ends turn
                                }
                            }
                            if (dealerScore == 17) //dealerScore is 17, plus an ace would be 18
                            {
                                if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                                else
                                {
                                    dealerScore = 18;
                                    dealerTurn = false; //dealer ends turn
                                }
                            }
                            if ((dealerScore + 11) == 20) //dealerScore would be nine
                            {
                                if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                                else
                                {
                                    dealerScore += 11; //since dealer doesn't pick up another card
                                    dealerTurn = false; //dealer ends turn
                                }
                            }
                            if ((dealerScore + 11) == 19) //dealerScore would be eight
                            {
                                if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                                else
                                {
                                    dealerScore += 11; //since dealer doesn't pick up another card
                                    dealerTurn = false; //dealer ends turn
                                }
                            }
                            if ((dealerScore + 11) == 18) //dealerScore would be seven
                            {
                                if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                                else
                                {
                                    dealerScore += 11; //since dealer doesn't pick up another card
                                    dealerTurn = false; //dealer ends turn
                                }
                            }
                            if ((dealerScore + 11) == 17) //dealerScore would be six
                            {
                                if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                                else
                                {
                                    dealerScore += 11; //since dealer doesn't pick up another card
                                    dealerTurn = false; //dealer ends turn
                                }
                            }
                            if (dealerScore > 10 && dealerScore < 17) //other card values would be 11, 12, 13, 14, 15, 16
                            {
                                if (dealerScore == 16) //only because dealerScore is 16, plus an ace would be 17 since ace value has to be one
                                {
                                    if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                    {
                                        if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                        {
                                            addToDealerHand(dealerHand,deck, cardsDealt);
                                            cardsDealt = addCard(cardsDealt);
                                        }
                                    }
                                    else
                                    {
                                        dealerScore += 1; //since dealer doesn't pick up another card
                                        dealerTurn = false; //dealer ends turn
                                    }
                                }
                                else //for other dealerScores such as 11, 12, 13, 14, 15 because with an ace value has to be one which is less than seventeen
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore < 6) //for dealersScores 0,2,3,4,5 with an ace would make the dealerScore less than 17
                            {
                                if (cardsDealt == 52)
                                {
                                    dealerScore += 11;
                                    dealerTurn = false;
                                }
                                if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                {
                                    addToDealerHand(dealerHand,deck, cardsDealt);
                                    cardsDealt = addCard(cardsDealt);
                                }
                            }
                            if (dealerScore > 21) //if dealerScore is greater than 21
                            {
                                dealerScore++; //one ace value of 1 is added to dealerScore
                                dealerBust = true; //dealer busted
                                dealerTurn = false; //dealer ends turn
                            }
                        }
                        if (dealerAceQuantity == 0) //no aces, so no adding to dealerScore is needed
                        {
                            if (dealerScore > 21)
                            {
                                dealerBust = true; //dealer busted
                                dealerTurn = false; //dealer ends turn
                            }
                            if (dealerScore == 21)
                            {
                                dealerTurn = false; //dealer ends turn
                            }
                            if (dealerScore == 20)
                            {
                                if ((rand.nextInt(100) + 1) > 92) //92 is probability percentage of busting with a hand value of 20
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore == 19)
                            {
                                if ((rand.nextInt(100) + 1) > 85) //85 is probability percentage of busting with a hand value of 19
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore == 18)
                            {
                                if ((rand.nextInt(100) + 1) > 77) //77 is probability percentage of busting with a hand value of 18
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore == 17)
                            {
                                if ((rand.nextInt(100) + 1) > 69) //69 is probability percentage of busting with a hand value of 17
                                {
                                    if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                    {
                                        addToDealerHand(dealerHand,deck, cardsDealt);
                                        cardsDealt = addCard(cardsDealt);
                                    }
                                }
                            }
                            if (dealerScore < 17)
                            {
                                if (cardsDealt == 52)
                                {
                                    dealerTurn = false;
                                }
                                if (cardsDealt != 52) //if cards dealed from deck is 52, the dealer cannot draw another card
                                {
                                    addToDealerHand(dealerHand,deck, cardsDealt);
                                    cardsDealt = addCard(cardsDealt);
                                }
                            }
                        }
                    }
                    if (dealerTurn == false && playerTurn == false) //if both turns are done
                    {
                        System.out.println("The dealer has ended their turn.");
                        checkWinner(playerHand, dealerScore, dealerBust, deckCheck, dealerHand);
                        gameOver = true;
                    }
                } while (gameOver != true);
                break;
                case "q": //quits program
                System.out.println();
                System.out.println("Quitting Blackjack.");
                break;
                case "r": //gives rules of the game
                System.out.println();
                System.out.println("Blackjack is a comparing card game between players and a dealer. Though in this game you are the only player playing against the dealer. You are competing against the dealer. There is only one deck of 52 cards in this Blackjack game. The objective of the game is to beat the dealer in one of the following ways:");
                System.out.println("• Get 21 points on the player's first two cards (called a \"blackjack\" or \"natural\"), without a dealer blackjack");
                System.out.println("• Reach a final score higher than the dealer without exceeding 21");
                System.out.println("• Let the dealer draw additional cards until their hand exceeds 21");
                System.out.println("If the player has the same score as the dealer or if the player and dealer both bust, then it is a tie. No one wins or loses.");
                System.out.println();
                System.out.println("The value of cards two through ten is their pip value (2 through 10). Face cards (Jack, Queen, and King) are all worth ten. Aces can be worth one or eleven. A hand's value is the sum of the card values. Players are allowed to draw additional cards to improve their hands.");
                System.out.println();
                System.out.println("Play begins with the player (which is you). You have two options:");
                System.out.println("• Stand: Player stands holds their total and ends their turn.");
                System.out.println("• Hit: Player draws another card (and more if the player wishes). If this card causes the player's total points to exceed 21 (known as \"breaking\" or \"busting\") then the player loses.");
                System.out.println();
                System.out.println("Once the player (which is you) has completed their turn, it is the dealer’s turn. The dealer must hit until his card total is 17 or more points. Once the dealer has finshed his turn. The player and dealer both show their cards to see who has the higher score.");
                System.out.println();
                break;
                case "?": //gives help command
                System.out.println();
                System.out.println("You are viewing the command options of Blackjack. Press a key binded to a command to continue.");
                System.out.println();
                break;
                default: //if incorrect input
                System.out.println("Invalid input. Please try again. Please type only one character or the correct input. ");
                System.out.println();
            }
        } while (!choice.equals("q"));
    }

    public static int getDealerScore(ArrayList<String> x, String[] y) //returns the dealers score without any aces
    {
        int dScore = 0;
        for (int i = 0; i < x.size(); i++)
        {
            if (x.get(i).equals(y[0]))
            {
                dScore +=2;
            }
            if (x.get(i).equals(y[1]))
            {
                dScore +=3;
            }
            if (x.get(i).equals(y[2]))
            {
                dScore += 4;
            }
            if (x.get(i).equals(y[3]))
            {
                dScore += 5;
            }
            if (x.get(i).equals(y[4]))
            {
                dScore += 6;
            }
            if (x.get(i).equals(y[5]))
            {
                dScore += 7;
            }
            if (x.get(i).equals(y[6]))
            {
                dScore += 8;
            }
            if (x.get(i).equals(y[7]))
            {
                dScore += 9;
            }
            if (x.get(i).equals(y[8]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[9]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[10]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[11]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[13]))
            {
                dScore += 2;
            }
            if (x.get(i).equals(y[14]))
            {
                dScore += 3;
            }
            if (x.get(i).equals(y[15]))
            {
                dScore += 4;
            }
            if (x.get(i).equals(y[16]))
            {
                dScore += 5;
            }
            if (x.get(i).equals(y[17]))
            {
                dScore += 6;
            }
            if (x.get(i).equals(y[18]))
            {
                dScore += 7;
            }
            if (x.get(i).equals(y[19]))
            {
                dScore += 8;
            }
            if (x.get(i).equals(y[20]))
            {
                dScore += 9;
            }
            if (x.get(i).equals(y[21]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[22]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[23]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[24]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[26]))
            {
                dScore += 2;
            }
            if (x.get(i).equals(y[27]))
            {
                dScore += 3;
            }
            if (x.get(i).equals(y[28]))
            {
                dScore += 4;
            }
            if (x.get(i).equals(y[29]))
            {
                dScore += 5;
            }
            if (x.get(i).equals(y[30]))
            {
                dScore += 6;
            }
            if (x.get(i).equals(y[31]))
            {
                dScore += 7;
            }
            if (x.get(i).equals(y[32]))
            {
                dScore += 8;
            }
            if (x.get(i).equals(y[33]))
            {
                dScore += 9;
            }
            if (x.get(i).equals(y[34]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[35]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[36]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[37]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[39]))
            {
                dScore += 2;
            }
            if (x.get(i).equals(y[40]))
            {
                dScore += 3;
            }
            if (x.get(i).equals(y[41]))
            {
                dScore += 4;
            }
            if (x.get(i).equals(y[42]))
            {
                dScore += 5;
            }
            if (x.get(i).equals(y[43]))
            {
                dScore += 6;
            }
            if (x.get(i).equals(y[44]))
            {
                dScore += 7;
            }
            if (x.get(i).equals(y[45]))
            {
                dScore += 8;
            }
            if (x.get(i).equals(y[46]))
            {
                dScore += 9;
            }
            if (x.get(i).equals(y[47]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[48]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[49]))
            {
                dScore += 10;
            }
            if (x.get(i).equals(y[50]))
            {
                dScore += 10;
            }
        }
        return dScore;
    }

    public static int dealerHasAce(ArrayList<String> x, String[] y) //sees if dealer has any aces
    {
        int numberOfDealerAces = 0;
        for (int i = 0; i < x.size(); i++)
        {
            if (x.get(i).equals(y[12]))
            {
                numberOfDealerAces++;
            }
            if (x.get(i).equals(y[25]))
            {
                numberOfDealerAces++;
            }
            if (x.get(i).equals(y[38]))
            {
                numberOfDealerAces++;
            }
            if (x.get(i).equals(y[51]))
            {
                numberOfDealerAces++;
            }
        }
        return numberOfDealerAces;
    }

    public static ArrayList<String> addToDealerHand(ArrayList<String> x, String [] y, int z) //adds a card to dealer hand
    {
        x.add(y[z]);
        System.out.println("The dealer draws the next card from the deck.");
        return x;
    }

    public static void checkWinner(ArrayList<String> x, int y, boolean z, String [] d, ArrayList<String> cpu) //determines who is the winner between the delaer and player
    {
        boolean playerBust = false;
        int playerScore = 0;
        int playerHasAce = 0;
        for (int i = 0; i < x.size(); i++)
        {
            if (x.get(i).equals(d[0]))
            {
                playerScore += 2;
            }
            if (x.get(i).equals(d[1]))
            {
                playerScore += 3;
            }
            if (x.get(i).equals(d[2]))
            {
                playerScore += 4;
            }
            if (x.get(i).equals(d[3]))
            {
                playerScore += 5;
            }
            if (x.get(i).equals(d[4]))
            {
                playerScore += 6;
            }
            if (x.get(i).equals(d[5]))
            {
                playerScore += 7;
            }
            if (x.get(i).equals(d[6]))
            {
                playerScore += 8;
            }
            if (x.get(i).equals(d[7]))
            {
                playerScore += 9;
            }
            if (x.get(i).equals(d[8]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[9]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[10]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[11]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[12]))
            {
                playerHasAce++;
            }
            if (x.get(i).equals(d[13]))
            {
                playerScore += 2;
            }
            if (x.get(i).equals(d[14]))
            {
                playerScore += 3;
            }
            if (x.get(i).equals(d[15]))
            {
                playerScore += 4;
            }
            if (x.get(i).equals(d[16]))
            {
                playerScore += 5;
            }
            if (x.get(i).equals(d[17]))
            {
                playerScore += 6;
            }
            if (x.get(i).equals(d[18]))
            {
                playerScore += 7;
            }
            if (x.get(i).equals(d[19]))
            {
                playerScore += 8;
            }
            if (x.get(i).equals(d[20]))
            {
                playerScore += 9;
            }
            if (x.get(i).equals(d[21]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[22]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[23]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[24]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[25]))
            {
                playerHasAce++;
            }
            if (x.get(i).equals(d[26]))
            {
                playerScore += 2;
            }
            if (x.get(i).equals(d[27]))
            {
                playerScore += 3;
            }
            if (x.get(i).equals(d[28]))
            {
                playerScore += 4;
            }
            if (x.get(i).equals(d[29]))
            {
                playerScore += 5;
            }
            if (x.get(i).equals(d[30]))
            {
                playerScore += 6;
            }
            if (x.get(i).equals(d[31]))
            {
                playerScore += 7;
            }
            if (x.get(i).equals(d[32]))
            {
                playerScore += 8;
            }
            if (x.get(i).equals(d[33]))
            {
                playerScore += 9;
            }
            if (x.get(i).equals(d[34]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[35]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[36]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[37]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[38]))
            {
                playerHasAce++;
            }
            if (x.get(i).equals(d[39]))
            {
                playerScore += 2;
            }
            if (x.get(i).equals(d[40]))
            {
                playerScore += 3;
            }
            if (x.get(i).equals(d[41]))
            {
                playerScore += 4;
            }
            if (x.get(i).equals(d[42]))
            {
                playerScore += 5;
            }
            if (x.get(i).equals(d[43]))
            {
                playerScore += 6;
            }
            if (x.get(i).equals(d[44]))
            {
                playerScore += 7;
            }
            if (x.get(i).equals(d[45]))
            {
                playerScore += 8;
            }
            if (x.get(i).equals(d[46]))
            {
                playerScore += 9;
            }
            if (x.get(i).equals(d[47]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[48]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[49]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[50]))
            {
                playerScore += 10;
            }
            if (x.get(i).equals(d[51]))
            {
                playerHasAce++;
            }
        }
        if (playerHasAce == 4) //if player has four aces in his hand
        {
            if (playerScore == 17 || playerScore == 7) //if player score is 17 with four ace values of 1 is blackjack. if player score is 7 with three ace values of 1 and one value of 11 is blackjack
            {
                playerScore = 21;
            }
            if (playerScore < 17 && playerScore > 7) //playerScore values from 8 to 16
            {
                playerScore += 4;
            }
            if (playerScore < 7) //playerScores values: 0,2,3,4,5,6
            {
                playerScore += 14;
            }
            if (playerScore > 17) //if player score is greater than 17, then with four ace values of 1 the dealer has busted
            {
                playerScore += 4;
                playerBust = true;
            }
        }
        if (playerHasAce == 3)
        {
            if (playerScore == 18 || playerScore == 8) //if player score is 18 with three ace values of 1 is blackjack. if player score is 8 with two ace values of 2 and one ace value of 11 is blackjack
            {
                playerScore = 21;
            }
            if (playerScore < 18 && playerScore > 8) //player score values: 9 to 17
            {
                playerScore += 3;
            }
            if (playerScore < 8) //if player score is less than 8
            {
                playerScore += 13;
            }
            if (playerScore > 18) //if player score is greater than 18, three ace values of 1 means the dealer has busted
            {
                playerScore += 3;
                playerBust = true;
            }
        }
        if (playerHasAce == 2)
        {
            if (playerScore == 9 || playerScore == 19) //if player score is 9 with one value of 1 and one ace value of 11 is blackjack. if player score is 19 with two ace values of 1 is blackjack
            {
                playerScore = 21;
            }
            if (playerScore < 19 && playerScore > 9) //10,11,12,13,14,15,16,17,18
            {
                playerScore += 2;
            }
            if (playerScore < 9) //0,2,3,4,5,6,7,8
            {
                playerScore += 12;
            }
            if (playerScore > 19) //if playerScore is greater than 19 with two ace values of 1, the dealer has busted
            {
                playerScore += 2;
                playerBust = true;
            }
        }
        if (playerHasAce == 1) //if player has one ace in his hand
        {
            if (playerScore == 20 || playerScore == 10) //if player score is 20 with an ace value of 1 is blackjack. if player score is 10 with an avlue of 11 is blackjack
            {
                playerScore = 21;
            }
            if (playerScore < 20 && playerScore > 10) //for player scores : 19,18,17,16,15,14,13,12,11
            {
                playerScore += 1;
            }
            if (playerScore < 10) //for player scores: 9,8,7,6,5,4,3,2,0
            {
                playerScore += 11;
            }
            if (playerScore > 21) //if player score is greater than 21, the you have busted
            {
                playerScore += 1;
                playerBust = true;
            }
        }
        if (playerHasAce == 0)
        {
            if (playerScore > 21)
            {
                playerBust = true;
            }
        }
        if (playerBust == true && z == true)
        {
            System.out.println("You and the dealer both busted. It's a tie game");
            System.out.println();
        }
        else if (playerBust == true && z == false)
        {
            System.out.println("You busted and the dealer didn't. You lose.");
            System.out.println();
        }
        else if (playerBust == false && z == true)
        {
            System.out.println("The dealer busted and you didn't! You win!");
            System.out.println();
        }
        else
        {
            if (playerScore > y) //if playerScore > dealerScore
            {
                System.out.println("You scored higher than the dealer! You win!");
            }
            else if (playerScore < y)
            {
                System.out.println("You scored lower than the dealer. You lose.");
            }
            else
            {
                System.out.println("You and the dealer tied for score. It's a tie game.");
            }
            System.out.println();
        }
        System.out.println("Your hand was: " + x);
        System.out.println("The dealer's hand was: " + cpu);
        System.out.println("Player score: " + playerScore);
        System.out.println("Dealer score: " + y);
        System.out.println();
    }

    public static int addCard(int x) //adds plus one to cards that are dealt
    {
        x++;
        return x;
    }
}
