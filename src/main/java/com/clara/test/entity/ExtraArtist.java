package com.clara.test.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name="EXTRA_ARTIST")
public class ExtraArtist {

	
	private String name;
	private String anv;
	private String join;
	private String role;
	private String tracks;
	private int id;
	private String resource_url;
}
