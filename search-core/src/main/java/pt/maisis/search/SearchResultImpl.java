package pt.maisis.search;

import java.util.List;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.io.Serializable;

public class SearchResultImpl implements SearchResult, Serializable {

    private final List elements = new ArrayList();
    private final Map index = new HashMap();
    private final List names = new ArrayList();
    private final int length;
    private int requiredStartIndex = -1;

    public SearchResultImpl(final List resultParams) {
        this.length = resultParams.size();
        buildIndex(resultParams);
    }

    public List getElements() {
        return this.elements;
    }

    public List getNames() {
        return this.names;
    }

    public SearchResultElement getElement(int index) {
        if (index < this.elements.size()) {
            return (SearchResultElement) this.elements.get(index);
        }
        return null;
    }

    public int getResultCount() {
        return this.elements.size();
    }

    public boolean isEmpty() {
        return this.elements.size() == 0;
    }

    public SearchResultElement createElement() {
        SearchResultElement element = new SearchResultElement(this.length, this.requiredStartIndex);

        this.elements.add(element);
        return element;
    }

    public boolean contains(final String name) {
        return (this.index.get(name) == null) ? false : true;
    }

    public String getName(final int index) {
        return (String) this.names.get(index);
    }

    public int getIndex(final String name) {
        Integer index = (Integer) this.index.get(name);
        return (index == null) ? -1 : index.intValue();
    }

    private void buildIndex(final List resultParams) {
        int i = 0;
        for (Iterator it = resultParams.iterator(); it.hasNext(); i++) {
            ResultParameter rp = (ResultParameter) it.next();
            this.index.put(rp.getName(), new Integer(this.index.size()));
            this.names.add(rp.getName());
            if (this.requiredStartIndex == -1 && rp.isRequired()) {
                this.requiredStartIndex = i;
            }
        }
    }

    public String toString() {
        return new StringBuffer("SearchResult[").append("elements=").append(this.elements).append("]").toString();
    }
}
