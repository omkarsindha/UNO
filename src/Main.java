import java.util.*;

public class Main {
    public static void main(String[] args) {
        instantiateDeck();
    }
    public static void instantiateDeck() {

        Hand player1Cards = new Hand();
        Hand player2Cards = new Hand();
        Hand player3Cards = new Hand();

        Deck deck = new Deck();
        generateDeck(deck);
        System.out.println(deck.seeHand());
        Collections.shuffle(deck.seeHand());

        //Distributing cards
        getCardsFromDeck(player1Cards,deck);
        System.out.println("Player1 Cards: " + player1Cards.seeHand());
        getCardsFromDeck(player2Cards,deck);
        System.out.println("Player2 Cards: " + player2Cards.seeHand());
        getCardsFromDeck(player3Cards,deck);
        System.out.println("Player3 Cards: " + player3Cards.seeHand());

        Card firstCardinPile = deck.seeHand().get(0);
        System.out.println("First card on board: "+firstCardinPile);
        deck.seeHand().remove(firstCardinPile);

        Player player1 = new Player("Player 1", player1Cards);
        Player player2 = new Player("Player 2", player2Cards);
        Player player3 = new Player("PLayer 3", player3Cards);

        startGame(player1,player2,player3,firstCardinPile,deck);
    }

    public static void startGame(Player p1,Player p2,Player p3, Card firstCardinPile,Deck deck){
        ArrayList<Player> players = new ArrayList<>();
        players.add(p1);
        players.add(p2);
        players.add(p3);

        HashMap<Integer,Card> cardIndexHolder = new HashMap<>();
        Scanner input = new Scanner(System.in);
        boolean won = false;
        Card topCard =  firstCardinPile;
        System.out.println("\n************Game Board**********\n");

        Player currentPlayer = p1;
        Player nextPlayer = p2;

        while(!won){
            System.out.println(" ");
            System.out.println("Top card is: " + topCard);
            System.out.println( currentPlayer.getName()+ "'s "+"hand: ");
//            System.out.println(p.getHand().seeHand());
            int cardNumber = 1;
            //creating hash map
            for (Card card: currentPlayer.getHand().seeHand()) {
                System.out.print(cardNumber + "-" +card+ "  ");
                cardIndexHolder.put(cardNumber,card);
                cardNumber++;
            }
            System.out.println(0 + " " + "Draw a Card");

            System.out.print("Choose to Play: ");
            int cardChosen = input.nextInt();
            Card playingCard = cardIndexHolder.get(cardChosen);

            if(cardChosen == 0){
                currentPlayer.getHand().seeHand().add(deck.seeHand().get(0));
                System.out.println("Card drawn: " + deck.seeHand().get(0));
                deck.seeHand().remove(0);
                //changing players
                currentPlayer = nextPlayer;
                if (players.indexOf(nextPlayer) == (players.size() - 1)) {
                    nextPlayer = players.get(0);
                } else {
                    nextPlayer = players.get(players.indexOf(nextPlayer) + 1);
                }
            }
            //playable card block
            else if(canPlayCard(topCard,playingCard)){
                System.out.println("Player choose to play: "+ playingCard);
                currentPlayer.getHand().seeHand().remove(cardChosen-1);
                cardActionHandler(players,currentPlayer,nextPlayer,deck,playingCard);
                topCard = playingCard;

                //changing players
                currentPlayer = nextPlayer;
                if (players.indexOf(nextPlayer) == (players.size() - 1)) {
                    nextPlayer = players.get(0);
                } else {
                    nextPlayer = players.get(players.indexOf(nextPlayer) + 1);
                }
            }
            else{
                System.out.println("Cannot play card choose a card again\n ");
            }
            //checking win condition
            if (currentPlayer.getHand().seeHand().isEmpty()){
                System.out.println("***************************");
                System.out.println(currentPlayer.getName() + " won the game!!!");
                won = true;
            }

        }

    }

    public static void generateDeck(Deck deck){
        for (Color color : Color.values()) {
            if (color != Color.WILD) {
                for (Value value : Value.values()) {
                    if (value != Value.NONE && value!=Value.DRAW_FOUR) {
                        for (int i = 0; i < 2; i++) {
                            deck.addCardToHand(new Card(color, value));
                        }
                    }
                }
            }
        }
        for (int i = 0; i < 4; i++) {
            deck.addCardToHand(new Card(Color.WILD, Value.DRAW_TWO));
            deck.addCardToHand(new Card(Color.WILD, Value.DRAW_FOUR));
        }
    }
    public static void getCardsFromDeck(Hand playerCards,Deck deck){
        for (int i = 0; i < 3; i++) {
            playerCards.addCardToHand(deck.seeHand().get(0));
            deck.seeHand().remove(deck.seeHand().get(0));
        }
    }

    public static boolean canPlayCard(Card topCard, Card playingCard){
        if(playingCard.getColor() == Color.WILD){
            return true;
        } else if (topCard.getValue() == playingCard.getValue()) {
            return true;
        } else if (topCard.getColor() == playingCard.getColor()) {
            return true;
        } else{
            return false;
        }
    }
    public static void cardActionHandler(ArrayList<Player> players,Player currentPlayer, Player nextPlayer,CardHolder deck, Card card){

        if(card.getValue() == Value.DRAW_TWO){
            nextPlayer.getHand().addCardToHand(deck.seeHand().get(0));
            nextPlayer.getHand().addCardToHand(deck.seeHand().get(1));
            deck.seeHand().remove(0);
            deck.seeHand().remove(0);
            nextPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }

        if(card.getValue() == Value.DRAW_FOUR){
            nextPlayer.getHand().addCardToHand(deck.seeHand().get(0));
            nextPlayer.getHand().addCardToHand(deck.seeHand().get(1));
            nextPlayer.getHand().addCardToHand(deck.seeHand().get(2));
            nextPlayer.getHand().addCardToHand(deck.seeHand().get(3));
            deck.seeHand().remove(0);
            deck.seeHand().remove(0);
            deck.seeHand().remove(0);
            deck.seeHand().remove(0);
            nextPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }

        if(card.getValue()==Value.SKIP){
           nextPlayer = players.get(players.indexOf(currentPlayer) + 1);
        }

        if(card.getColor()==Color.WILD){
            System.out.print("Which color do you want to use? (1-RED, 2-BLUE, 3-GREEN, 4-YELLOW): ");
            Scanner sc = new Scanner(System.in);
            int choice = sc.nextInt();
            if(choice == 1){
                card.setColor(Color.RED);
                card.setValue(Value.NONE);
            } else if (choice == 2) {
                card.setColor(Color.BLUE);
                card.setValue(Value.NONE);
            } else if(choice == 3){
                card.setColor(Color.GREEN);
                card.setValue(Value.NONE);
            } else if (choice == 4) {
                card.setColor(Color.YELLOW);
                card.setValue(Value.NONE);
            }
            else{
                System.out.println("Invalid choice :(");
            }
        }
    }



}