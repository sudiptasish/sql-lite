/**
 *  
 */

package com.sc.hm.sqll.parser.factory.intf;

import java.io.File;

import com.sc.hm.sqll.parser.cobject.CustomizedXMLObject;

public interface XMLParser {

	/**
	 * 
	 *
	 * @param		
	 * @return		
	 * @exception
	 */
	public void parse() throws Exception;
	
	/**
	 * 
	 *
	 * @param		
	 * @return		
	 * @exception
	 */
	public void parse(String xmlFile) throws Exception;
	
	/**
	 * 
	 *
	 * @param		
	 * @return		
	 * @exception
	 */
	public void parse(File xmlFile) throws Exception;
	
	/**
	 * 
	 *
	 * @param		
	 * @return		
	 * @exception
	 */
	public void parse(String xmlFile, CustomizedXMLObject cObject) throws Exception;
}
