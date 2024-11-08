package com.javaweb.service.impl;

import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.ICustomerService;
import com.javaweb.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Service
@Transactional

public class CustomerServiceImpl implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<CustomerDTO> findAllCustomers(Map<String, Object> params, Pageable pageable) {
        List<CustomerEntity> customers = customerRepository.findAllCustomers(params, pageable);
        List<CustomerDTO> listResult = new ArrayList<CustomerDTO>();
        for(CustomerEntity customer : customers) {
            CustomerDTO customerDTO = customerConverter.convertToDto(customer);
            listResult.add(customerDTO);
        }

        return listResult;
    }

    @Override
    public ResponseDTO listStaffs(long customerId) {
        CustomerEntity customer = customerRepository.findById(customerId).get() ;
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1,"STAFF") ;
        List<UserEntity> staffAssignment = customer.getUserWithCustomer();
        List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>() ;


        ResponseDTO result = new ResponseDTO() ;


        for (UserEntity item:staffs) {
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO() ;
            staffResponseDTO.setFullName(item.getFullName());
            staffResponseDTO.setStaffId(item.getId());
            if(staffAssignment.contains(item)){
                staffResponseDTO.setChecked("checked");
            }else{
                staffResponseDTO.setChecked("");
            }
            staffResponseDTOS.add(staffResponseDTO) ;
        }

        result.setData(staffResponseDTOS);
        result.setMessage("success");
        return result ;
    }

    @Override
    public int countTotalItems(Map<String, Object> params) {
        return  customerRepository.countTotalItem(params);
    }
}
