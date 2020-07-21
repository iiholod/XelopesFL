package org.eltech.ddm.miningcore.miningdata.assignment;

import org.eltech.ddm.miningcore.miningdata.ELogicalAttribute;
import org.eltech.ddm.miningcore.miningdata.PhysicalAttribute;

import java.util.ArrayList;

public class SetSetting {
	public int index;
	public ArrayList<PhysicalAttribute> e_physicalData;
	public ELogicalAttribute logicalAttribute;	
	
	public SetSetting(ArrayList<PhysicalAttribute> physicalAttribute, ELogicalAttribute new_logicalAttribute){
		e_physicalData = new ArrayList<PhysicalAttribute>();
		logicalAttribute = new ELogicalAttribute();
		e_physicalData = physicalAttribute;
		logicalAttribute = new_logicalAttribute;
	}
}
