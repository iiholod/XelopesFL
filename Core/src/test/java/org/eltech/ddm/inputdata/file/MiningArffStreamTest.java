package org.eltech.ddm.inputdata.file;

import org.eltech.ddm.inputdata.MiningVector;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningdata.AttributeDataType;
import org.eltech.ddm.miningcore.miningdata.EDirectAttributeAssignment;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.ELogicalData;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;

public class MiningArffStreamTest {
	MiningArffStream arffStream;

	@Before
	public void setUp() throws Exception {
		// Open ARFF stream:
		arffStream = new MiningArffStream("../data/arff/labor.arff");
		arffStream.open();    // can be omitted!
	}

	@Test
	public void testRead() {


		// Display physical model:
		try {
			//arffStream.findPhysicalModel();

			System.out.println("Physical model: " + arffStream.getPhysicalData());

			// Display logical model, i.e. meta data:
			ELogicalData logicaData = arffStream.getLogicalData();
			// verify meta data
			assertEquals("labor-neg-data", logicaData.getName());
			assertEquals(17, logicaData.getAttributesNumber());
			assertEquals(8, logicaData.getAttributes(AttributeType.numerical).size());
			assertEquals(9, logicaData.getAttributes(AttributeType.categorical).size());
			assertEquals(0, logicaData.getAttributes(AttributeType.ordinal).size());
			assertEquals(0, logicaData.getAttributes(AttributeType.notSpecified).size());
			// verify numerical attribute
			ELogicalAttribute lan = logicaData.getAttribute(0);
			assertEquals("duration", lan.getName());
			assertEquals(AttributeType.numerical, lan.getAttributeType());

			// verify categorial attribute
			ELogicalAttribute lac = logicaData.getAttribute(4);
			assertEquals("cost-of-living-adjustment", lac.getName());
			assertEquals(AttributeType.categorical, lac.getAttributeType());
			assertEquals(AttributeDataType.stringType, lac.getCategoricalProperties().getDataType());
			assertEquals(3, lac.getCategoricalProperties().getSize());
			Object[] a = {"none", "tcf", "tc"};
			assertArrayEquals(a, lac.getCategoricalProperties().getValues());
			assertEquals(1, lac.getCategoricalProperties().getIndex("tcf").intValue());

			System.out.println("metaData: " + logicaData);

			// Show all mining vectors:


			Assert.assertEquals(57, arffStream.getVectorsNumber());

			MiningVector mv = arffStream.next();
			Assert.assertTrue(mv.isMissing());

			Assert.assertEquals(1.0, mv.getValue(0), 0);
			Assert.assertEquals(5.0, mv.getValue(1), 0);
			Assert.assertEquals(Double.NaN, mv.getValue(2), 0);
			Assert.assertTrue(mv.isMissing(2));

			Assert.assertEquals(1.0, mv.getValue(11), 0);
			Assert.assertEquals("average", mv.getValueCategory(11).getValue());


			// verify attribute assignment (direct assignment)
			System.out.println();
			System.out.println("Attribute Assignment attributes (direct assignment): ");
			int ia = arffStream.getAttributeAssignmentSet().getSize();
			for (int i = 0; i < ia; i++) {
				System.out.println("Logical: " +
						arffStream.getAttributeAssignmentSet().getAttributeAssignment(i).getLogicalAttribute().get(0) +
						"  physical:  " +
						((EDirectAttributeAssignment) (arffStream.getAttributeAssignmentSet().getAttributeAssignment(i))).getAttribute());

				// assertEquals()
			}

			// verify removing of logacalAttribute and appropriate attributeAssignment

			String nameRemoveAtt = "contribution-to-dental-plan";
			arffStream.updateRemoveAttLogicalData(nameRemoveAtt);

			System.out.println();
			System.out.println("Attribute Assignment attributes after removing attribute '" + nameRemoveAtt + "' (direct assignment): ");
			ia = arffStream.getAttributeAssignmentSet().getSize();
			for (int i = 0; i < ia; i++) {
				System.out.println("Logical: " +
						arffStream.getAttributeAssignmentSet().getAttributeAssignment(i).getLogicalAttribute().get(0) +
						"  physical:  " +
						((EDirectAttributeAssignment) (arffStream.getAttributeAssignmentSet().getAttributeAssignment(i))).getAttribute());

				// assertEquals()
			}


			// Same result (meta data and mining vetors):
			//   System.out.println(excelStream);

			// Close stream:
			arffStream.close();


		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
