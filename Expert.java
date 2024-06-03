package EmployeeSpecificPositions;

import EmployeeModel.Employee;

public class Expert extends Employee {

    private String position;
    private String expertise;
    private String id;
    private String department;
    private String division;
    private static final CorrectStructureController structureController;
    private static int totalInstancesCounter;

    static {
        structureController = new CorrectStructureController();
    }

    public Expert(String name, String surname, String dateOfBirth, double experienceInYears,
                  String position, String expertise) throws Exception {
        super(name, surname, dateOfBirth, experienceInYears);
        structureController.positionAndExpertiseChecker(position, expertise);
        expertDesigner(position, expertise);
        setId();
        levelSpecificSalaryBonus();
        levelSpecificAssets();
        totalInstancesCounter++;
    }

    public static int getTotalInstancesCounter() {
        return totalInstancesCounter;
    }

    public String getPosition() {
        return this.position;
    }

    private void setPosition(String position) {
        this.position = position;
    }

    public String getExpertise() {
        return this.expertise;
    }

    private void setExpertise(String expertise) {
        this.expertise = expertise;
    }

    public String fullId() {
        return this.id;
    }

    private void setId() {
        this.id = super.id()
                .concat(String.valueOf((Character.getNumericValue((this.getDepartment().charAt(0)))))
                        .concat(String.valueOf(totalInstancesCounter)));
    }

    public String getDepartment() {
        return this.department;
    }

    private void setDepartment(String department) {
        this.department = department;
    }

    public String getDivision() {
        if (this.division != null) {
            return this.division;
        }
        return this.department;
    }

    private void setDivision(String division) {
        this.division = division;
    }

    private void levelSpecificSalaryBonus() {
        double seniorityCoefficient = 0;

        if (super.getLevel() == 1) {
            return;
        }
        else if (super.getLevel() == 2) {
            seniorityCoefficient = 1.5;
        }
        else if (super.getLevel() == 3) {
            seniorityCoefficient = 2;
        }
        else if (super.getLevel() == 4) {
            seniorityCoefficient = 2.5;
        }

        double salaryBonus = super.getSalaryBase() * (seniorityCoefficient * (super.getLevel() + 1));
        senioritySpecificSalaryAggregation(salaryBonus);
    }

    private void levelSpecificAssets() {
        super.senioritySpecificAssetsAllocation(super.getLevel());
    }

    private void expertDesigner(String position, String expertise) throws IllegalArgumentException {
        String positionToUpperCase = position.toUpperCase();
        String expertiseToUpperCase = expertise.toUpperCase();
        if (positionToUpperCase.equals("DIRECTOR")) {
            if (structureController.checkDirectorPositionVacancy(expertiseToUpperCase)) {
                throw new IllegalArgumentException("This director position already exists!");
            }
        }

        String expertPosition = expertiseToUpperCase + " " + positionToUpperCase;
        String division = expertiseToUpperCase + " Direction";
        String directorPosition;
        String companyDepartment;

        switch (expertiseToUpperCase) {
            case "GENERAL MANAGER":
                companyDepartment = "HEAD";
                directorPosition = "CEO";
                directorProfile(companyDepartment, directorPosition, expertiseToUpperCase, directorPosition);  // director position as a last argument instead of positionToUpperCase, because of level assignment in directorProfile method;
                structureController.occupiedDirectorPositionsUpdater(directorPosition, true);
                break;
            case "OPERATIONS":
            case "ADMINISTRATION":
            case "LEGAL":
                companyDepartment = "Operations";
                if (positionToUpperCase.equals("DIRECTOR")) {
                    directorPosition = "COO";
                    directorProfile(companyDepartment, directorPosition, expertiseToUpperCase, positionToUpperCase);
                    structureController.occupiedDirectorPositionsUpdater(directorPosition, true);
                } else {
                    expertProfile(companyDepartment, expertPosition, expertiseToUpperCase, positionToUpperCase, division);
                }
                break;
            case "TECHNICAL":
                companyDepartment = "Technical Department";
                if (positionToUpperCase.equals("DIRECTOR")) {
                    directorPosition = "CTO";
                    directorProfile(companyDepartment, directorPosition, expertiseToUpperCase, positionToUpperCase);
                    structureController.occupiedDirectorPositionsUpdater(directorPosition, true);
                } else {
                    expertProfile(companyDepartment, expertPosition, expertiseToUpperCase, positionToUpperCase, division);
                }
                break;
            case "MARKETING":
            case "SALES":
                companyDepartment = "Marketing and Sales";
                if (positionToUpperCase.equals("DIRECTOR")) {
                    directorPosition = "CMO";
                    directorProfile(companyDepartment, directorPosition, expertiseToUpperCase, positionToUpperCase);
                    structureController.occupiedDirectorPositionsUpdater(directorPosition, true);
                } else {
                    expertProfile(companyDepartment, expertPosition, expertiseToUpperCase, positionToUpperCase, division);
                }
                break;
            case "FINANCE":
                companyDepartment = "Financial Department";
                if (positionToUpperCase.equals("DIRECTOR")) {
                    directorPosition = "CFO";
                    directorProfile(companyDepartment, directorPosition, expertiseToUpperCase, positionToUpperCase);
                    structureController.occupiedDirectorPositionsUpdater(directorPosition, true);
                } else {
                    expertProfile(companyDepartment, expertPosition, expertiseToUpperCase, positionToUpperCase, division);
                }
                break;
            default:
                throw new IllegalArgumentException("Invalid input for expertise: " + "\"" + expertise + "\"" + "!");
        }
    }

    private void expertProfile(String department, String expertPosition, String expertise, String position, String division) {
        directorProfile(department, expertPosition, expertise, position);
        setDivision(division);
    }

    private void directorProfile(String department, String directorPosition, String expertise, String position) {
        setDepartment(department);
        setPosition(directorPosition);
        setExpertise(expertise);

        switch (position) {
            case "OPERATOR":
                super.setLevel(1);
                break;
            case "MANAGER":
                super.setLevel(2);
                break;
            case "DIRECTOR":
                super.setLevel(3);
                break;
            default:
                super.setLevel(4);
                break;
        }
    }

    @Override
    public String toString() {
        return this.getName() + " " + this.getSurname()
                + " " + this.fullId()
                + " " + super.getEMailAddress();
    }
}
