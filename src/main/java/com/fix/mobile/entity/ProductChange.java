package com.fix.mobile.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "product_change")
public class ProductChange {
    @Id
    @Column(name = "id_change")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idChange;

    @Column(name = "imei")
    private String imei;

    @Column(name = "date_change")
    private Date dateChange;

    @Column(name = "note")
    private String note;

    @Column(name = "email")
    private String email;

    @Column(name = "status")
    private int  status;

    @Column(name = "quantity_change")
    private int  quantity;


    @ManyToOne
    @JoinColumn(name = "username")
    private Account account;

    @ManyToOne
    @JoinColumn(name = "id_order_detail")
    private OrderDetail orderDetail;


    //
    @JsonIgnore
    @OneToMany(mappedBy = "productChange")
    private List<ChangeDetail> changeDetails;


    @OneToMany(mappedBy = "productChange")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Image> images;

    @JsonManagedReference
    public List<Image> getImages(){
        return images;
    }

    @Transient
    private List<MultipartFile> files;
}
