package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.neuralnet.ActivationFunction.ActivationFunctions;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;

public class RBFNeuralNetworkModel extends NeuralNetworkModel {

    private final static int[] HIDDEN_LAYER = {3};

    public RBFNeuralNetworkModel(EMiningFunctionSettings settings, int inNeuronsNum, int hidNeuronsNum, int outNeuronsNum) throws MiningException {
        super(settings);

        setInputLayer(new NeuralLayer("NeuralInput", inNeuronsNum, ActivationFunctions.identity));
        sets.add(HIDDEN_LAYER[0], new NeuralLayer("HiddenLayer", hidNeuronsNum, ActivationFunctions.radialBasis));
        setOutputLayer(new NeuralLayer("NeuralOutput", outNeuronsNum, ActivationFunctions.identity));
        this.activationFunction = ActivationFunctions.radialBasis;
        connectLayers();
    }

    @Override
    protected void connectLayers() throws MiningException {
        getHiddenLayer().connectLayer(getInputLayer());
        getOutputLayer().connectLayer(getHiddenLayer());
    }

    public NeuralLayer getHiddenLayer() throws MiningException {
        return (NeuralLayer)getElement(RBFNeuralNetworkModel.HIDDEN_LAYER);
    }

    @Override
    public int numberOfLayers() {
        return 3;
    }

    @Override
    public int numberOfNeurons() throws MiningException {
        return getInputLayer().getNumberOfNeurons() + getHiddenLayer().getNumberOfNeurons() + getOutputLayer().getNumberOfNeurons();
    }
}
