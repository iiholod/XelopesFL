package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.neuralnet.ActivationFunction.ActivationFunctions;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class FeedForwardNeuralNetTest {

    @Test
    public void FFNetTestNoHiddenLayer() throws MiningException {

        ClassificationFunctionSettings settings = new ClassificationFunctionSettings(new ELogicalData());
        int inNeuronsNum = 3;
        int outNeuronsNum = 2;

        FeedForwardNeuralNetworkModel testNet = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);

        System.out.println(testNet);
//        System.out.println(testNet.getInputLayer());
//        System.out.println(testNet.getOutputLayer());
        System.out.println("Number of neurons in X layer: " + testNet.getOutputLayer().getNumberOfNeurons());
        System.out.println("Number of layers in the network: " + testNet.numberOfLayers());

        assertEquals(testNet.numberOfLayers(), 2);
        assertEquals(testNet.numberOfNeurons(), inNeuronsNum + outNeuronsNum);
    }

    @Test
    public void FFNetTestTwoHiddenLayers() throws MiningException {

        ClassificationFunctionSettings settings = new ClassificationFunctionSettings(new ELogicalData());
        int inNeuronsNum = 5;
        int outNeuronsNum = 2;
        int[] hidNeuronsNum = {3, 3, 3};

        FeedForwardNeuralNetworkModel testNet = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square, hidNeuronsNum);
//FeedforwardNeuralNet testNet2 = testNet;
//testNet.join(testNet2);
        System.out.println(testNet);
//        System.out.println(testNet.getInputLayer());
//        System.out.println(testNet.getOutputLayer());
        System.out.println("Number of layers in the network: " + testNet.numberOfLayers());
        System.out.println("Number of neurons in the network: " + testNet.numberOfNeurons());

        assertEquals(testNet.numberOfLayers(), 2 + hidNeuronsNum.length);
        assertEquals(testNet.numberOfNeurons(), inNeuronsNum + outNeuronsNum + Arrays.stream(hidNeuronsNum).sum());
    }

}
