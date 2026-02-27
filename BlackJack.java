import java.util.*;

public class BlackJack{
   private int playerPot;
   private int dealerPot;
   private int playerBet;
   private int dealerBet;
   private int playerCardCount;
   private int dealerCardCount;
   private int playerCardNum;
   private int dealerCardNum;
   private int playerAceCount;
   private int dealerAceCount;
   private int playerAceReduceCounter;
   private int dealerAceReduceCounter;
   private ArrayList<String> deck;
   private String[] cards = {"2H", "3H", "4H", "5H", "6H", "7H", "8H", "9H", "10H", "JH", "QH", "KH", "AH",
             "2D", "3D", "4D", "5D", "6D", "7D", "8D", "9D", "10D", "JD", "QD", "KD", "AD",
             "2C", "3C", "4C", "5C", "6C", "7C", "8C", "9C", "10C", "JC", "QC", "KC", "AC",
             "2S", "3S", "4S", "5S", "6S", "7S", "8S", "9S", "10S", "JS", "QS", "KS", "AS"};
   private Scanner read;

   public BlackJack() {
    playerPot = 100000;
    dealerPot = 100000;
    playerBet = 0;
    dealerBet = 0;
    playerCardCount = 0;
    dealerCardCount = 0;
    playerCardNum = 0;
    dealerCardNum = 0;
    deck = new ArrayList<>(Arrays.asList(cards));
    read = new Scanner(System.in);
    playerAceCount = 0;
    dealerAceCount = 0;
    playerAceReduceCounter = 0;
    dealerAceReduceCounter = 0;
   }

