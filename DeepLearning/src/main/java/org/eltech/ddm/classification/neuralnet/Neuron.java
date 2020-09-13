package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class Neuron extends MiningModelElement {

    private double bias;
    private ActivationFunction.ActivationFunctions activationFunction;

    public Neuron(String id, ActivationFunction.ActivationFunctions af) {
        super(id);
        bias = 0;
        activationFunction = af;
    }

    public Neuron(String id,  ActivationFunction.ActivationFunctions af, double bias) {
        super(id);
        this.bias = bias;
        activationFunction = af;
    }

    public void addNewConnection(Neuron neuron){
        add(new NCon( neuron.getID() + " to " + getID(), neuron));
    }

    double calculateOutput(){
        double output = bias;
        for (int i = 0; i < size(); i++) {
            output += getConnection(i).getWeightedOutput();
        }
        output = ActivationFunction.applyFunction(activationFunction, output);

        return output;
    }

    public NCon getConnection(int index) {
        return (NCon) getElement(index);
    }

    public double getBias() {
        return bias;
    }

    public void setBias(double bias) {
        this.bias = bias;
    }

    @Override
    protected String propertiesToString() {
        return null;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        double sum = 0;
        for (MiningModelElement neuron : elements) {
            sum += ((Neuron)neuron).getBias();
        }
        bias = sum / elements.size();
    }
}
