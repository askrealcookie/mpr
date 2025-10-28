package org.example;

public enum Stanowisko {
    Prezes(25000),
    Wiceprezes(18000),
    Manager(12000),
    Programista(8000),
    Stazysta(3000);

    private final double pensja;

    Stanowisko(double stawka) {
        this.pensja = stawka;
    }

    public double getPensja() {
        return pensja;
    }
}
