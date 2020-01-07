package be.beanleaf.vim.app.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartDto {

  private List<ChartDataSetDto> datasets;
  private List<String> labels = new ArrayList<>();
  private boolean beginYAtZero;
  private String type;
  private boolean show0Values;
  private boolean legendDisplay;

  public ChartDto(String type, boolean beginYAtZero) {
    this.type = type;
    this.beginYAtZero = beginYAtZero;
    setShow0Values(true);
    setLegendDisplay(true);
  }

  public List<ChartDataSetDto> getDatasets() {
    return datasets;
  }

  public void setDatasets(List<ChartDataSetDto> datasets) {
    this.datasets = datasets;
  }

  public boolean isBeginYAtZero() {
    return beginYAtZero;
  }

  public void setBeginYAtZero(boolean beginYAtZero) {
    this.beginYAtZero = beginYAtZero;
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public boolean isShow0Values() {
    return show0Values;
  }

  public void setShow0Values(boolean show0Values) {
    this.show0Values = show0Values;
  }

  public boolean isLegendDisplay() {
    return legendDisplay;
  }

  public void setLegendDisplay(boolean legendDisplay) {
    this.legendDisplay = legendDisplay;
  }

  public List<String> getLabels() {
    return labels;
  }

  public void addLabel(String label) {
    this.labels.add(label);
  }
}