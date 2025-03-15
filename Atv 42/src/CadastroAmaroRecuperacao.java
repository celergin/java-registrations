import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

class Candidato {
    String nome;
    String formacao;
    String areaInteresse;

    public Candidato(String nome, String formacao, String areaInteresse) {
        this.nome = nome;
        this.formacao = formacao;
        this.areaInteresse = areaInteresse;
    }
}

class TelaCadastro extends JFrame {
    private JTextField txtNome, txtFormacao;
    private JTextArea txtAreaInteresse;
    private List<Candidato> listaCandidatos;

    public TelaCadastro(List<Candidato> listaCandidatos) {
        this.listaCandidatos = listaCandidatos;
        setTitle("Tela de Cadastro dos Candidatos");
        setSize(870, 370);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(4, 2, 10, 10));

        add(new JLabel("Nome:"));
        txtNome = new JTextField(20);
        add(txtNome);

        add(new JLabel("Formação:"));
        txtFormacao = new JTextField(20);
        add(txtFormacao);

        add(new JLabel("Área de Interesse:"));
        txtAreaInteresse = new JTextArea(2, 20);
        add(new JScrollPane(txtAreaInteresse));

        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        JButton btnSalvar = new JButton("Salvar");
        JButton btnCancelar = new JButton("Cancelar");
        JButton btnProximo = new JButton("Próximo >>");

        btnSalvar.setPreferredSize(new Dimension(120, 30));
        btnCancelar.setPreferredSize(new Dimension(120, 30));
        btnProximo.setPreferredSize(new Dimension(130, 30));

        botoes.add(btnSalvar);
        botoes.add(btnCancelar);
        botoes.add(btnProximo);
        add(botoes);

        btnSalvar.addActionListener(e -> {
            listaCandidatos.add(new Candidato(txtNome.getText(), txtFormacao.getText(), txtAreaInteresse.getText()));
            txtNome.setText("");
            txtFormacao.setText("");
            txtAreaInteresse.setText("");
        });

        btnCancelar.addActionListener(e -> System.exit(0));

        btnProximo.addActionListener(e -> {
            dispose();
            new TelaContratacao(listaCandidatos).setVisible(true);
        });
    }
}

class TelaContratacao extends JFrame {
    private JList<String> listaCandidatosJList, listaContratadosJList;
    private DefaultListModel<String> modeloCandidatos, modeloContratados;

    public TelaContratacao(List<Candidato> listaCandidatos) {
        setTitle("Contratação de Funcionários");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(1, 3, 10, 10));

        modeloCandidatos = new DefaultListModel<>();
        for (Candidato c : listaCandidatos) {
            modeloCandidatos.addElement(c.nome);
        }
        listaCandidatosJList = new JList<>(modeloCandidatos);
        add(new JScrollPane(listaCandidatosJList));

        JPanel botoes = new JPanel(new GridLayout(6, 1, 10, 10));
        JButton btnAdicionar = new JButton("Adicionar >>");
        JButton btnRemover = new JButton("<< Remover");
        JButton btnAdicionarTodos = new JButton("Adicionar Todos >>");
        JButton btnRemoverTodos = new JButton("<< Remover Todos");
        JButton btnVoltar = new JButton("<< Voltar");
        JButton btnProximo = new JButton("Próximo >>");

        botoes.add(btnAdicionar);
        botoes.add(btnRemover);
        botoes.add(btnAdicionarTodos);
        botoes.add(btnRemoverTodos);
        botoes.add(btnVoltar);
        botoes.add(btnProximo);
        add(botoes);

        modeloContratados = new DefaultListModel<>();
        listaContratadosJList = new JList<>(modeloContratados);
        add(new JScrollPane(listaContratadosJList));

        btnAdicionar.addActionListener(e -> moverItem(modeloCandidatos, modeloContratados, listaCandidatosJList));
        btnRemover.addActionListener(e -> moverItem(modeloContratados, modeloCandidatos, listaContratadosJList));
        btnAdicionarTodos.addActionListener(e -> moverTodos(modeloCandidatos, modeloContratados));
        btnRemoverTodos.addActionListener(e -> moverTodos(modeloContratados, modeloCandidatos));
        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaCadastro(listaCandidatos).setVisible(true);
        });
        btnProximo.addActionListener(e -> {
            dispose();
            new TelaCentralFuncionarios(listaContratadosJList, listaCandidatos).setVisible(true);
        });
    }

    private void moverItem(DefaultListModel<String> origem, DefaultListModel<String> destino, JList<String> lista) {
        String selecionado = lista.getSelectedValue();
        if (selecionado != null) {
            origem.removeElement(selecionado);
            destino.addElement(selecionado);
        }
    }

    private void moverTodos(DefaultListModel<String> origem, DefaultListModel<String> destino) {
        while (!origem.isEmpty()) {
            destino.addElement(origem.remove(0));
        }
    }
}

class TelaCentralFuncionarios extends JFrame {
    private JLabel lblFormacao;
    private JTextArea txtAreaInteresse;

    public TelaCentralFuncionarios(JList<String> listaContratados, List<Candidato> listaCandidatos) {
        setTitle("Central de Funcionários");
        setSize(500, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JScrollPane scrollPane = new JScrollPane(listaContratados);
        scrollPane.setPreferredSize(new Dimension(150, getHeight()));
        add(scrollPane, BorderLayout.WEST);

        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        lblFormacao = new JLabel("Formação: ");
        txtAreaInteresse = new JTextArea("Área de Interesse: ");
        txtAreaInteresse.setEditable(false);
        infoPanel.add(lblFormacao);
        infoPanel.add(new JScrollPane(txtAreaInteresse));
        add(infoPanel, BorderLayout.CENTER);

        JButton btnVoltar = new JButton("<< Voltar");
        add(btnVoltar, BorderLayout.SOUTH);

        btnVoltar.addActionListener(e -> {
            dispose();
            new TelaContratacao(listaCandidatos).setVisible(true);
        });

        listaContratados.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) { // Evita duplicação de eventos
                String nomeSelecionado = listaContratados.getSelectedValue();
                if (nomeSelecionado != null) {
                    for (Candidato c : listaCandidatos) {
                        if (c.nome.equals(nomeSelecionado)) {
                            lblFormacao.setText("Formação: " + c.formacao);
                            txtAreaInteresse.setText("Área de Interesse: " + c.areaInteresse);
                            break;
                        }
                    }
                }
            }
        });
    }
}

public class CadastroAmaroRecuperacao {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            List<Candidato> listaCandidatos = new ArrayList<>();
            new TelaCadastro(listaCandidatos).setVisible(true);
        });
    }
}