package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.ClassificationMiningModel;
import org.eltech.ddm.classification.neuralnet.ActivationFunction.ActivationFunctions;
import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;

public abstract class NeuralNetworkModel extends ClassificationMiningModel{

    private final static int[] NEURAL_INPUT = {1};
    private final static int[] NEURAL_OUTPUT = {2};
    protected ActivationFunctions activationFunction;

    public NeuralNetworkModel(
            EMiningFunctionSettings settings)
            throws MiningException {
        super(settings);

        sets.add(NEURAL_INPUT[0], new NeuralLayer("NeuralInput"));
        sets.add(NEURAL_OUTPUT[0], new NeuralLayer("NeuralOutput"));
    }


    @Override
    public void initModel() throws MiningException {

    }

    @Override
    public double apply(MiningVector miningVector) throws MiningException {
        return 0;
    }

    public NeuralLayer getInputLayer() throws MiningException {
        return (NeuralLayer)getElement(NeuralNetworkModel.NEURAL_INPUT);
    }

    public void setInputLayer(NeuralLayer layer) throws MiningException {
        setElement(NeuralNetworkModel.NEURAL_INPUT, layer);
    }

    public NeuralLayer getOutputLayer() throws MiningException {
        return (NeuralLayer)getElement(NeuralNetworkModel.NEURAL_OUTPUT);
    }

    public void setOutputLayer(NeuralLayer layer) throws MiningException {
        setElement(NeuralNetworkModel.NEURAL_OUTPUT, layer);
    }

    protected abstract void connectLayers() throws MiningException;

    public abstract int numberOfLayers();

    public abstract int numberOfNeurons() throws MiningException;

    public ActivationFunctions getActivationFunction() {
        return activationFunction;
    }

    @Override
    public Object clone() {
        return super.clone();
    }
}
