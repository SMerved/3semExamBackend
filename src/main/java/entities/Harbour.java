package entities;

import dtos.BoatDto;
import dtos.HarbourDto;
import dtos.OwnerDto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "harbours")
@NamedQuery(name = "Harbour.deleteAllRows", query = "DELETE from Harbour")
public class Harbour implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;
    @NotNull
    @Column(name = "address")
    private String address;
    @NotNull
    @Column(name = "capacity")
    private int capacity;

    @OneToMany(mappedBy = "harbour")
    private List<Boat> boats = new ArrayList<>();

    public Harbour() {
    }

    public Harbour(String name, String address, int capacity) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
    }

    public Harbour(String name, String address, int capacity, List<Boat> boats) {
        this.name = name;
        this.address = address;
        this.capacity = capacity;
        this.boats = boats;
    }

    public Harbour(HarbourDto harbourDto){
        this.name = harbourDto.getName();
        this.address = harbourDto.getAddress();
        this.capacity = harbourDto.getCapacity();
        this.boats = new ArrayList<>();
        for (BoatDto boatDto : harbourDto.getBoats()) {
            this.boats.add(new Boat(boatDto));
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

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public List<Boat> getBoats() {
        return boats;
    }

    public void setBoats(List<Boat> boats) {
        this.boats = boats;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
