// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class LoginShop$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.LoginShop> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492990, "field 'emailtext'");
    target.emailtext = finder.castView(view, 2131492990, "field 'emailtext'");
    view = finder.findRequiredView(source, 2131492991, "field 'passtext'");
    target.passtext = finder.castView(view, 2131492991, "field 'passtext'");
    view = finder.findRequiredView(source, 2131492992, "method 'onClick1'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick1(p0);
        }
      });
    view = finder.findRequiredView(source, 2131492994, "method 'onClick2'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick2(p0);
        }
      });
    view = finder.findRequiredView(source, 2131492993, "method 'onClick3'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick3(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.emailtext = null;
    target.passtext = null;
  }
}
