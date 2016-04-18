package pizzaorder;

import java.util.ArrayList;

public class Pizza
{
    public static enum Topping
    {
        Pepperoni, Sausage, Olives, Bacon, Peppers;

        public String filename()
        {
            return "pics/" + this.name() + ".png";
        }
    };

    public static enum Size
    {
        Small, Medium, Large;

        public double price()
        {
            double price;

            if (this == Small)
                price = 10.00;
            else if (this == Medium)
                price = 13.00;
            else
                price = 15.00;

            return price;
        }

        @Override
        public String toString()
        {
            return this.name() + " Pizza";
        }
    };


    /**********************************************************************************************
     *============================================================================================*
     *|  Class variables                                                                         |*
     *============================================================================================*
     **********************************************************************************************/

    //Class constants
    public static final double TOPPING_PRICE = 1.50;
    public static final Size   DEFAULT_SIZE  = Size.Medium;

    //Instance data
    private Size size;
    private ArrayList<Topping> toppings;
    private double price;

    public Pizza(Size size)
    {
        this.size = size;
        toppings  = new ArrayList<>();
        price     = size.price();
    }

    public Pizza()
    {
        this(DEFAULT_SIZE);
    }

    /**
     * Adds the given topping. If adding pepperoni, it will always be placed at the bottom, and
     * sausage will always be moved on top.
     *
     * @param topping Topping to be added
     * @return True if topping was added successfully
     */
    public boolean addTopping(Topping topping)
    {
        boolean added = true;

        if (!toppings.contains(topping))
        {
            price += TOPPING_PRICE;
            if (topping == Topping.Pepperoni)
                toppings.add(0, topping);
            else
                toppings.add(topping);
            if (toppings.contains(Topping.Sausage))
            {
                toppings.remove(Topping.Sausage);
                toppings.add(Topping.Sausage);
            }
        }
        else
            added = false;

        return added;
    }

    public boolean removeTopping(Topping topping)
    {
        if (toppings.contains(topping))
            price -= TOPPING_PRICE;

        return toppings.remove(topping);
    }

    public ArrayList<Topping> getToppings()
    {
        return toppings;
    }

    public void setSize(Size size)
    {
        price     -= this.size.price();
        this.size  = size;
        price     += size.price();
    }

    public Size size()
    {
        return size;
    }

    public double price()
    {
        return price;
    }
}
