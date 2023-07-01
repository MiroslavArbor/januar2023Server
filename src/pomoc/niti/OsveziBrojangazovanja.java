/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pomoc.niti;

import forme.ServerskaForma;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author miroslavjovanovic
 */
public class OsveziBrojangazovanja extends Thread{
    ServerskaForma sf;

    public OsveziBrojangazovanja(ServerskaForma sf) {
        this.sf = sf;
    }

    @Override
    public void run() {
        while (true) {            
            sf.osveziBrojAngazovanja();
            System.out.println("Osvezio broj angazovanja");
            try {
                sleep(5000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OsveziBrojangazovanja.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    
}
