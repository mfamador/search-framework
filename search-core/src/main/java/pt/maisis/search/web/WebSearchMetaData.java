package pt.maisis.search.web;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;

/**
 * Classe cuja instância contém a MetaData relativa às 
 * pesquisas web configuradas.
 *
 * @version 1.0
 *
 * @see pt.maisis.search.web.WebSearch
 */
/** Índice das pesquisas. */
public class WebSearchMetaData {

    /** Lista das pesquisas. */
    private final List searches = new ArrayList();
    /** Índice das pesquisas. */
    private final Map searchesIndex = new HashMap();
    /** Índice dos exporters. */
    private final Map exportersIndex = new HashMap();

    public WebSearchMetaData() {
    }

    public void addWebExporter(final WebExporter exporter) {
        this.exportersIndex.put(exporter.getName(), exporter);
    }

    public void addWebSearch(final WebSearch search) {
        this.searchesIndex.put(search.getName(), search);
        this.searches.add(search);
    }

    public WebSearch getSearch(final String name) {
        return (WebSearch) this.searchesIndex.get(name);
    }

    public WebExporter getExporter(final String name) {
        return (WebExporter) this.exportersIndex.get(name);
    }

    public boolean isExporter(final String name) {
        return this.exportersIndex.get(name) != null;
    }

    public List getSearches() {
        return this.searches;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        StringBuffer sb = new StringBuffer("WebSearchMetaData").append(this.searches.toString());
        return sb.toString();
    }
}
