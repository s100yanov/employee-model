import EmployeeModel.Employee;
import EmployeeSpecificPositions.Expert;

import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) throws Exception {

        Employee Victor = new Employee("Victor", "Stefanov", "18/03/1985", 12.5);
        Employee Maria = new Employee("Maria", "Petrova", "14/04/1984", 15);
        Employee Alexander = new Employee("Alexander", "Yordanov", "26/03/1986", 14);
        Expert Daniel = new Expert("Daniel", "Stefanov", "18/10/1976", 23, "Operator", "Legal");
        Expert Boris = new Expert("Boris", "Dimitrov", "06/08/1991", 4, "Operator", "Technical");
        Expert Iva = new Expert("Iva", "Nikolova", "25/08/1993", 8.5, "Manager", "Legal");
        Expert Elena = new Expert("Elena", "Angelova", "27/09/1973", 21, "Director", "Operations");
        Expert Ivan = new Expert("Ivan", "Ivanov", "17/03/1964", 33, "Director", "General Manager");

        System.out.println("Victor's salary:   " + Victor.salary());
        System.out.println("Victor's assets:   " + Victor.getAssets());
        System.out.println("Iva's salary:   " + Iva.salary());
        System.out.println("Iva's assets:   " + Iva.getAssets());
        System.out.println("Elena's salary:   " + Elena.salary());
        System.out.println("Elena's assets:   " + Elena.getAssets());
        System.out.println("Ivan's salary:   " + Ivan.salary());
        System.out.println("Ivan's assets:   " + Ivan.getAssets());

        System.out.println("---------------------------------------------------------------");

        System.out.println("Maria:");
        System.out.println("Brief Profile : " + Maria);
        System.out.println("Date of Birth: " + Maria.getDateOfBirth());
        System.out.println("Boris:");
        System.out.println("ID: " + Boris.id());
        System.out.println("Full ID: " + Boris.fullId());
        System.out.println("E-Mail: " + Boris.getEMailAddress());
        System.out.println("Department: " + Boris.getDepartment());
        System.out.println("Division: " + Boris.getDivision());
        System.out.println("Position: " + Boris.getPosition());
        System.out.println("Iva:");
        System.out.println("Position: " + Iva.getPosition());
        System.out.println("Expertise: " + Iva.getExpertise());
        System.out.println("Department: " + Iva.getDepartment());
        System.out.println("Division: " + Iva.getDivision());

        System.out.println("---------------------------------------------------------------");

        List<Employee> subordinates = new ArrayList<>(List.of(Victor, Maria, Daniel));
        Iva.setSubordinates(subordinates, Iva);
        System.out.println("Victor's superior:   " + Victor.getSuperior());
        System.out.println("Iva's subordinates:   " + Iva.getSubordinates());

        Iva.setSuperior(Elena, Iva);
        Ivan.setSubordinates(Elena, Ivan);
        System.out.println("Iva's superior:   " + Iva.getSuperior());
        System.out.println("Ivan's subordinates:   " + Ivan.getSubordinates());
        Elena.setSubordinates(Alexander, Elena);
        System.out.println("Elena's subordinates:   " + Elena.getSubordinates());
        System.out.println("Alexander's superior:   " + Alexander.getSuperior());

        System.out.println("---------------------------------------------------------------");

        System.out.println("Director Elena is checking employee Boris' salary: " + Elena.checkRandomEmployeeSalary(Boris));

        System.out.println("---------------------------------------------------------------");

        System.out.println("Total count of Employees: " + Employee.getTotalCountOfEmployees());
        System.out.println("Total count of Experts: " + Expert.getTotalInstancesCounter());
    }
}
