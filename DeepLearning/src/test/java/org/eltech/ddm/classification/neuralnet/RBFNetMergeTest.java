package org.eltech.ddm.classification.neuralnet;

import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningmodel.EMiningModel;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class RBFNetMergeTest {
    @Test
    public void RBFNetTest() throws MiningException {

        ClassificationFunctionSettings settings = new ClassificationFunctionSettings(new ELogicalData());
        int inNeuronsNum = 3;
        int hidNeuronsNum = 5;
        int outNeuronsNum = 2;

        RBFNeuralNetworkModel testNet = new RBFNeuralNetworkModel(settings, inNeuronsNum, hidNeuronsNum, outNeuronsNum);
        double weight1 = testNet.getOutputLayer().getNeuron(0).getConnection(0).getWeight();
        System.out.println(testNet + "\n");

        RBFNeuralNetworkModel testNet2 = new RBFNeuralNetworkModel(settings, inNeuronsNum, hidNeuronsNum, outNeuronsNum);
        double weight2 = testNet2.getOutputLayer().getNeuron(0).getConnection(0).getWeight();
        System.out.println(testNet2 + "\n");

        RBFNeuralNetworkModel testNet3 = new RBFNeuralNetworkModel(settings, inNeuronsNum, hidNeuronsNum, outNeuronsNum);
        double weight3 = testNet3.getOutputLayer().getNeuron(0).getConnection(0).getWeight();
        System.out.println(testNet3 + "\n");

        List<EMiningModel> list = new LinkedList<>();
        list.add(testNet);
        list.add(testNet2);
        list.add(testNet3);
        testNet.join(list);

        System.out.println(testNet);
//        System.out.println(testNet.getInputLayer());
//        System.out.println(testNet.getOutputLayer());
//        System.out.println("Number of neurons in X layer: " + testNet.getOutputLayer().getNumberOfNeurons());
//        System.out.println("Number of layers in the network: " + testNet.numberOfLayers());

        double mergedWeight = (weight2 + weight3) / 2;
        assertEquals(testNet.getOutputLayer().getNeuron(0).getConnection(0).getWeight(), mergedWeight, 0.001);
    }

}
