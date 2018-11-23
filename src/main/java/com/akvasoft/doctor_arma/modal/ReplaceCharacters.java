package com.akvasoft.doctor_arma.modal;

public class ReplaceCharacters {
    public String replace(String text) {
        text = text.replace("ı", "i");
        text = text.replace("ğ", "g");
        text = text.replace("İ", "I");
        text = text.replace("Ş", "S");
        text = text.replace("Ğ", "G");
        text = text.replace("ş", "s");
        text = text.replace("c", "c");
        text = text.replace("Ö", "O");
        text = text.replace("Ç", "C");
        text = text.replace("Ü", "U");
        text = text.replace("Ğ", "G");
        text = text.replace("ü", "u");
        text = text.replace("ç", "c");
        text = text.replace("ö", "o");
        return text;
    }
    public String replaceToThurky(String text) {
        text = text.replace("i", "ı");
        text = text.replace("g", "ğ");
        text = text.replace("I", "İ");
        text = text.replace("S", "Ş");
        text = text.replace("G", "Ğ");
        text = text.replace("s", "ş");
        text = text.replace("c", "c");
        text = text.replace("O", "Ö");
        text = text.replace("C", "Ç");
        text = text.replace("U", "Ü");
        text = text.replace("G", "Ğ");
        text = text.replace("u", "ü");
        text = text.replace("c", "ç");
        text = text.replace("o", "ö");
        return text;
    }
}
