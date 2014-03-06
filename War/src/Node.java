/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class Node
{
    int terrainHeight; /*Out Of XX*/
    boolean isCapitalRed;
    boolean isCapitalBlue;
    int distanceFromCapitalRed;
    int distanceFromCapitalBlue;
    /*int coordinateX;
    Road[] listOfRoads;
    int coordinateY;*/
    String name;
    int id;
    
    public Node (int _id, boolean _isCapitalRed, boolean _isCapitalBlue, int _distanceFromCapitalRed, int _distanceFromCapitalBlue, String _name)
    {
        id = _id;
        isCapitalRed = _isCapitalRed;
        isCapitalBlue = _isCapitalBlue;
        distanceFromCapitalRed = _distanceFromCapitalRed;
        distanceFromCapitalBlue = _distanceFromCapitalBlue;
        name = _name;
    }
}
