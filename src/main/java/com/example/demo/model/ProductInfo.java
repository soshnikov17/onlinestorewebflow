package com.example.demo.model;

import java.io.Serializable;

public class ProductInfo implements Serializable {
	private String product;

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}
}