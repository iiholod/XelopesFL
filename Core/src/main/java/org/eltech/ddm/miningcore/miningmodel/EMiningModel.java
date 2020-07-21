package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.MiningErrorCode;
import org.eltech.ddm.miningcore.MiningException;
import org.eltech.ddm.miningcore.miningfunctionsettings.EMiningFunctionSettings;
import org.omg.java.cwm.analysis.datamining.miningcore.miningmodel.MiningModel;

import java.util.*;


/**
 * CWM Class
 * <p>
 * A MiningModel holds the metadata of the result of a mining (training) task. This
 * information is sufficient to determine whether a model can be applied to a given data.
 *
 * @author Ivan Holod
 */
public abstract class EMiningModel extends MiningModel implements Cloneable  // Model
{
    public static final int CURRENT_ELEMENT = -1;

    static public final int ATTRIBUTE_SET = 0;

    //static public final MiningModelIndex INDEX_ATTRIBUTE_SET = new MiningModelIndex (ATTRIBUTE_SET);
    // Index of current mining vector in input mining stream
    static private final int[] INDEX_VECTOR_SET = {-1};
    static public final int[] INDEX_ATTRIBUTE_SET = {ATTRIBUTE_SET};
    static public final int[] INDEX_CURRENT_ATTRIBUTE = {ATTRIBUTE_SET, CURRENT_ELEMENT};
    static public final int[] INDEX_CURRENT_ATTRIBUTE_CURRENT_VALUE = {ATTRIBUTE_SET, CURRENT_ELEMENT, CURRENT_ELEMENT};

//	static public final MiningModelIndex INDEX_CURRENT_ATTRIBUTE = new MiningModelIndex (ATTRIBUTE_SET, MiningModelIndex.CURRENT_ELEMENT);
//	static public final MiningModelIndex INDEX_CURRENT_ATTRIBUTE_VALUE_SET =
//			                                    new MiningModelIndex (ATTRIBUTE_SET, MiningModelIndex.CURRENT_ELEMENT);
//	static public final MiningModelIndex INDEX_CURRENT_ATTRIBUTE_CURRENT_VALUE =
//				new MiningModelIndex (ATTRIBUTE_SET, MiningModelIndex.CURRENT_ELEMENT, MiningModelIndex.CURRENT_ELEMENT);

    // Array (forest) of all mining model's trees
    protected ArrayList<MiningModelElement> sets;

    // Map of current elements indexes
    private Map<String, Integer> currents;

    // Number of  mining vectors in input mining stream
    private int numberVectors = 0;

    // Added features for JDMAPI
    /**
     * Name of the application that generated this model
     */
    private String applicationName;

    /**
     * Time in seconds that was taken to build the model
     */
    private Integer buildDuration = null;

    /**
     * Major version of JDM by which the model was built.
     */
    private String majorVersion;

    /**
     * Minor version of JDM by which the model was built.
     */
    private String minorVersion;

    /**
     * Identifier of the task used to build or import the model
     */
    private String taskID;

    /**
     * The name of the JDM provider, i.e., the vendor, that built the model.
     */
    private String providerName;

    private String providerVersion;

    public static int[] index(int... index) {
        return index;
    }

    public EMiningModel() {

    }

    public EMiningModel(EMiningFunctionSettings settings) throws MiningException {
        this.settings = settings;
        currents = new HashMap<>();
        sets = new ArrayList<>();
        sets.add(ATTRIBUTE_SET, new LogicalDataElement(settings.getLogicalData().getName(), settings.getLogicalData()) {
            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {

            }
        });

    }


