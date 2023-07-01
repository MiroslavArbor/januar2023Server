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
public class OsveziBrojPoProfesoru extends Thread{
    ServerskaForma sf;

    public OsveziBrojPoProfesoru(ServerskaForma sf) {
        this.sf = sf;
    }

    @Override
    public void run() {
        while (true) {            
            sf.osveziBrojPoZvanju();
            System.out.println("Osvezio broj profesora po zvanju");
            try {
                sleep(3000);
            } catch (InterruptedException ex) {
                Logger.getLogger(OsveziBrojPoProfesoru.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }


    
}
