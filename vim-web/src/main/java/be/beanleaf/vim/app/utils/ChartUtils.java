package be.beanleaf.vim.app.utils;

import be.beanleaf.vim.app.dto.ChartDto;
import be.beanleaf.vim.app.dto.ChartPointDto;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.CollectionUtils;

public abstract class ChartUtils {

  public static List<String> getLabels(ChartDto chartDto) {
    List<ChartPointDto> points = chartDto.getPoints();
    if (CollectionUtils.isEmpty(points)) {
      return null;
    }
    List<String> labels = new ArrayList<>();
    for (ChartPointDto point : points) {
      labels.add(point.getLabel());
    }
    return labels;
  }

  public static List<Long> getData(ChartDto chartDto) {
    List<ChartPointDto> points = chartDto.getPoints();
    if (CollectionUtils.isEmpty(points)) {
      return null;
    }
    List<Long> data = new ArrayList<>();
    for (ChartPointDto point : points) {
      data.add(point.getValue());
    }
    return data;
  }

  public static List<String> getBackgroundColors(ChartDto chartDto) {
    List<ChartPointDto> points = chartDto.getPoints();
    if (CollectionUtils.isEmpty(points)) {
      return null;
    }
    List<String> colors = new ArrayList<>();
    for (ChartPointDto point : points) {
      String backgroundColor = point.getBackgroundColor();
      if (!StringUtils.isEmpty(backgroundColor)) {
        colors.add(backgroundColor);
      }
    }
    return CollectionUtils.isEmpty(colors) ? null : colors;
  }

  public static List<String> getBorderColors(ChartDto chartDto) {
    List<ChartPointDto> points = chartDto.getPoints();
    if (CollectionUtils.isEmpty(points)) {
      return null;
    }
    List<String> colors = new ArrayList<>();
    for (ChartPointDto point : points) {
      String borderColor = point.getBorderColor();
      if (!StringUtils.isEmpty(borderColor)) {
        colors.add(borderColor);
      }
    }
    return CollectionUtils.isEmpty(colors) ? null : colors;
  }
}
