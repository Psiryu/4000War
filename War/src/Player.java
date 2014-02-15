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
public class Player 
{
        
    
    
        Node capital;
        ArrayList CombatUnit[];
        int politicalPower;
        boolean isComp;
        int  playerID;
        
        
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
        
        public CombatUnit[] getlistOfCombatUnits()
        {
            return listOfCombatUnits;
        }
        public void listOfCombatUnits(CombatUnit[] x)
        {
            listOfCombatUnits = x;
        }
        
        
    
    
}
