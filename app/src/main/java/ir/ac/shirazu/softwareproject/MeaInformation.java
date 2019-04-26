package ir.ac.shirazu.softwareproject;

public class MeaInformation {
    private ReserveState reserveState;
    private String reservedFood;
    private String reservedPlace;

    public ReserveState getReserveState() {
        return reserveState;
    }

    public void setReserveState(ReserveState reserveState) {
        this.reserveState = reserveState;
    }

    public MeaInformation(String reservedFood, String reservedPlace, ReserveState reserveState) {
        this.reserveState = reserveState;
        this.reservedFood = reservedFood;
        this.reservedPlace = reservedPlace;
    }

    public String getReservedPlace() {
        return reservedPlace;
    }

    public void setReservedPlace(String reservedPlace) {
        this.reservedPlace = reservedPlace;
    }

    public String getReservedFood() {
        return reservedFood;
    }

    public void setReservedFood(String reservedFood) {
        this.reservedFood = reservedFood;
    }

}

