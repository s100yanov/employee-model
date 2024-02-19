package EmployeeModel;

public interface CompanyStaff {

    String companyName = "NextGenTelecom";
    int companyId = 4108527;

    static String emailDomain(String uniqueEmployeeID) {
        return uniqueEmployeeID.concat("@").concat(companyName);
    }

    String id();

    double salary();
}
