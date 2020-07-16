package com.cityconnectivity.api.cities.model;

import java.util.HashSet;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = true)
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ObjectError extends BaseModel {

	private static final long serialVersionUID = 7368890090809207379L;

	public HttpStatus status;
	public String message;
	@Builder.Default
	public Set<ConstraintViolation<?>> violations = new HashSet<>();
}
