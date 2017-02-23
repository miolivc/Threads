
package br.edu.ifpb.pod.sistema.de.atendimento;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
/**
 * 
 * @author miolivc
 */
public class Main {
    
    volatile static int time = 90, atendido = 0, chegaramAgora = 0, quantidadeChegou = 0, foramEmbora = 0, ficouNaFila = 0;
    static Queue<String> fila = new LinkedList<>();

    static Thread gerenciaChegada = new Thread(){
        public void run(){
            while(true){
                int taxaChegada = new Random().nextInt(6);
                    chegaramAgora = taxaChegada;
                    quantidadeChegou += chegaramAgora;
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    static Thread gerenciaFila = new Thread(){
        public void run(){
            while(true){
                for(int i = 1; i <= chegaramAgora; i++){
                    if(fila.size() < 50){
                        fila.add("Cliente");
                        chegaramAgora--;
                    } else if (chegaramAgora > 0){
                        foramEmbora += chegaramAgora;
                    }
                }
            }
        }
    };

    static Thread gerenciaAtendimento = new Thread(){
        public void run(){
            while(true){
                try {
                    Thread.sleep(2000);
                    fila.poll();
                    atendido++;
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
            }
        }
    };

    static Thread timeThread = new Thread(){
        public void run(){
            try {
                Thread.sleep(time * 1000);
            } catch (InterruptedException ex) {
                ex.printStackTrace();
            }
        }
    };

    static Thread engine = new Thread(){
        public void run(){
            gerenciaChegada.start();
            gerenciaFila.start();
            gerenciaAtendimento.start();
        }
    };

    private static void methodExec(){
        while(true){
            if(! timeThread.isAlive()){
                engine.interrupt();
                System.out.println("Quantidade de pessoas que chegaram: " + quantidadeChegou);
                System.out.println("Quantidade de pessoas que retornaram: " + foramEmbora);
                System.out.println("Quantidade de pessoas que foram atendidas: " + atendido );
                System.out.println("Quantidade de pessoas que ficaram na fila: " + fila.size());
                System.exit(0);
            }
        }
        
    }

    public static void main(String[] args) {
        timeThread.start();
        engine.start();
        methodExec();
    }
}
