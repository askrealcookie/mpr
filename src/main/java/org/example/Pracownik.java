package org.example;

public class Pracownik {
    private String imieNazwisko;
    private String email;
    private String nazwaFirmy;
    private Stanowisko stanowisko;

    public Pracownik(String imieNazwisko,  String email, String nazwaFirmy, Stanowisko stanowisko) {
        this.imieNazwisko = imieNazwisko;
        this.email = email;
        this.nazwaFirmy = nazwaFirmy;
        this.stanowisko = stanowisko;
    }

    public String getImieNazwisko() {
        return imieNazwisko;
    }

    public void setImieNazwisko(String imieNazwisko) {
        this.imieNazwisko = imieNazwisko;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNazwaFirmy() {
        return nazwaFirmy;
    }

    public void setNazwaFirmy(String nazwaFirmy) {
        this.nazwaFirmy = nazwaFirmy;
    }

    public Stanowisko getStanowisko() {
        return stanowisko;
    }

    public void setStanowisko(Stanowisko stanowisko) {
        this.stanowisko = stanowisko;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pracownik p){
            return p.email.equals(this.email);
        }

        return false;
    }

    @Override
    public int hashCode() {
        return this.email.hashCode();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Pracownik(");
        sb.append("imie i nazwisko:").append(imieNazwisko);
        sb.append(", email:").append(email);
        sb.append(", nazwaFirmy:").append(nazwaFirmy);
        sb.append(", stanowisko:").append(stanowisko);
        sb.append(')');
        return sb.toString();
    }
}
