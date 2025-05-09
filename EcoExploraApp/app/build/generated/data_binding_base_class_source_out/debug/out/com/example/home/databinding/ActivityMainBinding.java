// Generated by view binder compiler. Do not edit!
package com.example.home.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.home.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityMainBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final ImageView imageView2;

  @NonNull
  public final Button iniciar;

  @NonNull
  public final FrameLayout layout1;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final ImageView rounded1;

  @NonNull
  public final ImageView rounded2;

  @NonNull
  public final ImageView rounded3;

  @NonNull
  public final TextView titulo;

  private ActivityMainBinding(@NonNull ConstraintLayout rootView, @NonNull ImageView imageView2,
      @NonNull Button iniciar, @NonNull FrameLayout layout1, @NonNull ConstraintLayout main,
      @NonNull ImageView rounded1, @NonNull ImageView rounded2, @NonNull ImageView rounded3,
      @NonNull TextView titulo) {
    this.rootView = rootView;
    this.imageView2 = imageView2;
    this.iniciar = iniciar;
    this.layout1 = layout1;
    this.main = main;
    this.rounded1 = rounded1;
    this.rounded2 = rounded2;
    this.rounded3 = rounded3;
    this.titulo = titulo;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityMainBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_main, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityMainBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.imageView2;
      ImageView imageView2 = ViewBindings.findChildViewById(rootView, id);
      if (imageView2 == null) {
        break missingId;
      }

      id = R.id.iniciar;
      Button iniciar = ViewBindings.findChildViewById(rootView, id);
      if (iniciar == null) {
        break missingId;
      }

      id = R.id.layout1;
      FrameLayout layout1 = ViewBindings.findChildViewById(rootView, id);
      if (layout1 == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.rounded1;
      ImageView rounded1 = ViewBindings.findChildViewById(rootView, id);
      if (rounded1 == null) {
        break missingId;
      }

      id = R.id.rounded2;
      ImageView rounded2 = ViewBindings.findChildViewById(rootView, id);
      if (rounded2 == null) {
        break missingId;
      }

      id = R.id.rounded3;
      ImageView rounded3 = ViewBindings.findChildViewById(rootView, id);
      if (rounded3 == null) {
        break missingId;
      }

      id = R.id.titulo;
      TextView titulo = ViewBindings.findChildViewById(rootView, id);
      if (titulo == null) {
        break missingId;
      }

      return new ActivityMainBinding((ConstraintLayout) rootView, imageView2, iniciar, layout1,
          main, rounded1, rounded2, rounded3, titulo);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
