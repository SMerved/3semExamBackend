package dtos;

import entities.Boat;
import entities.Owner;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class BoatDto implements Serializable {
    @NotNull
    private Long id;
    @NotNull
    private final String brand;
    @NotNull
    private final String make;
    @NotNull
    private final String name;
    @NotNull
    private final String image;
    @NotNull
    private final HarbourDto.InnerHarbourDto innerHarbourDto;
    @NotNull
    private final Set<OwnerDto> ownerDtos;

    public BoatDto(Long id, String brand, String make, String name, String image, HarbourDto.InnerHarbourDto innerHarbourDto, Set<OwnerDto> ownerDtos) {
        this.id = id;
        this.brand = brand;
        this.make = make;
        this.name = name;
        this.image = image;
        this.innerHarbourDto = innerHarbourDto;
        this.ownerDtos = ownerDtos;
    }

    public BoatDto(Boat boat){
        this.id = boat.getId();
        this.brand = boat.getBrand();
        this.make = boat.getMake();
        this.name = boat.getName();
        this.image = boat.getImage();
        this.innerHarbourDto = new HarbourDto.InnerHarbourDto(boat.getHarbour());
        this.ownerDtos = new HashSet<>();
        for (Owner owner : boat.getOwners()) {
            this.ownerDtos.add(new OwnerDto(owner));
        }
    }

    public String getBrand() {
        return brand;
    }

    public String getMake() {
        return make;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }

    public Long getId() {
        return id;
    }

    public HarbourDto.InnerHarbourDto getInnerHarbourDto() {
        return innerHarbourDto;
    }

    public Set<OwnerDto> getOwnerDtos() {
        return ownerDtos;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BoatDto entity = (BoatDto) o;
        return  Objects.equals(this.brand, entity.brand) &&
                Objects.equals(this.make, entity.make) &&
                Objects.equals(this.name, entity.name) &&
                Objects.equals(this.image, entity.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, make, name, image);
    }

    @Override
    public String toString() {
        return "BoatDto{" +
                "id=" + id +
                ", brand='" + brand + '\'' +
                ", make='" + make + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", innerHarbourDto=" + innerHarbourDto +
                ", ownerDtos=" + ownerDtos +
                '}';
    }
}
