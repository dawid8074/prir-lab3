import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import static java.lang.Thread.sleep;

public class Negatyw  {
    static class Obraz
    {
        BufferedImage image;
        int width;
        int height;
        Obraz() throws IOException {
            File input = new File("test.jpg");
            image = ImageIO.read(input);
            width = image.getWidth();
            height = image.getHeight();
        }
        void licz(int i, int j)
        {
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
        }

        void zapiszPlik() throws IOException {
            File ouptut = new File("grayscale.jpg");
            ImageIO.write(image, "jpg", ouptut);
        }


    }
    static class obliczenia extends Thread
    {
        Obraz obraz;
        obliczenia(Obraz obraz)
        {
            this.obraz=obraz;
        }
        @Override
        public void run() {
            for(int i=1; i<obraz.height-1; i++) {
                for (int j = 1; j < obraz.width - 1; j++) {
                    kalkulator kalkulator=new kalkulator(obraz,i,j);
                    kalkulator.start();
                }
            }
        }
    }
    static class kalkulator extends Thread
    {
        Obraz obraz;
        int i, j;
        kalkulator(Obraz obraz, int i, int j)
        {
            this.obraz=obraz;
            this.j=j;
            this.i=i;
        }

        @Override
        public void run() {
            obraz.licz(i,j);
        }
    }



    public static void main(String[] args) throws IOException, InterruptedException {
        Obraz nowy=new Obraz();
        obliczenia obliczenia=new obliczenia(nowy);
        obliczenia.start();
        while (true)
        {
            if (!obliczenia.isAlive())
            {
                nowy.zapiszPlik();
                break;
            }
            else
            {
                sleep(5000);
            }
        }
    }
}
