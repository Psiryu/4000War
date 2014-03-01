
import java.util.Random;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Fearless Jay
 */
public class Game 
{
    
    public static int turnCount = 0 ; 
    
    int getWeather()
    {
        return Global.weather;
    }
    
    void updateWeather()
    {
        Random random = new Random();
        Global.weather = (int) (5 * random.nextDouble());
    }
    
}
