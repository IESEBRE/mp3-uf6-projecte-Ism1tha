    package com.insebre.project.controller;

    import com.insebre.project.exception.FileCorruptDataException;
    import com.insebre.project.exception.FileDataGenerateErrorException;
    import com.insebre.project.exception.FileNullOnSaveException;
    import com.insebre.project.model.Program;

    import javax.swing.*;
    import java.io.*;

    public class DataController {
        /* Constants */
        public static final int MAX_PROGRAMS = 200;

        /* Program Data */
        public static Program[] appData = new Program[MAX_PROGRAMS];
        public static int appDataIndex = 0;

        public static void loadData(){
            try{
                File data = new File(System.getProperty("user.dir"), "programs.dat");
                FileInputStream fis = new FileInputStream(data);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                while(true) {
                    Program item = (Program) ois.readObject();
                    appData[appDataIndex] = item;
                    appDataIndex++;
                }
            }catch (NullPointerException | FileNotFoundException ex){
                String dataFile = System.getProperty("user.dir") + "/programs.dat";
                try {
                    FileOutputStream fos = new FileOutputStream(dataFile);
                    fos.close();
                    JOptionPane.showMessageDialog(null,
                            "El archivo de datos se ha generado correctamente.",
                            "Información",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch(IOException e) {
                    ExceptionController.handleException(new FileDataGenerateErrorException());
                }
            }
            catch (EOFException ex){
                JOptionPane.showMessageDialog(null,
                        "El archivo de datos se ha encontrado y se han cargado correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (ClassNotFoundException ex) {
                ExceptionController.handleException(new FileCorruptDataException());
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        ex,
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Data loaded successfully.");
        }

        public static void saveData() {
            try{
                File data = new File(System.getProperty("user.dir"), "programs.dat");
                FileOutputStream fos = new FileOutputStream(data);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                for(int i = 0; i < appDataIndex; i++) {
                    if(appData[i] != null ) {
                        oos.writeObject(appData[i]);
                    }
                }
                oos.close();
                bos.close();
                fos.close();
            } catch (FileNotFoundException ex) {
                ExceptionController.handleException(new FileNullOnSaveException());
            } catch (IOException ex) {
                ExceptionController.handleException(ex);
            }
        }

        public static Program[] getData() { return appData; }

        public static void setData(Program[] newData) {
            appData = newData;
        }

        public static void realocateDataObjects(){
            Program[] appDataTmp = new Program[MAX_PROGRAMS];
            int tmpIndex = 0;
            for(int i = 0; i < MAX_PROGRAMS; i++){
                if(appData[i] != null){
                    appDataTmp[tmpIndex] = appData[i];
                    tmpIndex++;
                }
            }
            appData = appDataTmp;
        }

        public static Object[][] getParsedPrograms() {
            Program[] programs = DataController.getData();
            Object[][] parsedPrograms = new Object[appDataIndex][6];
            for (int i = 0; i < appDataIndex; i++) {
                parsedPrograms[i][0] = programs[i].getName();
                parsedPrograms[i][1] = programs[i].getDescription();
                parsedPrograms[i][2] = programs[i].getCategory();
                parsedPrograms[i][3] = programs[i].getLanguage();
                parsedPrograms[i][4] = programs[i].getVersion();
                parsedPrograms[i][5] = programs[i].getReleaseDate();
            }
            return parsedPrograms;
        }

        public static Object[][] getParsedProgramVersions(int programIndex) {
            Program program = appData[programIndex];
            if (program == null || program.getVersions() == null || program.getVersions().isEmpty()) {
                return new Object[0][3]; // Return an empty 2D array with 0 rows and 3 columns
            }

            Object[][] parsedProgramVersions = new Object[program.getVersions().size()][3];
            for (int i = 0; i < program.getVersions().size(); i++) {
                parsedProgramVersions[i][0] = program.getVersions().get(i).getVersion();
                parsedProgramVersions[i][1] = program.getVersions().get(i).getDate();
                parsedProgramVersions[i][2] = program.getVersions().get(i).getCommits();
            }
            return parsedProgramVersions;
        }

        public static void addProgramVersion(int programIndex, String version, String releaseDate, String commits) {
            Program program = appData[programIndex];
            if (program == null) {
                return;
            }
            program.addVersion(version, releaseDate, commits);
            saveData();
        }

        public static void editProgramVersion(int programIndex, int versionIndex, String version, String releaseDate, String commits) {
            Program program = appData[programIndex];
            if (program == null) {
                return;
            }
            program.editVersion(versionIndex, version, releaseDate, commits);
            saveData();
        }

        public static void deleteProgramVersion(int programIndex, int versionIndex) {
            Program program = appData[programIndex];
            if (program == null) {
                return;
            }
            program.deleteVersion(versionIndex);
            saveData();
        }

        public static void switchProgramSuperCollectionType(int programIndex) {
            Program program = appData[programIndex];
            if (program == null) {
                return;
            }
            program.switchSuperCollectionType();
            saveData();
        }
    }
