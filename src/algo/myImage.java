package algo;
import java.awt.Image;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Scanner;


public class myImage {

    public String ReadImageAsString(String Path) throws Exception {
        byte b;
        File file=new File(Path);
        byte data[]=getbytefromfile(file);
        String string=Base64.getEncoder().encodeToString(data);
        return string;
    }
    public void writeImagefromstring(String string,String Path) throws IOException {

        byte []data=	Base64.getDecoder().decode(string);
        FileOutputStream fileOutputStream=new FileOutputStream(new File(Path));
        fileOutputStream.write(data);
        fileOutputStream.close();


    }
    public void savetextinfile(String path,String string) throws IOException {


        FileWriter fos=new FileWriter(path);
        fos.write(string);
    }
    public byte[] getbytefromfile(File file)throws Exception {
        byte[] buffetr=new byte[1024];
        ByteArrayOutputStream os=new ByteArrayOutputStream();
        FileInputStream fis=new FileInputStream(file);
        int read;
        while((read=fis.read(buffetr))!=-1) {


            os.write(buffetr,0,read);
        }
        fis.close();
        os.close();
        return os.toByteArray();

    }
    public void compreessionimag(String pathimage,String pathfilesave) throws Exception {

        String imagestring=ReadImageAsString(pathimage);
        Teable LZW=new Teable();
        ArrayList<Integer> Result=LZW.LZWEncod(imagestring);
        FileWriter fileWriter=new FileWriter(new File(pathfilesave));
        ObjectOutputStream objectOutputStream=new ObjectOutputStream(new FileOutputStream(new File(pathfilesave)));
//for(int i=0;i<0;i++)
        objectOutputStream.writeObject(Result);

        objectOutputStream.close();
    }
    public void Decompressionimage(String pathimag,String pathsave) throws IOException, ClassNotFoundException {
        //FileReader fileReader=new FileReader(new File(pathimag));
        ObjectInputStream inputStream=new ObjectInputStream(new FileInputStream(new File(pathimag)));
        ArrayList<Integer> output=new ArrayList<Integer>();
        output=(ArrayList<Integer>) inputStream.readObject();


        String imgag= Teable.DecodeLZW(output);
        writeImagefromstring(imgag, pathsave);



    }
    public void commpressiontext(String Pathfile,String Pathsaving) throws IOException {
        File file=new File(Pathfile);
        Scanner sc=new Scanner(file);
        String result = "";
        while(sc.hasNextLine())
            result=result+sc.nextLine()+"\n";
        ArrayList<Integer>	sd=Teable.LZWEncod(result);
        ObjectOutputStream obs=new ObjectOutputStream(new FileOutputStream(new File(Pathsaving)));
        obs.writeObject(sd);
        obs.close();


    }
    public void Decomreessiontext(String Pathfile,String pathdecom) throws FileNotFoundException, IOException, ClassNotFoundException {
        ObjectInputStream obs=new ObjectInputStream(new FileInputStream(new File(Pathfile)));
        ArrayList<Integer> ds=(ArrayList<Integer>) obs.readObject();
        obs.close();
        String s=Teable.DecodeLZW(ds);
        FileWriter fileOutputStream=new FileWriter(pathdecom);
        fileOutputStream.write(s);
        fileOutputStream.close();
    }
}



