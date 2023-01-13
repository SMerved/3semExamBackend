package dtos;

import entities.Boat;
import entities.Harbour;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class HarbourDto implements Serializable {
    @NotNull
    private final String name;
    @NotNull
    private final String address;
    @NotNull
    private final int capacity;
    private final List<BoatDto> boats;

    public HarbourDto(String name, String address, int capacity, List<BoatDto> boats) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.boats = boats;
    }

    public HarbourDto(Harbour harbour){
        this.name = harbour.getName();
        this.address = harbour.getAddress();
        this.capacity = harbour.getCapacity();
        this.boats = new ArrayList<>();
        for (Boat boat : harbour.getBoats()) {
            this.boats.add(new BoatDto(boat));
        }
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public int getCapacity() {
        return capacity;
    }

    public List<BoatDto> getBoats() {
        return boats;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        HarbourDto entity = (HarbourDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.capacity, entity.capacity) &&
                Objects.equals(this.boats, entity.boats);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, capacity, boats);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "capacity = " + capacity + ", " +
                "boats = " + boats + ")";
    }
}
