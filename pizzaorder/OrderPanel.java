package pizzaorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;

public class OrderPanel extends JPanel
{
    /**********************************************************************************************
     *============================================================================================*
     *|  Class variables                                                                         |*
     *============================================================================================*
     **********************************************************************************************/

    //Class constants
    private Pizza pizza;
    private final static Pizza.Topping[] TOPPINGS = Pizza.Topping.values();

    //Instance data
    private static JLabel title;
    private JTable invoice;
    private DefaultTableModel model;
    private JPanel options;
    private JPanel pizzaPanel;
    private PizzaLabel pizzaLabel;
    private JRadioButton[] sizeBoxes;
    private JCheckBox[] toppingBoxes;

    /**********************************************************************************************
     *============================================================================================*
     *|  Class methods                                                                           |*
     *============================================================================================*
     **********************************************************************************************/

    public OrderPanel() throws IOException
    {
        pizza = new Pizza();

        setLayout(new BorderLayout());

        title = new JLabel("Pizza Ordering System", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        //Set up invoice
        String[] columns = { "Item", "Price" };
        Object[][] data  = new Object[2][2];    //[Pizza size, toppings, and total][Item and price]
        data[0][0] = pizza.size().toString();
        data[0][1] = String.format("$%4.2f", pizza.size().price());
        data[data.length - 1][0] = "Total:";
        data[data.length - 1][1] = String.format("$%5.2f", pizza.price());
        model   = new DefaultTableModel(data, columns);
        invoice = new JTable();
        invoice.setModel(model);
        invoice.setFont(new Font("Times New Roman", Font.PLAIN, 16));
        invoice.getColumnModel().getColumn(0).setPreferredWidth(100);
        invoice.getColumnModel().getColumn(1).setPreferredWidth(50);
        invoice.setShowGrid(false);
        invoice.setBackground(Color.LIGHT_GRAY);

        //Set up options
        options = new JPanel();
        options.setLayout(new BoxLayout(options, BoxLayout.Y_AXIS));

        JLabel sizeLabel = new JLabel("Select a Size:", SwingConstants.CENTER);
        sizeLabel.setAlignmentX(CENTER_ALIGNMENT);
        final Pizza.Size[] SIZES = Pizza.Size.values();
        sizeBoxes = new JRadioButton[SIZES.length];

        ButtonGroup sizeGroup = new ButtonGroup();
        sizeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        options.add(sizeLabel);
        for (int i = 0; i < sizeBoxes.length; i++)
        {
            sizeBoxes[i] = new JRadioButton(SIZES[i].name());
            options.add(sizeBoxes[i]);
            sizeGroup.add(sizeBoxes[i]);
            sizeBoxes[i].addActionListener(new SizeListener());
        }
        sizeBoxes[1].setSelected(true);

        toppingBoxes         = new JCheckBox[TOPPINGS.length];
        JLabel toppingsLabel = new JLabel("Select Toppings:", SwingConstants.CENTER);
        toppingsLabel.setAlignmentX(CENTER_ALIGNMENT);

        toppingsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        options.add(toppingsLabel);
        for (int i = 0; i < toppingBoxes.length; i++)
        {
            toppingBoxes[i] = new JCheckBox(TOPPINGS[i].name());
            options.add(toppingBoxes[i]);
            toppingBoxes[i].addActionListener(new ToppingsListener());
        }

        options.setPreferredSize(new Dimension(175, 350));

        //Set up pizza panel
        pizzaPanel = new JPanel();
        pizzaLabel = new PizzaLabel(pizza);
        pizzaLabel.setAlignmentX(CENTER_ALIGNMENT);
        pizzaLabel.setAlignmentY(CENTER_ALIGNMENT);
        pizzaLabel.setPreferredSize(new Dimension(300, 300));
        pizzaPanel.add(pizzaLabel);


        //Add components to main panel
        add(title, BorderLayout.NORTH);
        add(options, BorderLayout.WEST);
        add(invoice, BorderLayout.EAST);
        add(pizzaPanel, BorderLayout.CENTER);
    }


    private class SizeListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();

            if (source == sizeBoxes[0])
                setSize(Pizza.Size.Small);
            else if (source == sizeBoxes[1])
                setSize(Pizza.Size.Medium);
            else
                setSize(Pizza.Size.Large);
        }

        private void setSize(Pizza.Size size)
        {
            pizza.setSize(size);
            invoice.setValueAt(pizza.size(), 0, 0);
            invoice.setValueAt(String.format("$%4.2f", pizza.size().price()), 0, 1);
            invoice.setValueAt(String.format("$%5.2f", pizza.price()), model.getRowCount() - 1, 1);
            model.fireTableDataChanged();
        }
    }

    private class ToppingsListener implements ActionListener
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            Object source = e.getSource();

            if (source == toppingBoxes[0])
                toggleTopping(1);
            else if (source == toppingBoxes[1])
                toggleTopping(2);
            else if (source == toppingBoxes[2])
                toggleTopping(3);
            else if (source == toppingBoxes[3])
                toggleTopping(4);
            else if (source == toppingBoxes[4])
                toggleTopping(5);
        }

        private void toggleTopping(int tableRow)
        {
            if (toppingBoxes[tableRow - 1].isSelected())
                addTopping(TOPPINGS[tableRow - 1], tableRow);
            else
                removeTopping(TOPPINGS[tableRow - 1], tableRow);
        }

        private void addTopping(Pizza.Topping topping, int tableRow)
        {
            pizza.addTopping(topping);
            String[] data = { topping.name(), String.format("$%5.2f", Pizza.TOPPING_PRICE) };
            invoice.setValueAt(String.format("$%5.2f", pizza.price()), model.getRowCount() - 1, 1);
            model.insertRow(model.getRowCount() - 1, data);
            pizzaPanel.repaint();
        }

        private void removeTopping(Pizza.Topping topping, int tableRow)
        {
            pizza.removeTopping(topping);
            invoice.setValueAt(String.format("$%5.2f", pizza.price()), model.getRowCount() - 1, 1);
            removeRow(topping.name());
            pizzaPanel.repaint();
        }

        private void removeRow(String topping)
        {
            boolean found = false;

            for (int i = 0; i < invoice.getRowCount() && !found; i++)
            {
                if (model.getValueAt(i, 0).equals(topping))
                {
                    found = true;
                    model.removeRow(i);
                }
            }
        }
    }
}
