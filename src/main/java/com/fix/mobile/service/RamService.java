package com.fix.mobile.service;

import com.fix.mobile.entity.Ram;

import java.util.List;
import java.util.Optional;

public interface RamService extends GenericService<Ram, Integer> {
	public Optional<Ram> findByName(String name);

	public List<Ram> getALL();
}