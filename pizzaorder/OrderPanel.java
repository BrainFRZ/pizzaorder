package pizzaorder;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

    //Instance data
    private static JLabel title;
    private JTable invoice;
    private DefaultTableModel model;
    private JPanel options;
    private JRadioButton[] sizeBoxes;
    private JCheckBox[] toppingBoxes;

    /**********************************************************************************************
     *============================================================================================*
     *|  Class methods                                                                           |*
     *============================================================================================*
     **********************************************************************************************/

    public OrderPanel()
    {
        pizza = new Pizza();

        setLayout(new BorderLayout());

        title = new JLabel("Pizza Ordering System", SwingConstants.CENTER);
        title.setFont(new Font("Comic Sans MS", Font.PLAIN, 24));

        //Set up invoice
        String[] columns = { "Item", "Price" };
        Object[][] data  = new Object[2][2];    //[Pizza size + number of toppings][Columns]
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
        sizeLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);
        sizeBoxes = new JRadioButton[Pizza.Size.values().length];

        JPanel sizesPanel = new JPanel();
        ButtonGroup sizeGroup = new ButtonGroup();
        sizeLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        for (int i = 0; i < sizeBoxes.length; i++)
        {
            sizeBoxes[i] = new JRadioButton(Pizza.Size.values()[i].name());
            sizesPanel.add(sizeBoxes[i]);
            sizeGroup.add(sizeBoxes[i]);
            sizeBoxes[i].addActionListener(new SizeListener());
        }
        sizeBoxes[1].setSelected(true);
        options.add(sizeLabel);
        options.add(sizesPanel);

        JPanel toppingsPanel = new JPanel();
        toppingBoxes         = new JCheckBox[Pizza.Topping.values().length];
        JLabel toppingsLabel = new JLabel("Select Toppings:", SwingConstants.CENTER);
        toppingsLabel.setAlignmentX(java.awt.Component.CENTER_ALIGNMENT);

        toppingsLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        for (int i = 0; i < toppingBoxes.length; i++)
        {
            toppingBoxes[i] = new JCheckBox(Pizza.Topping.values()[i].name());
            toppingsPanel.add(toppingBoxes[i]);
            toppingBoxes[i].addActionListener(new ToppingsListener());
        }
        options.add(toppingsLabel);
        options.add(toppingsPanel);

        //Add components to main panel
        add(title, BorderLayout.NORTH);
        add(options, BorderLayout.CENTER);
        add(invoice, BorderLayout.EAST);
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
            if (e.getSource() == toppingBoxes[0])
            {
                if (toppingBoxes[0].isSelected())
                    addTopping(Pizza.Topping.values()[0], 1);
                else
                    removeTopping(Pizza.Topping.values()[0], 1);
            }
            else if (e.getSource() == toppingBoxes[1])
            {
                if (toppingBoxes[1].isSelected())
                    addTopping(Pizza.Topping.values()[1], 2);
                else
                    removeTopping(Pizza.Topping.values()[1], 2);
            }
            else if (e.getSource() == toppingBoxes[2])
            {
                if (toppingBoxes[2].isSelected())
                    addTopping(Pizza.Topping.values()[2], 3);
                else
                    removeTopping(Pizza.Topping.values()[2], 3);
            }
            else if (e.getSource() == toppingBoxes[3])
            {
                if (toppingBoxes[3].isSelected())
                    addTopping(Pizza.Topping.values()[3], 4);
                else
                    removeTopping(Pizza.Topping.values()[3], 4);
            }
            else if (e.getSource() == toppingBoxes[4])
            {
                if (toppingBoxes[4].isSelected())
                    addTopping(Pizza.Topping.values()[4], 5);
                else
                    removeTopping(Pizza.Topping.values()[4], 5);
            }
        }

        private void addTopping(Pizza.Topping topping, int row)
        {
            pizza.addTopping(topping);
            String[] data = { topping.name(), String.format("$%5.2f", Pizza.TOPPING_PRICE) };
            invoice.setValueAt(String.format("$%5.2f", pizza.price()), model.getRowCount() - 1, 1);
            model.insertRow(model.getRowCount() - 1, data);
        }

        private void removeTopping(Pizza.Topping topping, int row)
        {
            pizza.removeTopping(topping);
            invoice.setValueAt(String.format("$%5.2f", pizza.price()), model.getRowCount() - 1, 1);
            removeRow(topping.name());
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
