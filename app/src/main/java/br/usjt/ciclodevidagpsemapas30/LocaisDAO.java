package br.usjt.ciclodevidagpsemapas30;

import java.util.ArrayList;
import java.util.List;

public class LocaisDAO {

    private final static List<Localizacao> locais = new ArrayList<>(50);


    public void salva(Localizacao localizacao) {
        locais.add(localizacao);
    }


    public List<Localizacao> dados() {
        return new ArrayList<>(locais);
    }
}
