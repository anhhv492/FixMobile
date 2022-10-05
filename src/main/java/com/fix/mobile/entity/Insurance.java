package com.fix.mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "insurance")
public class Insurance {
    @Id
    @Column(name = "id_insurance")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idInsurance;

    @Column(name = "name")
    private Integer name;

    @Column(name = "date_start")
    private java.sql.Date dateStart;

    @Column(name = "date_end")
    private java.sql.Date dateEnd;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "price")
    private BigDecimal price;

    //
    @JsonIgnore
    @OneToMany(mappedBy = "insurance")
    private List<InsuranceDetail> insuranceDetails;
}
