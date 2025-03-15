import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class SistemaEntregaAki {

    public static void main(String[] args) {
        new TelaPedido();
    }
}

class TelaPedido {
    private JFrame frame;
    private JComboBox<String> tamanhoPizza;
    private JCheckBox[] saboresCheckbox;
    private JRadioButton semBorda, comBorda;
    private JComboBox<String> bebida;
    private JLabel totalLabel;
    private JButton enviarPedido, historicoButton;

    private final String[] tamanhos = {"Pequena", "Média", "Grande", "Família"};
    private final String[] sabores = {"Queijo", "Calabresa", "Frango", "Frango com Catupiry", "Palmito", "Mexicana", "4 Queijos", "Portuguesa", "Margarita", "Camarão (+R$10)", "Salmão (+R$15)", "Filé (+R$20)"};
    private final String[] bebidas = {"Nenhuma", "Refrigerante 2L (R$5)", "Suco 1L (R$7)"};
    private final String[] tiposBorda = {"Catupiry", "Chocolate", "Cheddar"};

    private final double[] precosTamanhos = {45.00, 60.00, 75.00, 90.00};
    private final int[] maxSabores = {1, 2, 3, 4};

    private double total = 0.0;
    private ArrayList<String> pedidosHistorico = new ArrayList<>();
    private boolean modoEscuro = true;

    private JComboBox<String> opcoesBorda;

    public TelaPedido() {
        frame = new JFrame("Sistema Online EntregaAki");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 600);
        frame.setLayout(new FlowLayout(FlowLayout.LEFT, 10, 10));

        criarInterface();
        aplicarTema();

