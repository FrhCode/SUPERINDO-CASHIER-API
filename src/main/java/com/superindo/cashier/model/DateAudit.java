package com.superindo.cashier.model;

import java.io.Serializable;
import java.util.Date;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class DateAudit implements Serializable {
	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	@JsonProperty("created_date")
	private Date createdDate;

	@LastModifiedDate
	@JsonProperty("updated_date")
	@Column(name = "updated_date")
	private Date updatedDate;

}