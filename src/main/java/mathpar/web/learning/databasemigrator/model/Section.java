package mathpar.web.learning.databasemigrator.model;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "sections")
public class Section {

    @Id
    @Column(name = "ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "TASK", nullable = false, columnDefinition = "TEXT")
    String task;

    @Column(name = "ANSWER", columnDefinition = "TEXT")
    String answer;

    @Column(name = "LATEX", columnDefinition = "TEXT")
    String latex;

    @Column(name = "LEGACY_SECTION_ID")
    Integer legacySectionId;

    @ManyToOne
    @JoinColumn(name = "TASK_ID", nullable = false)
    Task parentTask;

}
