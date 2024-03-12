package edu.up.cs301.craps;

/**
 * @author Wes H.       Last Revision: 3/11/2024
 * @author Troy C.      Last Revision: TBD
 * @author Rowena A.    Last Revision: TBD
 * @author Sydney D.    Last Revision: TBD
 * @version 3/11/2024
 *
 * Important notes for this version:
 * Completed:
 * Instance variables, public static final variables, constructors (including deep copy), getters, setters, toString, checkPair, checkSum
 * Incomplete:
 * Bet payout, checkThisBetWon method (includes most helper methods)
 * Other:
 * Bets need to have a known payout for each type and the game state needs to pay players accordingly
 * It would be helpful if every bet also had a known payout with their chances, odds, winning, etc. info displayed to the player
 */

public class Bet {
    //instance variables

    private int betAmount; //the amount of money this bet has put on it
    private int betID; //value that represents the type of bet in a public static final (PSF) string array of bet names
    private String betName; //name of the bet

    //public static final variables
    //list of all names of all bets
    public static final String[] ALL_BET_NAMES = new String[]{
            "NO BET", "PASS", "DON'T PASS", "COME", "FIELD", "4",
            "5", "SIX", "8", "NINE", "10", "C", "E",
            "7 (4 to 1)", "Pair of 2s", "Pair of 3s",
            "Pair of 4s", "Pair of 5s", "2 & 1", "Pair of 1s",
            "Pair of 6s", "5 & 6", "CRAPS"
    };

    //default cnstr
    public Bet() {
        this.betAmount = 0;
        this.betID = 0;
        this.betName = ALL_BET_NAMES[0];
    }

    //betID cnstr
    public Bet(int givenBetID) {
        this.betAmount = 0;
        this.betID = givenBetID;
        this.betName = ALL_BET_NAMES[givenBetID];
    }

    //copy cnstr
    public Bet(Bet copyBet) {
        this.betAmount = copyBet.betAmount;
        this.betID = copyBet.betID;
        this.betName = ALL_BET_NAMES[copyBet.betID];
    }

    //simple getter methods
    public int getBetAmount() {
        return betAmount;
    }

    public int getBetID() {
        return betID;
    }

    public String getBetName() {
        return betName;
    }

    //set bet amount
    public void setBetAmount(int newBetAmount) {
        this.betAmount = newBetAmount;
    }

    //set bet ID & bet Name
    public void setBetID(int newBetID) {
        this.betID = newBetID;
        this.betName = ALL_BET_NAMES[newBetID];
    }

    /**
     * big bad method that checks if bet is won overall
     * NOT YET HAS ALL BET TYPES CODED NOR ARE THE HELPER METHODS DONE
     * @param die1      dice 1
     * @param die2      dice 2
     * @param diceTotal the sum of the dice
     * @return boolean true if bet is won otherwise false (check comment on first line of method for more info)
     */
    public boolean checkThisBetWon(int die1, int die2, int diceTotal) {
        /*
        conditional that checks what the name of the bet is
        and calls the appropriate helper method that determines
        if the bet is won based on the rules
         */
        switch (this.betName) {
            case "4":
                return this.checkDiceSum(diceTotal, 4);
            case "5":
                return this.checkDiceSum(diceTotal, 5);
            case "SIX":
                return this.checkDiceSum(diceTotal, 6);
            case "8":
                return this.checkDiceSum(diceTotal, 8);
            case "NINE":
                return this.checkDiceSum(diceTotal, 9);
            case "10":
                return this.checkDiceSum(diceTotal, 10);
            case "E":
                return this.checkDiceSum(diceTotal, 11);
            case "Pair of 1s":
                return this.checkPairOfN(die1, die2, 1);
            case "Pair of 2s":
                return this.checkPairOfN(die1, die2, 2);
            case "Pair of 3s":
                return this.checkPairOfN(die1, die2, 3);
            case "Pair of 4s":
                return this.checkPairOfN(die1, die2, 4);
            case "Pair of 5s":
                return this.checkPairOfN(die1, die2, 5);
            case "Pair of 6s":
                return this.checkPairOfN(die1, die2, 6);
        }
        return false; //returns false if none of the previous cases are met
    }


    /**
     * a bunch of said helper methods for the checkThisBetWon method
     */

    /**
     * checks if the dice are pairs of given int n
     *
     * @param die1 int dice 1
     * @param die2 int dice 2
     * @param n    int desired pair of dice
     * @return boolean
     */
    public boolean checkPairOfN(int die1, int die2, int n) {
        if (die1 == n && die2 == n) {
            return true;
        }
        return false;
    }

    //checks if the sum of the die are equal to the winning value
    public boolean checkDiceSum(int dieSum, int winningVal) {
        return (dieSum == winningVal);
    }

    //toString method
    @Override
    public String toString() {
        return this.betName + " Bet with $" + this.betAmount;
    }

}