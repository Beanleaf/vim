package be.beanleaf.vim.app.dto;

import java.util.List;

public class ChartDto {

  private String title;
  private String type;
  private List<ChartPointDto> points;

  public ChartDto(String title, List<ChartPointDto> points) {
    this(title, null, points);
  }

  public ChartDto(String title, String type, List<ChartPointDto> points) {
    this.title = title;
    this.type = type;
    this.points = points;
  }

  public String getTitle() {
    return title;
  }

  public List<ChartPointDto> getPoints() {
    return points;
  }

  public String getType() {
    return type;
  }

  public static class ChartPointDto {

    private Integer X;
    private Integer Y;
    private String label;
    private String indexLabel;

    public ChartPointDto(Integer x, Integer y, String label, String indexLabel) {
      this.X = x;
      this.Y = y;
      this.label = label;
      this.indexLabel = indexLabel;
    }

    public ChartPointDto(Integer x, Integer y) {
      this(x, y, null, null);
    }

    public ChartPointDto(String label, Integer y) {
      this(null, y, label, null);
    }

    public ChartPointDto(String label, Integer y, String indexLabel) {
      this(null, y, label, indexLabel);
    }

    public Integer getX() {
      return X;
    }

    public void setX(Integer x) {
      X = x;
    }

    public Integer getY() {
      return Y;
    }

    public void setY(Integer y) {
      Y = y;
    }

    public String getLabel() {
      return label;
    }

    public void setLabel(String label) {
      this.label = label;
    }

    public String getIndexLabel() {
      return indexLabel;
    }

    public void setIndexLabel(String indexLabel) {
      this.indexLabel = indexLabel;
    }
  }
}