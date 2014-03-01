/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author BotBen
 */
public class Scenario 
{
    Node[] listOfNodes;
    Road[] listOfRoads;
    int scenarioID = 0;
    
    void Initialize(){
        switch (scenarioID) {
            case 0:
                String[] names = {"Utica","Fidenza","Faenza","Volterra"
                        ,"Saturnia","Senigalia","Clusium","Palestrina","Naples"
                        ,"Nola","Brindis","Dyrrhachulum","Kavala","Roma"};
                int[][] capitalDistances = {{1,5},{3,6},{2,5},{2,5},{2,5},{1,4}
                        ,{1,4},{1,4},{1,2},{2,1},{3,0},{4,1},{5,2},{0,3}};
                listOfNodes = new Node[14];
                for (int i = 0;i < 12; i++){
                    listOfNodes[i].name = names[i];
                    listOfNodes[i].distanceFromCapitalBlue = capitalDistances[i][0];
                    listOfNodes[i].distanceFromCapitalRed = capitalDistances[i][1];
                    if ("Roma".equals(names[i]))
                        listOfNodes[i].isCapitalBlue = true;
                    else if ("Brindis".equals(names[i]))
                        listOfNodes[i].isCapitalRed = true;
                }
                
        }
    }
    
}
