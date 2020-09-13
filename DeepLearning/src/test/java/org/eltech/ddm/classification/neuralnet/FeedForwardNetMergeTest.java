package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.classification.neuralnet.ActivationFunction.ActivationFunctions;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class FeedForwardNetMergeTest {

    /**
     * Weights in NCon
     * @throws MiningException
     */
    @Test
    public void FFNetTestWeightsMerge() throws MiningException {

        ClassificationFunctionSettings settings = new ClassificationFunctionSettings(new ELogicalData());
        int inNeuronsNum = 3;
        int outNeuronsNum = 2;

        FeedForwardNeuralNetworkModel testNet = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);
        System.out.println(testNet + "\n");
        double weight1 = testNet.getOutputLayer().getNeuron(0).getConnection(0).getWeight();

        FeedForwardNeuralNetworkModel testNet2 = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);
        System.out.println(testNet2 + "\n");
        double weight2 = testNet2.getOutputLayer().getNeuron(0).getConnection(0).getWeight();

        FeedForwardNeuralNetworkModel testNet3 = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);
        System.out.println(testNet3 + "\n");
        double weight3 = testNet3.getOutputLayer().getNeuron(0).getConnection(0).getWeight();

        FeedForwardNeuralNetworkModel testNet4 = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);
        System.out.println(testNet4 + "\n");
        double weight4 = testNet4.getOutputLayer().getNeuron(0).getConnection(0).getWeight();

        List<EMiningModel> list = new LinkedList<>();
        list.add(testNet);
        list.add(testNet2);
        list.add(testNet3);
        list.add(testNet4);
        testNet.join(list);

        System.out.println(testNet);
//        System.out.println(testNet.getInputLayer());
//        System.out.println(testNet.getOutputLayer());
//        System.out.println("Number of neurons in X layer: " + testNet.getOutputLayer().getNumberOfNeurons());
//        System.out.println("Number of layers in the network: " + testNet.numberOfLayers());

        double mergedWeight = (weight4 + weight2 + weight3) / 3;
        assertEquals(testNet.getOutputLayer().getNeuron(0).getConnection(0).getWeight(), mergedWeight, 0.001);
//        assertEquals(testNet.numberOfNeurons(), inNeuronsNum + outNeuronsNum);
    }

    /**
     * Bias is Neuron
     * @throws MiningException
     */
    @Test
    public void FFNetTestBiasMerge() throws MiningException {

        ClassificationFunctionSettings settings = new ClassificationFunctionSettings(new ELogicalData());
        int inNeuronsNum = 3;
        int outNeuronsNum = 2;

        FeedForwardNeuralNetworkModel testNet = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);
        double bias1 = 1;
        testNet.getOutputLayer().getNeuron(0).setBias(bias1);
        System.out.println(testNet + "\n");

        FeedForwardNeuralNetworkModel testNet2 = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);
        double bias2 = 0.5;
        testNet2.getOutputLayer().getNeuron(0).setBias(bias2);
        System.out.println(testNet2 + "\n");

        FeedForwardNeuralNetworkModel testNet3 = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square);
        double bias3 = 0.1;
        testNet3.getOutputLayer().getNeuron(0).setBias(bias3);
        System.out.println(testNet3 + "\n");

        List<EMiningModel> list = new LinkedList<>();
        list.add(testNet);
        list.add(testNet2);
        list.add(testNet3);

        testNet.join(list);

        System.out.println(testNet);
        double mergedBias = (bias2 + bias3) / 2;
        assertEquals(testNet.getOutputLayer().getNeuron(0).getBias(), mergedBias, 0.001);
//        assertEquals(testNet.numberOfNeurons(), inNeuronsNum + outNeuronsNum);
    }

    /**
     * NCon's Weight in multiple layer net
     * @throws MiningException
     */
    @Test
    public void FFNetTestWeightMergeMultipleLayers() throws MiningException {

        ClassificationFunctionSettings settings = new ClassificationFunctionSettings(new ELogicalData());
        int inNeuronsNum = 5;
        int outNeuronsNum = 2;
        int[] hidNeuronsNum = {3, 3, 3};

        FeedForwardNeuralNetworkModel testNet = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square, hidNeuronsNum);
        double weight1 = testNet.getOutputLayer().getNeuron(0).getConnection(0).getWeight();
        System.out.println(testNet + "\n");

        FeedForwardNeuralNetworkModel testNet2 = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square, hidNeuronsNum);
        double weight2 = testNet2.getOutputLayer().getNeuron(0).getConnection(0).getWeight();
        System.out.println(testNet2 + "\n");

        FeedForwardNeuralNetworkModel testNet3 = new FeedForwardNeuralNetworkModel(settings, inNeuronsNum, outNeuronsNum, ActivationFunctions.square, hidNeuronsNum);
        double weight3 = testNet3.getOutputLayer().getNeuron(0).getConnection(0).getWeight();
        System.out.println(testNet3 + "\n");

        List<EMiningModel> list = new LinkedList<>();
        list.add(testNet);
        list.add(testNet2);
        list.add(testNet3);
        testNet.join(list);

        System.out.println(testNet);

        double mergedWeight = (weight2 + weight3) / 2;
        assertEquals(testNet.getOutputLayer().getNeuron(0).getConnection(0).getWeight(), mergedWeight, 0.001);
    }
}
