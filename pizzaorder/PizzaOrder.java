package pizzaorder;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;

public class PizzaOrder {

    /**********************************************************************************************
     * Method Name    : OlympicsScores - Main
     * Author         : Terry Weiss
     * Date           : April 8, 2016
     * Course/Section : CSC264 - 001
     * Program Description: This method creates and launches the GUI frame.
     **********************************************************************************************/
    public static void main(String[] args) throws IOException
    {
        //Create frame
        JFrame frame = new JFrame("Terry's Pizzas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create score panel
        OrderPanel panel = new OrderPanel();
        frame.getContentPane().add(panel);

        //Display frame
        Dimension minSize = new Dimension(650, 375);
        frame.setPreferredSize(minSize);
        frame.setMinimumSize(minSize);
        frame.pack();
        frame.setVisible(true);

    }//end method Main
}
