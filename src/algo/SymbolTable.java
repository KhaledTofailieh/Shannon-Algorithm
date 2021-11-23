package algo;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Set;

public class SymbolTable implements Serializable {
    private final Hashtable<String,String> symbolTable;

    public SymbolTable() {
        symbolTable = new Hashtable<>();
    }
    public void put(String key,String value){
        symbolTable.put(key,value);
    }
    public String get(String key){
        return symbolTable.get(key);

    }
    public Set<String> getKeySet(){
        return  symbolTable.keySet();
    }
    public boolean contain(String k){
        return  symbolTable.containsKey(k);
    }
}
