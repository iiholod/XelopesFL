package org.omg.java.cwm.analysis.datamining.miningcore.miningfunctionsettings;

import org.omg.java.cwm.objectmodel.core.ModelElement;

/** 
 *  A mining algorithm settings object captures the parameters associated with a particular
 *  algorithm. It allows a knowledgeable user to fine tune algorithm parameters. Generally,
 *  not all parameters must be specified, however, those specified are taken into account by
 *  the underlying data mining system.
 *  
 *  Separating mining algorithm from mining function provides a natural and convenient
 *  separation for those users experienced with data mining algorithms and those only
 *  familiar with mining functions.
 *  
 *  @author Ivan Holod
 */
public abstract class MiningAlgorithmSettings extends ModelElement {


}