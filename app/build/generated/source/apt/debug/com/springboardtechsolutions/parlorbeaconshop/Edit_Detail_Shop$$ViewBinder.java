// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Edit_Detail_Shop$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Edit_Detail_Shop> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131492987, "field 'shopkeepernametext'");
    target.shopkeepernametext = finder.castView(view, 2131492987, "field 'shopkeepernametext'");
    view = finder.findRequiredView(source, 2131492988, "field 'shopnametext'");
    target.shopnametext = finder.castView(view, 2131492988, "field 'shopnametext'");
    view = finder.findRequiredView(source, 2131492989, "field 'emailtext'");
    target.emailtext = finder.castView(view, 2131492989, "field 'emailtext'");
    view = finder.findRequiredView(source, 2131492990, "field 'shopphonetext'");
    target.shopphonetext = finder.castView(view, 2131492990, "field 'shopphonetext'");
    view = finder.findRequiredView(source, 2131492991, "field 'shopaddrtext'");
    target.shopaddrtext = finder.castView(view, 2131492991, "field 'shopaddrtext'");
    view = finder.findRequiredView(source, 2131492992, "field 'shopcitytext'");
    target.shopcitytext = finder.castView(view, 2131492992, "field 'shopcitytext'");
    view = finder.findRequiredView(source, 2131492993, "field 'shopziptext'");
    target.shopziptext = finder.castView(view, 2131492993, "field 'shopziptext'");
    view = finder.findRequiredView(source, 2131492994, "field 'shopopentext' and method 'onClick1'");
    target.shopopentext = finder.castView(view, 2131492994, "field 'shopopentext'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick1(p0);
        }
      });
    view = finder.findRequiredView(source, 2131492995, "field 'shopclosetext' and method 'onClick2'");
    target.shopclosetext = finder.castView(view, 2131492995, "field 'shopclosetext'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick2(p0);
        }
      });
  }

  @Override public void unbind(T target) {
    target.shopkeepernametext = null;
    target.shopnametext = null;
    target.emailtext = null;
    target.shopphonetext = null;
    target.shopaddrtext = null;
    target.shopcitytext = null;
    target.shopziptext = null;
    target.shopopentext = null;
    target.shopclosetext = null;
  }
}
