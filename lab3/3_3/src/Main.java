import java.util.Random;

import static java.lang.Thread.sleep;

public class Main {
    public static class Samochod extends Thread {
        private String nrRej;
        private int pojZbiornika;
        private int paliwo;
        boolean jazda=true;

        public Samochod(String rejestracja, int _pojZbiornika) {
            nrRej=rejestracja;
            pojZbiornika=_pojZbiornika;
            paliwo=pojZbiornika;
        }

        public void tankowanie(int _paliwo)
        {
            if (paliwo+_paliwo>pojZbiornika)
                paliwo=pojZbiornika;
            else
                paliwo+=_paliwo;
            System.out.println("dotankowano "+_paliwo+" paliwa. Obecna ilość to: "+paliwo);
        }

        public void run()
        {
            while (true) {
                if (jazda) {
                    try {
                        sleep(1000);
                        paliwo--;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    System.out.println("obecna losc paliwa: " + paliwo + " w aucie: " + nrRej);
                }

            }
        }

    }

    public static void main(String[] args) throws InterruptedException {
        Samochod porshe= new Samochod("pierwszy", 100 );
        porshe.start();
        sleep(4000);
        porshe.tankowanie(2);
        porshe.stop();

    }
}
