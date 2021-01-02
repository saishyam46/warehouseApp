package com.it.sero.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="pruchase_order_tab")
public class PurchaseOrder {
	
	@Id
	@Column(name="po_id_col")
	@GeneratedValue
	private Integer id;
	
	@Column(name="po_code_col")
	private String orderCode;
	
	@Column(name="po_ref_col")
	private String refNumber;
	
	@Column(name="po_qltyCheck_col")
	private String qltyCheck;
	
	@Column(name="po_status_col")
	private String status;

	@Column(name="po_desc_col")
	private String description;
	
	@ManyToOne
	@JoinColumn(name="ship_id_fk")
	private ShipmentType ShipmentType;
	
	@ManyToOne
	@JoinColumn(name="wh_user_vendor_id_fk")
	private WhUserType whUserType;
}
