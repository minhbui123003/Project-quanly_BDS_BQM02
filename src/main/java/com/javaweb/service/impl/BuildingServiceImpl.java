package com.javaweb.service.impl;

import com.javaweb.converter.BuildingConverter;
import com.javaweb.converter.BuildingResponseConverter;
import com.javaweb.converter.BuildingSearchRequestConverter;
import com.javaweb.entity.AssignmentBuildingEntity;
import com.javaweb.entity.BuildingEntity;
import com.javaweb.entity.RentAreaEntity;
import com.javaweb.entity.UserEntity;
import com.javaweb.model.dto.AssignmentBuildingDTO;
import com.javaweb.model.dto.BuildingDTO;
import com.javaweb.model.request.BuildingSearchBuilder;
import com.javaweb.model.response.BuildingSearchResponse;
import com.javaweb.model.response.ResponseDTO;
import com.javaweb.model.response.StaffResponseDTO;
import com.javaweb.repository.BuildingRepository;
import com.javaweb.repository.RentAreaRepository;
import com.javaweb.repository.UserRepository;
import com.javaweb.service.BuildingService;
import com.javaweb.utils.UploadFileUtils;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.*;

@Service
@Transactional
public class BuildingServiceImpl implements BuildingService {
    @Autowired
    private BuildingRepository buildingRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BuildingSearchRequestConverter buildingSearchRequestConverter;
    @Autowired
    private BuildingResponseConverter buildingResponseConverter;

    @Autowired
    private RentAreaRepository rentAreaRepository;

    @Autowired
    private BuildingConverter buildingConverter;
    @Autowired
    private UploadFileUtils uploadFileUtils;

    @Override
    public ResponseDTO listStaffs(Long buildingId) {
        BuildingEntity building = buildingRepository.findById(buildingId).get();
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1,"STAFF");
        List<UserEntity> staffAssignmentBuilding = building.getUserEntities();
        List<StaffResponseDTO> staffResponseDTOS = new ArrayList<>();

        ResponseDTO result = new ResponseDTO();

        for(UserEntity item : staffs){
            StaffResponseDTO staffResponseDTO = new StaffResponseDTO();
            staffResponseDTO.setFullName(item.getFullName());
            staffResponseDTO.setStaffId(item.getId());
            if(staffAssignmentBuilding.contains(item)){
                staffResponseDTO.setChecked("checked");
            }
            else
            {
                staffResponseDTO.setChecked("no success");
            }
            staffResponseDTOS.add(staffResponseDTO);
        }

