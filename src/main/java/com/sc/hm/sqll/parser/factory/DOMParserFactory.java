/**
 * 
 */

package com.sc.hm.sqll.parser.factory;

import com.sc.hm.sqll.parser.XMLDOMParser;
import com.sc.hm.sqll.parser.factory.intf.ParserFactoryType;
import com.sc.hm.sqll.parser.factory.intf.XMLParser;


public class DOMParserFactory implements ParserFactoryType {

	public DOMParserFactory() {}

	public XMLParser newParser() {
		return new XMLDOMParser();
	}	
	
}
