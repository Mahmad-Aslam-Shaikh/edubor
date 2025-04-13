package com.enotes_api.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "NOTES")
@EntityListeners(AuditingEntityListener.class)
public class NotesEntity extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "NOTE_ID")
    private Integer id;

    @Column(name = "TITLE", length = 50, nullable = false)
    private String title;

    @Column(name = "DESCRIPTION", length = 250, nullable = false)
    private String description;

    @ManyToOne
    @JoinColumn(name = "CATEGORY_ID")
    private MasterCategoryEntity category;

}
