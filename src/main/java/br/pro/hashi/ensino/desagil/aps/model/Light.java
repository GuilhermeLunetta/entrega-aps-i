package br.pro.hashi.ensino.desagil.aps.model;

import java.awt.*;

public class Light implements Receiver {
    private Color color;
    private Emitter emitter;
    private Color offColor;

    public Light(int r, int g, int b, int r1, int g1, int b1) {
        color = new Color(r, g, b);
        emitter = null;
        offColor = new Color(r1, g1, b1);
    }

    public Color getColor() {
        if (emitter != null && emitter.read()) {
            return color;
        }
        return Color.BLACK;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public  Color getOffColor() {
        if (emitter != null && emitter.read()) {
            return offColor;
        }
        return Color.BLACK;
    }

    public void setOffColor(Color offColor) {
        this.offColor = offColor;
    }

    @Override
    public void connect(int inputIndex, Emitter emitter) {
        if (inputIndex != 0) {
            throw new IndexOutOfBoundsException(inputIndex);
        }
        this.emitter = emitter;
    }
}
