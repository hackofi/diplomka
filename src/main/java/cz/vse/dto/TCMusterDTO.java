package cz.vse.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Created by pcejka on 10.10.2016.
 */

public class TCMusterDTO extends BaseDTO {

    private LocalDateTime createdDateTime;
    private String name;
    private Long project_id;
    private List<Long> tsMusters_id;
    private List<Long> tcInstances_id;

    public LocalDateTime getCreatedDateTime() {
        return createdDateTime;
    }

    public void setCreatedDateTime(LocalDateTime createdDateTime) {
        this.createdDateTime = createdDateTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getTsMusters_id() {
        return tsMusters_id;
    }

    public void setTsMusters_id(List<Long> tsMusters_id) {
        this.tsMusters_id = tsMusters_id;
    }

    public List<Long> getTcInstances_id() {
        return tcInstances_id;
    }

    public void setTcInstances_id(List<Long> tcInstances_id) {
        this.tcInstances_id = tcInstances_id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TCMusterDTO)) return false;
        if (!super.equals(o)) return false;

        TCMusterDTO that = (TCMusterDTO) o;

        if (getCreatedDateTime() != null ? !getCreatedDateTime().equals(that.getCreatedDateTime()) : that.getCreatedDateTime() != null)
            return false;
        if (getName() != null ? !getName().equals(that.getName()) : that.getName() != null) return false;
        return project_id != null ? project_id.equals(that.project_id) : that.project_id == null;

    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (getCreatedDateTime() != null ? getCreatedDateTime().hashCode() : 0);
        result = 31 * result + (getName() != null ? getName().hashCode() : 0);
        result = 31 * result + (project_id != null ? project_id.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "TCMusterDTO{" +
                "createdDateTime=" + createdDateTime +
                ", name='" + name + '\'' +
                ", project_id=" + project_id +
                '}';
    }

    public Long getProject_id() {
        return project_id;
    }

    public void setProject_id(Long project_id) {
        this.project_id = project_id;
    }
}