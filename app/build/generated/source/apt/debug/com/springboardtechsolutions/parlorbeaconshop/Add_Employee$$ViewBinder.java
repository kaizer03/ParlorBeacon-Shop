// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Add_Employee$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Add_Employee> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493007, "field 'nametext'");
    target.nametext = finder.castView(view, 2131493007, "field 'nametext'");
    view = finder.findRequiredView(source, 2131493008, "field 'emailtext'");
    target.emailtext = finder.castView(view, 2131493008, "field 'emailtext'");
    view = finder.findRequiredView(source, 2131493009, "field 'designationtext'");
    target.designationtext = finder.castView(view, 2131493009, "field 'designationtext'");
    view = finder.findRequiredView(source, 2131493010, "method 'onClick'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.nametext = null;
    target.emailtext = null;
    target.designationtext = null;
  }
}
