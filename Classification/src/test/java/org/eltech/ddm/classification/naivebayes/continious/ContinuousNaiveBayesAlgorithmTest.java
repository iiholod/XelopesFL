package org.eltech.ddm.classification.naivebayes.continious;

import com.univocity.parsers.csv.CsvParserSettings;
import org.eltech.ddm.classification.ClassificationFunctionSettings;
import org.eltech.ddm.environment.ConcurrentCSVExecutionEnvironment;
import org.eltech.ddm.inputdata.file.common.FileSeparator;
import org.eltech.ddm.inputdata.file.csv.CsvFileSeparator;
import org.eltech.ddm.inputdata.file.csv.MiningCsvStream;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.algorithms.MiningAlgorithm;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningAlgorithmSettings;
import org.eltech.ddm.miningcore.miningtask.EMiningBuildTask;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

public class ContinuousNaiveBayesAlgorithmTest {

    private static final String ALGO_NAME = "NaiveBayesAlgorithm";
    private static final String ALGO_PATH = "org.eltech.ddm.classification.naivebayes.continious.ContinuousNaiveBayesAlgorithmTest";

    private static final String[] TEST_FILES =
            {"E:\\data\\data\\test_1.csv", "E:\\data\\data\\test_2.csv"};

    private static final int HANDLERS_NUMBER = 3;

    protected MiningCsvStream inputData;
    private ClassificationFunctionSettings miningSettings;


    @Before
    public void setUp() throws Exception {
        CsvParserSettings settings = getCsvParserSettings();
        this.inputData = new MiningCsvStream("E:\\data\\data\\test.csv", settings, true);
    }

    private CsvParserSettings getCsvParserSettings() {
        CsvParserSettings settings = new CsvParserSettings();
        settings.setDelimiterDetectionEnabled(true);
        settings.setHeaderExtractionEnabled(true);
        return settings;
    }


    @Test
    public void testSeparation() throws FileNotFoundException {
        FileSeparator<MiningCsvStream> separator = new CsvFileSeparator();
        List<MiningCsvStream> separate = separator.separate("diabet-data.csv", 2);
        Assert.assertEquals(separate.size(), 2);
    }

    @Test
    public void test() throws MiningException {
        createMiningSettings();
        for (String fileName : TEST_FILES) {
            ContinuousBayesModel resultModel = (ContinuousBayesModel) createBuidTask(fileName).execute();
            verifyModel(resultModel);
        }

    }

    private EMiningBuildTask createBuidTask(String targetFile) throws MiningException {
        MiningAlgorithm algorithm = new ContinuousNaiveBayesAlgorithm(miningSettings);
        ConcurrentCSVExecutionEnvironment environment = new ConcurrentCSVExecutionEnvironment(targetFile, HANDLERS_NUMBER);

        EMiningBuildTask buildTask = new EMiningBuildTask();
        buildTask.setMiningAlgorithm(algorithm);
        buildTask.setMiningSettings(miningSettings);
        buildTask.setExecutionEnvironment(environment);
        return buildTask;
    }

    protected void verifyModel(ContinuousBayesModel model) throws MiningException {
//        MiningCsvStream stream = new MiningCsvStream("diabet-data.csv", getCsvParserSettings());
//        MiningVector current = stream.next();
//        while (current != null) {
////            Map<Double, BigDecimal> result = model.apply(current.getValues());
////            System.out.println(result);
////            System.out.println(predictions(result));
//            current = stream.next();
//        }
    }

    private Double predictions(Map<Double, BigDecimal> probabilityList) {
        return probabilityList.entrySet().stream().max(Comparator.comparing(Map.Entry::getValue)).get().getKey();
    }

    private void createMiningSettings() throws MiningException {
        ELogicalData logicalData = inputData.getLogicalData();
        ELogicalAttribute targetAttribute = logicalData.getAttribute("res");

        EMiningAlgorithmSettings algorithmSettings = new EMiningAlgorithmSettings();
        algorithmSettings.setName(ALGO_NAME);
        algorithmSettings.setClassname(ALGO_PATH);

        miningSettings = new ClassificationFunctionSettings(logicalData);
        miningSettings.setTarget(targetAttribute);
        miningSettings.setAlgorithmSettings(algorithmSettings);
        miningSettings.verify();
    }


}