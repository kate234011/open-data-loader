package ru.fns.suppliers.cdi;

public enum LawType {
    UNKNOWN_LAW("0"),
    FZ44("1"),
    FZ223("2"),
    PP65("3");

    private final String value;

    LawType(String value) {
        this.value = value;
    }

    public static LawType fromString(String value) {
        LawType returnValue = null;
        LawType[] lawsArray = LawType.values();

        for (LawType lawType : lawsArray) {
            if (lawType.value.equals(value)) {
                returnValue = lawType;
            }
        }

        return returnValue != null ? returnValue : UNKNOWN_LAW;
    }
}
