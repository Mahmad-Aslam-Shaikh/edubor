package com.enotes_api.service.implementation;

import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.exception.ExceptionMessages;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.MasterCategoryRepository;
import com.enotes_api.request.MasterCategoryRequest;
import com.enotes_api.response.MasterCategoryResponse;
import com.enotes_api.service.MasterCategoryService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.time.LocalDateTime;
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
        masterCategoryEntity = masterCategoryRepository.save(masterCategoryEntity);
        MasterCategoryResponse masterCategoryResponse = modelMapper.map(masterCategoryEntity,
                MasterCategoryResponse.class);
        return masterCategoryResponse;
    }

    public MasterCategoryEntity updateMasterCategory(MasterCategoryRequest masterCategoryRequest,
                                                     MasterCategoryEntity masterCategoryEntity) {
        if (!ObjectUtils.isEmpty(masterCategoryRequest.getName()))
            masterCategoryEntity.setName(masterCategoryRequest.getName());

        if (!ObjectUtils.isEmpty(masterCategoryRequest.getDescription()))
            masterCategoryEntity.setDescription(masterCategoryRequest.getDescription());

        masterCategoryEntity.setUpdatedBy(1);
        masterCategoryEntity.setUpdatedOn(LocalDateTime.now());

        return masterCategoryEntity;
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
        List<MasterCategoryEntity> activeCategories =
                masterCategoryRepository.findByIsActiveAndIsDeleted(Boolean.TRUE, Boolean.FALSE);
        List<MasterCategoryResponse> activeCategoryResponse =
                activeCategories.stream().map(eachCategory -> modelMapper.map(eachCategory,
                        MasterCategoryResponse.class)).collect(Collectors.toList());
        return activeCategoryResponse;
    }

    @Override
    public MasterCategoryResponse getMasterCategoryById(Integer categoryId) throws ResourceNotFoundException {
        Optional<MasterCategoryEntity> optionalMasterCategory =
                masterCategoryRepository.findByIdAndIsDeleted(categoryId, Boolean.FALSE);
        if (optionalMasterCategory.isPresent()) {
            MasterCategoryEntity masterCategory = optionalMasterCategory.get();
            MasterCategoryResponse masterCategoryResponse = modelMapper.map(masterCategory,
                    MasterCategoryResponse.class);
            return masterCategoryResponse;
        }
        throw new ResourceNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND_MESSAGE + categoryId);
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

    @Override
    public MasterCategoryResponse updateMasterCategory(MasterCategoryRequest masterCategoryRequest) throws ResourceNotFoundException {
        Optional<MasterCategoryEntity> optionalMasterCategory =
                masterCategoryRepository.findByIdAndIsDeleted(masterCategoryRequest.getId(), Boolean.FALSE);
        if (optionalMasterCategory.isPresent()) {
            MasterCategoryEntity masterCategory = optionalMasterCategory.get();

            if (!ObjectUtils.isEmpty(masterCategoryRequest.getName()))
                masterCategory.setName(masterCategoryRequest.getName());

            if (!ObjectUtils.isEmpty(masterCategoryRequest.getDescription()))
                masterCategory.setDescription(masterCategoryRequest.getDescription());

            masterCategory = masterCategoryRepository.save(masterCategory);
            MasterCategoryResponse masterCategoryResponse = modelMapper.map(masterCategory,
                    MasterCategoryResponse.class);
            return masterCategoryResponse;
        }
        throw new ResourceNotFoundException(ExceptionMessages.CATEGORY_NOT_FOUND_MESSAGE + masterCategoryRequest.getId());

    }

}
