/**************************************************************************************************
 * Program Name   :
 * Author         : Terry Weiss
 * Date           : Apr 15, 2016
 * Course/Section :
 * Program Description:
 **************************************************************************************************/

package pizzaorder;

import java.util.ArrayList;

public class Pizza
{
    public static enum Topping { Pepperoni, Sausage, Olives, Bacon, Peppers };

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

    public boolean addTopping(Topping topping)
    {
        boolean added = true;

        if (!toppings.contains(topping))
        {
            price += TOPPING_PRICE;
            toppings.add(topping);
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
