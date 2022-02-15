package Piles;

import Cards.Card;

import java.util.Stack;

/**
 * The Piles.Pile class is an abstract class that provides a stack representing the card pile,
 * and rapper functions to manipulate the stack.
 */
public abstract class Pile {
    protected Stack<Card> pile;

    /**
     * Exactly what you'd expect...
     */
    public Pile() {
        pile = new Stack<>();
    }

    /** Rapper for stack function */
    public Card pop() { return pile.pop(); }
    /** Rapper for stack function */
    public Card peek() { return pile.peek(); }
    /** Rapper for stack function */
    public void push(Card card) { pile.push(card); }
    /** Rapper for stack function */
    public int size() { return pile.size(); }
    /** Rapper for stack function */
    public boolean empty() { return pile.empty(); }
}
