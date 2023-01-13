package entities;

import dtos.OwnerDto;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


@Entity
@Table(name = "owners")
@NamedQuery(name = "Owner.deleteAllRows", query = "DELETE from Owner")
public class Owner implements Serializable {

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
    @Column(name = "phone")
    private String phone;


    public Owner() {
    }

    public Owner(String name, String address, String phone) {
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Owner(OwnerDto ownerDto) {
        this.name = ownerDto.getName();
        this.address = ownerDto.getAddress();
        this.phone = ownerDto.getPhone();
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
