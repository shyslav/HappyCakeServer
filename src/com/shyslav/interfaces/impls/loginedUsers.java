package com.shyslav.interfaces.impls;

import com.shyslav.interfaces.listJob;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Created by Shyshkin Vladyslav on 27.03.2016.
 */
public class loginedUsers implements listJob {
    private ObservableList<String> pesronsList = FXCollections.observableArrayList();

    @Override
    public void delete(int i) {
        pesronsList.remove(i);
    }

    @Override
    public void add(String s) {
        pesronsList.add(s);
    }

    @Override
    public void remove(int i) {

    }
}
