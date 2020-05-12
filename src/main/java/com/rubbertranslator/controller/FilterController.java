package com.rubbertranslator.controller;

import com.rubbertranslator.modules.system.SystemConfiguration;
import com.rubbertranslator.modules.system.SystemResourceManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.stage.FileChooser;

import java.io.File;
import java.util.List;

/**
 * @author Raven
 * @version 1.0
 * @date 2020/5/12 21:12
 */
public class FilterController {
    @FXML
    private VBox vBox;
    @FXML   // processList
    private ListView<String> processList;
    @FXML
    private Button addBt;
    @FXML
    private Button removeBt;

    // 文件选择器
    private FileChooser fileChooser = new FileChooser();
    {
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Windows进程", "*.exe")
        );
    }


    @FXML
    public void initialize() {
//        ((Stage)(vBox.getScene().getWindow())
        // 开启多选模式
        processList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Label label = new Label("当前过滤列表为空，请添加要过滤的进程名");
        label.setFont(Font.font(16));
        label.setWrapText(true);
        label.paddingProperty().setValue(new Insets(10));
        processList.setPlaceholder(label);
        // 回显
        SystemConfiguration configuration = SystemResourceManager.getConfigurationProxy();
        processList.getItems().addAll(configuration.getProcessFilterConfig().getProcessList());
    }

    @FXML
    public void onAddButtonClick(){
        List<File> selectedFiles = fileChooser.showOpenMultipleDialog(vBox.getScene().getWindow());
        if(selectedFiles != null){
            for (File f : selectedFiles){
                processList.getItems().add(f.getName());
            }
            SystemResourceManager.getConfigurationProxy().getProcessFilterConfig().setProcessList(
                    processList.getItems()
            );
        }
    }

    @FXML
    public void onRemoveButtonClick(){
        ObservableList<String> selectedItems =
                processList.getSelectionModel().getSelectedItems();
        if(selectedItems != null){
            processList.getItems().removeAll(selectedItems);
            // 更新设置
            SystemResourceManager.getConfigurationProxy().getProcessFilterConfig().setProcessList(
                    processList.getItems()
            );
        }
    }

}
