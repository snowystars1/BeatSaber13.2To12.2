package BSPackage;

import java.io.*;
import java.util.Scanner;

public class Cleanse {

    static boolean errorCheck=false;

    public static void main(String[] args){
        System.out.println("Type in the absolute path of your custom songs folder:");
        do{
            if(errorCheck)
                errorCheck=false;
            System.out.println("Example: C:\\Program Files (x86)\\Steam\\steamapps\\common\\Beat Saber\\CustomSongs");
            Scanner s = new Scanner(System.in);  // Create a Scanner object

            String songFolder = s.nextLine();  // Read user input
            File folder = new File(songFolder);
            Cleanse listFiles = new Cleanse();
            listFiles.cleanFiles(folder);
        }while(errorCheck);
        System.out.println("Success!");
    }

    private void cleanFiles(File folder){
        try{
            File[] fileNames = folder.listFiles();
            for(File file : fileNames){
                // if directory call the same method again
                if(file.getName().equals(".cache")){
                    continue;
                }
                if(file.isDirectory()){
                    cleanFiles(file);
                }else{
                    try {
                        if(file.getName().equals("ExpertPlus.json") || file.getName().equals("Expert.json") || file.getName().equals("Hard.json") || file.getName().equals("Normal.json") || file.getName().equals("Easy.json")){
                            readContent(file);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }
            }
        }catch(java.lang.NullPointerException e){
            System.out.println("Oops. Folder not found. You probably typed in the path incorrectly.");
            errorCheck=true;
        }
    }

    private void readContent(File file) throws IOException{
        //System.out.println("read file " + file.getCanonicalPath() );
        try(BufferedReader br  = new BufferedReader(new FileReader(file))){
            String strLine;
            // Read lines from the file, returns null when end of stream
            // is reached
            while((strLine = br.readLine()) != null){
                int warnings = strLine.indexOf(",\"_warnings\":[],\"_information\":[],\"_suggestions\":[],\"_requirements\":[]",0);
                strLine = strLine.replace(",\"_warnings\":[],\"_information\":[],\"_suggestions\":[],\"_requirements\":[]","");
                if(warnings!=-1){
                    strLine = strLine.replace(",\"_warnings\":[],\"_information\":[],\"_suggestions\":[],\"_requirements\":[]","");
                    BufferedWriter bwr = new BufferedWriter(new FileWriter(file));
                    bwr.write(strLine,0,strLine.length());
                    bwr.close();
                }
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
}
