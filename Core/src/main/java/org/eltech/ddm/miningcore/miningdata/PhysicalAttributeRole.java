/**
 * 
 */
package org.eltech.ddm.miningcore.miningdata;

/**
 * The enumeration PhysicalAttributeRole determines the role of a PhysicalAttribute. A role defines how 
 * an algorithm or mining operation will interpret the data in that attribute. 
 * 
 * @author Ivan Holod
 *
 */
public enum PhysicalAttributeRole {
	data,
	caseId,
	attributeName,
	attributeValue,
	taxonomyChildId,
	taxonomyParentId
}
