package org.eltech.ddm.miningcore.miningmodel;

import org.eltech.ddm.miningcore.MiningException;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;


/**
 * MiningModelElement is part of mining model. It contains a pattern that is discovered in the data.
 *
 * @author iholod
 */
abstract public class MiningModelElement implements Cloneable, Serializable { // , Iterable<MiningModelElement> implements Cloneable  {
    //public static final int ID = 0;

    // Properties of this mining model's element
    //protected volatile List<Object> properties;
    private String id;

    // Set of children mining model's elements
    protected List<MiningModelElement> set;

    public MiningModelElement(String id){
//        this.properties = new ArrayList<>();
//        this.properties.add(ID, id);
        this.id= id;
    }

    public MiningModelElement() {

    }

    public MiningModelElement createNewCopyElement() {
        return new MiningModelElement(getID()) {
            @Override
            protected String propertiesToString() {
                return "";
            }

            @Override
            public void merge(List<MiningModelElement> elements) throws MiningException {}
        };
    }

    public String getID() {
        //return properties.get(ID).toString();
        return id;
    }


//    public Object get(int index){
//        return properties.get(index);
//    }
//
//    protected void set(int index, Object value){
//        this.properties.set(index, value);
//    }

    public boolean equals(Object obj) {
        return (getID().equals(((MiningModelElement)obj).getID()));
    }

    public synchronized int size(){
        if (set == null)
            return 0;
        return set.size();
    }

    public synchronized MiningModelElement getElement(int index){
        if((set == null) || (set.size() <= index ))
            return null;
        return set.get(index);
    }

    protected synchronized void add(MiningModelElement element){
        if (set == null)
            set = new ArrayList<>();

        //System.out.println(Thread.currentThread() + ": element:  " + element  );
        set.add(element);
    }

//    synchronized void insert(int pos, MiningModelElement element){
//        set.add(pos, element);
//    }

    protected synchronized void replace(int pos, MiningModelElement element){
        set.set(pos, element);
    }

    protected synchronized void remove(int index){
        set.remove(index);
    }


    /**
     * Clone mining model's element for parallel processing
     * O(n) = n where n - number of elements of this set
     * @return
     */
    @Override
    public Object clone(){
        MiningModelElement o = null;
        try {
            o = (MiningModelElement)super.clone();
            //o.changed = false;
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
        }

//        o.properties = new ArrayList<>();
//        for(int i=0; i < properties.size(); i++)
//            o.properties.add(properties.get(i));

        if(set != null){
            o.set = new ArrayList<>();
            for(MiningModelElement element : set)
                o.set.add((MiningModelElement)element.clone());
        }

        return o;
    }

    protected abstract String propertiesToString();

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();

        stringBuilder.append("<");
        stringBuilder.append("<id=" + id + propertiesToString()+">");
//        stringBuilder.append("{");
//        for(int i=0; i < properties.size(); i++)
//            stringBuilder.append(properties.get(i).toString()).append(",");
 //       stringBuilder.append("},");

        if(set!= null) {
             stringBuilder.append(",{");
             for (MiningModelElement element : set) {
                 if (element != null)
                     stringBuilder.append(element.toString()).append(";");
                 else
                     stringBuilder.append("null").append(";");
             }
            stringBuilder.append("}");
        }
        stringBuilder.append(">");

        return stringBuilder.toString();
    }

    abstract public void merge(List<MiningModelElement> elements) throws MiningException;

    /**
     *  Union source set of mining model with all sets of mining models which are built by parallel functions
     *  O(n) = n + p*n + n = (p+2)*n, where p - number of parallel functions, n - number of elements of this set
     * @param elems - sets of mining models which are built by parallel functions
     * @throws MiningException
     */
    void union(List<MiningModelElement> elems) throws MiningException {
        // 1 merge all properties of this mining element
        merge(elems);

        if(set == null) // if this element has not children elements
            return;

        // 2. ?????
        // forming lists for each element of this set
        int includingSelf = 0;
        Map<String, List<MiningModelElement>> lists = new TreeMap<>();
        for(MiningModelElement setm: elems) {
            if(this == setm) { // if this element is same setm
                includingSelf++;
                continue;
            }

            for (MiningModelElement entm : setm.set) {
                List<MiningModelElement> list = lists.get(entm.getID());
                if(list == null) {
                    list = new ArrayList<>();
                    lists.put(entm.getID(), list);
                }
                list.add(entm);
            }
        }

        List<MiningModelElement> remElem = new ArrayList<>();
        // process each element from this set
        for (MiningModelElement ent : set) {
            List<MiningModelElement> list = lists.get(ent.getID());
            lists.remove(ent.getID());
            if(list == null || (list.size() < (elems.size() - includingSelf))) { // if element was deleted by parallel function
                // save removed element
                remElem.add(ent);
            }
            else {// if element was changed (may be)
                ent.union(list);
            }
        }

        // remove removed elements
        for(MiningModelElement elmt : remElem){
            set.remove(elmt);
            System.out.println("Thread-" + Thread.currentThread().getName() + " remove " + elmt);
        }

        // add new elements
        for(List<MiningModelElement> list: lists.values()) {
            if (list.isEmpty())
                continue;

            MiningModelElement nset = list.get(0).createNewCopyElement();
            //list.remove(0);
            nset.union(list);
            add(nset);
            //System.out.println("Thread-" + Thread.currentThread().getName() + " add " + nset);
        }
    }


}
