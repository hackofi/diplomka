package cz.vse.dto;

import cz.vse.entity.Defect;
import cz.vse.entity.TCMuster;
import cz.vse.entity.TSInstance;

import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pcejka on 10.10.2016.
 */




public class TSDTO extends BaseDTO {

    private LocalDateTime createdDateTime;
    private LocalDateTime updatedDateTime;
    private String action;
    private String expected;

    private List<Long> defects_id;

    private List<Long> TSInstances_id;

    private List<Long> TCMusters_id;

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public LocalDateTime getUpdatedDateTime() {
        return updatedDateTime;
    }

    public void setUpdatedDateTime(LocalDateTime updatedDateTime) {
        this.updatedDateTime = updatedDateTime;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getExpected() {
        return expected;
    }

    public void setExpected(String expected) {
        this.expected = expected;
    }

    public List<Long> getDefects_id() {
        return defects_id;
    }

    public void setDefects_id(List<Long> defects_id) {
        this.defects_id = defects_id;
    }

    public List<Long> getTSInstances_id() {
        return TSInstances_id;
    }

    public void setTSInstances_id(List<Long> TSInstances_id) {
        this.TSInstances_id = TSInstances_id;
    }

    public List<Long> getTCMusters_id() {
        return TCMusters_id;
    }

    public void setTCMusters_id(List<Long> TCMusters_id) {
        this.TCMusters_id = TCMusters_id;
    }
}

