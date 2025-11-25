package org.example.service;

import org.example.model.Employee;

import java.util.*;
import java.util.stream.Collectors;

public class EmployeeRatingService {

    private final Map<Employee, List<Integer>> ratings = new HashMap<>();

    public void addRating(Employee employee, int rating) {
        if (employee == null) {
            throw new IllegalArgumentException("employeee null");
        }
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("rating out of range");
        }
        ratings.computeIfAbsent(employee, e -> new ArrayList<>()).add(rating);
    }

    public List<Integer> getRatings(Employee employee) {
        List<Integer> list = ratings.get(employee);
        if (list == null) {
            return Collections.emptyList();
        }
        return new ArrayList<>(list);
    }

    public double getAverageRating(Employee employee) {
        List<Integer> list = ratings.get(employee);
        if (list == null || list.isEmpty()) {
            return 0.0;
        }
        int sum = 0;
        for (int r : list) {
            sum += r;
        }
        return (double) sum / list.size();
    }

    public List<Employee> getTopRatedEmployees() {
        Map<Employee, Double> averages = new HashMap<>();
        for (Map.Entry<Employee, List<Integer>> entry : ratings.entrySet()) {
            List<Integer> list = entry.getValue();
            if (list.isEmpty()) {
                continue;
            }
            int sum = 0;
            for (int r : list) {
                sum += r;
            }
            double avg = (double) sum / list.size();
            averages.put(entry.getKey(), avg);
        }
        if (averages.isEmpty()) {
            return Collections.emptyList();
        }
        double max = Collections.max(averages.values());
        List<Employee> result = new ArrayList<>();
        for (Map.Entry<Employee, Double> e : averages.entrySet()) {
            if (Double.compare(e.getValue(), max) == 0) {
                result.add(e.getKey());
            }
        }
        return result;
    }

    public Map<Employee, List<Integer>> getAllRatings() {
        Map<Employee, List<Integer>> copy = new HashMap<>();
        for (Map.Entry<Employee, List<Integer>> e : ratings.entrySet()) {
            copy.put(e.getKey(), new ArrayList<>(e.getValue()));
        }
        return copy;
    }
}