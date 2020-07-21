package org.eltech.ddm.miningcore.miningdata.assignment;

import java.util.ArrayList;

public class AttributeVectorPool {
	public AttributeVectorPool(){
		instances = new ArrayList<double[]>(); 
		instances_row_indx = -1;
	};
	
	public ArrayList<double[]> instances;    
	public int instances_row_indx;
	public int cur_row_indx;
	
}
