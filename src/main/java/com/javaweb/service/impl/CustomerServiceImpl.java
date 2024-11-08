package com.javaweb.service.impl;

import com.javaweb.converter.CustomerConverter;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.CustomerEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentCustomerDTO;
import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.CustomerRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.security.utils.SecurityUtils;
import com.javaweb.service.ICustomerService;
import com.javaweb.service.IUserService;
import org.modelmapper.ModelMapper;
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
    @Autowired
    private ModelMapper modelMapper;

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

    @Override
    public CustomerDTO insertOrUpdateCustomer(CustomerDTO customerDTO) {
        // Chuyển đổi CustomerDTO thành CustomerEntity
        CustomerEntity cusEntity = modelMapper.map(customerDTO, CustomerEntity.class);

        // Lưu hoặc cập nhật nếu có id thì là cập nhật , chưa có thì là luuw
        cusEntity = customerRepository.save(cusEntity);

        // Kiểm tra nếu người dùng hiện tại có vai trò là nhân viên ("ROLE_STAFF") và thao tác này là cập nhật khách hàng.
        // Điều kiện `customerDTO.getId() != null` đảm bảo chỉ thực hiện cho các khách hàng đã tồn tại.
        if (SecurityUtils.getAuthorities().contains("ROLE_STAFF") && customerDTO.getId() != null) {
            Long staffId = SecurityUtils.getPrincipal().getId();
            // Gọi phương thức custom repository để lưu mối quan hệ giữa nhân viên và khách hàng.
            // Điều này giúp theo dõi nhân viên nào đã thao tác với khách hàng cụ thể này.
            customerRepository.insert(staffId, cusEntity.getId());
        }

        // Chuyển đổi lại CustomerEntity đã lưu thành CustomerDTO để trả về
        return modelMapper.map(cusEntity, CustomerDTO.class);
    }

    @Override
    public void deletedCustomer(List<Long> ids) {
        for(Long id : ids)
        {
           customerRepository.deleteById(id);
        }
    }

    @Override
    public void updateAssignmentCustomer(AssignmentCustomerDTO assignmentCustomerDTO) {

        CustomerEntity customer = customerRepository.findById(assignmentCustomerDTO.getCustomerId()).get();
        customer.getUserWithCustomer().clear();
        for(Long it : assignmentCustomerDTO.getStaffs())
        {
            UserEntity staff = userRepository.findById(it).get();
            customer.getUserWithCustomer().add(staff);
        }
        customerRepository.save(customer);

    }

}
