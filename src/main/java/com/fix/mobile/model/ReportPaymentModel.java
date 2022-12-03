package com.fix.mobile.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import net.jcip.annotations.Immutable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "reportcustomer")
@Immutable
@Entity
public class ReportPaymentModel {
     @Id
	 @Column(name = "id_order")
	 private Integer id;

	 @Column(name = "full_name")
	 private String fullname;

	 @Column(name = "totalquantity")
	 private Integer quantity;

	 @Column(name = "totalorder")
	 private Integer order;

	 @Column(name = "totalmoney")
	 private Double money;

	 @Column(name = "create_date")
	 private Date createDate;


}
