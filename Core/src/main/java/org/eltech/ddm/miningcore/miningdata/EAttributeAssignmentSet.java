package org.eltech.ddm.miningcore.miningdata;

import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignment;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.AttributeAssignmentSet;

import java.util.ArrayList;

/**
 * CWM Class
 *
 * This object contains a attributes of AttributeAssignment objects and completes attribute
 * assignment for a mining operation.
 *
 * @author Ivan Holod
 *
 */
public class EAttributeAssignmentSet extends AttributeAssignmentSet implements Cloneable {

	public EAttributeAssignmentSet(){
		assignment = new ArrayList<AttributeAssignment>();
	}

	public void addAssignment(AttributeAssignment newElement) {
		assignment.add(newElement);
	}

	public void modifyAssignment(AttributeAssignment oldElement, AttributeAssignment newElement) {
		int index;
		index = assignment.indexOf(oldElement);
		if(index >= 0)
			assignment.add(index, newElement);
	}

	public void removeAssignment(AttributeAssignment oldElement) {
		int index;
		index = assignment.indexOf(oldElement);
		if(index >= 0)
			assignment.remove(index);
	}

	public void setAssignment(AttributeAssignment newValue) {
		assignment.add(newValue);
	}

	public AttributeAssignment getAttributeAssignment(int index)
	{
		return (AttributeAssignment)assignment.get(index);
	}

	public int getSize()
	{
		return assignment.size();
	}

	public Object clone() {
		EAttributeAssignmentSet o = null;
	    o = (EAttributeAssignmentSet)super.clone();

	    if (assignment != null) {
			ArrayList<AttributeAssignment> mm = new ArrayList<AttributeAssignment>();
			for (AttributeAssignment ml : assignment)
				mm.add((AttributeAssignment) ml.clone());
			o.assignment = mm;
		}

	    return o;
	}
	
	public void removeAllAssignments() {
		assignment.clear();
	}
	
}