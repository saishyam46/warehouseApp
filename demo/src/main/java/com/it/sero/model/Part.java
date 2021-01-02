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
@Table(name="part_tab")
public class Part {

	@Id
	@GeneratedValue
	@Column(name="part_id_col")
	private Integer id;
	
	@Column(name="part_item_code_col")
	private String partItemCode;
	
	@Column(name="part_item_length_col")
	private Double partItemLength;
	
	@Column(name="part_item_width_col")
	private Double partItemWidth;
	
	@Column(name="part_item_height_col")
	private Double partItemHeight;
	
	@Column(name="part_item_cost_col")
	private Double partitemCost;
	
	@Column(name="part_item_curr_col")
	private String partItemCurrency;
	
	@Column(name="part_item_gst_slob")
	private Integer gstSlob;
	
	@ManyToOne
	@JoinColumn(name="uom_id_fk_col")
	private Uom uom;
	
	@ManyToOne
	@JoinColumn(name="orderMethod_id_fk_col")
	private OrderMethod orderMethod;
}
