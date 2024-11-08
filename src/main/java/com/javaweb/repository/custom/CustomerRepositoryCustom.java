package com.javaweb.repository.custom;

import com.javaweb.entity.CustomerEntity;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CustomerRepositoryCustom {
    List<CustomerEntity> findAllCustomers(Map<String,Object> params , Pageable pageable);
    int countTotalItem(Map<String, Object> params);
    void insert(Long staffid , Long customerid) ;
}
