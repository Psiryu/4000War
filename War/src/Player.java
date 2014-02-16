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
        protected ArrayList combatUnitList;
        protected int politicalPower;
        protected boolean isComp;
        protected int playerID;
        
        protected int aggregateDistanceFromCapital;
        protected int totalInitialArmy;
        protected int totalCurrentArmy;
        
        
        
        /*Constructor*/
        public Player(boolean _isComp,Node _capital, int id)
        {
            playerID = id;
            politicalPower = 100;
            isComp = _isComp;
            capital = _capital;
            /*NOW We place the COMBATS UNIT ACCORDING TO SCENARIO SPECIFICATIONS*/
            
        }
        
        
        
        /*GETS AND SETS*/
        public int getPolitcalPower()
        {
            return politicalPower;
        }
        public void setPoliticalPower(int x)
        {
            politicalPower = x;
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
            
            politicalPower = 100 - metricOne - (50-(totalCurrentArmy/totalInitialArmy)*50) - metricTwo;
            
            return politicalPower;
            
            
            
        }
        
        
    
    
}
