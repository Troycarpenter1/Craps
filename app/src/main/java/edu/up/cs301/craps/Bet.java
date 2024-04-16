package edu.up.cs301.craps;

/**
 * @author Wes H.       Last Revision: 3/18/2024
 * @author Troy C.      Last Revision: TBD
 * @author Rowena A.    Last Revision: TBD
 * @author Sydney D.    Last Revision: TBD
 * @version 3/18/2024
 * <p>
 * Important notes for this version:
 * Completed:
 * Instance variables, public static final variables, constructors (including deep copy),
 * getters, setters, toString, checkPair, checkSum, checkFieldBet, checkCrapsBet, checkComeBetWon,
 * checkComeBetVal
 * Incomplete:
 * checkThisBetWon method (includes most helper methods)
 */

public class Bet {
    //instance variables

    private int ID; //value that represents the type of bet in an array of bet names & payouts
    private String name; //name of the bet
    private double amount; //the amount of money this bet has put on it
    private double payout;

    //public static final variables
    //list of all names of all bets
    public static final String[] ALL_BET_NAMES = new String[]{
            "NO BET", "PASS", "DON'T PASS", "COME", "FIELD", "4",
            "5", "6", "8", "9", "10", "C", "E",
            "7 (4 to 1)", "Pair of 2s", "Pair of 3s",
            "Pair of 4s", "Pair of 5s", "2 & 1", "Pair of 1s",
            "Pair of 6s", "5 & 6", "CRAPS"
    };
    //list of all payouts of all bets
    //todo: add all the payouts

    public static final double[] ALL_BET_PAYOUTS = new double[]{
            /* No Bet: */        0.0,
            /* Pass Line Bet: */ 1.2,
            /* Dont Pass Bet: */ 1.2,
            /* Come Bet: */      1.2,
            /* Field Bet: */     1.2,
            /* 4: */             9.0/5.0,
            /* 5: */             7.0/5.0,
            /* 6: */             7.0/6.0,
            /* 8: */             7.0/6.0,
            /* 9: */             7.0/5.0,
            /* 10: */            9.0/5.0,
            /* C: */             15.0,
            /* E: */             15.0,
            /* 7 (4 to 1): */    4.0,
            /* Pair of 2s: */    30.0,
            /* Pair of 3s: */    30.0,
            /* Pair of 4s: */    30.0,
            /* Pair of 5s: */    30.0,
            /* 2 & 1: */         15.0,
            /* Pair of 1s: */    30.0,
            /* Pair of 6s: */    30.0,
            /* 5 & 6: */         15.0,
            /* Craps: */         15.0
    };


    //default cnstr
    public Bet() {
        this.ID = 0;
        this.name = ALL_BET_NAMES[0];
        this.payout = ALL_BET_PAYOUTS[0];
        this.amount = 0;
    }

    //betID cnstr
    public Bet(double newAmount, int newID) {
        this.ID = newID;
        this.name = ALL_BET_NAMES[newID];
        this.payout = ALL_BET_PAYOUTS[newID];
        this.amount = newAmount;
    }

    //copy cnstr
    public Bet(Bet copyBet) {
        this.ID = copyBet.ID;
        this.name = ALL_BET_NAMES[copyBet.ID];
        this.payout = ALL_BET_PAYOUTS[copyBet.ID];
        this.amount = copyBet.amount;
    }

    //simple getter methods
    public double getAmount() {
        return this.amount;
    }

    public double getPayout() {
        return this.payout;
    }

    public int getID() {
        return this.ID;
    }

    public String getName() {
        return this.name;
    }

    //set bet amount
    public void setBetAmount(double newAmount) {
        this.amount = newAmount;
    }

