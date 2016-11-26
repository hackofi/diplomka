package cz.vse.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pcejka on 10.10.2016.
 */

public class TCInstanceDTO extends BaseDTO {

    private String name;
    private Long tcMuster_id;
    private List<Long> tsInstances_id;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getTcMuster_id() {
        return tcMuster_id;
    }

    public void setTcMuster_id(Long tcMuster_id) {
        this.tcMuster_id = tcMuster_id;
    }

    public List<Long> getTsInstances_id() {
        return tsInstances_id;
    }

    public void setTsInstances_id(List<Long> tsInstances_id) {
        this.tsInstances_id = tsInstances_id;
    }
}