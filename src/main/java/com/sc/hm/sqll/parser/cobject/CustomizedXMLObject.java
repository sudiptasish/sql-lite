package com.sc.hm.sqll.parser.cobject;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

/**
 * Represents an XML node.
 * 
 * A CustomizedXMLObject node can have attributes and children. Post parsing an
 * XML the complete DOM tree is stored as an instance of CustomizedXMLObject. It
 * provides the API to manipulate the tree and/or get access to it's attributes,
 * children, etc. Every child of this object itself is an oobject of CustomizedXMLObject.
 * 
 * @author Sudiptasish Chanda
 */
public interface CustomizedXMLObject {
	
    /**
     * Return the node name.
     * @return String
     */
	String getNodeName();
	
    /**
     * Set the node value.
     * @param value 
     */
	void setNodeValue(String value);
	
    /**
     * Get the value associated with this node.
     * @return String
     */
	String getNodeValue();
	
	/**
	 * Check if this node has any child beneath it.
     * @return boolean
	 */
	public boolean hasChild();
	
	/**
	 * 
     * @param flag
	 */
	public void hasChild(boolean flag);
	
	/**
	 * Add the new node as a child of this node.
     * @param child
	 */
	public void addChild(CustomizedXMLObject child);

	/**
	 * Return the first child of this node.
     * @return CustomizedXMLObject
	 */
	public CustomizedXMLObject getFirstChild();
	
	/**
	 * Get and return the child present at the specified index.
     * 
     * @param index
     * @return CustomizedXMLObject
	 */
	public CustomizedXMLObject getChildAt(int index);
	
	/**
	 * Return an enumeration over all the children of this node.
     * 
     * @return Enumeration
	 */
	public Enumeration<CustomizedXMLObject> getAllChildren();
	
	/**
	 * Add the attribute name and it's value.
     * @param attributeName
     * @param attributeValue
	 */
	public void addAttribute(String attributeName, String attributeValue);
	
	/**
	 * Add the set of attribute names and their corresponding values.
     * @param attributeNames
     * @param attributeValues
	 */
	public void addAttributeNames(String[] attributeNames, String[] attributeValues);
	
	/**
	 * Get the value associated with this attribute name.
     * @param attributeName
     * @return String
	 */
	public String getAttribute(String attributeName);
	
	/**
	 * Get an iterator over all the attribute names.
     * @return Iterator
	 */
	public Iterator<String> getAllAttributeNames();	
	
	/**
	 * Get an iterator over all the attribute values.
     * @return Iterator
	 */
	public Iterator<String> getAllAttributeValues();
	
	/**
	 * Get the list of children as identified by the child name.
     * @param childName
     * @return Vector
	 */
	public Vector<CustomizedXMLObject> getChildrenByName(String childName);
}
