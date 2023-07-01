/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package db;

import domen.Angazovanje;
import domen.Predmet;
import domen.Profesor;
import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import pomoc.BrojAngazovanjaPomocnaKlasa;
import pomoc.ProfesoriZvanjePomocnaKlasa;

/**
 *
 * @author Mafija
 */
public class DbBroker {
    Connection konekcija;
    
    public void otvoriKonekciju(){
        try {
            konekcija = DriverManager.getConnection("jdbc:mysql://localhost:6603/januar23", "root", "root");
            konekcija.setAutoCommit(false);
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void zatvoriKonekciju(){
        try {
            konekcija.close();
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void commit(){
        try {
            konekcija.commit();
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void rollback(){
        try {
            konekcija.rollback();
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public ArrayList<Profesor> vratiProfesore() {
        
        try {
            String upit = "SELECT * FROM profesor";
            
            Statement s = konekcija.createStatement();
            
            ResultSet rs = s.executeQuery(upit);
            
            ArrayList<Profesor> lista = new ArrayList<>();
            
            while (rs.next()) {                
                lista.add(new Profesor(rs.getInt("profesorID"), rs.getString("ime"), rs.getString("prezime"), rs.getString("zvanje"), rs.getString("emailKorisnika"), null));
            }
            
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public ArrayList<Predmet> vratiPredmete() {
        try {
            String upit = "SELECT * FROM predmet";
            
            Statement s = konekcija.createStatement();
            
            ResultSet rs = s.executeQuery(upit);
            
            ArrayList<Predmet> lista = new ArrayList<>();
            
            while (rs.next()) {                
                lista.add(new Predmet(rs.getString("sifraPredmeta"), rs.getString("nazivPredmeta"), rs.getString("kodPredmeta"), rs.getInt("brojESPB")));
            }
            
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public void sacuvajProfesora(Profesor profa) throws SQLException {
        String upit = "INSERT INTO profesor(ime, prezime, zvanje, emailKorisnika) VALUES (?,?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setString(1, profa.getIme());
        ps.setString(2, profa.getPrezime());
        ps.setString(3, profa.getZvanje());
        ps.setString(4, profa.getEmailKorisnika());
        ps.executeUpdate();
    }

    public void sacuvajAngazovanje(Angazovanje angazovanje) throws SQLException {
        String upit = "INSERT INTO angazovanje(sifraPredmeta, profesorID, datum, emailKorisnika) VALUES (?,?,?,?)";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setString(1, angazovanje.getPredmet().getSifraPredmeta());
        ps.setInt(2, angazovanje.getProfesor().getProfesorID());
        ps.setDate(3, new java.sql.Date(angazovanje.getDatum().getTime()));
        ps.setString(4, angazovanje.getEmailKorisnika());
        ps.executeUpdate();
    }

    public int vratiIdProfesora() {
         try {
            String upit = "SELECT MAX(profesorID) as max FROM profesor";
            
            Statement s = konekcija.createStatement();
            
            ResultSet rs = s.executeQuery(upit);
            
            
            while (rs.next()) {                
                return rs.getInt("max");
            }
                        
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 1;
    }

    public ArrayList<Angazovanje> vratiAngazovanje(Profesor prof) {
         try {
            String upit = "SELECT * FROM angazovanje a JOIN profesor p ON a.profesorID = p.profesorID JOIN predmet pr ON a.sifraPredmeta = pr.sifraPredmeta WHERE a.profesorID = " + prof.getProfesorID();
            
            Statement s = konekcija.createStatement();
            
            ResultSet rs = s.executeQuery(upit);
            
            ArrayList<Angazovanje> lista = new ArrayList<>();
            
            while (rs.next()) {    
                Predmet predmet = new Predmet(rs.getString("sifraPredmeta"), rs.getString("nazivPredmeta"), rs.getString("kodPredmeta"), rs.getInt("brojESPB"));
                Profesor profesor = new Profesor(rs.getInt("profesorID"), rs.getString("ime"), rs.getString("prezime"), rs.getString("zvanje"), rs.getString("emailKorisnika"), null);
                lista.add(new Angazovanje(rs.getInt("id"), predmet, profesor, new Date(rs.getDate("datum").getTime()), rs.getString("emailKorisnika"), ""));
            }
            return lista;
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public void izmeniAngazovanje(Angazovanje angazovanje) throws SQLException {
        String upit = "UPDATE angazovanje SET datum = ? WHERE id = ?";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setDate(1, new java.sql.Date(angazovanje.getDatum().getTime()));
        ps.setInt(2, angazovanje.getId());
        ps.executeUpdate();
    }

    public void obrisiAngazovanje(Angazovanje angazovanje) throws SQLException {
        String upit = "DELETE FROM angazovanje WHERE id = ?";
        PreparedStatement ps = konekcija.prepareStatement(upit);
        ps.setInt(1, angazovanje.getId());
        ps.executeUpdate();
    }

    public ArrayList<BrojAngazovanjaPomocnaKlasa> vratiBrojAngazovanje() {
        try {
            String upit = "SELECT ime, prezime, zvanje, count(a.id) as brojAngazovanja FROM profesor p JOIN angazovanje a on p.profesorID = a.profesorID GROUP BY a.profesorID;";
            
            Statement s = konekcija.createStatement();
            
            ResultSet rs = s.executeQuery(upit);
            
            ArrayList<BrojAngazovanjaPomocnaKlasa> lista = new ArrayList<>();
            
            while (rs.next()) {                
                lista.add(new BrojAngazovanjaPomocnaKlasa(rs.getString("ime"), rs.getString("prezime"), rs.getString("zvanje"), rs.getInt("brojAngazovanja")));
            }
            
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }

    public ArrayList<ProfesoriZvanjePomocnaKlasa> vratiBrojPoZvanju() {
        try {
            String upit = "SELECT zvanje, count(profesorID) as broj FROM profesor GROUP BY zvanje HAVING broj > 0;";
            
            Statement s = konekcija.createStatement();
            
            ResultSet rs = s.executeQuery(upit);
            
            ArrayList<ProfesoriZvanjePomocnaKlasa> lista = new ArrayList<>();
            
            while (rs.next()) {                
                lista.add(new ProfesoriZvanjePomocnaKlasa(rs.getString("zvanje"), rs.getInt("broj")));
            }
            
            return lista;
            
        } catch (SQLException ex) {
            Logger.getLogger(DbBroker.class.getName()).log(Level.SEVERE, null, ex);
        }
        return new ArrayList<>();
    }
}
