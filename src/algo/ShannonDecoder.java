package algo;

import java.io.*;
import java.util.BitSet;



public class ShannonDecoder {
    SymbolTable symbolTable;
    BitSet bitSet;
    String message;
    long file_size;
    
    public String decode(String path){
        file_size= readFromFile(path);
        message=decodeMessage();
        return  message;
    }
    private String decodeMessage(){
        int i=0;
        StringBuilder symbol=new StringBuilder(),message=new StringBuilder();

        while(i<bitSet.length()){
          symbol.append(getCode(bitSet.get(i)));
          if(symbolTable.contain(symbol.toString())){

              message.append(symbolTable.get(symbol.toString()));
              symbol.delete(0,symbol.length());
          }
          i++;
        }
       return message.toString();
    }
    private long readFromFile(String path){
        readTable(getDirectory(path));
       return readMessage(path);
    }
    private  void readTable(String Dir) {
        String tablePath=Dir+"Table";
       
        try {
            FileInputStream fin  = new FileInputStream(tablePath);
            ObjectInputStream ob=new ObjectInputStream(fin);
            symbolTable =(SymbolTable)ob.readObject();
            for (String s:symbolTable.getKeySet()) {
             System.out.println(s+"     "+symbolTable.get(s));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }


    }
    private long readMessage(String path){
        
        File messagefile=new File(path);
        FileInputStream fin= null;
        try {
            fin = new FileInputStream(messagefile);
            bitSet=BitSet.valueOf(fin.readAllBytes());

            System.out.println();
            for(int i = 0; i< bitSet.length(); i++){
                System.out.print(getCode(bitSet.get(i)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messagefile.length();
    }

    private int getCode(boolean b){
        return  b?1:0;
    }
    private String getDirectory(String path){

        StringBuilder temp=new StringBuilder(path),temp1;
        String Dir;
        temp=temp.reverse();
        temp1 =new StringBuilder(temp.substring(temp.indexOf("\\")));
        Dir=temp1.reverse().toString();

        return Dir;
    }

    public long getFile_size() {
        return file_size;
    }
}
