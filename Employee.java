package EmployeeModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.*;

public class Employee implements CompanyStaff, Cloneable {

    private String name;
    private String surname;
    private Date dateOfBirth;
    private double experienceInYears;
    private int level;
    private String id;
    private String eMailAddress;
    private double salary;
    private static final int SALARY_BASE;
    private List<String> assets;
    private List<Employee> subordinates;
    private Employee superior;
    private static final AssetsAllocator ALLOCATOR;
    private static int totalCountOfEmployees;

    static {
        SALARY_BASE = 1000;
        ALLOCATOR = new AssetsAllocator();
    }

    public Employee(String name, String surname, String dateOfBirth, double experienceInYears) throws Exception {
        setName(name);
        setSurname(surname);
        setDateOfBirth(dateOfBirth);
        setExperienceInYears(experienceInYears);
        int defaultEmployeeLevel = 1;
        setLevel(defaultEmployeeLevel);
        setUniqueEmployeeId();
        setEmailAddress();
        setSalary();
        setAssets();
        subordinates = new ArrayList<>();
        superior = null;
        totalCountOfEmployees++;
    }

    public static int getTotalCountOfEmployees() {
        return totalCountOfEmployees;
    }

    public String getName() {
        return this.name;
    }

    private void setName(String name) throws IllegalArgumentException {
        nameControl(name);
        this.name = name;
    }

    public String getSurname() {
        return this.surname;
    }

    private void setSurname(String surname) throws IllegalArgumentException {
        nameControl(surname);
        this.surname = surname;
    }

    public String getDateOfBirth() {
        return DateFormat.getDateInstance().format(dateOfBirth);
    }

    private void setDateOfBirth(String birthDate) throws ParseException, IllegalArgumentException {
        this.dateOfBirth = LegalAgeChecker.checkAge(birthDate);
    }

    private void setExperienceInYears(double experienceInYears) throws IllegalArgumentException {
        if (experienceInYears < 0) {
            throw new IllegalArgumentException("Experience cannot be a negative number!");
        }
        this.experienceInYears = experienceInYears;
    }

    protected int getLevel() {
        return this.level;
    }

    protected void setLevel(int level) {
        this.level = level;
    }

    private void setUniqueEmployeeId() {
        this.id = String.valueOf(companyId / 100)
                .concat(String.valueOf(totalCountOfEmployees));
    }

    public String getEMailAddress() {
        return this.eMailAddress;
    }

    private void setEmailAddress() {
        String uniqueEmployeeID = this.getSurname().concat(".")
                .concat(this.getName().substring(0, 2))
                .concat("_").concat(this.id);
        this.eMailAddress = CompanyStaff.emailDomain(uniqueEmployeeID);
    }

    private void setSalary() {
        this.salary = salaryCalculator();
    }

    public List<String> getAssets() {
        return new ArrayList<>(this.assets);
    }

    private void setAssets() {
        this.assets = new ArrayList<>(ALLOCATOR.defaultAssetsAllocator());
    }

    public List<Employee> getSubordinates() throws CloneNotSupportedException {
       List<Employee> cloned = new ArrayList<>();
        for (Employee original : this.subordinates) {
            cloned.add((Employee) original.clone());
        }
        return cloned;
    }

    public void setSubordinates(List<Employee> subordinates, Employee superior) throws IllegalArgumentException {
        subordinates.forEach(employee -> {
            try {
                superior.setSubordinates(employee, this);
            } catch (CloneNotSupportedException e) {
                e.printStackTrace();
            }
        });
    }

    public void setSubordinates(Employee subordinate, Employee superior) throws IllegalArgumentException, CloneNotSupportedException {
        if (this != superior) {
            throw new IllegalArgumentException("Invalid input! Employee can only set subordinates to itself.");        
        }
        if (subordinate.level >= this.level) {
            throw new IllegalArgumentException("Invalid input! Subordinate cannot be equal or higher " +
                    "in hierarchy than it's superior! " + subordinate);
        }
        if (checkSuperior(subordinate)) {
            throw new IllegalArgumentException("Invalid input! This subordinate already has a superior!");
        }

        Employee clonedSubordinate = (Employee) subordinate.clone();
        this.subordinates.add(clonedSubordinate);
        subordinate.setSuperior(superior, subordinate);
    }

