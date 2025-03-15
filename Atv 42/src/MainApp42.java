import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainApp42 {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Menu Principal");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(400, 300);

            JPanel panel = new JPanel();
            panel.setLayout(new GridLayout(5, 1, 10, 10));

            JButton poupancaButton = new JButton("Cálculo da Poupança");
            JButton calculadoraButton = new JButton("Calculadora");
            JButton analiseNumerosButton = new JButton("Análise de Números");
            JButton adivinheNumeroButton = new JButton("Adivinhe o Número");

            panel.add(poupancaButton);
            panel.add(calculadoraButton);
            panel.add(analiseNumerosButton);
            panel.add(adivinheNumeroButton);

            frame.add(panel);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);

            poupancaButton.addActionListener(e -> new PoupancaApp());
            calculadoraButton.addActionListener(e -> new CalculadoraApp());
            analiseNumerosButton.addActionListener(e -> new AnaliseNumerosApp());
            adivinheNumeroButton.addActionListener(e -> new AdivinheONumeroApp());
        });
    }
}

class PoupancaApp {
    public PoupancaApp() {
        JFrame frame = new JFrame("Cálculo da Poupança");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel valorLabel = new JLabel("Valor Mensal:");
        JTextField valorField = new JTextField();
        JLabel jurosLabel = new JLabel("Juros (%):");
        JTextField jurosField = new JTextField();
        JLabel tempoLabel = new JLabel("Tempo (anos):");
        JTextField tempoField = new JTextField();
        JButton calcularButton = new JButton("Calcular");
        JLabel totalLabel = new JLabel("Total Poupado R$:");
        JLabel resultadoLabel = new JLabel();

        panel.add(valorLabel);
        panel.add(valorField);
        panel.add(jurosLabel);
        panel.add(jurosField);
        panel.add(tempoLabel);
        panel.add(tempoField);
        panel.add(calcularButton);
        panel.add(new JLabel());
        panel.add(totalLabel);
        panel.add(resultadoLabel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        calcularButton.addActionListener(e -> {
            try {
                double valor = Double.parseDouble(valorField.getText());
                double juros = Double.parseDouble(jurosField.getText()) / 100;
                int tempo = Integer.parseInt(tempoField.getText());
                double total = valor * ((Math.pow(1 + juros, tempo) - 1) / juros);
                resultadoLabel.setText(String.format("%.2f", total));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira valores válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

class CalculadoraApp {
    public CalculadoraApp() {
        JFrame frame = new JFrame("Calculadora");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(300, 400);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        JTextField display = new JTextField();
        display.setEditable(false);
        panel.add(display, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(4, 4, 5, 5));

        String[] buttons = {
                "7", "8", "9", "/",
                "4", "5", "6", "*",
                "1", "2", "3", "-",
                "0", ".", "=", "+"
        };

        for (String text : buttons) {
            JButton button = new JButton(text);
            buttonPanel.add(button);
            button.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String cmd = e.getActionCommand();
                    if (cmd.equals("=")) {
                        try {
                            String result = String.valueOf(eval(display.getText()));
                            display.setText(result);
                        } catch (Exception ex) {
                            display.setText("Erro");
                        }
                    } else {
                        display.setText(display.getText() + cmd);
                    }
                }
            });
        }

        panel.add(buttonPanel, BorderLayout.CENTER);
        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private double eval(String expression) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < expression.length()) ? expression.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < expression.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if (eat('+')) x += parseTerm(); 
                    else if (eat('-')) x -= parseTerm(); 
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if (eat('*')) x *= parseFactor(); 
                    else if (eat('/')) x /= parseFactor(); 
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); 
                if (eat('-')) return -parseFactor(); 

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(expression.substring(startPos, this.pos));
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                return x;
            }
        }.parse();
    }
}

class AnaliseNumerosApp {
    public AnaliseNumerosApp() {
        JFrame frame = new JFrame("Análise de Números");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 300);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));

        JLabel numerosLabel = new JLabel("Números (separados por vírgula):");
        JTextField numerosField = new JTextField();
        JButton okButton = new JButton("OK");
        JLabel menorLabel = new JLabel("Menor:");
        JLabel menorResultadoLabel = new JLabel();
        JLabel maiorLabel = new JLabel("Maior:");
        JLabel maiorResultadoLabel = new JLabel();
        JLabel mediaLabel = new JLabel("Média:");
        JLabel mediaResultadoLabel = new JLabel();

        panel.add(numerosLabel);
        panel.add(numerosField);
        panel.add(okButton);
        panel.add(new JLabel());
        panel.add(menorLabel);
        panel.add(menorResultadoLabel);
        panel.add(maiorLabel);
        panel.add(maiorResultadoLabel);
        panel.add(mediaLabel);
        panel.add(mediaResultadoLabel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        okButton.addActionListener(e -> {
            try {
                String[] numeros = numerosField.getText().split(",");
                double[] valores = new double[numeros.length];
                for (int i = 0; i < numeros.length; i++) {
                    valores[i] = Double.parseDouble(numeros[i].trim());
                }
                double menor = Double.MAX_VALUE;
                double maior = Double.MIN_VALUE;
                double soma = 0;
                for (double valor : valores) {
                    if (valor < menor) menor = valor;
                    if (valor > maior) maior = valor;
                    soma += valor;
                }
                double media = soma / valores.length;
                menorResultadoLabel.setText(String.valueOf(menor));
                maiorResultadoLabel.setText(String.valueOf(maior));
                mediaResultadoLabel.setText(String.format("%.2f", media));
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira números válidos.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });
    }
}

class AdivinheONumeroApp {
    private int numero;

    public AdivinheONumeroApp() {
        gerarNovoNumero();

        JFrame frame = new JFrame("Adivinhe o Número");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(400, 200);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(3, 1, 10, 10));

        JLabel instrucoesLabel = new JLabel("Eu tenho um número entre 1 e 100. Você pode adivinhá-lo?");
        JTextField chuteField = new JTextField();
        JButton tentarButton = new JButton("Tentar");
        JButton novoJogoButton = new JButton("Novo Jogo");
        JLabel resultadoLabel = new JLabel();

        panel.add(instrucoesLabel);
        panel.add(chuteField);
        panel.add(tentarButton);
        panel.add(novoJogoButton);
        panel.add(resultadoLabel);

        frame.add(panel);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        tentarButton.addActionListener(e -> {
            try {
                int chute = Integer.parseInt(chuteField.getText());
                if (chute < numero) {
                    resultadoLabel.setText("O número é maior.");
                } else if (chute > numero) {
                    resultadoLabel.setText("O número é menor.");
                } else {
                    resultadoLabel.setText("Correto!");
                    chuteField.setEditable(false);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Por favor, insira um número válido.", "Erro", JOptionPane.ERROR_MESSAGE);
            }
        });

        novoJogoButton.addActionListener(e -> {
            gerarNovoNumero();
            chuteField.setText("");
            resultadoLabel.setText("");
            chuteField.setEditable(true);
        });
    }

    private void gerarNovoNumero() {
        numero = (int) (Math.random() * 100) + 1;
    }
}
