/**
 * 
 */

package com.sc.hm.sqll.parser.factory;

import java.io.File;

import com.sc.hm.sqll.parser.cobject.CustomizedXMLObject;
import com.sc.hm.sqll.parser.factory.intf.XMLParser;

public abstract class ParserAdapter implements XMLParser {
	
	public ParserAdapter() {}

	public void parse() throws Exception {}

	public void parse(String xmlFile) throws Exception {}

	public void parse(File xmlFile) throws Exception {}
	
	public void parse(String xmlFile, CustomizedXMLObject cObject) throws Exception {}
	
}
