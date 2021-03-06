package cz.vse.service.impl;

import cz.vse.dto.*;
import cz.vse.entity.Person;
import cz.vse.entity.WorkList;
import cz.vse.entity.WorkTC;
import cz.vse.repository.WorkListRepository;
import cz.vse.service.PersonService;
import cz.vse.service.WorkListService;
import cz.vse.service.WorkTCService;
import ma.glasnost.orika.MapperFacade;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Created by pcejka on 03.10.2016.
 */
@Service
@Transactional
public class WorkListServiceImpl implements WorkListService {
    private final Logger l = Logger.getLogger(this.getClass());
    @Autowired
    private MapperFacade mapper;

    @Autowired
    private WorkListRepository workListRepository;

    @Autowired
    private WorkTCService workTCService;

    @Autowired
    private PersonService personService;

    @Override
    public void createWorkList(WorkList workList) {
        l.info("with: " + workList);
        workList.setCreatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
        l.info("created: " + workList);
    }

    @Override
    public void createWorkList(WorkListDTO workListDTO) {
        l.info("with: " + workListDTO);
        WorkList workList = mapper.map(workListDTO, WorkList.class);
        workList.setCreatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
        l.info("created: " + workList);
    }

    @Override
    public void createWorkList(WorkListForm workListForm) {
        l.info("with: " + workListForm);
        WorkList workList = mapper.map(workListForm, WorkList.class);
        workList.setCreatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
        l.info("created: " + workList);
    }

    @Override
    public void updateWorkList(WorkList workList) {
        l.info("with: " + workList);
        workList.setUpdatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
        l.info("updated: " + workList);
    }

    @Override
    public void updateWorkList(WorkListDTO workListDTO) {
        l.info("with: " + workListDTO);
        WorkList workList = findWorkListById(workListDTO.getId());
        mapper.map(workListDTO, workList);
        workList.setUpdatedDateTime(LocalDateTime.now());
        workTCService.updateWorkTCEntity(workList.getWorkTCList());
        workListRepository.save(workList);
        l.info("updated: " + workList);
    }

    @Override
    /** ÚPRAVA WORKLISTU - optimalizace */
    public void updateWorkList(WorkListForm workListForm) {
        l.info("with: " + workListForm);
        WorkList workList = findWorkListById(workListForm.getId());
        mapper.map(workListForm, workList);
        workList.setUpdatedDateTime(LocalDateTime.now());
        workTCService.updateWorkTCEntity(workList.getWorkTCList());
        workListRepository.save(workList);
        l.info("updated: " + workList);
    }

    @Override
    public List<WorkList> findAllWorkList() {
        return workListRepository.findAll();
    }

    @Override
    public List<WorkListDTO> findAllWorkListDTO() {
        List<WorkList> workListList = workListRepository.findAll();
        List<WorkListDTO> workListDTOList = mapper.mapAsList(workListList, WorkListDTO.class);
        l.info("found: " + workListDTOList);
        return workListDTOList;
    }

    @Override
    public WorkList findWorkListById(long id) {
        l.info("with: " + id);
        return workListRepository.findOne(id);
    }

    @Override
    public WorkListDTO findWorkListDTOById(long id) {
        l.info("with: " + id);
        WorkList workList = workListRepository.findOne(id);
        WorkListDTO workListDTO = mapper.map(workList, WorkListDTO.class);
        l.info("found: " + workListDTO);
        return workListDTO;
    }

    @Override
    public WorkTCWrapper findWorkTCWrapperByWorklistId(long id) {
        l.info("with: " + id);
        WorkTCWrapper workTCWrapper = new WorkTCWrapper();
        WorkList workList = workListRepository.findOne(id);

        List<WorkTC> workTCS = workList.getWorkTCList();
        List<WorkTCDTO> workTCDTOS = mapper.mapAsList(workTCS, WorkTCDTO.class);
        workTCDTOS.sort(Comparator.comparing(WorkTCDTO::getId));            // seřazení
        workTCWrapper.setWorkList_id(id);
        workTCWrapper.setWorkTCDTOList(workTCDTOS);

        l.info("found: " + workTCWrapper);
        return workTCWrapper;
    }

