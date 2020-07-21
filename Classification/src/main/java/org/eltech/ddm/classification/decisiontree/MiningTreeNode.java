package org.eltech.ddm.classification.decisiontree;


import java.util.Arrays;
import java.util.Enumeration;
import java.util.Vector;

/**
 * Node of a tree structure.
 * This means that any node may contain multiple children and one parent. <p>
 *
 * From PDM CWM extension.
 *
 * @see
 */
public class MiningTreeNode
{
    // -----------------------------------------------------------------------
    //  Variables definitions
    // -----------------------------------------------------------------------
    /** Parent node. */
    public MiningTreeNode parent;

    /** Is node root node? */
    public boolean root;

    /** Is node leaf node? */
    public boolean leaf;

    /** Level of node. */
    public int level;

    /** Children of node. */
    public MiningTreeNode[] children;

    // -----------------------------------------------------------------------
    //  Methods of node manipulation
    // -----------------------------------------------------------------------
    /**
     * Get number of childs.
     *
     * @return number of childs
     */
    public int getChildCount()
    {
      return ( children != null ) ? children.length : 0;
    }

    /**
     * Get child node at specified index.
     *
     * @param childIndex index of child
     * @return child at specified index
     */
    public MiningTreeNode getChildAt( int childIndex )
    {
        return ( children != null ) ? children[childIndex] : null;
    }

    /**
     * Return list of all children
     *
     * @return all children
     */
    public Enumeration children()
    {
        if( children != null )
        {
            Vector v = new Vector( Arrays.asList( children ) );
            return v.elements();
        }
        else
        {
            return null;
        }
    }

    /**
     * Get index of child node.
     *
     * @param node child node
     * @return index of child node, -1 if not found
     */
    public int getIndex( MiningTreeNode node )
    {
        int index = -1;
        if( children == null )
        {
            index = -1;
        }
        else
        {
            for( int i = 0; i < children.length; i++ )
            {
                if( children[i].equals( node ) )
                {
                    index = i;
                }
            }
        }
        return index;
    }

    /**
     * Adds new child to node.
     *
     * @param child new child to add
     */
    public void addChild(MiningTreeNode child)
    {
      if(children == null) children = new MiningTreeNode[0];
      MiningTreeNode[] childs = new MiningTreeNode[children.length+1];
      System.arraycopy(children,0,childs,0,children.length);
      childs[children.length] = child;
      children = childs;
      leaf = false;
    }

    /**
     * Remove child from node.
     *
     * @param child child to be removed
     */
    public void removeChild(MiningTreeNode child)
    {
      if(children == null) return;
      int index = getIndex(child);
      if(index != -1)
      {
        removeChild(index);
      }
    }

    /**
     * Remove child of given index.
     *
     * @param childIndex index of child to be removed
     */
    public void removeChild(int childIndex)
    {
        MiningTreeNode[] childs = new MiningTreeNode[children.length-1];
        System.arraycopy(children,0,childs,0,childIndex);
        System.arraycopy(children,childIndex+1,childs,childIndex,children.length-childIndex-1);
        children = childs;
        if(children.length == 0) leaf = true;
    }

    /**
     * Remove all children.
     */
    public void removeAllChildren()
    {
      children = null;
      leaf = true;
    }

    /**
     * Calculates total number of all children by recursive call.
     *
     * @return number of all children
     */
    public int getTotalNumberOfChildren() {

      int nchild = getChildCount();
      for (int i = 0; i < getChildCount(); i++) {
        nchild = nchild + getChildAt(i).getTotalNumberOfChildren();
      };

      return nchild;
    }

    /**
     * Return parent node.
     *
     * @return parent node
     */
    public MiningTreeNode getParent()
    {
        return parent;
    }

    /**
     * Set parent node.
     *
     * @param parent parent node to attributes
     */
    public void setParent( MiningTreeNode parent )
    {
        this.parent = parent;
    }

    /**
     * Calculates total number of all parents by recursive call.
     * Of course, this should return the same result as
     * the method getLevel.
     *
     * @return number of all parents
     */
    public int getTotalNumberOfParents() {

      int nparent = 0;
      if (getParent() != null)
        nparent = nparent + 1 + getParent().getTotalNumberOfParents();

      return nparent;
    }

    /**
     * Is node a leaf?
     *
     * @return true, if leaf, elso false
     */
    public boolean isLeaf()
    {
        return leaf;
    }

    /**
     * Set node leaf status.
     *
     * @param leaf true if node is leaf, else false
     */
    public void setLeaf( boolean leaf )
    {
        this.leaf = leaf;
    }

    /**
     * Is node a root?
     *
     * @return true, if root, elso false
     */
    public boolean isRoot()
    {
        return root;
    }

    /**
     * Set node root status.
     *
     * @param root true if node is root, else false
     */
    public void setRoot( boolean root )
    {
        this.root = root;
    }

    /**
     * Return hierarchy level (root level = 0).
     *
     * @return level of node
     */
    public int getLevel()
    {
        return level;
    }

    /**
     * Set level (root level = 0).
     *
     * @param lev node's level
     */
    public void setLevel( int lev )
    {
        this.level = lev;
    }
}