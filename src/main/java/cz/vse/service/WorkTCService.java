package cz.vse.service;

import cz.vse.dto.TCInstanceRunDTO;
import cz.vse.dto.TCMusterDTO;
import cz.vse.dto.WorkTCDTO;
import cz.vse.entity.*;

import java.util.List;

/**
 * Created by pcejka on 03.10.2016.
 */
public interface WorkTCService {
    void createWorkTC(WorkTC workTC);

    void createWorkTC(WorkTCDTO workTCDTO);

    void updateWorkTC(WorkTC workTC);

    void updateWorkTC(WorkTCDTO workTCDTO);

    List<WorkTC> findAllWorkTC();

    List<WorkTCDTO> findAllWorkTCDTO();

    WorkTC findWorkTCById(long id);

    WorkTCDTO findWorkTCDTOById(long id);

}