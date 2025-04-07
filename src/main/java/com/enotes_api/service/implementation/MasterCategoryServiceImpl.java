package com.enotes_api.service.implementation;

import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.repository.MasterCategoryRepository;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;
import com.enotes_api.service.MasterCategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MasterCategoryServiceImpl implements MasterCategoryService {

    private MasterCategoryRepository masterCategoryRepository;

    private ModelMapper modelMapper;

    @Override
    public MasterCategoryResponse saveMasterCategory(MasterCategoryRequest masterCategoryRequest) {

        MasterCategoryEntity masterCategoryEntity = modelMapper.map(masterCategoryRequest, MasterCategoryEntity.class);

//		MasterCategoryEntity masterCategoryEntity = new MasterCategoryEntity();
//		masterCategoryEntity.setName(masterCategoryRequest.getName());
//		masterCategoryEntity.setDescription(masterCategoryRequest.getDescription());

        MasterCategoryEntity savedMasterCategory = masterCategoryRepository.save(masterCategoryEntity);

        MasterCategoryResponse masterCategoryResponse = modelMapper.map(savedMasterCategory,
                MasterCategoryResponse.class);

        return masterCategoryResponse;
    }

    @Override
    public List<MasterCategoryResponse> getAllMasterCategories() {
        List<MasterCategoryEntity> allCategories = masterCategoryRepository.findAllByIsDeleted(Boolean.FALSE);
        List<MasterCategoryResponse> allCategoryResponse =
                allCategories.stream().map(eachCategory -> modelMapper.map(eachCategory,
                        MasterCategoryResponse.class)).collect(Collectors.toList());
        return allCategoryResponse;
    }

    @Override
    public List<MasterCategoryResponse> getActiveMasterCategories() {
        List<MasterCategoryEntity> activeCategories = masterCategoryRepository.findByIsActiveAndIsDeleted(Boolean.TRUE, Boolean.FALSE);
        List<MasterCategoryResponse> activeCategoryResponse =
                activeCategories.stream().map(eachCategory -> modelMapper.map(eachCategory,
                        MasterCategoryResponse.class)).collect(Collectors.toList());
        return activeCategoryResponse;
    }

    @Override
    public MasterCategoryResponse getMasterCategoryById(Integer categoryId) {
        Optional<MasterCategoryEntity> optionalMasterCategory = masterCategoryRepository.findByIdAndIsDeleted(categoryId, Boolean.FALSE);
        if (optionalMasterCategory.isPresent()) {
            MasterCategoryEntity masterCategory = optionalMasterCategory.get();
            MasterCategoryResponse masterCategoryResponse = modelMapper.map(masterCategory,
                    MasterCategoryResponse.class);
            return masterCategoryResponse;
        }
        return null;
    }

    @Override
    public Boolean deleteMasterCategoryById(Integer categoryId) {
        Optional<MasterCategoryEntity> optionalMasterCategory = masterCategoryRepository.findById(categoryId);
        if (optionalMasterCategory.isPresent()) {
            MasterCategoryEntity masterCategory = optionalMasterCategory.get();
            masterCategory.setIsDeleted(Boolean.TRUE);
            MasterCategoryEntity deletedMasterCategory = masterCategoryRepository.save(masterCategory);
            return deletedMasterCategory.getIsDeleted();
        }
        return Boolean.FALSE;
    }

}
