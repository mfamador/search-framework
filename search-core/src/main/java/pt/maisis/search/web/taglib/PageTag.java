package pt.maisis.search.web.taglib;

import pt.maisis.search.util.MessageResources;

import javax.servlet.jsp.JspException;

/**
 * Permite fazer o rendering das páginas resultantes de
 * uma dada pesquisa.<br/>
 *
 * Esta tag deve ser utilizada embebida na tag
 * {@link PageableSearchResultTag}.
 *
 * @version 1.0
 */
public class PageTag extends TagSupport {

    /**
     * Nome do atributo que é colocado no pageContext e que
     * identifica o número da página.
     */
    public static final String PAGE = "page";
    /**
     * Nome do atributo que identifica se o valor do atributo
     * identificado por {@link #PAGE} corresponde à página actual.
     */
    public static final String SELECTED = "selected";
    /** Parent tag. */
    private PageableSearchResultTag parent;
    private int page;

    public PageTag() {
    }

    public int doStartTag() throws JspException {
        this.parent = (PageableSearchResultTag) findAncestorWithClass(this, PageableSearchResultTag.class);

        if (this.parent == null) {
            throw new JspException(MessageResources.getInstance().getMessage("tag.invalid.parent"));
        }

        if (this.parent.getNumberPages() > 0) {
            this.page = this.parent.getStartPage();
            setAttributes();
            return EVAL_BODY_INCLUDE;
        }
        return SKIP_BODY;
    }

    public int doAfterBody() throws JspException {
        if ((this.page - this.parent.getStartPage())
                < this.parent.getNumberVisiblePages()
                && this.page <= this.parent.getNumberPages()) {
            setAttributes();
            return EVAL_BODY_AGAIN;
        }
        return SKIP_BODY;
    }

    public void setAttributes() {
        setAttribute(PAGE, this.page);

        setAttribute(SELECTED, this.parent.getCurrentPage() == this.page);

        int start = (this.page - 1) * this.parent.getCount();

        setAttribute(PageableSearchResultTag.URL, this.parent.getURL(start));

        setAttribute(PageableSearchResultTag.START, start);

        this.page++;
    }
}
