package com.fix.mobile.service;

import com.fix.mobile.entity.Capacity;

import java.util.Optional;

public interface CapacityService extends GenericService<Capacity, Integer> {
	public Optional<Capacity> findByName(String name);
}