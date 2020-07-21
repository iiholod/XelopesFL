package org.eltech.ddm.miningcore.miningdata;

import org.eltech.ddm.miningcore.MiningException;
import org.junit.Before;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CategoryMapObjectTest {

	ECategoryMapObject cmo;
	
	ECategory c1 = new ECategory("1");
	ECategory c11 = new ECategory("1.1");
	ECategory c12 = new ECategory("1.2");
	ECategory c2 = new ECategory("2");
	ECategory c21 = new ECategory("2.1");
	ECategory c22 = new ECategory("2.2");
	ECategory c211 = new ECategory("2.1.1");
	ECategory c212 = new ECategory("2.1.2");
	ECategory c213 = new ECategory("2.1.3");
	
	@Before
	public void setUp() throws Exception {

		
		cmo = new ECategoryMapObject();
		ECategory[] childArray1 = {c11,c12};
		cmo.addChildren(c1, childArray1);
		
		ECategory[] childArray2 = {c21,c22};
		cmo.addChildren(c2, childArray2);
		
		ECategory[] childArray21 = {c211,c212,c213};
		cmo.addChildren(c21, childArray21);
	}

	@Test
	public void testGetChildrenCategory() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetLeaves() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetParents() {
		try {
			Collection col = cmo.getChildren(c1);
			assertEquals(2, col.size());
			
			Collection col2 = cmo.getChildren(c2);
			assertEquals(2, col2.size());
			
			Collection col3 = cmo.getChildren(c21);
			assertEquals(3, col3.size());
		} catch (MiningException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void testGetRoots() {
		fail("Not yet implemented");
	}

	@Test
	public void testAddChildren() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveDescendants() {
		fail("Not yet implemented");
	}

	@Test
	public void testRemoveRelationship() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetChildrenObject() {
		fail("Not yet implemented");
	}

}
