// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class EmployeeDetailSet$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.EmployeeDetailSet> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493081, "field 'nametext'");
    target.nametext = finder.castView(view, 2131493081, "field 'nametext'");
    view = finder.findRequiredView(source, 2131493082, "field 'emailtext'");
    target.emailtext = finder.castView(view, 2131493082, "field 'emailtext'");
    view = finder.findRequiredView(source, 2131493083, "field 'designationtext'");
    target.designationtext = finder.castView(view, 2131493083, "field 'designationtext'");
  }

  @Override public void unbind(T target) {
    target.nametext = null;
    target.emailtext = null;
    target.designationtext = null;
  }
}
