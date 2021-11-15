import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import javax.swing.JFrame;
public class GrayScale {
    BufferedImage image;
    int width;
    int height;
    public GrayScale() {
        try {
//odczyt obrazu z pliku
            File input = new File("Ratusz_Białystok_rok_2013.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
            //odczyt pixeli obrazu w dwóch pętlach po kolumnach i wierszach
            for(int i=1; i<height-1; i++){
                for(int j=1; j<width-1; j++){

                    //odczyt składowych koloru RGB
                    Color c = new Color(image.getRGB(j, i));
                    int red = (int)(c.getRed());
                    int green = (int)(c.getGreen());
                    int blue = (int)(c.getBlue());

                    int final_red, final_green, final_blue;

                    //negatyw
                    final_red = 255-red;
                    final_green = 255-green;
                    final_blue = 255-blue;
                    Color newColor = new Color(final_red, final_green, final_blue);
                    image.setRGB(j,i,newColor.getRGB());
                } //koniec dwóch pętli po kolumnach i wierszach obrazu
            }
//zapis do pliku zmodyfikowanego obrazu
            File ouptut = new File("grayscale.jpg");
            ImageIO.write(image, "jpg", ouptut);
        } catch (Exception e) {}
    }
    static public void main(String args[]) throws Exception
    {
        GrayScale obj = new GrayScale();
    }
}