package dtos;

import entities.Owner;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;

public class OwnerDto implements Serializable {
    @NotNull
    private final String name;
    @NotNull
    private final String address;
    @NotNull
    private final String phone;

    public OwnerDto(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
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
