package org.example;

import java.util.Map;

public class Main {
    public static void main(String[] args) {
        EmployeesService service = new EmployeesService();

        service.add(new Employee(
                "Jan",
                "Kowalski",
                "jankowalski@gmail.com",
                "MPR",
                Position.Prezes));
        service.add(new Employee(
                "Anna",
                "Nowak",
                "annanowak@gmail.com",
                "MPG",
                Position.Wiceprezes));
        service.add(new Employee(
                "Marek",
                "Grzyb",
                "marekgrzyb@gmail.com",
                "MPR",
                Position.Manager));
        service.add(new Employee(
                "Julia",
                "Żuk",
                "juliazuk@gmail.com",
                "MPR",
                Position.Programista));
        service.add(new Employee(
                "Stefan",
                "Śledź",
                "stefansledz@gmail.com",
                "MPG",
                Position.Programista));
        service.add(new Employee(
                "Katarzyna",
                "Widmo",
                "katarzynawidmo@gmail.com",
                "MPG",
                Position.Stazysta));
        service.add(new Employee(
                "Andrzej",
                "Biały",
                "andrzejbialy@gmail.com",
                "MPG",
                Position.Stazysta));

        System.out.println("Wszyscy:");
        for (Employee p : service.all()) {
            System.out.println(p.toString());
        }

        System.out.println("Wszyscy w firmie MPR:");
        for (Employee p : service.allInCompany("MPG")) {
            System.out.println(p.toString());
        }

        System.out.println("Wszyscy programiści:");
        for (Employee p : service.allGroupByPosition().get(Position.Programista)) {
            System.out.println(p.toString());
        }

        Map<Position, Integer> map = service.countEmployeesByPositions();
        for (Map.Entry<Position, Integer> entry : map.entrySet()) {
            Position s = entry.getKey();
            Integer value = entry.getValue();
            System.out.print(s);
            System.out.print(": ");
            System.out.print(value);
            System.out.println(" ");
        }

        System.out.println("najlepiej zarabiajacy:");
        System.out.println(service.findEmployeeWithBestSalary());

        System.out.println("pracownicy alfabetycznie nazwiskami:");
        for(Employee p : service.employeesSortedByLastName()) {
            System.out.println(p);
        }

        System.out.println("Średnie wynagrodzenie w organizacji:");
        System.out.println(service.calculateAverageSalary());
    }
}