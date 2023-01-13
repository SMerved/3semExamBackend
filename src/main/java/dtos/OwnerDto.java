package dtos;

import entities.Boat;
import entities.Owner;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class OwnerDto implements Serializable {
    @NotNull
    private final String name;
    @NotNull
    private final String address;
    @NotNull
    private final String phone;
    private Set<BoatDto> boatDtos;

    public OwnerDto(String name, String address, String phone, Set<BoatDto> boatDtos) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.boatDtos = boatDtos;
    }

    public  OwnerDto(Owner owner){
        this.name = owner.getName();
        this.address = owner.getAddress();
        this.phone = owner.getPhone();
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public String getPhone() {
        return phone;
    }

    public Set<BoatDto> getBoatDtos() {
        return boatDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerDto entity = (OwnerDto) o;
        return Objects.equals(this.name, entity.name) &&
                Objects.equals(this.address, entity.address) &&
                Objects.equals(this.phone, entity.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone);
    }

    @Override
    public String toString() {
        return getClass().getSimpleName() + "(" +
                "name = " + name + ", " +
                "address = " + address + ", " +
                "phone = " + phone + ")";
    }
}
