package com.enotes_api.service.implementation;

import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.entity.NotesEntity;
import com.enotes_api.exception.ExceptionMessages;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.NotesRepository;
import com.enotes_api.request.NotesRequest;
import com.enotes_api.response.NotesResponse;
import com.enotes_api.service.MasterCategoryService;
import com.enotes_api.service.NotesService;
import com.enotes_api.utility.MapperUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {

    private NotesRepository notesRepository;

    private MasterCategoryService masterCategoryService;

    private MapperUtil mapperUtil;


    @Override
    public NotesResponse saveNotes(NotesRequest notesRequest) throws ResourceNotFoundException {
        Integer categoryId = notesRequest.getCategory().getId();
        MasterCategoryEntity masterCategory = masterCategoryService.getMasterCategoryById(categoryId);

        NotesEntity notesEntity = mapperUtil.map(notesRequest, NotesEntity.class);
        notesEntity.setCategory(masterCategory);

        NotesEntity savedNotes = notesRepository.save(notesEntity);
        return mapperUtil.map(savedNotes, NotesResponse.class);
    }

    @Override
    public NotesEntity getNotesById(Integer notesId) throws ResourceNotFoundException {
        return notesRepository.findById(notesId)
                .orElseThrow(() -> new ResourceNotFoundException(ExceptionMessages.NOTES_NOT_FOUND_MESSAGE + notesId));
    }

    @Override
    public List<NotesResponse> getAllNotes() {
        List<NotesEntity> allNotes = notesRepository.findAll();
        return mapperUtil.mapList(allNotes, NotesResponse.class);

    }
}
