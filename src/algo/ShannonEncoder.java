package algo;


import java.io.*;
import java.util.*;

public class ShannonEncoder {
    private StringBuilder iString;
    private final Hashtable<String,Integer> myTable;
    private SymbolTable symbolTable;
    private SymbolTable decodeTable;
    private BitSet bitSet;
    private int all_count;
    private StringBuilder sorted_string,encoded_message;
    private long pre_size;
    private long after_size;

public ShannonEncoder(){
    myTable=new Hashtable<>();
    all_count=0;
    symbolTable = new SymbolTable();
    decodeTable=new SymbolTable();
    iString=new StringBuilder();
    encoded_message=new StringBuilder();
}
    private long read_from_file(String path){
        File f=new File(path);
        try {
            FileInputStream fin=new FileInputStream(f);
            int content;
            char sympol;

            while((content=fin.read())!=-1){
              sympol=(char)content;
              if(myTable.containsKey(String.valueOf(sympol))) {
                  myTable.put(String.valueOf(sympol), myTable.get(String.valueOf(sympol)) + 1);
              }else{
                  myTable.put(String.valueOf(sympol),1);
              }
              iString.append(sympol);
              all_count+=1;
            }
        } catch (IOException e) {
            System.out.println("Not Founded!");
        }
        return f.length();
    }

    private StringBuilder sorting_string(){
        Collection<Integer> c_list;
        ArrayList<Integer> list;

        c_list= myTable.values();
        list=new ArrayList(Arrays.asList(c_list.toArray()));
        list.sort( (integer, t1) -> {
            if(integer>t1){
                return -1;
            }return  1;
        });
        StringBuilder s_list=new StringBuilder();
        for (Integer i:list) {
            for (String s : myTable.keySet()) {
                if (myTable.get(s).equals(i) && !s_list.toString().contains(s)) {
                    s_list.append(s);
                }
            }
        }
      return s_list;
    }

    public BitSet encode(String path){
        pre_size= read_from_file(path);
        this.sorted_string=sorting_string();

        Node myTree=new Node(sorted_string.toString(),all_count,"");

        createSympolTable(myTree);
        createDecodeTable();

        this.bitSet=encodingMessage();
        printCodedMessage();
        return bitSet;
    }
    public long saveToFile(String path){
        after_size= saveMessage(path);
        saveDecodeTable(getDirectory(path));
        System.out.println(path);
        System.out.println(path);

        return after_size;
    }
    private void saveDecodeTable(String Dir){

        String tablePath=Dir+"Table";
        File tableFile=new File(tablePath);
        try {
            FileOutputStream tableFr= new FileOutputStream(tableFile);
            ObjectOutputStream ob=new ObjectOutputStream(tableFr);
            ob.writeObject(decodeTable);
         } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private long saveMessage(String path){
        File messsageFile=new File(path);
        FileOutputStream fr= null;
        try {
            fr = new FileOutputStream(messsageFile);
            fr.write(bitSet.toByteArray());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return messsageFile.length();
    }
    private void createDecodeTable(){
       for(String s:symbolTable.getKeySet()){
           decodeTable.put(symbolTable.get(s),s);
       }
    }
    private void createSympolTable(Node tree){

        for(int i=0;i<sorted_string.length();i++){
            String s=getCodeString(tree,"",String.valueOf(sorted_string.charAt(i)));

            symbolTable.put(String.valueOf(sorted_string.charAt(i)),s);
            System.out.println(sorted_string.charAt(i)+"   "+symbolTable.get(String.valueOf(sorted_string.charAt(i))));
        }

    }
    private BitSet encodingMessage(){
        BitSet bitSet=new BitSet();
        String temp_code;
        int code_counter=0;

        for(int i=0;i<iString.length();i++){
          temp_code= symbolTable.get(String.valueOf(iString.charAt(i)));
          for(int j=0;j<temp_code.length();j++,code_counter++){
              if(temp_code.charAt(j)=='1'){
                  bitSet.set(code_counter);
             }
         }
       }
        bitSet.set(code_counter);
      return bitSet;
    }
    private int getCode(boolean b){
    return  b?1:0;
    }
    public void printCodedMessage(){
    for(int i=0;i<bitSet.length();i++){
        System.out.print(getCode(bitSet.get(i)));
    }


    }
    public StringBuilder getCodedMessage(){
        for(int i=0;i<bitSet.length();i++){
            encoded_message.append(getCode(bitSet.get(i)));
        }
    return encoded_message;
    }
    private String getDirectory(String path){
       StringBuilder temp=new StringBuilder(path),temp1;
       String Dir;
       temp=temp.reverse();
       temp1 =new StringBuilder(temp.substring(temp.indexOf("\\")));
       Dir=temp1.reverse().toString();

      return Dir;
    }
    private String getCodeString(Node n,String mCode,String mChar){
        if(isGoal(n,mChar) ){
            return mCode+n.getCode();
        }else if(isLeaf(n)){
            return "-";
        }
        else{
            String s1=mCode,s2=mCode;
            s1= mCode+n.getCode()+getCodeString(n.getLeft(),s1,mChar);
            s2=mCode+n.getCode()+getCodeString(n.getRight(),s2,mChar);
            if(s1.endsWith("-")){
              return s2;
            }return  s1;
        }
    }
    private boolean isLeaf(Node n){
    return n.getValue().length()==1;
    }
    private boolean isGoal(Node n ,String s){
    return n.getValue().equals(s);
    }

    private class Node {
        private String Value, code;
        private  int Count ;
        private Node Left,Right;

        public Node(String value, int count , String code) {
            Value = value;
            Count = count;
            this.code=code;
            if(value.length()>1){

                Left=new Node(String.valueOf(value.charAt(0)),myTable.get(String.valueOf(value.charAt(0))),"0");
                int new_count=Count-myTable.get(String.valueOf(value.charAt(0)));
                Right=new Node(value.substring(1),new_count,"1");
            }


          //  System.out.println(" "+value+"   "+count+"   "+code);
        }

        public void setValue(String value) {
            Value = value;
        }

        public void setCount(int count) {
            Count = count;
        }
        public void setLeft(Node left) {
            Left = left;
        }

        public void setRight(Node right) {
            Right = right;
        }

        public String getValue() {
            return Value;
        }

        public int getCount() {
            return Count;
        }

        public Node getLeft() {
            return Left;
        }

        public Node getRight() {
            return Right;
        }

        public String getCode() {
            return code;
        }
    }

    public long getPre_size() {
        return pre_size;
    }

    public long getAfter_size() {
        return after_size;
    }
}
