package com.example.model;

import lombok.*;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Transaction {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private @Getter @Setter long id;
	@Column
	private @Getter @Setter String type;
	@Column
	private @Getter @Setter BigDecimal amount;
	@Column
	private @Getter @Setter Date date;
	@ManyToOne
	@JoinColumn(name="investor_id")
	private @Getter @Setter Investor investor; 
	@ManyToOne
	@JoinColumn(name="fund_id")
	private @Getter @Setter Fund fund;
}
