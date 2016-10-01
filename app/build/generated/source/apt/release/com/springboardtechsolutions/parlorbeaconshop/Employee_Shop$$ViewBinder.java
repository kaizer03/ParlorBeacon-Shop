// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Employee_Shop$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Employee_Shop> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493102, "field 'employeelist'");
    target.employeelist = finder.castView(view, 2131493102, "field 'employeelist'");
  }

  @Override public void unbind(T target) {
    target.employeelist = null;
  }
}
