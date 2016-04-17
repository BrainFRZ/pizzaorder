package pizzaorder;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class PizzaLabel extends JLabel
{
    private final Pizza pizza;
    private static BufferedImage pizzaImage;
    private static BufferedImage[] toppingImages;

    public PizzaLabel(Pizza pizza) throws IOException
    {
        this.pizza = pizza;
        if (pizzaImage == null)
            loadImages();
    }

    private static void loadImages() throws IOException
    {
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
            page2D.drawImage(toppingImages[topping.ordinal()], null, 0, 0);
    }
}
