package com.bsf.bsfservice.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity()
@Table(name = "transaction")
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id",nullable = false)
	private Long id;

	@Column(name = "from_account_id",nullable = false)
	private Long fromAccountId;


	@Column(name = "to_account_id",nullable = false)
	private Long toAccountId;

	@Column(name = "action",nullable = false)
	private String action;

	@Column(name = "status",nullable = false)
	private String status;

	@Column(name = "created_date",nullable = false)
	private Instant createdDate;

	@Column(name = "updated_date",nullable = false)
	private Instant updatedDate;
}
