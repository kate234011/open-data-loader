package ru.fns.suppliers.model;

import ru.fns.suppliers.cdi.LawType;
import java.util.List;

public class Path {

    private LawType lawType;

    private List<String> listPath;

    public void setLawType(LawType lawType) {
        this.lawType = lawType;
    }

    public void setListPath(List<String> listPath) {
        this.listPath = listPath;
    }

    public List<String> getListPath() {
        return listPath;
    }

    public LawType getTypeLaw() {
        return lawType;
    }
}
