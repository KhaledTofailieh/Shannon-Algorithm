package algo;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.*;
import java.util.*;
public class Teable {
    public static 	Map<String,Integer > table;
    public 	static Map<Integer,String > tableDecoder;
    public ArrayList<Integer> outputs;
    public Teable() {

        table=new HashMap<String, Integer>();
        tableDecoder=new HashMap<Integer, String>();
        init();

    }
    public void  init() {
        char a;
        String character;
        for(int i=0;i<256;i++) {
            a=(char)i;
            character=""+a;
            table.put(character, i);
            tableDecoder.put(i, character);
        }

    }

    public static ArrayList<Integer> LZWEncod(String text) {
        ArrayList<Integer> output=new ArrayList<Integer>();
        String current=text.charAt(0)+"";
        String next;
        String Result;
        int cod=256;
        for(int i=1;i<text.length();i++) {
            next=text.charAt(i)+"";
            Result=current+next;
            if(table.get(Result)!=null) {
                current=Result;

            }
            else {
                output.add(table.get(current));
                table.get(current);
                table.put(Result,cod );
                tableDecoder.put(cod, Result);
                cod++;
                current=next;

            }


        }

        output.add(table.get(current));
//outputs=output;
        return output;



    }
    public static String DecodeLZW(ArrayList<Integer> output) {
        String []Result=new String[output.size()];
        String temp="";

        int key;
        int val;

        for (int i=0;i<output.size();i++) {
            //System.out.println(i);
            Result[i]=tableDecoder.get(output.get(i));


        }
        try {
            FileWriter f=new FileWriter("D:\\txtdecom.txt");
            for(int ik=0;ik<Result.length;ik++)
                f.write(Result[ik]);
            f.close();

        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        try {
            FileReader fr=new FileReader("D:\\txtdecom.txt");
            Scanner scan=new Scanner(fr);
            temp=scan.nextLine();
            fr.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }


        return temp;

    }

}