    public abstract void initModel() throws MiningException;

//	/**
//	 * Returns the name of the application that generated this model. Refer to BuildTask.
//	 * @return
//	 */
//	public String getApplicationName() {
//		return applicationName;
//	}
//
//	/**
//	 * @param applicationName the applicationName to attributes
//	 */
//	public void setApplicationName(String applicationName) {
//		this.applicationName = applicationName;
//	}
//
//	/**
//	 * Returns the time in seconds that was taken to build the model.
//	 * Returns null if this feature is not supported. .
//	 * @return
//	 */
//	public Integer getBuildDuration() {
//		return buildDuration;
//	}
//
//	/**
//	 * @param buildDuration the buildDuration to attributes
//	 */
//	public void setBuildDuration(Integer buildDuration) {
//		this.buildDuration = buildDuration;
//	}
//
//	/**
//	 * Returns the build settings used to build the model, as specified by the user.
//	 * If the model was imported, it is possible that the settings may be null.
//	 * Note! This method uses instead JDMAPI method getBuildSettings
//	 * @return
//	 */
//	public EMiningFunctionSettings getMiningSettings() {
//		return (EMiningFunctionSettings)settings;
//	}
//
//	/**
//	 * @param settings the settings to attributes
//	 */
//	public void setMiningSettings(EMiningFunctionSettings settings) {
//		this.settings = settings;
//	}
//
//	/**
//	 * Returns the build settings that was actually used by the DME (with systemDefault
//	 * and systemDetermined replaced with actual values). The inclusion of LogicalData is vendor
//	 * specific.If the model signature contains a subset of active attributes, the effective
//	 * settings needs to contain the original logical data so that the information is not lost.
//	 * If model was imported, it is possible that the settings may be null.
//	 * @return
//	 */
//	public EMiningFunctionSettings getEffectiveBuildSettings() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//	/**
//	 * Returns the major version of JDM by which the model was built.
//	 * @return
//	 */
//	public String getMajorVersion() {
//		return majorVersion;
//	}
//
//	/**
//	 * Returns the type of the mining algorithm used to build the model.
//	 * @return
//	 */
//	public MiningAlgorithm getMiningAlgorithm() {
//		// TODO Auto-generated method stub
//		return null;
//	}
//
//
//	/**
//	 * Returns the minor version of JDM by which the model was built.
//	 * @return
//	 */
//	public String getMinorVersion() {
//		// TODO Auto-generated method stub
//		return minorVersion;
//	}
//
//	/**
//	 * Returns the name of the JDM provider, i.e., the vendor, that built the model.
//	 * @return
//	 */
//	public String getProviderName() {
//		return providerName;
//	}
//
//	/**
//	 * Returns the version number of the JDM system provided by a vendor that was
//	 * used to build the model.
//	 * @return
//	 */
//	public String getProviderVersion() {
//		return providerVersion;
//	}
//
//	/**
//	 * Returns the model signature (if any), i.e., the attributes of
//	 * required inputs for applying the model, or as was used for building the model.
//	 * @return
//	 */
//	public EModelSignature getSignature() {
//		return (EModelSignature)modelSignature;
//	}
//
//	/**
//	 * @param modelSignature the modelSignature to attributes
//	 */
//	public void setSignature(EModelSignature modelSignature) {
//		this.modelSignature = modelSignature;
//	}
//
//	/**
//	 * Returns the identifier of the task used to build or import the model.
//	 * If the model was imported, this may be null.
//	 * @return
//	 */
//	public String getTaskIdentifier() {
//		return taskID;
//	}
//
//	/**
//	 * Returns a string that can be used to uniquely reference the JDM model.
//	 * Application and vendor requirements determine how unique this identifier must be.
//	 * This is distinct from the identifier returned from MiningObject.getObjectIdentifier.
//	 * @return
//	 */
//	public String getUniqueIdentifier() {
//		// TODO Auto-generated method stub
//		return keyValue.toString();
//	}
//
//	/**
//	 * Returns the JDM version by which the model was built.
//	 * @return
//	 */
//	public String getVersion() {
//		return majorVersion + "." + minorVersion;
//	}
//
//	/**
//	 * @param algorithmName the algorithmName to attributes
//	 */
//	public void setAlgorithmName(String algorithmName) {
//		this.algorithmName.setString(algorithmName);
//	}
//
//	/**
//	 * @return the algorithmName
//	 */
//	public String getAlgorithmName() {
//		return algorithmName.getString();
//	}


    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        for (MiningModelElement set : sets) {
            if (set != null)
                stringBuilder.append(set.toString());
            else
                stringBuilder.append("null");
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    public EMiningModel share() {
        EMiningModel m = (EMiningModel) super.clone();

        m.settings = settings;
        m.modelSignature = modelSignature;
        m.keyAttribute = keyAttribute;
        m.sets = sets;

        if (currents != null) {
            m.currents = new HashMap<>();
            for (String key : currents.keySet())
                m.currents.put(key, currents.get(key));
        }
        return m;
    }

    public Object clone() {

        EMiningModel o = share();
//		o = (EMiningModel) super.clone();
//
//		o.settings = settings;
//		o.modelSignature = modelSignature;
//		o.keyAttribute = keyAttribute;
//
//		if(currents!= null){
//			for(String key: currents.keySet())
//				o.currents.put(key, currents.get(key);
//		}

        if (sets != null) {
            o.sets = new ArrayList<MiningModelElement>();
            for (MiningModelElement set : sets)
                o.sets.add((MiningModelElement) set.clone());
        }

        return o;
    }

    public void join(List<EMiningModel> models) throws MiningException {
        for (int i = 1; i < sets.size(); i++) { // first set are not joined
            List<MiningModelElement> modelSets = new ArrayList<>();
            for (EMiningModel m : models) {
                modelSets.add(m.sets.get(i));
            }
            sets.get(i).union(modelSets);
        }
    }

    public MiningModelElement getParentElement(int[] index) throws MiningException {
        MiningModelElement elem = sets.get(index[0]);

        for (int i = 1; i < index.length - 1; i++) {
            int pos = index[i];
//			if ((pos > elem.size())) {
//				throw new MiningException(MiningErrorCode.INVALID_INDEX);
//			}
//			else
            if (pos == CURRENT_ELEMENT) {
                pos = currents.get(Arrays.toString(Arrays.copyOf(index, i)));
            }
            elem = elem.getElement(pos);
        }

        return elem;
    }

    public MiningModelElement getElement(int[] index) throws MiningException {
        if (index.length == 1)
            return sets.get(index[0]);

        MiningModelElement elem = getParentElement(index);
        if (elem == null) {
            throw new MiningException(MiningErrorCode.INVALID_INDEX, "For index " + Arrays.toString(index) + " a mining element is absent.");
        }
        int pos = index[index.length - 1];
        if (pos == CURRENT_ELEMENT) {
            pos = currents.get(Arrays.toString(Arrays.copyOf(index, index.length - 1)));
        }

        return elem.getElement(pos);
    }

    //	public void insertElement(MiningModelIndex index, int pos, MiningModelElement element) throws MiningException {
//
//		MiningModelElement elem = getElement(index);
//		elem.insert(pos, element);
//	}
    public void addTree(int pos, MiningModelElement tree) throws MiningException {
        addElement(null, tree);
    }


    public void addElement(int[] indexSet, MiningModelElement element) throws MiningException {
        if (indexSet == null)
            sets.add(element);
        else {
            MiningModelElement set = getElement(indexSet);
            set.add(element);
            //currents.put(Arrays.toString(indexSet), set.size() - 1);
        }
    }

    public void removeElement(int[] index) throws MiningException {
        if (index.length == 1)
            sets.remove(index[0]);
        else {
            MiningModelElement elem = getParentElement(index);
            elem.remove(index[index.length - 1]);
        }
    }

    public void setElement(int[] index, MiningModelElement element) throws MiningException {
        if (index.length == 1)
            sets.set(index[0], element);
        else {
            MiningModelElement set = getParentElement(index);
            set.replace(index[index.length - 1], element);
            //currents.put(Arrays.toString(index), set.size() - 1);
        }
    }

//	public void setProperty(int[] index, int indexProperty, Object valuePropery) throws MiningException {
//		MiningModelElement element = getElement(index);
//		element.set(indexProperty, valuePropery);
//	}
//
//	public Object getProperty(int[] index, int indexProperty) throws MiningException {
//		return getElement(index).get(indexProperty);
//
//	}

    /**
     * Set index of current element in position pos
     *
     * @param indexParent - index of parent element
     * @param pos         - position for current element
     * @throws MiningException
     */
    public void setCurrentElement(int[] indexParent, int pos) throws MiningException {
        currents.put(Arrays.toString(indexParent), pos);
    }

//	public MiningModelElement getCurrElement(int[] indexSet) throws MiningException {
//		int pos = currents.get(Arrays.toString(indexSet));
//		MiningModelElement elem = getElement(indexSet);
//		return elem.getElement(pos);
//	}

    public MiningModelElement nextCurrElement(int[] indexSet) throws MiningException {
        int pos = currents.get(Arrays.toString(indexSet));
        MiningModelElement set = getElement(indexSet);
        MiningModelElement elem = set.getElement(pos);
        currents.put(Arrays.toString(indexSet), pos + 1);
        return elem;
    }

    public int getCurrentElementIndex(int[] indexSet) throws MiningException {
        return currents.get(Arrays.toString(indexSet));
    }

    public boolean currIsLastElement(int[] indexSet) throws MiningException {
        MiningModelElement elem = getElement(indexSet);
        int curr = currents.get(Arrays.toString(indexSet));
        return (curr >= elem.size());
    }

    public int getCurrentVectorIndex() {
        return currents.get(Arrays.toString(INDEX_VECTOR_SET));
    }

    public void setCurrentVector(int currentVector) {
        currents.put(Arrays.toString(INDEX_VECTOR_SET), currentVector);
    }

//	public int getNumberVectors() {
//		return numberVectors;
//	}
//
//	public void setNumberVectors(int vectorsNumber) {
//		numberVectors = vectorsNumber;
//	}

    public LogicalAttributeElement getCurrentAttribute() throws MiningException {
        return (LogicalAttributeElement) getElement(EMiningModel.INDEX_CURRENT_ATTRIBUTE);
    }

    public int getCurrentAttributeIndex() {
        Integer index = currents.get(Arrays.toString(INDEX_ATTRIBUTE_SET));
        if (index == null)
            return -1;
        return index;
    }

    public LogicalAttributeValueElement getCurrentAttributeValue() throws MiningException {
        return (LogicalAttributeValueElement) getElement(EMiningModel.INDEX_CURRENT_ATTRIBUTE_CURRENT_VALUE);
    }

    public int getCurrentAttributeValueIndex() throws MiningException {
        Integer index = currents.get(Arrays.toString(INDEX_CURRENT_ATTRIBUTE));
        if (index == null)
            return -1;
        return index;
    }


}