    public Employee getSuperior() throws CloneNotSupportedException {
        return superior != null ? (Employee) superior.clone() : null;
    }

    public void setSuperior(Employee superior, Employee subordinate) throws IllegalArgumentException, CloneNotSupportedException {
        if (this != subordinate) {
            throw new IllegalArgumentException("Invalid input! Employee can only set superior to itself.");
        }
        if (superior.level <= subordinate.level || checkSuperior(subordinate)) {
            throw new IllegalArgumentException("Invalid input! Superior is either lower in hierarchy " +
                    "than it's subordinate or this subordinate already has a superior!");
        }

        subordinate.superior = (Employee) superior.clone();
        Employee clonedSubordinate = (Employee) subordinate.clone();

        if (!checkSubordinate(superior, this)) {
            superior.subordinates.add(clonedSubordinate);
        }
    }

    private boolean checkSubordinate(Employee superior, Employee subordinate) {
        return superior.subordinates.stream().anyMatch(x -> x.equals(subordinate));
    }

    private boolean checkSuperior(Employee subordinate) {
        return subordinate.superior != null;
    }

    protected int getSalaryBase() {
        return SALARY_BASE;
    }

    public double checkRandomEmployeeSalary(Employee person) throws Exception {
        if ((this == person) || (this.level > 2 && this.level > person.level)) {
            return person.salary();
        }
        throw new Exception("Unauthorized access! " +
                "Your position does not provide access to this information.");
    }

    protected void senioritySpecificAssetsAllocation(int level) {
        ALLOCATOR.bonusAssetsAllocator(level, this.assets);
    }

    protected void senioritySpecificSalaryAggregation(double salaryBonus) {
        this.salary += salaryBonus;
    }

    private void nameControl(String name) throws IllegalArgumentException {
        if (name.length() < 3) {
            throw new IllegalArgumentException("Name and Surname cannot be shorter " +
                    "than 3 symbols each!");
        }

        for (int i = 0; i < name.length(); i++) {
            char letter = name.charAt(i);
            if (!Character.isAlphabetic(letter)) {
                throw new IllegalArgumentException("Name and Surname must contain only " +
                        "letters from the Latin alphabet!");
            }
        }
    }

    private double salaryCalculator () {
        double experienceCoefficient = 1;

        if (this.experienceInYears > 5 && this.experienceInYears <= 10) {
            experienceCoefficient = 1.5;
        }
        else if(this.experienceInYears > 10 && this.experienceInYears <= 20) {
            experienceCoefficient = 2;
        }
        else if(this.experienceInYears > 20) {
            experienceCoefficient = 2.5;
        }

        return (SALARY_BASE * ((1 + (experienceInYears / 10) * experienceCoefficient)));
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        Employee clonedEmployee = (Employee) super.clone();

        clonedEmployee.assets = new ArrayList<>(this.assets);
        List<Employee> cloned = new ArrayList<>();
        for (Employee original : subordinates) {
            Employee copy = (Employee) original.clone();
            cloned.add(copy);
        }
        clonedEmployee.subordinates = cloned;

        if (superior != null) {
            clonedEmployee.superior = (Employee) this.superior.clone();
        }
        else {
            clonedEmployee.superior = null;
        }

        return clonedEmployee;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || this.getClass() != obj.getClass()) {
            return false;
        }
        Employee employee = (Employee) obj;

        return this.name.equals(employee.name)
                && this.dateOfBirth.equals(employee.dateOfBirth)
                && this.eMailAddress.equals(employee.eMailAddress);
    }

    @Override
    public String toString() {
        return this.getName() + " "
                + this.getSurname() + " "
                + this.id() + " "
                + this.getEMailAddress();
    }

    @Override
    public String id() {
        return this.id;
    }

    @Override
    public double salary() {
        return this.salary;
    }
}
