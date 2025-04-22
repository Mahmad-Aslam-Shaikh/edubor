package com.enotes_api.service.implementation;

import com.enotes_api.entity.FileEntity;
import com.enotes_api.entity.MasterCategoryEntity;
import com.enotes_api.entity.NotesEntity;
import com.enotes_api.exception.ExceptionMessages;
import com.enotes_api.exception.FileUploadFailedException;
import com.enotes_api.exception.InvalidFileException;
import com.enotes_api.exception.ResourceNotFoundException;
import com.enotes_api.repository.NotesRepository;
import com.enotes_api.request.NotesRequest;
import com.enotes_api.response.NotesPaginationResponse;
import com.enotes_api.response.NotesResponse;
import com.enotes_api.service.FileService;
import com.enotes_api.service.MasterCategoryService;
import com.enotes_api.service.NotesService;
import com.enotes_api.utility.MapperUtil;
import com.enotes_api.utility.PageUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
@AllArgsConstructor
public class NotesServiceImpl implements NotesService {

    private NotesRepository notesRepository;

    private MasterCategoryService masterCategoryService;

    private FileService fileService;

    private MapperUtil mapperUtil;


    @Override
    public NotesResponse saveNotes(NotesRequest notesRequest, List<MultipartFile> files) throws ResourceNotFoundException,
            FileUploadFailedException, IOException, InvalidFileException {
        Integer categoryId = notesRequest.getCategory().getId();
        MasterCategoryEntity masterCategory = masterCategoryService.getMasterCategoryById(categoryId);

        NotesEntity notesEntity = mapperUtil.map(notesRequest, NotesEntity.class);
        notesEntity.setCategory(masterCategory);

        if (!CollectionUtils.isEmpty(files)) {
            List<FileEntity> fileEntities = new ArrayList<>();
            for (MultipartFile file : files) {
                FileEntity fileEntity = fileService.saveFile(file);
                fileEntities.add(fileEntity);
            }
            notesEntity.setFiles(fileEntities);
        }

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

    @Override
    public NotesPaginationResponse getUserNotesWithPagination(Integer userId, Integer pageNo) {
        Pageable pageable = PageRequest.of(pageNo, PageUtil.pageSize);
        Page<NotesEntity> userNotes = notesRepository.findByCreatedBy(userId, pageable);

        long totalElements = userNotes.getTotalElements();
        int totalPages = userNotes.getTotalPages();
        int pageNum = userNotes.getNumber();
        int pageSize = userNotes.getSize();
        boolean isEmpty = userNotes.isEmpty();
        boolean isFirstPage = userNotes.isFirst();
        boolean isLastPage = userNotes.isLast();

        List<NotesEntity> paginatedUserNotes = userNotes.getContent();
        List<NotesResponse> paginatedNotesResponse = mapperUtil.mapList(paginatedUserNotes, NotesResponse.class);

        return NotesPaginationResponse.builder()
                .notes(paginatedNotesResponse)
                .pageNo(pageNum)
                .pageSize(pageSize)
                .totalElements(totalElements)
                .totalPages(totalPages)
                .isPageEmpty(isEmpty)
                .isFirstPage(isFirstPage)
                .isLastPage(isLastPage)
                .build();
    }

    @Override
    public NotesResponse updateNotes(Integer notesId, NotesRequest notesRequest, List<MultipartFile> files)
            throws ResourceNotFoundException, InvalidFileException, FileUploadFailedException, IOException {
        NotesEntity notesToBeUpdated = getNotesById(notesId);

        if (notesRequest != null) {
            if (!ObjectUtils.isEmpty(notesRequest.getCategory())) {
                MasterCategoryEntity masterCategory =
                        masterCategoryService.getMasterCategoryById(notesRequest.getCategory().getId());
                notesToBeUpdated.setCategory(masterCategory);
            }

            if (!ObjectUtils.isEmpty(notesRequest.getTitle()))
                notesToBeUpdated.setTitle(notesRequest.getTitle());

            if (!ObjectUtils.isEmpty(notesRequest.getDescription()))
                notesToBeUpdated.setDescription(notesRequest.getDescription());
        }

        if (!CollectionUtils.isEmpty(files)) {
            List<FileEntity> fileEntities = notesToBeUpdated.getFiles();

            if (fileEntities == null) {
                fileEntities = new ArrayList<>();
                notesToBeUpdated.setFiles(fileEntities);
            }

            for (MultipartFile file : files) {
                FileEntity fileEntity = fileService.saveFile(file);
                fileEntities.add(fileEntity);
            }
        }

        notesToBeUpdated = notesRepository.save(notesToBeUpdated);
        return mapperUtil.map(notesToBeUpdated, NotesResponse.class);

    }

}
