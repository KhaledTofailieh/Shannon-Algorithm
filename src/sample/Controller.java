package sample;

import algo.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;


import javax.imageio.ImageIO;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;


public class Controller {
    @FXML
    private Label file_info_txt;
    @FXML
    private Label message_txt;
    @FXML
    void shanon_encode(ActionEvent e){
        try {
            String path=file_text_chooser("*.txt");
            ShannonEncoder en=new ShannonEncoder();
            en.encode(path);

            LocalDateTime now = LocalDateTime.now();
            en.saveToFile(getDirectory(path)+"\\decoded_message.shn");

            message_txt.setText(en.getCodedMessage().toString());
            file_info_txt.setText("Size1: "+en.getPre_size() +" B" +"\nSize2: "+en.getAfter_size()+" B");

        }catch (Exception ex) {
        }
    }
    @FXML
    void decode_shannon(ActionEvent e){
        try{
            String path=file_text_chooser("*.shn");
            ShannonDecoder de=new ShannonDecoder();
            String msg= de.decode(path);
            message_txt.setText(msg);
            file_info_txt.setText("Size: "+de.getFile_size());
        }catch(Exception ex){

        }

    }
    @FXML
    void decode_lzw(ActionEvent e) throws IOException, ClassNotFoundException {
        try{
            myImage image=new myImage();
            String path=Filechooser();
            boolean a=path.endsWith(".txt");
            if(a)
                image.Decomreessiontext(path,path+" Deccompression.txt");
            else
                image.Decompressionimage(path,"D:\\decom.png");
        }catch (Exception ignored){

        }

    }
    @FXML
    void  encode_lzw(ActionEvent e) throws Exception {
        try{
            myImage image=new myImage();
            String path=Filechooser();
            boolean a=path.endsWith(".txt");
            Teable t=new Teable();

            if(a)
                image.commpressiontext(path,path+"compression.txt");
            else image.compreessionimag(path,path+"compression.kh");
            File file=new File(path);
            File f=new File(path+"compression.txt");
            file_info_txt.setText("Size 1:"+file.length()/1000+"kb\n"+"Size 2:"+f.length()/1000+"kb");
        }catch(Exception ignored){

        }

    }
    @FXML
    void encode_decode_folder(ActionEvent e){
        DirectoryChooser dch=new DirectoryChooser();
    }
    @FXML
    void read_write_jpeg(ActionEvent e){
        try{
            JpegReader jr=new JpegReader();
            String path=ImageFileChooser();
            jr.read(path);
            String dir=getDirectory(path);

            long s2= jr.saveJpeg(dir+"\\Im2");
            long s1=jr.saveBmp(dir+"\\Im1");

            message_txt.setText("Saved to:\n" +dir +"\\Im2.jpg"+"\n"+dir +"\\Im1.bmp");
            file_info_txt.setText("Size1: "+s1 +" B" +"\nSize2: "+s2+" B");


        }catch(Exception ex){

        }

    }
    public String  Filechooser(){
        FileChooser fc=new FileChooser();
        fc.setInitialDirectory(new File("D:\\"));
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text","*.txt")
                ,new FileChooser.ExtensionFilter("Image","*.png","*.jpg")
        ,new FileChooser.ExtensionFilter("K","*.kh","*.jpg"));
        File selectedfile=fc.showOpenDialog(null);

        if(selectedfile!=null) {

        return selectedfile.getAbsolutePath();
        }
        return null;
        }

    public String file_text_chooser(String filter){
        FileChooser fc=new FileChooser();
        fc.setInitialDirectory(new File("D:\\"));
        fc.setTitle("Choose Text File:");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text",filter));
        File selectedfile=fc.showOpenDialog(null);
        if(selectedfile!=null) {

            return selectedfile.getAbsolutePath();
        }
        return null;
    }
    public String ImageFileChooser(){
        FileChooser fc=new FileChooser();
        fc.setInitialDirectory(new File("D:\\"));
        fc.setTitle("Choose Text File:");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Jpeg","*.jpg"),
                new FileChooser.ExtensionFilter("PNG","*.png"));
        File selectedfile=fc.showOpenDialog(null);
        if(selectedfile!=null) {

            return selectedfile.getAbsolutePath();
        }
        return null;
    }

    private String getDirectory(String path){
        StringBuilder temp=new StringBuilder(path),temp1;
        String Dir;
        temp=temp.reverse();
        temp1 =new StringBuilder(temp.substring(temp.indexOf("\\")));
        Dir=temp1.reverse().toString();

        return Dir;
    }
}



