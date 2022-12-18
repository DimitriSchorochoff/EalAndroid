package com.example.eal.Class.Spell.Element;

import androidx.annotation.Nullable;

public abstract class Element {
    public abstract String getElementName();

    @Override
    public boolean equals(@Nullable Object obj) {
        if(obj == null) return false;
        return getClass() == obj.getClass();
    }
}

