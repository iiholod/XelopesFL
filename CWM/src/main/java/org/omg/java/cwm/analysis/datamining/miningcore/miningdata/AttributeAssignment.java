package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

import org.omg.java.cwm.objectmodel.core.Attribute;
import org.omg.java.cwm.objectmodel.core.ModelElement;

import java.util.ArrayList;

/**
 * This object provides a mapping between a mining attribute (logical data) and
 * a set of attributes in the input data (physical data). LogicalAttribute is
 * the mining attribute being mapped by this object. OrderIdAttribute is used
 * when ordering of attributes is required. In some cases, ordering of
 * attributes is important (as in sequence analysis). In other cases, a sequence
 * of an attribute is favored over having a set-valued attribute.
 * AttributeAssignment can be reused among several tasks, but a MiningAttribute
 * can be referred to by an AttributeAssignment within a task.
 *
 * @author Ivan Holod
 *
 */
public abstract class AttributeAssignment extends ModelElement {
	
	/**
	 * This reference points to one or more mining attributes that are used to
	 * identify the order of certain sequences.
	 */
	protected ArrayList<Attribute> orderIdAttribute;

	/**
	 * This points to one or more mining attributes that are being mapped to by
	 * the AttributeAssignment object.
	 */
	protected ArrayList<MiningAttribute> logicalAttribute;

	public AttributeAssignment() {
		logicalAttribute = new ArrayList<MiningAttribute>();
		orderIdAttribute = new ArrayList<Attribute>();
	}

	public void addLogicalAttribute(MiningAttribute new_element) {
		logicalAttribute.add(new_element);
	}

	public ArrayList<MiningAttribute> getLogicalAttribute() {
		return logicalAttribute;
	}

	public Object clone() {
		AttributeAssignment o = null;
		o = (AttributeAssignment) super.clone();

		if (orderIdAttribute != null) {
			ArrayList<Attribute> mm = new ArrayList<Attribute>();
			for (Attribute ml : orderIdAttribute)
				mm.add((Attribute) ml.clone());
			o.orderIdAttribute = mm;
		}

		if (logicalAttribute != null) {
			ArrayList<MiningAttribute> mm = new ArrayList<MiningAttribute>();
			for (MiningAttribute ml : logicalAttribute)
				mm.add((MiningAttribute) ml.clone());
			o.logicalAttribute = mm;
		}

		return o;
	}

}