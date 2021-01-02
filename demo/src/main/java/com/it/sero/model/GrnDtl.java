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
@Table(name="grn_dtl_tab")
public class GrnDtl {

	@Id
	@GeneratedValue
	@Column(name="grn_dtl_id_col")
	private Integer id;
	
	@Column(name="grn_dtl_itemCode_col")
	private String itemCode;
	
	@Column(name="grn_dtl_baseCost_col")
	private Double baseCost;
	
	@Column(name="grn_dtl_qnty_col")
	private Integer qnty;
	
	@Column(name="grn_dtl_value_col")
	private Double value;
	
	@Column(name="grn_dtl_status_col")
	private String grnStatus;
	
	@ManyToOne
	@JoinColumn(name="grn_dtl_id_fk_col")
	private Grn grn;
}
