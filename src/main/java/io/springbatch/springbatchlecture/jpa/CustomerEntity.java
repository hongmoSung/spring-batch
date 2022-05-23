package io.springbatch.springbatchlecture.jpa;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Data
@Entity
@Table(name = "customer")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CustomerEntity {

    @Id
    private Long id;
    private String firstName;
    private String lastName;
    private String birthdate;

}
