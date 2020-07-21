package org.eltech.ddm.miningcore.miningdata.assignment;

import org.eltech.ddm.miningcore.miningdata.AttributeDataType;
import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.PhysicalAttribute;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeType;

public class AttributeValuesMapping {
	public AttributeValuesMapping(){}
	
	public ELogicalAttribute getLogicalAttribute (PhysicalAttribute physicalAttribute){
		ELogicalAttribute logicalAttribute = new ELogicalAttribute();
		logicalAttribute.setName(physicalAttribute.getName());
		AttributeDataType physicalAttributeDataType = physicalAttribute.getDataType();
		if(physicalAttributeDataType == AttributeDataType.stringType){
			logicalAttribute.setAttributeType(AttributeType.categorical);
		}
		else if(physicalAttributeDataType == AttributeDataType.doubleType){
			logicalAttribute.setAttributeType(AttributeType.numerical);
		}
		else if(physicalAttributeDataType == AttributeDataType.integerType){
			logicalAttribute.setAttributeType(AttributeType.numerical);
		}
		else if(physicalAttributeDataType == AttributeDataType.unknownType){
			logicalAttribute.setAttributeType(AttributeType.notSpecified);
		}
		return logicalAttribute;
	}
}
