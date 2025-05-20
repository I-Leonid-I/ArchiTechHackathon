package entities.client;

public enum ClientType {
    INDIVIDUAL("ФЛ"),
    LEGAL("ЮЛ");

    private final String type;

    ClientType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
