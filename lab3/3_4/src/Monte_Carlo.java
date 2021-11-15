import java.util.Random;

public class Monte_Carlo{
    static class MonteCarlo
    {
        MonteCarlo(int liczbaTestow)
        {
            this.liczbaTestow=liczbaTestow;
        }
        int wKole=0;
        int pozaKolem=0;
        int liczbaTestow;
        int promienKola=3;
        int poleKwadratu= (int) Math.pow(2*promienKola,2);

        synchronized void dodaj_wKole()
        {
            wKole++;
        }
        synchronized void dodaj_pozaKolem()
        {
            pozaKolem++;
        }
        void licz()
        {
            double tempX=generatorLiczbLosowych();
            double tempY=generatorLiczbLosowych();
            if (Math.pow(tempX,2)+Math.pow(tempY,2)<=Math.pow(promienKola,2))
            {
                dodaj_wKole();
            }
            else
            {
                dodaj_pozaKolem();
            }
        }
        private double generatorLiczbLosowych()
        {
            Random rand= new Random();
            return (-promienKola) + ((2*promienKola)*rand.nextDouble());
        }
        void podajWynik()
        {
            System.out.println("pole koła wynosi: "+(double) poleKwadratu*wKole/liczbaTestow);
        }
    }
    static class kalkulator extends Thread
    {
        MonteCarlo objekt;
        kalkulator(MonteCarlo objekt)
        {
            this.objekt=objekt;
        }
        @Override
        public void run() {
            objekt.licz();
        }
    }
    static class menagoKalkulator extends Thread
    {
        MonteCarlo watek;
        menagoKalkulator(MonteCarlo watek)
        {
            this.watek=watek;
        }
        @Override
        public void run() {
            for (int i=0; i<watek.liczbaTestow; i++)
            {
                kalkulator test=new kalkulator(watek);
                test.start();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        int liczbaTestow=10000;
        MonteCarlo test=new MonteCarlo(liczbaTestow);
        menagoKalkulator menago=new menagoKalkulator(test);
        menago.start();
        menago.join();

        test.podajWynik();
    }
}