    @Override
    public WorkListForm findWorkListFormById(long id) {
        l.info("with: " + id);
        WorkList workList = workListRepository.findOne(id);
        WorkListForm workListForm = mapper.map(workList, WorkListForm.class);
        l.info("found: " + workListForm);
        return workListForm;
    }

    @Override
    public List<WorkListList> findAllWorkListListByAuthorId(long id) {
        l.info("with: " + id);
        Person author = personService.findPersonById(id);
        List<WorkListList> workListLists;
        List<WorkList> workListList = workListRepository.findAllWorkListDTOByAuthor(author);
        workListLists = mapper.mapAsList(workListList, WorkListList.class);
        l.info("found: " + workListLists);
        return workListLists;
    }

    @Override
    public List<WorkListList> findAllWorkListListByMember(long id) {
        l.info("with: " + id);
        Person member = personService.findPersonById(id);

        List<WorkListList> workListLists;
        List<WorkList> workListList = workListRepository.findAllWorkListDTOByProjectIn(member.getProjectsMember());
        workListLists = mapper.mapAsList(workListList, WorkListList.class);
        l.info("found: " + workListLists);
        return workListLists;
    }

    @Override
    public List<Long> getListTCMusterIdByWorklistInWorkTCDTO(long id) {
        l.info("with: " + id);
        List<WorkTCDTO> workTCDTOList = workTCService.findAllWorkTCDTOByWorkListId(id);
        List<Long> listTCMusterIdByWorklistInWorkTCDTO = new ArrayList<>();
        for (WorkTCDTO workTCDTO : workTCDTOList) {
            Long tcMuster_id = workTCDTO.getTcMuster_id();
            listTCMusterIdByWorklistInWorkTCDTO.add(tcMuster_id);
        }
        l.info("found: " + listTCMusterIdByWorklistInWorkTCDTO);
        return listTCMusterIdByWorklistInWorkTCDTO;
    }

    public List<WorkListDTO> findAllWorkListDTOByMemberToday(Person person) {
        l.info("with: " + person);
        LocalDate plannedExecution = LocalDate.now();
        long personId = person.getId();
        List<WorkList> workListList = workListRepository.findAllWorkListDTOByMemberToday(plannedExecution, personId);
        l.warn("xxx: " + workListList);
        List<WorkListDTO> workListDTOList = mapper.mapAsList(workListList, WorkListDTO.class);

        l.info("found: " + workListDTOList);
        return workListDTOList;
    }

    public List<WorkListDTO> findAllWorkListDTOByMemberTomorrow(Person person) {
        l.info("with: " + person);
        LocalDate plannedExecution = LocalDate.now().plus(1, ChronoUnit.DAYS);
        long personId = person.getId();
        List<WorkList> workListList = workListRepository.findAllWorkListDTOByMemberToday(plannedExecution, personId);
        l.warn("xxx2: " + workListList);
        List<WorkListDTO> workListDTOList = mapper.mapAsList(workListList, WorkListDTO.class);

        l.info("found: " + workListDTOList);
        return workListDTOList;
    }

    @Override
    public List<WorkListDTO> findAllWorkListDTOByMemberLastThreeDays(Person person) {
        l.info("with: " + person);
        LocalDate dayStart = LocalDate.now().minus(3, ChronoUnit.DAYS);
        LocalDate dayEnd = LocalDate.now();
        long personId = person.getId();
        List<WorkList> workListList = workListRepository.findAllWorkListDTOByMemberBetweenDays(personId, dayStart, dayEnd);
        List<WorkListDTO> workListDTOList = mapper.mapAsList(workListList, WorkListDTO.class);
        l.info("found: " + workListDTOList);
        return workListDTOList;
    }

    @Override
    public void updateWorkTC(WorkTCWrapper workTCWrapper) {
        l.info("with: " + workTCWrapper);
        l.fatal("PŘED");
        WorkList workList = findWorkListById(workTCWrapper.getWorkList_id());
        List<WorkTCDTO> workTCDTOS = workTCWrapper.getWorkTCDTOList();
        List<WorkTC> workTCS = mapper.mapAsList(workTCDTOS, WorkTC.class);
        workList.setWorkTCList(workTCS);

        workTCService.updateWorkTCEntity(workList.getWorkTCList());
        workListRepository.save(workList);


        l.fatal("PO");
        l.info("updated: " + workList);
    }
}