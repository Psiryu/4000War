/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.util.ArrayList;
/**
 *
 * @author Fearless Jay
 */
public class Player extends Game
{
        protected Node capital;
        protected ArrayList<CombatUnit> combatUnits = new ArrayList<CombatUnit>();
        protected int politicalPower;
        protected int politicalPowerPreviousState;
        protected int politicalPowerDecrease;
        protected boolean isComp;
        protected int playerID;
        protected String playerName;
        protected int aggregateDistanceFromCapital;
        protected int totalInitialArmy;
        protected int totalCurrentArmy;
        
        /*Constructor*/
        public Player(boolean _isComp,Node _capital, int id, String _playerName)
        {
            playerID = id;
            politicalPower = 100;
            isComp = _isComp;
            capital = _capital;
            playerName = _playerName;
            /*NOW We place the COMBATS UNIT ACCORDING TO SCENARIO SPECIFICATIONS*/
            
        }  
        
        /*GETS AND SETS*/
        public int getPolitcalPower()
        {
            return politicalPower;
        }
        public void setPoliticalPower(int x)
        {
            politicalPowerPreviousState = politicalPower;
            politicalPower = x;
            if (politicalPower < politicalPowerPreviousState)
                politicalPowerDecrease = politicalPower - politicalPowerPreviousState;
        }
        public int getPoliticalPowerDecrease()
        {
            return politicalPowerDecrease;
        }
        
        public boolean getIsComp()
        {
            return isComp;
        }
        public void setIsComp(boolean x)
        {
            isComp = x;
        }
        
        public int AdjustPoliticalPower()
        {            
            int metricOne = aggregateDistanceFromCapital;
            
            if (metricOne > 20)
            {
                metricOne = 20;
            }
            
            int metricTwo = turnCount;
            if (metricTwo > 30)
            {
                metricTwo = 30;
            }
            
            politicalPower = 100 - metricOne - (int)(50-((double)totalCurrentArmy/(double)totalInitialArmy)*50) - metricTwo;
            
            return politicalPower;        
        }
        
        public void setAggDistance()
        {
            int calculatedDistance = 0;
            
            for (int i = 0; i < combatUnits.size(); i++)
            {
                if (playerName.equals("red"))
                {
                    calculatedDistance += combatUnits.get(i).location.distanceFromCapitalRed;
                }
                else
                {
                    calculatedDistance += combatUnits.get(i).location.distanceFromCapitalBlue;
                }
            }                
            
            aggregateDistanceFromCapital = calculatedDistance;
        }
        
        public void addUnit (CombatUnit addition)
        {
            combatUnits.add(addition);
        }
    
}
