// Generated code from Butter Knife. Do not modify!
package com.springboardtechsolutions.parlorbeaconshop;

import android.view.View;
import butterknife.ButterKnife.Finder;
import butterknife.ButterKnife.ViewBinder;

public class Edit_Detail_Shop$$ViewBinder<T extends com.springboardtechsolutions.parlorbeaconshop.Edit_Detail_Shop> implements ViewBinder<T> {
  @Override public void bind(final Finder finder, final T target, Object source) {
    View view;
    view = finder.findRequiredView(source, 2131493018, "field 'shopkeepernametext'");
    target.shopkeepernametext = finder.castView(view, 2131493018, "field 'shopkeepernametext'");
    view = finder.findRequiredView(source, 2131493019, "field 'shopnametext'");
    target.shopnametext = finder.castView(view, 2131493019, "field 'shopnametext'");
    view = finder.findRequiredView(source, 2131493020, "field 'emailtext'");
    target.emailtext = finder.castView(view, 2131493020, "field 'emailtext'");
    view = finder.findRequiredView(source, 2131493021, "field 'shopphonetext'");
    target.shopphonetext = finder.castView(view, 2131493021, "field 'shopphonetext'");
    view = finder.findRequiredView(source, 2131493022, "field 'shopaddrtext'");
    target.shopaddrtext = finder.castView(view, 2131493022, "field 'shopaddrtext'");
    view = finder.findRequiredView(source, 2131493023, "field 'shopcitytext'");
    target.shopcitytext = finder.castView(view, 2131493023, "field 'shopcitytext'");
    view = finder.findRequiredView(source, 2131493024, "field 'shopziptext'");
    target.shopziptext = finder.castView(view, 2131493024, "field 'shopziptext'");
    view = finder.findRequiredView(source, 2131493025, "field 'shopopentext' and method 'onClick1'");
    target.shopopentext = finder.castView(view, 2131493025, "field 'shopopentext'");
    view.setOnClickListener(
      new butterknife.internal.DebouncingOnClickListener() {
        @Override public void doClick(
          android.view.View p0
        ) {
          target.onClick1(p0);
        }
      });
    view = finder.findRequiredView(source, 2131493026, "field 'shopclosetext' and method 'onClick2'");
    target.shopclosetext = finder.castView(view, 2131493026, "field 'shopclosetext'");
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
