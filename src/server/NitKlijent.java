/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server;

import domen.Angazovanje;
import domen.Korisnik;
import domen.Predmet;
import domen.Profesor;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import konstante.Operacija;
import logika.Kontroler;
import transfer.Odgovor;
import transfer.Zahtev;

/**
 *
 * @author Mafija
 */
public class NitKlijent extends Thread{
    Socket klijent;
    
    boolean kraj = false;
    

    public NitKlijent(Socket klijent) {
        this.klijent = klijent;
    }

    @Override
    public void run() {
        
        while (!kraj) {            
            Zahtev zahtev = primiZahtev();
            Odgovor odgovor = new Odgovor();
            int operacija = zahtev.getOperacija();
            
            switch (operacija) {
                case Operacija.LOGIN:
                    Korisnik korisnik = (Korisnik) zahtev.getParametar();
                    Korisnik ulovaniKorisnik = Kontroler.getInstanca().proveriLogin(korisnik.getEmail(), korisnik.getLozinka());
                    odgovor.setOdgovor(ulovaniKorisnik);
                    break;
                case Operacija.VRATI_PROFESORE:
                    
                    ArrayList<Profesor> listaProf = Kontroler.getInstanca().vratiProfesore();

                    odgovor.setOdgovor(listaProf);
                    
                    break;
               case Operacija.VRATI_PREDMETE:
                    
                    ArrayList<Predmet> listaPredmeta = Kontroler.getInstanca().vratiPredmete();

                    odgovor.setOdgovor(listaPredmeta);
                    
                    break;
                    
               case Operacija.SACUVAJ_PROFESORA:
                   
                   Profesor profa = (Profesor) zahtev.getParametar();
                    
                    boolean sacuvano = Kontroler.getInstanca().sacuvajProfesora(profa);
                    
                    if (sacuvano) {
                       odgovor.setPoruka("USpesno sacuvan profesor");
                   }else{
                       odgovor.setPoruka("Nije uspesno sacuvan profesor");
                    }

                    odgovor.setUspesno(sacuvano);
                    break;
               case Operacija.PRETRAZI:
                   
                   Profesor prof = (Profesor) zahtev.getParametar();
                    
                    ArrayList<Angazovanje> listaAngazovanja = Kontroler.getInstanca().vratiAngazovanjaZaProfesora(prof);

                    odgovor.setOdgovor(listaAngazovanja);
                    break;
               case Operacija.SACUVAJ_IZMENE:
                   
                   ArrayList<Angazovanje> listaA = (ArrayList<Angazovanje>) zahtev.getParametar();
                   
                    boolean sacu = Kontroler.getInstanca().sacuvajAngazovanja(listaA);
                    
                    if (sacu) {
                       odgovor.setPoruka("USpesno sacuvan profesor");
                   }else{
                       odgovor.setPoruka("Nije uspesno sacuvan profesor");
                    }

                    odgovor.setUspesno(sacu);
                    break;
            }
            
            posaljiOdgovor(odgovor);
        }
        
    }

    private Zahtev primiZahtev() {
        try {
            ObjectInputStream ois = new ObjectInputStream(klijent.getInputStream());
            return (Zahtev) ois.readObject();
        } catch (IOException ex) {
            Logger.getLogger(NitKlijent.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(NitKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }

    private void posaljiOdgovor(Odgovor odgovor) {
        try {
            ObjectOutputStream oos = new ObjectOutputStream(klijent.getOutputStream());
            oos.writeObject(odgovor);
        } catch (IOException ex) {
            Logger.getLogger(NitKlijent.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
