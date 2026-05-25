package com.restro.entity;

import com.restro.enums.DeliveryStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private CustomerOrder order;
	private String deliveryPartnerName;
	private Long trackingNumber;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;
	
}
