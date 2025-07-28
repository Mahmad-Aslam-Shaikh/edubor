package com.enotes_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FavoriteNotesPKs implements Serializable {

    @Column(name = "USER_ID")
    private Integer userId;

    @Column(name = "NOTE_ID")
    private Integer noteId;

}
