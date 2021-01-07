import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Metro {
    List<Station> stations;
    Map<String, List<Station>> metro;
    Map<Integer, Station> ids = new LinkedHashMap<>();
    String operation = "";
    String metroName = "";
    String stationName = "";
    String weight = "0";
    MetroGraph mg = new MetroGraph();

    public void run(String filePath) {
        //Map<String, JsonObject> metros = Utils.loadFromJson(filePath);
        Map<String, JsonObject> metros = new LinkedHashMap<>();
        Map<String, List<Station>> hyperMetro = Utils.loadFromJson(filePath);
        metro = new LinkedHashMap<>();
        if (metros != null) {
            int startId = 0;
            for (Map.Entry<String, JsonObject> entry : metros.entrySet()) {
              /*  stations = Utils.getStations(entry.getValue().toString());
                Station depot = new Station();
                depot.setName("depot");
                stations.add(0, depot);
                stations.add(depot);
                metro.put(entry.getKey(), stations);*/
               // System.out.println(entry.getKey());
               // System.out.println(entry.getValue().toString());
                stations = Utils.getStations(entry.getValue().toString());
                /*for (Station station : stations) {
                    station.setGraph_id(startId);
                    mg.addStations(station);
                    station.line = entry.getKey();
                    ids.put(startId, station);
                    startId++;
                }*/
                Station depot = new Station();
                depot.setName("depot");
                stations.add(0, depot);
                stations.add(depot);
                metro.put(entry.getKey(), stations);
                //printStation(stations);
            }
       /*List<String> stations = Utils.readF(filePath);
       stations.add(0, "depot");
       stations.add("depot");
       printStation(stations);*/
            //mg.bfs(ids.get(1), ids.get(3));
            //indexStation();
          //  createGraph(1,3);
            menu();
        }
    }

    public void indexStation() {
        int startId = 0;
        ids = new LinkedHashMap<>();
        for (Map.Entry<String, List<Station>> entry : metro.entrySet()) {
          //  System.out.println("Line is " + entry.getKey());
            for (Station station : entry.getValue()) {
            //    System.out.println("name is " + station.name);
                if (!station.name.equals("depot")) {
                    station.setGraph_id(startId);
                   // mg.addStations(station);
                    station.line = entry.getKey();
                    ids.put(startId, station);
                    startId++;
                }
            }
        }
    }




    public void createGraph(int idSrc, int idDst) {

        int size = ids.size();
        pathUnweighted pu = new pathUnweighted(size);

        for (Map.Entry<String, List<Station>> entry : metro.entrySet()) {
            //  System.out.println(entry.getKey());
            for (int i = 1; i < entry.getValue().size() - 1; i++) {
                //System.out.println(entry.getValue().get(i).getGraph_id());
                if (!entry.getValue().get(i+1).getName().equals("depot")) {
                    pu.addEdge(entry.getValue().get(i).graph_id, entry.getValue().get(i + 1).graph_id);
                }
                if (entry.getValue().get(i).transfer != null && entry.getValue().get(i).transfer.size() > 0) {
                    int tempId = 0;
                    for (Station s : metro.get(entry.getValue().get(i).transfer.get(0).line)) {
                        if (s.name.equals(entry.getValue().get(i).name)) {
                            tempId = s.graph_id;
                        }
                    }
                    pu.addEdge(entry.getValue().get(i).graph_id, ids.get(tempId).graph_id);
                }
            }
        }
        LinkedList<Integer> path = pu.printShortestDistance(idSrc, idDst, size);
        Collections.reverse(path);
        String prevLine = ids.get(path.get(0)).line;
        for (Integer i : path) {
            if (!ids.get(i).line.equals(prevLine)) {
                System.out.println("Transition to line " + ids.get(i).line);
                prevLine = ids.get(i).line;
            }
            System.out.println(ids.get(i).getName());
        }


    }


    public void menu() {
        Scanner sc = new Scanner(System.in);
        boolean isRun = true;
        List<Station> temp;
        Station st;
        while (isRun) {
            String command = sc.nextLine();
            parseCommand(command);
            switch (operation) {
                case "/append":
                    temp = metro.get(metroName);
                    st = new Station();
                    st.setName(stationName);
                    st.time = Integer.parseInt(weight);
                    temp.add(temp.size() - 1 , st);
                    metro.replace(metroName, temp);
                    break;
                case "/add-head":
                    temp = metro.get(metroName);
                    st = new Station();
                    st.setName(stationName);
                    temp.add(1 , st);
                    metro.replace(metroName, temp);
                    break;
                case "/remove":
                    temp = metro.get(metroName);
                    for (int i = 0; i < temp.size(); i++) {
                        if (temp.get(i).getName().equals(stationName)) {
                            temp.remove(i);
                        }
                    }
                    metro.replace(metroName, temp);
                    break;
                case "/output":
                    printStation(metro.get(metroName));
                    /*for (Map.Entry<String, List<Station>> entry : metro.entrySet()) {
                        printStation(entry.getValue());
                    }*/
                    break;
                case "/route":
                    indexStation();
                    String[] arrayr = command.split(" \"");
                    String line1r = arrayr[1].replace("\"", "");
                    String station1r = arrayr[2].replace("\"", "");
                    String line2r = arrayr[3].replace("\"", "");
                    String station2r = arrayr[4].replace("\"", "");
                    int idSrc = 0;
                    int idDst = 0;
                    for (Map.Entry<String, List<Station>> entry : metro.entrySet()) {
                        for (Station s : entry.getValue()) {
                            if (s.line.equals(line1r) && s.name.equals(station1r)) idSrc = s.graph_id;
                            if (s.line.equals(line2r) && s.name.equals(station2r)) idDst = s.graph_id;
                        }
                    }
                    createGraph(idSrc, idDst);
                    break;
                case "/fastest-route":
                    indexStation();
                    String[] arrayf = command.split(" \"");
                    String line1f = arrayf[1].replace("\"", "");
                    String station1f = arrayf[2].replace("\"", "");
                    String line2f = arrayf[3].replace("\"", "");
                    String station2f = arrayf[4].replace("\"", "");
                    idSrc = 0;
                    idDst = 0;
                    for (Map.Entry<String, List<Station>> entry : metro.entrySet()) {
                        for (Station s : entry.getValue()) {
                            if (s.line.equals(line1f) && s.name.equals(station1f)) idSrc = s.graph_id;
                            if (s.line.equals(line2f) && s.name.equals(station2f)) idDst = s.graph_id;
                        }
                    }
                    Dijkstra d = new Dijkstra();
                    d.createGraph(idSrc, idDst, ids, metro);
                    break;
                case "/connect":
                    String[] array = command.split(" \"");
                    String line1 = array[1].replace("\"", "");
                    String station1 = array[2].replace("\"", "");
                    String line2 = array[3].replace("\"", "");
                    String station2 = array[4].replace("\"", "");
                    Transfer transfer1 = new Transfer(line1, station1);
                    Transfer transfer2 = new Transfer(line2, station2);
                   // System.out.println(line1 + "/" + station1 + "/" + line2 + "/" + station2);

                    List<Station> temp1 = metro.get(line1);
                    for (int i = 0; i < temp1.size(); i++) {
                        if (temp1.get(i).name.equals(station1)) {
                            Station tempSt = temp1.get(i);
                            //tempSt.setTransfer(transfer2);
                            temp1.set(i, tempSt);
                        }
                    }
                    metro.replace(line1, temp1);

                    List<Station> temp2 = metro.get(line2);
                    for (int i = 0; i < temp2.size(); i++) {
                        if (temp2.get(i).name.equals(station2)) {
                            Station tempSt = temp2.get(i);
                            //tempSt.setTransfer(transfer1);
                            temp2.set(i, tempSt);
                        }
                    }
                    metro.replace(line2, temp2);


                    /*for (Map.Entry<String, List<Station>> entry : metro.entrySet()) {
                        printStation(entry.getValue());
                    }*/
                    break;
                case "/exit":
                    isRun = false;
                    break;
            }
        }

    }

    public void parseCommand(String input) {
        operation = "";
        metroName = "";
        stationName = "";
        weight = "0";
        //input = input.replace("\"", "");
        String[] array = input.split(" \"");
        for (int i = 0; i < array.length; i++) {
            if (i == 0) operation = array[i];
            else if (i == 1) metroName = array[i];
            else if (i == 2) stationName = array[i];
                else weight = array[i];
        }
        operation = operation.replace("\"", "").trim();
        metroName = metroName.replace("\"", "").trim();;
        stationName = stationName.replace("\"", "").trim();;
        weight = weight.replace("\"", "").trim();;
    }

    public void printStation(List<Station> stations) {

            for (int i = 0; i < stations.size() - 2; i++) {
                System.out.print(stations.get(i).print());
                System.out.print(" - ");
                System.out.print(stations.get(i + 1).print());
                System.out.print(" - ");
                System.out.print(stations.get(i + 2).print());
                System.out.println();
            }


        /*for (int i = 0; i < stations.size(); i++) {
            System.out.println(stations.get(i).print());
        }*/
    }

}
