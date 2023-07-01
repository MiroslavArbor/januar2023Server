/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import forme.ServerskaForma;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Mafija
 */
public class PokretanjeServera extends Thread{
    ServerskaForma sf;

    public PokretanjeServera(ServerskaForma sf) {
        this.sf = sf;
    }

    @Override
    public void run() {
        try {
            ServerSocket ss = new ServerSocket(9000);
            sf.serverJePokrenut();
            while (true) {                
                Socket klijent = ss.accept();
                System.out.println("Klijent se povezao");
                NitKlijent nk = new NitKlijent(klijent);
                nk.start();
            }
        } catch (IOException ex) {
            Logger.getLogger(PokretanjeServera.class.getName()).log(Level.SEVERE, null, ex);
            sf.nijePokrenut();
        }
    }
    
    
}
