package be.beanleaf.vim.app.dto;

import java.util.List;

public class ChartDto {

  private String title;
  private List<ChartPointDto> points;
  private boolean beginYAtZero;
  private String type;
  private boolean show0Values;
  private Integer borderWidth;
  private boolean legendDisplay;

  public ChartDto(String title, List<ChartPointDto> points, String type, boolean beginYAtZero) {
    this.title = title;
    this.points = points;
    this.type = type;
    this.beginYAtZero = beginYAtZero;
    setShow0Values(true);
    setBorderWidth(1);
    setLegendDisplay(true);
  }

  public ChartDto(String title, List<ChartPointDto> points) {
    this(title, points, "line", true);
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public List<ChartPointDto> getPoints() {
    return points;
  }

  public void setPoints(List<ChartPointDto> points) {
    this.points = points;
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

  public Integer getBorderWidth() {
    return borderWidth;
  }

  public void setBorderWidth(Integer borderWidth) {
    this.borderWidth = borderWidth;
  }

  public boolean isLegendDisplay() {
    return legendDisplay;
  }

  public void setLegendDisplay(boolean legendDisplay) {
    this.legendDisplay = legendDisplay;
  }
}