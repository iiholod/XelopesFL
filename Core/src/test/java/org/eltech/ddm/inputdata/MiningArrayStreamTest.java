package org.eltech.ddm.inputdata;

import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryProperty;

public class MiningArrayStreamTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void testRead() {
	      try {
	          // FIRST CONSTRUCTOR TYPE:
	          System.out.println("Mining array without metadata given:");

	          // Mining array for xor problem:
	          double[][] mArr = new double[4][3];
	          mArr[0][0] = 0;
	          mArr[0][1] = 1;
	          mArr[0][2] = 2.0;
	          mArr[1][0] = 0;
	          mArr[1][1] = 1;
	          mArr[1][2] = 1.0;
	          mArr[2][0] = 1;
	          mArr[2][1] = 0;
	          mArr[2][2] = 1.0;
	          mArr[3][0] = 1;
	          mArr[3][1] = 1;
	          mArr[3][2] = 0.0;

	          // Create mining array stream for numeric attributes:
	          MiningArrayStream mas = new MiningArrayStream( mArr );

	          // Show data from mining array stream:
	          ELogicalData logicalData = mas.getLogicalData();
	          System.out.println("metaData: " + logicalData.toString());
	          Assert.assertEquals(4, mas.getVectorsNumber());

	          MiningVector vec = mas.next();
	          Assert.assertEquals(0.0, vec.getValue(0), 0);
	          Assert.assertEquals(1.0, vec.getValue(1), 0);
	          Assert.assertEquals(2.0, vec.getValue(2), 0);

	          // SECOND CONSTRUCTOR TYPE:
	          System.out.println("Mining array with metadata given:");

	          // Define attributes for current array:
	          ELogicalAttribute numAtt1    = new ELogicalAttribute("numAtt1",AttributeType.numerical);
	          ELogicalAttribute numAtt2    = new ELogicalAttribute("numAtt2",AttributeType.numerical);
	          ELogicalAttribute catAtt    = new ELogicalAttribute("catAtt",AttributeType.categorical);
	          catAtt.getCategoricalProperties().addCategory("zero", CategoryProperty.valid);
	          catAtt.getCategoricalProperties().addCategory("one", CategoryProperty.valid);

	          // Define new meta data:
	          logicalData = new ELogicalData();
	          logicalData.addAttribute(numAtt1);
	          logicalData.addAttribute(numAtt2);
	          logicalData.addAttribute(catAtt);

	          // Create new mining array stream with new meta data:
	          mas = new MiningArrayStream( mArr, logicalData );

	          // Show data from mining array stream:
	          logicalData = mas.getLogicalData();
	          System.out.println("metaData: " + logicalData.toString());

	          System.out.println();

	          // THIRD CONSTRUCTOR TYPE:
	          System.out.println("Mining object array with metadata given:");

	          // Array of objects:
	          Object[][] mObArr = new Object[4][3];
	          mObArr[0][0] = new Double(0);
	          mObArr[0][1] = new Double(1);
	          mObArr[0][2] = "zero";
	          mObArr[1][0] = new Double(1);
	          mObArr[1][1] = new Double(0);
	          mObArr[1][2] = "one";
	          mObArr[2][0] = new Double(0);
	          mObArr[2][1] = new Double(1);
	          mObArr[2][2] = "one";
	          mObArr[3][0] = new Double(1);
	          mObArr[3][1] = new Double(1);
	          mObArr[3][2] = "zero";

	          // Create new mining object array stream with given meta data:
	          mas = new MiningArrayStream( mObArr, logicalData );

	          // Show data from mining array stream:
	          logicalData = mas.getLogicalData();
	          System.out.println("metaData: " + logicalData.toString());
	          Assert.assertEquals(4, mas.getVectorsNumber());

	          vec = mas.next();
	          Assert.assertEquals(0.0, vec.getValue(0), 0);
	          Assert.assertEquals(1.0, vec.getValue(1), 0);
	          Assert.assertEquals(0.0, vec.getValue(2), 0);

	          // THIRD CONSTRUCTOR TYPE WITH UNBOUNDED CATEGORIES:
	          System.out.println("Mining object array with metadata of unbounded categories:");

	          // Set unboundedCategories for categorical attribute:
	          //catAtt.setUnboundedCategories(true);

	          // Modify array of objects by adding an object not defined in meta data:
	          mObArr[2][2] = "edinica (po russkii)";

	          // Create new mining object array stream with given meta data:
	          mas = new MiningArrayStream( mObArr, logicalData);

	          // Show data from mining array stream:
	          logicalData = mas.getLogicalData();
	          System.out.println("metaData: " + logicalData.toString());
	          Assert.assertEquals(4, mas.getVectorsNumber());

	          vec = mas.next();
	          Assert.assertEquals(0.0, vec.getValue(0), 0);
	          Assert.assertEquals(1.0, vec.getValue(1), 0);
	          Assert.assertEquals(0.0, vec.getValue(2), 0);
	          vec = mas.getVector(2);
	          Assert.assertEquals(2.0, vec.getValue(2), 0);
	          Assert.assertEquals("edinica (po russkii)", vec.getValueCategory(2).getValue());

	          // FOURTH CONSTRUCTOR TYPE:
	          System.out.println("Mining array from input stream:");

	          // Create new mining array stream from previous one:
	          mas.reset();
	          MiningArrayStream mas2 = new MiningArrayStream( mas );

	          // Show data from mining array stream 2:
	          logicalData = mas2.getLogicalData();
	          Assert.assertEquals(4, mas.getVectorsNumber());

	          vec = mas.next();
	          Assert.assertEquals(0.0, vec.getValue(0), 0);
	          Assert.assertEquals(1.0, vec.getValue(1), 0);
	          Assert.assertEquals(0.0, vec.getValue(2), 0);
	          vec = mas.getVector(2);
	          Assert.assertEquals(2.0, vec.getValue(2), 0);
	          Assert.assertEquals("edinica (po russkii)", vec.getValueCategory(2).getValue());
	      }
	      catch (Exception ex) {
	        ex.printStackTrace();
	      };

	}

}
