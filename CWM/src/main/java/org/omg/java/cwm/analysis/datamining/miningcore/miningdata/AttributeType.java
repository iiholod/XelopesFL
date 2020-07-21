package org.omg.java.cwm.analysis.datamining.miningcore.miningdata;

/**
 *	CWM Enumeration
 * 
 * 	 The attribute type indicates if the attribute is categorical, ordinal, numerical, or not specified.
 * 	 If either categoricalProperties or numericalProperties are specified, a constraint exists to
 * 	 ensure the attributeType value is consistent with these attributes. This attribute allows a
 * 	 MiningAttribute to be identified with a particular type even if no additional properties are
 * 	 specified. If ordinal, then the OrdinalAttributeProperties must be specified to indicate the
 * 	 ordering of the categories.
 *
 * @author Ivan Holod
 *
 */
public enum AttributeType {
	categorical,
	numerical,
	ordinal,
	notSpecified
}