        frame.setVisible(true);
    }

    private void criarInterface() {
        JLabel titulo = new JLabel("Sistema EntregaAki", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        titulo.setOpaque(true);
        titulo.setBackground(Color.RED);
        titulo.setForeground(Color.WHITE);
        titulo.setPreferredSize(new Dimension(480, 30));
        frame.add(titulo);

        JToggleButton temaButton = new JToggleButton("Modo Claro");
        temaButton.setSelected(true);
        temaButton.addActionListener(e -> {
            modoEscuro = !modoEscuro;
            temaButton.setText(modoEscuro ? "Modo Claro" : "Modo Escuro");
            aplicarTema();
        });
        frame.add(temaButton);

        adicionarSeparador();

        frame.add(new JLabel("Escolha o TAMANHO da pizza:"));
        tamanhoPizza = new JComboBox<>(tamanhos);
        frame.add(tamanhoPizza);

        adicionarSeparador();

        frame.add(new JLabel("Escolha os SABORES:"));
        saboresCheckbox = new JCheckBox[sabores.length];
        for (int i = 0; i < sabores.length; i++) {
            saboresCheckbox[i] = new JCheckBox(sabores[i]);
            frame.add(saboresCheckbox[i]);
        }

        JLabel qtdeSabores = new JLabel("Qtde de Sabores: 0");
        frame.add(qtdeSabores);
        adicionarSeparador();

        frame.add(new JLabel("Escolha a BORDA (+R$8,00):"));
        ButtonGroup bordaGroup = new ButtonGroup();
        semBorda = new JRadioButton("Sem borda", true);
        comBorda = new JRadioButton("Com borda");
        bordaGroup.add(semBorda);
        bordaGroup.add(comBorda);
        frame.add(semBorda);
        frame.add(comBorda);

        opcoesBorda = new JComboBox<>(tiposBorda);
        opcoesBorda.setVisible(false);
        frame.add(opcoesBorda);
        adicionarSeparador();

        frame.add(new JLabel("Escolha a BEBIDA:"));
        bebida = new JComboBox<>(bebidas);
        frame.add(bebida);
        adicionarSeparador();

        totalLabel = new JLabel("Total a Pagar: R$0.00");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 14));
        frame.add(totalLabel);

        enviarPedido = new JButton("ENVIAR PEDIDO");
        frame.add(enviarPedido);

        historicoButton = new JButton("VER HISTÓRICO");
        frame.add(historicoButton);

        adicionarListeners(qtdeSabores);
    }

    private void adicionarSeparador() {
        frame.add(new JSeparator(SwingConstants.HORIZONTAL) {{
            setPreferredSize(new Dimension(480, 1));
        }});
    }

    private void adicionarListeners(JLabel qtdeSabores) {
        tamanhoPizza.addActionListener(e -> atualizarQuantidadeSabores(qtdeSabores));

        for (JCheckBox checkbox : saboresCheckbox) {
            checkbox.addActionListener(e -> verificarLimiteSabores());
        }

        semBorda.addActionListener(e -> {
            opcoesBorda.setVisible(false);
            atualizarTotal();
        });

        comBorda.addActionListener(e -> {
            opcoesBorda.setVisible(true);
            atualizarTotal();
        });

        bebida.addActionListener(e -> atualizarTotal());

        enviarPedido.addActionListener(e -> enviarPedido());
        historicoButton.addActionListener(e -> abrirHistorico());
    }

    private void atualizarQuantidadeSabores(JLabel qtdeSabores) {
        int index = tamanhoPizza.getSelectedIndex();
        int max = maxSabores[index];
        qtdeSabores.setText("Qtde de Sabores: " + max);
        atualizarTotal();
    }

    private void verificarLimiteSabores() {
        int index = tamanhoPizza.getSelectedIndex();
        int max = maxSabores[index];
        int selecionados = 0;

        for (JCheckBox checkbox : saboresCheckbox) {
            if (checkbox.isSelected()) {
                selecionados++;
            }
        }

        if (selecionados > max) {
            for (int i = saboresCheckbox.length - 1; i >= 0; i--) {
                if (saboresCheckbox[i].isSelected()) {
                    saboresCheckbox[i].setSelected(false);
                    break;
                }
            }
            JOptionPane.showMessageDialog(frame, "Número máximo de sabores permitidos: " + max, "Aviso", JOptionPane.WARNING_MESSAGE);
        }

        atualizarTotal();
    }

    private void atualizarTotal() {
        total = precosTamanhos[tamanhoPizza.getSelectedIndex()];

        for (JCheckBox checkbox : saboresCheckbox) {
            if (checkbox.isSelected()) {
                if (checkbox.getText().contains("(+R$10)")) total += 10;
                if (checkbox.getText().contains("(+R$15)")) total += 15;
                if (checkbox.getText().contains("(+R$20)")) total += 20;
            }
        }

        if (comBorda.isSelected()) {
            total += 8;
        }

        if (bebida.getSelectedIndex() == 1) {
            total += 5;
        } else if (bebida.getSelectedIndex() == 2) {
            total += 7;
        }

        totalLabel.setText(String.format("Total a Pagar: R$%.2f", total));
    }

    private void enviarPedido() {
        StringBuilder pedido = new StringBuilder();
        pedido.append("Pedido:\n");
        pedido.append("Tamanho: ").append(tamanhoPizza.getSelectedItem()).append("\n");

        pedido.append("Sabores: ");
        for (JCheckBox checkbox : saboresCheckbox) {
            if (checkbox.isSelected()) {
                pedido.append(checkbox.getText()).append(", ");
            }
        }
        if (pedido.toString().endsWith(", ")) {
            pedido.setLength(pedido.length() - 2);
        }
        pedido.append("\n");

        pedido.append("Borda: ").append(comBorda.isSelected() ? "Com borda (" + opcoesBorda.getSelectedItem() + ")" : "Sem borda").append("\n");
        pedido.append("Bebida: ").append(bebida.getSelectedItem()).append("\n");
        pedido.append(String.format("Total: R$%.2f\n", total));

        JOptionPane.showMessageDialog(frame, pedido.toString(), "Resumo do Pedido", JOptionPane.INFORMATION_MESSAGE);

        pedidosHistorico.add(0, pedido.toString());
    }

    private void abrirHistorico() {
        JFrame historicoFrame = new JFrame("Histórico de Pedidos");
        historicoFrame.setSize(400, 300);

        JTextArea historicoTextArea = new JTextArea(String.join("\n\n", pedidosHistorico));
        historicoTextArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(historicoTextArea);

        historicoFrame.add(scrollPane);
        historicoFrame.setVisible(true);
    }

    private void aplicarTema() {
        Color fundo = modoEscuro ? Color.DARK_GRAY : Color.WHITE;
        Color texto = modoEscuro ? Color.WHITE : Color.BLACK;

        frame.getContentPane().setBackground(fundo);
        for (Component comp : frame.getContentPane().getComponents()) {
            if (comp instanceof JLabel || comp instanceof JCheckBox || comp instanceof JRadioButton || comp instanceof JComboBox || comp instanceof JButton) {
                comp.setBackground(fundo);
                comp.setForeground(texto);
            }
        }

        opcoesBorda.setBackground(fundo);
        opcoesBorda.setForeground(texto);
    }
}
