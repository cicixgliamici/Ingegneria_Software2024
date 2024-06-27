package org.example.model.playarea;

import org.example.enumeration.CardRes;
import org.example.enumeration.GoldenPoint;
import org.example.enumeration.Side;
import org.example.enumeration.Type;
import org.example.model.Model;
import org.example.model.deck.*;
import org.example.enumeration.cast.CastCardRes;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Represents the area of a player within a card game, holding their hand, played cards,
 * and managing interactions with game nodes.
 */
public class PlayerCardArea {
    private final List<Card> hand = new ArrayList<>(); // Cards currently held by the player.
    private List<Card> tempSecretObjective; // Temporary storage for secret objectives.
    private Card secretObjective; // The player's secret objective card.
    private final Counter counter = new Counter(); // Tracks various game metrics like resources and points.
    private final List<PlaceHolder> allNodes; // All nodes available in the game.
    private final List<PlaceHolder> availableNodes; // Nodes available for new cards.
    private final List<PlaceHolder> placeHolders; // Current placeholders where cards are or can be placed.
    private List<PlaceHolder> alreadyUsed; // Nodes that have already been used for specific checks.
    private Card cardStarter; // The starter card for the player.


    /**
     * Constructs a new PlayerCardArea with initialized lists.
     * 
     * This constructor initializes the PlayerCardArea by creating empty lists for 
     * available nodes, all nodes, placeholders, already used nodes, and temporary 
     * secret objectives.
     */
    public PlayerCardArea() {
        this.availableNodes = new ArrayList<>();
        this.allNodes = new ArrayList<>();
        this.placeHolders = new ArrayList<>();
        this.alreadyUsed = new ArrayList<>();
        this.tempSecretObjective = new ArrayList<>();
    }

    /**
     * Sets the starting node with the starting card and updates the game counters accordingly.
     */
    public void setStarterNode() {
        Node starter = new Node(this.cardStarter, 0, 0, placeHolders, availableNodes, allNodes);
        updateCounter(cardStarter);
    }

    /**
     * Places a card on a specified node and updates the game area.
     *
     * @param card The card to place.
     * @param placeHolder The node where the card will be placed.
     */
    public void playACard(Card card, PlaceHolder placeHolder) {
        modifyGameArea(card, placeHolder);
    }

    /**
     * Updates the game area by setting a card at a node and updating the related counters and points.
     *
     * @param card The card to be placed.
     * @param node The node where the card will be placed.
     */
    public void modifyGameArea(Card card, PlaceHolder node) {
        node.setCardNode(card, placeHolders, availableNodes, allNodes); // Place the card on the node.
        availableNodes.remove(node); // Remove this node from available nodes as it's now occupied.
        updateCounter(card); // Update resource counters based on the new card.
        updatePoints(card); // Update points based on the new card.
        removeResources(node); // Remove resources that are covered by the new card.
    }

    /**
     * Updates the counter for resources and points based on the corners of the placed card.
     * Special handling for resource and gold cards placed on the back side.
     *
     * @param card The card used for updating the counter.
     */
    public void updateCounter(Card card) {
        // Add resources from all corners of the card.
        counter.addResource(card.getSide().getChosenList().get(0).getPropertiesCorner());
        counter.addResource(card.getSide().getChosenList().get(1).getPropertiesCorner());
        counter.addResource(card.getSide().getChosenList().get(2).getPropertiesCorner());
        counter.addResource(card.getSide().getChosenList().get(3).getPropertiesCorner());
        if (card.getSide().getSide().equals(Side.BACK) &&
                (card.getType() == Type.RESOURCES || card.getType() == Type.GOLD)) {
            // If it's a resource or gold card on its back side, add its resources.
            CastCardRes castCardRes = new CastCardRes(card.getCardRes());
            counter.addResource(castCardRes.getPropertiesCorner());
        }
        if (card.getType() == Type.STARTER && card.getSide().getSide() == Side.FRONT) {
            // If it's a starter card on its front side, add required gold resources.
            for (CardRes cardRes : card.getRequireGold()) {
                CastCardRes castCardRes = new CastCardRes(cardRes);
                counter.addResource(castCardRes.getPropertiesCorner());
            }
        }
    }

