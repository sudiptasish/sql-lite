/**
 * 
 */

package com.sc.hm.sqll.parser.factory;

import com.sc.hm.sqll.parser.XMLSAXParser;
import com.sc.hm.sqll.parser.factory.intf.ParserFactoryType;
import com.sc.hm.sqll.parser.factory.intf.XMLParser;


public class SAXParserFactory implements ParserFactoryType {
	
	public SAXParserFactory() {}

	public XMLParser newParser() {
		return new XMLSAXParser();
	}

}
