/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pomoc;

/**
 *
 * @author miroslavjovanovic
 */
public class ProfesoriZvanjePomocnaKlasa {
    private String zvanje;
    private int broj;

    public ProfesoriZvanjePomocnaKlasa() {
    }

    public ProfesoriZvanjePomocnaKlasa(String zvanje, int broj) {
        this.zvanje = zvanje;
        this.broj = broj;
    }

    public int getBroj() {
        return broj;
    }

    public void setBroj(int broj) {
        this.broj = broj;
    }

    public String getZvanje() {
        return zvanje;
    }

    public void setZvanje(String zvanje) {
        this.zvanje = zvanje;
    }
    
    
}
