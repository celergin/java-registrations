import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class Produto {
    private String nome;
    private String tipo;
    private double valor;

    public Produto(String nome, String tipo, double valor) {
        this.nome = nome;
        this.tipo = tipo;
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public String getTipo() {
        return tipo;
    }

    public double getValor() {
        return valor;
    }
}

class Inventario {
    private DefaultTableModel tabela;

    public Inventario(DefaultTableModel tabela) {
        this.tabela = tabela;
    }

    public void adicionar(String nome, String tipo, double valor) {
        tabela.addRow(new Object[]{nome, tipo, valor});
    }
}

public class LanchoneteAmaro47 {
    public static void main(String[] args) {
        JFrame janela = new JFrame("Gestão de Produtos");
        janela.setSize(600, 400);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());

        JPanel painelSuperior = new JPanel(new GridLayout(1, 3));
        JButton botaoCadastrar = new JButton("Cadastrar Produto");
        JButton botaoVisualizar = new JButton("Ver Produtos");
        JButton botaoLimpar = new JButton("Limpar Produtos");

        painelSuperior.add(botaoCadastrar);
        painelSuperior.add(botaoVisualizar);
        painelSuperior.add(botaoLimpar);

        DefaultTableModel modelo = new DefaultTableModel(new Object[]{"Nome", "Tipo", "Valor"}, 0);
        JTable tabela = new JTable(modelo);
        JScrollPane rolagem = new JScrollPane(tabela);

        JPanel painelCadastro = new JPanel(new GridLayout(3, 2));
        JTextField nomeCampo = new JTextField();
        JTextField tipoCampo = new JTextField();
        JTextField valorCampo = new JTextField();

        painelCadastro.add(new JLabel("Nome:"));
        painelCadastro.add(nomeCampo);
        painelCadastro.add(new JLabel("Tipo:"));
        painelCadastro.add(tipoCampo);
        painelCadastro.add(new JLabel("Valor:"));
        painelCadastro.add(valorCampo);

        Inventario inventario = new Inventario(modelo);

        botaoCadastrar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String nome = nomeCampo.getText().trim();
                String tipo = tipoCampo.getText().trim();
                String valorTexto = valorCampo.getText().trim();

                if (nome.isEmpty() || tipo.isEmpty() || valorTexto.isEmpty()) {
                    JOptionPane.showMessageDialog(janela, "Todos os campos devem ser preenchidos!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                double valor;
                try {
                    valor = Double.parseDouble(valorTexto);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(janela, "Valor inválido!", "Erro", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                inventario.adicionar(nome, tipo, valor);
                nomeCampo.setText("");
                tipoCampo.setText("");
                valorCampo.setText("");
            }
        });

        botaoVisualizar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                if (modelo.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(janela, "Nenhum produto cadastrado.", "Informação", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(janela, new JScrollPane(tabela), "Produtos Cadastrados", JOptionPane.INFORMATION_MESSAGE);
                }
            }
        });

        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                modelo.setRowCount(0);
            }
        });

        janela.add(painelSuperior, BorderLayout.NORTH);
        janela.add(painelCadastro, BorderLayout.CENTER);
        janela.add(rolagem, BorderLayout.SOUTH);
        janela.setVisible(true);
    }
}
