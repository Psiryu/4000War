/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Prem
 */
public class Global {
    public static Boolean opponent = false; //if true, against player. false means AI
    public static Boolean chosenTeam = false; // toggle for the character player 1 choses, false means player 1 is team 1
    public static int weather; // storage for the weather value
    public static int season; // storage for the season value
    public static String teamOne; // team name 1
    public static String teamTwo; // team name 2
    public static int intScenario; // scenario number
    public static MapEvent mapEvent;
    public static int curPlayer; //global variable for keeping track of whos turn it is
    public static String gameSummary;
    public static String gameSummary2;
    public static int intGameOver;
    
    
    public static void ResetGlobals() {
        opponent = false; //if true, against player. false means AI
        chosenTeam = false; // toggle for the character player 1 choses, false means player 1 is team 1
        weather =0; // storage for the weather value
        season =0; // storage for the season value
        teamOne =null; // team name 1
        teamTwo = null; // team name 2
        intScenario = 0; // scenario number
        mapEvent = null;
        curPlayer = 0; //global variable for keeping track of whos turn it is
        gameSummary = "";
    }
}
