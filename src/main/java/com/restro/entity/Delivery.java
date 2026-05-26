package com.restro.entity;

import com.restro.enums.DeliveryStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Delivery {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="order_id")
	private CustomerOrder order;
	private String deliveryPartnerName;
	private String trackingNumber;
	
	@Enumerated(EnumType.STRING)
	private DeliveryStatus deliveryStatus;
	
	public Delivery() {
		
	}

	public Delivery(Long id, CustomerOrder order, String deliveryPartnerName, String trackingNumber,
			DeliveryStatus deliveryStatus) {
		super();
		this.id = id;
		this.order = order;
		this.deliveryPartnerName = deliveryPartnerName;
		this.trackingNumber = trackingNumber;
		this.deliveryStatus = deliveryStatus;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public CustomerOrder getOrder() {
		return order;
	}

	public void setOrder(CustomerOrder order) {
		this.order = order;
	}

	public String getDeliveryPartnerName() {
		return deliveryPartnerName;
	}

	public void setDeliveryPartnerName(String deliveryPartnerName) {
		this.deliveryPartnerName = deliveryPartnerName;
	}

	public String getTrackingNumber() {
		return trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public DeliveryStatus getDeliveryStatus() {
		return deliveryStatus;
	}

	public void setDeliveryStatus(DeliveryStatus deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
	}

	@Override
	public String toString() {
		return "Delivery [id=" + id + ", order=" + order + ", deliveryPartnerName=" + deliveryPartnerName
				+ ", trackingNumber=" + trackingNumber + ", deliveryStatus=" + deliveryStatus + "]";
	}
	
	
	
}
