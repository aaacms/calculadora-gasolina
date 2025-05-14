package pacotecalculadora;

import javax.swing.*;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.text.NumberFormat;
import java.util.Locale;

public class GasolinaCalc extends JFrame {
    // Componentes de seleção de modo
    private final JRadioButton rbDistancia  = new JRadioButton("Distância total (km)");
    private final JRadioButton rbOdometro   = new JRadioButton("Diferença (km)");
    private final JPanel       pnlInputs    = new JPanel(new CardLayout());

    // Campos de entrada
    private final JTextField   tfDistancia  = new JTextField(10);
    private final JTextField   tfOdoInicial = new JTextField(8);
    private final JTextField   tfOdoFinal   = new JTextField(8);
    private final JTextField   tfConsumo    = new JTextField(10);
    private final JTextField   tfPreco      = new JTextField(10);
    private final JTextField   tfReceita    = new JTextField(10);  // Campo Receita

    // Labels de resultado
    private final JLabel resultLitro = new JLabel("Litros gastos: ");
    private final JLabel resultCusto = new JLabel("Custo total: ");
    private final JLabel resultLucro = new JLabel("Lucro estimado: ");

    public GasolinaCalc() {
        super("Calculadora de Ganhos para Taxista");
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        // painel principal
        JPanel main = new JPanel(new BorderLayout(15, 15));
        main.setBorder(new EmptyBorder(20, 20, 20, 20));
        setContentPane(main);

        // 1) Panel Receita em primeiro lugar
        JPanel pnlReceita = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        pnlReceita.setBorder(new CompoundBorder(
                new TitledBorder("Receita"),
                new EmptyBorder(5, 10, 10, 10)
        ));
        pnlReceita.add(new JLabel("Receita (R$):"));
        pnlReceita.add(tfReceita);
        main.add(pnlReceita, BorderLayout.NORTH);

        // 2) Panel Dados de Viagem (modo + distância/odômetro) com borda titulada
        // 2.1 painel de modo
        JPanel pnlModo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        ButtonGroup bg = new ButtonGroup();
        bg.add(rbDistancia);
        bg.add(rbOdometro);
        rbDistancia.setSelected(true);
        pnlModo.add(new JLabel("Modo de entrada:"));
        pnlModo.add(rbDistancia);
        pnlModo.add(rbOdometro);

        // 2.2 cards de distância ou odômetro
        JPanel cardDist = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        cardDist.add(new JLabel("Distância (km):"));
        cardDist.add(tfDistancia);

        JPanel cardOdo = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 10));
        cardOdo.add(new JLabel("Quilômetro inicial:"));
        cardOdo.add(tfOdoInicial);
        cardOdo.add(new JLabel("Final:"));
        cardOdo.add(tfOdoFinal);

        pnlInputs.add(cardDist, "DIST");
        pnlInputs.add(cardOdo,  "ODO");

        // 2.3 painel composto com borda
        JPanel pnlViagem = new JPanel(new BorderLayout(10, 10));
        pnlViagem.setBorder(new CompoundBorder(
                new TitledBorder("Dados de Viagem"),
                new EmptyBorder(5, 10, 10, 10)
        ));
        pnlViagem.add(pnlModo,    BorderLayout.NORTH);
        pnlViagem.add(pnlInputs,  BorderLayout.CENTER);

        main.add(pnlViagem, BorderLayout.CENTER);

        // 3) Parâmetros de cálculo (consumo, preço)
        JPanel pnlParams = new JPanel(new GridLayout(2, 2, 10, 10));
        pnlParams.add(new JLabel("Consumo (km/l):"));
        pnlParams.add(tfConsumo);
        pnlParams.add(new JLabel("Preço por litro (R$):"));
        pnlParams.add(tfPreco);

        // 4) Botão Calcular
        JButton btnCalc = new JButton("Calcular");
        btnCalc.setPreferredSize(new Dimension(120, 30));
        btnCalc.addActionListener(this::onCalcular);

        // 5) Painel de resultados (Litros | Custo | Lucro)
        JPanel pnlResults = new JPanel(new GridLayout(3, 1, 0, 8));
        pnlResults.add(resultLitro);
        pnlResults.add(resultCusto);
        pnlResults.add(resultLucro);

        // Destaque visual nos resultados
        Font   f = resultLitro.getFont().deriveFont(Font.BOLD, 14f);
        Color  c = new Color(0, 102, 204);
        for (JLabel lbl : new JLabel[]{ resultLitro, resultCusto, resultLucro }) {
            lbl.setFont(f);
            lbl.setForeground(c);
        }
        pnlResults.setBorder(new CompoundBorder(
                new TitledBorder("Resultado"),
                new EmptyBorder(5,10,10,10)
        ));

        // 6) Combina parâmetros, botão e resultados no sul
        JPanel pnlSul = new JPanel();
        pnlSul.setLayout(new BoxLayout(pnlSul, BoxLayout.Y_AXIS));
        pnlSul.setBorder(new EmptyBorder(10,0,0,0));
        pnlSul.add(pnlParams);
        pnlSul.add(Box.createVerticalStrut(10));
        pnlSul.add(btnCalc);
        pnlSul.add(Box.createVerticalStrut(15));
        pnlSul.add(pnlResults);
        main.add(pnlSul, BorderLayout.SOUTH);

        // listeners de troca de card
        rbDistancia.addActionListener(e -> switchCard("DIST"));
        rbOdometro .addActionListener(e -> switchCard("ODO"));

        pack();
        setLocationRelativeTo(null);
    }

    private void switchCard(String name) {
        CardLayout cl = (CardLayout)(pnlInputs.getLayout());
        cl.show(pnlInputs, name);
    }

    private void onCalcular(ActionEvent e) {
        try {
            // calcula distância
            double distancia;
            if (rbDistancia.isSelected()) {
                distancia = Double.parseDouble(tfDistancia.getText().replace(",", "."));
            } else {
                double ini = Double.parseDouble(tfOdoInicial.getText().replace(",", "."));
                double fim = Double.parseDouble(tfOdoFinal  .getText().replace(",", "."));
                distancia = fim - ini;
                if (distancia < 0) throw new NumberFormatException("Km final menor que inicial");
            }

            // parâmetros
            double consumo       = Double.parseDouble(tfConsumo .getText().replace(",", "."));
            double precoPorLitro = Double.parseDouble(tfPreco   .getText().replace(",", "."));
            double receita       = Double.parseDouble(tfReceita .getText().replace(",", "."));

            // cálculos
            double litros = distancia / consumo;
            double custo  = litros * precoPorLitro;
            double lucro  = receita - custo;

            // formatação e exibição
            String litrosFmt = String.format("%.2f L", litros);
            NumberFormat cf = NumberFormat.getCurrencyInstance(new Locale("pt", "BR"));
            resultLitro.setText("Litros gastos: "     + litrosFmt);
            resultCusto.setText("Custo total: "       + cf.format(custo));
            resultLucro.setText("Lucro estimado: "    + cf.format(lucro));

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this,
                "Confira se todos os campos estão corretos.\nErro: " + ex.getMessage(),
                "Entrada Inválida",
                JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new GasolinaCalc().setVisible(true));
    }
}
