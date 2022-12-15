package com.fix.mobile.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "imayproduct")
@Entity
public class ImayProduct {
	@Id
	@Column(name = "id_imay")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer idImay;

	@Column(name = "name")
	private String name;

	@Column(name = "status")
	private int status;

	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;

	@ManyToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "id_detail")
	private OrderDetail orderDetail;


	public ImayProduct( String name, int status, Product product) {
		this.name = name;
		this.status = status;
		this.product = product;
	}
}
