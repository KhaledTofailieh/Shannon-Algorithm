package algo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.RenderedImage;
import java.io.File;
import java.io.IOException;

public class JpegReader {
    Image myImage;

    public long read(String path) throws IOException {
        File f=new File(path);
        myImage= ImageIO.read(f);
        return  f.length();
    }

    public long saveJpeg(String path) throws IOException {
        File f=new File(path);
        ImageIO.write((RenderedImage) myImage,"jpg",f);

        return f.length();
    }
    public long saveBmp(String path) throws IOException {
        File f=new File(path+".bmp");
        ImageIO.write((RenderedImage) myImage,"bmp",f);

        return f.length();
    }
}
