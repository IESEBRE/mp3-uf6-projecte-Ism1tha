    package com.insebre.project.controller;

    import com.insebre.project.model.Programa;

    import javax.swing.*;
    import java.io.*;

    public class DataController {
        /* Constants */
        public static final int MAX_PROGRAMS = 200;

        /* Program Data */
        public static Programa[] dadesApp = new Programa[MAX_PROGRAMS];
        public static int dadesAppIndex = 0;

        public static void loadData(){
            try{
                File data = new File(System.getProperty("user.dir"), "programs.dat");
                FileInputStream fis = new FileInputStream(data);
                BufferedInputStream bis = new BufferedInputStream(fis);
                ObjectInputStream ois = new ObjectInputStream(bis);
                while(true) {
                    Programa item = (Programa) ois.readObject();
                    dadesApp[dadesAppIndex] = item;
                    dadesAppIndex++;
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
                    JOptionPane.showMessageDialog(null,
                            "Se ha producido un error al generar el archivo de datos.",
                            "Error",
                            JOptionPane.INFORMATION_MESSAGE);
                }
            }
            catch (EOFException ex){
                JOptionPane.showMessageDialog(null,
                        "El archivo de datos se ha encontrado y se han cargado correctamente.",
                        "Información",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (ClassNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "El archivo de datos parece estar corrupto.",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        ex,
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
            System.out.println("Data loaded successfully.");
        }

        public static void saveData(){
            try{
                File data = new File(System.getProperty("user.dir"), "programs.dat");
                FileOutputStream fos = new FileOutputStream(data);
                BufferedOutputStream bos = new BufferedOutputStream(fos);
                ObjectOutputStream oos = new ObjectOutputStream(bos);
                for(int i = 0; i < MAX_PROGRAMS; i++) {
                    if(dadesApp[i] != null ) oos.writeObject(dadesApp[i]);
                }
                oos.close();
                bos.close();
                fos.close();
            } catch (FileNotFoundException ex) {
                JOptionPane.showMessageDialog(null,
                        "No se ha encontrado el archivo de datos al guardar.",
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null,
                        ex,
                        "Error",
                        JOptionPane.INFORMATION_MESSAGE);
            }
        }

        public static Programa[] getData() { return dadesApp; }

        public static void setData(Programa[] newData) {
            dadesApp = newData;
        }

        public static void addDummyData () {
            Programa[] programas = new Programa[3];
            programas[0] = new Programa("Program 1", "Description 1", "Category 1", "Language 1", "1.0", "2021-01-01");
            programas[1] = new Programa("Program 2", "Description 2", "Category 2", "Language 2", "2.0", "2021-02-02");
            programas[2] = new Programa("Program 3", "Description 3", "Category 3", "Language 3", "3.0", "2021-03-03");
            setData(programas);
        }

        public static void relocateDataObjects(){
            Programa[] dadesAppTmp = new Programa[MAX_PROGRAMS];
            int tmpIndex = 0;
            for(int i = 0; i < MAX_PROGRAMS; i++){
                if(dadesApp[i] != null){
                    dadesAppTmp[tmpIndex] = dadesApp[i];
                    tmpIndex++;
                }
            }
            dadesApp = dadesAppTmp;
        }

        public static Object[][] getParsedPrograms() {
            Programa[] programas = DataController.getData();
            Object[][] parsedPrograms = new Object[programas.length][6];
            for (int i = 0; i < programas.length; i++) {
                parsedPrograms[i][0] = programas[i].getName();
                parsedPrograms[i][1] = programas[i].getDescription();
                parsedPrograms[i][2] = programas[i].getCategory();
                parsedPrograms[i][3] = programas[i].getLanguage();
                parsedPrograms[i][4] = programas[i].getVersion();
                parsedPrograms[i][5] = programas[i].getReleaseDate();
            }
            return parsedPrograms;
        }

    }
