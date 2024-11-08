import java.util.ArrayList;
import java.util.Scanner;
public class SimulatorOne{
    public static void main (String[] args){
        Scanner sc = new Scanner(System.in);
        String[] arrEdgesCost = new String[100000];
        Graph graph =  new Graph();

        int numberOfNodes = Integer.parseInt( sc.nextLine());        
        String arrGraph[] = new String[numberOfNodes];
        for (int i =0;i<numberOfNodes;i++){
            arrGraph[i] = sc.nextLine();
        }
        int numberOfShops = Integer.parseInt( sc.nextLine());  
        String[] shopNodes = sc.nextLine().split(" ");
        int numberOfClients = Integer.parseInt( sc.nextLine());  
        String[] clientNodes = sc.nextLine().split(" ");        
        sc.close();           
                              
		for (int j=0;j<numberOfNodes;j++) {
                String line = arrGraph[j];
                arrEdgesCost = line.split(" ");
                for (int i = 1; i<arrEdgesCost.length;i=i+2){
                    String source = arrEdgesCost[0];
                    String destination = arrEdgesCost[i];
                    double cost = 0;
                    cost = Double.parseDouble(arrEdgesCost[i+1]);
                    graph.addEdge(source, destination, cost);
                }
            }

String[] results = new String[numberOfClients];
for (int i=0;i<numberOfClients;i++){
    results[i]="";
}
//taxi to client
for (int p=0;p<numberOfClients;p++){ 
    String client = clientNodes[p];

    double min = 1000000;
    
    ArrayList<Double> arrCost = new ArrayList<>();
    ArrayList<String> arrTaxi = new ArrayList<>();
    ArrayList<String> arrPath = new ArrayList<>();

    String finalPath = "client "+client+"\n";
 
    for (int i = 0;i<numberOfShops;i++){               
        String taxi = shopNodes[i];
        String sameStartsameDest = graph.dijkstra3(taxi);     
        arrCost.add(graph.getCost(client));
        arrTaxi.add(taxi);
        arrPath.add(taxi+" "+graph.getPath(client));                         
    }    

           
    for (int j = 0;j<numberOfShops;j++){
        String taxi = shopNodes[j];
        String sameStartsameDest = graph.dijkstra3(taxi);
        String[] multiPaths = sameStartsameDest.split(" ");      
 
        for (int k=0;k<multiPaths.length;k++){
            if (client.equals(multiPaths[k]))
            {                      
                arrPath.set(j, "multiple solutions cost "+Math.round(arrCost.get(j)));
            }
        }      

    }     

    for (int i = 0;i<numberOfShops;i++){
        if (arrCost.get(i)<min){
            min = arrCost.get(i);        
        }                      
    }

    for (int i=0; i<numberOfShops-1;i=i+2){
        if (Integer.parseInt(arrTaxi.get(i)) >Integer.parseInt(arrTaxi.get(i+1)) ){
            double tempCost = arrCost.get(i);
            arrCost.set(i, arrCost.get(i+1));
            arrCost.set(i+1, tempCost);

            String tempTaxi = arrTaxi.get(i);
            arrTaxi.set(i, arrTaxi.get(i+1));
            arrTaxi.set(i+1, tempTaxi);   
            
            String tempPath = arrPath.get(i);
            arrPath.set(i, arrPath.get(i+1));
            arrPath.set(i+1, tempPath);             
        }
    }

    for (int i = 0;i<numberOfShops;i++){
        if (arrCost.get(i) == min){
            finalPath += "taxi "+arrTaxi.get(i)+"\n"+arrPath.get(i)+"\n";       
        }         
        //finalPath += "\n";        
    }     
      
    if (min == 1000000){
        results[p] = "client "+client+"\ncannot be helped";
    } else
        results[p] += finalPath;
} // taxi to client
                     

        
//client to shop
        for (int p=0;p<numberOfClients;p++){ 
            String client = clientNodes[p];
            String sameStartsameDest = graph.dijkstra3(client);

            double min = 1000000;
            
            ArrayList<Double> arrCost = new ArrayList<>();
            ArrayList<String> arrShop = new ArrayList<>();
            ArrayList<String> arrPath = new ArrayList<>();

            String finalPath = "";

            for (int i = 0;i<numberOfShops;i++){
                String shop = shopNodes[i];
                arrCost.add(graph.getCost(shop));
                arrShop.add(shop);
                arrPath.add(client+" "+graph.getPath(shop));
            }
               
            for (int i = 0;i<numberOfShops;i++){
                String shop = shopNodes[i];
                String[] multiPaths = sameStartsameDest.split(" ");      
   
                for (int k=0;k<multiPaths.length;k++){
                    if (shop.equals(multiPaths[k]))
                    {          
                        int j=i;           
                        arrPath.set(j, "multiple solutions cost "+Math.round(arrCost.get(j)));
                    }
                }                 
            } 
            

            for (int i = 0;i<numberOfShops;i++){
                if (arrCost.get(i)<min){
                    min = arrCost.get(i);        
                }                      
            } 

            for (int i = 0;i<numberOfShops;i++){
                if (arrCost.get(i) == min){                    
                    finalPath += "shop "+arrShop.get(i)+"\n"+arrPath.get(i)+"\n";       
                }                 
            }            
            if (results[p].contains("cannot be helped") || (min==1000000))
            {
                results[p] = "client "+client+"\ncannot be helped";
            } else
                results[p] += finalPath;
        } // client to shop
        
        for (int i=0;i<numberOfClients;i++){
            System.out.print(results[i]);
        }

    } // main

} // class