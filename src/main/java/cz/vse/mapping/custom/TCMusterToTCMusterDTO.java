package cz.vse.mapping.custom;

import cz.vse.dto.TCInstanceDTO;
import cz.vse.dto.TCMusterDTO;
import cz.vse.entity.StatusEnum;
import cz.vse.entity.TCInstance;
import cz.vse.entity.TCMuster;
import cz.vse.entity.TSInstance;
import cz.vse.service.TCInstanceService;
import ma.glasnost.orika.CustomMapper;
import ma.glasnost.orika.MappingContext;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by pcejka on 26.11.2016.
 */
@Component
public class TCMusterToTCMusterDTO extends CustomMapper<TCMuster, TCMusterDTO> {
    private final Logger l = Logger.getLogger(this.getClass());

    @Autowired
    TCInstanceService tcInstanceService;

    @Override
    public void mapAtoB(TCMuster tcMuster, TCMusterDTO tcMusterDTO, MappingContext context) {
        TCInstance tcInstance = tcInstanceService.findLastTCInstanceByTCMuster(tcMuster);
        StatusEnum statusEnum = tcInstance!= null ? getTCInstanceStatusFromTSInstancesStatuses(tcInstance) : StatusEnum.NORUN;
        tcMusterDTO.setStatus(statusEnum);
        super.mapAtoB(tcMuster, tcMusterDTO, context);
    }

    private StatusEnum getTCInstanceStatusFromTSInstancesStatuses(TCInstance tcInstance) {
        List<TSInstance> tsInstanceList = tcInstance.getTsInstances();
        List<StatusEnum> statusEnumList = new ArrayList<>();

        for (TSInstance tsInstance : tsInstanceList) {
            statusEnumList.add(tsInstance.getStatus());
        }

        if (statusEnumList.contains(StatusEnum.FAILED)) {
            return StatusEnum.FAILED;
        }
        if (statusEnumList.contains(StatusEnum.BLOCKED)) {
            return StatusEnum.BLOCKED;
        }

        if (isItAllNorun(statusEnumList)) {
            return StatusEnum.NORUN;
        }
        if (isItAllPassed(statusEnumList)) {
            return StatusEnum.PASSED;
        }

        return StatusEnum.NOTCOMPLETED;
    }

    private boolean isItAllPassed(List<StatusEnum> tsInstanceList) {
        for (StatusEnum statusEnum : tsInstanceList) {
            if (statusEnum != StatusEnum.PASSED) {
                return false;
            }
        }
        return true;
    }

    private boolean isItAllNorun(List<StatusEnum> tsInstanceList) {
        for (StatusEnum statusEnum : tsInstanceList) {
            if (statusEnum != StatusEnum.NORUN) {
                return false;
            }
        }
        return true;
    }
}