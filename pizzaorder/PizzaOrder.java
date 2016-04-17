package pizzaorder;

import java.awt.Dimension;
import java.io.IOException;
import javax.swing.JFrame;

public class PizzaOrder {

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
