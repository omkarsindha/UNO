public class Player {
    private String name;
    private Hand hand;

    public Player(String name, Hand hand){
        this.name= name;
        this.hand = hand;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CardHolder getHand() {
        return this.hand;
    }

    public void setHand(Hand hand) {
        this.hand = hand;
    }


    public void playCard(Card card) {
        hand.removeCardFromHand(card);
    }

    @Override
    public String toString() {
        return "Player{" +
                "name='" + name + '\'' +
                ", hand=" + hand +
                '}';
    }
}
