package com.rkarp.flc.controller;

import com.rkarp.appcore.controller.LanguageController;
import com.rkarp.appcore.debug.DebugHelper;
import com.rkarp.appcore.debug.ExceptionHandler;
import com.rkarp.appcore.util.CommonUtil;
import com.rkarp.flc.settings.AppConfiguration;
import com.rkarp.flc.view.layout.MainLayout;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FilelistController {

    public static FilelistController flController;
    private int lenght[] = new int[MainLayout.fileColumn.length];
    private int dirlenght;
    public static boolean option[] = new boolean[MainLayout.fileColumn.length];
    public static int sortingIdx;
    public static int UNIT_IDX = 1;
    public static boolean canShowHeader = true;
    public static boolean canShowEmptyFolder = true;

    public String getList() {
        final int spacing = 4;
        final int diffColumn[] = new int [MainLayout.fileColumn.length];
        String separatorLine;

        StringBuilder sb = new StringBuilder();

        if(canShowHeader) {
            sb.append("FileList\n");
            sb.append("-----------------------------------------------\n");
            sb.append(LanguageController.getString("fcreation")).append(": ").append(CommonUtil.getDate("dd.MM.yyyy")).append(" ").append(LanguageController.getString("at")).append(" ").append(CommonUtil.getTime("HH:mm")).append("\n");
            sb.append(LanguageController.getString("fpath")).append(": ").append(MainLayout.pathTF.getText()).append("\n");
            sb.append("-----------------------------------------------\n\n");
        }

        //calculate column space
        for (int i = 0; i < MainLayout.fileColumn.length; i++) {
            if (option[i]) {
                sb.append(MainLayout.fileColumn[i].getText());
                switch (sortingIdx) {

                    //sorting is: none
                    case 0:
                        diffColumn[i] = lenght[i] - MainLayout.fileColumn[i].getText().length() + spacing;

                        /**
                         * check if column space difference is smaller than 3
                         * if this is the case, set a minimum space of 2
                         **/
                        if (diffColumn[i] < 3) { diffColumn[i] = 2; }
                        break;

                    //sorting is: folder
                    case 1:
                        if (i == 0) {
                            //check if filename is longer than directory name
                            if (lenght[i] > dirlenght) {
                                diffColumn[i] = lenght[i] - MainLayout.fileColumn[i].getText().length() + spacing; }
                            else { diffColumn[i] = dirlenght - MainLayout.fileColumn[i].getText().length() + spacing; }
                        }
                        else
                        {
                            diffColumn[i] = lenght[i] - MainLayout.fileColumn[i].getText().length() + spacing;
                        }
                        if (diffColumn[i] < 3) { diffColumn[i] = 2; }
                        break;
                }
                for (int e = 0; e < diffColumn[i]; e++) { sb.append(" "); }
            }
        }

        //contruct separator line string
        separatorLine = getSeparatorLine(diffColumn);
        sb.append("\n");

        switch (sortingIdx) {

            //sorting is 'none'
            case 0:
                //separator line
                sb.append(separatorLine);
                sb.append("\n");

                for (int pos = 0; pos < AppConfiguration.fileData.size(); pos++) {
                    if (option[0]) {
                        sb.append(AppConfiguration.fileData.get(pos).getFilename());
                        int diff = lenght[0] - AppConfiguration.fileData.get(pos).getFilename().length() + MainLayout.fileColumn[0].getText().length() - lenght[0] + diffColumn[0];
                        for (int i = 0; i < diff; i++) { sb.append(" "); }
                    }

                    if (option[1]) {
                        sb.append(AppConfiguration.fileData.get(pos).getFiletype());
                        int diff1 = lenght[1] - AppConfiguration.fileData.get(pos).getFiletype().length() + MainLayout.fileColumn[1].getText().length() - lenght[1] + diffColumn[1];
                        for (int i = 0; i < diff1; i++) {
                            sb.append(" ");
                        }
                    }

                    if (option[2]) {
                        sb.append(AppConfiguration.fileData.get(pos).getFilepath());
                        int diff2 = lenght[2] - AppConfiguration.fileData.get(pos).getFilepath().length() + MainLayout.fileColumn[2].getText().length() - lenght[2] + diffColumn[2];
                        for (int i = 0; i < diff2; i++) { sb.append(" "); }
                    }

                    if (option[3]) {
                        sb.append(MainLayout.fileTable.getColumns().get(3).getCellData(pos));
                        int diff3 = lenght[3] - String.valueOf(MainLayout.fileTable.getColumns().get(3).getCellData(pos)).length() + MainLayout.fileColumn[3].getText().length() - lenght[3] + diffColumn[3];
                        for (int i = 0; i < diff3; i++) { sb.append(" "); }
                    }

                    if (option[4]) {
                        sb.append(AppConfiguration.fileData.get(pos).getCreationDate());
                        int diff4 = lenght[4] - AppConfiguration.fileData.get(pos).getCreationDate().length() + MainLayout.fileColumn[4].getText().length() - lenght[4] + diffColumn[4];
                        for (int i = 0; i < diff4; i++) { sb.append(" "); }
                    }

                    if (option[5]) {
                        sb.append(AppConfiguration.fileData.get(pos).getLastModif());
                        int diff5 = lenght[5] - AppConfiguration.fileData.get(pos).getLastModif().length() + MainLayout.fileColumn[5].getText().length() - lenght[5] + diffColumn[5];
                        for (int i = 0; i < diff5; i++) { sb.append(" "); }
                    }

                    if (option[6]) {
                        sb.append(AppConfiguration.fileData.get(pos).getLastAccessDate());
                        int diff6 = lenght[6] - AppConfiguration.fileData.get(pos).getLastAccessDate().length() + MainLayout.fileColumn[6].getText().length() - lenght[6] + diffColumn[6];
                        for (int i = 0; i < diff6; i++) { sb.append(" "); }
                    }

                    if (option[7]) {
                        sb.append(AppConfiguration.fileData.get(pos).getIsHidden());
                        int diff7 = lenght[7] - AppConfiguration.fileData.get(pos).getIsHidden().length() + MainLayout.fileColumn[7].getText().length() - lenght[7] + diffColumn[7];
                        for (int i = 0; i < diff7; i++) { sb.append(" "); }
                    }

                    sb.append("\n");
                }

                sb.append(separatorLine);

                //total file/size amount
                sb.append("\n");

                if (AppConfiguration.subFolderCheck) {
                    long totalSize = 0;
                    for (int i = 0; i < AppConfiguration.dirData.size(); i++) {
                        totalSize += AppConfiguration.dirData.get(i).getFileSizeInDir();
                    }
                    sb.append(AppConfiguration.fileData.size())
                            .append(" ").append(LanguageController.getString("files")).append("; ")
                            .append(totalSize).append(" Bytes");
                } else {
                    sb.append(AppConfiguration.dirData.get(0).getFilesInDir())
                            .append(" ").append(LanguageController.getString("files")).append("; ")
                            .append(AppConfiguration.dirData.get(0).getFileSizeInDir()).append(" Bytes");
                }
                break;

            //sorting is 'folder'
            case 1:
                sb.append(separatorLine);
                sb.append("\n");

                for (int pos1 = 0; pos1 < AppConfiguration.dirData.size(); pos1++) {

                    if (AppConfiguration.dirData.get(pos1).getFilesInDir() > 0 || canShowEmptyFolder) {

                        //add path
                        sb.append(AppConfiguration.dirData.get(pos1).getDir()).append("\n");
                        sb.append(separatorLine);
                        sb.append("\n");

                        //add files of subdir
                        for (int pos = 0; pos < AppConfiguration.fileData.size(); pos++) {
                            if (AppConfiguration.fileData.get(pos).getFilepath().equals(AppConfiguration.dirData.get(pos1).getDir())) {
                                if (option[0]) {
                                    sb.append(AppConfiguration.fileData.get(pos).getFilename());
                                    int diff = lenght[0] - AppConfiguration.fileData.get(pos).getFilename().length() + MainLayout.fileColumn[0].getText().length() - lenght[0] + diffColumn[0];
                                    for (int i = 0; i < diff; i++) {
                                        sb.append(" ");
                                    }
                                }

                                if (option[1]) {
                                    sb.append(AppConfiguration.fileData.get(pos).getFiletype());
                                    int diff1 = lenght[1] - AppConfiguration.fileData.get(pos).getFiletype().length() + MainLayout.fileColumn[1].getText().length() - lenght[1] + diffColumn[1];
                                    for (int i = 0; i < diff1; i++) {
                                        sb.append(" ");
                                    }
                                }

                                if (option[2]) {
                                    sb.append(AppConfiguration.fileData.get(pos).getFilepath());
                                    int diff2 = lenght[2] - AppConfiguration.fileData.get(pos).getFilepath().length() + MainLayout.fileColumn[2].getText().length() - lenght[2] + diffColumn[2];
                                    for (int i = 0; i < diff2; i++) {
                                        sb.append(" ");
                                    }
                                }

                                if (option[3]) {
                                    sb.append(MainLayout.fileTable.getColumns().get(3).getCellData(pos));
                                    int diff3 = lenght[3] - String.valueOf(MainLayout.fileTable.getColumns().get(3).getCellData(pos)).length() + MainLayout.fileColumn[3].getText().length() - lenght[3] + diffColumn[3];
                                    for (int i = 0; i < diff3; i++) {
                                        sb.append(" ");
                                    }
                                }

                                if (option[4]) {
                                    sb.append(AppConfiguration.fileData.get(pos).getCreationDate());
                                    int diff4 = lenght[4] - AppConfiguration.fileData.get(pos).getCreationDate().length() + MainLayout.fileColumn[4].getText().length() - lenght[4] + diffColumn[4];
                                    for (int i = 0; i < diff4; i++) {
                                        sb.append(" ");
                                    }
                                }

                                if (option[5]) {
                                    sb.append(AppConfiguration.fileData.get(pos).getLastModif());
                                    int diff5 = lenght[5] - AppConfiguration.fileData.get(pos).getLastModif().length() + MainLayout.fileColumn[5].getText().length() - lenght[5] + diffColumn[5];
                                    for (int i = 0; i < diff5; i++) {
                                        sb.append(" ");
                                    }
                                }

                                if (option[6]) {
                                    sb.append(AppConfiguration.fileData.get(pos).getLastAccessDate());
                                    int diff6 = lenght[6] - AppConfiguration.fileData.get(pos).getLastAccessDate().length() + MainLayout.fileColumn[6].getText().length() - lenght[6] + diffColumn[6];
                                    for (int i = 0; i < diff6; i++) {
                                        sb.append(" ");
                                    }
                                }

                                if (option[7]) {
                                    sb.append(AppConfiguration.fileData.get(pos).getIsHidden());
                                    int diff7 = lenght[7] - AppConfiguration.fileData.get(pos).getIsHidden().length() + MainLayout.fileColumn[7].getText().length() - lenght[7] + diffColumn[7];
                                    for (int i = 0; i < diff7; i++) {
                                        sb.append(" ");
                                    }
                                }

                                sb.append("\n");
                            }
                        }
                        sb.append(separatorLine);

                        //file/size amount of current folder
                        sb.append("\n");
                        sb.append(AppConfiguration.dirData.get(pos1).getFilesInDir())
                                .append(" ").append(LanguageController.getString("files")).append("; ")
                                .append(AppConfiguration.dirData.get(pos1).getFileSizeInDir()).append(" Bytes");

                        sb.append("\n\n");
                    }
                }

                sb.append(separatorLine);
                sb.append("\n");

                //total file/size amount
                long totalSize = 0;
                for (int i = 0; i < AppConfiguration.dirData.size(); i++) {
                    totalSize += AppConfiguration.dirData.get(i).getFileSizeInDir();
                }
                sb.append(AppConfiguration.fileData.size())
                        .append(" ").append(LanguageController.getString("files")).append("; ")
                        .append(totalSize).append(" Bytes");
                break;
        }
        return sb.toString();
    }

    private String getSeparatorLine(int[] diffColumn) {

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < MainLayout.fileColumn.length; i++) {
            if (option[i]) {
                int sepLine = MainLayout.fileColumn[i].getText().length() + diffColumn[i];
                for (int pos = 0; pos < sepLine; pos++) {
                    sb.append("-");
                }
            }
        }
        return sb.toString();
    }

    public void calcColumnLenght() {
        for (int i = 0; i < AppConfiguration.fileData.size(); i++) {
            if (lenght[0] < AppConfiguration.fileData.get(i).getFilename().length()) { lenght[0] = AppConfiguration.fileData.get(i).getFilename().length(); }
            if (lenght[1] < AppConfiguration.fileData.get(i).getFiletype().length()) { lenght[1] = AppConfiguration.fileData.get(i).getFiletype().length(); }
            if (lenght[2] < AppConfiguration.fileData.get(i).getFilepath().length()) { lenght[2] = AppConfiguration.fileData.get(i).getFilepath().length(); }
            if (lenght[3] < String.valueOf(MainLayout.fileTable.getColumns().get(3).getCellData(i)).length()) { lenght[3] = String.valueOf(MainLayout.fileTable.getColumns().get(3).getCellData(i)).length(); }
            if (lenght[4] < AppConfiguration.fileData.get(i).getCreationDate().length()) { lenght[4] = AppConfiguration.fileData.get(i).getCreationDate().length(); }
            if (lenght[5] < AppConfiguration.fileData.get(i).getLastModif().length()) { lenght[5] = AppConfiguration.fileData.get(i).getLastModif().length(); }
            if (lenght[6] < AppConfiguration.fileData.get(i).getLastAccessDate().length()) { lenght[6] = AppConfiguration.fileData.get(i).getLastAccessDate().length(); }
            if (lenght[7] < AppConfiguration.fileData.get(i).getIsHidden().length()) { lenght[7] = AppConfiguration.fileData.get(i).getIsHidden().length(); }
        }
        //System.out.println("Longest filelength: " + lenght[0] + " " + lenght[1] + " " + lenght[2] + " " + lenght[3] + " " + lenght[4]);

        for (int i = 0; i < AppConfiguration.dirData.size(); i++) {
            if (dirlenght < AppConfiguration.dirData.get(i).getDir().length()) { dirlenght = AppConfiguration.dirData.get(i).getDir().length(); }
        }
        //System.out.println("Longest dirlength: " + dirlenght);
    }

    /**
     * Saves the filelist to a text/html file
     */
    public void saveToFile(File file, int fileformatIDx){
        try {
            FileWriter fileWriter = new FileWriter(file);
            switch (fileformatIDx) {
                case 0: fileWriter.write(MainLayout.previewTA.getText()); break;
                case 1: fileWriter.write(getHTML()); break;
            }
            fileWriter.close();
        } catch (IOException e1) {
            if (DebugHelper.DEBUGVERSION) { e1.printStackTrace(); } else { new ExceptionHandler(Thread.currentThread(), e1); }
        }
    }

    /**
     * Builds the FileList in an HTML format and returns the HTML code as a string
     */
    private String getHTML() {
        StringBuilder html = new StringBuilder();
        html.append("<table border=\"1\" style=\"width:100%\">\n" + "<h1>File List</h1>\n" + "<h2>Created: ").append(CommonUtil.getDate("dd.MM.yyyy")).append(" at ").append(CommonUtil.getTime("HH:mm")).append("</h2>\n");
        for (int i = 0; AppConfiguration.fileData.size() > i; i++) {
            html.append("<tr>\n" + " <td>").append(AppConfiguration.fileData.get(i).getFilename()).append("</td>\n")
                    .append("    <td>").append(AppConfiguration.fileData.get(i).getFiletype()).append("</td>\n")
                    .append("    <td>").append(AppConfiguration.fileData.get(i).getFilepath()).append("</td>\n")
                    .append("    <td>").append(MainLayout.fileTable.getColumns().get(3).getCellData(i)).append("</td>\n")
                    .append("    <td>").append(AppConfiguration.fileData.get(i).getCreationDate()).append("</td>\n")
                    .append("    <td>").append(AppConfiguration.fileData.get(i).getLastModif()).append("</td>\n")
                    .append("    <td>").append(AppConfiguration.fileData.get(i).getLastAccessDate()).append("</td>\n")
                    .append("    <td>").append(AppConfiguration.fileData.get(i).getIsHidden()).append("</td>\n")
                    .append("</tr>\n");
        }
        html.append("</table>");
        return html.toString();
    }
}