    /**
     * Removes resources from the counter when a card covers another card's resources.
     *
     * @param node The node whose resources are being covered and should be removed.
     */
    public void removeResources(PlaceHolder node) {
        for (PlaceHolder adjacentNode : allNodes) {
            // Remove resources from all covered nodes.
            if (adjacentNode.getTopR() == node) {
                counter.removeResource(adjacentNode.getCard().getSide().getChosenList().get(1).getPropertiesCorner());
            }
            if (adjacentNode.getTopL() == node) {
                counter.removeResource(adjacentNode.getCard().getSide().getChosenList().get(0).getPropertiesCorner());
            }
            if (adjacentNode.getBotR() == node) {
                counter.removeResource(adjacentNode.getCard().getSide().getChosenList().get(2).getPropertiesCorner());
            }
            if (adjacentNode.getBotL() == node) {
                counter.removeResource(adjacentNode.getCard().getSide().getChosenList().get(3).getPropertiesCorner());
            }
        }
    }

    /**
     * Calculates and adds the correct amount of points.
     *
     * @param card the card to calculate points for
     */
    public void updatePoints(Card card) {
        if(card.getSide().getSide().equals(Side.FRONT)) {
            switch (card.getType()) {
                case RESOURCES:
                    counter.addPoint(card.getPoints());
                    break;
                case GOLD:
                    if (card.getGoldenPoint() == GoldenPoint.NULL) {
                        counter.addPoint(card.getPoints());
                    } else if (card.getGoldenPoint() == GoldenPoint.CORNER) {
                        counter.addPoint(card.getCoveredCornerByCard() * card.getPoints());
                    } else if (card.getGoldenPoint() == GoldenPoint.INKWELL) {
                        counter.addPoint(card.getPoints() * counter.getInkwellCounter());
                    } else if (card.getGoldenPoint() == GoldenPoint.MANUSCRIPT) {
                        counter.addPoint(card.getPoints() * counter.getManuscriptCounter());
                    } else if (card.getGoldenPoint() == GoldenPoint.QUILL) {
                        counter.addPoint(card.getPoints() * counter.getQuillCounter());
                    }
            }
        }
    }

