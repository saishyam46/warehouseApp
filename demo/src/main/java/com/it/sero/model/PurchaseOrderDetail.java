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
@Table(name="po_dtl_tab")
public class PurchaseOrderDetail {

	@Id
	@GeneratedValue
	@Column(name="po_dtl_id_col")
	private Integer id;
	
	@Column(name="po_dtl_qnty_col")
	private Integer qnty;
	
	@ManyToOne
	@JoinColumn(name="po_dtl_fk_id_col")
	private Part parts;
	
	@ManyToOne
	@JoinColumn(name="po_dtl_order_id_fk_col")
	private PurchaseOrder order;
}
