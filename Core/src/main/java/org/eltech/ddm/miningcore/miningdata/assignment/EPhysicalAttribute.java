package org.eltech.ddm.miningcore.miningdata.assignment;

import org.eltech.ddm.miningcore.miningdata.PhysicalAttribute;

public class EPhysicalAttribute extends PhysicalAttribute{
	
	//private char cloverETLFieldType;
	private int cloverETLFieldType;
	
	public EPhysicalAttribute() {
		
	}
	
	public EPhysicalAttribute(String m_name, int m_fieldType) {
		name.setString(m_name);
		cloverETLFieldType = m_fieldType;
	}
	
	public void setCloverETLFieldType(int new_cloverETLFieldType){
		cloverETLFieldType = new_cloverETLFieldType;
	}
	
	public int getCloverETLFieldType(){
		return cloverETLFieldType;
	}

}
