package com.it.sero.model;

import java.util.List;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="order_method_tab")
public class OrderMethod  {
	
	@Id
	@GeneratedValue(generator = "om_gen")
	@SequenceGenerator(name = "om_gen",sequenceName = "om_gen_seq")
	private Integer id;

	@Column(name="order_mode_col")
	private String orderMode;
	@Column(name="order_code_col")
	private String orderCode;
	@Column(name="order_typ_col")
	private String orderType;
	
	@ElementCollection(fetch = FetchType.EAGER)
	@CollectionTable(name="ord_acpt_tab",joinColumns = @JoinColumn(name="ord_id_col"))
	private List<String> orderAccept;
	
	@Column(name="order_desc_col")
	private String description;
}

