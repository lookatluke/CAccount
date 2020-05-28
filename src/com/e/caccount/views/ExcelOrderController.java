/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.e.caccount.views;

import com.e.caccount.CAccounting;
import com.e.caccount.Model.Person;
import java.net.URL;
import javafx.fxml.FXML;
import java.util.ResourceBundle;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

/**
 * FXML Controller class
 *
 * @author trito
 */
public class ExcelOrderController implements Initializable {

    private Person person = Person.getInstance();

    @FXML
    private ListView<String> typeList;
    @FXML
    private Button ok;
    @FXML
    private Button cancel;
    @FXML
    private Button up;
    @FXML
    private Button down;

    // Empty Constructor
    public ExcelOrderController() {
    }

    ////////////////////////////////////////////////////////////////////////////
    //////////////////               SET MAIN             //////////////////////
    ////////////////////////////////////////////////////////////////////////////
    private CAccounting Main;

    public void setMain(CAccounting main) {
        this.Main = main;
    }

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        typeList.setItems(person.getTypeList());
    }

    private void reorderList(int index1, int index2, String type1, String type2) {
        person.getTypeList().remove(index1);
        person.getTypeList().add(index1, type2);
        person.getTypeList().remove(index2);
        person.getTypeList().add(index2, type1);
        // Clear Focus
        typeList.getSelectionModel().clearSelection();
    }

    public void up() {
        if (typeList.getSelectionModel().getSelectedItem() != null) {
            String SelectedType = typeList.getSelectionModel().getSelectedItem();
            int index = person.getTypeList().indexOf(SelectedType);

            if ((index - 1) >= 0) {
                String aboveType = person.getTypeList().get(index - 1);
                reorderList(index, index - 1, SelectedType, aboveType);
            }
        }
    }

    public void down() {
        if (typeList.getSelectionModel().getSelectedItem() != null) {
            String SelectedType = typeList.getSelectionModel().getSelectedItem();
            int index = person.getTypeList().indexOf(SelectedType);

            try {
                String aboveType = person.getTypeList().get(index + 1);
                reorderList(index, index + 1, SelectedType, aboveType);
            } catch (IndexOutOfBoundsException iobe) {
                // Do nothing
            }
        }
    }

    public void ok() {
        // Reorder PageView
        person.ReorderAllDATA();
        // Close Stage
        Main.getReorderStage().hide();
    }
}
