/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import pomoc.BrojAngazovanjaPomocnaKlasa;
import pomoc.ProfesoriZvanjePomocnaKlasa;

/**
 *
 * @author miroslavjovanovic
 */
public class ModelTabeleBrojPoZvanju extends AbstractTableModel{
    
    ArrayList<ProfesoriZvanjePomocnaKlasa> lista;

    public ModelTabeleBrojPoZvanju() {
        lista = new ArrayList<>();
    }

    
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 2;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        ProfesoriZvanjePomocnaKlasa ba = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return ba.getZvanje();
            case 1: return ba.getBroj();
            default: return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Zvanje";
            case 1: return "Broj Profesora";
            default: return "N/A";
        }
    }

    public void setLista(ArrayList<ProfesoriZvanjePomocnaKlasa> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
    
    
    
}
