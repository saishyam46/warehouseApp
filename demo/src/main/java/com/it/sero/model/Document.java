package com.it.sero.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="docs_tab")
public class Document {

	@Id
	@Column(name="doc_id_col")
	private Integer docId;
	
	@Column(name="doc_name_col")
	private String docName;
	
	@Column(name="doc_data_col")
	@Lob
	private byte[] docDatata;
}
