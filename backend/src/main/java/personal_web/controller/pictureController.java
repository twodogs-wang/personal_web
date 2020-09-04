package personal_web.controller;
import org.springframework.http.HttpStatus;
import personal_web.entity.Picture;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.io.File;
import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.io.*;

@RestController
@RequestMapping("/Pictures")
public class pictureController {
    private HashMap<Integer,Picture> pictureMap = csv_to_HashMap();
    //pictures are all on the could. No database needed
    private int size= pictureMap.size();
    @PostMapping(value="/addPicture")
    public ResponseEntity<String> addPicture(@RequestBody Picture temp) {
        File file = new File(temp.getLink());
        if(file.exists()) return new ResponseEntity<String>("file already exists·",HttpStatus.BAD_REQUEST);
        this.size++;
        //temp.addId(this.size++);
        pictureMap.put(temp.getId(),temp);
        HashMap_to_CSV(pictureMap);
        return new ResponseEntity<String>("file saved",HttpStatus.OK);
    }


    @DeleteMapping("/deletePicture")
    public ResponseEntity<String> deletePictureById(@RequestParam int id) {
        if(!pictureMap.containsKey(id)) return new ResponseEntity<String>("already deleted",HttpStatus.BAD_REQUEST);
        String target = this.pictureMap.get(id).getLink();
        this.pictureMap.remove(id);
        HashMap_to_CSV(pictureMap);
        this.size--;
        String msg="";
        try{
            File file = new File(target);
            if(file.delete()){msg="file deleted";}
            else{
                msg="delete failed";
            }
        }
        catch(Exception e){e.printStackTrace();}

        return new ResponseEntity<String>(msg,HttpStatus.OK);
        //return ResponseEntity.ok(pictureMap);
    }

    @GetMapping("/getLink")
    public ResponseEntity<String> getPictureById(@RequestParam("id") int id) {
        //String ans = "not found";
        if(pictureMap.containsKey((id))) return new ResponseEntity<>(this.pictureMap.get(id).getLink(),HttpStatus.OK);
        else return new ResponseEntity<>("not found",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/getDescription")
    public ResponseEntity<String> getDescriptionById(@RequestParam("id") int id) {
        //String ans = "not found";
        if(pictureMap.containsKey((id))) return new ResponseEntity<>(this.pictureMap.get(id).getDescription(),HttpStatus.OK);
        else return new ResponseEntity<>("not found",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/validID")
    public ResponseEntity<Integer> getValidID() {
        //Id must be starts from 1, so size equals to IDs
        int id = 1;
        for(Integer key:this.pictureMap.keySet()){
            if(id == this.pictureMap.get(key).getId()) id++;
        }
        return new ResponseEntity<Integer>(id,HttpStatus.OK);
    }
    public static void main(String[] args){
        System.out.println("Testing pictureController.java..........");
        HashMap<Integer,Picture> map = csv_to_HashMap();
        for(Integer key: map.keySet()){
            System.out.println(map.get(key).getLink());
        }

    }
    private static HashMap<Integer,Picture> csv_to_HashMap(){
        HashMap<Integer,Picture> temp = new HashMap<>();
        String Path = "C://Users//Administrator//Dropbox//I_am_Mr_Sex//storage//path//picture_path.csv";
        BufferedReader reader;
        try{
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(Path)));
        }
        catch(Exception e){
            System.out.println("file "+Path+"does not exist");
            return temp;
        }

        String line="";
        while(true){
            try{
                line = reader.readLine();
                if(line==null) break;
            }
            catch(IOException e){System.out.println(("input/output error, reading terminate"));}

            String[] info = line.split(",");
            int id=-1;
            try{
                id = Integer.parseInt(info[0]);
            }
            catch(Exception e){
                id = temp.size()+1;
            }
            temp.put(id,new Picture (id,info[1],info[2],info[3]));
        }

        return temp;
    }
    private void HashMap_to_CSV(HashMap<Integer,Picture> map){
        String path = "C://Users//Administrator//Dropbox//I_am_Mr_Sex//storage//path//picture_path.csv";
        File csv = new File(path);
        try{
            BufferedWriter file = new BufferedWriter(new FileWriter(csv,false));
            //file.
            for(Integer key:map.keySet()){
                ArrayList<String> row = new ArrayList<>();
                row.add(String.valueOf(map.get(key).getId()));
                row.add(map.get(key).getName());
                row.add(map.get(key).getLink());
                row.add(map.get(key).getDescription());
                String line = String.join(",",row);
                //System.out.println(line);
                file.write(line);
                file.newLine();
            }
            file.flush();
            file.close();
        }
        catch(IOException e){
            e.printStackTrace();
        }
    }
}
