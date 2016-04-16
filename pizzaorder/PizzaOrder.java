/**************************************************************************************************
 * Program Name   :
 * Author         : Terry Weiss
 * Date           : Apr 15, 2016
 * Course/Section :
 * Program Description:
 **************************************************************************************************/

package pizzaorder;

import java.awt.Dimension;
import javax.swing.JFrame;

public class PizzaOrder {

    /**********************************************************************************************
     * Method Name    : OlympicsScores - Main
     * Author         : Terry Weiss
     * Date           : April 8, 2016
     * Course/Section : CSC264 - 001
     * Program Description: This method creates and launches the GUI frame.
     **********************************************************************************************/
    public static void main(String[] args)
    {
        //Create frame
        JFrame frame = new JFrame("Terry's Pizzas");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Create score panel
        OrderPanel panel = new OrderPanel();
        frame.getContentPane().add(panel);

        //Display frame
        frame.setPreferredSize(new Dimension(450, 240));
        frame.setResizable(false);
        frame.pack();
        frame.setVisible(true);

    }//end method Main
}
