package ir.ac.shirazu.softwareproject.server_api;

public enum ReserveState {
    /*
     *ToDo: This enum must have 3 value:
     * instead of (EDITABLE_RESERVED & NON_EDITABLE_RESERVED) we must have just RESERVED.
     * Practically we must handle the (Editable and Non_editable) states with the date, time and logic.
     * */

    EDITABLE_RESERVED,
    NON_EDITABLE_RESERVED,
    UNPLANNED,
    NOT_RESERVED,
}
