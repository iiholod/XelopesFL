package org.omg.java.cwm.objectmodel.core;

import java.io.Serializable;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * A model element is an element that is an abstraction drawn from the system
 * being modeled.
 *
 *
 *
 * In the metamodel, a ModelElement is a named entity in a Model. It is the base
 * for all modeling metaclasses in the CWM. All other modeling metaclasses are
 * either direct or indirect subclasses of ModelElement.
 */
public abstract class ModelElement extends Element implements Cloneable, Serializable {

  protected Name name;

  protected VisibilityKind visibility;

  protected Dependency[] clientDependency;

  protected Constraint[] constraint;

  protected Package[] importer;

  protected Namespace namespace;

  protected Map<java.lang.String, TaggedValue> taggedValue;



  /**
   * Empty constructor.
   */
  public ModelElement()
  {
	  name = new Name();
	  visibility = new VisibilityKind();
	  taggedValue = new HashMap<>();
  }

  public java.lang.String getName() {

    return name.getString();
  }

  public void setName(java.lang.String name) {

    Name n = new Name();
    n.setString(name);
    this.name = n;
  }

  public java.lang.String getVisibility() {

    return visibility.toString();
  }

  public void setVisibility(java.lang.String visibility) {

    VisibilityKind vk = new VisibilityKind();
    //vk.setString(visibility);
    this.visibility = vk;
  }

  public Collection getClientDependency() {

    return null;//CWMTools.ArrayToList(clientDependency);
  }

  public void setClientDependency(Collection clientDependency) {

    this.clientDependency = new Dependency[ clientDependency.size() ];
    Iterator it = clientDependency.iterator();
    for (int i = 0; i < clientDependency.size(); i++)
      this.clientDependency[i] = (Dependency) it.next();
  }

  public void addClientDependency( Dependency input) {

    int size = (clientDependency == null) ? 0 : clientDependency.length;
    Dependency[] oldData = clientDependency;
    clientDependency = new Dependency[size+1];
    if (size > 0) System.arraycopy(oldData, 0, clientDependency, 0, size);
    clientDependency[size] = (Dependency) input;
  }

  public void removeClientDependency( Dependency input) {

    int size = (clientDependency == null) ? 0 : clientDependency.length;
    if (size == 0)
      return;

    int ipos = -1;
    for (int i = 0; i < size; i++)
      if (clientDependency[i].equals(input)) {
        ipos = i;
        break;
      }
    if (ipos == -1)
      return;

    Dependency[] oldData = clientDependency;
    clientDependency = new Dependency[size-1];
    for (int i = 0; i < ipos; i++)
      clientDependency[i] = oldData[i];
    for (int i = ipos+1; i < size; i++)
      clientDependency[i-1] = oldData[i];
  }

  public Collection getConstraint() {

    return null;//CWMTools.ArrayToList(constraint);
  }

  public void setConstraint(Collection constraint) {

    this.constraint = new Constraint[ constraint.size() ];
    Iterator it = constraint.iterator();
    for (int i = 0; i < constraint.size(); i++)
      this.constraint[i] = (Constraint) it.next();
  }

  public void addConstraint(Constraint input) {

    int size = (constraint == null) ? 0 : constraint.length;
    Constraint[] oldData = constraint;
    constraint = new Constraint[size+1];
    if (size > 0) System.arraycopy(oldData, 0, constraint, 0, size);
    constraint[size] = (Constraint) input;
  }

  public void removeConstraint(Constraint input)  {

    int size = (constraint == null) ? 0 : constraint.length;
    if (size == 0)
      return;

    int ipos = -1;
    for (int i = 0; i < size; i++)
      if (constraint[i].equals(input)) {
        ipos = i;
        break;
      }
    if (ipos == -1)
      return;

    Constraint[] oldData = constraint;
    constraint = new Constraint[size-1];
    for (int i = 0; i < ipos; i++)
      constraint[i] = oldData[i];
    for (int i = ipos+1; i < size; i++)
      constraint[i-1] = oldData[i];
  }

  public Collection getImporter() {

    return null;//CWMTools.ArrayToList(importer);
  }

  public void setImporter(Collection importer) {

    this.importer = new Package[ importer.size() ];
    Iterator it = importer.iterator();
    for (int i = 0; i < importer.size(); i++)
      this.importer[i] = (Package) it.next();
  }

  public void addImporter(Package input) {

    int size = (importer == null) ? 0 : importer.length;
    Package[] oldData = importer;
    importer = new Package[size+1];
    if (size > 0) System.arraycopy(oldData, 0, importer, 0, size);
    importer[size] = (Package) input;
  }

  public void removeImporter(Package input) {

    int size = (importer == null) ? 0 : importer.length;
    if (size == 0)
      return;

    int ipos = -1;
    for (int i = 0; i < size; i++)
      if (importer[i].equals(input)) {
        ipos = i;
        break;
      }
    if (ipos == -1)
      return;

    Package[] oldData = importer;
    importer = new Package[size-1];
    for (int i = 0; i < ipos; i++)
      importer[i] = oldData[i];
    for (int i = ipos+1; i < size; i++)
      importer[i-1] = oldData[i];
  }

  public Namespace getNamespace() {

    return namespace;
  }

  public void setNamespace(Namespace namespace) {

    this.namespace = (Namespace) namespace;
  }
  
  public Object clone() {
		ModelElement o = null;
		o = (ModelElement) super.clone();

		if (name != null)
			o.name = (Name) name.clone();
		
		if (visibility != null)
			o.visibility = (VisibilityKind) visibility.clone();

		if (clientDependency != null) {
			Dependency[] d = new Dependency[clientDependency.length];
			for (int i = 0; i < clientDependency.length; i++)
				d[i] = (Dependency) clientDependency[i].clone();
			o.clientDependency = d;
		}

		if (constraint != null) {
			Constraint[] c = new Constraint[constraint.length];
			for (int i = 0; i < constraint.length; i++)
				c[i] = (Constraint) constraint[i].clone();
			o.constraint = c;
		}

		if (importer != null) {
			Package[] p = new Package[importer.length];
			for (int i = 0; i < importer.length; i++)
				p[i] = (Package) importer[i].clone();
			o.importer = p;
		}

		if(namespace != null)
			o.namespace = (Namespace) namespace.clone();

		if (taggedValue != null) {
			Map<java.lang.String, TaggedValue> t = new HashMap<java.lang.String, TaggedValue>(taggedValue.size());
			for (java.lang.String tg: taggedValue.keySet()){
				TaggedValue ctv = (TaggedValue) taggedValue.get(tg).clone();
				ctv.modelElement = o;
				t.put(tg, ctv);
			}
			o.taggedValue = t;
		}

		return o;
	}
  
  public Map<java.lang.String, TaggedValue> getTaggedValues(){
	  return taggedValue;
  }
  
  protected void addTaggedValue(java.lang.String name, java.lang.String value, java.lang.String type){
	  if(taggedValue != null){
		  TaggedValue tg = new TaggedValue();
		  tg.tag = new Name(name);
		  tg.value = new String(value);
		  tg.modelElement = this;
		  tg.stereotype = new Stereotype(type);
		  taggedValue.put(name, tg);
	  }
  }
  
  protected void setTaggedValue(java.lang.String name, java.lang.String value){
	  if(taggedValue != null){
		  TaggedValue tv = taggedValue.get(name);
		  if(tv != null)
			  tv.value = new String(value);
	  }
  }

  
  protected java.lang.String getTaggedValue(java.lang.String name){
	  TaggedValue tv = taggedValue.get(name);
	  if(tv != null)
		  return tv.value.getString();
	  else 
		  return null;
  }
  
}