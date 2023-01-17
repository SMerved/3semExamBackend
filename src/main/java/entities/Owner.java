package entities;

import dtos.BoatDto;
import dtos.OwnerDto;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "owners")
@NamedQuery(name = "Owner.deleteAllRows", query = "DELETE from Owner")
public class Owner implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "address")
    private String address;
    @NotNull
    @Column(name = "phone")
    private String phone;

    @ManyToMany
    @JoinTable(
            name = "boat_owners",
            joinColumns = @JoinColumn(name = "owner_id"),
            inverseJoinColumns = @JoinColumn(name = "boat_id"))
    private Set<Boat> boats = new LinkedHashSet<>();

    public Owner() {
    }

    public Owner(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Owner(String name, String address, String phone, Set<Boat> boats) {
        this.name = name;
        this.address = address;
        this.phone = phone;
        this.boats = boats;
    }

    public Owner(OwnerDto ownerDto) {
        this.id = ownerDto.getId();
        this.name = ownerDto.getName();
        this.address = ownerDto.getAddress();
        this.phone = ownerDto.getPhone();
        this.boats = new HashSet<>();
        if (ownerDto.getBoatDtos()!= null) {
            for (BoatDto boatDto : ownerDto.getBoatDtos()) {
                this.boats.add(new Boat(boatDto));
            }
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Set<Boat> getBoats() {
        return boats;
    }

    public void setBoats(Set<Boat> boats) {
        this.boats = boats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(name, owner.name) && Objects.equals(address, owner.address) && Objects.equals(phone, owner.phone);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, address, phone);
    }

    @Override
    public String toString() {
        return "Owner{" +
                "name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
