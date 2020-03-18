package com.sc.hm.sqll.config;

import com.sc.hm.sqll.parser.cobject.CustomizedXMLObject;
import com.sc.hm.sqll.parser.cobject.ParserNode;
import com.sc.hm.sqll.parser.factory.AbstractXMLParserFactory;
import com.sc.hm.sqll.parser.factory.intf.ParserFactoryType;
import com.sc.hm.sqll.parser.factory.intf.XMLParser;
import com.sc.hm.sqll.util.SQLLConstants;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Vector;

/**
 * Configuration manager for initializing the SQLLiTE.
 * 
 * @author Sudiptasish Chanda
 */
public class SQLLConfigManager implements ConfigurationManager {
    
    private static final String ENV_TYPE = "sqllite";

    // Cache that holds the connection configuration.
    // Every connection is given a unique name, that represents the key.
	private final Map<String, EnvironmentCache> CACHE = new HashMap<>();
	
	SQLLConfigManager() {}
	
	@Override
	public void configure(String file) throws Exception {
        /*caches.put("localhost", new EnvironmentCache(new ConnectionConfig(
				"localhost"
				, "localhost"
				, "1521"
				, "orcl12c"
				, null
				, "system"
				, "welcome1")));*/
        
        CustomizedXMLObject cObject = readXML(file);
        
        Vector<CustomizedXMLObject> childList = cObject.getChildrenByName("db-config");
        for (Enumeration<CustomizedXMLObject> enm = childList.elements(); enm.hasMoreElements(); ) {
			CustomizedXMLObject appConfig = enm.nextElement();
            
			CustomizedXMLObject name = appConfig.getChildrenByName("name").elementAt(0);
            CustomizedXMLObject dbType = appConfig.getChildrenByName("dbType").elementAt(0);
            CustomizedXMLObject host = appConfig.getChildrenByName("host").elementAt(0);
            CustomizedXMLObject port = appConfig.getChildrenByName("port").elementAt(0);
            CustomizedXMLObject sid = appConfig.getChildrenByName("sid").elementAt(0);
            CustomizedXMLObject service = appConfig.getChildrenByName("service").elementAt(0);
            CustomizedXMLObject user = appConfig.getChildrenByName("user").elementAt(0);
            CustomizedXMLObject password = appConfig.getChildrenByName("password").elementAt(0);
            
			CACHE.put(name.getNodeValue(), new EnvironmentCache(new ConnectionConfig(
				name.getNodeValue()
                , Enum.valueOf(DatabaseType.class, dbType.getNodeValue())
				, host.getNodeValue()
				, port.getNodeValue()
				, sid.getNodeValue()
				, service.getNodeValue()
				, user.getNodeValue()
				, password.getNodeValue())));
		}
	}
	
	private CustomizedXMLObject readXML(String file) throws Exception {
		CustomizedXMLObject cXMLObject = new ParserNode(ENV_TYPE);
		ParserFactoryType parserFactory = AbstractXMLParserFactory
            .getXMLParserFactory()
            .getParserFactory(SQLLConstants.PARSER_FACTORY_TYPE_DOM.toString());
        
		XMLParser xmlParser = parserFactory.newParser();
		xmlParser.parse(file, cXMLObject);
		
		return cXMLObject;
	}
	
	@Override
	public Iterator<ConnectionConfig> getAll() {
		List<ConnectionConfig> configs = new ArrayList<>(CACHE.size());
		
		for (Iterator<Map.Entry<String, EnvironmentCache>> itr = CACHE.entrySet().iterator(); itr.hasNext(); ) {
			Map.Entry<String, EnvironmentCache> me = itr.next();
			EnvironmentCache envCache = me.getValue();
			configs.add(envCache.getConfig());
		}
		return configs.iterator();
	}
	
	@Override
	public ConnectionConfig get(String name) {
		EnvironmentCache envCache = CACHE.get(name);
		if (envCache != null) {
			return envCache.getConfig();
		}
		return null;
	}
	
	@Override
	public void put(String name, ConnectionConfig config) {
		CACHE.put(name, new EnvironmentCache(config));
	}
	
	@Override
	public void clear() {
		for (Iterator<Map.Entry<String, EnvironmentCache>> itr = CACHE.entrySet().iterator(); itr.hasNext(); ) {
			Map.Entry<String, EnvironmentCache> me = itr.next();
			EnvironmentCache envCache = me.getValue();
			envCache.clear();
		}
		CACHE.clear();
	}

	@Override
	public void remove(String name) {
		CACHE.remove(name);
	}
}
