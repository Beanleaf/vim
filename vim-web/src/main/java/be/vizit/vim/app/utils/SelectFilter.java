package be.vizit.vim.app.utils;

import java.util.List;

public abstract class SelectFilter<T> {

  List<T> objectList;
  String parameterName;
  T selectedObject;

  public SelectFilter(String parameterName, List<T> objectList, T selectedObject) {
    this.parameterName = parameterName;
    this.objectList = objectList;
    this.selectedObject = selectedObject;
  }

  public String getParameterName() {
    return parameterName;
  }

  public List<T> getObjectList() {
    return objectList;
  }

  public T getSelectedObject() {
    return selectedObject;
  }

  public abstract String getValue(T object);

  public String getText(T object) {
    return object != null ? object.toString() : null;
  }
}
