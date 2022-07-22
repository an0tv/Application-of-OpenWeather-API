import java.io.*;
import java.net.MalformedURLException;
import java.util.*;
import javax.swing.*;
import java.awt.event.*;
public class UserInterface {
    private String key;
    public boolean APIKey, position;
    public List<Coordinates> coordArr;
    public List<Coordinates> cityArray;
    UserInterface() {
        this.key = "";
        this.APIKey = false;
        this.position = false;
        this.coordArr = new ArrayList<>();
        this.cityArray = new ArrayList<>();
    }
    public void startUI() throws IOException {
        checkSave();
        Scanner input = new Scanner(System.in);
        //IF statments check if there are position and apikey files
        //IF they do not exists program asks to enter api key and position if needed
        if(!APIKey) {
            System.out.println("Enter your key from https://openweathermap.org");
            if(input.hasNextLine()) {
                this.key = input.nextLine();
            }
        }
        if(!position) {
            getLoc();
        }
        int z = 0;
        for (Coordinates items : coordArr) {
            System.out.println("[" + z + "] " + items.toString());
            z++;
        }
        String uin = "";
        //While loop with a switch statment to add some sort of UI, 5 selections
        //Add location, Delete location, Select location, close program, and print all locations
        while(!uin.equals("C")){
            System.out.println("Add, Delete, Select, Close, Print locations (A/D/S/C/P)");
            uin = input.nextLine();
            switch(uin) {
                case "A":
                    getLoc();
                    break;
                case "D":
                    System.out.println("Enter number to delete");
                    int din;
                    if(input.hasNextInt()){
                        din = input.nextInt();
                    }
                    else{
                        System.out.println("Enter a number next time");
                        input.nextLine();
                        break;
                    }
                    if(din > coordArr.size() - 1 || din < 0)
                    {
                        System.out.println("Out of bounds");
                        input.nextLine();
                        break;
                    }
                    coordArr.remove(coordArr.get(din));
                    input.nextLine();
                    break;
                case "S":
                    System.out.println("Enter number to select");
                    int sin;
                    if(input.hasNextInt()){
                        sin = input.nextInt();
                    }
                    else{
                        System.out.println("Enter a number next time");
                        input.nextLine();
                        break;
                    }
                    if(sin > coordArr.size() - 1 || sin < 0)
                    {
                        System.out.println("Out of bounds");
                        input.nextLine();
                        break;
                    }
                    GetWeather test = new GetWeather(coordArr.get(sin).getLat(), coordArr.get(sin).getLon(), key);
                    input.nextLine();
                    break;
                case "P":
                    int x = 0;
                    for (Coordinates items : coordArr) {
                        System.out.println("[" + x + "] " + items.toString());
                        x++;
                    }
                    break;
                case "C":
                    break;

            }
        }
        input.close();
        Save();
    }
    //function that checks if the files with api key and locations exists, if exists writes api to var and locations to a list
    private void checkSave() throws FileNotFoundException {
        File API = new File("APIKey.txt");
        File Location = new File("Location.txt");
        if(API.exists()) {
            Scanner apiFile = new Scanner(API);
            key = apiFile.nextLine();
            APIKey = true;
        }
        if(Location.exists()){
            Scanner locFile = new Scanner(Location);
            while(locFile.hasNextLine())
            {
                String[] parts = locFile.nextLine().split(",");
                coordArr.add(new Coordinates(parts[3],parts[4], parts[0], parts[1], parts[2]));
            }
            if(coordArr.size() != 0) {
                position = true;
            }
        }
    }
    //function that saves all the changes to a file, only runs when user decides to close teh program via 'C' key.
    private void Save(){
        File API = new File("APIKey.txt");
        File Location = new File("Location.txt");
        if(!API.exists()) {
            try {
                API.createNewFile();
                FileWriter writter = new FileWriter(API);
                writter.write(key);
                writter.close();
            } catch (Exception e) {
                System.out.println("Error creating file");
            }
        }
        if(!Location.exists()) {
            try {
                Location.createNewFile();
                FileWriter writter = new FileWriter(Location);
                for(Coordinates items : coordArr){
                    writter.write(items.toFile());
                }
                writter.close();
            } catch (Exception e) {
                System.out.println("Error creating file");
            }
        }
        else{
            try {
                Location.delete();
                Location.createNewFile();
                FileWriter writter = new FileWriter(Location);
                BufferedWriter bw = new BufferedWriter(writter);
                for (Coordinates items : coordArr) {
                    bw.write(items.toFile());
                    bw.newLine();
                }
                bw.close();
                writter.close();
            }
            catch (Exception e)
            {
                System.out.println("Failed writing to file");
            }
        }
    }
    //Part of the user interface that allows the user to input a city name and select the city from a list of cities.
    private void getLoc() throws MalformedURLException {
        Scanner input2 = new Scanner(System.in);
        String city = "";
        System.out.println("Enter your city");
        if(input2.hasNextLine()) {
            city = input2.nextLine();
        }
        GetLonLat cityIn = new GetLonLat(city , this.key);
        for(int i = 0; i < cityIn.jsonCityArray.size(); i++){
            Coordinates temp = new Coordinates(Double.toString(cityIn.jsonCityArray.get(i).getDouble("lat")), Double.toString(cityIn.jsonCityArray.get(i).getDouble("lon")), cityIn.jsonCityArray.get(i).getString("country"), cityIn.jsonCityArray.get(i).getString("state"), cityIn.jsonCityArray.get(i).getString("name"));
            cityArray.add(temp);
        }
        int i = 0;
        for(Coordinates items : cityArray) {
            System.out.println("[" + i + "] " + items.toString());
            i++;
        }
        if(cityArray.size() < 1)
        {
            System.out.println("Invalid name");
            return;
        }
        System.out.println("Enter the number you want");
        int userIn = 0;
        if(input2.hasNextInt())
        {
            userIn = input2.nextInt();
        }
        if(userIn >= cityArray.size() || userIn < 0)
        {
            System.out.println("Out of bounds");
            cityArray.clear();
            return;
        }
        coordArr.add(cityArray.get(userIn));
        cityArray.clear();
    }
}  
