package org.example.service;

import org.example.model.Employee;

import java.time.LocalDate;
import java.time.Period;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SeniorityService {

    public int getYearsOfService(LocalDate hireDate, LocalDate now) {
        if (hireDate == null || now == null) {
            throw new IllegalArgumentException("dates null");
        }
        if (hireDate.isAfter(now)) {
            return 0;
        }
        return Period.between(hireDate, now).getYears();
    }

    public List<Employee> filterBySeniorityRange(Map<Employee, LocalDate> hireDates, int minYears, int maxYears, LocalDate now) {
        List<Employee> result = new ArrayList<>();
        for (Map.Entry<Employee, LocalDate> e : hireDates.entrySet()) {
            int years = getYearsOfService(e.getValue(), now);
            if (years >= minYears && years <= maxYears) {
                result.add(e.getKey());
            }
        }
        return result;
    }

    public List<Employee> findJubilees(Map<Employee, LocalDate> hireDates, LocalDate now) {
        List<Employee> result = new ArrayList<>();
        for (Map.Entry<Employee, LocalDate> e : hireDates.entrySet()) {
            int years = getYearsOfService(e.getValue(), now);
            if (years > 0 && years % 5 == 0) {
                result.add(e.getKey());
            }
        }
        return result;
    }

    public Map<Integer, Long> buildSeniorityReport(Map<Employee, LocalDate> hireDates, LocalDate now) {
        Map<Integer, Long> report = new HashMap<>();
        for (Map.Entry<Employee, LocalDate> e : hireDates.entrySet()) {
            int years = getYearsOfService(e.getValue(), now);
            Long count = report.get(years);
            if (count == null) {
                report.put(years, 1L);
            } else {
                report.put(years, count + 1);
            }
        }
        return report;
    }
}

