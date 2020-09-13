package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.neuralnet.ActivationFunction.ActivationFunctions;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;

public class FeedForwardNeuralNetworkModel extends NeuralNetworkModel {

    private final static int[] HIDDEN_LAYERS = {3};
    private NeuralLayerSet neuralLayerSet;

    public FeedForwardNeuralNetworkModel(EMiningFunctionSettings settings, int inNeuronsNum, int outNeuronsNum, ActivationFunctions activationFunction, int... hidNeuronsNums)
            throws MiningException {
        super(settings);

        setInputLayer(new NeuralLayer("NeuralInput", inNeuronsNum, activationFunction));
        setOutputLayer(new NeuralLayer("NeuralOutput", outNeuronsNum, activationFunction));
        this.activationFunction = activationFunction;

        if (hidNeuronsNums.length > 0){
            neuralLayerSet = new NeuralLayerSet("Hidden Layers", activationFunction, hidNeuronsNums);
            sets.add(HIDDEN_LAYERS[0], neuralLayerSet);
        }

        connectLayers();
    }

    @Override
    protected void connectLayers() throws MiningException {
        if (sets.size() < 4){
            getOutputLayer().connectLayer(getInputLayer());
        } else {
            neuralLayerSet.createConnections(getInputLayer(), getOutputLayer());
        }
    }

    @Override
    public int numberOfLayers() {
        if (neuralLayerSet != null) {
            return neuralLayerSet.size() + 2;
        } else return 2;
    }

    @Override
    public int numberOfNeurons() throws MiningException {
        if (neuralLayerSet != null) {
            return getInputLayer().getNumberOfNeurons() + getOutputLayer().getNumberOfNeurons() + neuralLayerSet.getNumberOfNeurons();
        } else {
            return getInputLayer().getNumberOfNeurons() + getOutputLayer().getNumberOfNeurons();
        }
    }

    public ActivationFunctions getActivationFunction() {
        return activationFunction;
    }
}
