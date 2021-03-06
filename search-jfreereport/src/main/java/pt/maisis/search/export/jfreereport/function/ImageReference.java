package pt.maisis.search.export.jfreereport.function;

import org.jfree.chart.JFreeChart;
import java.io.File;
import java.io.IOException;

import org.jfree.chart.ChartUtilities;
import org.jfree.report.URLImageContainer;
import java.net.URL;

/**
 * Simples implementacao de <code>URLImageContainer</code> que
 * e' utilizada em ambiente web. Isto e', quando e' feita a
 * exportacao do relatorio para HTML.
 */
class ImageReference implements URLImageContainer {
    private AbstractChartFunction chart;
    private String name;

    public ImageReference(final AbstractChartFunction chart) {
        this.chart = chart;
    }

    public String getSourceURLString() {
        return "image?name=" + this.name;
    }

    public URL getSourceURL() {
        // HACK!!
        // Este metodo so e' chamado uma vez.
        // Como retorna null o metodo getSourceURLString() e
        // chamado a seguir (mais do que uma vez).
        // Aproveitar para criar a imagem.
        // TODO: alterar isto!!!!!!
        File file = createImage();
        this.name = file.getName();

        return null;
    }

    public boolean isLoadable() {
        return false;
    }

    public int getImageHeight() {
        return this.chart.getHeight();
    }

    public int getImageWidth() {
        return this.chart.getWidth();
    }

    public float getScaleX() {
        return 1;
    }

    public float getScaleY() {
        return 1;
    }

    protected File createImage() {
        try {
            JFreeChart jfreechart = this.chart.createChart();
            this.chart.setCustomProperties(jfreechart);

            File tmpdir = new File(System.getProperty("java.io.tmpdir"));
            File file = File.createTempFile("chart_", ".png", tmpdir);
            file.deleteOnExit();

            ChartUtilities.saveChartAsPNG
                (file, jfreechart, getImageWidth(), getImageHeight());

            return file;

        } catch (IOException e) {
            return null;
        }
    }

}
