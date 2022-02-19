package com.bsf.bsfservice.api.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account")
public class Account {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id",nullable = false)
	private Long id;

	@Column(name = "balance",nullable = false)
	private BigDecimal balance;

	@Column(name = "email",nullable = false)
	private String email;

	@Column(name = "status",nullable = false)
	private String accountStatus;
}
