import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EnhancedShopBillingSystem extends JFrame {
    // Fields for items
    private JTextField riceField, sugarField, milkField, soapField, oilField;
    private JTextField teaField, coffeeField, toothpasteField, shampooField;
    private JTextArea billArea;

    public EnhancedShopBillingSystem() {
        setTitle("Enhanced Shop Billing System");
        setSize(700, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(10, 2, 10, 10));
        inputPanel.setBorder(BorderFactory.createTitledBorder("Enter Quantity"));

        riceField = addItem("Rice (₹50/kg):", inputPanel);
        sugarField = addItem("Sugar (₹40/kg):", inputPanel);
        milkField = addItem("Milk (₹25/litre):", inputPanel);
        soapField = addItem("Soap (₹30/pc):", inputPanel);
        oilField = addItem("Oil (₹100/litre):", inputPanel);
        teaField = addItem("Tea (₹120/kg):", inputPanel);
        coffeeField = addItem("Coffee (₹300/kg):", inputPanel);
        toothpasteField = addItem("Toothpaste (₹45/pc):", inputPanel);
        shampooField = addItem("Shampoo (₹80/bottle):", inputPanel);

        JButton generateButton = new JButton("Generate Bill");
        JButton clearButton = new JButton("Clear");
        inputPanel.add(generateButton);
        inputPanel.add(clearButton);
        add(inputPanel, BorderLayout.NORTH);

        // Bill Area
        billArea = new JTextArea();
        billArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        billArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(billArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Bill"));
        add(scrollPane, BorderLayout.CENTER);

        // Bottom panel for save and print
        JPanel bottomPanel = new JPanel();
        JButton saveButton = new JButton("Save Bill");
        JButton printButton = new JButton("Print Bill");
        bottomPanel.add(saveButton);
        bottomPanel.add(printButton);
        add(bottomPanel, BorderLayout.SOUTH);

        // Action Listeners
        generateButton.addActionListener(e -> generateBill());
        clearButton.addActionListener(e -> clearFields());
        saveButton.addActionListener(e -> saveBillToFile());
        printButton.addActionListener(e -> printBill());

        setVisible(true);
    }

    private JTextField addItem(String label, JPanel panel) {
        panel.add(new JLabel(label));
        JTextField field = new JTextField("0");
        panel.add(field);
        return field;
    }

    private void generateBill() {
        int rice = getValue(riceField), sugar = getValue(sugarField), milk = getValue(milkField);
        int soap = getValue(soapField), oil = getValue(oilField), tea = getValue(teaField);
        int coffee = getValue(coffeeField), paste = getValue(toothpasteField), shampoo = getValue(shampooField);

        billArea.setText("********** SHOP BILL **********\n");
        billArea.append("Date: " + new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date()) + "\n\n");

        double total = 0;

        total += printItem("Rice", rice, 50);
        total += printItem("Sugar", sugar, 40);
        total += printItem("Milk", milk, 25);
        total += printItem("Soap", soap, 30);
        total += printItem("Oil", oil, 100);
        total += printItem("Tea", tea, 120);
        total += printItem("Coffee", coffee, 300);
        total += printItem("Toothpaste", paste, 45);
        total += printItem("Shampoo", shampoo, 80);

        billArea.append("\n-----------------------------\n");
        double gst = total * 0.05;
        double finalAmount = total + gst;
        billArea.append(String.format("Subtotal: ₹%.2f\n", total));
        billArea.append(String.format("GST (5%%): ₹%.2f\n", gst));
        billArea.append(String.format("Total Amount: ₹%.2f\n", finalAmount));
        billArea.append("*****************************\n");
        billArea.append("Thank you! Visit Again!\n");
    }

    private double printItem(String name, int qty, double rate) {
        if (qty > 0) {
            double amt = qty * rate;
            billArea.append(String.format("%-12s x%-3d @ ₹%.2f = ₹%.2f\n", name, qty, rate, amt));
            return amt;
        }
        return 0;
    }

    private int getValue(JTextField field) {
        try {
            return Integer.parseInt(field.getText().trim());
        } catch (Exception e) {
            return 0;
        }
    }

    private void clearFields() {
        for (Component c : ((JPanel) getContentPane().getComponent(0)).getComponents()) {
            if (c instanceof JTextField) {
                ((JTextField) c).setText("0");
            }
        }
        billArea.setText("");
    }

    private void saveBillToFile() {
        try {
            String filename = "Bill_" + System.currentTimeMillis() + ".txt";
            BufferedWriter writer = new BufferedWriter(new FileWriter(filename));
            writer.write(billArea.getText());
            writer.close();
            JOptionPane.showMessageDialog(this, "Bill saved as " + filename);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error saving bill.");
        }
    }

    private void printBill() {
        try {
            billArea.print();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Error printing bill.");
        }
    }

    public static void main(String[] args) {
        new EnhancedShopBillingSystem();
    }
}
