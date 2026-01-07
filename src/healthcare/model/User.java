package healthcare.model;

public class User {
    protected String userId;
    protected String name;
    protected String contactNo;
    protected String email;
    protected String address;
    protected String role;

    public User() {}

    public User(String userId, String name, String contactNo, String email, String address, String role) {
        this.userId = userId;
        this.name = name;
        this.contactNo = contactNo;
        this.email = email;
        this.address = address;
        this.role = role;
    }

    public boolean login() { return true; }
    public void logout() {}

    public String getUserId() { return userId; }
    public String getName() { return name; }
    public String getContactNo() { return contactNo; }
    public String getEmail() { return email; }
    public String getAddress() { return address; }
    public String getRole() { return role; }

    public void setUserId(String userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setContactNo(String contactNo) { this.contactNo = contactNo; }
    public void setEmail(String email) { this.email = email; }
    public void setAddress(String address) { this.address = address; }
    public void setRole(String role) { this.role = role; }
}

