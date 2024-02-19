package EmployeeSpecificPositions;

import java.util.HashMap;

class CorrectStructureController {

    private HashMap<String, Boolean> occupiedDirectorPositions;

    CorrectStructureController() {
       initializationOfPositionsHashMap();
    }

    void positionAndExpertiseChecker(String position, String expertise) throws IllegalArgumentException {
        checkPosition(position.toUpperCase());
        checkExpertise(expertise.toUpperCase());
    }

    private void checkPosition(String position) throws IllegalArgumentException {
        if (position.equals("DIRECTOR") || position.equals("MANAGER")
            || position.equals("OPERATOR")) {
            return;
        }
        throw new IllegalArgumentException("Invalid input for position: " + "\"" + position + "\"" + "!");
    }

    private void checkExpertise(String expertise) {
        switch (expertise) {
            case "GENERAL MANAGER":
            case "OPERATIONS":
            case "ADMINISTRATION":
            case "LEGAL":
            case "TECHNICAL":
            case "MARKETING":
            case "SALES":
            case "FINANCE":
                return;
            default:
                throw new IllegalArgumentException("Invalid input for expertise: " + "\"" + expertise + "\"" + "!");
        }
    }

    boolean checkDirectorPositionVacancy(String expertise) {
        switch (expertise) {
            case "GENERAL MANAGER":
                return (occupiedDirectorPositions.get("CEO"));
            case "OPERATIONS":
            case "ADMINISTRATION":
            case "LEGAL":
                return (occupiedDirectorPositions.get("COO"));
            case "TECHNICAL":
                return (occupiedDirectorPositions.get("CTO"));
            case "MARKETING":
            case "SALES":
                return (occupiedDirectorPositions.get("CMO"));
            case "FINANCE":
                return (occupiedDirectorPositions.get("CFO"));
            default:
                throw new IllegalArgumentException("This message should normally be unreachable! " +
                        "Something is wrong either with the input for expertise, " +
                        "or with the use/work of occupiedDirectorPositionsUpdater method.");
        }
    }

    void occupiedDirectorPositionsUpdater(String key, boolean value) {
        occupiedDirectorPositions.put(key, value);
    }

    private void initializationOfPositionsHashMap() {
        occupiedDirectorPositions = new HashMap<>();
        occupiedDirectorPositions.put("CEO", false);
        occupiedDirectorPositions.put("COO", false);
        occupiedDirectorPositions.put("CTO", false);
        occupiedDirectorPositions.put("CMO", false);
        occupiedDirectorPositions.put("CFO", false);
    }
}