    /**
     * Checks whether a player can place a gold card.
     * Returns true if he can't.
     *
     * @param card the gold card to check
     * @return true if the player cannot place the gold card, false otherwise
     */
    public boolean checkPlayForGold(Card card) {
        List<CardRes> cardResList = Arrays.asList(card.getRequireGold());
        for (CardRes cardRes : cardResList) {
            int requiredAmount = (int) cardResList.stream()
                    .filter(res -> res.equals(cardRes))
                    .count();
            if (!counter.isPresent(cardRes, requiredAmount)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Checks whether a player has completed the public objectives.
     * If so, adds the points to the player scoreboard.
     *
     * @param model the game model containing the public objectives
     */
    public void publicObjective(Model model) {
        int points = 0;
        for (Card card : model.getPublicObjective()) {
            switch (card.getObjectivePoints()) {
                case DIAG:
                    switch (card.getCardRes()) {
                        case FUNGI:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.FUNGI && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;

                        case PLANT:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.PLANT && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;

                        case INSECT:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.INSECT && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;

                        case ANIMAL:
                            for (PlaceHolder node : allNodes) {
                                if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.ANIMAL && findDiagonal(node, alreadyUsed)) {
                                    counter.addPoint(2);
                                    counter.addObjectiveCounter();
                                }
                            }
                            alreadyUsed.clear();
                            break;
                    }
                    break;
                case RES:
                    switch (card.getCardRes()) {
                        case FUNGI:
                            int FungiCounter = counter.getFungiCounter();
                            while (FungiCounter >= 3) {
                                points += 2;
                                FungiCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;

                        case PLANT:
                            int PlantCounter = counter.getPlantCounter();
                            while (PlantCounter >= 3) {
                                points += 2;
                                PlantCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;

                        case INSECT:
                            int InsectCounter = counter.getInsectCounter();
                            while (InsectCounter >= 3) {
                                points += 2;
                                InsectCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;

                        case ANIMAL:
                            int AnimalCounter = counter.getAnimalCounter();
                            while (AnimalCounter >= 3) {
                                points += 2;
                                AnimalCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;

                        case QUILL:
                            int QuillCounter = counter.getQuillCounter();
                            while (QuillCounter >= 3) {
                                points += 2;
                                QuillCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;

                        case INKWELL:
                            int InkwellCounter = counter.getInkwellCounter();
                            while (InkwellCounter >= 3) {
                                points += 2;
                                InkwellCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;

                        case MANUSCRIPT:
                            int ManuscriptCounter = counter.getManuscriptCounter();
                            while (ManuscriptCounter >= 3) {
                                points += 2;
                                ManuscriptCounter = -3;
                                counter.addObjectiveCounter();
                            }
                            counter.addPoint(points);
                            break;
                    }
                    break;
                case REDGREEN:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.FUNGI && findRedGreen(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case GREENPURPLE:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.PLANT && findGreenPurple(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case BLUERED:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.ANIMAL && findBlueRed(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case PURPLEBLUE:
                    for (PlaceHolder node : allNodes) {
                        if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.INSECT && findPurpleBlue(node, alreadyUsed)) {
                            counter.addPoint(3);
                            counter.addObjectiveCounter();
                            alreadyUsed.clear();
                        }
                    }
                    break;
                case MIX:
                    int QuillCounter = counter.getQuillCounter();
                    int InkwellCounter = counter.getInkwellCounter();
                    int ManuscriptCounter = counter.getManuscriptCounter();
                    while (ManuscriptCounter > 0 && InkwellCounter > 0 && QuillCounter > 0) {
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        ManuscriptCounter--;
                        InkwellCounter--;
                        QuillCounter--;
                    }
                    break;
            }
        }
    }

    /**
     * Checks whether a player has completed his private objectives.
     * If so, adds the points to the player scoreboard.
     */
    public void privateObjective() {
        alreadyUsed = new ArrayList<>();
        int points = 0;
        switch (secretObjective.getObjectivePoints()) {
            case DIAG:
                switch (secretObjective.getCardRes()) {
                    case FUNGI:
                        for (PlaceHolder node : allNodes) {
                            if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.FUNGI && findDiagonal(node, alreadyUsed)) {
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;

                    case PLANT:
                        for (PlaceHolder node : allNodes) {
                            if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.PLANT && findDiagonal(node, alreadyUsed)) {
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;

                    case INSECT:
                        for (PlaceHolder node : allNodes) {
                            if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.INSECT && findDiagonal(node, alreadyUsed)) {
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;

                    case ANIMAL:
                        for (PlaceHolder node : allNodes) {
                            if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.ANIMAL && findDiagonal(node, alreadyUsed)) {
                                counter.addPoint(2);
                                counter.addObjectiveCounter();
                            }
                        }
                        alreadyUsed.clear();
                        break;
                }
                break;
            case RES:
                switch (secretObjective.getCardRes()) {
                    case FUNGI:
                        int FungiCounter = counter.getFungiCounter();
                        while (FungiCounter >= 3) {
                            points += 2;
                            FungiCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;

                    case PLANT:
                        int PlantCounter = counter.getPlantCounter();
                        while (PlantCounter >= 3) {
                            points += 2;
                            PlantCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;

                    case INSECT:
                        int InsectCounter = counter.getInsectCounter();
                        while (InsectCounter >= 3) {
                            points += 2;
                            InsectCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;

                    case ANIMAL:
                        int AnimalCounter = counter.getAnimalCounter();
                        while (AnimalCounter >= 3) {
                            points += 2;
                            AnimalCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;

                    case QUILL:
                        int QuillCounter = counter.getQuillCounter();
                        while (QuillCounter >= 3) {
                            points += 2;
                            QuillCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;

                    case INKWELL:
                        int InkwellCounter = counter.getInkwellCounter();
                        while (InkwellCounter >= 3) {
                            points += 2;
                            InkwellCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;

                    case MANUSCRIPT:
                        int ManuscriptCounter = counter.getManuscriptCounter();
                        while (ManuscriptCounter >= 3) {
                            points += 2;
                            ManuscriptCounter = -3;
                            counter.addObjectiveCounter();
                        }
                        counter.addPoint(points);
                        break;
                }
                break;
            case REDGREEN:
                for (PlaceHolder node : allNodes) {
                    if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.FUNGI && findRedGreen(node, alreadyUsed)) {
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                    }
                }
                alreadyUsed.clear();
                break;
            case GREENPURPLE:
                for (PlaceHolder node : allNodes) {
                    if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.PLANT && findGreenPurple(node, alreadyUsed)) {
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        alreadyUsed.clear();
                    }
                }
                break;
            case BLUERED:
                for (PlaceHolder node : allNodes) {
                    if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.ANIMAL && findBlueRed(node, alreadyUsed)) {
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        alreadyUsed.clear();
                    }
                }
                break;
            case PURPLEBLUE:
                for (PlaceHolder node : allNodes) {
                    if (!alreadyUsed.contains(node) && node.getCard().getCardRes() == CardRes.INSECT && findPurpleBlue(node, alreadyUsed)) {
                        counter.addPoint(3);
                        counter.addObjectiveCounter();
                        alreadyUsed.clear();
                    }
                }
                break;
            case MIX:
                int QuillCounter = counter.getQuillCounter();
                int InkwellCounter = counter.getInkwellCounter();
                int ManuscriptCounter = counter.getManuscriptCounter();
                while (ManuscriptCounter > 0 && InkwellCounter > 0 && QuillCounter > 0) {
                    counter.addPoint(3);
                    counter.addObjectiveCounter();
                    ManuscriptCounter--;
                    InkwellCounter--;
                    QuillCounter--;
                }
                break;
        }
    }

    /**
     * Returns true if three cards of the same type are placed on a diagonal.
     * Useful for calculating the diagonal objectives.
     *
     * @param node the node to check
     * @param alreadyUsed the list of already used nodes
     * @return true if the diagonal is found, false otherwise
     */
    public boolean findDiagonal(PlaceHolder node, List<PlaceHolder> alreadyUsed) {
        for (PlaceHolder node1 : allNodes) {
            // Top-right
            if (node1.getX() == node.getX() + 1 && node1.getY() == node.getY() + 1 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX() == node1.getX() + 1 && node2.getY() == node1.getY() + 1 && !alreadyUsed.contains(node2) && sameType(node, node1)) {
                        alreadyUsed.add(node);
                        alreadyUsed.add(node1);
                        alreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            // Top-left
            if (node1.getX() == node.getX() - 1 && node1.getY() == node.getY() + 1 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX() == node1.getX() - 1 && node2.getY() == node1.getY() + 1 && !alreadyUsed.contains(node2) && sameType(node, node1)) {
                        alreadyUsed.add(node);
                        alreadyUsed.add(node1);
                        alreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            // Bottom-right
            if (node1.getX() == node.getX() + 1 && node1.getY() == node.getY() - 1 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX() == node1.getX() + 1 && node2.getY() == node1.getY() - 1 && !alreadyUsed.contains(node2) && sameType(node, node1)) {
                        alreadyUsed.add(node);
                        alreadyUsed.add(node1);
                        alreadyUsed.add(node2);
                        return true;
                    }
                }
            }
            // Bottom-left
            if (node1.getX() == node.getX() - 1 && node1.getY() == node.getY() - 1 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
                for (PlaceHolder node2 : allNodes) {
                    if (node2.getX() == node1.getX() - 1 && node2.getY() == node1.getY() - 1 && !alreadyUsed.contains(node2) && sameType(node, node1)) {
                        alreadyUsed.add(node);
                        alreadyUsed.add(node1);
                        alreadyUsed.add(node2);
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Returns true if two cards are of the same type.
     *
     * @param node1 the first node
     * @param node2 the second node
     * @return true if the cards are of the same type, false otherwise
     */
    public boolean sameType(PlaceHolder node1, PlaceHolder node2) {
        return node1.getCard().getCardRes() == node2.getCard().getCardRes();
    }

   /**
 * Checks for a specific pattern involving two red and green cards.
 * 
 * This method looks for a node at the same x-coordinate and 2 units below the given node,
 * and then checks for another node at a specific position relative to the second node with
 * a PLANT resource. If found, it adds these nodes to the already used list.
 * 
 *
 * @param node the starting node
 * @param alreadyUsed the list of already used nodes
 * @return true if the pattern is found, false otherwise
 */
public boolean findRedGreen(PlaceHolder node, List<PlaceHolder> alreadyUsed) {
    for (PlaceHolder node1 : allNodes) {
        if (node1.getX() == node.getX() && node1.getY() == node.getY() - 2 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
            for (PlaceHolder node2 : allNodes) {
                if (node2.getX() == node1.getX() + 1 && node2.getY() == node1.getY() - 1 && !alreadyUsed.contains(node2) && node2.getCard().getCardRes() == CardRes.PLANT) {
                    alreadyUsed.add(node);
                    alreadyUsed.add(node1);
                    alreadyUsed.add(node2);
                    return true;
                }
            }
        }
    }
    return false;
}

/**
 * Checks for a specific pattern involving two green and purple cards.
 * 
 * This method looks for a node at the same x-coordinate and 2 units below the given node,
 * and then checks for another node at a specific position relative to the second node with
 * an INSECT resource. If found, it adds these nodes to the already used list.
 * 
 *
 * @param node the starting node
 * @param alreadyUsed the list of already used nodes
 * @return true if the pattern is found, false otherwise
 */
public boolean findGreenPurple(PlaceHolder node, List<PlaceHolder> alreadyUsed) {
    for (PlaceHolder node1 : allNodes) {
        if (node1.getX() == node.getX() && node1.getY() == node.getY() - 2 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
            for (PlaceHolder node2 : allNodes) {
                if (node2.getX() == node1.getX() - 1 && node2.getY() == node1.getY() - 1 && !alreadyUsed.contains(node2) && node2.getCard().getCardRes() == CardRes.INSECT) {
                    alreadyUsed.add(node);
                    alreadyUsed.add(node1);
                    alreadyUsed.add(node2);
                    return true;
                }
            }
        }
    }
    return false;
}

/**
 * Checks for a specific pattern involving two blue and red cards.
 * 
 * This method looks for a node at the same x-coordinate and 2 units above the given node,
 * and then checks for another node at a specific position relative to the second node with
 * a FUNGI resource. If found, it adds these nodes to the already used list.
 * 
 *
 * @param node the starting node
 * @param alreadyUsed the list of already used nodes
 * @return true if the pattern is found, false otherwise
 */
public boolean findBlueRed(PlaceHolder node, List<PlaceHolder> alreadyUsed) {
    for (PlaceHolder node1 : allNodes) {
        if (node1.getX() == node.getX() && node1.getY() == node.getY() + 2 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
            for (PlaceHolder node2 : allNodes) {
                if (node2.getX() == node1.getX() + 1 && node2.getY() == node1.getY() + 1 && !alreadyUsed.contains(node2) && node2.getCard().getCardRes() == CardRes.FUNGI) {
                    alreadyUsed.add(node);
                    alreadyUsed.add(node1);
                    alreadyUsed.add(node2);
                    return true;
                }
            }
        }
    }
    return false;
}

/**
 * Checks for a specific pattern involving 2 purple and blue cards.
 * 
 * This method looks for a node at the same x-coordinate and 2 units above the given node,
 * and then checks for another node at a specific position relative to the second node with
 * an ANIMAL resource. If found, it adds these nodes to the already used list.
 * 
 *
 * @param node the starting node
 * @param alreadyUsed the list of already used nodes
 * @return true if the pattern is found, false otherwise
 */
public boolean findPurpleBlue(PlaceHolder node, List<PlaceHolder> alreadyUsed) {
    for (PlaceHolder node1 : allNodes) {
        if (node1.getX() == node.getX() && node1.getY() == node.getY() + 2 && !alreadyUsed.contains(node1) && sameType(node, node1)) {
            for (PlaceHolder node2 : allNodes) {
                if (node2.getX() == node1.getX() - 1 && node2.getY() == node1.getY() + 1 && !alreadyUsed.contains(node2) && node2.getCard().getCardRes() == CardRes.ANIMAL) {
                    alreadyUsed.add(node);
                    alreadyUsed.add(node1);
                    alreadyUsed.add(node2);
                    return true;
                }
            }
        }
    }
    return false;
}

// Getter e setter zone

  /**
 * Returns the list of cards in the player's hand.
 * 
 * @return the list of cards in the hand
 */
public List<Card> getHand() {
    return hand;
}

/**
 * Returns the counter associated with the player.
 * 
 * @return the counter
 */
public Counter getCounter() {
    return counter;
}

/**
 * Finds and returns a placeholder node by its x and y coordinates.
 * 
 * @param x the x-coordinate of the node
 * @param y the y-coordinate of the node
 * @return the placeholder node at the specified coordinates, or null if not found
 */
public PlaceHolder getNodeByXY(int x, int y) {
    for (PlaceHolder p : allNodes) {
        if (p.getX() == x && p.getY() == y) {
            return p;
        }
    }
    return null;
}

/**
 * Sets the player's secret objective card.
 * 
 * @param secretObjective the secret objective card
 */
public void setSecretObjective(Card secretObjective) {
    this.secretObjective = secretObjective;
}

/**
 * Returns the player's secret objective card.
 * 
 * @return the secret objective card
 */
public Card getSecretObjective() {
    return secretObjective;
}

/**
 * Returns the list of available nodes for placing cards.
 * 
 * @return the list of available nodes
 */
public List<PlaceHolder> getAvailableNodes() {
    return availableNodes;
}

/**
 * Returns the list of placeholders.
 * 
 * @return the list of placeholders
 */
public List<PlaceHolder> getPlaceHolders() {
    return placeHolders;
}

/**
 * Returns the list of nodes that have already been used.
 * 
 * @return the list of already used nodes
 */
public List<PlaceHolder> getAlreadyUsed() {
    return alreadyUsed;
}

/**
 * Returns the list of all nodes.
 * 
 * @return the list of all nodes
 */
public List<PlaceHolder> getAllNodes() {
    return allNodes;
}

/**
 * Returns the list of temporary secret objective cards.
 * 
 * @return the list of temporary secret objective cards
 */
public List<Card> getTempSecretObjective() {
    return tempSecretObjective;
}

/**
 * Returns the player's starter card.
 * 
 * @return the starter card
 */
public Card getCardStarter() {
    return cardStarter;
}

/**
 * Sets the player's starter card.
 * 
 * @param cardStarter the starter card
 */
public void setCardStarter(Card cardStarter) {
    this.cardStarter = cardStarter;
}
}
