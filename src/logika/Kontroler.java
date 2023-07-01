/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package logika;

import db.DbBroker;
import domen.Angazovanje;
import domen.Korisnik;
import domen.Predmet;
import domen.Profesor;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pomoc.BrojAngazovanjaPomocnaKlasa;
import pomoc.ProfesoriZvanjePomocnaKlasa;

/**
 *
 * @author Mafija
 */
public class Kontroler {
    
    private static Kontroler instanca;
    ArrayList<Korisnik> listaKorisnika;
    DbBroker db;

    private Kontroler() {
        listaKorisnika = new ArrayList<>();
        listaKorisnika.add(new Korisnik("Kaca", "Grubar", "kaca@gmail.com", "kaca"));
        listaKorisnika.add(new Korisnik("Sofija", "Mafija", "sofija@gmail.com", "sofija"));
        
        db = new DbBroker();
    }

    public static Kontroler getInstanca() {
        if(instanca == null)
            instanca = new Kontroler();
        return instanca;
    }
    
    public Korisnik proveriLogin(String emailAdresa, String lozinka){
        for (Korisnik korisnik : listaKorisnika) {
            if(korisnik.getEmail().equals(emailAdresa) && korisnik.getLozinka().equals(lozinka)){
                return korisnik;
            }
        }
        
        return null;
    }

    public ArrayList<Profesor> vratiProfesore() {
        db.otvoriKonekciju();
        
        ArrayList<Profesor> listaProfesora = db.vratiProfesore();
        
        db.zatvoriKonekciju();
        
        return listaProfesora;
    }

    public ArrayList<Predmet> vratiPredmete() {
        db.otvoriKonekciju();
        
        ArrayList<Predmet> lista = db.vratiPredmete();
        
        db.zatvoriKonekciju();
        
        return lista;
    }

    public boolean sacuvajProfesora(Profesor profa) {
        boolean sacuvano = false;
        db.otvoriKonekciju();
        try {
            db.sacuvajProfesora(profa);
            int idProfesora = db.vratiIdProfesora();
            profa.setProfesorID(idProfesora);
            for (Angazovanje angazovanje : profa.getLista()) {
                angazovanje.setProfesor(profa);
                db.sacuvajAngazovanje(angazovanje);
            }
            db.commit();
            sacuvano = true;
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            db.rollback();
        }
        
        db.zatvoriKonekciju();
        
        return sacuvano;
    }

    public ArrayList<Angazovanje> vratiAngazovanjaZaProfesora(Profesor prof) {
        db.otvoriKonekciju();
        
        ArrayList<Angazovanje> lista = db.vratiAngazovanje(prof);
        
        db.zatvoriKonekciju();
        
        return lista;
    }

    public boolean sacuvajAngazovanja(ArrayList<Angazovanje> listaA) {
        boolean sacuvano = false;
        db.otvoriKonekciju();
        try {
            
            for (Angazovanje angazovanje : listaA) {
                if(angazovanje.getStatus().equals("NOVI")){
                   db.sacuvajAngazovanje(angazovanje);
                }
                if(angazovanje.getStatus().equals("IZMENA")){
                   db.izmeniAngazovanje(angazovanje);
                }
                if(angazovanje.getStatus().equals("BRISANJE")){
                   db.obrisiAngazovanje(angazovanje);
                }
            }
            db.commit();
            sacuvano = true;
        } catch (SQLException ex) {
            Logger.getLogger(Kontroler.class.getName()).log(Level.SEVERE, null, ex);
            db.rollback();
        }
        
        db.zatvoriKonekciju();
        
        return sacuvano;
    }

    public ArrayList<BrojAngazovanjaPomocnaKlasa> vratiBrojAngazovanja() {
        db.otvoriKonekciju();
        
        ArrayList<BrojAngazovanjaPomocnaKlasa> lista = db.vratiBrojAngazovanje();
        
        db.zatvoriKonekciju();
        
        return lista;
    }

    public ArrayList<ProfesoriZvanjePomocnaKlasa> vratiBrojPoZvanju() {
        db.otvoriKonekciju();
        
        ArrayList<ProfesoriZvanjePomocnaKlasa> lista = db.vratiBrojPoZvanju();
        
        db.zatvoriKonekciju();
        
        return lista;
    }
}
