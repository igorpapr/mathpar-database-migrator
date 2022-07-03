package mathpar.web.learning.databasemigrator.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import mathpar.web.learning.databasemigrator.model.LegacySectionList;
import mathpar.web.learning.databasemigrator.model.Section;
import mathpar.web.learning.databasemigrator.model.Task;
import mathpar.web.learning.databasemigrator.repository.SectionRepository;
import mathpar.web.learning.databasemigrator.repository.TaskRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.transaction.Transactional;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Slf4j
@AllArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class TaskMigrator {

    SectionRepository sectionRepository;

    TaskRepository taskRepository;

    ObjectMapper objectMapper;

    @PostConstruct
    @Transactional
    public void migrate() {
        List<Task> legacyTasks = taskRepository.findAll();
        List<Section> parsedSections = parseAndBuildSections(legacyTasks);
        sectionRepository.saveAll(parsedSections);
    }

    private List<Section> parseAndBuildSections(List<Task> legacyTasks) {
        return legacyTasks.stream()
                .map(task -> parseTask(task.getTask(), task))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private List<Section> parseTask(String taskJson, Task parentTask) {
        log.info("Parsing task for taskId: {}", parentTask.getId());
        try {
            LegacySectionList legacySectionList = objectMapper.readValue(taskJson, LegacySectionList.class);
            return legacySectionList.getSections().stream()
                    .map(legacySection -> Section.builder()
                             .task(legacySection.getTask())
                             .answer(legacySection.getAnswer())
                             .latex(legacySection.getLatex())
                             .legacySectionId(legacySection.getSectionId())
                             .parentTask(parentTask)
                            .build())
                    .collect(Collectors.toList());
        } catch (JsonProcessingException e) {
            log.error("Could not parse task json into a LegacySectionList class, error: {}", e.getMessage());
            return Collections.emptyList();
        }
    }
}
