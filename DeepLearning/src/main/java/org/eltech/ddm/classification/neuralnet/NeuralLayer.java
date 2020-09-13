package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.neuralnet.ActivationFunction.ActivationFunctions;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class NeuralLayer extends MiningModelElement {

    private ActivationFunction.ActivationFunctions activationFunction;

    public int getNumberOfNeurons() {
        return size();
    }

    public NeuralLayer(String id) {
        super(id);
    }

    public NeuralLayer(String id, int numberOfNeurons, ActivationFunction.ActivationFunctions af) {
        super(id);
        activationFunction = af;
        if (activationFunction == ActivationFunctions.radialBasis) {
            for (int i = 0; i < numberOfNeurons; i++) {
                addNewRBFNeuron();
            }
        } else {
            for (int i = 0; i < numberOfNeurons; i++) {
                addNewNeuron();
            }
        }

    }

    public void addNewNeuron() {
        add(new Neuron(this.getID() + "Neuron № " + size(), activationFunction));
    }

    public void addNewRBFNeuron() {
        add(new RBFNeuron(this.getID() + "Neuron № " + size(), activationFunction));
    }

    /**
     * For every {@link Neuron} in this layer creates new connections to every Neuron in {@code layer}
     * @param layer Neural layer to which connections are made
     */
    public void connectLayer(NeuralLayer layer) {
        for (int i = 0; i < size(); i++) {
            for (int j = 0; j < layer.size(); j++) {
                getNeuron(i).addNewConnection(layer.getNeuron(j));
            }
        }
    }

    public Neuron getNeuron(int index) {
        return (Neuron) getElement(index);
    }

    @Override
    protected String propertiesToString() {
        return null;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
