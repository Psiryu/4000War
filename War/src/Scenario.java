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
                //  storage of the city names
                String[] names = {"Utica","Fidenza","Faenza","Volterra"
                        ,"Saturnia","Senigalia","Clusium","Palestrina","Naples"
                        ,"Nola","Brindis","Dyrrhachulum","Kavala","Roma"};
                // storage of the distance to capitol of Blue and Red respectively
                int[][] capitalDistances = {{1,5},{3,6},{2,5},{2,5},{2,5},{1,4}
                        ,{1,4},{1,4},{1,2},{2,1},{3,0},{4,1},{5,2},{0,3}};
                // storage of road capacity {0..2}, location a, and location b respectively
                int[][] locations = {{2,0,13},{2,1,3},{2,2,5},{1,3,5},{2,3,4}
                        ,{2,4,6},{2,7,13},{0,5,13},{1,7,13},{2,8,13},{2,8,9}
                        ,{2,9,10},{2,10,11},{2,11,12}};
                listOfRoads = new Road[14];
                listOfNodes = new Node[14];
                // for each of the cities
                for (int i = 0;i < 14; i++){
                    listOfNodes[i].name = names[i];
                    listOfNodes[i].distanceFromCapitalBlue = capitalDistances[i][0];
                    listOfNodes[i].distanceFromCapitalRed = capitalDistances[i][1];
                    // check for the capitals
            switch (names[i]) {
                case "Roma":
                    listOfNodes[i].isCapitalBlue = true;
                    break;
                case "Brindis":
                    listOfNodes[i].isCapitalRed = true;
                    break;
            }
                }
                // for each of the roads
                for (int j = 0;j < 14;j++)
                {
                    listOfRoads[j].capacity = locations[j][0];
                    listOfRoads[j].locationA = listOfNodes[locations[j][1]];
                    listOfRoads[j].locationB = listOfNodes[locations[j][2]];
                    if (j == 0 || j == 12)
                        listOfRoads[j].isNaval = true;
                }
        }
    }
    }

