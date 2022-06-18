package com.devsuperior.bds04.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.devsuperior.bds04.dto.CityDTO;
import com.devsuperior.bds04.entities.City;
import com.devsuperior.bds04.repositories.CityRepository;
import com.devsuperior.bds04.services.exceptions.ResourceNotFoundException;

@Service
public class CityService {

	@Autowired
	private CityRepository repository;
	
	// @Transactional(readOnly = true)
	// public List<CityDTO> findAll() {
	// 	List<City> list = repository.findAll();
	// 	return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList()); 
	// }  

	@Transactional(readOnly = true)
	public CityDTO findById(Long id) {
		Optional<City> obj = repository.findById(id);
		City entity = obj.orElseThrow(() -> new ResourceNotFoundException("Entity not found."));
		return new CityDTO(entity);
	}

	@Transactional
	public CityDTO insert(CityDTO dto) {
		City entity = new City();
		copyDtoToEntity(dto, entity);
		entity = repository.save(entity);
		return new CityDTO(entity);
	}

	@Transactional(readOnly = true)
	public List<CityDTO> findAll() {
		Sort sort = Sort.by(Direction.ASC, "name");
		List<City> list = repository.findAll(sort);
		return list.stream().map(x -> new CityDTO(x)).collect(Collectors.toList()); 
	}
	
	private void copyDtoToEntity(CityDTO dto, City entity) {
		entity.setName(dto.getName());
	}
}
