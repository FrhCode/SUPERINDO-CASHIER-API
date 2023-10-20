package com.superindo.cashier.model;

import java.util.Date;

import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
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
public class Audit {
	@CreatedDate
	@Column(name = "created_date", nullable = false, updatable = false)
	@JsonProperty("created_date")
	private Date createdDate;

	@LastModifiedDate
	@Column(name = "updated_date", nullable = false)
	@JsonProperty("updated_date")
	private Date updatedDate;

	@LastModifiedBy
	@Column(name = "updated_user", nullable = false)
	@JsonProperty("updated_user")
	private String updatedUser;

	@CreatedBy
	@Column(name = "created_user", nullable = false)
	@JsonProperty("created_user")
	private String createdUser;
}
