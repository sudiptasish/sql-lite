/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sc.hm.sqll.util;

/**
 *
 * @author Sudiptasish Chanda
 */
public final class SQLLConstants {
    
    public static final SQLLConstants PARSER_FACTORY_TYPE_DOM = new SQLLConstants("PARSER_FACTORY_TYPE_DOM");
	public static final SQLLConstants PARSER_FACTORY_TYPE_SAX = new SQLLConstants("PARSER_FACTORY_TYPE_SAX");
	
    private final String value;
    
	public SQLLConstants(String value) {
		this.value = value;
	}
	
	public String getValue() {
		return this.value;
	}
	
    @Override
	public String toString() {
		return this.value;
	}
}