   public void pickBet(){
    if(playerPot == 0){
        playerPot = 100000;
    }else if(dealerPot == 0){
        dealerPot += 100000;
      }
    System.out.println("Your Pot: " + playerPot + " and Dealer's Pot: " + dealerPot);
      System.out.println("Enter your bet amount:");
      playerBet = read.nextInt();
      dealerBet = playerBet;

      
      if (playerBet <= 0 || playerPot - playerBet < 0 || dealerPot - dealerBet < 0) {
         System.out.println("Invalid bet amount.");
         pickBet();
      }else{
      playerPot -= playerBet;
      dealerPot -= dealerBet;
      }
    }
    //Distributes first two cards for player and dealer
   public void giveCards(){
    playerAceCount = 0;
    dealerAceCount = 0;
    playerAceReduceCounter = 0;
    dealerAceReduceCounter = 0;
    for(int i = 0; i < 2; i++){
        int cardIndex = (int)(Math.random() * deck.size());
        String tempCardHolder = deck.remove(cardIndex);
        if(tempCardHolder.substring(0, 1).equals("J") || tempCardHolder.substring(0, 1).equals("Q") || tempCardHolder.substring(0, 1).equals("K")){
            playerCardCount += 10;
            System.out.println("You drawed " + tempCardHolder);
        }else if(tempCardHolder.substring(0, 2).equals("10")){
            playerCardCount += 10;
            System.out.println("You drawed " + tempCardHolder);
        }
        else if(tempCardHolder.substring(0, 1).equals("A") && playerCardCount <= 10 && playerAceCount == 0){
            playerCardCount += 11;
            playerAceCount++;
            System.out.println("You drawed " + tempCardHolder);
        }else if(tempCardHolder.substring(0, 1).equals("A") && (playerAceCount > 0 || playerCardCount > 10)){
           if(playerCardCount > 10)
            playerCardCount++;
          else if (playerAceCount > 0)
            playerCardCount -=9;
            playerAceCount++;
            System.out.println("You drawed " + tempCardHolder);
        }else{
        playerCardCount += Integer.valueOf(tempCardHolder.substring(0, 1));
        System.out.println("You drawed " + tempCardHolder);
        }
        playerCardNum++;
    }
    System.out.println("Your cards: " + playerCardCount);

    for(int j = 0; j < 2; j++){
        int cardIndex = (int)(Math.random() * deck.size());
        String tempCardHolder = deck.remove(cardIndex);
        if(tempCardHolder.substring(0, 1).equals("J") || tempCardHolder.substring(0, 1).equals("Q") || tempCardHolder.substring(0, 1).equals("K") || tempCardHolder.substring(0, 2).equals("10")){
                dealerCardCount += 10;
            }else if(tempCardHolder.substring(0, 1).equals("A") && dealerCardCount <= 10 && dealerAceCount == 0){
                dealerCardCount += 11;
                dealerAceCount++;
            }else if(tempCardHolder.substring(0, 1).equals("A") && (dealerAceCount > 0 || dealerCardCount > 10)){
                if(dealerCardCount > 10)
                    dealerCardCount++;
                else if (dealerAceCount > 0)
                    dealerCardCount -= 9;
                dealerAceCount++;
            }else{
                dealerCardCount += Integer.valueOf(tempCardHolder.substring(0, 1));;
            }
        if(j == 0)
            System.out.println("Dealer's first card: " + dealerCardCount);
        dealerCardNum++;
    }
   }
   //Function that decides whether you proceed after the initial card pulls
   public void hitOrStand(){
    if(playerCardCount == 21){
                playerPot += playerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("Blackjack! You win!");
                playGame();
    }else if(dealerCardCount == 21){
                dealerPot += dealerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("Dealer has Blackjack! You lose.");
                playGame();
    }
        System.out.println("Do you want to hit or stand? (h/s)");
        if(read.next().equalsIgnoreCase("h")){
            int cardIndex = (int)(Math.random() * deck.size());
            String tempCardHolder = deck.remove(cardIndex);
        if(tempCardHolder.substring(0, 1).equals("J") || tempCardHolder.substring(0, 1).equals("Q") || tempCardHolder.substring(0, 1).equals("K")){
            if(playerAceCount > 0 && playerCardCount > 10 && playerAceReduceCounter == 0){
                playerCardCount -= 10;
                playerAceReduceCounter++;
            }
            playerCardCount += 10;
            System.out.println("You drawed " + tempCardHolder);
        }else if(tempCardHolder.substring(0, 2).equals("10")){
            if(playerAceCount > 0 && playerCardCount > 10 && playerAceReduceCounter == 0){
                playerCardCount -= 10;
                playerAceReduceCounter++;
            }
            playerCardCount += 10;
            System.out.println("You drawed " + tempCardHolder);
        }else if(tempCardHolder.substring(0, 1).equals("A") && playerCardCount <= 10 && playerAceCount == 0){
            playerCardCount += 11;
            playerAceCount++;
            System.out.println("You drawed " + tempCardHolder);
        }else if(tempCardHolder.substring(0, 1).equals("A") && (playerAceCount > 0 || playerCardCount > 10)){
          if(playerCardCount > 10)
            playerCardCount++;
          else if (playerAceCount > 0)
            playerCardCount -= 9;
            playerAceCount++;
            System.out.println("You drawed " + tempCardHolder);
        }else{
        if(playerAceCount > 0 && playerCardCount > 10 && playerAceReduceCounter == 0){
                playerCardCount -= 10;
                playerAceReduceCounter++;
            }
        playerCardCount += Integer.valueOf(tempCardHolder.substring(0, 1));
        System.out.println("You drawed " + Integer.valueOf(tempCardHolder.substring(0, 1)));
        }
        System.out.println("Your cards:" + playerCardCount);
        if(playerCardNum == 5){
            playerPot += playerBet * 2;
            playerBet = 0;
            dealerBet = 0;
           System.out.println("Player Wins!");
           playGame();
        }else if(playerCardCount > 21){
                dealerPot += playerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("You busted!");
                playGame();
            }else if(playerCardCount == 21){
                playerPot += playerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("Blackjack! You win!");
                playGame();
            }else
                hitOrStand();
        }else{
           while(dealerCardCount < playerCardCount){
            int cardIndex = (int)(Math.random() * deck.size());
            String tempCardHolder = deck.remove(cardIndex);
            if(tempCardHolder.substring(0, 1).equals("J") || tempCardHolder.substring(0, 1).equals("Q") || tempCardHolder.substring(0, 1).equals("K")){
                if(dealerAceCount > 0 && dealerCardCount > 10 && dealerAceReduceCounter == 0){
                    dealerCardCount -= 10;
                    dealerAceReduceCounter++;
                }
                dealerCardCount += 10;
                System.out.println("Dealer draws: " + tempCardHolder);
            }else if(tempCardHolder.substring(0, 2).equals("10")){
                if(dealerAceCount > 0 && dealerCardCount > 10 && dealerAceReduceCounter == 0){
                    dealerCardCount -= 10;
                    dealerAceReduceCounter++;
                }
                dealerCardCount += 10;
                System.out.println("Dealer draws: " + tempCardHolder);
            }else if(tempCardHolder.substring(0, 1).equals("A") && dealerCardCount <= 10 && dealerAceCount == 0){
                dealerCardCount += 11;
                dealerAceCount++;
                System.out.println("Dealer draws: " + tempCardHolder);
            }else if(tempCardHolder.substring(0, 1).equals("A") && (dealerAceCount > 0 || dealerCardCount > 10)){
                if(dealerCardCount > 10)
                    dealerCardCount++;
                else if (dealerAceCount > 0){
                    dealerCardCount -= 9;
                    dealerAceCount++;
                }
                System.out.println("Dealer draws: " + tempCardHolder);
            }else{
                if(dealerAceCount > 0 && dealerCardCount > 10 && dealerAceReduceCounter == 0){
                    dealerCardCount -= 10;
                    dealerAceReduceCounter++;
                }
                dealerCardCount += Integer.valueOf(tempCardHolder.substring(0, 1));
                System.out.println("Dealer draws: " + tempCardHolder);
            }
           }
           System.out.println("Dealer's cards:" + dealerCardCount);
            if(dealerCardCount > 21){
                playerPot += dealerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("You win!");
                playGame();
            }else if(dealerCardCount == 21){
                dealerPot += playerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("You busted!");
                playGame();
            }else if(dealerCardCount == playerCardCount){
                dealerPot += playerBet;
                playerPot += dealerBet;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("Draw");
                playGame();
            }else if(dealerCardCount < playerCardCount){
                playerPot += dealerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("You Win!");
                playGame();
            }else if(dealerCardCount > playerCardCount){
                dealerPot += playerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("Dealer Wins!");
                playGame();
            }else if(dealerCardNum == 5){
                dealerPot += playerBet * 2;
                playerBet = 0;
                dealerBet = 0;
                System.out.println("Dealer Wins!");
                playGame();
            
        }
        }
   }

   public void playGame(){
        System.out.println("Do you want to play? (y/n)");
        if(read.next().equalsIgnoreCase("y")){
            playerCardCount = 0;
            dealerCardCount = 0;
            deck = new ArrayList<>(Arrays.asList(cards));
            pickBet();
            giveCards();
            hitOrStand();
            
        }else
        System.out.println("Loser");
   }

   public static void main(String[]args){
        BlackJack Game = new BlackJack();
        Game.playGame();
   }
}
