package org.example;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PracownikKontroler {
    private final Map<String, Pracownik> pracownicy = new HashMap<>();

    public void dodaj(Pracownik pracownik) {
        String email = pracownik.getEmail();

        if(pracownicy.containsKey(email)) {
            System.out.println("istnieje juz pracownik z takim eamilem");
            return;
        }

        pracownicy.put(email, pracownik);
    }

    public List<Pracownik> wszyscy() {
        return List.copyOf(pracownicy.values());
    }

    public List<Pracownik> wszyscyWFirmie(String firma) {
        List<Pracownik> pracownicy = new ArrayList<>();

        for(Pracownik pracownik : wszyscy()) {
            if(pracownik.getNazwaFirmy().equals(firma)) {
                pracownicy.add(pracownik);
            }
        }

        return pracownicy;
    }

    public Map<Stanowisko, List<Pracownik>> wszyscyGrupowaniePoStanowisku() {
        Map<Stanowisko, List<Pracownik>> map = new HashMap<>();

        for(Pracownik pracownik : wszyscy()) {
            if(map.containsKey(pracownik.getStanowisko())) {
                map.get(pracownik.getStanowisko()).add(pracownik);
            } else {
                map.put(pracownik.getStanowisko(), new ArrayList<>());
                map.get(pracownik.getStanowisko()).add(pracownik);
            }
        }

        return map;
    }

    public Map<Stanowisko, Integer> zliczPracownikowNaStanowiskach() {
        Map<Stanowisko, Integer> map = new HashMap<>();
        for(Pracownik pracownik : wszyscy()) {
            if(map.containsKey(pracownik.getStanowisko())) {
                map.replace(pracownik.getStanowisko(), map.get(pracownik.getStanowisko())+1);
            } else {
                map.put(pracownik.getStanowisko(), 1);
            }
        }

        return map;
    }

    public Pracownik znajdzNajlepiejZarabiajacego() {
        if (pracownicy.isEmpty()) {
            return null;
        }
        Pracownik najlepszy = (Pracownik) pracownicy.values().toArray()[0];
        for (Pracownik p : pracownicy.values()) {
            if (p.getStanowisko().getPensja() > najlepszy.getStanowisko().getPensja()) {
                najlepszy = p;
            }
        }
        return najlepszy;
    }

    public List<Pracownik> pracownicyPosortowaniPoNazwisku() {
        List<Pracownik> posortowani = new ArrayList<>(pracownicy.values());
        posortowani.sort(new Comparator<Pracownik>() {
            @Override
            public int compare(Pracownik p1, Pracownik p2) {
                return pobierzNazwisko(p1.getImieNazwisko()).compareToIgnoreCase(pobierzNazwisko(p2.getImieNazwisko()));
            }
        });
        return posortowani;
    }

    private static String pobierzNazwisko(String imieNazwisko) {
        String[] tab = imieNazwisko.split(" ");
        return tab[tab.length - 1];
    }

    public double obliczSrednieWynagrodzenie() {
        if (pracownicy.isEmpty()) {
            return 0.0;
        }

        double suma = 0.0;
        for (Pracownik pracownik : pracownicy.values()) {
            suma += pracownik.getStanowisko().getPensja();
        }

        return suma / pracownicy.size();
    }
}
