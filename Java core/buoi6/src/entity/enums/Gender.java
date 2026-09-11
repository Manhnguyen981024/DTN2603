package entity.enums;

public enum Gender {
    M("Male"),
    F("Female"),
    U("Unknown");

    private String value;
    
    private Gender(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
    