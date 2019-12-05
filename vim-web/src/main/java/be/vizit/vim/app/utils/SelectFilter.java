package be.vizit.vim.app.utils;

import java.util.List;

public abstract class SelectFilter<T> {

  List<T> objectList;
  String parameterName;

  public SelectFilter(String parameterName, List<T> objectList) {
    this.parameterName = parameterName;
    this.objectList = objectList;
  }

  public String getParameterName() {
    return parameterName;
  }

  public List<T> getObjectList() {
    return objectList;
  }

  public abstract String getValue(T object);

  public String getText(T object) {
    return object != null ? object.toString() : null;
  }
}
