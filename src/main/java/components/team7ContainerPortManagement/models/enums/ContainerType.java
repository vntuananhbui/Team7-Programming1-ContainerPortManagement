package src.main.java.components.team7ContainerPortManagement.models.enums;

public enum ContainerType {
    DRY_STORAGE("Dry storage"),
    OPEN_TOP("Open top"),
    OPEN_SIDE("Open side"),
    REFRIGERATED("Refrigerated"),
    LIQUID("Liquid");

    private final String label;

    ContainerType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return this.label;
    }
}
