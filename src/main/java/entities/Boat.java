package entities;

import dtos.BoatDto;
import dtos.OwnerDto;

import java.io.Serializable;
import java.util.*;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "boats")
@NamedQuery(name = "Boat.deleteAllRows", query = "DELETE from Boat")
public class Boat implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "boat_id")
    private Long id;

    @NotNull
    @Column(name = "brand")
    private String brand;
    @NotNull
    @Column(name = "make")
    private String make;
    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "image")
    private String image;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "harbour_id")
    private Harbour harbour;

    @ManyToMany(mappedBy = "boats")
    private Set<Owner> owners;

    public Boat() {
    }

    public Boat(String brand, String make, String name, String image, Harbour harbour) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.harbour = harbour;
    }

    public Boat(String brand, String make, String name, String image, Harbour harbour, Set<Owner> owners) {
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.harbour = harbour;
        this.owners = owners;
    }

    public Boat(BoatDto boatDto){
        this.id = boatDto.getId();
        this.brand = boatDto.getBrand();
        this.make = boatDto.getMake();
        this.name = boatDto.getName();
        this.image = boatDto.getImage();
        this.harbour = new Harbour(boatDto.getInnerHarbourDto());
        this.owners = new HashSet<>();
        for (OwnerDto ownerDto : boatDto.getOwnerDtos()) {
            this.owners.add(new Owner(ownerDto));
        }
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Harbour getHarbour() {
        return harbour;
    }

    public void setHarbour(Harbour harbour) {
        this.harbour = harbour;
    }

    public Set<Owner> getOwners() {
        return owners;
    }

    public void setOwners(Set<Owner> owners) {
        this.owners = owners;
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
        Boat boat = (Boat) o;
        return Objects.equals(brand, boat.brand) && Objects.equals(make, boat.make) && Objects.equals(name, boat.name) && Objects.equals(image, boat.image) && Objects.equals(harbour, boat.harbour) && Objects.equals(owners, boat.owners);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, make, name, image, harbour, owners);
    }

    @Override
    public String toString() {
        return "Boat{" +
                "brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", harbour=" + harbour +
                ", owners=" + owners +
                '}';
    }
}
