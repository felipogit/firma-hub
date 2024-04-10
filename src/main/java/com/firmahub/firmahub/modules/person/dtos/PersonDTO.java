package com.firmahub.firmahub.modules.person.dtos;

import java.io.Serializable;

import lombok.Data;

@Data
public class PersonDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Long id;
    private String firstName;
	private String lastName;
	private String address;
	private String gender;
}
