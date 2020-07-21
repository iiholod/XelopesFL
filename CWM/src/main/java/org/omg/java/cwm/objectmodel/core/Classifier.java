package org.omg.java.cwm.objectmodel.core;

/**
 * 
 * @author push_king
 */
public abstract class Classifier extends Namespace implements Cloneable {
	/*
	 * {author=push_king , src_lang=Java}
	 */

	public Boolean isAbstract;

	public Feature[] feature;

	public Object clone() {
		Classifier o = null;
		o = (Classifier) super.clone();

		if (feature != null) {
			Feature[] f = new Feature[feature.length];
			for (int i = 0; i < feature.length; i++) {
				f[i] = (Feature) feature[i].clone();
			}
			o.feature = f;
		}

		return o;
	}

}