package com.lab.sample.model;

import java.util.Date;

import lombok.Data;

@Data
public class CustomerRequestInfo {

	private String id;

	private String firstName;

	private String lastName;

	private Date birthDate;

	private Address address;

}
