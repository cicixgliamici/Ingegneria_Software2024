package org.example.enumeration;

/**
 * Enum representing the position of a card in various stages of the game.
 * The card can be in one of the following positions:
 * HAND - The card is in the player's hand.
 * PLAYGROUND - The card is on the playground.
 * DECKHIDDEN - The card is in the deck and hidden from view.
 * DECKVISIBLE - The card is in the deck and visible to players.
 */
public enum CardPosition {
    HAND, PLAYGROUND, DECKHIDDEN, DECKVISIBLE
}