        result.setData(staffResponseDTOS);
        result.setMessage("success");
        return result ;
    }


    @Override
    public Page<BuildingSearchResponse> findAllBuildings(Map<String, Object> params, List<String> typeCode , Pageable pageable) {
        BuildingSearchBuilder buildingSearchBuilder = buildingSearchRequestConverter.toBuildingSearchBuilder( params,typeCode);

        Page<BuildingEntity> listBuildingEntities = buildingRepository.findAllBuildings(buildingSearchBuilder , pageable);
//        áp dụng phân trang

        List<BuildingSearchResponse> result = new ArrayList<BuildingSearchResponse>();


        for(BuildingEntity item : listBuildingEntities.getContent()){
            BuildingSearchResponse buildingSearchResponse = buildingResponseConverter.toBuildingSearchResponse(item);

            // Thêm các trường audit nếu chưa có
//            buildingSearchResponse.setCreatedDate(item.getCreatedDate()); // Thêm
//            buildingSearchResponse.setCreatedBy(item.getCreatedBy()); // Thêm
//            buildingSearchResponse.setModifiedDate(item.getModifiedDate()); // Thêm
//            buildingSearchResponse.setModifiedBy(item.getModifiedBy()); // Thêm

            result.add(buildingSearchResponse);

        }


//        return result;
        return new PageImpl<>(result, pageable, listBuildingEntities.getTotalElements());
    }

    private void saveThumbnail(BuildingDTO bdto, BuildingEntity buildingEntity)
    {
        String path = "/building/" + bdto.getImageName();
        if (null != bdto.getImageBase64())
        {
            if (null != buildingEntity.getAvatar())
            {
                if (!path.equals(buildingEntity.getAvatar()))
                {
                    File file = new File("D:/JAVA_BACK_END/project3_lan3/src/main/resources/uploads/" + buildingEntity.getAvatar());
                    file.delete();
                }
            }
            byte[] bytes = Base64.decodeBase64(bdto.getImageBase64().getBytes());
            uploadFileUtils.writeOrUpdate(path, bytes);
            buildingEntity.setAvatar(path);
        }
    }

    @Override
    public BuildingDTO createBuilding(BuildingDTO dto) {
//        cập nhật
       if(dto.getId()!= null){

           BuildingEntity building = buildingRepository.findById(dto.getId()).get();
           building = buildingConverter.toBuildingEntity(dto);
           buildingRepository.save(building) ;

           List<String> list = Arrays.asList(dto.getRentArea().split(","));

//           List<RentAreaEntity> rentAreaEntities= rentAreaRepository.findAll(dto.getId());
//           for(RentAreaEntity db : rentAreaEntities){
//               rentAreaRepository.delete(db);
//           }

           for (String it: list) {
               RentAreaEntity rnew = new RentAreaEntity() ;
               rnew.setValue(it);
               rnew.setBuilding(building);
               rentAreaRepository.save(rnew) ;
           }


       }
//       thêm
       else {
           BuildingEntity building = buildingConverter.toBuildingEntity(dto);
           buildingRepository.save(building) ;
           List<String> list = Arrays.asList(dto.getRentArea().split(","));
           for (String it: list) {
               RentAreaEntity rnew = new RentAreaEntity() ;
               rnew.setValue(it);
               rnew.setBuilding(building);
               rentAreaRepository.save(rnew) ;
           }

       }
       return dto;
    }

    @Override
    public void deleteBuilding(List<Long> ids) {
       for(Long id : ids)
       {
           BuildingEntity buildingEntity = buildingRepository.findById(id).get();
           List<RentAreaEntity> rentAreaEntities  = rentAreaRepository.findAll(id);
//           for(RentAreaEntity it : rentAreaEntities){
//               rentAreaRepository.delete(it);
//           }
//           buildingEntity.getUserEntities().clear();
           buildingRepository.delete(buildingEntity);
       }
    }

    @Override
    public BuildingDTO findBuildingById(Long id) {
       BuildingEntity building = buildingRepository.findById(id).get();
       BuildingDTO buildingDTO = buildingConverter.toBuildingDTO(building);
       List<RentAreaEntity> list = rentAreaRepository.findAll(building.getId());
//       taoj một chuỗi để lưu giá trị của rentAreaValues
        StringBuilder rentAreaValues = new StringBuilder();
        for (RentAreaEntity it : list) {
            if (rentAreaValues.length() > 0) {
                rentAreaValues.append(", "); // phân cách giữa các giá trị
            }
            rentAreaValues.append(it.getValue());
        }

        // Set chuỗi chứa nhiều giá trị vào DTO
        buildingDTO.setRentArea(rentAreaValues.toString());
       return buildingDTO;
    }


    @Override
//    tìm building rồi xóa nhân viên rồi thêm lại
    public void updateAssignmentBuilding(AssignmentBuildingDTO assignmentBuildingDTO) {

        BuildingEntity building = buildingRepository.findById(assignmentBuildingDTO.getBuildingId()).get();
        building.getUserEntities().clear();
        for(Long it : assignmentBuildingDTO.getStaffs())
        {
            UserEntity staff = userRepository.findById(it).get();
            building.getUserEntities().add(staff);
        }
        buildingRepository.save(building);

    }

}
