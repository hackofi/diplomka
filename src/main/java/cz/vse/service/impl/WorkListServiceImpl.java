package cz.vse.service.impl;

import cz.vse.dto.WorkListDTO;
import cz.vse.dto.WorkTCDTO;
import cz.vse.entity.WorkList;
import cz.vse.entity.WorkTC;
import cz.vse.repository.WorkListRepository;
import cz.vse.repository.WorkTCRepository;
import cz.vse.service.WorkListService;
import cz.vse.service.WorkTCService;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pcejka on 03.10.2016.
 */
@Service
@Transactional
public class WorkListServiceImpl implements WorkListService {
    @Autowired
    private MapperFacade mapper;

    @Autowired
    private WorkListRepository workListRepository;

    @Override
    public void createWorkList(WorkList workList) {
        workList.setCreatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
    }

    @Override
    public void createWorkList(WorkListDTO workListDTO) {
        WorkList workList;
        workList = mapper.map(workListDTO, WorkList.class);
        workList.setCreatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
    }

    @Override
    public void updateWorkList(WorkList workList) {
        workList.setUpdatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
    }

    @Override
    public void updateWorkList(WorkListDTO workListDTO) {
        WorkList workList = mapper.map(workListDTO, WorkList.class);
        workList.setUpdatedDateTime(LocalDateTime.now());
        workListRepository.save(workList);
    }

    @Override
    public List<WorkList> findAllWorkList() {
        return workListRepository.findAll();
    }

    @Override
    public List<WorkListDTO> findAllWorkListDTO() {
        List<WorkList> workListList = workListRepository.findAll();
        List<WorkListDTO> workListDTOList = mapper.mapAsList(workListList, WorkListDTO.class);
        return workListDTOList;
    }

    @Override
    public WorkList findWorkListById(long id) {
        return workListRepository.findOne(id);
    }

    @Override
    public WorkListDTO findWorkListDTOById(long id) {
        WorkList workList = workListRepository.findOne(id);
        WorkListDTO workListDTO = mapper.map(workList, WorkListDTO.class);
        return workListDTO;
    }
}