    /**
     * big bad method that pays the player the moneyzz (if the bet is won overall)
     * Note: See "to-do" in the checkThisBetWon() method
     *
     * @param die1      dice 1
     * @param die2      dice 2
     * @param diceTotal the sum of the dice
     * @param firstRoll the first roll the shooter made
     * @return the value that will be paid out to the player if the player wins the bet (otherwise it is 0)
     */
    public double payoutBet(int die1, int die2, int diceTotal, int firstRoll) {
        if (checkThisBetWon(die1, die2, diceTotal, firstRoll)) { //checks if the bet is won
            if (this.name.equals("FIELD") && (diceTotal == 2 || diceTotal == 12)) { // checks double pay out on field bets
                return this.amount * (this.payout * 2);
            }
            return this.amount * this.payout;

        } else { //takes your money if you lose haha
            return 0.0;
        }
    }

    /**
     * big bad method that checks if bet is won overall
     * TODO: NOT YET HAS ALL BET TYPES CODED NOR ARE THE HELPER METHODS DONE
     * <p>
     *
     * @param die1      dice 1
     * @param die2      dice 2
     * @param diceTotal the sum of the dice
     * @param firstRoll the first roll the shooter made
     * @return boolean true if bet is won otherwise false (check comment on first line of method for more info)
     */
    public boolean checkThisBetWon(int die1, int die2, int diceTotal, int firstRoll) {
        /*
        conditional that checks what the name of the bet is
        and calls the appropriate helper method that determines
        if the bet is won based on the rules
         */
        switch (this.name) {
            case "NO BET":
                return false;
            case "PASS":
                if (firstRoll == 7 || firstRoll == 11 || firstRoll == diceTotal) {
                    return true;
                }
                return false;
            case "DON'T PASS":
                if (firstRoll == 2 || firstRoll == 3 || diceTotal == 7) {
                    return true;
                }
                return false;
            case "COME":
                //the values that CAN win a Come bet
                int[] comeWinningVals = new int[]{
                        4, 5, 6, 8, 9, 10
                };
                //checks if the dice total CAN win
                for (int num : comeWinningVals) {
                    if (diceTotal == num) {
                        //checks if the die sum of this roll matches the first roll
                        return (diceTotal == firstRoll);
                    }
                }
                return false;
            case "FIELD":
                //the values that will win a field bet
                int[] fieldWinningVals = new int[]{
                        2, 3, 4, 9, 10, 11, 12
                };
                //checks if the dice total will win
                for (int num : fieldWinningVals) {
                    if (diceTotal == num) {
                        return true;
                    }
                }
                return false;
            case "4":
                return this.checkDiceSum(diceTotal, 4);
            case "5":
                return this.checkDiceSum(diceTotal, 5);
            case "6":
                return this.checkDiceSum(diceTotal, 6);
            case "8":
                return this.checkDiceSum(diceTotal, 8);
            case "9":
                return this.checkDiceSum(diceTotal, 9);
            case "10":
                return this.checkDiceSum(diceTotal, 10);
            case "7 (4 to 1)":
                return this.checkDiceSum(diceTotal, 7);
            case "2 & 1":
                return this.checkDiceSum(diceTotal, 3);
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
            case "E":
            case "5 & 6":
                return this.checkDiceSum(diceTotal, 11);
            case "C":
            case "CRAPS":
                //the values that will win a Craps bet
                int[] crapsWinningVals = new int[]{
                        2, 3, 12
                };
                //checks if the dice total will win
                for (int num : crapsWinningVals) {
                    if (diceTotal == num) {
                        return true;
                    }
                }
                return false;
        }
        return false; //returns false if none of the previous cases are met
    }


    /*
     * a bunch of said helper methods for the checkThisBetWon method
     */

    /**
     * checkPairOfN
     *
     * checks if the dice are pairs of given int n
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

    /**
     * checkDiceSum
     *
     * checks if the sum of the die are equal to the winning value
     * @param dieSum
     * @param winningVal
     * @return
     */
    public boolean checkDiceSum(int dieSum, int winningVal) {
        return (dieSum == winningVal);
    }

    /**
     * toString method
     * @return
     */
    @Override
    public String toString() {
        return this.name + " Bet ID of: " + this.ID + " with $" + this.amount + " and Pays out: " + this.payout;
    }

}