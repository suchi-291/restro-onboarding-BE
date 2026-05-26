package com.restro.entity;

import com.restro.enums.PaymentStatus;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class Payment {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	
	@OneToOne
	@JoinColumn(name="order_id")
	private CustomerOrder order;
	private String gatewayOrderId;
	private String gatewayPaymentId;
	private String signature;
	@Enumerated(EnumType.STRING)
	private PaymentStatus paymentStatus;
	private Boolean processed;
	
	public Payment() {
		
	}
	
	public Payment(Long id, CustomerOrder order, String gatewayOrderId, String gatewayPaymentId, String signature,
			PaymentStatus paymentStatus, Boolean processed) {
		super();
		this.id = id;
		this.order = order;
		this.gatewayOrderId = gatewayOrderId;
		this.gatewayPaymentId = gatewayPaymentId;
		this.signature = signature;
		this.paymentStatus = paymentStatus;
		this.processed = processed;
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
	public String getGatewayOrderId() {
		return gatewayOrderId;
	}
	public void setGatewayOrderId(String gatewayOrderId) {
		this.gatewayOrderId = gatewayOrderId;
	}
	public String getGatewayPaymentId() {
		return gatewayPaymentId;
	}
	public void setGatewayPaymentId(String gatewayPaymentId) {
		this.gatewayPaymentId = gatewayPaymentId;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	public PaymentStatus getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(PaymentStatus paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Boolean getProcessed() {
		return processed;
	}
	public void setProcessed(Boolean processed) {
		this.processed = processed;
	}
	@Override
	public String toString() {
		return "Payment [id=" + id + ", order=" + order + ", gatewayOrderId=" + gatewayOrderId + ", gatewayPaymentId="
				+ gatewayPaymentId + ", signature=" + signature + ", paymentStatus=" + paymentStatus + ", processed="
				+ processed + "]";
	}
		
	

}
