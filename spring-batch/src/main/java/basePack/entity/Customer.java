package basePack.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Customer {
    @Id
    @GeneratedValue
    Long id;
    String name;
    String email;
    String address;
    int revenue;
}
