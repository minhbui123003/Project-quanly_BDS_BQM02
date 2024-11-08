package com.javaweb.converter;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.RoleEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.dto.RoleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomerConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO convertToDto(CustomerEntity entity) {
        CustomerDTO result = modelMapper.map(entity, CustomerDTO.class);
        return result;
    }

    public CustomerEntity convertToEntity(CustomerDTO dto) {
        CustomerEntity result = modelMapper.map(dto, CustomerEntity.class);
        return result;
    }
}
