// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Booking_Today_List$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Booking_Today_List> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493036, "field 'CustName'");
    target.CustName = finder.castView(view, 2131493036, "field 'CustName'");
    view = finder.findRequiredView(source, 2131493037, "field 'CustEmail'");
    target.CustEmail = finder.castView(view, 2131493037, "field 'CustEmail'");
    view = finder.findRequiredView(source, 2131493038, "field 'CustService'");
    target.CustService = finder.castView(view, 2131493038, "field 'CustService'");
    view = finder.findRequiredView(source, 2131493039, "field 'CustServTime'");
    target.CustServTime = finder.castView(view, 2131493039, "field 'CustServTime'");
    view = finder.findRequiredView(source, 2131493042, "field 'relativeLayout'");
    target.relativeLayout = finder.castView(view, 2131493042, "field 'relativeLayout'");
    view = finder.findRequiredView(source, 2131493040, "field 'CustServStatus'");
    target.CustServStatus = finder.castView(view, 2131493040, "field 'CustServStatus'");
  }

  @Override public void unbind(T target) {
    target.CustName = null;
    target.CustEmail = null;
    target.CustService = null;
    target.CustServTime = null;
    target.relativeLayout = null;
    target.CustServStatus = null;
  }
}
