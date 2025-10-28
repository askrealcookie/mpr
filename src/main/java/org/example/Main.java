package org.example;

import java.util.HashMap;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        PracownikKontroler kontroler = new PracownikKontroler();

        kontroler.dodaj(new Pracownik(
                "Jan Kowalski",
                "jankowalski@gmail.com",
                "MPR",
                Stanowisko.Prezes));
        kontroler.dodaj(new Pracownik(
                "Anna Nowak",
                "annanowak@gmail.com",
                "MPG",
                Stanowisko.Wiceprezes));
        kontroler.dodaj(new Pracownik(
                "Marek Grzyb",
                "marekgrzyb@gmail.com",
                "MPR",
                Stanowisko.Manager));
        kontroler.dodaj(new Pracownik(
                "Julia Żuk",
                "juliazuk@gmail.com",
                "MPR",
                Stanowisko.Programista));
        kontroler.dodaj(new Pracownik(
                "Stefan Śledź",
                "stefansledz@gmail.com",
                "MPG",
                Stanowisko.Programista));
        kontroler.dodaj(new Pracownik(
                "Katarzyna Widmo",
                "katarzynawidmo@gmail.com",
                "MPG",
                Stanowisko.Stazysta));
        kontroler.dodaj(new Pracownik(
                "Andrzej Biały",
                "andrzejbialy@gmail.com",
                "MPG",
                Stanowisko.Stazysta));

        System.out.println("Wszyscy:");
        for (Pracownik p : kontroler.wszyscy()) {
            System.out.println(p.toString());
        }

        System.out.println("Wszyscy w firmie MPR:");
        for (Pracownik p : kontroler.wszyscyWFirmie("MPG")) {
            System.out.println(p.toString());
        }

        System.out.println("Wszyscy programiści:");
        for (Pracownik p : kontroler.wszyscyGrupowaniePoStanowisku().get(Stanowisko.Programista)) {
            System.out.println(p.toString());
        }

        Map<Stanowisko, Integer> map = kontroler.zliczPracownikowNaStanowiskach();
        for (Map.Entry<Stanowisko, Integer> entry : map.entrySet()) {
            Stanowisko s = entry.getKey();
            Integer value = entry.getValue();
            System.out.print(s);
            System.out.print(": ");
            System.out.print(value);
            System.out.println(" ");
        }

        System.out.println("najlepiej zarabiajacy:");
        System.out.println(kontroler.znajdzNajlepiejZarabiajacego());

        System.out.println("pracownicy alfabetycznie nazwiskami:");
        for(Pracownik p : kontroler.pracownicyPosortowaniPoNazwisku()) {
            System.out.println(p);
        }

        System.out.println("Średnie wynagrodzenie w organizacji:");
        System.out.println(kontroler.obliczSrednieWynagrodzenie());
    }
}