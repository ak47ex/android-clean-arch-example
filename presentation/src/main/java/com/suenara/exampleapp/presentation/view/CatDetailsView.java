package com.suenara.exampleapp.presentation.view;

import com.suenara.exampleapp.presentation.model.CatModel;

public interface CatDetailsView extends LoadDataView {

    void renderCat(CatModel cat);

}
