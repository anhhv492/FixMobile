package com.fix.mobile.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

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
	private String nameImay;

	@Column(name = "status")
	private int status;

	@ManyToOne
	@JoinColumn(name = "id_product")
	private Product product;
}