package pt.maisis.search.web.taglib;

import pt.maisis.search.web.LocaleConfig;
import pt.maisis.search.ResultParameterMetaData;
import pt.maisis.search.format.Formatter;
import pt.maisis.search.util.MessageResources;

import java.util.Locale;

import javax.servlet.jsp.JspException;
import javax.servlet.http.HttpServletRequest;

/**
 * Simples tag que faz o set do resultado de uma pesquisa 
 * ({@link pt.maisis.search.SearchResult}) no contexto da página.
 *
 * @version 1.0
 */
public class SearchResultBodyColumnTag extends TagSupport {

    public static final String RESULT_PARAMETER = "resultParam";
    public static final String VALUE = "value";
    protected SearchResultBodyTag parent;
    protected int index;

    public SearchResultBodyColumnTag() {
    }

    public int doStartTag() throws JspException {

        this.parent = (SearchResultBodyTag) findAncestorWithClass(this, SearchResultBodyTag.class);

        if (this.parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        this.index = 0;

        if (this.parent.getSearchResultElement().length() > 0) {
            setParameters();
            this.index++;
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    public int doAfterBody() throws JspException {
        if (this.index < this.parent.getSearchResultElement().length()) {
            setParameters();
            this.index++;
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    protected void setParameters() {
        removeAttribute(VALUE);
        removeAttribute(RESULT_PARAMETER);

        setAttribute(VALUE, getValue(this.index));
        setAttribute(RESULT_PARAMETER, getResultMetaData(this.index));
    }

    protected ResultParameterMetaData getResultMetaData(int index) {
        return this.parent.getResult().getResultMetaData(this.index);
    }

    protected Object getValue(int index) {
        ResultParameterMetaData rpmd = getResultMetaData(this.index);
        Formatter formatter = rpmd.getFormatter();
        Object value = this.parent.getSearchResultElement().getValue(this.index);

        Locale locale = LocaleConfig.getLocale((HttpServletRequest) pageContext.getRequest());
        return formatter.format(value, locale);
    }

    protected Object getUnformattedValue(int index) {
        ResultParameterMetaData rpmd = getResultMetaData(this.index);
        return this.parent.getSearchResultElement().getValue(this.index);
    }
}
