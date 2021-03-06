package pt.maisis.search;

import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

public class ResultImpl implements Result {

    private final SearchResult sr;
    private final SearchMetaData smd;
    private final List rpmdsExpanded = new ArrayList();
    private final List rpmds;
    private final List rrpmds;

    public ResultImpl(final SearchResult sr,
            final SearchMetaData smd,
            final List rpmds,
            final List rrpmds) {
        this.sr = sr;
        this.smd = smd;
        this.rpmds = rpmds;
        this.rrpmds = rrpmds;
        expand(rpmds);
    }

    public SearchResult getSearchResult() {
        return this.sr;
    }

    public boolean isSearchResultPageable() {
        return (this.sr instanceof PageableSearchResult);
    }

    public String getName(final int index) {
        return ((ResultParameterMetaData) this.rpmdsExpanded.get(index)).getName();
    }

    public SearchMetaData getSearchMetaData() {
        return this.smd;
    }

    public List getResultMetaData() {
        return this.rpmds;
    }

    public List getExpandedResultMetaData() {
        return this.rpmdsExpanded;
    }

    public ResultParameterMetaData getResultMetaData(final int index) {
        return (ResultParameterMetaData) this.rpmdsExpanded.get(index);
    }

    public List getRequiredResultMetaData() {
        return this.rrpmds;
    }

    private void expand(final List rpmds) {
        Iterator it = rpmds.iterator();
        while (it.hasNext()) {
            expand((ResultParameterMetaData) it.next());
        }
    }

    private void expand(final ResultParameterMetaData rpmd) {
        if (rpmd.isComposite()) {
            Iterator it = rpmd.getChildren().iterator();
            while (it.hasNext()) {
                expand((ResultParameterMetaData) it.next());
            }
        } else {
            this.rpmdsExpanded.add(rpmd);
        }
    }
}
