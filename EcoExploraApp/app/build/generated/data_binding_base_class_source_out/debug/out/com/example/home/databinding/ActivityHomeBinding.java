// Generated by view binder compiler. Do not edit!
package com.example.home.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.home.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityHomeBinding implements ViewBinding {
  @NonNull
  private final ConstraintLayout rootView;

  @NonNull
  public final CardView carPeixes;

  @NonNull
  public final CardView cardAnfibios;

  @NonNull
  public final CardView cardAves;

  @NonNull
  public final CardView cardDevLogin;

  @NonNull
  public final CardView cardMamiferos;

  @NonNull
  public final CardView cardPlantas;

  @NonNull
  public final CardView cardRepteis;

  @NonNull
  public final CardView cardTop;

  @NonNull
  public final TextView cardTopSeta;

  @NonNull
  public final ImageView fundoBottom1;

  @NonNull
  public final ImageButton fundoBottom2;

  @NonNull
  public final ImageView fundoTop1;

  @NonNull
  public final ImageButton fundoTop2;

  @NonNull
  public final ImageButton imageAnfibios;

  @NonNull
  public final ImageButton imageAves;

  @NonNull
  public final ImageButton imageMamiferos;

  @NonNull
  public final ImageButton imagePeixes;

  @NonNull
  public final ImageButton imagePlantas;

  @NonNull
  public final ImageButton imageRepteis;

  @NonNull
  public final TextView loginBtn;

  @NonNull
  public final ConstraintLayout main;

  @NonNull
  public final ImageButton profileButton;

  @NonNull
  public final CardView profileTest;

  @NonNull
  public final TextView subtitulo;

  @NonNull
  public final TextView textAnfibios;

  @NonNull
  public final TextView textAves;

  @NonNull
  public final TextView textBottom;

  @NonNull
  public final TextView textCardTop;

  @NonNull
  public final TextView textMamiferos;

  @NonNull
  public final TextView textPeixes;

  @NonNull
  public final TextView textPlantas;

  @NonNull
  public final TextView textRepteis;

  @NonNull
  public final TextView textSetaBottom;

  @NonNull
  public final TextView titulo;

  @NonNull
  public final TextView userNameText;

  private ActivityHomeBinding(@NonNull ConstraintLayout rootView, @NonNull CardView carPeixes,
      @NonNull CardView cardAnfibios, @NonNull CardView cardAves, @NonNull CardView cardDevLogin,
      @NonNull CardView cardMamiferos, @NonNull CardView cardPlantas, @NonNull CardView cardRepteis,
      @NonNull CardView cardTop, @NonNull TextView cardTopSeta, @NonNull ImageView fundoBottom1,
      @NonNull ImageButton fundoBottom2, @NonNull ImageView fundoTop1,
      @NonNull ImageButton fundoTop2, @NonNull ImageButton imageAnfibios,
      @NonNull ImageButton imageAves, @NonNull ImageButton imageMamiferos,
      @NonNull ImageButton imagePeixes, @NonNull ImageButton imagePlantas,
      @NonNull ImageButton imageRepteis, @NonNull TextView loginBtn, @NonNull ConstraintLayout main,
      @NonNull ImageButton profileButton, @NonNull CardView profileTest,
      @NonNull TextView subtitulo, @NonNull TextView textAnfibios, @NonNull TextView textAves,
      @NonNull TextView textBottom, @NonNull TextView textCardTop, @NonNull TextView textMamiferos,
      @NonNull TextView textPeixes, @NonNull TextView textPlantas, @NonNull TextView textRepteis,
      @NonNull TextView textSetaBottom, @NonNull TextView titulo, @NonNull TextView userNameText) {
    this.rootView = rootView;
    this.carPeixes = carPeixes;
    this.cardAnfibios = cardAnfibios;
    this.cardAves = cardAves;
    this.cardDevLogin = cardDevLogin;
    this.cardMamiferos = cardMamiferos;
    this.cardPlantas = cardPlantas;
    this.cardRepteis = cardRepteis;
    this.cardTop = cardTop;
    this.cardTopSeta = cardTopSeta;
    this.fundoBottom1 = fundoBottom1;
    this.fundoBottom2 = fundoBottom2;
    this.fundoTop1 = fundoTop1;
    this.fundoTop2 = fundoTop2;
    this.imageAnfibios = imageAnfibios;
    this.imageAves = imageAves;
    this.imageMamiferos = imageMamiferos;
    this.imagePeixes = imagePeixes;
    this.imagePlantas = imagePlantas;
    this.imageRepteis = imageRepteis;
    this.loginBtn = loginBtn;
    this.main = main;
    this.profileButton = profileButton;
    this.profileTest = profileTest;
    this.subtitulo = subtitulo;
    this.textAnfibios = textAnfibios;
    this.textAves = textAves;
    this.textBottom = textBottom;
    this.textCardTop = textCardTop;
    this.textMamiferos = textMamiferos;
    this.textPeixes = textPeixes;
    this.textPlantas = textPlantas;
    this.textRepteis = textRepteis;
    this.textSetaBottom = textSetaBottom;
    this.titulo = titulo;
    this.userNameText = userNameText;
  }

  @Override
  @NonNull
  public ConstraintLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityHomeBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_home, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityHomeBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.carPeixes;
      CardView carPeixes = ViewBindings.findChildViewById(rootView, id);
      if (carPeixes == null) {
        break missingId;
      }

      id = R.id.cardAnfibios;
      CardView cardAnfibios = ViewBindings.findChildViewById(rootView, id);
      if (cardAnfibios == null) {
        break missingId;
      }

      id = R.id.cardAves;
      CardView cardAves = ViewBindings.findChildViewById(rootView, id);
      if (cardAves == null) {
        break missingId;
      }

      id = R.id.cardDevLogin;
      CardView cardDevLogin = ViewBindings.findChildViewById(rootView, id);
      if (cardDevLogin == null) {
        break missingId;
      }

      id = R.id.cardMamiferos;
      CardView cardMamiferos = ViewBindings.findChildViewById(rootView, id);
      if (cardMamiferos == null) {
        break missingId;
      }

      id = R.id.cardPlantas;
      CardView cardPlantas = ViewBindings.findChildViewById(rootView, id);
      if (cardPlantas == null) {
        break missingId;
      }

      id = R.id.cardRepteis;
      CardView cardRepteis = ViewBindings.findChildViewById(rootView, id);
      if (cardRepteis == null) {
        break missingId;
      }

      id = R.id.cardTop;
      CardView cardTop = ViewBindings.findChildViewById(rootView, id);
      if (cardTop == null) {
        break missingId;
      }

      id = R.id.cardTopSeta;
      TextView cardTopSeta = ViewBindings.findChildViewById(rootView, id);
      if (cardTopSeta == null) {
        break missingId;
      }

      id = R.id.fundoBottom1;
      ImageView fundoBottom1 = ViewBindings.findChildViewById(rootView, id);
      if (fundoBottom1 == null) {
        break missingId;
      }

      id = R.id.fundoBottom2;
      ImageButton fundoBottom2 = ViewBindings.findChildViewById(rootView, id);
      if (fundoBottom2 == null) {
        break missingId;
      }

      id = R.id.fundoTop1;
      ImageView fundoTop1 = ViewBindings.findChildViewById(rootView, id);
      if (fundoTop1 == null) {
        break missingId;
      }

      id = R.id.fundoTop2;
      ImageButton fundoTop2 = ViewBindings.findChildViewById(rootView, id);
      if (fundoTop2 == null) {
        break missingId;
      }

      id = R.id.imageAnfibios;
      ImageButton imageAnfibios = ViewBindings.findChildViewById(rootView, id);
      if (imageAnfibios == null) {
        break missingId;
      }

      id = R.id.imageAves;
      ImageButton imageAves = ViewBindings.findChildViewById(rootView, id);
      if (imageAves == null) {
        break missingId;
      }

      id = R.id.imageMamiferos;
      ImageButton imageMamiferos = ViewBindings.findChildViewById(rootView, id);
      if (imageMamiferos == null) {
        break missingId;
      }

      id = R.id.imagePeixes;
      ImageButton imagePeixes = ViewBindings.findChildViewById(rootView, id);
      if (imagePeixes == null) {
        break missingId;
      }

      id = R.id.imagePlantas;
      ImageButton imagePlantas = ViewBindings.findChildViewById(rootView, id);
      if (imagePlantas == null) {
        break missingId;
      }

      id = R.id.imageRepteis;
      ImageButton imageRepteis = ViewBindings.findChildViewById(rootView, id);
      if (imageRepteis == null) {
        break missingId;
      }

      id = R.id.loginBtn;
      TextView loginBtn = ViewBindings.findChildViewById(rootView, id);
      if (loginBtn == null) {
        break missingId;
      }

      ConstraintLayout main = (ConstraintLayout) rootView;

      id = R.id.profileButton;
      ImageButton profileButton = ViewBindings.findChildViewById(rootView, id);
      if (profileButton == null) {
        break missingId;
      }

      id = R.id.profileTest;
      CardView profileTest = ViewBindings.findChildViewById(rootView, id);
      if (profileTest == null) {
        break missingId;
      }

      id = R.id.subtitulo;
      TextView subtitulo = ViewBindings.findChildViewById(rootView, id);
      if (subtitulo == null) {
        break missingId;
      }

      id = R.id.textAnfibios;
      TextView textAnfibios = ViewBindings.findChildViewById(rootView, id);
      if (textAnfibios == null) {
        break missingId;
      }

      id = R.id.textAves;
      TextView textAves = ViewBindings.findChildViewById(rootView, id);
      if (textAves == null) {
        break missingId;
      }

      id = R.id.textBottom;
      TextView textBottom = ViewBindings.findChildViewById(rootView, id);
      if (textBottom == null) {
        break missingId;
      }

      id = R.id.textCardTop;
      TextView textCardTop = ViewBindings.findChildViewById(rootView, id);
      if (textCardTop == null) {
        break missingId;
      }

      id = R.id.textMamiferos;
      TextView textMamiferos = ViewBindings.findChildViewById(rootView, id);
      if (textMamiferos == null) {
        break missingId;
      }

      id = R.id.textPeixes;
      TextView textPeixes = ViewBindings.findChildViewById(rootView, id);
      if (textPeixes == null) {
        break missingId;
      }

      id = R.id.textPlantas;
      TextView textPlantas = ViewBindings.findChildViewById(rootView, id);
      if (textPlantas == null) {
        break missingId;
      }

      id = R.id.textRepteis;
      TextView textRepteis = ViewBindings.findChildViewById(rootView, id);
      if (textRepteis == null) {
        break missingId;
      }

      id = R.id.textSetaBottom;
      TextView textSetaBottom = ViewBindings.findChildViewById(rootView, id);
      if (textSetaBottom == null) {
        break missingId;
      }

      id = R.id.titulo;
      TextView titulo = ViewBindings.findChildViewById(rootView, id);
      if (titulo == null) {
        break missingId;
      }

      id = R.id.userNameText;
      TextView userNameText = ViewBindings.findChildViewById(rootView, id);
      if (userNameText == null) {
        break missingId;
      }

      return new ActivityHomeBinding((ConstraintLayout) rootView, carPeixes, cardAnfibios, cardAves,
          cardDevLogin, cardMamiferos, cardPlantas, cardRepteis, cardTop, cardTopSeta, fundoBottom1,
          fundoBottom2, fundoTop1, fundoTop2, imageAnfibios, imageAves, imageMamiferos, imagePeixes,
          imagePlantas, imageRepteis, loginBtn, main, profileButton, profileTest, subtitulo,
          textAnfibios, textAves, textBottom, textCardTop, textMamiferos, textPeixes, textPlantas,
          textRepteis, textSetaBottom, titulo, userNameText);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
