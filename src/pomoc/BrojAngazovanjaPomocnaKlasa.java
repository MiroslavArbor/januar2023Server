/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pomoc;

/**
 *
 * @author miroslavjovanovic
 */
public class BrojAngazovanjaPomocnaKlasa {
    private String ime;
    private String prezime;
    private String zvanje;
    private int brojAngazovanja;

    public BrojAngazovanjaPomocnaKlasa() {
    }

    public BrojAngazovanjaPomocnaKlasa(String ime, String prezime, String zvanje, int brojAngazovanja) {
        this.ime = ime;
        this.prezime = prezime;
        this.zvanje = zvanje;
        this.brojAngazovanja = brojAngazovanja;
    }

    public int getBrojAngazovanja() {
        return brojAngazovanja;
    }

    public void setBrojAngazovanja(int brojAngazovanja) {
        this.brojAngazovanja = brojAngazovanja;
    }

    public String getIme() {
        return ime;
    }

    public void setIme(String ime) {
        this.ime = ime;
    }

    public String getPrezime() {
        return prezime;
    }

    public void setPrezime(String prezime) {
        this.prezime = prezime;
    }

    public String getZvanje() {
        return zvanje;
    }

    public void setZvanje(String zvanje) {
        this.zvanje = zvanje;
    }
    
     
}
