package finalproject.emag.util;

public enum AttributeUtil {

    USER("user"), CART("cart");

    private final String value;

    AttributeUtil(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}