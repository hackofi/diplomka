package cz.vse.mapping.custom;

import cz.vse.dto.WorkListDTO;
import cz.vse.entity.TCMuster;
import cz.vse.entity.WorkList;
import cz.vse.entity.WorkTC;
import cz.vse.service.TCMusterService;
import cz.vse.service.WorkTCService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcejka on 26.11.2016.
 */
@Component
public class WorkListToWorkListDTO extends CustomMapper<WorkList, WorkListDTO> {
    private final Logger l = Logger.getLogger(this.getClass());
    @Autowired
    private TCMusterService tcMusterService;

    @Autowired
    private WorkTCService workTCService;

    @Override
    public void mapAtoB(WorkList workList, WorkListDTO workListDTO, MappingContext context) {
        l.warn("A -> B");
        workListDTO.setName(workList.getName());
        workListDTO.setProject_id(workList.getProject().getId());

    }

    @Override
    public void mapBtoA(WorkListDTO workListDTO, WorkList workList, MappingContext context) {
        l.info("B -> A");
        List<WorkTC> workTCList = createWorkTCForWorkList(workList, workListDTO.getTcMuster_id());
        workList.setWorkTCList(workTCList);
    }

    private List<Long> getTCMusterIdFromWorkTCDTO(WorkList workList) {
        List<WorkTC> workTCList = workList.getWorkTCList();
        List<Long> tcMusters_id = new ArrayList<>();
        for (WorkTC workTC : workTCList) {
            tcMusters_id.add(workTC.getTcMuster().getId());
        }
        return tcMusters_id;
    }

    private List<WorkTC> createWorkTCForWorkList(WorkList workList, List<Long> tcMusterIdList) {
        List<WorkTC> workTCList = new ArrayList<>();
        List<Long> tcMusterIdListOld = new ArrayList<>();
        if (workList.getWorkTCList() == null) {
            l.info("(workList.getWorkTCList() je NULL");
        } else {
            l.info("(workList.getWorkTCList() není null");
            workTCList.addAll(workList.getWorkTCList());
            for (WorkTC workTC : workList.getWorkTCList()) {
                tcMusterIdListOld.add(workTCService.findWorkTCById(workTC.getId()).getTcMuster().getId());
            }
        }


        for (Long tcMusterId : tcMusterIdList) {
            if (!tcMusterIdListOld.contains(tcMusterId)) {
                WorkTC workTC = new WorkTC();
                TCMuster tcMuster = tcMusterService.findTestCaseMusterById(tcMusterId);
                workTC.setCreatedDateTime(LocalDateTime.now());
                workTC.setPriority(tcMuster.getPriority());
                workTC.setTcMuster(tcMuster);
                workTC.setWorkList(workList);
                workTCList.add(workTC);
                workTCService.createWorkTC(workTC);
                l.info("vytvořeno i " + workTC);
            }
        }

        return workTCList;
    }

}
