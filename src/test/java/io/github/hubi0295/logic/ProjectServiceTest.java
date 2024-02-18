package io.github.hubi0295.logic;

import io.github.hubi0295.TaskConfigurationProperties;
import io.github.hubi0295.model.*;
import io.github.hubi0295.model.projection.GroupReadModel;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static org.assertj.core.api.AssertionsForClassTypes.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.mock;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class ProjectServiceTest {

    @Test
    @DisplayName("should throw illegalStateExc then configured to allow just 1 group and the other undone group exisis")
    void createGroup_noMultipleGroupsConfig_And_undoneOpenGroups_throwsIllegalStateException() {
        //given
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(true);
        //and
        TaskConfigurationProperties mockConf = configurationReturning(false);
        //system under test
        var toTest = new ProjectService(null,mockGroupRepository,null,mockConf);
        //when
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));
        //then
        assertTrue(mockGroupRepository.existsByDoneIsFalseAndProject_Id(500));
        assertTrue(mockGroupRepository.findById(1).isEmpty());

        assertThat(exception).isInstanceOf(IllegalStateException.class).hasMessageContaining("one undone group");

    }
    @Test
    @DisplayName("should throw illegalArgumentExc when configuration is ok and no projects for given id")
    void createGroup_configurationOk_And_noProjects_throwsIllegalArgumentException() {
        //given
        TaskConfigurationProperties mockConf = configurationReturning(true);
        //
        //and
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //when
        var toTest = new ProjectService(mockRepository,null,null,mockConf);
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));
        //then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("id is not found");

    }
    @Test
    @DisplayName("should throw illegalArgumentExc when configuration to allow just 1 group and no groups and no project for a given id")
    void createGroup_noMultipleGroupsConfig_And_noUndoneGroupExisis_noProjects_ThrowIllegalArgumentException() {
        //given
        TaskConfigurationProperties mockConf = configurationReturning(true);
        // and
        TaskGroupRepository MtaskGroupRepo = groupRepositoryReturning(false);
        //and
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.empty());
        //when
        var toTest = new ProjectService(mockRepository,MtaskGroupRepo,null,mockConf);
        var exception = catchThrowable(()->toTest.createGroup(LocalDateTime.now(),0));
        //then
        assertThat(exception).isInstanceOf(IllegalArgumentException.class).hasMessageContaining("id is not found");

    }
    @Test
    @DisplayName("should create a new group from project")
    public void createGroup_configurationOK_existingProject_cretaetsandsavesGroup(){
        //given
        int countbefore = inMemorygroupRepository().count();

        var today= LocalDate.now().atStartOfDay();
        //and
        var project = projectWith("opisblablaHubert",Set.of(-1,-2));
        var mockRepository = mock(ProjectRepository.class);
        when(mockRepository.findById(anyInt())).thenReturn(Optional.of(project));
        //and
        InMemGroupRepo inMem = inMemorygroupRepository();
        var serviceWithInMemRepo = new TaskGroupService(inMem,null);
        TaskConfigurationProperties mockConfig = configurationReturning(true);
        //system under test
        var toTest = new ProjectService(mockRepository,inMem,null,mockConfig);

        //when
        GroupReadModel groupRModel = toTest.createGroup(today, 1);

        //then
        assertThat(groupRModel).hasFieldOrPropertyWithValue("description","opisblablaHubert");
        assertThat(groupRModel.getDeadline()).isEqualTo(today.minusDays(1));
//        assertThat(groupRModel.getTasks()).isEqualTo(today.minusDays(1));
        assertThat(countbefore).isEqualTo(inMemorygroupRepository().count());
    }
    private static TaskGroupRepository groupRepositoryReturning( boolean b) {
        var mockGroupRepository = mock(TaskGroupRepository.class);
        when(mockGroupRepository.existsByDoneIsFalseAndProject_Id(anyInt())).thenReturn(b);
        return mockGroupRepository;
    }

    private static TaskConfigurationProperties configurationReturning(boolean b) {
        var mockTemplate = mock(TaskConfigurationProperties.Template.class);
        when(mockTemplate.isAllowMultipleTasks()).thenReturn(b);
        //and
        var mockConfig = mock(TaskConfigurationProperties.class);
        when(mockConfig.getTemplate()).thenReturn(mockTemplate);
        return mockConfig;
    }
    private InMemGroupRepo inMemorygroupRepository() {
        return new InMemGroupRepo();
    }
    private static class InMemGroupRepo implements  TaskGroupRepository{

                private Map<Integer,TaskGroup> map = new HashMap<>();
                private int index=0;
                public int count(){
                    return map.values().size();
                }
                @Override
                public List<TaskGroup> findAll() {
                    return map.values().stream().collect(Collectors.toList());
                }

                @Override
                public Optional<TaskGroup> findById(Integer id) {
                    return Optional.ofNullable(map.get(id));
                }

                @Override
                public TaskGroup save(TaskGroup entity) {
                    if(entity.getId()==0) {
                        try {
                            var field = TaskGroup.class.getDeclaredField("id");
                            field.setAccessible(true);
                            field.set(entity, ++index);
                        } catch (NoSuchFieldException | IllegalAccessException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    map.put(entity.getId(),entity);
                    return entity;
                }

                @Override
                public boolean existsByDoneIsFalseAndProject_Id(Integer projectId) {
                    return map
                            .values()
                            .stream()
                            .filter(group->!group.isDone())
                            .filter(group->group.getProject()!=null && group.getProject()
                                    .getId()==projectId)
                            .findAny()
                            .isPresent();
                }

    }
    private Project projectWith(String desc, Set<Integer> daysToDeadline){
        var result = mock(Project.class);
        Set<ProjectStep> daysTo = daysToDeadline.stream().map(days->{
            var step = mock(ProjectStep.class);
            when(step.getDescription()).thenReturn("test");
            when(step.getDaysToDeadline()).thenReturn(days);
            return step;
        }).collect(Collectors.toSet());
        when(result.getDescription()).thenReturn(desc);
        when(result.getSteps()).thenReturn(daysTo);
        return result;
    }
}