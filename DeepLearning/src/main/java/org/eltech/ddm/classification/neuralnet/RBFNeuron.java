package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.neuralnet.ActivationFunction.ActivationFunctions;

public class RBFNeuron extends Neuron {

    private double width;
    private double altitude;
    //center = weigth in connection
//    private double[] center;

    public RBFNeuron(String id, ActivationFunction.ActivationFunctions af) {
        super(id, af);
    }

    public RBFNeuron(String id, ActivationFunctions af, double bias) {
        super(id, af, bias);
    }

    @Override
    double calculateOutput() {

        double output = 0d;

        for (int i = 0; i < size(); i++) {
            NCon con = getConnection(i);
            double diff = con.getOutput() - con.getWeight();
            output += diff * diff;
        }
        output = output / (2 * width * width);
        output = ActivationFunction.applyRBFfunction(output, altitude, size());

        return output;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }
}
