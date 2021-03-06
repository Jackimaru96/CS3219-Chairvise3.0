package sg.edu.nus.comp.cs3219.viz.common.datatransfer;

import sg.edu.nus.comp.cs3219.viz.common.entity.Presentation;
import sg.edu.nus.comp.cs3219.viz.common.entity.PresentationSection;

import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

public class AnalysisRequest {

    private long userId;

    private List<Presentation.FileMappings> mappings = new ArrayList<>();

    private List<PresentationSection.Selection> selections = new ArrayList<>();

    @NotEmpty
    private List<PresentationSection.Record> involvedRecords = new ArrayList<>();

    private List<PresentationSection.Filter> filters = new ArrayList<>();

    private List<PresentationSection.Joiner> joiners = new ArrayList<>();

    private List<PresentationSection.Grouper> groupers = new ArrayList<>();

    private List<PresentationSection.Sorter> sorters = new ArrayList<>();

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public List<Presentation.FileMappings> getMappings() {
        return mappings;
    }

    public void setMappings(List<Presentation.FileMappings> mappings) {
        this.mappings = mappings;
    }

    public List<PresentationSection.Selection> getSelections() {
        return selections;
    }

    public void setSelections(List<PresentationSection.Selection> selections) {
        this.selections = selections;
    }

    public List<PresentationSection.Record> getInvolvedRecords() {
        return involvedRecords;
    }

    public void setInvolvedRecords(List<PresentationSection.Record> involvedRecords) {
        this.involvedRecords = involvedRecords;
    }

    public List<PresentationSection.Filter> getFilters() {
        return filters;
    }

    public void setFilters(List<PresentationSection.Filter> filters) {
        this.filters = filters;
    }

    public List<PresentationSection.Joiner> getJoiners() {
        return joiners;
    }

    public void setJoiners(List<PresentationSection.Joiner> joiners) {
        this.joiners = joiners;
    }

    public List<PresentationSection.Grouper> getGroupers() {
        return groupers;
    }

    public void setGroupers(List<PresentationSection.Grouper> groupers) {
        this.groupers = groupers;
    }

    public List<PresentationSection.Sorter> getSorters() {
        return sorters;
    }

    public void setSorters(List<PresentationSection.Sorter> sorters) {
        this.sorters = sorters;
    }
}
