package com.enotes_api.scheduler;

import com.enotes_api.service.NotesService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@AllArgsConstructor
public class NotesScheduler {

    private NotesService notesService;

    @Scheduled(cron = "0 1 1 * * *")
    public void deleteOutdatedNotes() {
        LocalDateTime previousSevenDaysDateTime = LocalDateTime.now().minusDays(7);
        notesService.deleteOutDatedNotesFromBin(previousSevenDaysDateTime);
    }

}
