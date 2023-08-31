import java.util.ArrayList;

public interface CardHolder {

    void addCardToHand(Card card);
    void removeCardFromHand(Card card);
    ArrayList<Card> seeHand();

    @Override
    public String toString();
}
