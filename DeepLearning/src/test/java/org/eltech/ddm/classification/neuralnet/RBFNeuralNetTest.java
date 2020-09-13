package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class RBFNeuralNetTest {
    @Test
    public void RBFNetTest() throws MiningException {

        ClassificationFunctionSettings settings = new ClassificationFunctionSettings(new ELogicalData());
        int inNeuronsNum = 3;
        int hidNeuronsNum = 5;
        int outNeuronsNum = 2;

        RBFNeuralNetworkModel testNet = new RBFNeuralNetworkModel(settings, inNeuronsNum, hidNeuronsNum, outNeuronsNum);

        System.out.println(testNet);
//        System.out.println(testNet.getInputLayer());
//        System.out.println(testNet.getOutputLayer());
        System.out.println("Number of neurons in X layer: " + testNet.getOutputLayer().getNumberOfNeurons());
        System.out.println("Number of layers in the network: " + testNet.numberOfLayers());

        assertEquals(testNet.numberOfLayers(), 3);
        assertEquals(testNet.numberOfNeurons(), inNeuronsNum + hidNeuronsNum + outNeuronsNum);
    }
}
