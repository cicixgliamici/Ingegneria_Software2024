package org.example.model.playarea;

import org.example.model.deck.Card;
import java.util.List;

/**
 * Class for the placement of cards.
 * The node is an Object with 4 connections to other nodes.
 * You can go to every node only from the beginner.
 */
public class Node extends PlaceHolder {
    private Card card;
    private PlaceHolder topL;
    private PlaceHolder topR;
    private PlaceHolder botL;
    private PlaceHolder botR;

    /**
     * Constructs a Node with a card and position, setting its connections.
     *
     * @param card the card to place in the node
     * @param x the x-coordinate of the node
     * @param y the y-coordinate of the node
     * @param placeHolderList the list of placeholders
     * @param AvailableNodes the list of available nodes
     * @param AllNodes the list of all nodes
     */
    public Node(Card card, int x, int y, List<PlaceHolder> placeHolderList, List<PlaceHolder> AvailableNodes, List<PlaceHolder> AllNodes) {
        super(x, y);
        this.card = card;
        this.setPlaceHolderByCard(AvailableNodes, placeHolderList);
        this.setNullNode(AvailableNodes);
        AllNodes.add(this);
    }

    /**
     * Constructs a Node with a card, its connections, and position.
     *
     * @param card the card to place in the node
     * @param topL the top-left placeholder
     * @param topR the top-right placeholder
     * @param botL the bottom-left placeholder
     * @param botR the bottom-right placeholder
     * @param x the x-coordinate of the node
     * @param y the y-coordinate of the node
     */
    public Node(Card card, PlaceHolder topL, PlaceHolder topR, PlaceHolder botL, PlaceHolder botR, int x, int y) {
        super(x, y);
        this.card = card;
        this.topL = topL;
        this.topR = topR;
        this.botL = botL;
        this.botR = botR;
    }

    /**
     * Creates an empty and unusable node for when the corner is hidden.
     *
     * @return a new null node for the top-right position
     */
    public Node nullNodeTopR() {
        int x = this.x + 1;
        int y = this.y + 1;
        return new Node(null, null, null, this, null, x, y);
    }

    /**
     * Creates an empty and unusable node for when the corner is hidden.
     *
     * @return a new null node for the top-left position
     */
    public Node nullNodeTopL() {
        int x = this.x - 1;
        int y = this.y + 1;
        return new Node(null, null, null, null, this, x, y);
    }

    /**
     * Creates an empty and unusable node for when the corner is hidden.
     *
     * @return a new null node for the bottom-right position
     */
    public Node nullNodeBotR() {
        int x = this.x + 1;
        int y = this.y - 1;
        return new Node(null, this, null, null, null, x, y);
    }

    /**
     * Creates an empty and unusable node for when the corner is hidden.
     *
     * @return a new null node for the bottom-left position
     */
    public Node nullNodeBotL() {
        int x = this.x - 1;
        int y = this.y - 1;
        return new Node(null, null, this, null, null, x, y);
    }

    /**
     * Sets null nodes for the hidden corners.
     *
     * @param AvailableNodes the list of available nodes
     */
    public void setNullNode(List<PlaceHolder> AvailableNodes) {
        if (this.botR == null) {
            this.botR = nullNodeBotR();
            AvailableNodes.add((Node) this.botR);
        }

        if (this.botL == null) {
            this.botL = nullNodeBotL();
            AvailableNodes.add((Node) this.botL);
        }

        if (this.topL == null) {
            this.topL = nullNodeTopL();
            AvailableNodes.add((Node) this.topL);
        }

        if (this.topR == null) {
            this.topR = nullNodeTopR();
            AvailableNodes.add((Node) this.topR);
        }
    }

    /**
     * Sets placeholders based on the card's hidden corners.
     *
     * @param AvailableNodes the list of available nodes
     * @param PlaceHolders the list of placeholders
     */
    public void setPlaceHolderByCard(List<PlaceHolder> AvailableNodes, List<PlaceHolder> PlaceHolders) {
        if (this.getCard().BOTRCornerIsHidden()) {
            if (this.botR == null) {
                this.botR = new PlaceHolder(this.x + 1, this.y - 1);
                PlaceHolders.add(this.botR);
                if (AvailableNodes != null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if (node.x == this.x + 1 && node.y == this.y - 1) {
                            if (node.getTopR() != null) {
                                (node.getTopR()).setBotL(this.botR);
                            }
                            if (node.getBotL() != null) {
                                (node.getBotL()).setTopR(this.botR);
                            }
                            if (node.getBotR() != null) {
                                (node.getBotR()).setTopL(this.botR);
                            }
                            AvailableNodes.remove(node);
                        }
                    }
                }
            }
        }

