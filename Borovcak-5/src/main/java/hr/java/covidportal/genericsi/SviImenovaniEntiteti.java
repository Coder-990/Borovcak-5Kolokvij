package main.java.hr.java.covidportal.genericsi;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import main.java.hr.java.covidportal.model.ImenovaniEntitet;

import java.util.ArrayList;
import java.util.List;

/**
 *PETI-3
 */
@Data
@EqualsAndHashCode
@ToString
public class SviImenovaniEntiteti <E extends ImenovaniEntitet>{

    private List<E> listaSvihImenovanihEntiteta;

    public SviImenovaniEntiteti() {
        this.listaSvihImenovanihEntiteta = new ArrayList<E>();
    }

    public void dodajImenovaniEntitet(E uneseniEntitet){
        if(!listaSvihImenovanihEntiteta.contains(uneseniEntitet)){
            listaSvihImenovanihEntiteta.add(uneseniEntitet);
        }
    }
}
