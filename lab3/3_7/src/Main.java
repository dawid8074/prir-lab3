import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.Semaphore ;

public class Main {
    static int MAX;
    static Semaphore widelec[];

    public static class Filozof1 extends Thread {
        int mojNum;

        public Filozof1(int nr) {
            mojNum = nr;
        }

        public void run() {
            while (true) {
// myslenie
                System.out.println("Mysle ¦ " + mojNum);
                try {
                    Thread.sleep((long) (7000 * Math.random()));
                } catch (InterruptedException e) {
                }
                widelec[mojNum].acquireUninterruptibly(); //przechwycenie L widelca
                widelec[(mojNum + 1) % MAX].acquireUninterruptibly(); //przechwycenie P widelca
// jedzenie
                System.out.println("Zaczyna jesc " + mojNum);
                try {
                    Thread.sleep((long) (5000 * Math.random()));
                } catch (InterruptedException e) {
                }
                System.out.println("Konczy jesc " + mojNum);
                widelec[mojNum].release(); //zwolnienie L widelca
                widelec[(mojNum + 1) % MAX].release(); //zwolnienie P widelca
            }
        }
    }


        public static class Filozof2 extends Thread {
        int mojNum;

        public Filozof2(int nr) {
            mojNum = nr;
        }

        public void run() {
            while (true) {
// myslenie
                System.out.println("Mysle ¦ " + mojNum);
                try {
                    Thread.sleep((long) (5000 * Math.random()));
                } catch (InterruptedException e) {
                }
                if (mojNum == 0) {
                    widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
                    widelec[mojNum].acquireUninterruptibly();
                } else {
                    widelec[mojNum].acquireUninterruptibly();
                    widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
                }
// jedzenie
                System.out.println("Zaczyna jesc " + mojNum);
                try {
                    Thread.sleep((long) (3000 * Math.random()));
                } catch (InterruptedException e) {
                }
                System.out.println("Konczy jesc " + mojNum);
                widelec[mojNum].release();
                widelec[(mojNum + 1) % MAX].release();
            }
        }
    }


    public static class Filozof3 extends Thread {
        int mojNum;
        Random losuj;

        public Filozof3(int nr) {
            mojNum = nr;
            losuj = new Random(mojNum);
        }

        public void run() {
            while (true) {
// myslenie
                System.out.println("Mysle ¦ " + mojNum);
                try {
                    Thread.sleep((long) (5000 * Math.random()));
                } catch (InterruptedException e) {
                }
                int strona = 0;
                        //losuj.nextInt(2);
                boolean podnioslDwaWidelce = false;
                do {
                    if (strona == 0) {
                        widelec[mojNum].acquireUninterruptibly();
                        if (!(widelec[(mojNum + 1) % MAX].tryAcquire())) {
                            widelec[mojNum].release();
                        } else {
                            podnioslDwaWidelce = true;
                        }
                    } else {
                        widelec[(mojNum + 1) % MAX].acquireUninterruptibly();
                        if (!(widelec[mojNum].tryAcquire())) {
                            widelec[(mojNum + 1) % MAX].release();
                        } else {
                            podnioslDwaWidelce = true;
                        }
                    }
                } while (podnioslDwaWidelce == false);
                System.out.println("Zaczyna jesc " + mojNum);
                try {
                    Thread.sleep((long) (3000 * Math.random()));
                } catch (InterruptedException e) {
                }
                System.out.println("Konczy jesc " + mojNum);
                widelec[mojNum].release();
                widelec[(mojNum + 1) % MAX].release();
            }
        }
    }
        public static void main ( String [] args ) {
            //MAX=5;
            Scanner scanner=new Scanner(System.in);
            System.out.println("podaj ilosc filozofow: ");
            MAX=scanner.nextInt();
            widelec=new Semaphore[MAX];
            for ( int i =0; i<MAX; i++) {
                widelec [ i ]=new Semaphore ( 1 ) ;
            }

            int sposob;
            System.out.println("podaj ktory sposb wybeierasz(1-3): ");
            sposob=scanner.nextInt();

            switch (sposob)
            {
                case 1:
                    for ( int i =0; i<MAX; i++) {
                        new Filozof1(i).start();
                    }
                    break;


                case 2:
                    for ( int i =0; i<MAX; i++) {
                        new Filozof2(i).start();
                    }
                    break;


                case 3:
                    for ( int i =0; i<MAX; i++) {
                        new Filozof3(i).start();
                    }
                    break;
                default:
                    System.out.println("wybrano sposob poza zakresem");
            }

    }

}
