import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class AdvancedScientificCalculator extends JFrame implements ActionListener {
    private JTextField display;
    private JTextArea historyArea;
    private double num1 = 0, num2 = 0, result = 0;
    private String operator = "";
    private boolean isNewInput = true;
    private boolean isPower = false;

    private ArrayList<String> history = new ArrayList<>();

    public AdvancedScientificCalculator() {
        setTitle("Advanced Scientific Calculator");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Display Field
        display = new JTextField("0");
        display.setFont(new Font("Arial", Font.BOLD, 26));
        display.setHorizontalAlignment(SwingConstants.RIGHT);
        display.setEditable(false);
        add(display, BorderLayout.NORTH);

        // History Panel
        historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setBorder(BorderFactory.createTitledBorder("History"));
        scrollPane.setPreferredSize(new Dimension(180, 0));
        add(scrollPane, BorderLayout.EAST);

        // Button Panel
        JPanel buttonPanel = new JPanel(new GridLayout(7, 4, 5, 5));
        String[] buttons = {
            "7", "8", "9", "/",
            "4", "5", "6", "*",
            "1", "2", "3", "-",
            "0", ".", "=", "+",
            "π", "e", "x^y", "C",
            "sin", "cos", "tan", "sqrt",
            "log", "exp", "x^2", "1/x"
        };

        for (String text : buttons) {
            JButton btn = new JButton(text);
            btn.setFont(new Font("Arial", Font.BOLD, 16));
            btn.addActionListener(this);
            buttonPanel.add(btn);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        String cmd = e.getActionCommand();

        if ("0123456789.".contains(cmd)) {
            if (isNewInput || display.getText().equals("0")) {
                display.setText(cmd);
                isNewInput = false;
            } else {
                display.setText(display.getText() + cmd);
            }
        } else if ("+-*/".contains(cmd)) {
            num1 = Double.parseDouble(display.getText());
            operator = cmd;
            isNewInput = true;
            isPower = false;
        } else if (cmd.equals("x^y")) {
            num1 = Double.parseDouble(display.getText());
            isPower = true;
            isNewInput = true;
        } else if (cmd.equals("=")) {
            num2 = Double.parseDouble(display.getText());

            if (isPower) {
                result = Math.pow(num1, num2);
                operator = "x^" + num2;
                isPower = false;
            } else {
                switch (operator) {
                    case "+": result = num1 + num2; break;
                    case "-": result = num1 - num2; break;
                    case "*": result = num1 * num2; break;
                    case "/": result = (num2 != 0) ? num1 / num2 : 0; break;
                }
            }

            String expr = num1 + " " + operator + " " + num2 + " = " + format(result);
            updateHistory(expr);
            display.setText(format(result));
            isNewInput = true;
        } else if (cmd.equals("C")) {
            display.setText("0");
            num1 = num2 = result = 0;
            operator = "";
            isNewInput = true;
            isPower = false;
        } else if (cmd.equals("π")) {
            display.setText(format(Math.PI));
            isNewInput = true;
        } else if (cmd.equals("e")) {
            display.setText(format(Math.E));
            isNewInput = true;
        } else {
            double value = Double.parseDouble(display.getText());
            switch (cmd) {
                case "sin": result = Math.sin(Math.toRadians(value)); break;
                case "cos": result = Math.cos(Math.toRadians(value)); break;
                case "tan": result = Math.tan(Math.toRadians(value)); break;
                case "sqrt": result = Math.sqrt(value); break;
                case "log": result = Math.log10(value); break;
                case "exp": result = Math.exp(value); break;
                case "x^2": result = Math.pow(value, 2); break;
                case "1/x": result = value != 0 ? 1.0 / value : 0; break;
            }

            String expr = cmd + "(" + value + ") = " + format(result);
            updateHistory(expr);
            display.setText(format(result));
            isNewInput = true;
        }
    }

    private void updateHistory(String entry) {
        history.add(entry);
        historyArea.setText("");
        for (String line : history) {
            historyArea.append(line + "\n");
        }
    }

    private String format(double val) {
        DecimalFormat df = new DecimalFormat("0.########");
        return df.format(val);
    }

    public static void main(String[] args) {
        new AdvancedScientificCalculator();
    }
}
