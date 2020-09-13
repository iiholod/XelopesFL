package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningmodel.MiningModelElement;

import java.util.List;

public class NeuralLayerSet extends MiningModelElement {

    public NeuralLayerSet(String id) {
        super(id);
    }

    public NeuralLayerSet(String id, ActivationFunction.ActivationFunctions af, int... hidNeuronsNums) {
        super(id);

        for (int hidNeuronsNum : hidNeuronsNums) {
            addNewLayer(hidNeuronsNum, af);
        }
    }

    private void addNewLayer(int hidNeuronsNum, ActivationFunction.ActivationFunctions af) {
        add(new NeuralLayer("Hidden Layer [" + size() + "]", hidNeuronsNum, af));
    }

    public NeuralLayer getLayer(int index){
        return (NeuralLayer)getElement(index);
    }

    public void createConnections(NeuralLayer inLayer, NeuralLayer outLayer){
        getLayer(0).connectLayer(inLayer);
        if (size() > 1) {
            for (int i = 1; i < size(); i++) {
                getLayer(i).connectLayer(getLayer(i - 1));
            }
        }
        outLayer.connectLayer(getLayer(size() - 1));
    }

    public int getNumberOfNeurons() {
        int number = 0;
        for (MiningModelElement miningModelElement : set) {
            number += miningModelElement.size();
        }
        return number;
    }

    @Override
    protected String propertiesToString() {
        return null;
    }

    @Override
    public void merge(List<MiningModelElement> elements) throws MiningException {

    }
}
