package domain;

/**
 *  @author Meghashree M Ramachandra
 * Fields for the columns of employee database which will be used for CRUD operations
 * (Create, Retrieve, Update and Delete) to perform.
 */
public class EmployeeRecord {
    // Fileds for the columns in the employee DB record...
    private int employeeNumber;
    private String employeeLastName;
    private String employeeFirstName;
    private String employeePhoneExtention;
    private String employeeEmail;
    private String employeeOfficeCode;
    private int employeeReportsTo;
    private String employeeJobTitle;

    //Constructor with no arg
    public EmployeeRecord() {
    }

    //Constructor with full-arg
    public EmployeeRecord(int employeeNumber, String employeeLastName, String employeeFirstName, String employeePhoneExtention, String employeeEmail, String employeeOfficeCode, int employeeReportsTo, String employeeJobTitle) {
        this.employeeNumber = employeeNumber;
        this.employeeLastName = employeeLastName;
        this.employeeFirstName = employeeFirstName;
        this.employeePhoneExtention = employeePhoneExtention;
        this.employeeEmail = employeeEmail;
        this.employeeOfficeCode = employeeOfficeCode;
        this.employeeReportsTo = employeeReportsTo;
        this.employeeJobTitle = employeeJobTitle;
    }

    //Accessors and Mutators
    public int getEmployeeNumber() {
        return employeeNumber;
    }

    public void setEmployeeNumber(int employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public String getEmployeeLastName() {
        return employeeLastName;
    }

    public void setEmployeeLastName(String employeeLastName) {
        this.employeeLastName = employeeLastName;
    }

    public String getEmployeeFirstName() {
        return employeeFirstName;
    }

    public void setEmployeeFirstName(String employeeFirstName) {
        this.employeeFirstName = employeeFirstName;
    }

    public String getEmployeePhoneExtention() {
        return employeePhoneExtention;
    }

    public void setEmployeePhoneExtention(String employeePhoneExtention) {
        this.employeePhoneExtention = employeePhoneExtention;
    }

    public String getEmployeeEmail() {
        return employeeEmail;
    }

    public void setEmployeeEmail(String employeeEmail) {
        this.employeeEmail = employeeEmail;
    }

    public String getEmployeeOfficeCode() {
        return employeeOfficeCode;
    }

    public void setEmployeeOfficeCode(String employeeOfficeCode) {
        this.employeeOfficeCode = employeeOfficeCode;
    }

    public int getEmployeeReportsTo() {
        return employeeReportsTo;
    }

    public void setEmployeeReportsTo(int employeeReportsTo) {
        this.employeeReportsTo = employeeReportsTo;
    }

    public String getEmployeeJobTitle() {
        return employeeJobTitle;
    }

    public void setEmployeeJobTitle(String employeeJobTitle) {
        this.employeeJobTitle = employeeJobTitle;
    }

    //Overridden string method
    @Override
    public String toString() {
        return "EmployeeRecord{" + "employeeNumber=" + employeeNumber + ", employeeLastName=" + employeeLastName + ", employeeFirstName=" + employeeFirstName + ", employeePhoneExtention=" + employeePhoneExtention + ", employeeEmail=" + employeeEmail + ", employeeOfficeCode=" + employeeOfficeCode + ", employeeReportsTo=" + employeeReportsTo + ", employeeJobTitle=" + employeeJobTitle + '}';
    }
}
