package view;

import br.pro.hashi.ensino.desagil.aps.model.Gate;
import br.pro.hashi.ensino.desagil.aps.model.Light;
import br.pro.hashi.ensino.desagil.aps.model.Switch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class GateView extends FixedPanel implements ActionListener, MouseListener {
    private final Gate gate;

    private final JCheckBox inputA;
    private final JCheckBox inputB;
    //private final JCheckBox output;
    private final Switch sinalA;
    private final Switch sinalB;
    private final Light light;
    private final Image image;

    public GateView(Gate gate) {

        super(400, 220);

        this.gate = gate;

        inputA = new JCheckBox("A");
        inputB = new JCheckBox("B");
        //output = new JCheckBox("Output");

        sinalA = new Switch();
        sinalB = new Switch();

        light = new Light(255, 0, 0, 0, 0, 0);


        //JLabel entrada = new JLabel("Entrada: ");
        //JLabel saida = new JLabel(" :Saída");

        if (gate.getInputSize() == 1) {
            //add(entrada, 10, 115, 75, 25);
            add(inputA, 85, 115, 20, 20);
            //add(saida, 315, 115, 75,25);
            //add(output, 252, 115, 20, 20);
        } else {
            //add(entrada, 10, 115, 75, 25);
            add(inputA, 85, 92, 20, 20);
            add(inputB, 85, 138, 20, 20);
            //add(saida, 315, 115, 75, 25);
            //add(output, 252, 115, 20, 20);
        }

        String name = gate.toString() + ".png";
        URL url = getClass().getClassLoader().getResource(name);
        image = getToolkit().getImage(url);

        inputA.addActionListener(this);
        inputB.addActionListener(this);

        //output.setEnabled(false);

        addMouseListener(this);

        update();
    }

    private void update() {

        if (inputA.isSelected()) {
            sinalA.turnOn();
        } else {
            sinalA.turnOff();
        }

        if (inputB.isSelected()) {
            sinalB.turnOn();
        } else {
            sinalB.turnOff();
        }

        if (gate.getInputSize() == 1) {
            gate.connect(0, sinalA);
        } else {
            gate.connect(0, sinalA);
            gate.connect(1, sinalB);
        }

        light.connect(0, gate);
        repaint();
    }

    @Override
    public void actionPerformed(ActionEvent event) {
        update();
    }

    @Override
    public void mouseClicked(MouseEvent event) {

        // Descobre em qual posição o clique ocorreu.
        int x = event.getX();
        int y = event.getY();

        // Se o clique foi dentro do quadrado colorido...
        if (Math.pow(x - 255, 2) + Math.pow(y - 114, 2) <= 625) {

            // ...então abrimos a janela seletora de cor...
            Color color = JColorChooser.showDialog(this, null, light.getColor());
            if (gate.read()) {
                light.setColor(color);
            } else {
                light.setOffColor(color);
            }
            // ...e chamamos repaint para atualizar a tela.
            repaint();
        }
    }

    @Override
    public void mousePressed(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de pressionar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseReleased(MouseEvent event) {
        // Não precisamos de uma reação específica à ação de soltar
        // um botão do mouse, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseEntered(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // entrar no painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void mouseExited(MouseEvent event) {
        // Não precisamos de uma reação específica à ação do mouse
        // sair do painel, mas o contrato com MouseListener obriga
        // esse método a existir, então simplesmente deixamos vazio.
    }

    @Override
    public void paintComponent(Graphics g) {

        // Não podemos esquecer desta linha, pois não somos os
        // únicos responsáveis por desenhar o painel, como era
        // o caso nos Desafios. Agora é preciso desenhar também
        // componentes internas, e isso é feito pela superclasse.
        super.paintComponent(g);

        // Desenha a imagem, passando sua posição e seu tamanho.
        g.drawImage(image, 91, 70, 165, 110, this);
        //g.drawOval(253, 118, 17, 17);
        // Desenha um quadrado cheio.
        g.setColor(light.getColor());
        g.fillOval(255, 114, 25, 25);
        // Linha necessária para evitar atrasos
        // de renderização em sistemas Linux.
        getToolkit().sync();
    }
}
