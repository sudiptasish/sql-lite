/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sc.hm.sqll.config;

/**
 *
 * @author Sudiptasish Chanda
 */
public enum DatabaseType {
    
    ORACLE ("Oracle"),
    DB2 ("DB2"),
    SYSBASE ("Sybase"),
    DERBY ("Derby");
    
    private final String desc;
    
    DatabaseType(String desc) {
        this.desc = desc;
    }
    
    public String getDesc() {
        return this.desc;
    }
}
