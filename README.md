# Employee Model
----------------
## Model, representing an employee of corporation, including some basic properties and behaviors. The model consists of Interface, parent Class, child Class, three helper Classes. 

- CompanyStaff - Interface
- Employee - Parent Class, the main module
- Expert - Child Class, adds some extra details and functionality
- AssetsAllocator - helper Class of Employee
- LegalAgeChecker - helper Class of Employee
- CorrectStructureController - helper Class of Expert
- Main - the Driver Class

## Basic Employee properties and behaviors

- Creation of Employee instance from input, consisting of Name, Surname, DateOfBirth and Experience
- Automatic generation of ID, E-Mail, Salary calculation and Assets allocation
- Options for setting of Subordinate(s) and Superior
- Validation of input data

## Complementing properties added by Expert

- Expert is upgraded version of Employee, achieved by accepting additional input data - Position and Expertise
- Automatic design of Expert profile, setting structurally distinctive properties, modification of the basic Employee Salary, Assets and ID, reflecting the specific characteristics of the Expert
- Validation and control of the hierarchy

## The model works with the standard input and output. The validation requires specific syntax for the input. 

### ++Valid input data:++

- name and surname /case insensitive/
    * Each must be no less than 3 characters long
    * Only letters from the latin alphabet are accepted
- dateOfBirth
    * The accepted pattern is: "dd/MM/yyyy"
    * The minimum allowed age is 18 years old(considered at the time of the instantiation)
- experineceInYears
    * The value cannot be negative number
- position /case insensitive/
    * "Director"
    * "Manager"
    * "Operator"
- expertise /case insensitive/
    * "General Management"
    * "Operations"
    * "Administration"
    * "Legal"
    * "Technical"
    * "Marketing"
    * "Sales"
    * "Finance"
