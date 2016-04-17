package pizzaorder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class PizzaPanel extends JPanel
{
    private Pizza pizza;
    private BufferedImage pizzaImage;
    private BufferedImage[] toppingImages;

    public PizzaPanel(Pizza pizza) throws IOException
    {
        this.pizza = pizza;
        Pizza.Topping[] toppings = Pizza.Topping.values();

        pizzaImage = ImageIO.read(new File("pics/Pizza.png"));
        toppingImages = new BufferedImage[toppings.length];
        for (int i = 0; i < toppings.length; i++)
            toppingImages[i] = ImageIO.read(new File(toppings[i].filename()));

    }

    @Override
    public void paintComponent(Graphics page)
    {
        Graphics2D page2D = (Graphics2D)page;

        super.paintComponent(page);
        page2D.drawImage(pizzaImage, null, 0, 0);

        for (Pizza.Topping topping : pizza.getToppings())
        {
            try
            {
                BufferedImage image = ImageIO.read(new File(topping.filename()));
                page2D.drawImage(image, null, 0, 0);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }
}
