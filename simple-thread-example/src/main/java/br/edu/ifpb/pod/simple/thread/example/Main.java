/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.edu.ifpb.pod.simple.thread.example;

/**
 * Exemplo simples de Threads
 * @author miolivc
 */
public class Main {
    private static Object lock = new Object();  // fazer o bloqueio por meio do syncronized: 
    //bloqueia metodos e variaveis mas precisa ser objetos
    private volatile static Integer v = 0; // exclusividade de acesso (bloqueada p/ escrita e leitura): volatile
    
    public static void main(String[] args) throws InterruptedException{
        
        for(int i = 0; i < 10; i++){
        
            Thread t1 = new Thread() {
                public void run() {
                    v = 2;
                    v = v + 2;
                };
            };
        
            Thread t2 = new Thread() {
                public void run() {
                    v = 3;
                    v = v + 3;
                };
            };
        
            t1.start();
            t2.start();
            //Thread.sleep(1000);
            
            synchronized (v){
                // Pode imprimir 0, 2, 3, 4, 5, 6, 7, 8
                System.out.println("Valor de V :" + v);
                System.out.println("Valor de V :" + v);
            }
        }
        // Join: permite que uma thread se junte a outra
        // Wait: uma thread aguarda outra : aguarda uma notificaçao
        // notify: notifica quem aguarda (wait); uma thread so notifica a si mesma e, 
        // por consequencia notifca as qe estao esperando por ela;
        // para notificar uma thread e usando sincronized, compartilhando e monitorando 
        // um estado, pois estes sao a mesma variavel. O notify le uma variavel de estado e altera o estado 
        // para executar.. state = executando
        // uma thread antes de  morrer usa o notifyAll(); - notifica a todos, notify() - notifica uma (ultima)
    }
}
