package com.javaweb.controller.admin;


import com.javaweb.model.dto.CustomerDTO;
import com.javaweb.service.ICustomerService;
import com.javaweb.service.IUserService;
import com.javaweb.utils.DisplayTagUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Controller(value="customerControllerOfAdmin")
public class CustomerController {

    @Autowired
    private IUserService userService;
    @Autowired
    private ICustomerService customerService;

    @RequestMapping(value = "/admin/customer-list",method = RequestMethod.GET)
    public ModelAndView customerList(@ModelAttribute CustomerDTO customerDTO, @RequestParam Map<String ,Object> params, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/customer/list");

//        giữ lại data khi tìm kiếm
        mav.addObject("modalSearch",customerDTO) ;

        mav.addObject("listStaffs",userService.getStaffs()) ;

        CustomerDTO kh = new CustomerDTO();
        DisplayTagUtils.of(request,kh);
        List<CustomerDTO> result = customerService.findAllCustomers(params, PageRequest.of(kh.getPage()-1, kh.getMaxPageItems()) );
        kh.setListResult(result);
        kh.setTotalItems(customerService.countTotalItems(params));
//        kết quả trả bảng
        mav.addObject("model",kh);
        return mav;
    }

    @RequestMapping(value = "/admin/customer-edit", method = RequestMethod.GET)
    public ModelAndView customerIdEdit(@ModelAttribute CustomerDTO dto , HttpServletRequest request){
        // đẩy ra view theo đường dẫn file
        ModelAndView mav = new ModelAndView("admin/customer/edit") ;
        mav.addObject("modalAdd" , dto) ;
        return mav ;
    }

}
