package com.suenara.exampleapp.presentation.view;

import com.suenara.exampleapp.presentation.model.CatModel;

import java.util.Collection;

public interface CatListView extends LoadDataView {

    void renderCatList(Collection<CatModel> catModelCollection);

    void viewCat(CatModel catModel);

}