        if (this.getCard().BOTLCornerIsHidden()) {
            if (this.botL == null) {
                this.botL = new PlaceHolder(this.x - 1, this.y - 1);
                PlaceHolders.add(this.botL);
                if (AvailableNodes != null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if (node.x == this.x - 1 && node.y == this.y - 1) {
                            if (node.getTopL() != null) {
                                (node.getTopL()).setBotR(this.botL);
                            }
                            if (node.getBotL() != null) {
                                (node.getBotL()).setTopR(this.botL);
                            }
                            if (node.getBotR() != null) {
                                (node.getBotR()).setTopL(this.botL);
                            }
                            AvailableNodes.remove(node);
                        }
                    }
                }
            }
        }

        if (this.getCard().TOPRCornerIsHidden()) {
            if (this.topR == null) {
                this.topR = new PlaceHolder(this.x + 1, this.y + 1);
                PlaceHolders.add(this.topR);
                if (AvailableNodes != null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if (node.x == this.x + 1 && node.y == this.y + 1) {
                            if (node.getTopR() != null) {
                                node.getTopR().setBotL(this.topR);
                            }
                            if (node.getBotR() != null) {
                                node.getBotR().setTopL(this.topR);
                            }
                            if (node.getTopL() != null) {
                                node.getTopL().setBotR(this.topR);
                            }
                            AvailableNodes.remove(node);
                        }
                    }
                }
            }
        }

        if (this.getCard().TOPLCornerIsHidden()) {
            if (this.topL == null) {
                this.topL = new PlaceHolder(this.x - 1, this.y + 1);
                PlaceHolders.add(this.topL);
                if (AvailableNodes != null && !AvailableNodes.isEmpty()) {
                    for (PlaceHolder node : AvailableNodes) {
                        if (node.x == this.x - 1 && node.y == this.y + 1) {
                            if (node.getTopL() != null) {
                                node.getTopL().setBotR(this.topL);
                            }
                            if (node.getBotL() != null) {
                                node.getBotL().setTopR(this.topL);
                            }
                            if (node.getTopR() != null) {
                                node.getTopR().setBotL(this.topL);
                            }
                            AvailableNodes.remove(node);
                        }
                    }
                }
            }
        }
    }

    /**
     * Checks whether the 4 new nodes don't overlap with any placeholders and assigns the placeholder.
     *
     * @param placeHolderList the list of placeholders
     */
    public void setNodePlaceHolder(List<PlaceHolder> placeHolderList) {
        if (this.botR == null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if (placeHolder.x == this.x + 1 && placeHolder.y == this.y - 1) {
                    this.botR = placeHolder;
                }
            }
        }
        if (this.botL == null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if (placeHolder.x == this.x - 1 && placeHolder.y == this.y - 1) {
                    this.botL = placeHolder;
                }
            }
        }
        if (this.topR == null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if (placeHolder.x == this.x + 1 && placeHolder.y == this.y + 1) {
                    this.topR = placeHolder;
                }
            }
        }
        if (this.topL == null) {
            for (PlaceHolder placeHolder : placeHolderList) {
                if (placeHolder.x == this.x - 1 && placeHolder.y == this.y + 1) {
                    this.topL = placeHolder;
                }
            }
        }
    }

    /**
     * Checks whether the new node doesn't overlap with any placed card.
     *
     * @param AllNodes the list of all nodes
     */
    public void setNodeForExistingCard(List<PlaceHolder> AllNodes) {
        if (this.botR == null) {
            for (PlaceHolder node : AllNodes) {
                if (node.x == this.x + 1 && node.y == this.y - 1) {
                    this.botR = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if (this.botL == null) {
            for (PlaceHolder node : AllNodes) {
                if (node.x == this.x - 1 && node.y == this.y - 1) {
                    this.botL = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if (this.topR == null) {
            for (PlaceHolder node : AllNodes) {
                if (node.x == this.x + 1 && node.y == this.y + 1) {
                    this.topR = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
        if (this.topL == null) {
            for (PlaceHolder node : AllNodes) {
                if (node.x == this.x - 1 && node.y == this.y + 1) {
                    this.topL = node;
                    this.getCard().setCoveredCornerByCard(this.getCard().getCoveredCornerByCard() + 1);
                }
            }
        }
    }

    /**
     * Checks whether the new node doesn't overlap with any already existing free node.
     *
     * @param AvailableNodes the list of available nodes
     */
    public void setNodeForNewCard(List<PlaceHolder> AvailableNodes) {
        if (this.botR == null) {
            for (PlaceHolder node : AvailableNodes) {
                if (node.x == this.x + 1 && node.y == this.y - 1) {
                    this.botR = node;
                }
            }
        }
        if (this.botL == null) {
            for (PlaceHolder node : AvailableNodes) {
                if (node.x == this.x - 1 && node.y == this.y - 1) {
                    this.botL = node;
                }
            }
        }
        if (this.topR == null) {
            for (PlaceHolder node : AvailableNodes) {
                if (node.x == this.x + 1 && node.y == this.y + 1) {
                    this.topR = node;
                }
            }
        }
        if (this.topL == null) {
            for (PlaceHolder node : AvailableNodes) {
                if (node.x == this.x - 1 && node.y == this.y + 1) {
                    this.topL = node;
                }
            }
        }
    }

    /**
     * Assigns the card to the new node and updates the placeholders and nodes.
     *
     * @param c the card to assign
     * @param placeHolderList the list of placeholders
     * @param AvailableNodes the list of available nodes
     * @param AllNodes the list of all nodes
     */
    public void setCardNode(Card c, List<PlaceHolder> placeHolderList, List<PlaceHolder> AvailableNodes, List<PlaceHolder> AllNodes) {
        this.card = c;
        this.card.setCoveredCornerByCard(1);
        this.setPlaceHolderByCard(AvailableNodes, placeHolderList);
        this.setNodePlaceHolder(placeHolderList);
        this.setNodeForExistingCard(AllNodes);
        this.setNodeForNewCard(AvailableNodes);
        this.setNullNode(AvailableNodes);
        AllNodes.add(this);
    }

    // Getter and setter methods

    /**
     * Getter for the card.
     *
     * @return the card
     */
    public Card getCard() {
        return card;
    }

    /**
     * Getter for the top-left placeholder.
     *
     * @return the top-left placeholder
     */
    public PlaceHolder getTopL() {
        return topL;
    }

    /**
     * Getter for the top-right placeholder.
     *
     * @return the top-right placeholder
     */
    public PlaceHolder getTopR() {
        return topR;
    }

    /**
     * Getter for the bottom-left placeholder.
     *
     * @return the bottom-left placeholder
     */
    public PlaceHolder getBotL() {
        return botL;
    }

    /**
     * Getter for the bottom-right placeholder.
     *
     * @return the bottom-right placeholder
     */
    public PlaceHolder getBotR() {
        return botR;
    }

    @Override
    public String toString() {
        return "nodo: " + x + " " + y;
    }

    /**
     * Setter for the card.
     *
     * @param card the card to set
     */
    public void setCard(Card card) {
        this.card = card;
    }

    /**
     * Setter for the top-left placeholder.
     *
     * @param topL the top-left placeholder to set
     */
    public void setTopL(PlaceHolder topL) {
        this.topL = topL;
    }

    /**
     * Setter for the top-right placeholder.
     *
     * @param topR the top-right placeholder to set
     */
    public void setTopR(PlaceHolder topR) {
        this.topR = topR;
    }

    /**
     * Setter for the bottom-left placeholder.
     *
     * @param botL the bottom-left placeholder to set
     */
    public void setBotL(PlaceHolder botL) {
        this.botL = botL;
    }

    /**
     * Setter for the bottom-right placeholder.
     *
     * @param botR the bottom-right placeholder to set
     */
    public void setBotR(PlaceHolder botR) {
        this.botR = botR;
    }
}
