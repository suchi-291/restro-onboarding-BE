package com.restro.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	private String name;
	private String location;
	private Double taxPercentage;
	private Boolean deliveryEnabled;
	private Boolean active;
	
	public Restaurant() {
		
	}
	
	public Restaurant(Long id, String name, String location, Double taxPercentage, Boolean deliveryEnabled,
			Boolean active) {
		super();
		this.id = id;
		this.name = name;
		this.location = location;
		this.taxPercentage = taxPercentage;
		this.deliveryEnabled = deliveryEnabled;
		this.active = active;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public Double getTaxPercentage() {
		return taxPercentage;
	}

	public void setTaxPercentage(Double taxPercentage) {
		this.taxPercentage = taxPercentage;
	}

	public Boolean getDeliveryEnabled() {
		return deliveryEnabled;
	}

	public void setDeliveryEnabled(Boolean deliveryEnabled) {
		this.deliveryEnabled = deliveryEnabled;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

	@Override
	public String toString() {
		return "Restaurant [id=" + id + ", name=" + name + ", location=" + location + ", taxPercentage=" + taxPercentage
				+ ", deliveryEnabled=" + deliveryEnabled + ", active=" + active + "]";
	}
	
	
	
}
