package com.javaweb.controller.admin;
import com.javaweb.enums.District;
import com.javaweb.enums.TypeCode;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchBuilder;
import com.javaweb.model.request.BuildingSearchRequest;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.service.BuildingService;
import com.javaweb.service.IUserService;
import com.javaweb.utils.DistrictCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

//
@Controller (value="buildingControllerOfAdmin")
public class BuildingController {

    @Autowired
    private IUserService userService;
    @Autowired
    private BuildingService buildingService;

//    thêm @ @PageableDefault(size = ...) Pageable pageableể quản lý phân trang
    @RequestMapping(value = "/admin/building-list",method = RequestMethod.GET)
    public ModelAndView buildingList(@ModelAttribute BuildingSearchRequest buildingSearchRequest, @PageableDefault(size = 4) Pageable pageable  , @RequestParam Map<String, Object> params, @RequestParam  (name="typeCode", required=false) List<String> typeCode , HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/building/list");
//        xuống DB laays data
        mav.addObject("modelSearch",buildingSearchRequest);

        Page<BuildingSearchResponse> responseList = buildingService.findAllBuildings(params, typeCode,pageable);
//        lấy danh sách nội dung hiển thị
        mav.addObject("buildingList", responseList.getContent());
        // Tổng số trang
        mav.addObject("totalPages", responseList.getTotalPages());
        // Trang hiện tại
        mav.addObject("currentPage", responseList.getNumber());


//        các ds nhân viên , tỉnh- thành phố, nhà
        mav.addObject("listStaffs",userService.getStaffs());
        mav.addObject("listDistricts", District.type());
        mav.addObject("listTypeCode", TypeCode.listTypeCode());
        return mav;
    }

    @RequestMapping(value = "/admin/building-edit",method = RequestMethod.GET)
    public ModelAndView buildingEdit(@ModelAttribute("buildingEdit") BuildingDTO buildingDTO , HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/building/edit");


//        các ds  tỉnh-thành phố , nhà từ enums
        mav.addObject("listDistricts", District.type());
        mav.addObject("listTypeCode", TypeCode.listTypeCode());

        return mav;
    }

    @RequestMapping(value = "/admin/building-edit-{id}",method = RequestMethod.GET)
    public ModelAndView buildingEdit(@PathVariable("id") Long id, HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/building/edit");
//        xuống DB tìm building theo id

        BuildingDTO buildingDTO = buildingService.findBuildingById(id);


//        hiển thị data building lên phần sửa
        mav.addObject("buildingEdit",buildingDTO);


//        các ds  tỉnh- thành phố, nhà từ enums
        mav.addObject("listDistricts", District.type());
        mav.addObject("listTypeCode", TypeCode.listTypeCode());
        return mav;
    }
}
