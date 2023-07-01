/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modeli;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;
import pomoc.BrojAngazovanjaPomocnaKlasa;

/**
 *
 * @author miroslavjovanovic
 */
public class ModelTabeleBrojAngazovanja extends AbstractTableModel{
    
    ArrayList<BrojAngazovanjaPomocnaKlasa> lista;

    public ModelTabeleBrojAngazovanja() {
        lista = new ArrayList<>();
    }

    
    
    @Override
    public int getRowCount() {
        return lista.size();
    }

    @Override
    public int getColumnCount() {
        return 4;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        BrojAngazovanjaPomocnaKlasa ba = lista.get(rowIndex);
        
        switch (columnIndex) {
            case 0: return ba.getIme();
            case 1: return ba.getPrezime();
            case 2: return ba.getZvanje();
            case 3: return ba.getBrojAngazovanja();
            default: return "N/A";
        }
    }

    @Override
    public String getColumnName(int column) {
        switch (column) {
            case 0: return "Ime";
            case 1: return "Prezime";
            case 2: return "Zvanje";
            case 3: return "Broj angazovanja";
            default: return "N/A";
        }
    }

    public void setLista(ArrayList<BrojAngazovanjaPomocnaKlasa> lista) {
        this.lista = lista;
        fireTableDataChanged();
    }
    
    
    
}
