package com.enotes_api.entity;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity(name = "FAVORITE_NOTES")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class FavoriteNotesEntity {

    @EmbeddedId
    private FavoriteNotesPKs id;

    @ManyToOne
    @MapsId("noteId")
    @JoinColumn(name = "NOTE_ID")
    private NotesEntity note;

}
