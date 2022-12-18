<?xml version="1.0" encoding="utf-8"?>
<androidx.constraintlayout.widget.ConstraintLayout xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:layout_gravity="center_horizontal"
    android:orientation="vertical" android:layout_width="3@dimen/icon_size_normal"
    android:layout_height="wrap_content">

    <TextView
        android:id="@+id/TitleDialogList"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginTop="44dp"
        android:fontFamily="sans-serif-black"
        android:text="Ajouter une liste"
        android:textSize="22sp"
        android:textStyle="bold"
        android:textColor="@color/colorPrimary"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toTopOf="parent" />


    <EditText
        android:id="@+id/ListNameDialogList"
        android:layout_width="match_parent"
        android:layout_height="wrap_content"
        android:layout_gravity="start"
        android:layout_marginTop="@dimen/margin_vertical_large"
        android:hint="Nom de la liste"
        android:maxLines="1"
        android:layout_marginStart="@dimen/margin_horizontal_small"
        android:layout_marginRight="16dp"
        android:layout_marginEnd="16dp"
        android:layout_marginLeft="16dp"
        android:textAlignment="textStart"
        android:textSize="18sp"
        app:layout_constraintEnd_toEndOf="parent"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/TitleDialogList" />

    <TextView
        android:id="@+id/VisibiliteTextDialogList"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_marginStart="@dimen/margin_horizontal_small"
        android:layout_marginTop="56dp"
        android:text="Visibilité :"
        android:textSize="18sp"
        android:textStyle="italic"
        app:layout_constraintStart_toStartOf="parent"
        app:layout_constraintTop_toBottomOf="@+id/ListNameDialogList" />

    <RadioGroup
        android:id="@+id/VisibilityRadioButtonDialogList"
