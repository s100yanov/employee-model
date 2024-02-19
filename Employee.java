package EmployeeModel;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class Employee implements CompanyStaff {

    private String name;
    private String surname;
    private Date dateOfBirth;
    private double experienceInYears;
    private int level;
    private String id;
    private String eMailAddress;
    private double salary;
    private int salaryBase = 1000;
    private List<String> assets;
    private List<Employee> subordinates;
    private Employee superior;
    private static AssetsAllocator allocator;
    private static int totalCountOfEmployees;

    static {
        allocator = new AssetsAllocator();
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
        return Collections.unmodifiableList(this.assets);
    }

    private void setAssets(){
        this.assets = new ArrayList<>(allocator.defaultAssetsAllocator());
    }

    public List<Employee> getSubordinates() {
        return Collections.unmodifiableList(subordinates);
    }

    public void setSubordinates(List<Employee> subordinates) throws IllegalArgumentException {
        for (Employee person : subordinates) {
            setSubordinates(person);
        }
    }

    public void setSubordinates(Employee subordinate) throws IllegalArgumentException {
        if (subordinate.level >= this.level) {
            throw new IllegalArgumentException("Subordinate cannot be equal or higher " +
                    "in hierarchy than it's superior!");
        }
        subordinates.add(subordinate);
        setSuperior(this, subordinate);
    }

    public Employee getSuperior() {
        return this.superior;
    }

    public void setSuperior(Employee superior, Employee subordinate) throws IllegalArgumentException {
        if (this != subordinate && this != superior) {
            throw new IllegalArgumentException("Invalid input! Employee can only set superior to himself.");
        }
        if (superior.level <= subordinate.level || subordinate.getSuperior() != null) {
            throw new IllegalArgumentException("Invalid input! Superior is either lower in hierarchy " +
                    "than it's subordinate or this subordinate already has a superior!");
        }
        subordinate.superior = superior;
    }

    protected int getSalaryBase() {
        return this.salaryBase;
    }

    public double checkRandomEmployeeSalary(Employee person) throws Exception {
        if ((this == person) || (this.level > 2 && this.level > person.level)) {
            return person.salary();
        }
        throw new Exception("Unauthorized access! " +
                "Your position does not have access to this information.");
    }

    protected void senioritySpecificAssetsAllocation(int level) {
        allocator.bonusAssetsAllocator(level, this.assets);
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
        else if(this.experienceInYears > 10 && this.experienceInYears <= 20){
            experienceCoefficient = 2;
        }
        else if(this.experienceInYears > 20) {
            experienceCoefficient = 2.5;
        }

        return (salaryBase * ((1 + (experienceInYears / 10) * experienceCoefficient)));
    }

    @Override
    public String id() { return this.id; }

    @Override
    public double salary() { return this.salary; }

    public String toString() {
        return this.getName() + " "
                + this.getSurname() + " "
                + this.id() + " "
                + this.getEMailAddress();
    }
}
