public class Main {
    public static class Godzina
    {
        int sekundy;
        int minuty;
        int godziny;
        boolean czy_dodac=false;

        Godzina(int sekundy, int minuty, int godziny)
        {
            this.sekundy=sekundy;
            this.minuty=minuty;
            this.godziny=godziny;
        }
        public synchronized void dodaj_sekunde()
        {
            while (true) {
                while (czy_dodac == false) {
                    try {
                        wait();
                    } catch (InterruptedException e) {
                    }
                }
                sekundy++;
                if (sekundy >= 60) {
                    sekundy = 0;
                    minuty++;
                }
                if (minuty >= 60) {
                    minuty = 0;
                    godziny++;
                }
                if (godziny >= 24)
                    godziny = 0;


                czy_dodac = false;
                System.out.println("obecna godzina to: " + godziny + ":" +
                        minuty + ":" + sekundy);
            }

        }
        public synchronized void tiktak() throws InterruptedException {
                czy_dodac = true;
                notifyAll();
        }
    }
    static class Cykacz extends Thread
    {
        Godzina godzina;
        Cykacz(Godzina godzina)
        {
            this.godzina=godzina;
        }
        public void run()
        {
            while (true) {
                try {
                    Thread.sleep(1000);
                    godzina.tiktak();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    static class Licznik extends Thread
    {
        Main.Godzina godzina;
        Licznik(Godzina godzina)
        {
            this.godzina=godzina;
        }
        public synchronized void run()
        {

            godzina.dodaj_sekunde();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Godzina obecna= new Godzina(56,59,23);
        Licznik licznik= new Licznik(obecna);
        Cykacz cykacz= new Cykacz(obecna);
        licznik.start();
        cykacz.start();

    }
}
