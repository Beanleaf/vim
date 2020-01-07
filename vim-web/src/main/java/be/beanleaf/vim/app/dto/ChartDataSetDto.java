package be.beanleaf.vim.app.dto;

import java.util.ArrayList;
import java.util.List;

public class ChartDataSetDto {

  private String label;
  private List<Long> data = new ArrayList<>();
  private List<String> backgroundColor = new ArrayList<>();
  private List<String> borderColor = new ArrayList<>();
  private int borderWidth = 1;

  public ChartDataSetDto(String label) {
    setLabel(label);
  }

  public void addData(Long value, String bgColor, String borderColor) {
    this.data.add(value);
    this.backgroundColor.add(bgColor);
    this.borderColor.add(borderColor);
  }

  public String getLabel() {
    return label;
  }

  public void setLabel(String label) {
    this.label = label;
  }

  public List<Long> getData() {
    return data;
  }

  public void setData(List<Long> data) {
    this.data = data;
  }

  public List<String> getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(List<String> backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public List<String> getBorderColor() {
    return borderColor;
  }

  public void setBorderColor(List<String> borderColor) {
    this.borderColor = borderColor;
  }

  public int getBorderWidth() {
    return borderWidth;
  }

  public void setBorderWidth(int borderWidth) {
    this.borderWidth = borderWidth;
  }
}
