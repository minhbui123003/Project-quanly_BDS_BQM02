package com.javaweb.service;

import com.javaweb.entity.CustomerEntity;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface ICustomerService {
    List<CustomerDTO> findAllCustomers(Map<String,Object> params , Pageable pageable);
    //    trả về ds nhân viên
    ResponseDTO listStaffs(long customerId);
    int countTotalItems(Map<String, Object> params) ;

    CustomerDTO insertOrUpdateCustomer( CustomerDTO customerDTO) ;
    // hàm xóa
    void deletedCustomer(List<Long> ids);
    // hàm update giao tòa nhà
    void updateAssignmentCustomer(AssignmentCustomerDTO assignmentCustomerDTO);

}
