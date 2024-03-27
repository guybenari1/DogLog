package superapp.miniApps;

public class Business {
    private String name;
    private String activityTime;
    private String phone;
    private String address;
    private String service;

    public Business() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getActivityTime() {
        return activityTime;
    }

    public void setActivityTime(String activityTime) {
        this.activityTime = activityTime;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    @Override
    public String toString() {
        return "Business [name=" + name + ", activityTime=" + activityTime + ", phone=" + phone + ", address=" + address
                + ", service=" + service + "]";
    }
}
