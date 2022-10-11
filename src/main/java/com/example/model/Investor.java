package com.example.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Investor {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private @Getter @Setter long id;
	@Column
	private @Getter @Setter String name;
}
