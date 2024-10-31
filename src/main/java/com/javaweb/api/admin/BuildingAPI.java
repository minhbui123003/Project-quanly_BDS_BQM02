package com.javaweb.api.admin;

import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.service.BuildingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "buildingAPIOfAdmin")
@RequestMapping("/api/building")
@Transactional
public class BuildingAPI {
    @Autowired
    private BuildingService buildingService;

    @PostMapping
    public ResponseEntity<BuildingDTO> addOrUpdateBuilding(@RequestBody BuildingDTO buildingDTO) {

        BuildingDTO result = buildingService.createBuilding(buildingDTO);
        return ResponseEntity.ok(result);
    }

    //xóa nhân viên
    @DeleteMapping("/{ids}")
    public void deleteBuilding(@PathVariable List<Long> ids ) {
        buildingService.deleteBuilding(ids);
    }

//
    @GetMapping("/{id}/staffs")
    public ResponseDTO assignmentBuilding(@PathVariable Long id){
        ResponseDTO result = buildingService.listStaffs(id);
        return result;
    }

//    giao tòa nhà
    @PostMapping("/assignment")
    public void updateAssignmentBuilding (@RequestBody AssignmentBuildingDTO assignmentBuildingDTO) {
//        gọi hàm update
        buildingService.updateAssignmentBuilding(assignmentBuildingDTO);
        System.out.println("Update đã xong");
        ResponseEntity.noContent().build();
//        trả lời api
    }

}
