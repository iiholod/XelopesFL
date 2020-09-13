package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class NCon extends MiningModelElement {
    private Neuron from;
    private double weight;

    public NCon(String id, Neuron from) {
        super(id);
        this.from = from;
        this.weight = Math.random();
    }

    public NCon(String id, Neuron from, double weight) {
        super(id);
        this.from = from;
        this.weight = weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    double getWeight(){
        return weight;
    }

    double getWeightedOutput(){
        return from.calculateOutput() * weight;
    }

    double getOutput(){
        return from.calculateOutput();
    }

    @Override
    protected String propertiesToString() {
        return ", weight= " + weight;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {
        double sum = 0;
        for (MiningModelElement element : elements) {
            sum += ((NCon)element).getWeight();
        }
        weight = sum / elements.size();
    }
}
