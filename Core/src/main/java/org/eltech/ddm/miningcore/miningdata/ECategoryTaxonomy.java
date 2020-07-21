package org.eltech.ddm.miningcore.miningdata;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.Category;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryMap;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryMapTable;
import org.omg.java.cwm.analysis.datamining.miningcore.miningdata.CategoryTaxonomy;
import org.omg.java.cwm.objectmodel.core.Attribute;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

/**
 * CWM Class
 *
 * A CategoryTaxonomy supports the specification of taxonomy or category hierarchy as
 * required by data mining in the form of a directed acyclic graph. It enables two
 * representations: 1) Explicit specification of the graph through the referenced node
 * class, and 2) referencing a table with specific attributes (columns) that store the data in
 * tabular form.
 * A CategoryTaxonomy can contain multiple "root" nodes, in a sense being a single
 * representation for several possibly strict hierarchies.
 *
 *
 * @author Ivan Holod
 *
 */
public class ECategoryTaxonomy extends CategoryTaxonomy implements Cloneable {


	public void CategoryTaxonomy(){
		rootCategory = new ArrayList<Category>();
	}


	/**
	 * Returns a collection of the children of the specified category.
	 * Parent object must not be null.
	 */
	public Collection getChildren(Object parent) throws MiningException {
		if (parent == null)
			throw new MiningException(MiningErrorCode.INVALID_ARGUMENT, "Parent must not NULL.");

		ArrayList<Object> children = new ArrayList<Object>();
		for (CategoryMap map : categoryMap) {
			if (map instanceof ECategoryMapObject){
				if(parent instanceof ECategory)
					children.addAll(((ECategoryMapObject) map).getChildren((ECategory) parent));
				else
					throw new MiningException(MiningErrorCode.INVALID_ARGUMENT,  "Parent must be Category type.");
			}
			else if (map instanceof CategoryMapTable){
				if(parent instanceof Attribute){
					if(((CategoryMapTable) map).getChildAttribute().equals(parent))
						children.add(((CategoryMapTable) map).getParentAttribute());
				}else
					throw new MiningException(MiningErrorCode.INVALID_ARGUMENT,  "Parent must be Attribute type.");
			}

		}

		return children;
	}


	public Collection getLeaves() throws MiningException {
		// TODO Auto-generated method stub
		return null;
	}


	public Collection getParents(Object arg0) throws MiningException {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Returns a collection of the root categories of the taxonomy. The level of the roots is 0.
	 * If there are no roots, returns an empty collection.
	 */
	public Collection getRoots() throws MiningException {
		if(rootCategory != null)
			return (Collection) rootCategory.iterator();
		else
			return new ArrayList();
	}

	public Date getCreationDate() {
		// TODO Auto-generated method stub
		return null;
	}


	public String getCreatorInfo() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getDescription() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getObjectIdentifier() {
		// TODO Auto-generated method stub
		return null;
	}

	public String getObjectType() {
		// TODO Auto-generated method stub
		return null;
	}


	public void setDescription(String arg0) throws MiningException {
		// TODO Auto-generated method stub
	}

	public Object clone() {
		ECategoryTaxonomy o = null;
		try {
			o = (ECategoryTaxonomy) super.clone();
		} catch (CloneNotSupportedException e) {
			System.err.println(this.getClass().toString() + " can't be cloned");
		}

		if (rootCategory != null) {
			ArrayList<Category> ac = new ArrayList<Category>();
			for (Category c : rootCategory) {
				ac.add((ECategory) ((ECategory) c).clone());
			}
			o.rootCategory = ac;
		}

		if (categoryMap != null) {
			ArrayList<CategoryMap> acm = new ArrayList<CategoryMap>();
			for (CategoryMap cm : categoryMap) {
				acm.add((CategoryMap) cm.clone());
			}
			o.categoryMap = acm;
		}

		return o;
	}

}