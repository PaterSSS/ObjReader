package org.main.reader.exeptions;

public enum TypeOfError {
    MANY_VERTICES("many"),
    FEW_VERTICES("few"),
    MANY_IN_FACE("many face word"),
    FEW_IN_FACE("few face word");

    private final String textValue;

    TypeOfError(String textValue) {
        this.textValue = textValue;
    }
    public String getTextValue() {
        return textValue;
    }
}
