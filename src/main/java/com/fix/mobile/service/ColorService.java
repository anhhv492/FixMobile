package com.fix.mobile.service;

import com.fix.mobile.entity.Color;

import java.util.Optional;

public interface ColorService extends GenericService<Color, Integer> {
	public Optional<Color> findByName(String name);
}