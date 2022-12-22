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
@Table(name = "products")
public class Product {
    @Id
    @Column(name = "id_product")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idProduct;

    @Column(name = "name")
    private String name;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "camera")
    private String camera;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "size")
    private String size;

    @Column(name = "note")
    private String note;

    @Column(name = "status")
    private int status;


    @ManyToOne
    @JoinColumn(name = "id_ram")
    private Ram ram;

    @ManyToOne
    @JoinColumn(name = "id_color")
    private Color color;

    @ManyToOne
    @JoinColumn(name = "id_capacity")
    private Capacity capacity;

    @ManyToOne
    @JoinColumn(name = "id_category")
    private Category category;


    public Product(String name, String imei, Date createDate, String camera, BigDecimal price,
                   String size, String note, int status, Ram ram, Color color,
                   Capacity capacity, Category category, List<Image> images, List<MultipartFile> files) {
        this.name = name;
        this.createDate = createDate;
        this.camera = camera;
        this.price = price;
        this.size = size;
        this.note = note;
        this.status = status;
        this.ram = ram;
        this.color = color;
        this.capacity = capacity;
        this.category = category;
        this.images = images;
        this.files = files;
    }


    @OneToMany(mappedBy = "product")
    @LazyCollection(LazyCollectionOption.FALSE)
    private List<Image> images;
    
    @JsonManagedReference
    public List<Image> getImages(){
        return images;
    }

    @Transient
    private List<MultipartFile> files;


    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ChangeDetail> changeDetails;


    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<InsuranceDetail> insuranceDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<OrderDetail> orderDetails;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ProductReturn> productReturns;

    @JsonIgnore
    @OneToMany(mappedBy = "product")
    private List<ImayProduct> listImay;

}
